$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'xsquare/logmoney/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true, hidden: true  },
            { label: '具体事项', name: 'describe', index: 'describe', width: 80, align: "center", sortable: false },
			{ label: '金额', name: 'moneyNumber', index: 'money_number', width: 80, align: "center", sortable: true,
                //修改金额类型显示方式
                formatter: function(cellValue,options,rowObject) {
					// return Math.abs(cellValue)
					return cellValue
                }
			},
            { label: '财务类型', name: 'moneyType', index: 'money_type', width: 80, align: "center", sortable: false,
                //修改金额类型显示方式
                formatter: function(cellValue,options,rowObject) {
                    if (cellValue == 1) {
                        return '<span class="label label-success" style="padding: 6px 8px">收入</span>'
                    }
                    if (cellValue == 0) {
                        return '<span class="label label-danger" style="padding: 6px 8px">支出</span>'
                    }
                }
            },
			{ label: '操作时间', name: 'handleTime', index: 'handle_time', width: 80, align: "center", sortable: true },
			{ label: '操作人', name: 'handlePerson', index: 'handle_person', width: 80, align: "center", sortable: false },
			{ label: '备注', name: 'note', index: 'note', width: 80, hidden: true }
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
        	//加载完table后要执行的
            vm.completeDo()
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		logMoney: {},
		//查询条件
		query: {
            handlePerson: '',
            moneyType: ''
		},
		userList: null
	},
	methods: {
		find: function () {
			vm.reload();
		},
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.logMoney = {};
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
		saveOrUpdate: function (event) {
			var url = vm.logMoney.id == null ? "xsquare/logmoney/save" : "xsquare/logmoney/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.logMoney),
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
				    url: baseURL + "xsquare/logmoney/delete",
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
			$.get(baseURL + "xsquare/logmoney/info/"+id, function(r){
                vm.logMoney = r.logMoney;
            });
		},
        completeDo: function () {
            //加载管理员信息
            vm.getUserList()
        },
		getUserList: function () {
            $.get(baseURL + "/sys/user/list", function (userList) {
                log("userList.page.list", userList.page.list)
                //selected标签内容
                vm.userList = userList.page.list
            })
        },
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData: {
                    'handlePerson': vm.query.handlePerson,
                    'moneyType': vm.query.moneyType
                },
                page:page
            }).trigger("reloadGrid");
		}
	}
});