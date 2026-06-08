package gui.wrapper;

import data.Book;
import data.Borrow;
import data.Genre;
import data.Reader;
import service.BookService;
import service.BorrowService;
import service.GenreService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class BorrowWrapper {
    private final JFrame mainFrame;

    /*数据库处理服务*/
    private final BookService bookService;
    private final GenreService genreService;
    private final BorrowService borrowService;

    /*数据库资产*/
    private java.util.List<Genre> genres;
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
//    private final String[] columnNames = {"书名", "作者", "ISBN", "数量", "剩余数量"};
    private final String[] columnNames = {"书名", "作者", "ISBN", "剩余库存", "是否已借阅"};
    // 当前选中的分类
    private String currentCategory;

    Reader reader;

    public BorrowWrapper(JFrame mainFrame, Reader reader) {
        this.reader = reader;
        this.mainFrame = mainFrame;
        bookService = new BookService();
        genreService = new GenreService();
        borrowService = new BorrowService();
        this.mainPanel = createMainPanel();
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

    private Borrow checkBorrowed(Long reader_id, Long book_id) {
        Borrow borrow = new Borrow();
        borrow.setBook_id(book_id);
        borrow.setReader_id(reader_id);
        return borrowService.check(borrow);
    }

    private void borrow() {
        Borrow borrow = new Borrow();
        int r = dataTable.getSelectedRow();
        if (r == -1) {
            JOptionPane.showMessageDialog(mainFrame, "请选择要借阅的图书！");
            return;
        }
        borrow.setBook_id(books.get(r).getId());
        borrow.setReader_id(reader.getId());
        Borrow check = borrowService.check(borrow);
        if (check == null) {
            borrowService.insert(borrow);
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(mainFrame, "您已经借阅有该书！");
        }
    }
    // 按钮面板：借阅
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        JButton btnEdit = new JButton("借阅");

        // 修改按钮事件
        btnEdit.addActionListener(e -> borrow());

        panel.add(btnEdit);
        return panel;
    }

    // 右侧表格面板
    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("书籍列表"));

        tableModel = new DefaultTableModel();
        dataTable = new JTable(tableModel);
        dataTable.setRowHeight(26);
        // 单选模式
        dataTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tableScroll = new JScrollPane(dataTable);
        rightPanel.add(tableScroll, BorderLayout.CENTER);
        return rightPanel;
    }

    // 左侧分类面板
    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("分类选择"));

        refreshGenres();
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

    // 更新书籍数据
    private void refreshBooks() {
        books = bookService.selectByGenreID(genres.get(categoryList.getSelectedIndex()).getId());
        bookData = new Object[books.size()][columnNames.length];
        for (int i = 0; i < books.size(); i++) {
            bookData[i][0] = books.get(i).getTitle();
            bookData[i][1] = books.get(i).getAuthor();
            bookData[i][2] = books.get(i).getIsbn();
            bookData[i][3] = books.get(i).getNum() - books.get(i).getCount_borrowed();
            if (checkBorrowed(reader.getId(), books.get(i).getId()) == null){
                bookData[i][4] = "否";
            } else {
                bookData[i][4] = "是";
            }
        }
    }

    // 更新类别列表
    private void refreshGenres() {
        genres = genreService.select();
        categories = new String[genres.size()];
        for (int i = 0; i < genres.size(); i++) {
            categories[i] = genres.get(i).getGenre();
        }
    }

    // 刷新表格数据
    private void refreshTable() {
        refreshBooks();
        tableModel.setDataVector(bookData == null ? new Object[][]{} : bookData, columnNames);
    }

}
