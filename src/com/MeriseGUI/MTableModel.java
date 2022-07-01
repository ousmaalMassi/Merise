package com.MeriseGUI;

import javax.swing.table.AbstractTableModel;
import java.io.Serial;
import java.io.Serializable;

public class MTableModel extends AbstractTableModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    protected final String[] title;
    protected Object[][] data;

    public MTableModel(String[] title) {
        this.title = title;
        this.data = new Object[][]{};
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
        return false;
    }

    public Object[][] getData() {
        return this.data;
    }

    public void setData(Object[][] data) {
        this.data = data;
        this.fireTableDataChanged();
    }
}
