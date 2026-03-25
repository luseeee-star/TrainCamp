package lsj.gdut.project02.service;

import lsj.gdut.project02.pojo.User;

public interface UserService {
     User SelectUser(String userid, String password);
     User SelectUserById(String userid);
     void UpdateUser(User user);
     boolean RegisterUser(Integer role,String userid,String password1,String password2);
     boolean SetDormInfo(User user,String dormInfo);
}
