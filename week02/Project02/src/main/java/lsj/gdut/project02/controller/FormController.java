package lsj.gdut.project02.controller;

import lsj.gdut.project02.Utils.ResultJson;
import lsj.gdut.project02.Utils.ThreadLocalUtil;
import lsj.gdut.project02.pojo.Form;
import lsj.gdut.project02.pojo.User;
import lsj.gdut.project02.service.impl.FormServiceimpl;
import lsj.gdut.project02.service.impl.UserServiceimpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/Forms")
@CrossOrigin // 开启跨域：前端（可能）从不同源访问 /Forms 接口
public class FormController {
    private static final Logger log = LoggerFactory.getLogger(FormController.class);

    @Autowired
    private UserServiceimpl userServiceimpl;

    @Autowired
    private FormServiceimpl formServiceimpl;

    //创建报修单
    @PostMapping("/insert")
    public ResultJson<String> insert(@RequestParam("device_type") String device_type,
                                      @RequestParam("description") String description,
                                      @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        //user相关信息从token中获取
        Map<String,Object> claims = ThreadLocalUtil.get();
        String userid = claims.get("userid").toString();
        User user = userServiceimpl.SelectUserById(userid);
        String dormInfo = user == null ? null : user.getDorm_info();

        log.info("用户 {} 创建报修单 - 设备类型: {}, 宿舍: {}", userid, device_type, dormInfo);

        //上传图片
        String img_url = null;
        if(file != null){
            String originalfileName = file.getOriginalFilename();
            //生成随机前缀
            String fileName = UUID.randomUUID()+originalfileName.substring(originalfileName.lastIndexOf("."));
            file.transferTo(new File("D:\\Java\\TrainCamp\\week02\\Project02\\msg\\"+ fileName));
            img_url = "http://localhost:8080/msg/"+fileName;
            log.debug("用户 {} 上传报修单图片: {}", userid, fileName);
            formServiceimpl.InsertForm(userid,dormInfo,device_type,description,img_url);
        }else{
            log.debug("用户 {} 创建报修单 - 无图片上传", userid);
            formServiceimpl.InsertForm(userid,dormInfo,device_type,description,null);
        }
        log.info("用户 {} 报修单创建成功", userid);
        return ResultJson.success("success");
    }

    //查看报修记录
    @GetMapping("/selectForms")
    public ResultJson<List<Form>> selectForms(){
        Map<String,Object> claims = ThreadLocalUtil.get();
        String userid = claims.get("userid").toString();
        log.debug("用户 {} 查询个人报修单列表", userid);
        List<Form> list = formServiceimpl.SelectAll(userid);
        return ResultJson.success(list);
    }

    //删除报修单
    @DeleteMapping("/deleteForm")
    public ResultJson<String> deleteForm(@RequestBody Map<String,Integer> map){
        Integer id = map.get("id");
        Map<String,Object> claims = ThreadLocalUtil.get();
        String userid = claims.get("userid").toString();
        log.info("用户 {} 删除报修单 - ID: {}", userid, id);
        formServiceimpl.DeleteForm(id);
        return ResultJson.success("success");
    }

    //查看报修单
    @GetMapping("/selectAllForms")
    public ResultJson<List<Form>> selectAllForms(){
        Map<String,Object> claims = ThreadLocalUtil.get();
        String userid = claims.get("userid").toString();
        log.debug("用户 {} (可能为维修人员) 查询全部报修单列表", userid);
        List<Form> list = formServiceimpl.SelectAll();
        return ResultJson.success(list);
    }

    //查看单个报修单详细
    @GetMapping("/selectFormsByStatus")
    public ResultJson<List<Form>> selectFormsByStatus(){
        Map<String,Object> claims = ThreadLocalUtil.get();
        String userid = claims.get("userid").toString();
        log.debug("用户 {} 查询待维修报修单列表", userid);
        List<Form> list = formServiceimpl.SelectByStatus();
        return ResultJson.success(list);
    }

    // 查看报修单详细（按 id）
    @GetMapping("/selectById")
    public ResultJson<Form> selectById(@RequestParam("id") Integer id){
        Map<String,Object> claims = ThreadLocalUtil.get();
        String userid = claims.get("userid").toString();
        log.debug("用户 {} 查询报修单详情 - ID: {}", userid, id);
        Form form = formServiceimpl.SelectById(id);
        return ResultJson.success(form);
    }

    //更新报修单状态
    @PatchMapping("/updateStatus")
    public ResultJson<String> updateStatus(@RequestBody Map<String,Integer> map){
        //报修单id
        Integer id = map.get("id");
        //维修中-1，已完成-2
        Integer status = map.get("status");
        Map<String,Object> claims = ThreadLocalUtil.get();
        String userid = claims.get("userid").toString();
        
        String statusName = status == 1 ? "维修中" : (status == 2 ? "已完成" : "未知");
        log.info("用户 {} 更新报修单状态 - ID: {}, 新状态: {}", userid, id, statusName);
        
        //更新时间
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");
        String upodate_time = now.format(formatter);
        Form form = new Form();
        form.setId(id);
        form.setStatus(status);
        form.setUpdate_time(upodate_time);
        formServiceimpl.UpdateForm(form);
        return ResultJson.success("修改成功");
    }

}
