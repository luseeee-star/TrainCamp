package LSJ.QG.Services;

import LSJ.QG.Mapper.FormMapper;
import LSJ.QG.Pojo.Form;
import LSJ.QG.Utils.SQLSessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class FormService {
    SqlSessionFactory sqlSessionFactory = SQLSessionFactoryUtil.getSqlSessionFactory();
    //运用方法重载实现管理员端查询所有表以及学生查询自己的表
    public List<Form> SelectAll(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        FormMapper formMapper = sqlSession.getMapper(FormMapper.class);
        List<Form> list = formMapper.SelectAll(null);
        sqlSession.close();
        return list;
    }
    public List<Form> SelectAll(String userid){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        FormMapper formMapper = sqlSession.getMapper(FormMapper.class);
        List<Form> list = formMapper.SelectAll(userid);
        sqlSession.close();
        return list;
    }
    public List<Form> SelectByStatus(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        FormMapper formMapper = sqlSession.getMapper(FormMapper.class);
        List<Form> list = formMapper.SelectByStatus();
        sqlSession.close();
        return list;
    }
    public Form SelectById(Integer id){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        FormMapper formMapper = sqlSession.getMapper(FormMapper.class);
        Form form = formMapper.SelectById(id);
        sqlSession.close();
        return form;
    }

    //新建保修单
    public void InsertForm(Form form){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        FormMapper formMapper = sqlSession.getMapper(FormMapper.class);
        formMapper.InsertForm(form);
        sqlSession.commit();
        sqlSession.close();

    }

    public void UpdateForm(Form form){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        FormMapper formMapper = sqlSession.getMapper(FormMapper.class);
        formMapper.UpdateForm(form);
        sqlSession.commit();
        sqlSession.close();
    }

    public void DeleteForm(Integer id){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        FormMapper formMapper = sqlSession.getMapper(FormMapper.class);
        formMapper.DeleteForm(id);
        sqlSession.commit();
        sqlSession.close();
    }
}
