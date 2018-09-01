$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'xsquare/classtable/theList',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true, hidden: true },
			{ label: '课程名称', name: 'courseEntity.name', index: 'vip_card_id', width: 80, align: "center", sortable: false },
			{ label: '上课老师', name: 'teacherEntity.name', index: 'sign_time', width: 80, align: "center", sortable: false },
			{ label: '上课教室', name: 'classroomEntity.name', index: 'sign_time', width: 80, align: "center", sortable: false },
			{ label: '上课时间', name: '', index: 'sign_time', width: 80, align: "center", sortable: false,
                //将上课时间进行拼接
                formatter: function (cellValue, options, rowObject) {
                    return rowObject.date+' '+rowObject.startHour+':'+rowObject.startMinute
                }
			},
			{ label: '签到人数', name: 'signNum', index: 'sign_time', width: 80, align: "center", sortable: false,
                //将最大上课人数和当前签到人数拼接
                formatter: function (cellValue, options, rowObject) {
					var maxPerson = rowObject.courseEntity.maxPerson
					if (maxPerson == null) {
                        maxPerson = '无限制'
					}
                    return maxPerson+'/'+cellValue
                }  },
			{ label: '上课内容', name: 'describe', index: 'sign_time', width: 80 },
			{ label: '操作', name: '', index: 'handle', width: 80, align: "center", sortable: false,
                formatter: function (cellValue, options, rowObject) {
                    return '<span class="label label-info pointer" style="display: inline-block;padding: 8px 8px;" ' +
						'	onclick="addSign('+rowObject.id+', \''+rowObject.courseEntity.name+'\', \''+rowObject.date+' '+rowObject.startHour+':'+rowObject.startMinute+'\', \''+rowObject.courseEntity.maxPerson+'\', \''+rowObject.signNum+'\')">签到</span>'+
							'<span class="label label-primary pointer" style="display: inline-block;padding: 8px 8px;margin-left: 15px" onclick="signList('+rowObject.id+')">签到名单</span>'
                }
			}
        ],
		viewrecords: true,
        height: 500,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: false,
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
            //加载table后要加载的信息
            vm.completeDo()
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

// 展示某一节课的签到日志
$(function () {
    $("#theJqGrid").jqGrid({
        url: baseURL + 'xsquare/logsign/list',
        datatype: "json",
        colModel: [
            { label: 'id', name: 'id', index: 'id', width: 50, key: true, hidden: true },
            { label: '会员姓名', name: 'vipCardEntity.vipUser.name', index: '', width: 80, align: "center", sortable: false},
            { label: '联系方式', name: 'vipCardEntity.vipUser.phone', index: '', width: 120, align: "center", sortable: false},
            { label: '会员卡号', name: 'vipCardEntity.vipCardNum', index: '', width: 120, align: "center", sortable: false},
            { label: '会员类别', name: 'vipCardEntity.vipCardType.name', index: '', width: 120, align: "center", sortable: false},
            { label: '签到时间', name: 'signTime', index: 'sign_time', width: 150, align: "center", sortable: true},
            { label: '操作人员', name: 'signHandlePerson', index: '', width: 80, align: "center", sortable: false},
            { label: '操作', name: '', index: 'handle', width: 100, align: "center", sortable: false,
                formatter: function (cellValue, options, rowObject) {
                    return '<span class="label label-danger pointer" style="display: inline-block;padding: 8px 8px;" onclick="backsign('+rowObject.id+', \''+rowObject.classtableId+'\', \''+rowObject.vipCardId+'\''+')">撤销签到</span>'
                }
            }
        ],
        viewrecords: true,
        height: 450,
        rowNum: 10,
        rowList: [10,30,50],
        sortable: true,
        //行号
        rownumbers: true,
        //行号的宽度
        rownumWidth: 40,
        autowidth: true,
        //不要多选
        multiselect: false,
        pager: "#TheJqGridPager",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#theJqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });
});


//为当前课程添加签到
function addSign(classtableId, courseName, courseTime, maxPerson, signNum) {
    //最大人数 小于等于 签到人数 说明已经到达签到上线，询问是否继续签到
    if (maxPerson <= signNum) {
        layer.open({
            type: 1,
            title: '继续签到确认',
            //无效
            area: ['1300', '1300'],
            shade: false,
            content: jQuery("#continueSign"),
            btn: ['确认', '取消'],
            yes: function (index) {
                layer.close(index)
                vm.sign(classtableId, courseName, courseTime)
            },
            btn2: function (index) {
                layer.close(index)
            }
        });
    }else {
        vm.sign(classtableId, courseName, courseTime)
    }
}
//展示签到当前课程的签到列表
function signList(classtableId) {
    vm.show.theList = true
    //重新加载表格
    vm.reloadTheList(classtableId)
    layer.open({
        type: 1,
        title: vm.theListTitle,
        //无效
        area: ['1300', '1300'],
        shade: false,
        content: jQuery("#theList"),
        btn: ['取消'],
        yes: function (index) {
            layer.close(index)
        }
    });
}

//撤销签到
function backsign(logSignId, classtableId, vipCardId) {
    vm.del(logSignId, classtableId, vipCardId)
}

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		logSign: {},
		//用于展示输入会员卡号后,会员卡的信息
        vipCard: {
            vipUser: {}
		},
		show: {
            addSign: false,
            continueSign: false,
            theList: false

		},
        //所有的课程
        allCourses: {},
        //条件查询
        query: {
		    course: '',
            startDate: getNowFormatDate(),
            endDate: getNowFormatDate()
        }
	},
	methods: {
		find: function () {
			vm.reload();
		},
		query: function () {
			vm.reload();
		},
        //签到
		sign: function(classtableId, courseName, courseTime){
		    //清除信息
            vm.logSign = {}
		    vm.vipCard = {
                vipUser: {}
            },
            vm.show.addSign = true
            vm.logSign.courseName = courseName
            vm.logSign.courseTime = courseTime
            vm.logSign.classtableId = classtableId
            layer.open({
                type: 1,
                title: '签到 (<span style="color: red">*</span> 为必填项)',
                //无效
                area: ['1300', '1300'],
                shade: false,
                content: jQuery("#addSign"),
                btn: ['确认', '取消'],
                yes: function (index) {
                    if (isNUll(vm.logSign.vipCardId)) {
                        alert("您输入的会员卡号不存在或状态异常")
                        return
                    }
                    vm.save()
                    layer.close(index)
                },
                btn2: function (index) {
                    layer.close(index)
                }
            });
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		save: function (event) {
			var url = "xsquare/logsign/save";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.logSign),
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
		del: function (logSignId, classtableId, vipCardId) {
			confirm('确定要撤销签到？', function(){
			    var delParam = {
			        logSignId: logSignId,
                    classtableId: classtableId,
                    vipCardId: vipCardId
			    }
				$.ajax({
					type: "POST",
				    url: baseURL + "xsquare/logsign/delete",
                    contentType: "application/json",
				    data: JSON.stringify(delParam),
				    // data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
                            alert('操作成功', function(index){
								vm.reload()
								vm.reloadTheList()
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(id){
			$.get(baseURL + "xsquare/logsign/info/"+id, function(r){
                vm.logSign = r.logSign;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData: {
                    course: vm.query.course,
                    startDate: JSON.stringify(vm.query.startDate),
                    endDate: JSON.stringify(vm.query.endDate)
                },
                page:page
            }).trigger("reloadGrid");
		},
        //重新加载展示某一节课的签到日志
        reloadTheList: function (classtableId) {
            var page = $("#theJqGrid").jqGrid('getGridParam', 'page');
            //表格提交数据未更新，先清空下提交的数据
            $("#theJqGrid").jqGrid('clearGridData')
            $("#theJqGrid").jqGrid('setGridParam', {
                postData: {
                    classtableId: classtableId
                },
                page: page
            }).trigger("reloadGrid");
        },
        //当输入的会员卡号改变的时候,将下面展示会员卡信息进行及时的展示
        changeVipCardNum: function () {
			if (vm.logSign.vipCardNum != null  || vm.logSign.vipCardNum != '') {
				log(vm.logSign.vipCardNum)
                $.get(baseURL + "xsquare/vipcard/list?vipCardNum="+vm.logSign.vipCardNum, function(r){
                    if (r.page.list.length == 0) {
                        alert("您输入的会员卡号不存在或状态异常")
                        vm.vipCard = {
                            vipUser: {}
						}
                        return
                    } else {
                        vm.vipCard = r.page.list[0];
                    }
                    vm.logSign.vipCardId = vm.vipCard.id
                });
			}
        },
        //加载table后要加载的信息
        completeDo: function () {
		    //初始化时修改时间限制
            vm.queryDateChange()
            //加载所有的课程
            vm.getCourses()
        },
        //获取所有的课程
        getCourses: function () {
            $.get(baseURL + "/xsquare/course/list?status=", function (courses) {
                vm.allCourses = courses.page.list
            })
        },
        //查询条件中开始和结束的日期变化
        queryDateChange: function () {
		    //开始日期最大为结束的日期
            $("#query-startDate").attr('max', vm.query.endDate)
		    //结束日期最小为开始的日期
            $("#query-endDate").attr('min', vm.query.startDate)
            //如果结束时间小于开始时间,将开始时间改变为结束时间
            if (vm.query.endDate < vm.query.startDate) {
                vm.query.startDate = vm.query.endDate
            }
        },
        //快捷查询本周课程表
        queryNowWeek: function () {
		    //获取并设置为本周的日期
            var date = getStartAndEndFromWeek(getWeek(new Date(), 0))
            vm.query.startDate = dateToStr(date[0])
            vm.query.endDate = dateToStr(date[1])
            vm.find()
        }
	}
});