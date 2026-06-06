package data;

public class Reader {
    public Integer id;
    public String name;
    public String stu_id;
    public String stu_class;
    public String phone;

    public Reader(int id, String name, String stu_id, String stu_class, String phone) {
        this.id = id;
        this.name = name;
        this.stu_id = stu_id;
        this.stu_class = stu_class;
        this.phone = phone;
    }

    public Reader() {
        this.id = 0;
        this.name = "";
        this.stu_id = "";
        this.stu_class = "";
        this.phone = "";
    }


}
