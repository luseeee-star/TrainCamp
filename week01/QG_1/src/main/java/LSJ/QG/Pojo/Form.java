package LSJ.QG.Pojo;

public class Form {
    private Integer id;
    String userid;
    String device_type;
    String description;
    private Integer status;//0是未维修，1是维修中，2是已完成
    String update_time;

    public Integer getId() {
        return id;
    }

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

    public String toString() {
        return "From{id = " + id + ", userid = " + userid + ", device_type = " + device_type + ", description = " + description + ", status = " + status + ", update_time = " + update_time + "}";
    }
}
