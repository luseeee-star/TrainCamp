package LSJ.QG.Pojo;

public class User {
    private Integer id;
    String userid;
    String password;
    int role;//0是学生，1是维修员
    String dorm_info;


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
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取
     * @return role
     */
    public int getRole() {
        return role;
    }

    /**
     * 设置
     * @param role
     */
    public void setRole(int role) {
        this.role = role;
    }

    /**
     * 获取
     * @return dorm_info
     */
    public String getDorm_info() {
        return dorm_info;
    }

    /**
     * 设置
     * @param dorm_info
     */
    public void setDorm_info(String dorm_info) {
        this.dorm_info = dorm_info;
    }

    public String toString() {
        return "User{userid = " + userid + ", password = " + password + ", role = " + role + ", dorm_info = " + dorm_info + "}";
    }
}
