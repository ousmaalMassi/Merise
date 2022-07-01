package com.MeriseGUI.rules;

import com.MeriseGUI.DeleteButtonRenderer;
import com.MeriseGUI.MTableModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ManagementRulesPanel extends JPanel{

    private final MTableModel tableModel;

    public ManagementRulesPanel() {
        // Column Names
        String[] titles = { "num", "Régles de gestion", ""};

        tableModel = new RulesTableModel(titles);
        JTable table = new JTable(tableModel);

        table.setRowHeight(30);
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        table.getColumn("").setCellRenderer(new DeleteButtonRenderer(table));
        table.getColumn("").setCellEditor(new DeleteButtonRenderer(table));
        table.getColumn("").setMaxWidth(50);

        JButton addRowBtn = new JButton();
        addRowBtn.setText("Ajouter");
        addRowBtn.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
        addRowBtn.addActionListener(e -> addData());

        JPanel footer = new JPanel();
        footer.setLayout(new BorderLayout());
        footer.add(addRowBtn, BorderLayout.EAST);

        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setViewportView(table);

        this.setLayout(new BorderLayout());
        this.add(footer, BorderLayout.SOUTH);
        this.add(jScrollPane, BorderLayout.CENTER);
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

    }

    private void addData() {
        tableModel.addRow(this.createEmptyDataRow());
    }

    private Object[] createEmptyDataRow() {
        int index = tableModel.getRowCount()+1;
        return new Object[]{index, "", ""};
    }

    public Object[][] getData() {
        return tableModel.getData();
    }

    public void setData(Object[][] data) {
        this.tableModel.setData(data);
    }
}
