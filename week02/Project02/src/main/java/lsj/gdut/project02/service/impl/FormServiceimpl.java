package lsj.gdut.project02.service.impl;

import lsj.gdut.project02.mapper.FormMapper;
import lsj.gdut.project02.pojo.Form;
import lsj.gdut.project02.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class FormServiceimpl implements FormService {
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
    public void InsertForm(String userid,String dorm_info,String device_type,String description,String img_url){
        //通过时间库来获取当前时间
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");
        String upodate_time = now.format(formatter);
        Form form = new Form();
        form.setUserid(userid);
        form.setDorm_info(dorm_info);
        form.setDevice_type(device_type);
        form.setDescription(description);
        //0是未维修，1是维修中，2是已完成
        form.setStatus(0);
        form.setUpdate_time(upodate_time);
        form.setImg_url(img_url);
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
