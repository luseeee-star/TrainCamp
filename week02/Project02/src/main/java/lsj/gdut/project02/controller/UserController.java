package lsj.gdut.project02.controller;

import lsj.gdut.project02.DTOs.LoginDTO;
import lsj.gdut.project02.DTOs.RegisterDTO;
import lsj.gdut.project02.Utils.JwtUtil;
import lsj.gdut.project02.Utils.ResultJson;
import lsj.gdut.project02.Utils.ThreadLocalUtil;
import lsj.gdut.project02.pojo.User;
import lsj.gdut.project02.service.impl.UserServiceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/Users")
public class UserController {
    /*
    用户界面一共有登录注册两个功能，那么就设计两个函数
    一个是get查询
    一个是post添加
     */
    @Autowired
    private UserServiceimpl userServiceimpl;
    //------------------------主页面登录-----------------------------
    //登录
    @PostMapping("/login")
    public ResultJson<String> Login(@RequestBody LoginDTO logindto){
        String userid = logindto.getUserid();
        String password = logindto.getPassword();
        User user = userServiceimpl.SelectUser(userid,password);
        if(user!=null){
            //登陆成功，存放相关数据
            Map<String,Object> map = new HashMap<>();
            map.put("userid",userid);
            map.put("status",user.getRole());
            String token = JwtUtil.createToken(map);

            return ResultJson.success(token);
        }else{
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
        boolean Ifsuccess =  userServiceimpl.RegisterUser(role,userid,password1,password2);
        if(Ifsuccess){
            return ResultJson.success();
        }else{
            return ResultJson.error("注册失败");
        }
    }

    @GetMapping("/userinfo")
    public ResultJson<User> userinfo(){
        Map<String,Object> map = ThreadLocalUtil.get();
        String userid = map.get("userid").toString();
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
        if(!StringUtils.hasText(oldpassword)||!StringUtils.hasText(newpassword)||!StringUtils.hasText(repassword)){
            return ResultJson.error("值不能为空");
        }
        User user = userServiceimpl.SelectUserById(userid);
        if(!user.getPassword().equals(oldpassword)){
            return ResultJson.error("原密码错误");
        }
        if (!newpassword.equals(repassword)) {
            return ResultJson.error("两个密码不一致");
        }
        User newuser = new User();
        newuser.setUserid(userid);
        newuser.setPassword(newpassword);
        userServiceimpl.UpdateUser(newuser);
        return ResultJson.success("修改成功");
    }
    //绑定/修改宿舍
    @PatchMapping("/updateDorm")
    public ResultJson<String> updateDorm(@RequestBody Map<String,String> map){
        Map<String,Object> claims = ThreadLocalUtil.get();
        String userid = claims.get("userid").toString();
        User user = new User();
        user.setUserid(userid);
        String dormInfo = map.get("dormInfo");
        user.setDorm_info(dormInfo);
        userServiceimpl.UpdateUser(user);
        return ResultJson.success("修改成功");
    }
}
