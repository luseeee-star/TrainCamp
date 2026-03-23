package lsj.gdut.project02.DTOs;


public class RegisterDTO {
    private Integer role;
    private String userid;
    private String password1;
    private String password2;

    public RegisterDTO() {
    }

    public RegisterDTO(Integer role, String userid, String password1, String password2) {
        this.role = role;
        this.userid = userid;
        this.password1 = password1;
        this.password2 = password2;
    }

    /**
     * 获取
     * @return role
     */
    public Integer getRole() {
        return role;
    }

    /**
     * 设置
     * @param role
     */
    public void setRole(Integer role) {
        this.role = role;
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
     * @return password1
     */
    public String getPassword1() {
        return password1;
    }

    /**
     * 设置
     * @param password1
     */
    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    /**
     * 获取
     * @return password2
     */
    public String getPassword2() {
        return password2;
    }

    /**
     * 设置
     * @param password2
     */
    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String toString() {
        return "registerDTO{role = " + role + ", userid = " + userid + ", password1 = " + password1 + ", password2 = " + password2 + "}";
    }
}
