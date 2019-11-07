layui.use('form', function(){
    var layer = layui.layer,
        $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        t;                  //表格数据变量

    // t = {
    //     ele : '#accountTable',
    //     url : '/sysAccount/listByPageAndSearch',
    //     method : 'GET',
    //     page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
    //         layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'], //自定义分页布局
    //         //,curr: 5 //设定初始在第 5 页
    //         groups: 6, //只显示 1 个连续页码
    //         first: "first page", //显示首页
    //         last: "last page", //显示尾页
    //         limits: [3, 10, 20, 30]
    //     },
    //     cellMinWidth: 185,
    //     cols: [[
    //         {type: 'checkbox'},
    //         {fixed: 'right', align: 'center', toolbar: '#accountBar'}
    //     ]]
    // };
    // table.render(t);

    form.on('submit(searchForm)', function (data) {
        t.where = data.field;
        table.reload('userTable', t);
        return false;
    })

    var active = {
        addAccount : function () {
        }
    }

    $('.layui-inline .layui-btn').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    $("#getCode").click(function () {
        var phone = $("#phone").val();
        if (phone.replace(/(^s*)|(s*$)/g, "").length == 0 || phone.length != 13) {
            layer.msg('请输入正确手机号');
            return;
        }

    })
});