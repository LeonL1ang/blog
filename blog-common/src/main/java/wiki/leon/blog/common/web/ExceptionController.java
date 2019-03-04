package wiki.leon.blog.common.web;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import wiki.leon.blog.common.entity.BlogException;
import wiki.leon.blog.common.entity.Result;

@RestControllerAdvice
public class ExceptionController {

    // 拦截所有异常
    @ExceptionHandler(BlogException.class)
    public Result exceptionHandler(BlogException e){
        return Result.to(e.getStatusEnum());
    }

}
