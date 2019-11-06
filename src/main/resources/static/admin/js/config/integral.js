layui.use(['layer'], function () {
    var $ = layui.jquery;

    $("#save").click(function() {
        var id = $("#deductConfigId").val();
        $.ajax({
            url: '/admin/sysConfig/updateDeductIntegral',
            data: {
                "deductIntegral" : $("#configValue").val()
            },
            type: 'POST',
            success: function (res) {
                layer.msg(res.msg);
            }
        })
    });
})