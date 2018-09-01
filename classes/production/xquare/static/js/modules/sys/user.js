$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/user/list',
        datatype: "json",
        colModel: [			
			{ label: '用户ID', name: 'userId', index: "user_id", width: 45, key: true, align: "center" },
			{ label: '用户名', name: 'username', width: 75, align: "center"},
            { label: '姓名', name: 'name', width: 75, align: "center" },
            { label: '角色', name: 'roleName', width: 75, align: "center" },
            { label: '该人员管理的用户ID', name: 'managerID', width: 75, align: "center", formatter: function (value, options, row) {
                //为99999说明,为普通成员
				if (value == "99999") {
					return "暂无"
				} else {
					return value
				}
            } },
			{ label: '邮箱', name: 'email', width: 90, align: "center" },
			{ label: '手机号', name: 'mobile', width: 100, align: "center" },
			{ label: '状态', name: 'status', width: 80, align: "center", formatter: function(value, options, row){
				return value === 0 ? 
					'<span class="label label-danger">'+"禁用"+'</span>' :
					'<span class="label label-success">'+"正常"+'</span>';
			}},
			{ label: '创建时间', name: 'createTime', index: "create_time", width: 80, align: "center" }
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

function say (value) {
	console.log(value);
    // $("#custinfo").css("display", "block");
    layer.open({
        type: 1,
        // skin: 'layui-layer-molv',
        title: value,
        area: ['550px', '270px'],
        // shadeClose: true,
        shade: false,
        content: jQuery("#custinfo"),
        btn: ['确认']
    });
}

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			username: null
		},
		showList: true,
		title:null,
		roleList:{},
		user:{
			name: null,
			status:1,
			roleIdList:[]
		}
	},
	methods: {
        say: function () {
            alert("111111");
        },
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.roleList = {};
			vm.user = {status:1,roleIdList:[]};
			
			//获取角色信息
			this.getRoleList();
		},
		update: function () {
			var userId = getSelectedRow();
			if(userId == null){
				return ;
			}
			
			vm.showList = false;
            vm.title = "修改";
			
			vm.getUser(userId);
			//获取角色信息
			this.getRoleList();
		},
		del: function () {
			var userIds = getSelectedRows();
			if(userIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "sys/user/delete",
                    contentType: "application/json",
				    data: JSON.stringify(userIds),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(){
                                vm.reload();
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		saveOrUpdate: function () {
            if(vm.validator()){
                return ;
            }

			var url = vm.user.userId == null ? "sys/user/save" : "sys/user/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.user),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		getUser: function(userId){
			$.get(baseURL + "sys/user/info/"+userId, function(r){
				vm.user = r.user;
				vm.user.password = null;
			});
		},
		getRoleList: function(){
			$.get(baseURL + "sys/role/select", function(r){
				vm.roleList = r.list;
			});
		},
		reload: function () {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'username': vm.q.username},
                page:page
            }).trigger("reloadGrid");
		},
        validator: function () {
            if(isBlank(vm.user.username)){
                alert("用户名不能为空");
                return true;
            }

            if(vm.user.userId == null && isBlank(vm.user.password)){
                alert("密码不能为空");
                return true;
            }

            if(vm.user.name == null && isBlank(vm.user.name)){
                alert("姓名不能为空");
                return true;
            }
            // if(isBlank(vm.user.email)){
             //    alert("邮箱不能为空");
             //    return true;
            // }
            //
            // if(!validator.isEmail(vm.user.email)){
            //     alert("邮箱格式不正确");
            //     return true;
            // }
        }
	}
});