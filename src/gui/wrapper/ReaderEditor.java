package gui.wrapper;

import data.Reader;
import gui.util.TextFieldListener;
import service.ReaderService;
import service.impl.ReaderImpl;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ReaderEditor {
    private final JFrame mainFrame;
    private final ReaderService readerService;
    private Reader reader;

    private final int EDIT = 1;
    private final int ADD = 0;
    private int tag = ADD;

    /**
     * 没有id，添加新的信息
     * */
    public ReaderEditor(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        readerService = new ReaderImpl();
        editForm();
    }

    /**
     * 有id，修改信息
     * */
    public ReaderEditor(JFrame mainFrame, Reader reader) {
        this.mainFrame = mainFrame;
        readerService = new ReaderImpl();
        this.reader = reader;
        tag = EDIT;
        editForm();
    }

    // 打开编辑表单
    private void editForm() {
        JLabel[] labels = new JLabel[6];
        JTextField[] fields = new JTextField[6];

        // 表单控件
        labels[0] = new JLabel("用户名：");
        labels[1] = new JLabel("密码：");
        labels[2] = new JLabel("姓名：");
        labels[3] = new JLabel("学号：");
        labels[4] = new JLabel("班级：");
        labels[5] = new JLabel("电话：");

        Border border = BorderFactory.createEmptyBorder(0, 50, 0, 0);
        for (JLabel label : labels) {
            label.setBorder(border);
        }
        fields[0] = new JTextField();
        fields[1] = new JTextField();
        fields[2] = new JTextField();
        fields[3] = new JTextField();
        fields[4] = new JTextField();
        fields[5] = new JTextField();

        if (tag == EDIT) {
            fields[0].setText(reader.getUsername());
            fields[1].setText(reader.getPassword());
            fields[2].setText(reader.getName());
            fields[3].setText(reader.getId().toString());
            fields[4].setText(reader.getStu_class());
            fields[5].setText(reader.getPhone());
        }

        // 按钮面板
        JButton btnOk = new JButton("确定");
        JButton btnCancel = new JButton("取消");

        // 创建弹窗，依附主窗口
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        panel.setLayout(new GridLayout(7, 2, 5, 5));

        JDialog dialog = new JDialog(mainFrame, "信息填写", true); // true=模态弹窗(阻塞主窗口)

        dialog.setSize(350,400);
        dialog.setLocationRelativeTo(mainFrame); // 弹窗居中

        for (int i = 0; i<6; i++) {
            panel.add(labels[i]);
            panel.add(fields[i]);
        }

        panel.add(btnOk);
        panel.add(btnCancel);

        dialog.add(panel);

        TextFieldListener.addTextFieldListener(fields);

        btnOk.addActionListener(ev -> {
            reader.setUsername(fields[0].getText());
            reader.setPassword(fields[1].getText());
            reader.setName(fields[2].getText());
            reader.setStu_id(fields[3].getText());
            reader.setStu_class(fields[4].getText());
            reader.setPhone(fields[5].getText());

            if (tag == EDIT) {
                readerService.update(reader);
            } else {
                readerService.insert(reader);
            }
            dialog.dispose(); // 关闭弹窗
        });

        // 取消关闭弹窗
        btnCancel.addActionListener(ev-> dialog.dispose());

        dialog.setVisible(true); // 显示弹窗
    }
}
