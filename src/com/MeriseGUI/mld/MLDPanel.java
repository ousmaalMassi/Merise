package com.MeriseGUI.mld;

import com.mld.MLDGraph;

import javax.swing.*;

public class MLDPanel extends JPanel {
    private JTextArea textArea;
    private MLDGraph mldGraph;

    public MLDPanel() {
        textArea = new JTextArea();
        textArea.setRows(50);
        textArea.setColumns(50);
        textArea.setEditable(false);
        add(textArea);
    }

    public MLDGraph getMldGraph() {
        return mldGraph;
    }

    public void setMldGraph(MLDGraph mldGraph) {
        this.mldGraph = mldGraph;
        this.textArea.setText(mldGraph.toString());
    }
}
