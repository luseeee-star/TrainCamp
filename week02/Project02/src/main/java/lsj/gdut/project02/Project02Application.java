package lsj.gdut.project02;

import jakarta.annotation.PostConstruct;
import lsj.gdut.project02.pojo.Form;
import lsj.gdut.project02.pojo.User;
import lsj.gdut.project02.service.impl.FormServiceimpl;
import lsj.gdut.project02.service.impl.UserServiceimpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class Project02Application {

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context =SpringApplication.run(Project02Application.class, args);

        UserServiceimpl userServiceimpl = context.getBean(UserServiceimpl.class);
        FormServiceimpl formServiceimpl = context.getBean(FormServiceimpl.class);

    }

}
