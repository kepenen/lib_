package gui;

import javax.swing.*;

import gui.util.PageSwitcher;
import gui.wrapper.BookWrapper;
import gui.wrapper.BorrowManageWrapper;
import gui.wrapper.ReaderWrapper;

import java.awt.*;


public class MainFrame extends JFrame{
    // 菜单
    JMenuBar mb;
    JMenu m1;
    JMenuItem mi1_1, mi1_2, mi1_3;
    // 面板切换
    PageSwitcher switcher;

    // 页面容器
    JPanel contentPanel;
    // 封装面板
    BookWrapper bookWrapper;
    ReaderWrapper readerWrapper;
    BorrowManageWrapper borrowManageWrapper;

    public MainFrame() {
        // 窗口配置
        setTitle("图书管理");
        setSize(950, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        initBar();

        contentPanel = new JPanel();
        switcher = new PageSwitcher(contentPanel);

        // 图书、读者面板
        bookWrapper = new BookWrapper(this);
        readerWrapper = new ReaderWrapper(this);
        borrowManageWrapper = new BorrowManageWrapper(this);

        // 注册页面（key自定义）
        switcher.registerPage("book", bookWrapper.mainPanel);
        switcher.registerPage("reader", readerWrapper.mainPanel);
        switcher.registerPage("borrow", borrowManageWrapper.mainPanel);

        // 默认展示图书页面
        switcher.switchTo("book");
        add(contentPanel, BorderLayout.CENTER);
    }

    private void initBar() {
        mb = new JMenuBar();

        m1 = new JMenu("模式");

        mi1_1 = new JMenuItem("图书信息");
        mi1_2 = new JMenuItem("读者信息");
        mi1_3 = new JMenuItem("借书管理");
        m1.add(mi1_1);
        m1.add(mi1_2);
        m1.add(mi1_3);
        mb.add(m1);

        setJMenuBar(mb);

        mi1_1.addActionListener(e -> switcher.switchTo("book"));
        mi1_2.addActionListener(e -> switcher.switchTo("reader"));
        mi1_3.addActionListener(e -> switcher.switchTo("borrow"));
    }
}




