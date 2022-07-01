package com.MeriseGUI.ddd;

import javax.swing.table.AbstractTableModel;
import java.io.*;

class TableModel extends AbstractTableModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private final String[] title;
    private Object[][] data;

    public TableModel(String[] title) {
        this.title = title;
        this.data = new Object[][]{};
//        this.data = new Object[][]{
//                {"id client", "id_client", Property.Types.NUMERIC, "11", "", false, ""},
//                {"nom", "nom", Property.Types.ALPHABETICAL, "50", "", false, ""},
//                {"prénom", "prénom", Property.Types.ALPHABETICAL, "50", "", false, ""},
//                {"adresse", "adresse", Property.Types.ALPHANUMERIC, "256", "", false, ""},
//                {"id article", "id_article", Property.Types.NUMERIC, "11", "", false, ""},
//                {"prix achat", "prix_achat", Property.Types.NUMERIC, "10", "", false, ""},
//                {"prix vente", "prix_vente", Property.Types.NUMERIC, "10", "", false, ""},
//                {"designation", "designation", Property.Types.ALPHANUMERIC, "256", "", false, ""},
//                {"quantity", "quantity", Property.Types.NUMERIC, "2", "", false, ""},
//        };
    }

    @Override
    public String getColumnName(int col) {
        return this.title[col];
    }

    @Override
    public int getColumnCount() {
        return this.title.length;
    }

    @Override
    public int getRowCount() {
        return this.data.length;
    }

    @Override
    public Object getValueAt(int row, int col) {
        return this.data[row][col];
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        this.data[row][col] = value;
    }

    @Override
    public Class getColumnClass(int col) {
        return this.data[0][col].getClass();
    }

    public void removeRow(int position) {
        int index = 0, index2 = 0, nbRow = this.getRowCount() - 1, nbCol = this.getColumnCount();
        Object[][] temp = new Object[nbRow][nbCol];
        for (Object[] value : this.data) {
            if (index != position)
                temp[index2++] = value;
            index++;
        }
        data = temp;
        this.fireTableRowsDeleted(0, nbCol);
    }

    public void addRow(Object[] data) {
        int index = 0, nbRow = this.getRowCount(), nbCol = this.getColumnCount();
        Object[][] temp = this.data;
        this.data = new Object[nbRow + 1][nbCol];
        for (Object[] value : temp)
            this.data[index++] = value;
        this.data[index] = data;
        this.fireTableDataChanged();
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return switch (col) {
            case 4, 5 -> false;
            default -> true;
        };
    }

    public Object[][] getData() {
        return this.data;
    }

    public void setData(Object[][] dataDictionary) {
        this.data = dataDictionary;
        this.fireTableDataChanged();
    }
}