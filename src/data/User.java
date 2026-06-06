package data;

public class User {
    public Integer id;
    public String name;
    public String password;

    public User(Integer id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public User() {
        id = 0;
        name = "";
        password = "";
    }
}
