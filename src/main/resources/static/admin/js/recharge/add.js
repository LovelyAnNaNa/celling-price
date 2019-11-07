layui.use(['form', 'layer', 'jquery'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery;

    //添加用户
    form.on('submit(addRecharge)', function (data) {
        var loadIndex = layer.load(2, {
            shade: [0.3, '#333']
        });
        alert(JSON.stringify(data.field));
        $.ajax({
            type: "POST",
            url: "/admin/sysRecharge/saveAdd",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(data.field),
            success: function (res) {
                layer.close(loadIndex);
                if (res.code == 200) {
                    parent.layer.msg("积分修改成功！", {time: 1000}, function () {
                        //刷新父页面
                        parent.location.reload();
                    });
                } else {
                    layer.msg(res.message);
                }
            }
        });
        return false;
    });
});