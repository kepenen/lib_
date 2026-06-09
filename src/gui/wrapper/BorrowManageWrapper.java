package gui.wrapper;

import data.Book;
import data.Borrow;
import data.Reader;
import service.BorrowService;
import service.impl.BorrowImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class BorrowManageWrapper {
    private final JFrame mainFrame;

    /*数据库处理服务*/
    private final BorrowService borrowService;

    /*数据库资产*/
    private List<Reader> borrowers;
    private List<Book> books;

    /*数据资产*/
    private String[] categories; // 分类列表
    private Object[][] bookData;
    // panel资产
    public JPanel mainPanel;
    private JList<String> categoryList;
    private JTable dataTable;
    private DefaultTableModel tableModel;
    // 表格列名
    private final String[] columnNames = {"书名", "作者", "ISBN"};
    // 当前选中的分类
    private String currentCategory;

    public BorrowManageWrapper(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        borrowService = new BorrowImpl();
        this.mainPanel = createMainPanel();
    }

    private void returnBook() {
        // 获取所有选中的行下标
        int[] selectedRows = dataTable.getSelectedRows();

        // 判断是否选中行
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(mainFrame, "请先选中要还的书（Ctrl/Shift 多选）");
            return;
        }

        Borrow borrow = new Borrow();
        borrow.setReader_id(borrowers.get(categoryList.getSelectedIndex()).getId());

        for (int selectedRow : selectedRows) {
            borrow.setBook_id(books.get(selectedRow).getId());
            borrowService.delete(borrow);
        }
        JOptionPane.showMessageDialog(mainFrame, "还书成功！");
        refreshBooks();
    }
    // 主面板
    private JPanel createMainPanel() {
        JPanel panel = new JPanel();
        // 顶部按钮栏
        JPanel btnPanel = createButtonPanel();
        // 左右分割面板
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(createLeftPanel());
        splitPane.setRightComponent(createRightPanel());
        splitPane.setDividerLocation(200);
        splitPane.setOneTouchExpandable(true);

        // 整体布局：顶部按钮 + 中间分割面板
        panel.setLayout(new BorderLayout());
        panel.add(btnPanel, BorderLayout.SOUTH);
        panel.add(splitPane, BorderLayout.CENTER);
        refreshTable();
        return panel;
    }

    // 按钮面板：已还
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        JButton btnReturn = new JButton("已还");

        btnReturn.addActionListener(e -> returnBook());
        panel.add(btnReturn);
        return panel;
    }

    // 左侧用户面板
    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("用户选择"));

        refreshBorrowers();
        categoryList = new JList<>(categories);
        // 默认选中第一个分类
        categoryList.setSelectedIndex(0);

        categoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // 注册鼠标事件
        categoryList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentCategory = categoryList.getSelectedValue();
                if (currentCategory != null) {
                    refreshTable();
                }
            }
        });
        leftPanel.add(new JScrollPane(categoryList), BorderLayout.CENTER);
        return leftPanel;
    }

    // 右侧表格面板
    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("借阅书籍列表"));

        tableModel = new DefaultTableModel();
        dataTable = new JTable(tableModel);
        dataTable.setRowHeight(26);
        JScrollPane tableScroll = new JScrollPane(dataTable);
        rightPanel.add(tableScroll, BorderLayout.CENTER);
        return rightPanel;
    }

    // 更新书籍数据
    private void refreshBooks() {
        int n = categoryList.getSelectedIndex();
        if (n == -1) {
            tableModel.setDataVector(new Object[][]{}, columnNames);
            return;
        }
        books = borrowService.selectBookByReaderId(borrowers.get(n).getId());
        bookData = new Object[books.size()][columnNames.length];
        for (int i = 0; i < books.size(); i++) {
            bookData[i][0] = books.get(i).getTitle();
            bookData[i][1] = books.get(i).getAuthor();
            bookData[i][2] = books.get(i).getIsbn();
        }
        tableModel.setDataVector(bookData == null ? new Object[][]{} : bookData, columnNames);
    }

    // 更新用户列表
    private void refreshBorrowers() {
        borrowers = borrowService.selectBorrowers();
        categories = new String[borrowers.size()];
        for (int i = 0; i < borrowers.size(); i++) {
            categories[i] = borrowers.get(i).getUsername();
        }
    }

    // 刷新表格数据
    private void refreshTable() {
        refreshBooks();
        tableModel.setDataVector(bookData == null ? new Object[][]{} : bookData, columnNames);
    }
}
