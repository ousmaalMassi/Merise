package com;

import com.mcd.MCDPanel;

import javax.swing.*;

public class Merise extends JFrame {

    MCDPanel mcdPanel;

    public Merise() {
        initComponents();
        setTitle("merice_v2_pfe");
        setSize(1500, 1000);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(false);
    }

    private void initComponents() {
        mcdPanel = new MCDPanel();
        System.out.println("Merise");
    }
}
