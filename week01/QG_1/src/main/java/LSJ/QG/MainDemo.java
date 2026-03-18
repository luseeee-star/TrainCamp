package LSJ.QG;

import LSJ.QG.Pojo.Form;
import LSJ.QG.Pojo.User;
import LSJ.QG.Services.FormService;
import LSJ.QG.Services.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class MainDemo {
    /*
    注册insert不完全信息
    登录则需要再根据用户群体update其他信息
     */
    public static void main(String[] args) throws  InterruptedException {
        UserService userService = new UserService();
        String WindowsMain = "===========================\n" +
                "\uD83C\uDFE0 宿舍报修管理系统\n" +
                "===========================\n" +
                "1. 登录\n" +
                "2. 注册\n" +
                "3. 退出\n" +
                "请选择操作（输入 1-3）：";
        while (true) {
            System.out.println(WindowsMain);
            Scanner sc = new Scanner(System.in);
            String num = sc.nextLine();
            if (num.equals("1")) {
                Scanner sc1 = new Scanner(System.in);
                System.out.println("请输入学工号");
                String userid = sc1.nextLine();
                System.out.println("请输入密码");
                String password = sc1.nextLine();
                User user = userService.SelectUser(userid,password);
                if(user!=null){
                    //登录成功，判断角色
                    System.out.print("登陆成功！用户为：");
                    String regex = "3[1-2]25\\d{6}";
                    if(user.getUserid().matches(regex)){
                        System.out.println("学生");
                        Thread.sleep(500);
                        StudentMenu(user);
                    }else{
                        System.out.println("管理员");
                        Thread.sleep(500);
                        ManagerMenu(user);
                    }

                }else{
                    System.out.println("无用户数据，查询是否注册过");
                }
            }
            //注册功能，需要根据用户不同进行不同判定
            else if (num.equals("2")) {
                Scanner sc1 = new Scanner(System.in);
                System.out.println("===== 用户注册 =====\n" +
                        "请选择角色（0-学生，1-维修人员）：");
                int UserType = sc1.nextInt();
                sc1.nextLine();
                User user = new User();
                String userid = null;//存放学号or工号
                boolean isLegal = false;//判断学号or工号是否合法
                //如果用户是学生
                if (UserType == 0) {
                    System.out.println("请输入10位学号（前缀3125或3225）：");
                    userid = sc1.nextLine();
                    isLegal = userid.matches("3[1-2]25\\d{6}");
                }//如果是维修人员
                else if (UserType == 1) {
                    System.out.println("请输入10位工号（前缀0025）：");
                    userid = sc1.nextLine();
                    isLegal = userid.matches("0025\\d{6}");
                }
                if(isLegal){
                    System.out.println("请输入密码：");
                    String password1 = sc1.nextLine();
                    System.out.println("请确认密码：");
                    String password2 = sc1.nextLine();
                    if(password1.equals(password2)){
                        //如果密码一致则注册
                        user.setUserid(userid);
                        user.setPassword(password1);
                        user.setRole(UserType);
                        userService.registerUser(user);
                        System.out.println("注册成功，请返回主界面登录");
                    }else {
                        System.out.println("密码不一致！");
                    }
                }else{
                    System.out.println("学号不合法");
                }
            }else if (num.equals("3")) {
                System.out.println("正在退出...");
                Thread.sleep(1000);
                System.out.println("退出成功！");
                return;
            }
        }
    }

    public static void StudentMenu(User user) throws InterruptedException {
        UserService userService = new UserService();
        FormService formService = new FormService();
        String WindowStu = "===== 学生菜单 =====\n" +
                "1. 绑定/修改宿舍\n" +
                "2. 创建报修单\n" +
                "3. 查看我的报修记录\n" +
                "4. 取消报修单\n" +
                "5. 修改密码\n" +
                "6. 退出\n" +
                "请选择操作（输入 1-6）：";
        while (true) {
            System.out.println(WindowStu);
            Scanner sc = new Scanner(System.in);
            String choice = sc.nextLine();
            if(choice.equals("1")){
                Scanner sc1 = new Scanner(System.in);
                //判断是否绑定宿舍
                if(user.getDorm_info() ==null) {
                    System.out.println("请输入绑定宿舍信息：栋-房间号，如：01-101");
                }else{
                    System.out.println("请输入新宿舍信息：栋-房间号，如：01-101");
                }
                String dorm_info = sc1.nextLine();
                //判断宿舍信息合不合法
                boolean isLegal = dorm_info.matches("[0-1]\\d-\\d{3}");
                if (isLegal) {
                    //第一次登录，绑定宿舍
                    user.setDorm_info(dorm_info);
                    userService.UpdateUser(user);
                    //调出学生页面
                    System.out.println("宿舍信息成功更新");
                    Thread.sleep(300);
                } else {
                    System.out.println("宿舍不存在");
                }
            }else if(choice.equals("2")){
                Scanner sc1 = new Scanner(System.in);
                System.out.println("请输入报修设备类型");
                String device_type = sc1.nextLine();
                System.out.println("请输入详细问题描述");
                String description =  sc1.nextLine();
                //通过时间库来获取当前时间
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");
                String upodate_time = now.format(formatter);
                Form form = new Form();
                form.setUserid(user.getUserid());
                form.setDevice_type(device_type);
                form.setDescription(description);
                //0是未维修，1是维修中，2是已完成
                form.setStatus(0);
                form.setUpdate_time(upodate_time);
                formService.InsertForm(form);
                System.out.println("创建成功！");
                Thread.sleep(500);

            }else if(choice.equals("3")){
                String userid = user.getUserid();
                List<Form> forms = formService.SelectAll(userid);
                System.out.println("你的报修单详细如下");
                for(Form form:forms){
                    System.out.println(form);
                }
                Thread.sleep(500);
            }else if(choice.equals("4")){
                System.out.println("请输入要取消的报修单id");
                Integer id = Integer.parseInt(sc.nextLine());
                formService.DeleteForm(id);
                System.out.println("取消成功");
            }else if(choice.equals("5")){
                System.out.println("请输入原密码");
                Scanner sc1 = new Scanner(System.in);
                String prePassword = sc1.nextLine();
                //如果密码一致则可以修改
                if(user.getPassword().equals(prePassword)){
                    System.out.println("密码正确，请输入新的密码");
                    String password1 = sc1.nextLine();
                    System.out.println("请确认密码：");
                    String password2 = sc1.nextLine();
                    if(password1.equals(password2)){
                        //如果密码一致则可以添加
                        user.setPassword(password1);
                        userService.UpdateUser(user);
                    }
                }
            }else if(choice.equals("6")){
                System.out.println("正在退出...");
                Thread.sleep(1000);
                System.out.println("退出成功！");
                return;
            }
        }
    }

    public static void ManagerMenu(User user) throws InterruptedException {
        UserService userService = new UserService();
        FormService formService = new FormService();
        String WindowMana  ="===== 管理员菜单 =====\n" +
                "1. 查看报修单\n" +
                "2. 查看单个报修单详情\n" +
                "3. 更新报修单状态\n" +
                "4. 删除报修单\n" +
                "5. 修改密码\n" +
                "6. 退出\n" +
                "请选择操作（输入 1-6）：";
        while (true) {
            System.out.println(WindowMana);
            Scanner sc = new Scanner(System.in);
            String choice = sc.nextLine();
            if(choice.equals("1")){
                System.out.println("查询所有报修单输入a，未处理报修单输入b");
                Scanner sc1 = new Scanner(System.in);
                String SelectType = sc1.nextLine();
                if(SelectType.equals("a")){
                    List<Form> forms = formService.SelectAll();
                    if(forms.size()>0){
                        System.out.println("报修单详细如下");
                        for(Form form:forms){
                            System.out.println(form);
                        }
                    }else{
                        System.out.println("暂无信息！");
                    }
                    Thread.sleep(500);

                }else if(SelectType.equals("b")){
                    List<Form> forms = formService.SelectByStatus();
                    System.out.println("报修单详细如下");
                    for(Form form:forms){
                        System.out.println(form);
                    }
                    Thread.sleep(500);
                }else{
                    System.out.println("输入无效！");
                }
            }
            else if(choice.equals("2")){
                System.out.println("输入查询的报修单id");
                Integer id = Integer.parseInt(sc.nextLine());
                Form form = formService.SelectById(id);
                System.out.println("查询详情如下");
                System.out.println(form);
                Thread.sleep(500);
            }else if(choice.equals("3")){
                System.out.println("请输入要更新的报修单id");
                Integer id = Integer.parseInt(sc.nextLine());
                System.out.println("更新状态为:维修中-1，已完成-2");
                Scanner sc1 = new Scanner(System.in);
                Integer status = Integer.parseInt(sc.nextLine());
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");
                String upodate_time = now.format(formatter);
                Form form = new Form();
                form.setId(id);
                form.setStatus(status);
                form.setUpdate_time(upodate_time);
                formService.UpdateForm(form);
                System.out.println("更新成功！");
                Thread.sleep(500);

            }else if(choice.equals("4")){
                System.out.println("请输入要删除的报修单id");
                Integer id = Integer.parseInt(sc.nextLine());
                formService.DeleteForm(id);
                System.out.println("删除成功");
                Thread.sleep(500);
            }else if(choice.equals("5")){
                System.out.println("请输入原密码");
                Scanner sc1 = new Scanner(System.in);
                String prePassword = sc1.nextLine();
                //如果密码一致则可以修改
                if(user.getPassword().equals(prePassword)){
                    System.out.println("密码正确，请输入新的密码");
                    String password1 = sc1.nextLine();
                    System.out.println("请确认密码：");
                    String password2 = sc1.nextLine();
                    if(password1.equals(password2)){
                        //如果密码一致则可以添加
                        user.setPassword(password1);
                        userService.UpdateUser(user);
                    }
                }
            }else if(choice.equals("6")){
                System.out.println("正在退出...");
                Thread.sleep(1000);
                System.out.println("退出成功！");
                return;
            }
        }
    }
}
