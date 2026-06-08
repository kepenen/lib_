package data;

public class Reader {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String stu_id;
    private String stu_class;
    private String phone;

    public Reader(Long id, String name, String stu_id, String stu_class, String phone, String username, String password) {
        this.id = id;
        this.name = name;
        this.stu_id = stu_id;
        this.stu_class = stu_class;
        this.phone = phone;
        this.username = username;
        this.password = password;
    }

    public Reader() {

    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getStu_id() {
        return stu_id;
    }
    public void setStu_id(String stu_id) {
        this.stu_id = stu_id;
    }
    public String getStu_class() {
        return stu_class;
    }
    public void setStu_class(String stu_class) {
        this.stu_class = stu_class;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
