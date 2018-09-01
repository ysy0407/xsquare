$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'xsquare/vipcard/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true, hidden: true },
            { label: '会员姓名', name: 'vipUser.name', index: 'name', width: 50, align: "center",sortable: false },
            { label: '联系方式', name: 'vipUser.phone', index: 'phone', width: 80, align: "center",sortable: false },
            { label: '会员卡号', name: 'vipCardNum', index: 'vipCardNum', width: 70, align: "center",sortable: false },
            { label: 'vip用户ID', name: 'vipUserId', index: 'vip_user_id', width: 80, align: "center", hidden: true },
			{ label: '会员卡类型', name: 'vipCardType.name', index: 'vip_card_type_name', width: 80, align: "center", sortable: false },
			// { label: '出售价格', name: 'price', index: 'price', width: 80, align: "center" },
			{ label: '积分', name: 'integral', index: 'integral', width: 60, align: "center" },
            //2：余额扣费，3：按次扣费，4：有效期内免费
            {label: '扣费方式', name: 'dictDeductionType.describe', index: 'deduction_type_describe', width: 80, hidden: true },
			{ label: '扣费方式ID', name: 'deductionType', index: 'deduction_type', width: 80, hidden: true, align: "center" },
            {label: '剩余金额/次数', name: '', index: '', width: 70, align: "center", sortable: false,
                formatter: function(cellValue,options,rowObject) {
                    if (rowObject.deductionType == 2) {
                        return rowObject.balanceMoney + " 元"
                    }
                    if (rowObject.deductionType == 3) {
                        return rowObject.balanceNumber + " 次"
                    }
                    return " -- "
                }
            },
			// { label: '剩余金额', name: 'balanceMoney', index: 'balance_money', width: 80, hidden: true, align: "center" },
			{ label: '剩余次数', name: 'balanceNumber', index: 'balance_number', width: 80, hidden: true},
			{ label: '生效日期', name: 'takeEffectDate', index: 'takeEffectDate', width: 80, hidden: true },
			{ label: '有效期至', name: 'effectiveDate', index: 'effective_date', width: 60, align: "center" },
			{ label: '开卡日期', name: 'openDate', index: 'open_date', width: 60, align: "center" },
			//1：正常，2：冻结， 3：过期，4：换卡, 5 删除
			{ label: '会员卡状态', name: 'status', index: 'status', width: 50, align: "center", sortable: false,
                //修改会员卡显示方式
                formatter: function(cellValue,options,rowObject) {
                    if (cellValue == 5) {
                        return '<span class="label label-danger" style="padding: 6px 8px">删除</span>'
                    }
			        if (cellValue == 3) {
                        return '<span class="label label-info" style="padding: 6px 8px">过期</span>'
                    }
                    if (cellValue == 1) {
                        return '<span class="label label-success" style="padding: 6px 8px">正常</span>'
                    }
                    if (cellValue == 2) {
                        return '<span class="label label-default" style="padding: 6px 8px">冻结</span>'
                    }
                    if (cellValue == 4) {
                        return '<span class="label label-warning" style="padding: 6px 8px">换卡</span>'
                    }

                }
			},
            { label: '会员卡操作', width: 90, align: "center", sortable: false,
                formatter: function(cellValue,options,rowObject) {
					var info = '\''+rowObject.vipCardNum+'\',\''+rowObject.vipUser.name+'\',\''+rowObject.vipUserId+
						'\',\''+rowObject.dictDeductionType.describe+'\',\''+rowObject.balanceMoney+
						'\',\''+rowObject.balanceNumber+'\',\''+rowObject.integral+
						'\',\''+rowObject.effectiveDate+'\',\''+rowObject.id+'\',\''+rowObject.deductionType+
                        '\',\''+rowObject.takeEffectDate+'\',\''+rowObject.openDate+'\''
                    if (rowObject.status == 5 || rowObject.status == 4 || rowObject.status == 3) {
                        return '无法操作'
                    }
                    if (rowObject.status == 2) {
					    return '<span class="label label-info pointer" style="position: relative;display: inline-block;padding: 8px 8px;margin: -1px 43px " onclick="handle('+info+',13'+')">解冻</span>'
                    }
					var moreBtn = '<div class="moreDiv label label-default" style="position: absolute;display: none;height: 100px;width: 40px;z-index: 9999;background: black">'+
                        '<span class="label label-info pointer" style="display: block;padding: 2px 4px;margin: 4px -4px 8px -4px" onclick="handle('+info+',16'+')">积分</span>'+
						'<span class="label label-warning pointer" style="display: block;padding: 2px 4px; margin: 4px -4px 8px -4px" onclick="handle('+info+',14'+')">换卡</span>'+
						'<span class="label label-default pointer" style="display: block;padding: 2px 4px; margin: 4px -4px 8px -4px" onclick="handle('+info+',12'+')">冻结</span>'+
						'<span class="label label-danger pointer" style="display: block;padding: 2px 4px; margin: 4px -4px 8px -4px" onclick="handle('+info+',17'+')">退款</span></div>'
					return '<span class="label label-primary pointer" style="padding: 6px 8px" onclick="handle('+info+',11'+')">充值</span>'
							+'<span class="label label-success pointer" style="padding: 6px 8px;margin: 5px" onclick="handle('+info+',10'+')">扣费</span>'+
							'<div class="moreDivfirst" style="display: inline-block;width:40px;height:26px"><span class="label label-default moreBtn pointer" style="padding: 6px 8px;text-align:right;position: relative" onclick="showMoreBtn(this)">更多</span>'+moreBtn+'</div>'

                }}
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
            //监听moreDivShow 阻止冒泡事件
            $(".moreDivfirst").click(function (event) {
				event.stopPropagation()
            })
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
            //需要在table加载完成之后，执行的
            vm.completeDo()
        }
    });
});

//当改变会员卡类型时, flag:1表示新增，2表示换卡
function changeVipCardType(obj, flag) {
    var thisObj = $(obj)
	thisObj.blur()
	//通过被选中的option的序号，选择vipCardType数组中的对象，进行赋值
	var selectedIndex = thisObj.context.selectedIndex
    if (selectedIndex != 0) {
        var selectedVipCardType = vm.vipCardType[selectedIndex-1]
		log("selectedVipCardType", selectedVipCardType)
        if (flag == 1) {
            vm.vipCard.price = selectedVipCardType.price
            vm.vipCard.integral = selectedVipCardType.integral
            vm.vipCard.deductionType = selectedVipCardType.deductionType
            vm.dictDeductionDesc = selectedVipCardType.dictDeductionType.describe
            vm.vipCard.balanceMoney = selectedVipCardType.initialMoney
            vm.vipCard.balanceNumber = selectedVipCardType.initialNumber
            vm.vipCard.takeEffectDate = selectedVipCardType.takeEffectDate
            //将生效时间，初始有效类型和有效时间类型进行转换为 有效期至
            //将生效时间转为时间格式
            var takeEffectDate = strToDate(selectedVipCardType.takeEffectDate)
            //会员卡类型中的初始有效时间和有效时间类型
            var effectiveType = selectedVipCardType.effectiveType
            var initialEffectiveDate = selectedVipCardType.initialEffectiveDate
            if (effectiveType = 1) {
                takeEffectDate.setDate(takeEffectDate.getDate() + (initialEffectiveDate))
            } else if (effectiveType = 2) {
                takeEffectDate.setDate(takeEffectDate.getDate() + (initialEffectiveDate * 7))
            } else if (effectiveType = 3) {
                takeEffectDate.setMonth(takeEffectDate.getMonth() + (initialEffectiveDate))
            }
            vm.vipCard.effectiveDate = dateToStr(takeEffectDate)
            // //更改初始金额，初始次数的显示方式
            changeDeductionTypeValue(vm.vipCard.deductionType, vm.show)
            document.getElementById("needFocusTwo").focus()
            document.getElementById("needFocusOne").focus()
            thisObj.focus()
        } else {
            vm.newVipCard.price = selectedVipCardType.price
            vm.newVipCard.integral = selectedVipCardType.integral
            vm.newVipCard.deductionType = selectedVipCardType.deductionType
            vm.newVipCard.dictDeductionDesc = selectedVipCardType.dictDeductionType.describe
            vm.newVipCard.balanceMoney = selectedVipCardType.initialMoney
            vm.newVipCard.balanceNumber = selectedVipCardType.initialNumber
            vm.newVipCard.takeEffectDate = selectedVipCardType.takeEffectDate
            vm.newVipCard.vipCardTypeId = selectedVipCardType.id
            //将生效时间，初始有效类型和有效时间类型进行转换为 有效期至
            //将生效时间转为时间格式
            var takeEffectDate = strToDate(selectedVipCardType.takeEffectDate)
            //会员卡类型中的初始有效时间和有效时间类型
            var effectiveType = selectedVipCardType.effectiveType
            var initialEffectiveDate = selectedVipCardType.initialEffectiveDate
            if (effectiveType = 1) {
                takeEffectDate.setDate(takeEffectDate.getDate() + (initialEffectiveDate))
            } else if (effectiveType = 2) {
                takeEffectDate.setDate(takeEffectDate.getDate() + (initialEffectiveDate * 7))
            } else if (effectiveType = 3) {
                takeEffectDate.setMonth(takeEffectDate.getMonth() + (initialEffectiveDate))
            }
            vm.newVipCard.effectiveDate = dateToStr(takeEffectDate)
            // //更改初始金额，初始次数的显示方式
            changeDeductionTypeValue(vm.newVipCard.deductionType, vm.newVipCard.show)
            document.getElementById("needFocusThree").focus()
            document.getElementById("needFocusFour").focus()
            thisObj.focus()
        }
        changeTakeEffectDate(flag)
        log("vm.vipCard.deductionType", vm.vipCard.deductionType)
        log("vm.vipCard", vm.vipCard)
	} else {
        if (flag == 1) {
            vm.initVipCardType()
        } else {
            vm.initNewVipCard()
        }
	}
}

//当选择的历史会员改变的时候
function changeHistoryVipUser(obj) {
    var thisObj = $(obj)
    thisObj.blur()
    //通过被选中的option的序号，选择historyVipUser数组中的对象，进行赋值
    var selectedIndex = thisObj.context.selectedIndex
    if (selectedIndex != 0) {
        var selectedHistoryVipUser = vm.historyVipUser[selectedIndex-1]
        log("selectedHistoryVipUser", selectedHistoryVipUser)
		vm.vipUser.name = selectedHistoryVipUser.name
		vm.vipUser.gender = selectedHistoryVipUser.gender
		vm.vipUser.phone = selectedHistoryVipUser.phone
		vm.vipUser.birthday = selectedHistoryVipUser.birthday
		vm.vipUser.note = selectedHistoryVipUser.note
		vm.vipUser.password = selectedHistoryVipUser.password
        log("vm.vipUser", vm.vipUser)
    } else {
        vm.vipUser.name = ''
        vm.initVipUser()
    }
}

//当输入的会员名字变化的时候
function changeVipUserName() {
	log("vm.vipUser.name", vm.vipUser.name)
	//只有当新增会员的时候才执行
	if (vm.show.historyVipUserAll == true) {
        //重新获取历史会员
        vm.getHistoryVipUser()
        //初始化会员信息
        // vm.initVipUser()
	}
}

//当生效时间改变的时候
function changeTakeEffectDate(flag) {
	if (flag == 1) {
        $("#vipCard-effectiveDate").attr('min', vm.vipCard.takeEffectDate)
    } else {
        $("#changeVipCard-effectiveDate").attr('min', vm.newVipCard.takeEffectDate)
    }
	log("$(\"#vipCard-effectiveDate\").attr('min')", $("#vipCard-effectiveDate").attr('min'))
}

//当鼠标进入输入会员姓名的div中时
function vipNameOnMouseEnter() {
    vm.show.historyVipUser = true
}

//当鼠标离开输入会员姓名的div中,且焦点不在会员姓名输入框中时
function vipNameOnMouseLeave() {
    if (!($("#historyVipUser").is(":focus") || $("#vipUser-name").is(":focus"))) {
        vm.show.historyVipUser = false
    }
}

$(document).ready(function(){
    //监听新增和修改会员信息页面鼠标点击事件
    $("#vipCard").click(function () {
        log("监听新增和修改会员信息页面鼠标点击事件")
        if (!($("#historyVipUser").is(":focus") || $("#vipUser-name").is(":focus"))) {
            vm.show.historyVipUser = false
        }
    })

    //监听展示列表的鼠标点击事件
    $("#showList").click(function () {
        log("监听展示列表的鼠标点击事件")
        $(".moreDivShow").css("display", "none")
        $(".moreBtn").css("background", "#777")
    })


})

//点击更多按钮的时候
function showMoreBtn(obj) {
	//先将其他的moreDivShow的class改为moreDiv
    var moreDivShow = $(".moreDivShow")
    for (var i=0;i<moreDivShow.length;i++) {
        $(moreDivShow[i]).removeClass("moreDivShow")
        $(moreDivShow[i]).addClass("moreDiv")
    }
	var thisObj = $(obj)
    // log("thisObj.stopPropagation", thisObj.stopPropagation())
	var nextDiv = $(thisObj.context.nextElementSibling)
    //修改点击span的class
    nextDiv.removeClass("moreDiv")
    //将其他的所有的moreBtn设为不展示
    var moreDiv = $(".moreDiv")
    for (var i=0;i<moreDiv.length;i++) {
        $(moreDiv[i]).css("display", "none")
    }
    //将其他的所有的moreBtn设为不展示
    var moreBtn = $(".moreBtn")
    for (var i=0;i<moreDiv.length;i++) {
        $(moreBtn[i]).css("background", "#777")
    }
    thisObj.css("background", "black")
	//将自己展示
    nextDiv.css("display", "block")
	nextDiv.addClass("moreDivShow")
}

//操作
function handle(vipCardNum, name, vipUserId, dictDeductionDesc, balanceMoney, balanceNumber, integral, effectiveDate, id, deductionType, takeEffectDate, openDate, handleType) {
	//初始化操作信息
    vm.initHandleInfo()
    //更改初始金额，初始次数的显示方式
    changeDeductionTypeValue(deductionType, vm.show)
	//绑定参数
    vm.handle.type = handleType
	vm.vipCard.vipCardNum = vipCardNum
	vm.vipUser.name = name
	vm.vipUser.id = vipUserId
	vm.dictDeductionDesc = dictDeductionDesc
	vm.vipCard.deductionType = deductionType
	vm.vipCard.balanceMoney = balanceMoney
	vm.vipCard.balanceNumber = balanceNumber
	vm.vipCard.integral = integral
	vm.vipCard.effectiveDate = effectiveDate
	vm.vipCard.takeEffectDate = takeEffectDate
	vm.vipCard.openDate = openDate
	vm.vipCard.id = id
	//充值
	if (handleType == 11) {
        recharge()
	}
    //手动扣费
    if (handleType == 10) {
        deduct(deductionType)
    }
    //积分
    if (handleType == 16) {
        integralHandle(deductionType)
    }
    //换卡
    if (handleType == 14) {
        changeHandle(deductionType)
    }
    //冻结
    if (handleType == 12) {
        freeze()
    }
    //退款
    if (handleType == 17) {
        refund(deductionType)
    }
    //解冻
    if (handleType == 13) {
        unfreeze(deductionType)
    }
}

//充值
function recharge() {
	//调整有效期至的最大最小日期
    $("#handle-rechargeDate").attr('min', vm.vipCard.effectiveDate)
    vm.handle.rechargeDate = vm.vipCard.effectiveDate
    vm.show.recharge.all = true
    vm.show.deduct.all = false
    vm.showChangeVipCard = false
    vm.handleTitle = '充值信息'
    layer.open({
        type: 1,
        title: "充值",
        //无效
        area: ['1300', '1300'],
        shade: false,
        content: jQuery("#handleVipCard"),
        btn: ['确认', '取消'],
        yes: function (index) {
            if (isNUll(vm.handle.getMoney)) {
                alert("实收金额尚未输入!");
                return
            }
            vm.handleMethod()
            layer.close(index)
        },
        btn2: function (index) {
            layer.close(index)
        }
    });
}
//扣费
function deduct(deductionType) {
	log("deductionType", deductionType)
	if (deductionType == 4) {
        $("#handle-deductDate-label").empty()
	    $("#handle-deductDate-label").prepend('<span class="notNullMark" style="color: red">*</span> 有效期至')
		$("#handle-deductDate-label").css("width", "24%")
	} else {
        $("#handle-deductDate-label").empty()
        $("#handle-deductDate-label").prepend("有效期至")
        $("#handle-deductDate-label").css("width", "15%")
	}
    //调整有效期至的最大最小日期
    log("vm.vipCard",vm.vipCard)
    $("#handle-deductDate").attr('max', vm.vipCard.effectiveDate)
    $("#handle-deductDate").attr('min', vm.vipCard.openDate)
    vm.handle.deductDate = vm.vipCard.effectiveDate
    vm.show.recharge.all = false
    vm.show.deduct.all = true
    vm.show.deduct.outMoney = false
    vm.show.deduct.integral = false
    vm.show.deduct.deductDate = true
    vm.showChangeVipCard = false
    vm.show.deduct.noChangeVipCard = true
    vm.handleTitle = '扣费信息'
    layer.open({
        type: 1,
        title: "扣费",
        //无效
        area: ['1300', '1300'],
        shade: false,
        content: jQuery("#handleVipCard"),
        btn: ['确认', '取消'],
        yes: function (index) {

            if (deductionType == 4) {
                if (vm.vipCard.effectiveDate == vm.handle.deductDate){
                    alert("有效期至并未减少!")
                    return
                }
            } else if(deductionType == 3) {
                if (isNUll(vm.handle.deductNumber)){
                    alert("扣除次数尚未输入!")
                    return
                }
            } else if(deductionType == 2) {
                if (isNUll(vm.handle.deductMoney)){
                    alert("扣除金额尚未输入!")
                    return
                }
            }

			vm.handleMethod()
            layer.close(index)
        },
        btn2: function (index) {
            layer.close(index)
        }
    });
}
//积分操作
function integralHandle() {
    vm.show.recharge.all = false
    vm.show.deduct.all = true
    vm.show.deduct.outMoney = false
    vm.show.deduct.integral = true
    vm.show.deduct.deductDate = false
    vm.show.deduct.noChangeVipCard = true
    vm.showChangeVipCard = false
    vm.show.initialMoney = false
    vm.show.initialNumber = false
    vm.handleTitle = '积分操作'
    layer.open({
        type: 1,
        title: "积分",
        //无效
        area: ['1300', '1300'],
        shade: false,
        content: jQuery("#handleVipCard"),
        btn: ['确认', '取消'],
        yes: function (index) {
            if (isNUll(vm.handle.integral)) {
                alert("积分尚未输入!")
                return
            }
            vm.handleMethod()
            layer.close(index)
        },
        btn2: function (index) {
            layer.close(index)
        }
    });
}
//冻结
function freeze() {
    var msg = '您确定要冻结该会员卡嘛?'
    confirm(msg, function(){
        vm.handleMethod()
    });
}
//换卡
function changeHandle() {
    vm.show.recharge.all = false
    vm.show.deduct.all = true
    vm.show.deduct.outMoney = false
    vm.show.deduct.noChangeVipCard = false
    vm.showChangeVipCard = true;
    vm.handleTitle = '换卡操作'
    vm.initNewVipCard()
    layer.open({
        type: 1,
        title: "换卡",
        //无效
        area: ['1300', '1300'],
        shade: false,
        content: jQuery("#handleVipCard"),
        btn: ['确认', '取消'],
        yes: function (index) {
            if(isNUll(vm.newVipCard.vipCardTypeId)){
                alert("替换的会员卡类型尚未选择!")
                return
            }
            if(isNUll(vm.newVipCard.price)){
                alert("缴纳金额尚未输入!")
                return
            }
            if(isNUll(vm.newVipCard.takeEffectDate)){
                alert("生效时间尚未输入!")
                return
            }
            if(isNUll(vm.newVipCard.effectiveDate)){
                alert("有效期至尚未输入!")
                return
            }
            vm.handleMethod()
            layer.close(index)
        },
        btn2: function (index) {
            layer.close(index)
        }
    });
}
//退款
function refund(deductionType) {
    if (deductionType == 4) {
        $("#handle-deductDate-label").empty()
        $("#handle-deductDate-label").prepend('<span class="notNullMark" style="color: red">*</span> 有效期至')
        $("#handle-deductDate-label").css("width", "24%")
    } else {
        $("#handle-deductDate-label").empty()
        $("#handle-deductDate-label").prepend("有效期至")
        $("#handle-deductDate-label").css("width", "15%")
    }
    //调整有效期至的最大最小日期
    $("#handle-deductDate").attr('max', vm.vipCard.effectiveDate)
    $("#handle-deductDate").attr('min', vm.vipCard.openDate)
    vm.handle.deductDate = vm.vipCard.effectiveDate
    vm.show.recharge.all = false
    vm.show.deduct.all = true
    vm.show.deduct.outMoney = true
    vm.show.deduct.noChangeVipCard = true
    vm.show.deduct.integral = false
    vm.show.deduct.deductDate = true
    vm.showChangeVipCard = false;
    vm.handleTitle = '退款操作'
    layer.open({
        type: 1,
        title: "退款",
        //无效
        area: ['1300', '1300'],
        shade: false,
        content: jQuery("#handleVipCard"),
        btn: ['确认', '取消'],
        yes: function (index) {
            if (isNUll(vm.handle.outMoney)){
                alert("退款金额 尚未输入!")
                return
            }
            if (deductionType == 4) {
                if (vm.vipCard.effectiveDate == vm.handle.deductDate){
                    alert("有效期至并未减少!")
                    return
                }
            } else if(deductionType == 3) {
                if (isNUll(vm.handle.deductNumber)){
                    alert("扣除次数尚未输入!")
                    return
                }
            } else if(deductionType == 2) {
                if (isNUll(vm.handle.deductMoney)){
                    alert("扣除金额尚未输入!")
                    return
                }
            }
            vm.handleMethod()
            layer.close(index)
        },
        btn2: function (index) {
            layer.close(index)
        }
    });
}
//解冻
function unfreeze() {
    var msg = '您确定要解冻该会员卡嘛?'
    confirm(msg, function(){
        vm.handleMethod()
    });
}

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
        showVipUser: true,
        showVipCard: true,
        showChangeVipCard: false,
		title: null,
		//会员卡处理的标题
		handleTitle: null,
		vipUser: {},
		vipCard: {
            vipCardTypeId: '',
			price: '',
			integral: '',
			deductionType: '',
			balanceMoney: '',
			balanceNumber: '',
			takeEffectDate: '',
			effectiveDate: ''
		},
        //更换会员卡时使用
        newVipCard: {
		    show: {
                initialMoney: true,
                initialNumber: false
            }
        },
		//历史会员
		historyVipUser: {},
        dictDeductionDesc: '- - 未选择 - -',
        vipCardType: {},
        show: {
            //金额初始化时，展示
            initialMoney: true,
            //次数初始化时，不展示
            initialNumber: false,
			//历史会员select显示
            historyVipUser: false,
			//修改的时候直接不显示
            historyVipUserAll: true,
			//充值控制
            recharge: {
            	all: false,
            	//积分
                integral: false,
                balanceNumber: true
			},
			//退款控制
			deduct: {
				all: false,
				outMoney: false,
                integral: false,
                deductDate: true,
                noChangeVipCard: true
			}
        },
		query: {
			userNameOrCardId: '',
            vipCardType: '',
            vipCardStatus: 1
		},
		//处理请求存放的数据
		handle: {
			//操作类型
			type: '',
			//实收金额
			getMoney: 0,
			//充值金额
            rechargeMoney: 0,
			//充值次数
            rechargeNumber: 0,
			//赠送积分
            rechargeIntegral: 0,
			//增加至的有效期
            rechargeDate: '',
			//退款金额
			outMoney: 0,
			//扣除金额
            deductMoney: 0,
			//扣除次数
            deductNumber: 0,
            //扣除时间
            deductDate: '',
			//积分
            integral: null,
			//备注
			note: ''
		}
	},
	methods: {
        find: function () {
            vm.reload();
        },
		query: function () {
			vm.reload();
		},
		add: function(){
            if(vm.vipCardType.length == 0){
                alert("您还没有添加会员卡类型,无法添加会员,<br>请到\"会员管理\" > \"会员卡类型管理\"添加会员卡类型")
                return
            }
			vm.showList = false;
			vm.showVipUser = false;
			vm.showVipCard = false;
			vm.title = "新增会员信息";
			vm.vipCard = {};
			vm.initialMoney = true
			vm.initialNumber = false
            //初始化会员信息
            vm.initVipUser()
            vm.initVipCardType()
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			//只修改会员信息，会员卡信息不显示
			vm.showList = false
			vm.showVipUser = false
			vm.showVipCard = true
			//选择历史会员的不显示
			vm.show.historyVipUserAll = false
            vm.title = "修改会员";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			var url = '';
            if (vm.vipCard.id == null) {
                if (vm.validator()) {
                    return
                }
            	vm.handle.type = 6
				url = "xsquare/vipcard/save"
			} else {
                if (isNUll(vm.vipUser.name)) {
                    alert("会员姓名尚未输入!");
                    return
                }
                if (isNUll(vm.vipUser.phone)) {
                    alert("联系方式尚未输入!");
                    return
                }
                if (isNUll(vm.vipUser.password)) {
                    alert("会员密码尚未输入!");
                    return
                }
                vm.handle.type = 15
                url = "xsquare/vipuser/update"
			}
            var cardAndUser = {
                	handle: vm.handle,
                    vipCard: vm.vipCard,
                    vipUser: vm.vipUser
                }
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(cardAndUser),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "xsquare/vipcard/delete",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(id){
			$.get(baseURL + "xsquare/vipcard/info/"+id, function(r){
                vm.vipCard = r.vipCard;
                vm.vipUser = r.vipCard.vipUser
                vm.dictDeductionDesc = r.vipCard.dictDeductionType.describe
                changeDeductionTypeValue(vm.vipCard.deductionType, vm.show)
				log("vm.vipCard getInfo", vm.vipCard)
            });
		},
		reload: function (event) {
			vm.showList = true;
            vm.showVipUser = true
            vm.showVipCard= true
			//初始化会员名称，以重新加载历史会员
            vm.vipUser.name = ''
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData: {
                    query: JSON.stringify(vm.query)
                },
                page:page,

            }).trigger("reloadGrid");
		},
		//操作
        handleMethod: function () {
            var url = '/xsquare/vipcard/handle'
            var rechargeInfo = {
                handle: vm.handle,
                vipCard: vm.vipCard,
                vipUser: vm.vipUser,
                newVipCard: vm.newVipCard
            }
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(rechargeInfo),
                success: function(r){
                    if(r.code === 0){
                        alert('操作成功', function(index){
                            vm.reload();
                        });
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
		//加载table后要加载的信息
        completeDo: function () {
            //加载会员卡类型
            vm.getVipCardTypes()
			//加载所有历史会员
            vm.getHistoryVipUser()
        },
		//获取所有status=1的会员卡类型
		getVipCardTypes: function () {
            $.get(baseURL + "/xsquare/vipcardtype/list?status=1", function (vipCardType) {
                log("vipCardType.page.list", vipCardType.page.list)
                //selected标签内容
                vm.vipCardType = vipCardType.page.list
				log("vm.vipCardType",vm.vipCardType)
            })
        },
		//初始化会员卡类型信息
		initVipCardType: function () {
            vm.vipCard.vipCardTypeId = ''
            vm.vipCard.price = ''
            vm.vipCard.integral = ''
            vm.vipCard.deductionType = ''
            vm.dictDeductionDesc = '- - 未选择 - -'
            vm.vipCard.balanceMoney = ''
            vm.vipCard.balanceNumber = ''
            vm.vipCard.takeEffectDate = ''
            vm.vipCard.effectiveDate = ''
        },
        initNewVipCard: function () {
            vm.newVipCard.vipCardTypeId = ''
            vm.newVipCard.price = ''
            vm.newVipCard.integral = ''
            vm.newVipCard.deductionType = ''
            vm.newVipCard.dictDeductionDesc = '- - 未选择 - -'
            vm.newVipCard.balanceMoney = ''
            vm.newVipCard.balanceNumber = ''
            vm.newVipCard.takeEffectDate = ''
            vm.newVipCard.effectiveDate = ''
            vm.newVipCard.show.initialMoney = true
            vm.newVipCard.show.initialNumber = false
        },
        //获取所有历史会员,并根据会员名称模糊查询
        getHistoryVipUser: function () {
            $.get(baseURL + "/xsquare/vipuser/list?page=1&limit=5&order=asc&name="+vm.vipUser.name, function (vipUser) {
                log("vipUser.page.list", vipUser.page.list)
                //selected标签内容
                vm.historyVipUser = vipUser.page.list
                log("vm.historyVipUser",vm.historyVipUser)
            })
        },
		//初始化会员信息
		initVipUser: function () {
			vm.vipUser.id = ''
			vm.vipUser.name  = ''
			vm.vipUser.gender = 1
			vm.vipUser.password = 123456
			vm.vipUser.phone = ''
			vm.vipUser.birthday = ''
			vm.vipUser.note = ''
        },
		//初始化操作信息
		initHandleInfo: function () {
            vm.handle = {
                //操作类型
                type: '',
                    //实收金额
                    getMoney: 0,
                    //充值金额
                    rechargeMoney: 0,
                    //充值次数
                    rechargeNumber: 0,
                    //赠送积分
                    rechargeIntegral: 0,
                    //增加至的有效期
                    rechargeDate: '',
                    //退款金额
                    outMoney: 0,
                    //扣除金额
                    deductMoney: 0,
                    //扣除次数
                	deductNumber: 0,
                    //扣除时间
                    deductDate: '',
                    //积分
                    integral: null,
                    //备注
                    note: ''
            }
        },
		//格式验证
        validator: function () {
			//通过span的class的为空判断
            // if(notNullCheckByClass()){
            	// return true
			// }
            if (isNUll(vm.vipUser.name)) {
                alert("会员姓名尚未输入!");
                return true
            }
            if (isNUll(vm.vipUser.phone)) {
                alert("联系方式尚未输入!");
                return true
            }
            if (isNUll(vm.vipUser.password)) {
                alert("会员密码尚未输入!");
                return true
            }
            if (isNUll(vm.vipCard.vipCardNum)) {
                alert("会员卡号尚未输入!");
                return true
            }
            if (isNUll(vm.vipCard.vipCardTypeId)) {
                alert("会员卡类型尚未选择!");
                return true
            }
            if (isNUll(vm.vipCard.takeEffectDate)) {
                alert("会员卡生效时间尚未选择!");
                return true
            }
            if (isNUll(vm.vipCard.effectiveDate)) {
                alert("会员卡有效期至尚未选择!");
                return true
            }
			//生效时间应当小于有效期至的时间
			if(strToDate(vm.vipCard.takeEffectDate) > strToDate(vm.vipCard.effectiveDate)){
            	alert("生效时间应当小于有效期至的时间", $("#vipCard-effectiveDate"))
				return true
			}
        }
	}
});