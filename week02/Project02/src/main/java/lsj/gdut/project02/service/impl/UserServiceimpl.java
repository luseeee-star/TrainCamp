package lsj.gdut.project02.service.impl;

import lsj.gdut.project02.Utils.ResultJson;
import lsj.gdut.project02.mapper.UserMapper;
import lsj.gdut.project02.pojo.User;
import lsj.gdut.project02.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceimpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceimpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public User SelectUser(String userid, String password){
        log.debug("查询用户: {}", userid);
        return userMapper.SelectUser(userid,password);
    }

    @Override
    public User SelectUserById(String userid) {
        log.debug("按ID查询用户: {}", userid);
        return userMapper.SelectUserById(userid);
    }

    @Override
    @Transactional //成功则自动提交事务，否则回滚事务
    public void UpdateUser(User user){
        log.info("更新用户信息: {}", user.getUserid());
        userMapper.UpdateUser(user);
    }

    @Override
    @Transactional
    public ResultJson<String> RegisterUser(Integer role, String userid, String password1, String password2){
        Integer UserType = role;
        User user = new User();
        User user1 = userMapper.SelectUserById(userid);
        if(user1 != null){
            log.warn("注册失败 - 用户已存在: {}", userid);
            return ResultJson.error("用户已存在");
        }
        boolean isLegal = false;//判断学号or工号是否合法
        //如果用户是学生
        if (UserType == 0) {
            isLegal = userid.matches("3[1-2]25\\d{6}");
        }//如果是维修人员
        else if (UserType == 1) {
            isLegal = userid.matches("0025\\d{6}");
        }
        
        if(!isLegal) {
            log.warn("注册失败 - 学号/工号不合法: {}, 角色: {}", userid, UserType);
            return ResultJson.error("学号不合法");
        }
        
        if(!password1.equals(password2)){
            log.warn("注册失败 - 密码不一致: {}", userid);
            return ResultJson.error("密码不一致");
        }
        
        //密码一致则注册
        user.setUserid(userid);
        user.setPassword(password1);
        user.setRole(UserType);
        user.setDorm_info(null);
        userMapper.InsertUser(user);
        log.info("用户注册成功: {}, 角色: {}", userid, UserType == 0 ? "学生" : "维修人员");
        return ResultJson.success("注册成功");
    }

    @Override
    public boolean SetDormInfo(User user,String dormInfo){
        //判断是否绑定宿舍
        if(user.getDorm_info() == null) {
            log.info("用户 {} 首次绑定宿舍", user.getUserid());
        } else {
            log.info("用户 {} 修改宿舍信息", user.getUserid());
        }
        //判断宿舍信息合不合法
        boolean isLegal = dormInfo.matches("^[西东][0-1]\\d-\\d{3}");
        if (isLegal) {
            //第一次登录，绑定宿舍
            user.setDorm_info(dormInfo);
            userMapper.UpdateUser(user);
            log.info("用户 {} 宿舍信息更新成功: {}", user.getUserid(), dormInfo);
            return true;
        } else {
            log.warn("宿舍信息格式不合法: {} 用户: {}", dormInfo, user.getUserid());
            return false;
        }
    }
}
