package com;

import com.MeriseGUI.ddd.DDPanel;
import com.MeriseGUI.flow.FlowPanel;
import com.MeriseGUI.gdf.GDFPanel;
import com.MeriseGUI.mld.MLDPanel;
import com.MeriseGUI.mpd.MPDPanel;
import com.MeriseGUI.rules.ManagementRulesPanel;
import com.MeriseGUI.sql.SQLPanel;
import com.mcd.MCDGraph;
import com.MeriseGUI.mcd.MCDPanel;
import com.mld.MLDGraph;
import com.mpd.MPDGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Merise extends JFrame {

    private JMenuBar jMenuBar;
    private MCDPanel mcdPanel;
    private MLDPanel mldPanel;
    private MPDPanel mpdPanel;
    private SQLPanel sqlPanel;
    private Transform transform;
    private JButton btnNew;
    private JButton btnSave;
    private JButton btnSaveAs;
    private JButton btnExit;
    private JButton btnGenerate;
    private JButton btnGrid;
    private JToolBar toolBar;

    public Merise() {
        initComponents();
        setTitle("Merise_v2_pfe");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        System.out.println("\uD83D\uDE00");
    }

    private void initComponents() {
        createToolBar();
        addBtnListeners();

        ManagementRulesPanel managementRulesPanel = new ManagementRulesPanel();
        DDPanel ddPanel = new DDPanel();
        GDFPanel gdfGraph = new GDFPanel();
        FlowPanel flowPanel = new FlowPanel();
        mcdPanel = new MCDPanel();
        mldPanel = new MLDPanel();
        mpdPanel = new MPDPanel();
        sqlPanel = new SQLPanel();

        JTabbedPane jTabbedPane = new JTabbedPane();
        jTabbedPane.add("Diagramme de flux", flowPanel);
        jTabbedPane.add("Régles de gestion", managementRulesPanel);
        jTabbedPane.add("Dictionnaire de données", ddPanel);
        jTabbedPane.add("GDF", gdfGraph);
        jTabbedPane.add("MCD", mcdPanel);
        jTabbedPane.add("MLD", mldPanel);
        jTabbedPane.add("MPD", mpdPanel);
        jTabbedPane.add("SQL", sqlPanel);
        add(jTabbedPane, BorderLayout.CENTER);
//        add(jpn, BorderLayout.WEST);
        add(toolBar, BorderLayout.NORTH );
//        setJMenuBar(jMenuBar);

        pack();
    }

    private void createToolBar() {
        toolBar = new JToolBar();
        btnNew = initToolBarBtn("new_file", "nouveau fichier (CTRL+N)", true);
        btnSave = initToolBarBtn("open_folder", "Ouvrir un nouveau projet (CTRL+O)", true);
        btnSaveAs = initToolBarBtn("save_as", "Enregistrer sous (CTRL+SHIFT+S)", true);
        btnGenerate = initToolBarBtn("generate", "Générer l'MLD & l'MPD (F6)", true);
        btnExit = initToolBarBtn("exit", "Exit (ALT+F4)", true);
        btnGrid = initToolBarBtn("grid", "Grille (CTRL+G)", false);

        //toolBar.setBackground(Color.DARK_GRAY);
        toolBar.setOrientation(SwingConstants.HORIZONTAL);
    }

    private JButton initToolBarBtn(String icon, String toolTip, Boolean separator) {
        JButton btn = new JButton( new ImageIcon( "icons/"+icon+".png") );
        btn.setFocusable(false);
        btn.setToolTipText( toolTip );
        toolBar.add(btn);
        if(separator)
            toolBar.addSeparator();
        return btn;
    }

    private void addBtnListeners() {
        btnGrid.addActionListener((ActionEvent e) -> {
        });
        btnNew.addActionListener((ActionEvent e) -> {
            //gridEnabled = !gridEnabled;
        });
        btnSave.addActionListener((ActionEvent e) -> {
            //gridEnabled = !gridEnabled;
        });
        btnSaveAs.addActionListener((ActionEvent e) -> {
            //gridEnabled = !gridEnabled;
        });
        btnGenerate.addActionListener((ActionEvent e) -> {
            transform = new Transform();
            MCDGraph mcdGraph = mcdPanel.getMcdGraph();
            MLDGraph mldGraph = transform.mcdToMld(mcdGraph);
            MPDGraph mpdGraph = transform.mldToMpd(mldGraph);
            mldPanel.setMldGraph(mldGraph);
            mpdPanel.setMpdGraph(mpdGraph);
            sqlPanel.setSQLScript(transform.mpdToSQL(mpdGraph));
        });

        btnExit.addActionListener((ActionEvent e) -> this.dispose());

    }
}
