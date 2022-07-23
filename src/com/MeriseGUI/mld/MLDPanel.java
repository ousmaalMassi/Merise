package com.MeriseGUI.mld;

import com.models.mld.MLDGraph;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MLDPanel extends JPanel {
    private final JTextArea textArea;
    private MLDGraph mldGraph;

    public MLDPanel() {
        textArea = new JTextArea();
        textArea.setRows(50);
        textArea.setColumns(100);
        textArea.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));
        textArea.setBorder(new EmptyBorder(10,10,10,10));
        textArea.setEditable(false);

        mldGraph = new MLDGraph();
        this.setLayout(new BorderLayout());
        this.add(textArea, BorderLayout.CENTER);
        this.setBorder(new EmptyBorder(10,10,10,10));
    }

    public MLDGraph getMldGraph() {
        return mldGraph;
    }

    public void setMldGraph(MLDGraph mldGraph) {
        this.mldGraph = mldGraph;
        this.textArea.setText(mldGraph.toString());
    }
}
