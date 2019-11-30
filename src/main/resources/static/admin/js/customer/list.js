layui.use(['layer', 'form', 'table','laydate'], function () {
    var layer = layui.layer,
        $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        laydate = layui.laydate,
        t;              //表格变量

    //绑定日期控件
    $(".datetime").each(function(index,ele){
        //执行一个laydate实例
        laydate.render({
            elem: this, //指定元素
            type: 'datetime'
        });
    })

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
            {field: 'customerName', title: '用户名称',align:'center'},
            {field: 'integral', title: '积分余额',align:'center'},
            {field: 'createTime', title: '添加时间',align:'center'},
            {field: 'status', title: '用户状态',align:'center',templet:"#status"},
            {title: '操作', fixed: 'right', align: 'center', toolbar: '#customerBar'}
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

    table.on('tool(customerList)', function (obj) {
      var data = obj.data;
      if(obj.event == 'del'){
          layer.confirm("您确定要删除该用户吗?(该用户对应的充值记录也会删除)", {title:'删除用户',btn: ['是的,我确定', '不,我在想想']},
              function () {
                  $.post("/admin/sysCustomer/del", {"customerId": data.id}, function (res) {
                      var code = res.code;
                      if (200 == code) {
                          layer.msg("删除成功!", {time: 1000}, function () {
                              table.reload('customer-table', t);//重新刷新表格
                          });
                      } else {
                          layer.msg(res.msg);
                      }
                  });
              }
          );
      }else if(obj.event == 'recharge'){
          addIndex = layer.open({
              title: "修改积分",
              type: 2,
              area: ['80%', '100%'],//定义宽和高
              content: "/admin/sysRecharge/add?customerId=" + data.id,
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
      } else if (obj.event == 'enable' || obj.event == 'prohibit') {
          var operation = '启用';
          if (obj.event == 'prohibit') {
              operation = '禁用';
          }
          layer.confirm("您确定要" + operation + "该用户吗？", {title: operation + '用户',btn: ['是的,我确定', '不,我在想想']},
              function () {
                  $.post("/admin/sysCustomer/enableCustomerOrProhibit", {"id": data.id}, function (res) {
                      var code = res.code;
                      if (200 == code) {
                          layer.msg(operation + "成功!", {time: 1000}, function () {
                              table.reload('customer-table', t);//重新刷新表格
                          });
                      } else {
                          layer.msg(res.msg);
                      }
                  });
              }
          );
      }
    });

    $('.layui-inline .layui-btn').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
    //搜索
    form.on("submit(searchForm)", function (data) {
        t.where = data.field;
        table.reload('customer-table', t);
        return false;
    });
});