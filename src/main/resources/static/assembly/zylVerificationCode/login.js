layui.use(['carousel', 'form','jquery'], function(){
    var carousel = layui.carousel
        ,form = layui.form,
    $ = layui.jquery;
    //自定义验证规则
    form.verify({
        userName: function(value){
            if(value.length < 1){
                return '账号至少得3个字符';
            }
        }
        ,pass: [/^[\S]{6,12}$/,'密码必须6到12位，且不能出现空格']
        ,vercodes: function(value){
            //获取验证码
            var zylVerCode = $(".zylVerCode").html();
            if(value!=zylVerCode){
                return '验证码错误（区分大小写）';
            }
        }
        ,content: function(value){
            layedit.sync(editIndex);
        },

    });

    //监听提交
    form.on('submit(demo1)', function(data){
            $.ajax({
                url: "/sysUser/login",
                    type: "post",
                    data: {
                      username:$("#user").val(),
                      password:$("#pwd").val()
                    },
                success: function (info) {
                    if (info.code==200) {
                        window.location.href="/sysUser/forget-password";
                    }else {
                        layer.msg(info.msg);
                    }
                },
                error:function(data){
                    alert("服务器繁忙");
                }
            });
        return false;
    });
    //设置轮播主体高度
    var zyl_login_height = $(window).height()/1.0;
    var zyl_car_height = $(".body").css("cssText","height:" + zyl_login_height + "px!important");


    //Login轮播主体
    carousel.render({
        elem: '#zyllogin'//指向容器选择器
        ,width: '100%' //设置容器宽度
        // ,height:'zyl_car_height'
        ,arrow: 'always' //始终显示箭头
        ,anim: 'fade' //切换动画方式
        ,autoplay: true //是否自动切换false true
        ,arrow: 'hover' //切换箭头默认显示状态||不显示：none||悬停显示：hover||始终显示：always
        ,indicator: 'none' //指示器位置||外部：outside||内部：inside||不显示：none
        ,interval: '5000' //自动切换时间:单位：ms（毫秒）
    });

    //监听轮播--案例暂未使用
    carousel.on('change(zyllogin)', function(obj){
        var loginCarousel = obj.index;
    });

    //粒子线条
    $(".body").jParticle({
        background: "url(/static/img/login.jpg)",
        color: "#fff",//粒子和连线的颜色
        particlesNumber:200,//粒子数量
        //disableLinks:true,//禁止粒子间连线
        //disableMouse:true,//禁止粒子间连线(鼠标)
        particle: {
            minSize: 1,//最小粒子
            maxSize: 5,//最大粒子
            speed: 50,//粒子的动画速度
        }
    });

});