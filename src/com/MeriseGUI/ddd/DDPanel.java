package com.MeriseGUI.ddd;

import com.MeriseGUI.DeleteButtonRenderer;
import com.MeriseGUI.MTableModel;
import com.models.Property;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * @author rpc
 */
public class DDPanel extends JPanel {

    private static final String NAME = "Nom";
    private static final String CODE = "Code";
    private static final String TYPE = "Type";
    private static final String LENGTH = "Taille";
    private static final String MCD = "MCD";
    private static final String GDF = "GDF";
    private static final String DELETE = "";
    private static final int COLUMN_HEIGHT = 35;
    public static int NAME_COL_INDEX;
    public static int CODE_COL_INDEX;
    public static int TYPE_COL_INDEX;
    public static int LENGTH_COL_INDEX;
    public static int GDF_COL_INDEX;
    public static int MCD_COL_INDEX;
    private static JTable ddTable;
    private static MTableModel tableModel;
    private JComboBox<Property.Types> combo;

    public DDPanel() {
        initComponents();
//        loadData(DDTableModel.getData());
    }

    private void initComponents() {
        Property.Types[] comboData = new Property.Types[]{Property.Types.ALPHABETICAL, Property.Types.ALPHANUMERIC, Property.Types.DATE, Property.Types.NUMERIC, Property.Types.LOGIC};
        combo = new JComboBox(comboData);

        tableModel = new DDTableModel(new String[]{NAME, CODE, TYPE, LENGTH, MCD, GDF, DELETE});
        ddTable = new JTable(tableModel);

        NAME_COL_INDEX = ddTable.getColumn(NAME).getModelIndex();
        CODE_COL_INDEX = ddTable.getColumn(CODE).getModelIndex();
        TYPE_COL_INDEX = ddTable.getColumn(TYPE).getModelIndex();
        LENGTH_COL_INDEX = ddTable.getColumn(LENGTH).getModelIndex();
        MCD_COL_INDEX = ddTable.getColumn(MCD).getModelIndex();
        GDF_COL_INDEX = ddTable.getColumn(GDF).getModelIndex();

        ddTable.getColumn(TYPE).setCellEditor(new DefaultCellEditor(combo));
        ddTable.getColumn(DELETE).setCellRenderer(new DeleteButtonRenderer(ddTable));
        ddTable.getColumn(DELETE).setCellEditor(new DeleteButtonRenderer(ddTable));
        ddTable.getColumn(DELETE).setMaxWidth(50);
        ddTable.setRowHeight(COLUMN_HEIGHT);

        /*JTextField name = new JTextField();
        name.setColumns(20);
        JTextField code = new JTextField();
        code.setColumns(20);
        JTextField length = new JTextField();
        length.setColumns(20);*/

        JButton addRowBtn = new JButton();
        addRowBtn.setText("Ajouter une donnée");
        addRowBtn.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
        addRowBtn.addActionListener(e -> addData());


        JPanel footer = new JPanel();
        footer.setLayout(new BorderLayout());
//        footer.add(name);
//        footer.add(code);
//        footer.add(length);
//        footer.add(combo);
        footer.add(addRowBtn, BorderLayout.EAST);

        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setViewportView(ddTable);

        this.setLayout(new BorderLayout());
        this.add(footer, BorderLayout.SOUTH);
        this.add(jScrollPane, BorderLayout.CENTER);
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
    }

    private static String normalize(String name) {
        return name.replace(" ", "_");
    }

    /**************** GDF methods start ****************/
    public static Vector<String> getDataList() {
        int nRow = tableModel.getRowCount();
        Vector<String> attrList = new Vector<>();
        for (int i = 0; i < nRow; i++) {
            attrList.add(tableModel.getValueAt(i, CODE_COL_INDEX).toString());
        }
        attrList.removeIf(item -> item.equals(""));
        return attrList;
    }

    public static Vector<String> getDataForGDF() {
        int nRow = tableModel.getRowCount();
        Vector<String> attrList = new Vector<>();
        for (int i = 0; i < nRow; i++) {
            if (tableModel.getValueAt(i, GDF_COL_INDEX).toString().equals("false"))
                attrList.add(tableModel.getValueAt(i, NAME_COL_INDEX).toString());

        }
        attrList.removeIf(item -> item.equals(""));
        return attrList;
    }

    public static void setUsedInGDF(String dataName, boolean used) {
        int row = getDataList().indexOf(normalize(dataName));
        if (row != -1)
            tableModel.setValueAt(used, row, GDF_COL_INDEX);
    }

    /**************** end ****************/

    /**************** MCD methods start ****************/
    public static Vector<String> getDataForMCD() {
        int nRow = tableModel.getRowCount();
        Vector<String> attrList = new Vector<>();
        for (int i = 0; i < nRow; i++) {
            if (tableModel.getValueAt(i, MCD_COL_INDEX).toString().isEmpty())
                attrList.add(tableModel.getValueAt(i, NAME_COL_INDEX).toString());
        }
        attrList.removeIf(item -> item.equals(""));
        return attrList;
    }

    public static void setUsedInMCD(String dataName, String name) {
        int row = getDataList().indexOf(normalize(dataName));
        tableModel.setValueAt(name, row, MCD_COL_INDEX);
    }

    /**************** end ****************/

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

    private void addData() {
        String name = JOptionPane.showInputDialog(this, "Veuillez entrer le nom de la donnée", "fgjhgjh");
        if (name == null)
            return;
        String code = normalize(name);
        if (getDataList().contains(code)) {
            JOptionPane.showMessageDialog(this, "Cette donnée existe déjà!");
            return;
        }
        tableModel.addRow(this.createEmptyDataRow(name, code));
        tableModel.setValueAt(combo.getItemAt(0), ddTable.getRowCount() - 1, TYPE_COL_INDEX);
    }

    private Object[] createEmptyDataRow(String name, String code) {
        return new Object[]{name, code, "", "0", "", false, ""};
    }

    private boolean isUsedInGDF(int selectedRow) {
        return tableModel.getValueAt(selectedRow, GDF_COL_INDEX).toString().equals("true");
    }

    private boolean isUsedInMCD(int selectedRow) {
        return !tableModel.getValueAt(selectedRow, MCD_COL_INDEX).toString().isEmpty();
    }

    void PrintDataTable() {
        Object[][] tableData = tableModel.getData();
        for (Object[] tableRow : tableData) {
            for (Object tableColumn : tableRow) {
                System.out.println(tableColumn);
            }
        }
    }

    public Object[][] getData() {
        return tableModel.getData();
    }

    public void setData(Object[][] dataDictionary) {
        tableModel.setData(dataDictionary);
    }
}

        