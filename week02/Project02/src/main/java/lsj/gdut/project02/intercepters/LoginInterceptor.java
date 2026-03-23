package lsj.gdut.project02.intercepters;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lsj.gdut.project02.Utils.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler){
        String token = request.getHeader("Authorization");
        try {
            Map<String,Object> map = JwtUtil.verifyToken(token);
            return  true;
        } catch (Exception e) {
            response.setStatus(401);
            return false;
        }
    }

}
