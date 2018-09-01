//jqGrid的配置信息
$.jgrid.defaults.width = 1000;
$.jgrid.defaults.responsive = true;
$.jgrid.defaults.styleUI = 'Bootstrap';

//工具集合Tools
window.T = {};

// 获取请求参数
// 使用示例
// location.href = http://localhost/index.html?id=123
// T.p('id') --> 123;
var url = function(name) {
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r!=null)return  unescape(r[2]); return null;
};
T.p = url;

//请求前缀
var baseURL = "/xsquare/";

//登录token
var token = localStorage.getItem("token");
if(token == 'null'){
    parent.location.href = baseURL + 'login.html';
}

//jquery全局配置
$.ajaxSetup({
	dataType: "json",
	cache: false,
    headers: {
        "token": token
    },
    xhrFields: {
	    withCredentials: true
    },
    complete: function(xhr) {
        //token过期，则跳转到登录页面
        if(xhr.responseJSON.code == 401){
            parent.location.href = baseURL + 'login.html';
        }
    }
});

//jqgrid全局配置
$.extend($.jgrid.defaults, {
    ajaxGridOptions : {
        headers: {
            "token": token
        }
    }
});

//权限判断
function hasPermission(permission) {
    if (window.parent.permissions.indexOf(permission) > -1) {
        return true;
    } else {
        return false;
    }
}

//重写alert
window.alert = function(msg, callback){
    if ( msg == '请将您的信息填写完整') {
        if (typeof(callback) === "object") {
            var placeholder = callback.context.placeholder
            log("placeholder", placeholder)
            if (placeholder != null && placeholder != '') {
                msg = placeholder + '不可为空'
            }
         }
    }
	parent.layer.alert(msg, function(index){
		parent.layer.close(index);
		if(typeof(callback) === "function"){
			callback("ok");
		}
        if (typeof(callback) === "object") {
            callback.focus()
        }
	});
}

//重写confirm式样框
window.confirm = function(msg, callback){
	parent.layer.confirm(msg, {btn: ['确定','取消']},
	function(){//确定事件
		if(typeof(callback) === "function"){
			callback("ok");
		}
	});
}

//选择一条记录
function getSelectedRow() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
    	alert("请选择一条记录");
    	return ;
    }
    
    var selectedIDs = grid.getGridParam("selarrrow");
    if(selectedIDs.length > 1){
    	alert("只能选择一条记录");
    	return ;
    }
    return selectedIDs[0];
}

//选择theJqGrid表格的一条记录
function getTheSelectedRow() {
    var grid = $("#theJqGrid");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
        alert("请选择一条记录");
        return ;
    }

    var selectedIDs = grid.getGridParam("selarrrow");
    if(selectedIDs.length > 1){
        alert("只能选择一条记录");
        return ;
    }
    return selectedIDs[0];
}

//单选
function getSelectOne() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    // console.log("rowKey:"+rowKey)
    return rowKey
}

//theJqGrid表格单选
function getTheSelectOne() {
    var grid = $("#theJqGrid");
    var rowKey = grid.getGridParam("selrow");
    // console.log("rowKey:"+rowKey)
    return rowKey
}

function getSelectedRowSelf() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    // console.log("rowKey:"+rowKey)
    if(!rowKey){
        alert("请选择一条记录");
        return ;
    }

    var selectedIDs = grid.getGridParam("selarrrow");
    // console.log("selectedIDs:"+selectedIDs)
    if(selectedIDs.length > 1){
        alert("只能选择一条记录");
        return ;
    }

    var list = grid.jqGrid('getRowData', selectedIDs[0])
    // console.log("list")
    // console.log(list)
    return list;
}

//选择某个table的一条记录
function getSelectedRowSelfByID(tableID) {
    var grid = $(tableID);
    var rowKey = grid.getGridParam("selrow");
    var list = grid.jqGrid('getRowData', rowKey)
    // console.log("list")
    // console.log(list)
    return list;
}

//选择多条记录
function getSelectedRows() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
    	alert("请选择一条记录");
    	return ;
    }

    return grid.getGridParam("selarrrow");
}

//选择多条记录
function getTheSelectedRows() {
    var grid = $("#theJqGrid");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
        alert("请选择一条记录");
        return ;
    }

    return grid.getGridParam("selarrrow");
}

//判断是否为空
function isBlank(value) {
    return !value || !/\S/.test(value)
}

function isNUll(value) {
    if (value == "" || value == null || value == "null" || value == "0") {
        return true
    } else {
        return false
    }
}

//获取某个时间是当年的第几周, add为周的偏移量
//http://blog.csdn.net/ziwen00/article/details/12579305
function getWeek(date, add) {
    var time,week,checkDate = new Date(date);
    checkDate.setDate(checkDate.getDate() + 4 - (checkDate.getDay() || 7));
    time = checkDate.getTime();
    checkDate.setMonth(0);
    checkDate.setDate(1);
    week=Math.floor(Math.round((time - checkDate) / 86400000) / 7) + 1 + add;
    return date.getFullYear()+'-W'+(week<10?'0':'')+week;
}

//根据2018-W10的周样式获取这周的开始日期和结束日期
function getStartAndEndFromWeek(weekStr) {
    var yearAndWeek = weekStr.split("-")
    var year = yearAndWeek[0]
    var week = yearAndWeek[1].substring(1)
    var startDate = getXDate(year, week, -6)
    var endDate = getXDate(year, week, 0)
    return new Array(startDate, endDate)
}

//获取2018-W10的周样式获取这周所有的时间
function  getAllWeekDate(weekStr) {
    var yearAndWeek = weekStr.split("-")
    var year = yearAndWeek[0]
    var week = yearAndWeek[1].substring(1)
    return new Array(getXDate(year, week, -6), getXDate(year, week, -5), getXDate(year, week, -4), getXDate(year, week, -3), getXDate(year, week, -2), getXDate(year, week, -1), getXDate(year, week, 0))
}

//这个方法将取得某年(year)第几周(weeks)的星期几(weekDay)的日期
//http://blog.csdn.net/xingfuzhijianxia/article/details/38386095
function getXDate(year,weeks,weekDay){
    // 用指定的年构造一个日期对象，并将日期设置成这个年的1月1日
    // 因为计算机中的月份是从0开始的,所以有如下的构造方法
    var date = new Date(year,"0","1");
    // 取得这个日期对象 date 的长整形时间 time
    var time = date.getTime();
    // 将这个长整形时间加上第N周的时间偏移
    // 因为第一周就是当前周,所以有:weeks-1,以此类推
    // 7*24*3600000 是一星期的时间毫秒数,(JS中的日期精确到毫秒)
    time+=(weeks-1)*7*24*3600000;
    // 为日期对象 date 重新设置成时间 time
    date.setTime(time);
    return getNextDate(date,weekDay);
}

// 这个方法将取得 某日期(nowDate) 所在周的星期几(weekDay)的日期
function getNextDate(nowDate,weekDay){
    // 0是星期日,1是星期一,...
    weekDay%=7;
    var day = nowDate.getDay();
    var time = nowDate.getTime();
    var sub = weekDay-day;
    if(sub <= 0){
        sub += 7;
    }
    time+=sub*24*3600000;
    nowDate.setTime(time);
    return nowDate;
}

//获取当前日期和时间
function getNowFormatDateAndTime() {
    var dateAndTime = getNowFormatDate() + " " + getNowForTime()
    log("当前日期和时间", dateAndTime)
    return dateAndTime;
}

//获取当前日期
function getNowFormatDate() {
    return dateToStr(new Date())
}

//获取当前时间
function getNowForTime() {
    return dateToTimeStr(new Date);
}

//日期转时间字符串
function dateToTimeStr(date) {
    var seperator = ":";
    var currentTime = date.getHours() + seperator + date.getMinutes()
        + seperator + date.getSeconds();
    return currentTime
}

//日期转字符串年月日
function dateToStr(date) {
    var seperator = "-";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentDate = date.getFullYear() + seperator + month + seperator + strDate;
    return currentDate;
}

//字符串转日期
function strToDate(str) {
    var arrayDate = str.split("-")
    return new Date(arrayDate[0], arrayDate[1], arrayDate[2])
}

//日志输出
function log(str, value) {
    console.log(str)
    console.log(value)
}

//根据class判断哪些不能为空
function notNullCheckByClass() {
    var hasNotNullMarks = document.getElementsByClassName("notNullMarks")
    var hasNotNullMark = document.getElementsByClassName("notNullMark")
    log("hasNotNullMarks.length", hasNotNullMarks.length)
    log("hasNotNullMark", hasNotNullMark)
    //当拥有notNullMarks的class的时候，才会判断
    if (hasNotNullMarks.length != 0) {
        for (var obj in hasNotNullMark) {
            var divFirst = hasNotNullMark.item(obj).parentNode
            var divNext = $(divFirst).context.nextElementSibling
            var input = $(divNext).context.firstChild
            var value = $(input).context.value
            log("value", value)
            if (isNUll(value)) {
                alert("请将您的信息填写完整", $(input))
                return true
            }
        }
    }
    return false
}