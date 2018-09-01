$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'xsquare/course/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true, hidden: true },
			{ label: '课程名称', name: 'name', index: 'name', width: 100, align: "center" },
			{ label: '签到积分', name: 'signIntegral', index: 'sign_integral', width: 50, align: "center" },
			{ label: '每节课时长', name: 'time', index: 'time', width: 60, align: "center",
                formatter: function(cellValue,options,rowObject) {
                    return cellValue+' 分钟'
                }
			},
			{ label: '最高预约人数', name: 'maxPerson', index: 'max_person', width: 70, align: "center",
                formatter: function(cellValue,options,rowObject) {
                    if (cellValue == null || cellValue == '') {
                        return '无限制'
                    } else {
                        return cellValue+' 人'
					}
                }
			},
			// { label: '可上课会员卡类别，使用“|”分隔', name: 'vipCardType', index: 'vip_card_type', width: 80 },
			// { label: '课程描述', name: 'describe', index: 'describe', width: 80, sortable: false, align: "center" },
			{ label: '累计上课次数', name: 'countClassNumber', index: 'count_class_number', width: 80, align: "center" },
			{ label: '平均上课人次', name: 'avgPersonNumberTime', index: 'avg_person_number_time', width: 80, align: "center" },
			// { label: '累计上课人数', name: 'countPersonNumber', index: 'count_person_number', width: 80, align: "center" },
			{ label: '累计上课人次', name: 'countPersonNumberTime', index: 'count_person_number_time', width: 80, align: "center" },
			{ label: '课程状态', name: 'status', index: 'status', width: 50, align: "center", sortable: false,
                formatter: function(cellValue,options,rowObject) {
                    if (cellValue == 1) {
                        return '<span class="label label-success" style="padding: 6px 8px">正常</span>'
                    }
                    if (cellValue == 0) {
                        return '<span class="label label-danger" style="padding: 6px 8px">删除</span>'
                    }
                }
			}
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
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
            //需要在table加载完成之后，执行的
            vm.completeDo()
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		course: {},
		//用于选择课程可用的会员卡类型
        vipCardType: {},
		query: {
            status: 1,
            courseName: ''
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
        		alert("您还没有添加会员卡类型,无法添加课程,<br>请到\"会员管理\" > \"会员卡类型管理\"添加会员卡类型")
        		return
			}
			vm.showList = false;
			vm.title = "新增";
			vm.course = {};
            //将展示的最后一个会员卡类别的border-bottom宽度设为0px
            var vipCardTypeChoice = $(".vipCardTypeChoice:last")
            vipCardTypeChoice.css("border-width", "0px")
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id, function () {

            	var vipCardTypeStr = vm.course.vipCardType
				var vipCardTypeArray = vipCardTypeStr.split("|")
				log(vipCardTypeArray.length)
                for (var i=0;i < vipCardTypeArray.length; i++) {
                    log("进到外层循环了")
                    var vipCardTypeInfo = vipCardTypeArray[i].split("+")
                    for (var j=0;j < vm.vipCardType.length; j++) {
                    	if (vipCardTypeInfo[0] == vm.vipCardType[j].id) {
                    		log("进到内层循环了")
                            vm.vipCardType[j].initialMoney = vipCardTypeInfo[1]
                            vm.vipCardType[j].initialNumber = vipCardTypeInfo[2]
							if ("true" == vipCardTypeInfo[3]) {
                                vm.vipCardType[j].status = true
							} else {
                                vm.vipCardType[j].status = false
							}
						}
                    }
                }
                log("vm.vipCardType", vm.vipCardType)
            })
		},
		saveOrUpdate: function (event) {
			var courseInfo = {
                course: vm.course,
                vipCardType: vm.vipCardType
			}
			var url = vm.course.id == null ? "xsquare/course/save" : "xsquare/course/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(courseInfo),
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
				    url: baseURL + "xsquare/course/delete",
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
		getInfo: function(id, doFunction){
			$.get(baseURL + "xsquare/course/info/"+id, function(r){
                vm.course = r.course;
                log("vm.course", vm.course)
                doFunction()
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData: {
                    'status' : vm.query.status,
                    'courseName' : vm.query.courseName
                },
                page:page
            }).trigger("reloadGrid");
		},
        //加载table后要加载的信息
        completeDo: function () {
            //加载会员卡类型
            vm.getVipCardTypes()
        },
        //获取所有status=1的会员卡类型
        getVipCardTypes: function () {
            $.get(baseURL + "/xsquare/vipcardtype/list?status=1", function (vipCardType) {
                log("vipCardType.page.list", vipCardType.page.list)
                //selected标签内容
                vm.vipCardType = vipCardType.page.list
				for (var i=0;i < vm.vipCardType.length; i++) {
                    vm.vipCardType[i].status = false
                    vm.vipCardType[i].initialMoney = 0
                    vm.vipCardType[i].initialNumber = 0
				}
            })
        },
	}
});