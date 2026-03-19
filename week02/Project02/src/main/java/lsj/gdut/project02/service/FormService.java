package lsj.gdut.project02.service;

import lsj.gdut.project02.mapper.FormMapper;
import lsj.gdut.project02.pojo.Form;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FormService implements FormServ{
    @Autowired
    FormMapper formMapper;

    //运用方法重载实现管理员端查询所有表以及学生查询自己的表
    @Override
    public List<Form> SelectAll(){
        return formMapper.SelectAll(null);
    }
    @Override
    public List<Form> SelectAll(String userid){
        return formMapper.SelectAll(userid);
    }
    @Override
    public List<Form> SelectByStatus(){
        return formMapper.SelectByStatus();
    }
    @Override
    public Form SelectById(Integer id){
        return formMapper.SelectById(id);
    }

    @Override
    @Transactional
    //新建保修单
    public void InsertForm(Form form){
        formMapper.InsertForm(form);
    }

    @Override
    @Transactional
    public void UpdateForm(Form form){
        formMapper.UpdateForm(form);
    }

    @Override
    @Transactional
    public void DeleteForm(Integer id){
        formMapper.DeleteForm(id);
    }
}
