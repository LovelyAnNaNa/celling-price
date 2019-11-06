package com.whtt.cellingprice.exception;

import com.whtt.cellingprice.common.CommonResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Iterator;
import java.util.Set;

/**
 * 捕获校验异常
 * @Date 2019.09.17
 * @Author weishilei
 */
@ControllerAdvice
public class ExceptionAdvice {

    //捕捉参数校验异常
    @ResponseBody
    @ExceptionHandler(value = ConstraintViolationException.class)
    public CommonResult ConstraintViolationExceptionHandler(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        Iterator<ConstraintViolation<?>> iterator = constraintViolations.iterator();
        while (iterator.hasNext()) {
            return CommonResult.failed(iterator.next().getMessageTemplate());
        }

        return CommonResult.failed();
    }
}