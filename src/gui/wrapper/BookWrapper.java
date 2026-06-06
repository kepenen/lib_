package gui.wrapper;

import data.Book;
import data.Genre;
import service.BookService;
import service.GenreService;
import service.impl.BookImpl;
import service.impl.GenreImpl;
import gui.util.TextFieldListener;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class BookWrapper {
    private final JFrame mainFrame;

    private final int ADD = 0;
    private final int EDIT = 1;
    /*数据库处理服务*/
    private final BookService bookService;
    private final GenreService genreService;

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
    private final String[] columnNames = {"书名", "作者", "ISBN", "数量"};
    // 当前选中的分类
    private String currentCategory;

    public BookWrapper(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        bookService = new BookImpl();
        genreService = new GenreImpl();
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

    // 顶部按钮面板：修改、添加、删除
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        JButton btnEdit = new JButton("修改");
        JButton btnAdd = new JButton("新增");
        JButton btnDel = new JButton("删除");

        // 修改按钮事件
        btnEdit.addActionListener(e -> edit());
        // 新增按钮事件
        btnAdd.addActionListener(e -> addRow());
        // 删除按钮事件
        btnDel.addActionListener(e -> deleteRow());

        panel.add(btnEdit);
        panel.add(Box.createHorizontalStrut(20));
        panel.add(btnAdd);
        panel.add(Box.createHorizontalStrut(20));
        panel.add(btnDel);
        return panel;
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

    // 右侧表格面板
    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("书籍列表"));

        tableModel = new DefaultTableModel();
        dataTable = new JTable(tableModel);
        dataTable.setRowHeight(26);
        JScrollPane tableScroll = new JScrollPane(dataTable);
        rightPanel.add(tableScroll, BorderLayout.CENTER);
        return rightPanel;
    }

    // 更新书籍数据
    private void refreshBooks() {
        books = bookService.selectByGenreID(genres.get(categoryList.getSelectedIndex()).getId());
        bookData = new Object[books.size()][columnNames.length];
        for (int i = 0; i < books.size(); i++) {
            bookData[i][0] = books.get(i).getTitle();
            bookData[i][1] = books.get(i).getAuthor();
            bookData[i][2] = books.get(i).getIsbn();
            bookData[i][3] = books.get(i).getNum();
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

    // 打开编辑表单
    private void openEditForm(int tag) {
        JLabel lab_title, lab_author,lab_isbn, lab_num, lab_genre;
        JTextField[] fields = new JTextField[4];
//        JTextField tf_title, tf_author, tf_isbn, tf_num;
        JComboBox<String> genre_combo = new JComboBox<>(categories);

        // 表单控件
        lab_title = new JLabel("标题：");
        lab_author = new JLabel("作者");
        lab_isbn = new JLabel("ISBN：");
        lab_num = new JLabel("数量：");
        lab_genre = new JLabel(" 类型：");

        Border border = BorderFactory.createEmptyBorder(0, 40, 0, 0);
        lab_title.setBorder(border);
        lab_author.setBorder(border);
        lab_isbn.setBorder(border);
        lab_num.setBorder(border);
        lab_genre.setBorder(border);

        fields[0] = new JTextField();
        fields[1] = new JTextField();
        fields[2] = new JTextField();
        fields[3] = new JTextField();

        if (tag == EDIT) {
            int n = dataTable.getSelectedRow();
            Book b = books.get(n);
            fields[0].setText(b.getTitle());
            fields[1].setText(b.getAuthor());
            fields[2].setText(b.getIsbn());
            fields[3].setText(b.getNum().toString());
        }
        genre_combo.setSelectedIndex(genre_combo.getSelectedIndex());

        // 按钮面板
        JButton btnOk = new JButton("确定");
        JButton btnCancel = new JButton("取消");

        // 创建弹窗，依附主窗口
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        panel.setLayout(new GridLayout(6, 2, 5, 5));

        JDialog dialog = new JDialog(mainFrame, "信息填写", true); // true=模态弹窗(阻塞主窗口)
        dialog.setSize(220,190);
        dialog.setLocationRelativeTo(mainFrame); // 弹窗居中

        panel.add(lab_title);
        panel.add(fields[0]);

        panel.add(lab_author);
        panel.add(fields[1]);

        panel.add(lab_isbn);
        panel.add(fields[2]);

        panel.add(lab_num);
        panel.add(fields[3]);

        panel.add(lab_genre);
        panel.add(genre_combo);

        panel.add(btnOk);
        panel.add(btnCancel);

        dialog.add(panel);

        TextFieldListener.addTextFieldListener(fields);
        // 确定按钮：获取表单数据
        btnOk.addActionListener(ev->{
            Book b;
            if (tag == EDIT) {
                // 保留id
                b = books.get(dataTable.getSelectedRow());
            } else {
                b = new Book();
            }
            b.setTitle(fields[0].getText().trim());
            b.setAuthor(fields[1].getText().trim());
            b.setIsbn(fields[2].getText().trim());
            b.setNum(Long.parseLong(fields[3].getText().trim()));
            b.setGenre_id(genres.get(genre_combo.getSelectedIndex()).getId());

            if (tag == EDIT) {
                bookService.update(b);
            } else {
                bookService.insert(b);
            }

            dialog.dispose(); // 关闭弹窗
        });

        // 取消关闭弹窗
        btnCancel.addActionListener(ev-> dialog.dispose());

        dialog.setVisible(true); // 显示弹窗

    }

    // 修改书籍
    private void edit() {
        int[] selectedRows = dataTable.getSelectedRows();
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(mainFrame,"没有选中任何数据");
            return;
        }
        if (selectedRows.length > 1) {
            JOptionPane.showMessageDialog(mainFrame,"只能选择一条数据");
            return;
        }
        openEditForm(EDIT);
        refreshTable();
    }

    // 新增书籍
    private void addRow() {
        openEditForm(ADD);
        refreshTable();
    }

    // （批量）删除书籍
    private void deleteRow() {
        // 获取所有选中的行下标
        int[] selectedRows = dataTable.getSelectedRows();

        // 判断是否选中行
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(mainFrame, "请先选中要删除的行（Ctrl/Shift 多选）");
            return;
        }

        // 二次确认
        int confirm = JOptionPane.showConfirmDialog(mainFrame,
                "确定要删除选中的 " + selectedRows.length + " 条数据吗？",
                "删除确认",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        // 提取书籍id
        Long[] ids = new Long[selectedRows.length];
        for (int i = 0; i < selectedRows.length; i++) {
            ids[i] = books.get(selectedRows[i]).getId();
        }
        bookService.deleteBooks(ids);
        refreshTable();
    }
}