package lsj.gdut.project02.controller;

import lsj.gdut.project02.Utils.ResultJson;
import lsj.gdut.project02.Utils.ThreadLocalUtil;
import lsj.gdut.project02.pojo.Form;
import lsj.gdut.project02.pojo.User;
import lsj.gdut.project02.service.impl.FormServiceimpl;
import lsj.gdut.project02.service.impl.UserServiceimpl;
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
    @Autowired
    private UserServiceimpl userServiceimpl;

    @Autowired
    private FormServiceimpl formServiceimpl;

    //创建报修单
    @PostMapping("/insert")
    public ResultJson<String> insert(@RequestParam("device_type") String device_type,
                                      @RequestParam("description") String description,
                                      @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        Map<String,Object> claims = ThreadLocalUtil.get();
        String userid = claims.get("userid").toString();

        //上传图片
        if(file != null){
            String originalfileName = file.getOriginalFilename();
            String fileName = UUID.randomUUID()+originalfileName.substring(originalfileName.lastIndexOf("."));
            file.transferTo(new File("D:\\Java\\TrainCamp\\week02\\Project02\\msg\\"+ fileName));
            String img_url = null;
            img_url = "localhost:8080/msg/"+fileName;

            formServiceimpl.InsertForm(userid,device_type,description,img_url);
        }else{
            formServiceimpl.InsertForm(userid,device_type,description,null);
        }
        return ResultJson.success("success");
    }

    //查看报修记录
    @GetMapping("/selectForms")
    public ResultJson<List<Form>> selectForms(){
        Map<String,Object> claims = ThreadLocalUtil.get();
        String userid = claims.get("userid").toString();
        List<Form> list = formServiceimpl.SelectAll(userid);
        return ResultJson.success(list);
    }

    //删除报修单
    @DeleteMapping("/deleteForm")
    public ResultJson<String> deleteForm(@RequestBody Map<String,Integer> map){
        Integer id = map.get("id");
        formServiceimpl.DeleteForm(id);
        return ResultJson.success("success");
    }

    //查看报修单
    @GetMapping("/selectAllForms")
    public ResultJson<List<Form>> selectAllForms(){
        List<Form> list = formServiceimpl.SelectAll();
        return ResultJson.success(list);
    }
    //查看单个报修单详细
    @GetMapping("/selectFormsByStatus")
    public ResultJson<List<Form>> selectFormsByStatus(){
        List<Form> list = formServiceimpl.SelectByStatus();
        return ResultJson.success(list);
    }

    // 查看报修单详细（按 id）
    @GetMapping("/selectById")
    public ResultJson<Form> selectById(@RequestParam("id") Integer id){
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
