$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'xsquare/classtable/list',
        datatype: "json",
        // colNames: vm.colNames,
        colModel: [
            // { label: 'id', name: '', index: 'id', width: 50, key: true },
            { name: '周', index: 'course_id', width: 80, align: "center", sortable: false,
                //修改会员卡显示方式
                formatter: function (cellValue, options, rowObject) {
                    return '<span style="font-size: 14px;font-weight: 700;line-height: 100px">'+cellValue[0].describe+'</span>'
                }
            },
            { name: '星期一', index: 'teacher_id', width: 80, align: "center", sortable: false,
                //修改课程显示方式
                formatter: function (cellValue, options, rowObject) {
                    //日期加时间段
                    var params = '\''+cellValue[0].date+'\',\''+cellValue[0].timeSlot+'\',\''+cellValue[0].describe+'\''
                    log(cellValue[0])
                    var showContent = '<div onclick="showClass('+params+')" style="cursor: pointer">'
                    //长度为1,说明只有这个时段的课程信息,并无实际课程
                    if (cellValue.length == 1) {
                        showContent += '<span style="font-size: 14px;font-weight: 700;line-height: 100px">点击添加</span>'
                    }
                    for (var i=1; i<cellValue.length;i++) {
                        var color = "#76caff"
                        //奇数
                        if (i%2==1) {
                            color = '#47e3ff'
                        }
                        showContent += '<div style="border: 1px solid '+color+';border-radius: 10px;margin: 10px;margin-left:2px;background:'+color+';color: black ">'+
                            cellValue[i].courseEntity.name+'<br>'+
                            cellValue[i].teacherEntity.name+'<br>'+
                            cellValue[i].classroomEntity.name+'<br>'+
                            cellValue[i].startHour+':'+cellValue[i].startMinute+' ~ '+cellValue[i].endHour+':'+cellValue[i].endMinute+'<br>'+
                            '</div>'
                    }
                    showContent += '</div>'
                    return showContent
                }},
            { name: '星期二', index: 'classroom_id', width: 80, align: "center", sortable: false,
                //修改课程显示方式
                formatter: function (cellValue, options, rowObject) {
                    //日期加时间段
                    var params = '\''+cellValue[0].date+'\',\''+cellValue[0].timeSlot+'\',\''+cellValue[0].describe+'\''
                    log(cellValue[0])
                    var showContent = '<div onclick="showClass('+params+')" style="cursor: pointer">'
                    //长度为1,说明只有这个时段的课程信息,并无实际课程
                    if (cellValue.length == 1) {
                        showContent += '<span style="font-size: 14px;font-weight: 700;line-height: 100px">点击添加</span>'
                    }
                    for (var i=1; i<cellValue.length;i++) {
                        var color = "#76caff"
                        //奇数
                        if (i%2==1) {
                            color = '#47e3ff'
                        }
                        showContent += '<div style="border: 1px solid '+color+';border-radius: 10px;margin: 10px;margin-left:2px;background:'+color+';color: black ">'+
                            cellValue[i].courseEntity.name+'<br>'+
                            cellValue[i].teacherEntity.name+'<br>'+
                            cellValue[i].classroomEntity.name+'<br>'+
                            cellValue[i].startHour+':'+cellValue[i].startMinute+' ~ '+cellValue[i].endHour+':'+cellValue[i].endMinute+'<br>'+
                            '</div>'
                    }
                    showContent += '</div>'
                    return showContent
                }},
            { name: '星期三', index: 'start_hour', width: 80, align: "center", sortable: false,
                //修改课程显示方式
                formatter: function (cellValue, options, rowObject) {
                    //日期加时间段
                    var params = '\''+cellValue[0].date+'\',\''+cellValue[0].timeSlot+'\',\''+cellValue[0].describe+'\''
                    log(cellValue[0])
                    var showContent = '<div onclick="showClass('+params+')" style="cursor: pointer">'
                    //长度为1,说明只有这个时段的课程信息,并无实际课程
                    if (cellValue.length == 1) {
                        showContent += '<span style="font-size: 14px;font-weight: 700;line-height: 100px">点击添加</span>'
                    }
                    for (var i=1; i<cellValue.length;i++) {
                        var color = "#76caff"
                        //奇数
                        if (i%2==1) {
                            color = '#47e3ff'
                        }
                        showContent += '<div style="border: 1px solid '+color+';border-radius: 10px;margin: 10px;margin-left:2px;background:'+color+';color: black ">'+
                            cellValue[i].courseEntity.name+'<br>'+
                            cellValue[i].teacherEntity.name+'<br>'+
                            cellValue[i].classroomEntity.name+'<br>'+
                            cellValue[i].startHour+':'+cellValue[i].startMinute+' ~ '+cellValue[i].endHour+':'+cellValue[i].endMinute+'<br>'+
                            '</div>'
                    }
                    showContent += '</div>'
                    return showContent
                }},
            { name: '星期四', index: 'start_minute', width: 80, align: "center", sortable: false,
                //修改课程显示方式
                formatter: function (cellValue, options, rowObject) {
                    //日期加时间段
                    var params = '\''+cellValue[0].date+'\',\''+cellValue[0].timeSlot+'\',\''+cellValue[0].describe+'\''
                    log(cellValue[0])
                    var showContent = '<div onclick="showClass('+params+')" style="cursor: pointer">'
                    //长度为1,说明只有这个时段的课程信息,并无实际课程
                    if (cellValue.length == 1) {
                        showContent += '<span style="font-size: 14px;font-weight: 700;line-height: 100px">点击添加</span>'
                    }
                    for (var i=1; i<cellValue.length;i++) {
                        var color = "#76caff"
                        //奇数
                        if (i%2==1) {
                            color = '#47e3ff'
                        }
                        showContent += '<div style="border: 1px solid '+color+';border-radius: 10px;margin: 10px;margin-left:2px;background:'+color+';color: black ">'+
                            cellValue[i].courseEntity.name+'<br>'+
                            cellValue[i].teacherEntity.name+'<br>'+
                            cellValue[i].classroomEntity.name+'<br>'+
                            cellValue[i].startHour+':'+cellValue[i].startMinute+' ~ '+cellValue[i].endHour+':'+cellValue[i].endMinute+'<br>'+
                            '</div>'
                    }
                    showContent += '</div>'
                    return showContent
                }},
            { name: '星期五', index: 'end_hour', width: 80, align: "center", sortable: false,
                //修改课程显示方式
                formatter: function (cellValue, options, rowObject) {
                    //日期加时间段
                    var params = '\''+cellValue[0].date+'\',\''+cellValue[0].timeSlot+'\',\''+cellValue[0].describe+'\''
                    log(cellValue[0])
                    var showContent = '<div onclick="showClass('+params+')" style="cursor: pointer">'
                    //长度为1,说明只有这个时段的课程信息,并无实际课程
                    if (cellValue.length == 1) {
                        showContent += '<span style="font-size: 14px;font-weight: 700;line-height: 100px">点击添加</span>'
                    }
                    for (var i=1; i<cellValue.length;i++) {
                        var color = "#76caff"
                        //奇数
                        if (i%2==1) {
                            color = '#47e3ff'
                        }
                        showContent += '<div style="border: 1px solid '+color+';border-radius: 10px;margin: 10px;margin-left:2px;background:'+color+';color: black ">'+
                            cellValue[i].courseEntity.name+'<br>'+
                            cellValue[i].teacherEntity.name+'<br>'+
                            cellValue[i].classroomEntity.name+'<br>'+
                            cellValue[i].startHour+':'+cellValue[i].startMinute+' ~ '+cellValue[i].endHour+':'+cellValue[i].endMinute+'<br>'+
                            '</div>'
                    }
                    showContent += '</div>'
                    return showContent
                }},
            { name: '星期六', index: 'end_minute', width: 80, align: "center", sortable: false,
                //修改课程显示方式
                formatter: function (cellValue, options, rowObject) {
                    //日期加时间段
                    var params = '\''+cellValue[0].date+'\',\''+cellValue[0].timeSlot+'\',\''+cellValue[0].describe+'\''
                    log(cellValue[0])
                    var showContent = '<div onclick="showClass('+params+')" style="cursor: pointer">'
                    //长度为1,说明只有这个时段的课程信息,并无实际课程
                    if (cellValue.length == 1) {
                        showContent += '<span style="font-size: 14px;font-weight: 700;line-height: 100px">点击添加</span>'
                    }
                    for (var i=1; i<cellValue.length;i++) {
                        var color = "#76caff"
                        //奇数
                        if (i%2==1) {
                            color = '#47e3ff'
                        }
                        showContent += '<div style="border: 1px solid '+color+';border-radius: 10px;margin: 10px;margin-left:2px;background:'+color+';color: black ">'+
                            cellValue[i].courseEntity.name+'<br>'+
                            cellValue[i].teacherEntity.name+'<br>'+
                            cellValue[i].classroomEntity.name+'<br>'+
                            cellValue[i].startHour+':'+cellValue[i].startMinute+' ~ '+cellValue[i].endHour+':'+cellValue[i].endMinute+'<br>'+
                            '</div>'
                    }
                    showContent += '</div>'
                    return showContent
                }},
            { name: '星期日', index: 'describe', width: 80, align: "center", sortable: false,
                //修改课程显示方式
                formatter: function (cellValue, options, rowObject) {
                    //日期加时间段
                    var params = '\''+cellValue[0].date+'\',\''+cellValue[0].timeSlot+'\',\''+cellValue[0].describe+'\''
                    log(cellValue[0])
                    var showContent = '<div onclick="showClass('+params+')" style="cursor: pointer" style="cursor: pointer">'
                    //长度为1,说明只有这个时段的课程信息,并无实际课程
                    if (cellValue.length == 1) {
                        showContent += '<span style="font-size: 14px;font-weight: 700;line-height: 100px">点击添加</span>'
                    }
                    for (var i=1; i<cellValue.length;i++) {
                        var color = "#76caff"
                        //奇数
                        if (i%2==1) {
                            color = '#47e3ff'
                        }
                        showContent += '<div style="border: 1px solid '+color+';border-radius: 10px;margin: 10px;margin-left:2px;background:'+color+';color: black ">'+
                            cellValue[i].courseEntity.name+'<br>'+
                            cellValue[i].teacherEntity.name+'<br>'+
                            cellValue[i].classroomEntity.name+'<br>'+
                            cellValue[i].startHour+':'+cellValue[i].startMinute+' ~ '+cellValue[i].endHour+':'+cellValue[i].endMinute+'<br>'+
                            '</div>'
                    }
                    showContent += '</div>'
                    return showContent
                }
            }
        ],
        viewrecords: true,
        height: 600,
        // rowNum: 10,
        // rowList: [10, 30, 50],
        sortable: false,
        //不要行号
        rownumbers: false,
        //行号的宽度
        rownumWidth: 0,
        autowidth: true,
        //不要多选
        multiselect: false,
        // pager: "#jqGridPager",
        jsonReader: {
            root: "page.list",
            // page: "page.currPage",
            // total: "page.totalPage",
            // records: "page.totalCount"
        },
        // prmNames: {
        //     page: "page",
        //     rows: "limit",
        //     order: "order"
        // },
        gridComplete: function () {
            // $("#jqGrid tr td div").toggle(
            //     function(){
            //         $(this).addClass("selectBG");//点第一次选中
            //     },
            //     function(){
            //         $(this).removeClass("selectBG");//再点一次就反选
            //     }
            // );

            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
            //加载table后要加载的信息
            vm.completeDo()
        }
    });
});

$(function () {
    $("#theJqGrid").jqGrid({
        url: baseURL + 'xsquare/classtable/theList',
        datatype: "json",
        colModel: [
            { label: 'id', name: 'id', index: 'id', width: 50, key: true, hidden: true },
            { label: '课程', name: 'courseEntity.name', index: 'courseEntity_name', width: 200, align: "center", sortable: false},
            { label: '上课老师', name: 'teacherEntity.name', index: 'teacherEntity_name', width: 120, align: "center", sortable: false},
            { label: '上课教室', name: 'classroomEntity.name', index: 'classroomEntity_name', width: 120, align: "center", sortable: false},
            { label: '课程时间', name: '', index: '', width: 150, align: "center", sortable: false,
                //将上课时间进行拼接
                formatter: function (cellValue, options, rowObject) {
                    return rowObject.startHour+':'+rowObject.startMinute+' ~ '+rowObject.endHour+':'+rowObject.endMinute
                }
            },
            { label: '上课内容', name: 'describe', index: 'course_id', width: 200, align: "center", sortable: false}
        ],
        viewrecords: true,
        height: 380,
        rowNum: 10,
        rowList: [10,30,50],
        sortable: false,
        //行号
        rownumbers: true,
        //行号的宽度
        rownumWidth: 40,
        autowidth: true,
        //不要多选
        multiselect: true,
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
            //加载Thetable后要加载的信息
            vm.theCompletDo()
        }
    });
});


//点击课程表一个单元格,当天这个时间段的课程信息
function showClass(date, timeSlot, describe) {
    log(date, describe)
    vm.reloadTheList(date, timeSlot)
    vm.newClasstable.date = date
    vm.newClasstable.timeSlot = timeSlot
    vm.show.theList = true
    vm.theListTitle = date+' '+describe+' 课程表'
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


var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        classtable: {
        },
        query: {
            course: '',
            week: getWeek(new Date(), 0),
            //将周解析后分为开始日期和结束日期
            startDate: '',
            endDate: ''
        },
        //所有的课程
        allCourses: {},
        show: {
            //展示某一天的某个时间段的table
            theList: false,
            //新增,修改界面
            handle: false,
            //复制课程表
            copyCourses: false
        },
        //所有老师
        teachers: {},
        //新增需要课程
        courses: {},
        //所有教室
        classrooms: {},
        newClasstable : {
            date: '',
            timeSlot: '',
            timeSlotDesc: '',
            startMinute: '00',
            endMinute: '00',
            describe: ''
        },
        //复制课程表
        copyCourses: {
            courseWeek: '',
            course: '',
            startWeek: '',
            endWeek: ''
        },
        delCourses: {
            course: '',
            startDate: '',
            endDate: ''
        },
        theListTitle: '',
        //上课开始时间
        startHour: [],
        endHour: [6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24],
        minute: ['00','05','10','15','20','25','30','35','40','45','50','55']
    },
    methods: {
        //复制课程表
        copyCourse: function () {
            //将当前周的时间赋值给课程的复制时间
            vm.copyCourses.courseWeek = vm.query.week
            var startAndEnd = getStartAndEndFromWeek(vm.query.week)
            //获取当前查询周的结束日期，加上周的偏移量1，赋值给赋值的开始时间
            vm.copyCourses.startWeek = getWeek(startAndEnd[1], 1)
            vm.copyCourses.endWeek = getWeek(startAndEnd[1], 2)
            vm.copyCoursesWeekChange()
            vm.copyCourses.course = vm.courses[0].id
            vm.show.copyCourses = true;
            layer.open({
                type: 1,
                title: '复制课程表'+'(<span style="color: red">*</span> 为必填项)',
                //无效
                area: ['1300', '1300'],
                shade: false,
                content: jQuery("#copyCourses"),
                btn: ['确认', '取消'],
                yes: function (index) {
                    vm.linkCopyCourses()
                },
                btn2: function (index) {
                    layer.close(index)
                }
            });
        },
        //当复制的课程表的起始时间改变的时候
        copyCoursesWeekChange: function () {
            //开始日期最大为结束的日期
            $("#copyCourses_startWeek").attr('max', vm.copyCourses.endWeek)
            //结束日期最小为开始的日期
            $("#copyCourses_endWeek").attr('min', vm.copyCourses.startWeek)
            //如果结束时间小于开始时间,将开始时间改变为结束时间
            if (vm.copyCourses.endWeek < vm.copyCourses.startWeek) {
                vm.copyCourses.startWeek = vm.copyCourses.endWeek
            }
        },
        //请求复制课程表
        linkCopyCourses: function () {
            if (isNUll(vm.copyCourses.courseWeek)) {
                alert("请选择复制课程的周")
                return true
            }
            if (isNUll(vm.copyCourses.startWeek)) {
                alert("请选择复制课程的开始周")
                return true
            }
            if (isNUll(vm.copyCourses.endWeek)) {
                alert("请选择复制课程的结束周")
                return true
            }
            var url = "xsquare/classtable/copy"
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.copyCourses),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function () {
                            vm.find()
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        //批量删除课程表
        delCourse: function () {
            var startAndEnd = getStartAndEndFromWeek(vm.query.week)
            //获取查询周的开始和结束的日期,作为删除的开始和结束的日期
            vm.delCourses.startDate = dateToStr(startAndEnd[0])
            vm.delCourses.endDate = dateToStr(startAndEnd[1])
            vm.delCoursesWeekChange()
            vm.delCourses.course = vm.courses[0].id
            vm.show.delCourses = true;
            layer.open({
                type: 1,
                title: '刪除课程表'+'(<span style="color: red">*</span> 为必填项)',
                //无效
                area: ['1300', '1300'],
                shade: false,
                content: jQuery("#delCourses"),
                btn: ['确认', '取消'],
                yes: function (index) {
                    vm.linkDelCourses()
                },
                btn2: function (index) {
                    layer.close(index)
                }
            });
        },
        //当刪除的课程表的起始时间改变的时候
        delCoursesWeekChange: function () {
            //开始日期最大为结束的日期
            $("#delCourses_startDate").attr('max', vm.delCourses.endDate)
            //结束日期最小为开始的日期
            $("#delCourses_endDate").attr('min', vm.delCourses.startDate)
            //如果结束时间小于开始时间,将开始时间改变为结束时间
            if (vm.delCourses.endDate < vm.delCourses.startDate) {
                vm.delCourses.startDate = vm.delCourses.endDate
            }
        },
        //请求删除课程表
        linkDelCourses: function () {
            if (isNUll(vm.delCourses.startDate)) {
                alert("请选择删除的开始日期")
                return true
            }
            if (isNUll(vm.delCourses.endDate)) {
                alert("请选择删除的结束日期")
                return true
            }
            var url = "xsquare/classtable/del"
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.delCourses),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function () {
                            vm.find()
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        find: function () {
            vm.reload();
        },
        query: function () {
            vm.reload();
        },
        add: function () {
            //初始化设置老师，课程，教室的初始值
            if (vm.teachers.length != 0) {
                vm.newClasstable.teacherId = vm.teachers[0].id
            } else {
                alert('您还没有添加老师~<br>请在\"资源管理\" > \"老师管理\"中添加老师~')
                return
            }
            if (vm.courses.length != 0) {
                vm.newClasstable.courseId = vm.courses[0].id
            } else {
                alert('您还没有添加课程~<br>请在\"课程管理\" > \"课程管理\"中添加课程~')
                return
            }
            if (vm.classrooms.length != 0) {
                vm.newClasstable.classroomId = vm.classrooms[0].id
            } else {
                alert('您还没有添加教室~<br>请在\"资源管理\" > \"教室管理\"中添加教室~')
                return
            }
            vm.classtable = vm.newClasstable
            vm.timeSlotChange()
            vm.show.handle = true;
            layer.open({
                type: 1,
                title: '新增课程表'+'(<span class="notNullMarks" style="color: red">*</span> 为必填项)',
                //无效
                area: ['1300', '1300'],
                shade: false,
                content: jQuery("#handle"),
                btn: ['确认', '取消'],
                yes: function (index) {
                    vm.saveOrUpdate(function () {
                        layer.close(index)
                    })
                },
                btn2: function (index) {
                    vm.reloadTheList()
                    layer.close(index)
                }
            });
        },
        update: function (event) {
            var id = getTheSelectedRow();
            log(id)
            if (id == null) {
                return;
            }
            vm.getInfo(id)
            vm.show.handle = true;
            layer.open({
                type: 1,
                title: '修改课程表'+'(<span class="notNullMarks" style="color: red">*</span> 为必填项)',
                //无效
                area: ['1300', '1300'],
                shade: false,
                content: jQuery("#handle"),
                btn: ['确认', '取消'],
                yes: function (index) {
                    vm.saveOrUpdate(function () {
                        log("layer.close(index)")
                        layer.close(index)
                    })
                },
                btn2: function (index) {
                    vm.reloadTheList()
                    layer.close(index)
                }
            });
        },
        saveOrUpdate: function (close) {
            if(vm.validator()){
                return
            }
            var url = vm.classtable.id == null ? "xsquare/classtable/save" : "xsquare/classtable/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.classtable),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function () {
                            //动态修改theList的title
                            vm.theListTitle = vm.classtable.date+' '+vm.classtable.timeSlotDesc+' 课程表'
                            $(".layui-layer-title").empty()
                            $(".layui-layer-title").append(vm.theListTitle)
                            vm.reloadTheList(vm.classtable.date, vm.classtable.timeSlot);
                            vm.reload();
                            vm.classtable = {}
                            //关闭添加的模态框
                            close()
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        del: function (event) {
            var ids = getTheSelectedRows();
            if (ids == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "xsquare/classtable/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.code == 0) {
                            alert('操作成功', function (index) {
                                $("#jqGrid").trigger("reloadGrid");
                                $("#theJqGrid").trigger("reloadGrid");
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        getInfo: function (id) {
            $.get(baseURL + "xsquare/classtable/info/" + id, function (r) {
                vm.classtable = r.classtable;
                vm.timeSlotChange()
            });
        },
        reload: function (event) {
            vm.showList = true;
            //获取这周的开始日期和结束日期
            var startAndEndDate = getStartAndEndFromWeek(vm.query.week)
            vm.query.startDate = dateToStr(startAndEndDate[0])
            vm.query.endDate = dateToStr(startAndEndDate[1])
            log(vm.query.startDate, vm.query.endDate)
            // $("#jqGrid").jqGrid('GridUnload');
            //禁止jqGrid加载，下面的这个才是有用的，上面的会报找不到该方法
            // $.jgrid.gridUnload("jqGrid")
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            //表格提交数据未更新，先清空下提交的数据
            $("#jqGrid").jqGrid('clearGridData')
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    week: JSON.stringify(vm.query.week),
                    startDate: JSON.stringify(vm.query.startDate),
                    endDate: JSON.stringify(vm.query.endDate),
                    course: vm.query.course
                },
                page: page
            }).trigger("reloadGrid");
            //重新加载表格
            // $("#jqGrid").trigger("reloadGrid");
        },
        //重新加载展示某一天某个时段的课程
        reloadTheList: function (date, timeSlot) {
            var page = $("#theJqGrid").jqGrid('getGridParam', 'page');
            //表格提交数据未更新，先清空下提交的数据
            $("#theJqGrid").jqGrid('clearGridData')
            $("#theJqGrid").jqGrid('setGridParam', {
                postData: {
                    date: date,
                    timeSlot: timeSlot
                },
                page: page
            }).trigger("reloadGrid");
        },
        //加载table后要加载的信息
        completeDo: function () {
            vm.changeWeekDate()
            //加载所有的课程
            vm.getCourses(0)
        },
        //加载theTable要加载的信息
        theCompletDo: function () {
            vm.getTeachers()
            vm.getClassrooms()
            //加载未删除的课程
            vm.getCourses(1)
        },
        //获取所有status=1的老师
        getTeachers: function () {
            $.get(baseURL + "/xsquare/teacher/list?status=1", function (teachers) {
                vm.teachers = teachers.page.list
            })
        },
        //获取所有status=1的教室
        getClassrooms: function () {
            $.get(baseURL + "/xsquare/classroom/list?status=1", function (classrooms) {
                vm.classrooms = classrooms.page.list
            })
        },
        //获取所有status=1的课程
        getCourses: function (checkStatus) {
            log(checkStatus)
            if (checkStatus == 1) {
                $.get(baseURL + "/xsquare/course/list?status=1", function (courses) {
                    vm.courses = courses.page.list
                })
            } else {
                $.get(baseURL + "/xsquare/course/list?status=", function (courses) {
                    vm.allCourses = courses.page.list
                })
            }
        },
        //修改星期对应的日期
        changeWeekDate: function () {
            $("#jqGrid").jqGrid('destroyGroupHeader')
            var dateArray = getAllWeekDate(vm.query.week)
            $("#jqGrid").jqGrid('setGroupHeaders', {
                useColSpanStyle: true,
                groupHeaders: [
                    // {startColumnName: '周', numberOfColumns: 1, titleText: '日期'},
                    {startColumnName: '星期一', numberOfColumns: 1, titleText: dateToStr(dateArray[0])},
                    {startColumnName: '星期二', numberOfColumns: 1, titleText: dateToStr(dateArray[1])},
                    {startColumnName: '星期三', numberOfColumns: 1, titleText: dateToStr(dateArray[2])},
                    {startColumnName: '星期四', numberOfColumns: 1, titleText: dateToStr(dateArray[3])},
                    {startColumnName: '星期五', numberOfColumns: 1, titleText: dateToStr(dateArray[4])},
                    {startColumnName: '星期六', numberOfColumns: 1, titleText: dateToStr(dateArray[5])},
                    {startColumnName: '星期日', numberOfColumns: 1, titleText: dateToStr(dateArray[6])}
                ]
            });
        },
        //时间段改变
        timeSlotChange: function () {
            //上午
            if (vm.classtable.timeSlot == 1) {
                vm.startHour = [6,7,8,9,10,11,12]
                vm.classtable.timeSlotDesc = "上午"
            } else if (vm.classtable.timeSlot == 2) {
                vm.startHour = [13,14,15,16,17,18]
                vm.classtable.timeSlotDesc = "下午"
            }else if (vm.classtable.timeSlot == 3) {
                vm.startHour = [19,20,21,22,23,24]
                vm.classtable.timeSlotDesc = "晚上"
            }
            //每当上课时间段变化的时候，将上课开始时间设置为开始小时的第一个，结束时间设置为开始小时的第二个，即默认上课时间为1小时
            vm.classtable.startHour = vm.startHour[0]
            vm.classtable.endHour = vm.startHour[1]
        },
        //格式验证
        validator: function () {
            if (isNUll(vm.classtable.date)) {
                alert("请选择上课日期")
                return true
            }
            if (isNUll(vm.classtable.courseId)) {
                alert("请选择上课的课程")
                return true
            }
            if (isNUll(vm.classtable.teacherId)) {
                alert("请选择上课的老师")
                return true
            }
            if (isNUll(vm.classtable.classroomId)) {
                alert("请选择上课的教室")
                return true
            }
        }
    }
});