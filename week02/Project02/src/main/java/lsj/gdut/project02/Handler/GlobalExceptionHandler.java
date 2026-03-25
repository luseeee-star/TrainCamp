package lsj.gdut.project02.Handler;

import lsj.gdut.project02.Utils.ResultJson;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice // 标记这个类是一个[全局控制器增强器】，它会监听【整个应用中所有 @RestController 抛出的异常】
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResultJson handleException(Exception e) {
        e.printStackTrace();
        return ResultJson.error(StringUtils.hasLength(e.getMessage()) ? e.getMessage() : "操作失败");
    }
}