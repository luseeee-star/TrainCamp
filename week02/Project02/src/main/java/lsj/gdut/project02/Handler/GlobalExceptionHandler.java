package lsj.gdut.project02.Handler;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;
@RestControllerAdvice // 标记这个类是一个[全局控制器增强器】，它会监听【整个应用中所有 @RestController 抛出的异常】
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)// 强制将【HTTP态码】设置为【480 Bad Request来明确告诉客户端/前端"请求参数有误”】
    @ExceptionHandler(MethodArgumentNotValidException.class)
    // 指定【这个方法】专门处理【MethodArgumentNotValidException类型的异常(即参数验证失抛出的异常) 】
    public Map<String, String> handlevalidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors()//获取所有验证错
            .forEach(error->{
                    String fieldName = ((FieldError) error).getField();// 获取出错的字段名 (如 "username")
                    String errorMessage=error.getDefaultMessage();    // 获取咱们在注解中定义的错误信息(如"用户名不能为空")
                    errors.put(fieldName,errorMessage);    // 将这些信息组装成 [Map<String,String>(键值对)】
                });
        return errors;// Spring会自动将 【Map<String,String>(键值对)】 转换为Json返回给客户端。
    }
}