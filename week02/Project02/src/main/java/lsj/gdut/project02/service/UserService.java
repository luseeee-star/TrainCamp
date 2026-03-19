package lsj.gdut.project02.service;

import lsj.gdut.project02.mapper.UserMapper;
import lsj.gdut.project02.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserServ{
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

    @Override
    @Transactional
    public void registerUser(User user){
        user.setDorm_info(null);
        userMapper.InsertUser(user);
    }
}
