package com.MeriseGUI.rules;

import javax.swing.*;
import java.awt.*;

public class ManagementRulesPanel extends JPanel{

    private final JTable table;
    private final RulesTableModel rulesTableModel;
    public ManagementRulesPanel() {

        String[][] data = {
                { "1", "CSE" },
                { "2", "IT" }
        };

        // Column Names
        String[] titles = { "num", "Régles de gestion"};

        rulesTableModel = new RulesTableModel(data, titles);
        this.table = new JTable(rulesTableModel);


        JButton addRowBtn = new JButton();
        addRowBtn.setText("Ajouter une régle de gestion");
        addRowBtn.setFont(new Font("Tahoma", Font.BOLD, 15));
        addRowBtn.addActionListener(e -> addAttribute());

        JButton removeRowBtn = new JButton();
        removeRowBtn.setText("Supprimer");
        removeRowBtn.setFont(new Font("Tahoma", Font.BOLD, 15));
        removeRowBtn.addActionListener(e -> removeAttribute());

        JScrollPane jScrollPane1 = new JScrollPane();
        jScrollPane1.setViewportView(table);

        JPanel footer = new JPanel();
        footer.add(addRowBtn);
        footer.add(removeRowBtn);

        table.setRowHeight(30);
        table.getColumnModel().getColumn(0).setMaxWidth(50);

        this.setLayout(new BorderLayout());
        this.add(jScrollPane1, BorderLayout.CENTER);
        this.add(footer, BorderLayout.SOUTH);

    }

    private void addAttribute() {
        String inputDialog = JOptionPane.showInputDialog(this, "Veuillez entrer le nom de l'attribut");
        if (inputDialog == null)
            return;
        rulesTableModel.addRow(this.createEmptyDataRow(inputDialog));
    }

    private Object[] createEmptyDataRow(String name) {
        int index = rulesTableModel.getRowCount()+1;
        return new Object[]{index, name};
    }

    private void removeAttribute() {
        rulesTableModel.removeRow(table.getSelectedRow());
    }

}
