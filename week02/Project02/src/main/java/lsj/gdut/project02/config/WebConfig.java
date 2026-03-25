package lsj.gdut.project02.config;

import lsj.gdut.project02.intercepters.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).excludePathPatterns("/Users/login","/Users/registers","/msg/**");
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 定义你的固定物理路径
        String filePath = "D:\\Java\\TrainCamp\\week02\\Project02\\msg\\";

        // 注册映射规则
        // 含义：当浏览器访问 http://localhost:8080/msg/xxx.jpg 时
        // 服务器会去读取上面 filePath 目录下的 xxx.jpg 文件并拼接路径
        registry.addResourceHandler("/msg/**")
                .addResourceLocations("file:" + filePath);
    }
}
