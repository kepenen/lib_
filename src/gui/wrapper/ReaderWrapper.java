package gui.wrapper;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import data.Reader;
import gui.util.TextFieldListener;
import service.ReaderService;
import service.impl.ReaderImpl;

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
    private final String[] columnNames = {"姓名", "学号", "班级", "电话"};


    public ReaderWrapper(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        readerService = new ReaderImpl();
        this.mainPanel = createMainPanel();
    }

    // 更新数据
    private void refreshData() {
        readers = readerService.select();
        readerData = new Object[readers.size()][columnNames.length];
        for (int i = 0; i < readers.size(); i++) {
            readerData[i][0] = readers.get(i).getName();
            readerData[i][1] = readers.get(i).getStu_id();
            readerData[i][2] = readers.get(i).getStu_class();
            readerData[i][3] = readers.get(i).getPhone();
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

    // 打开编辑表单
    private void openEditForm(int tag) {
        JLabel lab_name, lab_stu_id,lab_stu_class, lab_phone;
        JTextField[] fields = new JTextField[4];

        // 表单控件
        lab_name = new JLabel("姓名：");
        lab_stu_id = new JLabel("学号");
        lab_stu_class = new JLabel("班级：");
        lab_phone = new JLabel("电话：");

        Border border = BorderFactory.createEmptyBorder(0, 40, 0, 0);
        lab_name.setBorder(border);
        lab_stu_id.setBorder(border);
        lab_stu_class.setBorder(border);
        lab_phone.setBorder(border);

        fields[0] = new JTextField();
        fields[1] = new JTextField();
        fields[2] = new JTextField();
        fields[3] = new JTextField();

        if (tag == EDIT) {
            int n = dataTable.getSelectedRow();
            Reader reader = readers.get(n);
            fields[0].setText(reader.getName());
            fields[1].setText(reader.getId().toString());
            fields[2].setText(reader.getStu_class());
            fields[3].setText(reader.getPhone());
        }

        // 按钮面板
        JButton btnOk = new JButton("确定");
        JButton btnCancel = new JButton("取消");

        // 创建弹窗，依附主窗口
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        panel.setLayout(new GridLayout(6, 2, 5, 5));

        JDialog dialog = new JDialog(mainFrame, "信息填写", true); // true=模态弹窗(阻塞主窗口)
        dialog.setSize(220,170);
        dialog.setLocationRelativeTo(mainFrame); // 弹窗居中

        panel.add(lab_name);
        panel.add(fields[0]);

        panel.add(lab_stu_id);
        panel.add(fields[1]);

        panel.add(lab_stu_class);
        panel.add(fields[2]);

        panel.add(lab_phone);
        panel.add(fields[3]);

        panel.add(btnOk);
        panel.add(btnCancel);

        dialog.add(panel);

        TextFieldListener.addTextFieldListener(fields);

        btnOk.addActionListener(ev -> {
            Reader r;
            if (tag == EDIT) {
                r = readers.get(dataTable.getSelectedRow());
            } else {
                r = new  Reader();
            }
            r.setName(fields[0].getText());
            r.setStu_id(fields[1].getText());
            r.setStu_class(fields[2].getText());
            r.setPhone(fields[3].getText());

            if (tag == EDIT) {
                readerService.update(r);
            } else {
                readerService.insert(r);
            }
            dialog.dispose(); // 关闭弹窗
        });

        // 取消关闭弹窗
        btnCancel.addActionListener(ev-> dialog.dispose());

        dialog.setVisible(true); // 显示弹窗
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
