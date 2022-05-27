package com.MeriseGUI.ddd;

import javax.swing.table.AbstractTableModel;

class TableModel extends AbstractTableModel {
    private Object[][] data;
    private final String[] title;

    public TableModel(Object[][] data, String[] title) {
        this.data = data;
        this.title = title;
    }

    /**
     * Retourne le titre de la colonne à l'indice spécifé
     */
    @Override
    public String getColumnName(int col) {
        return this.title[col];
    }

    /**
     * Retourne le nombre de colonnes
     */
    @Override
    public int getColumnCount() {
        return this.title.length;
    }

    /**
     * Retourne le nombre de lignes
     */
    @Override
    public int getRowCount() {
        return this.data.length;
    }

    /**
     * Retourne la valeur à l'emplacement spécifié
     */
    @Override
    public Object getValueAt(int row, int col) {
        return this.data[row][col];
    }

    /**
     * Défini la valeur à l'emplacement spécifié
     */
    @Override
    public void setValueAt(Object value, int row, int col) {
        //On interdit la modification sur certaine colonne !
        //if(!this.getColumnName(col).equals("Age") && !this.getColumnName(col).equals("Suppression"))
        this.data[row][col] = value;
    }

    /**
     * @param col
     */
    @Override
    public Class getColumnClass(int col) {
        return this.data[0][col].getClass();
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

    /**
     * Permet d'ajouter une ligne dans le tableau
     *
     * @param data
     */
    public void addRow(Object[] data) {
        int indice = 0, nbRow = this.getRowCount(), nbCol = this.getColumnCount();
        Object[][] temp = this.data;
        this.data = new Object[nbRow + 1][nbCol];
        for (Object[] value : temp)
            this.data[indice++] = value;
        this.data[indice] = data;
        this.fireTableDataChanged();
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return switch (col) {
            case 3, 4 -> false;
            default -> true;
        };
    }

    public Object[][] getTableData() {
        int nRow = this.getRowCount() - 1;
        int nCol = this.getColumnCount();
        Object[][] tableData = new Object[nRow][nCol];

        for (int i = 0; i < nRow; i++) {
            for (int j = 0; j < nCol; j++) {
                tableData[i][j] = this.getValueAt(i, j).toString();
            }
        }
        return tableData;
    }
}