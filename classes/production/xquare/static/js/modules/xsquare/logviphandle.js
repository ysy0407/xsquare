$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'xsquare/logviphandle/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true, hidden: true },
			{ label: '会员卡号', name: 'vipCardNum', index: 'vip_card_num', width: 40, align: "center", sortable: false,
                formatter: function(cellValue,options,rowObject) {
                    return '<span style="font-weight: 700">'+cellValue+'</span>'
                } },
			{ label: '会员卡操作类型', name: 'handleTypeDict.describe', index: 'handle_type', width: 30, align: "center", sortable: false },
			{ label: '操作时间', name: 'handleTime', index: 'handle_time', width: 40, align: "center", sortable: true },
			{ label: '操作人', name: 'handlePerson', index: 'handle_person', width: 20, align: "center", sortable: false },
			{ label: '描述', name: 'describe', index: 'describe', width: 100, sortable: false }
        ],
		viewrecords: true,
        height: 600,
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
        	//加载完表格需要执行的操作
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
		logVipHandle: {},
		//操作类型
		handleType: null,
		query: {
            vipCardNum: '',
            handleType: '',
            handlePerson: ''
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
			vm.logVipHandle = {};
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
			var url = vm.logVipHandle.id == null ? "xsquare/logviphandle/save" : "xsquare/logviphandle/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.logVipHandle),
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
				    url: baseURL + "xsquare/logviphandle/delete",
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
			$.get(baseURL + "xsquare/logviphandle/info/"+id, function(r){
                vm.logVipHandle = r.logVipHandle;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData: {
                    'vipCardNum': vm.query.vipCardNum,
                    'handleType': vm.query.handleType,
                    'handlePerson': vm.query.handlePerson
                },
                page:page
            }).trigger("reloadGrid");
		},
        completeDo: function () {
            //加载操作类型类型
            vm.getHandleTypeDict()
			//加载系统用户列表
			vm.getUserList()
        },
        //获取操作类型
        getHandleTypeDict: function () {
            $.get(baseURL + "/xsquare/dict/list?pType=5", function (dicts) {
                log("dicts.page.list", dicts.page.list)
                //selected标签内容
                vm.handleType = dicts.page.list
            })
        },
        getUserList: function () {
            $.get(baseURL + "/sys/user/list", function (userList) {
                log("userList.page.list", userList.page.list)
                //selected标签内容
                vm.userList = userList.page.list
            })
        }
	}
});