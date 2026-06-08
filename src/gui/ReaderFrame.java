package gui;

import javax.swing.*;

import data.Reader;
import gui.util.PageSwitcher;
import gui.wrapper.BorrowWrapper;
import gui.wrapper.ReaderEditor;
import gui.wrapper.ReturnWrapper;

import java.awt.*;

public class ReaderFrame extends JFrame {
    // 菜单
    JMenuBar mb;
    JMenu m1;
    JMenuItem mi1_1, mi1_2, mi1_3;
    // 面板切换
    PageSwitcher switcher;

    // 页面容器
    JPanel contentPanel;
    // 封装面板
    BorrowWrapper borrowWrapper;
    ReturnWrapper returnWrapper;

    Reader reader;

    public ReaderFrame(Reader reader) {
        this.reader = reader;
        // 窗口配置
        setTitle("读者借书管理");
        setSize(950, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        initBar();

        contentPanel = new JPanel();
        switcher = new PageSwitcher(contentPanel);

        borrowWrapper = new BorrowWrapper(this, reader);
        returnWrapper = new ReturnWrapper(this, reader);

        // 注册页面（key自定义）
        switcher.registerPage("borrow", borrowWrapper.mainPanel);
        switcher.registerPage("return", returnWrapper.mainPanel);
        // 默认展示图书页面
        switcher.switchTo("borrow");
        add(contentPanel, BorderLayout.CENTER);
    }

    private void initBar() {
        mb = new JMenuBar();

        m1 = new JMenu("模式");

        mi1_1 = new JMenuItem("借书");
        mi1_2 = new JMenuItem("还书");
        mi1_3 = new JMenuItem("修改个人信息");
        m1.add(mi1_1);
        m1.add(mi1_2);
        m1.add(mi1_3);
        mb.add(m1);

        setJMenuBar(mb);
        mi1_1.addActionListener(e -> switcher.switchTo("borrow"));
        mi1_2.addActionListener(e -> {
            switcher.switchTo("return");
            returnWrapper.refreshBooks();
        });
        mi1_3.addActionListener(e -> new ReaderEditor(this, reader));
    }
}
