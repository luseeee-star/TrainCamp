package LSJ.QG.Mapper;

import LSJ.QG.Pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    User SelectUser(@Param("userid")String userid,@Param("password")String password);
    void InsertUser(User user);
    void UpdateUser(User user);
}
