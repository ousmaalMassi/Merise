/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MeriseGUI.ddd;

import com.mcd.Property;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * @author rpc
 */
public class DDPanel extends JPanel {

    private static final String SUPP_TEXT = "supprimer";
    private static final String NAME = "Nom";
    private static final String TYPE = "Type";
    private static final String LENGTH = "Taille";
    private static final String MCD = "MCD";
    private static final String GDF = "GDF";
    private static JTable ddTable;
    private static int NAME_COL_INDEX;
    private static int TYPE_COL_INDEX;
    private static int LENGTH_COL_INDEX;
    public static int GDF_COL_INDEX;
    public static int MCD_COL_INDEX;
    private static TableModel tableModel;
    private JComboBox<Property.Types> combo;

    public DDPanel() {
        initComponents();
        showExample();
    }

    private void initComponents() {
        Object[][] data = new Object[][]{createEmptyDataRow("")};
        Property.Types[] comboData = new Property.Types[]{Property.Types.ALPHABETICAL, Property.Types.ALPHANUMERIC, Property.Types.DATE, Property.Types.DIGITAL, Property.Types.LOGIC};
        combo = new JComboBox(comboData);

        tableModel = new TableModel(data, new String[]{NAME, TYPE, LENGTH, MCD, GDF});
        ddTable = new JTable(tableModel);

        NAME_COL_INDEX = ddTable.getColumn(NAME).getModelIndex();
        TYPE_COL_INDEX = ddTable.getColumn(TYPE).getModelIndex();
        LENGTH_COL_INDEX = ddTable.getColumn(LENGTH).getModelIndex();
        MCD_COL_INDEX = ddTable.getColumn(MCD).getModelIndex();
        GDF_COL_INDEX = ddTable.getColumn(GDF).getModelIndex();

        ddTable.getColumn("Type").setCellEditor(new DefaultCellEditor(combo));
//        ddTable.getColumn("suppression").setCellRenderer(new ButtonRenderer());
        ddTable.setRowHeight(35);

        JButton addRowBtn = new JButton();
        addRowBtn.setText("Ajouter un attribue");
        addRowBtn.setFont(new Font("Tahoma", Font.BOLD, 15));
        addRowBtn.addActionListener(e -> addAttribute());

        JButton removeRowBtn = new JButton();
        removeRowBtn.setText(SUPP_TEXT);
        removeRowBtn.setFont(new Font("Tahoma", Font.BOLD, 15));
        removeRowBtn.addActionListener(e -> removeAttribute());

        JScrollPane jScrollPane1 = new JScrollPane();
        jScrollPane1.setViewportView(ddTable);

        JPanel footer = new JPanel();
        footer.add(addRowBtn);
        footer.add(removeRowBtn);

        this.setLayout(new BorderLayout());
        this.add(jScrollPane1, BorderLayout.CENTER);
        this.add(footer, BorderLayout.SOUTH);

        tableModel.setValueAt(combo.getItemAt(0), ddTable.getRowCount() - 1, 1);
        ddTable.remove(ddTable.getRowCount() - 1);
    }

    private void addAttribute() {
        String inputDialog = JOptionPane.showInputDialog(this, "Veuillez entrer le nom de l'attribut");
        if (inputDialog == null)
            return;
        tableModel.addRow(this.createEmptyDataRow(inputDialog));
        tableModel.setValueAt(combo.getItemAt(0), ddTable.getRowCount() - 1, 1);
    }

    private Object[] createEmptyDataRow(String name) {
        return new Object[]{name, "", "0", "", false};
    }

    private void removeAttribute() {
        int selectedRow = ddTable.getSelectedRow();
        if (selectedRow != -1) {
            if (isUsedInMCD(selectedRow) || isUsedInGDF(selectedRow))
                JOptionPane.showMessageDialog(this, "Cet Attribut est en cours d'utilisation!");
            else
                tableModel.removeRow(selectedRow);
        }
        else
            JOptionPane.showMessageDialog(this, "Aucun attribut n'a été sélectionné");
    }

    // GDF methods start
    public static Vector<String> getAttributeList() {
        int nRow = tableModel.getRowCount();
        Vector<String> attrList = new Vector<>();
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
    public static void setUsedInGDF(String attributeName, boolean used) {
        int row = getAttributeList().indexOf(attributeName);
        tableModel.setValueAt(used, row, GDF_COL_INDEX);
    }
    private boolean isUsedInGDF(int selectedRow) {
        return tableModel.getValueAt(selectedRow, GDF_COL_INDEX).toString().equals("true");
    }
    // GDF methods end


    // MCD methods start
    public static Vector<String> getMCDAttributes() {
        int nRow = tableModel.getRowCount();
        Vector<String> attrList = new Vector<>();
        for (int i = 0; i < nRow; i++) {
            if (tableModel.getValueAt(i, MCD_COL_INDEX).toString().isEmpty())
                attrList.add(tableModel.getValueAt(i, NAME_COL_INDEX).toString());
        }
        attrList.removeIf(item -> item.equals(""));
        return attrList;
    }
    public static void setUsedInMCD(String attributeName, String name) {
        int row = getAttributeList().indexOf(attributeName);
        tableModel.setValueAt(name, row, MCD_COL_INDEX);
    }
    private boolean isUsedInMCD(int selectedRow) {
        return !tableModel.getValueAt(selectedRow, MCD_COL_INDEX).toString().isEmpty();
    }
    // MCD methods end

    public static Map<String, String> getProperty(String string) {
        int nRow = tableModel.getRowCount();
        Map<String, String> prop = new HashMap<>();
        for (int i = 0; i < nRow; i++) {
            if (tableModel.getValueAt(i, NAME_COL_INDEX).toString().equals(string)) {
                prop.put("name", tableModel.getValueAt(i, NAME_COL_INDEX).toString());
                prop.put("type", tableModel.getValueAt(i, TYPE_COL_INDEX).toString());
                prop.put("length", tableModel.getValueAt(i, LENGTH_COL_INDEX).toString());
                break;
            }
        }
        return prop;
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
            tableModel.addRow(dataRow);
            tableModel.setValueAt(combo.getItemAt(0), ddTable.getRowCount() - 1, 1);
        }
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

        