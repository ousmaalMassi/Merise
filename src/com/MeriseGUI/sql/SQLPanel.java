package com.MeriseGUI.sql;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SQLPanel extends JPanel {
    private JTextArea textArea;
    private String[] DBMS_LIST = new String[] {"MySQL","PostgresSQL","SQLServer"} ;
//    private JButton generateScript = new JButton("Generate Script ->");
    private JComboBox<String> targetedDBMS;
    public SQLPanel() {
        targetedDBMS = new JComboBox<>(DBMS_LIST);
        JToolBar jToolBar = new JToolBar();
        jToolBar.add(targetedDBMS);
        jToolBar.addSeparator();
//        jToolBar.add(generateScript);

        textArea = new JTextArea();
        textArea.setRows(50);
        textArea.setColumns(200);
        textArea.setEditable(false);
        textArea.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));
        textArea.setBorder(new EmptyBorder(10,10,10,10));

        this.setLayout(new BorderLayout());
        this.add(jToolBar, BorderLayout.NORTH);
        this.add(textArea, BorderLayout.CENTER);
        this.setBorder(new EmptyBorder(10,10,10,10));
    }

    public void setSQLScript(String sqlScript) {
        this.textArea.setText(sqlScript);
    }
}
