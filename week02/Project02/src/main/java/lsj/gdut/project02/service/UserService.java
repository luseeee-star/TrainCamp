package lsj.gdut.project02.service;

import lsj.gdut.project02.Utils.ResultJson;
import lsj.gdut.project02.pojo.User;

public interface UserService {
     User SelectUser(String userid, String password);
     User SelectUserById(String userid);
     void UpdateUser(User user);
     ResultJson<String> RegisterUser(Integer role, String userid, String password1, String password2);
     boolean SetDormInfo(User user,String dormInfo);
}
