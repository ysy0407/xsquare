$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'xsquare/teacher/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true, hidden: true },
			{ label: '老师姓名', name: 'name', index: 'name', width: 50, align: "center" },
			{ label: '老师手机号', name: 'phone', index: 'phone', width: 80, align: "center" },
			{ label: '入职日期', name: 'entryDate', index: 'entry_date', width: 60, align: "center" },
			{ label: '离职日期', name: 'leaveDate', index: 'leave_date', width: 60, align: "center", sortable: false,
                //修改会员卡显示方式
                formatter: function(cellValue,options,rowObject) {
                    if (cellValue == null || cellValue == '') {
                        return '仍在工作中'
                    }
                    return cellValue
                } },
			// { label: '当前工资', name: 'currentSalary', index: 'current_salary', width: 80 }
			{ label: '就职状态', name: 'status', index: 'status', width: 40, align: "center", sortable: false,
                //修改会员卡显示方式
                formatter: function(cellValue,options,rowObject) {
                    if (cellValue == 1) {
                        return '<span class="label label-success" style="padding: 6px 8px">就职中</span>'
                    }
                    if (cellValue == 0) {
                        return '<span class="label label-danger" style="padding: 6px 8px">已离职</span>'
                    }
                } },
			{ label: '备注', name: 'note', index: 'note', width: 100 }
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
		teacher: {},
		query: {
			status: 1,
            teacherNameOrPhone: ''
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
			vm.showList = false;
			vm.title = "新增";
			vm.teacher = {};
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
			var url = vm.teacher.id == null ? "xsquare/teacher/save" : "xsquare/teacher/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.teacher),
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
			
			confirm('确定要令该老师离职？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "xsquare/teacher/delete",
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
			$.get(baseURL + "xsquare/teacher/info/"+id, function(r){
                vm.teacher = r.teacher;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData: {
                    'status' : vm.query.status,
                    'teacherNameOrPhone' : vm.query.teacherNameOrPhone
                },
                page:page
            }).trigger("reloadGrid");
		}
	}
});