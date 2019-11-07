package com.whtt.cellingprice.controller;

import com.whtt.cellingprice.common.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: wbh
 * @Date: 1019/11/6 15:19
 * @Description:
 */
@RestController
@RequestMapping("/test")
public class TestController {

    /**
     * 提供微信测试是否正常接口
     * @return
     */
    @GetMapping
    public CommonResult test() {
        return CommonResult.success();
    }

}
