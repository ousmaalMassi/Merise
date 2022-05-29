/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MeriseGUI.ddd;

import com.mcd.Property;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

/**
 * @author rpc
 */
public class DDPanel extends JPanel {

    private static final String SUPP_TEXT = "supprimer";
    private static JTable ddTable;
    private static final String NAME = "Nom";
    private static final String TYPE = "Type";
    private static final String LENGTH = "Taille";
    private static final String MCD = "MCD";
    private static final String GDF = "GDF";
    private static int GDF_COL_INDEX;
    private static int NAME_COL_INDEX;
    private JComboBox<Boolean> combo;
    private static TableModel tableModel;
    private Object[][] data;
    private GroupLayout layout;

    public DDPanel() {
        initComponents();
        showExample();
    }

    private void initComponents() {
        data = new Object[][]{createEmptyDataRow()};
        Property.Types[] comboData = new Property.Types[]{Property.Types.ALPHABETICAL, Property.Types.ALPHANUMERIC, Property.Types.DATE, Property.Types.DIGITAL, Property.Types.LOGIC};
        combo = new JComboBox(comboData);

        tableModel = new TableModel(data, new String[]{NAME, TYPE, LENGTH, MCD, GDF});
        ddTable = new JTable(tableModel);

        NAME_COL_INDEX = ddTable.getColumn(NAME).getModelIndex();
        GDF_COL_INDEX = ddTable.getColumn(GDF).getModelIndex();

        ddTable.getColumn("Type").setCellEditor(new DefaultCellEditor(combo));
//        ddTable.getColumn("suppression").setCellRenderer(new ButtonRenderer());
        ddTable.setRowHeight(35);

        JButton addRowBtn = new JButton();
        addRowBtn.setText("Ajouter un attribue");
        addRowBtn.setFont(new Font("Tahoma", Font.BOLD, 15));
        addRowBtn.addActionListener(e -> addNewRow(null));

        JButton removeRowBtn = new JButton();
        removeRowBtn.setText(SUPP_TEXT);
        removeRowBtn.setFont(new Font("Tahoma", Font.BOLD, 15));
        removeRowBtn.addActionListener(e -> tableModel.removeRow(ddTable.getSelectedRow()));

        JScrollPane jScrollPane1 = new JScrollPane();
        jScrollPane1.setViewportView(ddTable);

        JPanel footer = new JPanel();
        footer.add(addRowBtn);
        footer.add(removeRowBtn);

        this.setLayout(new BorderLayout());
        this.add(jScrollPane1, BorderLayout.CENTER);
        this.add(footer, BorderLayout.SOUTH);

        tableModel.setValueAt(combo.getItemAt(0), ddTable.getRowCount() - 1, 1);
        ddTable.remove(ddTable.getRowCount()-1);
    }

    private Object[] createEmptyDataRow() {
        return new Object[]{"", "","0", "", false};
    }

    private void addNewRow(Object[] dataRow) {
        if (dataRow != null) {
            tableModel.addRow(dataRow);
        } else
            tableModel.addRow(createEmptyDataRow());
        tableModel.setValueAt(combo.getItemAt(0), ddTable.getRowCount() - 1, 1);
    }

    private void showExample() {
        tableModel.removeRow(0);
        Object[][] dataExample = new Object[][]{
                {"id client", "", "11", "", false},
                {"nom", "", "50", "", false},
                {"prénom", "", "50", "", false},
                {"adresse", "", "255", "", false},
                {"id_article", "", "11", "", false},
                {"prix_achat", "", "10", "", false},
                {"prix_vente", "", "10", "", false},
                {"designation", "", true, "", false},
                {"quantity", "", "2", "", false},
        };

        for (Object[] dataRow : dataExample) {
            addNewRow(dataRow);
        }
    }

    public static ArrayList<String> getAttributeList() {
        int nRow = tableModel.getRowCount();
        ArrayList<String> attrList = new ArrayList<>();
        for (int i = 0; i < nRow; i++) {
            attrList.add(tableModel.getValueAt(i, NAME_COL_INDEX).toString());
        }
        attrList.removeIf(item -> item.equals(""));
        return attrList;
    }

    public static Vector<String> getGDFAttributes() {
        int nRow = tableModel.getRowCount();
        Vector<String> attrList = new Vector<>();
        for (int i = 0; i < nRow; i++) {
            if (tableModel.getValueAt(i, GDF_COL_INDEX).toString().equals("false"))
                attrList.add(tableModel.getValueAt(i, NAME_COL_INDEX).toString());

        }
        attrList.removeIf(item -> item.equals(""));
        return attrList;
    }

    public static void setUsedInGDF(String attributeName ,boolean used) {
        int row = getAttributeList().indexOf(attributeName);
        tableModel.setValueAt(used, row, GDF_COL_INDEX);
    }

   /* public static ArrayList<Property> getAttributeList() {
        int nRow = tableModel.getRowCount();
        ArrayList<Property> attrList = new ArrayList<>();
        for (int i = 0; i < nRow; i++) {
            String name = tableModel.getValueAt(i, 0).toString();
            String type = tableModel.getValueAt(i, 1).toString();
            int length = Integer.parseInt(tableModel.getValueAt(i, 2).toString());
            String types = tableModel.getValueAt(i, 1).toString();

            Property property = new Property(name, Property.Types.ALPHABETICAL, length, List.of(Property.Constraints.NOT_NULL));
            attrList.add(property);
        }
        attrList.removeIf(item -> item.equals(""));
        return attrList;
    }*/

    public static Object[][] getAttributes() {
        int nRow = tableModel.getRowCount() - 1;
        Object[][] tableData = new Object[nRow][2];
        for (int i = 0; i < nRow; i++) {
            tableData[i][0] = tableModel.getValueAt(i, NAME_COL_INDEX).toString();
            tableData[i][1] = tableModel.getValueAt(i, NAME_COL_INDEX).toString();
        }
        return tableData;
    }

    void PrintDataTable() {
        Object[][] tableData = tableModel.getTableData();
        for (Object[] tableRow : tableData) {
            for (Object tableColumn : tableRow) {
                System.out.println(tableColumn);
            }
        }
    }
}

        