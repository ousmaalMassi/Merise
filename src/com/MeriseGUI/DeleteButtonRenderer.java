package com.MeriseGUI;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.EventObject;

public class DeleteButtonRenderer implements TableCellRenderer, TableCellEditor {
    private final JButton btn;
    private int row;

    public DeleteButtonRenderer(JTable table) {
        btn = new JButton(new ImageIcon( "icons/delete.png"));
        btn.addActionListener(e -> {
            MTableModel model = (MTableModel) table.getModel();
//                if (isUsedInMCD(row) || isUsedInGDF(row))
//                    JOptionPane.showMessageDialog(this, "Cet Attribut est en cours d'utilisation!");
//                else
            model.removeRow(row);
        });
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object
            value, boolean isSelected, boolean hasFocus, int row, int column) {
        return btn;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object
            value, boolean isSelected, int row, int column) {
        this.row = row;
        return btn;
    }

    @Override
    public Object getCellEditorValue() {
        return true;
    }

    @Override
    public boolean isCellEditable(EventObject anEvent) {
        return true;
    }

    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        return true;
    }

    @Override
    public boolean stopCellEditing() {
        return true;
    }

    @Override
    public void cancelCellEditing() {
    }

    @Override
    public void addCellEditorListener(CellEditorListener l) {
    }

    @Override
    public void removeCellEditorListener(CellEditorListener l) {
    }
}
