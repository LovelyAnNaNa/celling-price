layui.use(['layer'], function () {
    var $ = layui.jquery;

    $("#save").click(function() {
        var data = [];
        var dataEle = $(".dataEle");
        for (var i = 0; i < dataEle.length; i++) {
            var ele = dataEle.eq(i);
            //获取配置id
            var id = ele.children(".layui-input-block").children(".id").val();
            //获取配置值
            var configValue = ele.children(".layui-input-block").children(".configValue").val();
            var configKey = ele.children(".layui-input-block").children(".configKey").val();
            var config = {'id':id,"configValue": configValue,"configKey":configKey};
            data.push(config);
        }
        $.ajax({
            url: '/admin/sysConfig/updateConfig',
            data: JSON.stringify(data),
            type: 'POST',
            //返回信息数据格式
            dataType: "json",
            //设置发送过去的数据格式
            contentType: "application/json",
            success: function (res) {
                layer.msg(res.msg);
            }
        })
    });
})