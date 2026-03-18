package LSJ.QG.Services;

import LSJ.QG.Mapper.UserMapper;
import LSJ.QG.Pojo.User;
import LSJ.QG.Utils.SQLSessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class UserService {
    SqlSessionFactory sqlSessionFactory = SQLSessionFactoryUtil.getSqlSessionFactory();

    public User SelectUser(String userid, String password){

        //获取sqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.SelectUser(userid,password);
        sqlSession.close();
        return user;
    }
    public void UpdateUser(User user){

        //获取sqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        userMapper.UpdateUser(user);
        //提交事务
        sqlSession.commit();

        sqlSession.close();
    }
    //注册方法
    public void registerUser(User user){
        user.setDorm_info(null);

        //获取sqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        userMapper.InsertUser(user);
        //提交事务
        sqlSession.commit();

        sqlSession.close();

    }
}
