package com.MeriseGUI.rules;

import javax.swing.table.AbstractTableModel;
import java.io.Serial;
import java.io.Serializable;

public class RulesTableModel extends AbstractTableModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Object[][] data;
    private final String[] titles;

    public RulesTableModel(String[] titles) {
        this.titles = titles;
        this.data = new Object[][]{};
//        this.data = new Object[][]{
//                {"1", "Chaque professeur est caractérisé par son code, son nom, son prénom, son adresse, son grade et son nombre d’heures d’enseignement selon son grade."},
//                {"2", "Un professeur peut enseigner plusieurs modules"}
//        };
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
        int index = 0, nbRow = this.getRowCount(), nbCol = this.getColumnCount();
        Object[][] temp = this.data;
        this.data = new Object[nbRow + 1][nbCol];
        for (Object[] value : temp)
            this.data[index++] = value;
        this.data[index] = data;
        this.fireTableDataChanged();
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        this.data[row][col] = value;
    }

    public void removeRow(int position) {
        int index = 0, index2 = 0, nbRow = this.getRowCount() - 1, nbCol = this.getColumnCount();
        Object[][] temp = new Object[nbRow][nbCol];
        for (Object[] value : this.data) {
            if (index != position) {
                temp[index2++] = value;
            }
            index++;
        }
        data = temp;
        this.fireTableDataChanged();
    }

    public Object[][] getData() {
        return this.data;
    }

    public void setData(Object[][] data) {
        this.data = data;
        this.fireTableDataChanged();
    }

}
