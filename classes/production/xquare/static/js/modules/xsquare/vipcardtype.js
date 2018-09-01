$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'xsquare/vipcardtype/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 50, key: true, hidden: true},
            {label: '会员卡类型名称', name: 'name', index: 'name', width: 100, align: "center"},
            {label: '售价', name: 'price', index: 'price', width: 60 ,align: "center"},
            {label: '赠送积分', name: 'integral', index: 'integral', width: 60, align: "center"},
            //2：余额扣费，3：按次扣费，4：有效期内免费
            {label: '扣费方式', name: 'dictDeductionType.describe', index: 'deduction_type_describe', width: 80, align: "center", sortable: false},
            {label: '扣费方式ID', name: 'deductionType', index: 'deduction_type', width: 80, hidden: true, align: "center"},
            {label: '初始金额/次数', name: '', index: '', width: 80, align: "center", sortable: false,
                formatter: function(cellValue,options,rowObject) {
                    if (rowObject.deductionType == 2) {
                        return rowObject.initialMoney + " 元"
                    }
                    if (rowObject.deductionType == 3) {
                        return rowObject.initialNumber + " 次"
                    }
                    return " -- "
                }
            },
            {label: '初始金额', name: 'initialMoney', index: 'initial_money', width: 80, hidden: true, align: "center"},
            {label: '初始次数', name: 'initialNumber', index: 'initial_number', width: 80, hidden: true, align: "center"},
            {
                label: '初始有效时间',
                name: 'initialEffectiveDate',
                index: 'initial_effective_date',
                width: 70,
                align: "center",
                //判断初始有效时间单位
                formatter: function(cellValue,options,rowObject) {
                    var effectiveType = ""
                    if (rowObject.effectiveType == 1) {
                        effectiveType = " 天"
                    }
                    if (rowObject.effectiveType == 2) {
                        effectiveType = " 周"
                    }
                    if (rowObject.effectiveType == 3) {
                        effectiveType = " 月"
                    }
                    return cellValue+effectiveType
                }
            },
            //1：天，2：周，3：月
            {label: '有效期类型', name: 'effectiveType', index: 'effective_type', width: 80, hidden: true},
            {label: '初始生效时间', name: 'takeEffectDate', index: 'take_effect_date', width: 80, align: "center"},
            //1：正常，0：删除
            {label: '会员卡类型状态', name: 'status', index: 'status', width: 70, align: "center", sortable: false,
                //修改会员卡显示方式
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
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
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
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
            //需要在table加载完成之后，执行的
            vm.completeDo()
        }
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        vipCardType: {},
        //扣费类型
        deductionType: null,
        query: {
            status: 1,
            cardTypeName: ''
        },
        show: {
            //金额初始化时，展示
            initialMoney: true,
            //次数初始化时，不展示
            initialNumber: false
        }
    },
    methods: {
        find: function () {
            vm.reload();
        },
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.show.initialMoney = true
            vm.show.initialNumber = false
            //初始化购 扣费方式
            vm.vipCardType = {
                integral: 0,
                deductionType: '2',
                effectiveType: '3',
                takeEffectDate: getNowFormatDate(),
                initialMoney: 0,
                initialNumber: 0
            };
        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
        },
        saveOrUpdate: function (event) {
            //todo 想要获取原生的验证结果，然后判断是否执行下面的内容
            // log("$(\"#formAdd\").onSubmit()",$("#formAdd").submit())
            if (vm.validator()) {
                return
            }
            var url = vm.vipCardType.id == null ? "xsquare/vipcardtype/save" : "xsquare/vipcardtype/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.vipCardType),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function (index) {
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        del: function (event) {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "xsquare/vipcardtype/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.code == 0) {
                            alert('操作成功', function (index) {
                                $("#jqGrid").trigger("reloadGrid");
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        getInfo: function (id) {
            $.get(baseURL + "xsquare/vipcardtype/info/" + id, function (r) {
                vm.vipCardType = r.vipCardType;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    'status': vm.query.status,
                    'cardTypeName': vm.query.cardTypeName
                },
                page: page
            }).trigger("reloadGrid");
        },
        completeDo: function () {
            //加载扣费类型
            vm.getDict(1, vm.deductionType)
        },
        //传入父字典类型,获取其子类所有数据
        getDict: function (pType) {
            $.get(baseURL + "/xsquare/dict/list?pType=" + pType, function (dicts) {
                log("dicts.page.list", dicts.page.list)
                //selected标签内容
                vm.deductionType = dicts.page.list
            })
        },
        validator: function () {
            if(isNUll(vm.vipCardType.name)){
                alert("会员卡类型名称不能为空");
                return true;
            }

            if(isNUll(vm.vipCardType.price)){
                alert("默认售价不能为空");
                return true;
            }

            if(isNUll(vm.vipCardType.initialEffectiveDate)){
                alert("初始有效时间不能为空");
                return true;
            }
        }
    }
});