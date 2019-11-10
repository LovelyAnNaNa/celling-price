layui.use(['layer', 'form', 'table','laydate'], function () {
    var layer = layui.layer,
        $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        laydate = layui.laydate,
        t;              //表格变量
    t = {
        elem: '#recharge-table',
        even: true,
        url: '/admin/sysRecharge/list',
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
            {field: 'customerId', title: '充值用户名称',align:'center',templet:'<div>{{ d.customer.customerName }}</div>'},
            {field: 'integral', title: '充值积分',align:'center'},
            {field: 'money', title: '充值金额',align:'center'},
            {field: 'createTime', title: '充值时间',align:'center'},
            {title: '操作', fixed: 'right', align: 'center', toolbar: '#rechargeBar'}
        ]]
        ,
        done: function () {
            // $("[data-field='id']").css('display','none');
            // $('table.layui-table thead tr th:eq(1)').addClass('layui-hide');
        }
    };
    table.render(t);

    table.on('tool(rechargeList)', function (obj) {
        var data = obj.data;
        if(obj.event == 'del'){
            layer.confirm("您确定要删除该条充值记录吗?", {title:'删除记录',btn: ['是的,我确定', '不,我在想想']},
                function () {
                    $.post("/admin/sysRecharge/del", {"rechargeId": data.id}, function (res) {
                        var code = res.code;
                        if (200 == code) {
                            layer.msg("删除成功!", {time: 1000}, function () {
                                table.reload('recharge-table', t);//重新刷新表格
                            });
                        } else {
                            layer.msg(res.msg);
                        }
                    });
                }
            );
        }
    });

    //绑定日期控件
    $(".datetime").each(function(index,ele){
        //执行一个laydate实例
        laydate.render({
            elem: this, //指定元素
            type: 'datetime'
        });
    })

    //搜索
    form.on("submit(searchForm)", function (data) {
        t.where = data.field;
        table.reload('recharge-table', t);
        return false;
    });
});