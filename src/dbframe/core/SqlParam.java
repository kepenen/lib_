package dbframe.core;

public class SqlParam {
    private Object value;
    private int type; // java.sql.Types

    public SqlParam(Object value,int type){
        this.value=value;
        this.type=type;
    }
    // getter setter
    public Object getValue(){return value;}
    public int getType(){return type;}
}
