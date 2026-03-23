package lsj.gdut.project02.service.impl;

import lsj.gdut.project02.mapper.UserMapper;
import lsj.gdut.project02.pojo.User;
import lsj.gdut.project02.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceimpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User SelectUser(String userid, String password){
        return userMapper.SelectUser(userid,password);
    }

    @Override
    @Transactional //成功则自动提交事务，否则回滚事务
    public void UpdateUser(User user){
        userMapper.UpdateUser(user);
    }


    @Transactional
    public void registerUser(User user){

        user.setDorm_info(null);
        userMapper.InsertUser(user);
    }
    @Override
    @Transactional
    public boolean RegisterUser(Integer role,String userid,String password1,String password2){
        int UserType = role;
        User user = new User();
        boolean isLegal = false;//判断学号or工号是否合法
        //如果用户是学生
        if (UserType == 0) {
            System.out.println("请输入10位学号（前缀3125或3225）：");
            isLegal = userid.matches("3[1-2]25\\d{6}");
        }//如果是维修人员
        else if (UserType == 1) {
            System.out.println("请输入10位工号（前缀0025）：");
            isLegal = userid.matches("0025\\d{6}");
        }
        if(isLegal){
            if(password1.equals(password2)){
                //如果密码一致则注册
                user.setUserid(userid);
                user.setPassword(password1);
                user.setRole(UserType);
                user.setDorm_info(null);
                userMapper.InsertUser(user);
                return true;
            }else {
                System.out.println("密码不一致！");
                return false;
            }
        }else{
            System.out.println("学号不合法");
            return false;
        }
    }

    @Override
    public boolean SetDormInfo(User user,String dormInfo){
        //判断是否绑定宿舍
        if(user.getDorm_info() ==null) {
            System.out.println("请输入绑定宿舍信息：栋-房间号，如：01-101");
        }else{
            System.out.println("请输入新宿舍信息：栋-房间号，如：01-101");
        }
        //判断宿舍信息合不合法
        boolean isLegal = dormInfo.matches("[0-1]\\d-\\d{3}");
        if (isLegal) {
            //第一次登录，绑定宿舍
            user.setDorm_info(dormInfo);
            userMapper.UpdateUser(user);
            return true;
        } else {
            System.out.println("宿舍不存在");
            return false;
        }
    }
}
