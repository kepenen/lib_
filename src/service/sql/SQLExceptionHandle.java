package service.sql;

import javax.swing.*;

public abstract class SQLExceptionHandle {
    private static final JFrame frame = new JFrame();

    public static void showDialog(String c, Exception e) {
        JOptionPane.showMessageDialog(frame, c + "\n" +e.getMessage(), "数据库错误", JOptionPane.ERROR_MESSAGE);
    }
}
