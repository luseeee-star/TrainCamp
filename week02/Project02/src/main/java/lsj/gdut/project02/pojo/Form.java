package lsj.gdut.project02.pojo;

public class Form {
    private Integer id;
    String userid;
    String device_type;
    String description;
    private Integer status;//0是未维修，1是维修中，2是已完成
    String update_time;
    String img_url;


    public Form() {
    }

    public Form(Integer id, String userid, String device_type, String description, Integer status, String update_time, String img_url) {
        this.id = id;
        this.userid = userid;
        this.device_type = device_type;
        this.description = description;
        this.status = status;
        this.update_time = update_time;
        this.img_url = img_url;
    }

    /**
     * 获取
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取
     * @return userid
     */
    public String getUserid() {
        return userid;
    }

    /**
     * 设置
     * @param userid
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * 获取
     * @return device_type
     */
    public String getDevice_type() {
        return device_type;
    }

    /**
     * 设置
     * @param device_type
     */
    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    /**
     * 获取
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取
     * @return status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取
     * @return update_time
     */
    public String getUpdate_time() {
        return update_time;
    }

    /**
     * 设置
     * @param update_time
     */
    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    /**
     * 获取
     * @return img_url
     */
    public String getImg_url() {
        return img_url;
    }

    /**
     * 设置
     * @param img_url
     */
    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String toString() {
        return "Form{id = " + id + ", userid = " + userid + ", device_type = " + device_type + ", description = " + description + ", status = " + status + ", update_time = " + update_time + ", img_url = " + img_url + "}";
    }
}
