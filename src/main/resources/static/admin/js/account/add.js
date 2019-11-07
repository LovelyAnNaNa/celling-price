layui.use('form', function(){
    var layer = layui.layer,
        $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        t;                  //表格数据变量
    $('.layui-inline .layui-btn').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    $("#getCode").click(function () {
        var phone = $("#phone").val();
        if (phone.replace(/(^s*)|(s*$)/g, "").length == 0 || phone.length != 11) {
            layer.msg('请输入正确手机号');
            return;
        }
        $.ajax({
            type: "GET",
            url: "/sysAccount/getAccountCode",
            dataType: "json",
            contentType: "application/json",
            data: {
                phone: phone
            },
            success: function (res) {
                layer.close(loadIndex);
                if (res.code == 200) {
                    parent.layer.msg("账户登录成功", {time: 1000}, function () {
                        //刷新父页面
                        parent.location.reload();
                    });
                } else {
                    layer.msg(res.message);
                }
            }
        });
    })
});