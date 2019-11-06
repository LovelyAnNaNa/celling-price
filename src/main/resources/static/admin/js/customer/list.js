layui.use(['layer', 'form', 'table'], function () {
    var layer = layui.layer,
        $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        t;              //表格变量
    t = {
        elem: '#customer-table',
        even: true,
        url: '/admin/sysCustomer/list',
        method: 'post',
        page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
            layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'], //自定义分页布局
            //,curr: 5 //设定初始在第 5 页
            groups: 6, //只显示 1 个连续页码
            first: "首页", //显示首页
            last: "尾页", //显示尾页
            limits: [3, 10, 20, 30]
        },
        width: $(parent.window).width() - 223,
        cols: [[
            {type: 'checkbox'},
            /* {field:'id',        title: 'ID'   },*/
            {field: 'customerName', title: '用户名称',align:'center'},
            {field: 'integral', title: '积分余额',align:'center'},
            {title:'操作',fixed:'right',align:'center',toolBar:'#customerBar'},
        ]]
        /*,
        done: function () {
            $("[data-field='id']").css('display','none');
        }*/
    };
    table.render(t);
    //搜索
    form.on("submit(searchForm)", function (data) {
        alert("aaaa");
        table.reload('role-table', {
            page: {curr: 1},
            where: data.field
        });
        return false;
    });
});