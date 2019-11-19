layui.use('form', function() {
    var layer = layui.layer,
        $ = layui.jquery,
        form = layui.form;

    form.on('submit(addSomeAccount)', function (data) {
        var field = data['field'];

        $.ajax({
            type: "POST",
            url: "/sysAccount/addSome",
            dataType: "JSON",
            timeout: 3300000,
            data: field,
            success: function (res) {
                debugger
                if (res.code == 200) {
                    var shoePhone = $("#showPhone");
                    var data = res['data']
                    var success = data['success']
                    var fail = data['fail']
                    fail.forEach(function (phone, index) {
                        var eleme = '<tr><td>' + phone + '</td><td>失败</td></tr>'
                        shoePhone.append(eleme)
                    });

                    success.forEach(function (phone, index) {
                        var eleme = '<tr><td>' + phone + '</td><td>成功</td></tr>'
                        shoePhone.append(eleme)
                    });

                    layer.msg('请求成功');
                } else {
                    layer.msg(res.msg);
                }
            },
            error: function(xhr, status, err) {
                debugger
            },
            complete: function(XMLHttpRequest, status) {
                debugger
            }
        });
    })
})