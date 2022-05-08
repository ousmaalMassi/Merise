package com.MeriseGUI.rules;

import javax.swing.*;

public class ManagementRulesPanel extends JPanel{

    public ManagementRulesPanel() {
        JTextArea textArea = new JTextArea();
        textArea.setRows(50);
        textArea.setColumns(50);
        add(textArea);
    }
}
