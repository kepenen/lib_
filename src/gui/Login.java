package gui;

import data.Reader;
import data.User;
import service.ReaderService;
import service.UserService;
import service.impl.ReaderImpl;
import service.impl.UserImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Login extends JFrame{
    JLabel lab_title, lab_name, lab_pwd;
    JTextField tf;
    JPasswordField pf;
    JButton btn_login, btn_reset;
    // 单选按钮
    JRadioButton rdoAdmin = new JRadioButton("管理员", true);
    JRadioButton rdoReader = new JRadioButton("读者");
    ButtonGroup group = new ButtonGroup();

    JPanel panel = new JPanel();

    UserService userService = new UserImpl();
    ReaderService readerService = new ReaderImpl();

    public Login() {
        init();
        setLocation(100, 100);
        setSize(450, 390);
        setVisible(true);
        setTitle("登录");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void init() {
        lab_title = new JLabel("欢迎来到图书管理系统");
        lab_name = new JLabel("用户名：");
        lab_pwd = new JLabel("密 码：");
        btn_login = new JButton("登录");
        btn_reset = new JButton("重置");

        tf = new JTextField(10);
        pf = new JPasswordField(10);

        panel.setLayout(null);

        Font font = new Font("宋体", Font.BOLD, 25);
        lab_title.setFont(new Font("宋体", Font.BOLD, 30));
        lab_name.setFont(font);
        lab_pwd.setFont(font);
        tf.setFont(font);
        pf.setFont(font);
        btn_login.setFont(font);
        btn_reset.setFont(font);
        rdoAdmin.setFont(font);
        rdoReader.setFont(font);

        group.add(rdoAdmin);
        group.add(rdoReader);

        lab_title.setBounds(65, 30, 330, 50);
        lab_name.setBounds(55 , 110, 120, 40);
        lab_pwd.setBounds(55 , 170, 120, 40);
        tf.setBounds(195 , 110, 190, 40);
        pf.setBounds(195 , 170, 190, 40);
        rdoAdmin.setBounds(90 , 230, 120, 40);
        rdoReader.setBounds(250 , 230, 120, 40);
        btn_login.setBounds(80 , 280, 120, 40);
        btn_reset.setBounds(240 , 280, 120, 40);

        add(panel);
        panel.add(lab_title);
        panel.add(lab_name);
        panel.add(lab_pwd);
        panel.add(tf);
        panel.add(pf);
        panel.add(btn_login);
        panel.add(btn_reset);
        panel.add(rdoAdmin);
        panel.add(rdoReader);

        btn_login.addActionListener(this::loginCheck);
        btn_reset.addActionListener(e -> reset());

        tf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // 判断按下Enter, DOWN
                if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN) {
                    pf.requestFocus();
                }
            }
        });

        pf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_UP) {
                    pf.requestFocus();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loginCheck(null);
                }
            }
        });
    }

    private void loginCheck(ActionEvent e) {
        String name = tf.getText().trim();
        char[] chars = pf.getPassword();
        String pwd = String.copyValueOf(chars);

        if (rdoAdmin.isSelected()) {
            User user = userService.selectByName(name);
            if (user == null || !pwd.equals(user.getPassword())) {
                JOptionPane.showMessageDialog(this, "账号或密码错误");
                return;
            }
            this.dispose();
            new MainFrame().setVisible(true);
        } else {
            Reader reader = readerService.selectByUsername(name);
            if (reader == null || !pwd.equals(reader.getPassword())) {
                JOptionPane.showMessageDialog(this, "账号或密码错误");
                return;
            }
            this.dispose();
            new ReaderFrame(reader).setVisible(true);
        }

    }


    private void reset() {
        tf.setText("");
        pf.setText("");
    }

}




