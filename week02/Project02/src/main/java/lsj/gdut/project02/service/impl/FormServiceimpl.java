package lsj.gdut.project02.service.impl;

import lsj.gdut.project02.mapper.FormMapper;
import lsj.gdut.project02.pojo.Form;
import lsj.gdut.project02.service.FormService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class FormServiceimpl implements FormService {
    private static final Logger log = LoggerFactory.getLogger(FormServiceimpl.class);

    @Autowired
    FormMapper formMapper;

    //运用方法重载实现管理员端查询所有表以及学生查询自己的表
    @Override
    public List<Form> SelectAll(){
        log.debug("查询所有报修单");
        return formMapper.SelectAll(null);
    }

    @Override
    public List<Form> SelectAll(String userid){
        log.debug("查询用户 {} 的报修单", userid);
        return formMapper.SelectAll(userid);
    }

    @Override
    public List<Form> SelectByStatus(){
        log.debug("查询待维修报修单");
        return formMapper.SelectByStatus();
    }

    @Override
    public Form SelectById(Integer id){
        log.debug("查询报修单详情 - ID: {}", id);
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
        
        log.info("创建新报修单 - 用户: {}, 设备: {}, 宿舍: {}, 时间: {}", userid, device_type, dorm_info, upodate_time);
        formMapper.InsertForm(form);
        log.debug("报修单插入数据库成功 - 用户: {}", userid);
    }

    @Override
    @Transactional
    public void UpdateForm(Form form){
        log.info("更新报修单 - ID: {}, 新状态: {}", form.getId(), form.getStatus());
        formMapper.UpdateForm(form);
        log.debug("报修单更新成功 - ID: {}", form.getId());
    }

    @Override
    @Transactional
    public void DeleteForm(Integer id){
        log.info("删除报修单 - ID: {}", id);
        formMapper.DeleteForm(id);
        log.debug("报修单删除成功 - ID: {}", id);
    }
}
