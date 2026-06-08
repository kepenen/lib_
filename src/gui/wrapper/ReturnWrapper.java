package gui.wrapper;

import data.Book;
import data.Borrow;
import data.Reader;
import service.BorrowService;
import service.impl.BorrowImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ReturnWrapper {
    private final JFrame mainFrame;

    /*数据库处理服务*/
    private BorrowService borrowService;

    /*数据库资产*/
    private List<Book> books;

    /*数据资产*/
    private Object[][] bookData;
    // panel资产
    public JPanel mainPanel;
    private JList<String> categoryList;
    private JTable dataTable;
    private DefaultTableModel tableModel;
    // 表格列名
    private final String[] columnNames = {"书名", "作者", "ISBN", "数量"};

    private final Reader reader;

    public ReturnWrapper(JFrame mainFrame, Reader reader) {
        this.mainFrame = mainFrame;
        this.reader = reader;
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
        borrow.setReader_id(reader.getId());
        for (int selectedRow : selectedRows) {
            borrow.setBook_id(books.get(selectedRow).getId());
            borrowService.delete(borrow);
        }
        JOptionPane.showMessageDialog(mainFrame, "还书成功！");
        refreshBooks();
    }

    // 更新书籍数据
    public void refreshBooks() {
        books = borrowService.selectBookByReaderId(reader.getId());
        bookData = new Object[books.size()][columnNames.length];
        for (int i = 0; i < books.size(); i++) {
            bookData[i][0] = books.get(i).getTitle();
            bookData[i][1] = books.get(i).getAuthor();
            bookData[i][2] = books.get(i).getIsbn();
            bookData[i][3] = books.get(i).getNum();
        }
        tableModel.setDataVector(bookData == null ? new Object[][]{} : bookData, columnNames);
    }
    // 主面板
    private JPanel createMainPanel() {
        JPanel panel = new JPanel();
        // 按钮栏
        JPanel btnPanel = createButtonPanel();
        JPanel listPanel = createListPanel();
        // 整体布局：中部表格 + 底部按钮
        panel.setLayout(new BorderLayout());
        panel.add(btnPanel, BorderLayout.SOUTH);
        panel.add(listPanel, BorderLayout.CENTER);
        refreshBooks();
        return panel;
    }
    // 按钮面板：还书
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        JButton btnEdit = new JButton("还书");

        // 修改按钮事件
        btnEdit.addActionListener(e -> returnBook());

        panel.add(btnEdit);
        return panel;
    }
    // 书籍表格面板
    private JPanel createListPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("书籍列表"));

        tableModel = new DefaultTableModel();
        dataTable = new JTable(tableModel);
        dataTable.setRowHeight(26);
        JScrollPane tableScroll = new JScrollPane(dataTable);
        rightPanel.add(tableScroll, BorderLayout.CENTER);
        return rightPanel;
    }

}
