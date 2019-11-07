layui.use(['layer', 'form', 'table','laydate'], function () {
    var layer = layui.layer,
        $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        laydate = layui.laydate,
        t;              //表格变量
    t = {
        elem: '#order-table',
        even: true,
        url: '/admin/sysOrder/list',
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
            {field: 'customer_id', title: '顶价用户',align:'center',templet:'<div>{{ d.customer.customerName }}</div>'},
            {field: 'commodity', title: '顶价的商品',align:'center'},
            {field: 'status', title: '顶价结果',align:'center',templet:'#orderStatus'},
            {field: 'deductIntegral', title: '扣除的积分',align:'center'},
            {field: 'createTime', title: '创建时间',align:'center'},
            {title: '操作', fixed: 'right', align: 'center', toolbar: '#orderBar'}
        ]]
        ,
        done: function () {
            $("[data-field='id']").css('display','none');
            // $('table.layui-table thead tr th:eq(1)').addClass('layui-hide');
        }
    };
    table.render(t);
    //隐藏表格id列
    // $('table.layui-table thead tr th:eq(0)').addClass('layui-hide');
    var active = {
        addCustomer: function () {
            addIndex = layer.open({
                title: "添加用户",
                type: 2,
                area: ['80%', '100%'],//定义宽和高
                content: "/admin/sysCustomer/add",
                success: function (layero, addIndex) {
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
        },
    };

    table.on('tool(orderList)', function (obj) {
        var data = obj.data;
        if(obj.event == 'del'){
            layer.confirm("您确定要删除该订单吗?", {title:'删除用户',btn: ['是的,我确定', '不,我在想想']},
                function () {
                    $.post("/admin/sysOrder/del", {"orderId": data.id}, function (res) {
                        var code = res.code;
                        if (200 == code) {
                            layer.msg("删除成功!", {time: 1000}, function () {
                                table.reload('order-table', t);//重新刷新表格
                            });
                        } else {
                            layer.msg(res.message);
                        }
                    });
                }
            );
        }
    });

    $(".datetime").each(function(index,ele){
        //执行一个laydate实例
        laydate.render({
            elem: this, //指定元素
            type: 'datetime'
        });
    })

    $('.layui-inline .layui-btn').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
    //搜索
    form.on("submit(searchForm)", function (data) {
        t.where = data.field;
        table.reload('order-table', t);
        return false;
    });
});