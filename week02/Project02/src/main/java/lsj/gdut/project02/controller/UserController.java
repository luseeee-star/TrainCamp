package lsj.gdut.project02.controller;

import lsj.gdut.project02.DTOs.LoginDTO;
import lsj.gdut.project02.DTOs.RegisterDTO;
import lsj.gdut.project02.Utils.JwtUtil;
import lsj.gdut.project02.Utils.ResultJson;
import lsj.gdut.project02.Utils.ThreadLocalUtil;
import lsj.gdut.project02.pojo.User;
import lsj.gdut.project02.service.impl.UserServiceimpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/Users")
@CrossOrigin //开启跨域
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserServiceimpl userServiceimpl;
    
    //------------------------主页面登录-----------------------------
    //登录
    @PostMapping("/login")
    public ResultJson<String> Login(@RequestBody LoginDTO logindto){
        String userid = logindto.getUserid();
        String password = logindto.getPassword();
        log.info("用户 {} 尝试登录", userid);
        User user = userServiceimpl.SelectUser(userid,password);
        if(user!=null){
            //登陆成功，存放相关数据
            Map<String,Object> map = new HashMap<>();
            map.put("userid",userid);
            map.put("status",user.getRole());
            String token = JwtUtil.createToken(map);
            log.info("用户 {} 登录成功", userid);
            return ResultJson.success(token);
        }else{
            log.warn("用户 {} 登录失败 - 账号或密码错误", userid);
            return ResultJson.error("账号或密码错误");
        }
    }

    //注册
    @PostMapping("/registers")
    public ResultJson<String> register(@RequestBody RegisterDTO registerdto){
        Integer role = registerdto.getRole();
        String userid = registerdto.getUserid();
        String password1 = registerdto.getPassword1();
        String password2 = registerdto.getPassword2();
        log.info("用户 {} 尝试注册，角色: {}", userid, role == 0 ? "学生" : "维修人员");
        ResultJson<String> result = userServiceimpl.RegisterUser(role,userid,password1,password2);
        if(result.getCode() == 200) {
            log.info("用户 {} 注册成功", userid);
        } else {
            log.warn("用户 {} 注册失败 - {}", userid, result.getMsg());
        }
        return result;
    }

    @GetMapping("/userinfo")
    public ResultJson<User> userinfo(){
        Map<String,Object> map = ThreadLocalUtil.get();
        String userid = map.get("userid").toString();
        log.debug("用户 {} 查询个人信息", userid);
        User user = userServiceimpl.SelectUserById(userid);
        return ResultJson.success(user);

    }

    @PatchMapping("/updatePwd")
    public ResultJson<String> updatePwd(@RequestBody Map<String,String> map){
        Map<String,Object> claims = ThreadLocalUtil.get();
        String userid = claims.get("userid").toString();
        String oldpassword = map.get("oldpassword");
        String newpassword = map.get("newpassword");
        String repassword = map.get("repassword");
        log.info("用户 {} 尝试修改密码", userid);
        if(!StringUtils.hasText(oldpassword)||!StringUtils.hasText(newpassword)||!StringUtils.hasText(repassword)){
            log.warn("用户 {} 修改密码失败 - 参数为空", userid);
            return ResultJson.error("值不能为空");
        }
        User user = userServiceimpl.SelectUserById(userid);
        if(!user.getPassword().equals(oldpassword)){
            log.warn("用户 {} 修改密码失败 - 原密码错误", userid);
            return ResultJson.error("原密码错误");
        }
        if (!newpassword.equals(repassword)) {
            log.warn("用户 {} 修改密码失败 - 新密码不一致", userid);
            return ResultJson.error("两个密码不一致");
        }
        User newuser = new User();
        newuser.setUserid(userid);
        newuser.setPassword(newpassword);
        userServiceimpl.UpdateUser(newuser);
        log.info("用户 {} 修改密码成功", userid);
        return ResultJson.success("修改成功");
    }
    
    //绑定/修改宿舍
    @PatchMapping("/updateDorm")
    public ResultJson<String> updateDorm(@RequestBody Map<String,String> map){
        Map<String,Object> claims = ThreadLocalUtil.get();
        String userid = claims.get("userid").toString();
        String dormInfo = map.get("dormInfo");
        log.info("用户 {} 尝试绑定/修改宿舍: {}", userid, dormInfo);
        User user = new User();
        user.setUserid(userid);
        user.setDorm_info(dormInfo);
        //判断用户宿舍格式是否合法
        if (user.getDorm_info().matches("^[西东][0-1]\\d-\\d{3}")) {
            //第一次登录，绑定宿舍
            userServiceimpl.UpdateUser(user);
            log.info("用户 {} 宿舍绑定成功: {}", userid, dormInfo);
            return ResultJson.success("更新成功");
        } else {
            log.warn("用户 {} 宿舍绑定失败 - 宿舍格式有误: {}", userid, dormInfo);
            return ResultJson.error("宿舍格式有误");
        }
    }
}
