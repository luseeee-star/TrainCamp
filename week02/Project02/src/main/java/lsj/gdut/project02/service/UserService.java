package lsj.gdut.project02.service;

import lsj.gdut.project02.pojo.User;

public interface UserService {
    public User SelectUser(String userid, String password);
    public void UpdateUser(User user);
    public boolean RegisterUser(Integer role,String userid,String password1,String password2);
    public boolean SetDormInfo(User user,String dormInfo);
}
