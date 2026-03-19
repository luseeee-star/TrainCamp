package lsj.gdut.project02.service;

import lsj.gdut.project02.pojo.User;

public interface UserServ {
    public User SelectUser(String userid, String password);
    public void UpdateUser(User user);
    public void registerUser(User user);
}
