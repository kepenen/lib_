package gui.wrapper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import data.Reader;
import service.ReaderService;

import java.awt.*;
import java.util.List;

public class ReaderWrapper {
    private final JFrame mainFrame;

    private final int ADD = 0;
    private final int EDIT = 1;

    /*数据库处理服务*/
    private final ReaderService  readerService;
    /*数据库资产*/
    private List<Reader> readers;

    /*数据资产*/
    private Object[][] readerData;
    // panel资产
    public JPanel mainPanel;
    private JTable dataTable;
    private DefaultTableModel tableModel;
    // 表格列名
    private final String[] columnNames = {"用户名", "姓名", "学号", "班级", "电话"};


    public ReaderWrapper(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        readerService = new ReaderService();
        this.mainPanel = createMainPanel();
    }

    // 更新数据
    private void refreshData() {
        readers = readerService.select();
        readerData = new Object[readers.size()][columnNames.length];
        for (int i = 0; i < readers.size(); i++) {
            readerData[i][0] = readers.get(i).getUsername();
            readerData[i][1] = readers.get(i).getName();
            readerData[i][2] = readers.get(i).getStu_id();
            readerData[i][3] = readers.get(i).getStu_class();
            readerData[i][4] = readers.get(i).getPhone();
        }
    }

    // 刷新表格
    private void refreshTable() {
        refreshData();
        tableModel.setDataVector(readerData == null ? new Object[][]{} : readerData, columnNames);
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

    // 创建列表面板
    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("读者列表"));
        tableModel = new DefaultTableModel();
        dataTable = new JTable(tableModel);
        dataTable.setRowHeight(26);
        JScrollPane tableScroll = new JScrollPane(dataTable);
        panel.add(tableScroll, BorderLayout.CENTER);
        return panel;
    }

    // 主面板
    private JPanel createMainPanel() {
        JPanel panel = new JPanel();
        // 顶部按钮栏
        JPanel btnPanel = createButtonPanel();
        panel.setLayout(new BorderLayout());
        panel.add(btnPanel, BorderLayout.SOUTH);
        panel.add(createCenterPanel(), BorderLayout.CENTER);
        refreshTable();
        return panel;
    }

    private void openEditForm(int tag) {
        if (tag == EDIT) {
            int n = dataTable.getSelectedRow();
            Reader reader = readers.get(n);
            new ReaderEditor(mainFrame, reader);
            return;
        }
        new ReaderEditor(mainFrame);
    }

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

    private void addRow() {
        openEditForm(ADD);
        refreshTable();
    }

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

        // 提取id
        Long[] ids = new Long[selectedRows.length];
        for (int i = 0; i < selectedRows.length; i++) {
            ids[i] = readers.get(selectedRows[i]).getId();
        }
        readerService.deleteReaders(ids);
        refreshTable();
    }
}
