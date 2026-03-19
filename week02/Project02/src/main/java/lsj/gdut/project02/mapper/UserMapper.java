package lsj.gdut.project02.mapper;

import lsj.gdut.project02.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User SelectUser(@Param("userid")String userid,@Param("password")String password);
    void InsertUser(User user);
    void UpdateUser(User user);
}
