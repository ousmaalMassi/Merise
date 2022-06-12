package com.MeriseGUI.rules;

import javax.swing.table.AbstractTableModel;

public class RulesTableModel  extends AbstractTableModel {
    private Object[][] data;
    private final String[] titles;

    public RulesTableModel(Object[][] data, String[] titles) {
        this.data = data;
        this.titles = titles;
    }

    @Override
    public int getRowCount() {
        return this.data.length;
    }

    @Override
    public int getColumnCount() {
        return this.titles.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return this.data[rowIndex][columnIndex];
    }

    @Override
    public boolean isCellEditable(int row, int col) {
         return col != 0;
    }

    @Override
    public Class getColumnClass(int col) {
        return this.data[0][col].getClass();
    }

    @Override
    public String getColumnName(int col) {
        return this.titles[col];
    }

    public void addRow(Object[] data) {
        int indice = 0, nbRow = this.getRowCount(), nbCol = this.getColumnCount();
        Object[][] temp = this.data;
        this.data = new Object[nbRow + 1][nbCol];
        for (Object[] value : temp)
            this.data[indice++] = value;
        this.data[indice] = data;
        this.fireTableDataChanged();
    }
    public void removeRow(int position) {
        int indice = 0, indice2 = 0, nbRow = this.getRowCount() - 1, nbCol = this.getColumnCount();
        Object[][] temp = new Object[nbRow][nbCol];
        for (Object[] value : this.data) {
            if (indice != position) {
                temp[indice2++] = value;
            }
            indice++;
        }
        data = temp;
        this.fireTableDataChanged();
    }

}
