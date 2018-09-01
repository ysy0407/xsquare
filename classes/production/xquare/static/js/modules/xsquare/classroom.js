$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'xsquare/classroom/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true, hidden: true },
			{ label: '教室名称', name: 'name', index: 'name', width: 80, align: "center", sortable: false },
			//1：使用中，0：删除
			{ label: '教室状态', name: 'status', index: 'status', width: 30, align: "center", sortable: false,
                //修改会员卡显示方式
                formatter: function(cellValue,options,rowObject) {
                    if (cellValue == 1) {
                        return '<span class="label label-success" style="padding: 6px 8px">使用中</span>'
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
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		classroom: {},
		query: {
			status: 1
		}
	},
	methods: {
        find: function () {
            $.ajax({
                type: "GET",
                url: baseURL + "getImg/main-logo.png",
                // contentType: "application/json",
                // data: JSON.stringify(vm.classroom),
                success: function(r){
                	log("r",r)
                    // if(r.code === 0){
                    //     // alert('操作成功', function(index){
                    //     //     vm.reload();
                    //     // });
                    // }else{
                    //     // alert(r.msg);
                    // }
                }
            });
            vm.reload();
        },
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.classroom = {};
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
			var url = vm.classroom.id == null ? "xsquare/classroom/save" : "xsquare/classroom/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.classroom),
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
				    url: baseURL + "xsquare/classroom/delete",
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
			$.get(baseURL + "xsquare/classroom/info/"+id, function(r){
                vm.classroom = r.classroom;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData: {
					'status' : vm.query.status
				},
                page:page
            }).trigger("reloadGrid");
		}
	}
});