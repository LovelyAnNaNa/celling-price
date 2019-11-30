layui.use(['layer', 'form', 'table'], function(){
    var layer = layui.layer,
        $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        t;                  //表格数据变量

    t = {
        elem : '#accountTable',
        url : '/sysAccount/listByPageAndSearch',
        method : 'GET',
        page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
            layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'], //自定义分页布局
            //,curr: 5 //设定初始在第 5 页
            groups: 6, //只显示 1 个连续页码
            first: "first page", //显示首页
            last: "last page", //显示尾页
            limits: [3, 10, 20, 30]
        },
        cellMinWidth: 185,
        cols: [[
            {type: 'checkbox'},
            {field: 'phone', title: '手机号',align:'center'},
            {field: 'msg', title: '账号信息',align:'center'},
            {field: 'type', title: '账号类型',align:'center',templet:'#accountType'},
            {field: 'status', title: '账号状态',align:'center',templet:"#status"},
            {field: 'count', title: '剩余次数',align:'center',templet:"#count"},
            {fixed: 'right', align: 'center', toolbar: '#accountBar'}
        ]]
    };
    table.render(t);

    form.on('submit(searchForm)', function (data) {
        t.where = data.field;
        table.reload('accountTable', t);
        return false;
    })

    var active = {
        addAccount: function () {
            layer.open({
                title: "账号登录",
                type: 1,
                area: ['400px', '200px'],
                shade: 0,
                content: $("#addPhone")
            });
        },
        addSome: function () {
            layer.open({
                title: "账号批量添加登录",
                type: 1,
                area: ['400px', '200px'],
                shade: 0,
                content: $("#addSomePhone")
            });
        },
        delSome: function () {
            var checkStatus = table.checkStatus('accountTable'),
                data = checkStatus.data;
            layer.confirm("您确定要删除选中账号吗?", {title:'删除账号',btn: ['是的,我确定', '不,我在想想']},
                function () {
                    $.ajax({
                        type: "POST",
                        url: "/sysAccount/deleteSome",
                        dataType: "json",
                        contentType: "application/json",
                        data: JSON.stringify(data),
                        success: function (res) {
                            var code = res['code'];
                            if (200 == code) {
                                layer.msg("删除成功!", {time: 1000}, function () {
                                    table.reload('accountTable', t);//重新刷新表格
                                });
                            } else {
                                layer.msg(res.msg);
                            }
                        }
                    });
                }
            );
        }
    };

    $("#saveAccount").click(function () {
        var phone = $("#phone").val();
        if (phone.replace(/(^s*)|(s*$)/g, "").length == 0 || phone.length != 11) {
            layer.msg('请输入正确手机号');
            return;
        }
        $.ajax({
            type: "POST",
            url: "/sysAccount/insert",
            dataType: "JSON",
            contentType: "application/json",
            data: JSON.stringify({
                "phone": phone
            }),
            success: function (res) {
                if (res.code == 200) {
                    layer.msg("账号添加成功", {time: 1000}, function () {
                        //刷新父页面
                        location.reload();
                    });
                } else {
                    layer.msg(res.msg);
                }
            }
        });
    })

    $("#saveSomeAccount").click(function () {
        var count = $("#count").val();
        if (count.replace(/(^s*)|(s*$)/g, "").length == 0 || count <= 0) {
            layer.msg('请输入数量');
            return;
        }

        $.ajax({
            type: "POST",
            url: "/sysAccount/addSome",
            dataType: "JSON",
            data: {
                "count": count
            },
            success: function (res) {
                if (res.code == 200) {
                    layer.msg("添加成功", {time: 1000}, function () {
                        //刷新父页面
                        location.reload();
                    });
                } else {
                    layer.msg(res.msg);
                }
            }
        });
    })

    table.on('tool(accountList)', function (obj) {
        var data = obj.data;
        if(obj.event == 'del'){
            layer.confirm("您确定要删除该账号吗?", {title:'删除账号',btn: ['是的,我确定', '不,我在想想']},
                function () {
                    $.post("/sysAccount/delete", {"id": data.id}, function (res) {
                        var code = res.code;
                        if (200 == code) {
                            layer.msg("删除成功!", {time: 1000}, function () {
                                table.reload('accountTable', t);//重新刷新表格
                            });
                        } else {
                            layer.msg(res.msg);
                        }
                    });
                }
            );
        }else if(obj.event == 'login'){
            addIndex = layer.open({
                title: "账号登录",
                type: 2,
                area: ['80%', '100%'],//定义宽和高
                content: "/sysAccount/add?phone=" + data.phone+"&id="+data.id,
                success: function () {
                    setTimeout(function () {
                        layer.tips('点击此处返回用户列表', '.layui-layer-setwin .layui-layer-close', {
                            tips: 3
                        });
                    }, 500);
                }
            });
            //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
            $(window).resize(function () {
                layer.full(addIndex);
            });
            layer.full(addIndex);
        }
    });

    $('.layui-inline .layui-btn').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
});