package com;

import com.mcd.MCDPanel;

import javax.swing.*;

public class Merise extends JFrame {

    MCDPanel mcdPanel;

    public Merise() {
        initComponents();
        setTitle("Merice_v2_pfe");
        setSize(192, 108);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initComponents() {
        mcdPanel = new MCDPanel();
    }
}
