package LSJ.QG.Utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class SQLSessionFactoryUtil {
    private static SqlSessionFactory sqlSessionFactory;
    static{
        //加载核心配置文件
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            //IO流读取xml文件
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //根据配置文件，创建出 MyBatis 的核心数据库会话工厂对象
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    public static SqlSessionFactory getSqlSessionFactory(){
        return sqlSessionFactory;
    }
}
