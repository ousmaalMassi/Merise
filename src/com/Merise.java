package com;

import com.MeriseGUI.flow.FlowPanel;
import com.MeriseGUI.mld.MLDPanel;
import com.MeriseGUI.mpd.MPDPanel;
import com.MeriseGUI.rules.ManagementRulesPanel;
import com.mcd.MCDGraph;
import com.MeriseGUI.mcd.MCDPanel;
import com.mld.MLDGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Merise extends JFrame {

    private JMenuBar jMenuBar;
    private MCDPanel mcdPanel;
    private MLDPanel mldPanel;
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

    }

    private void initComponents() {
        createToolBar();
        addBtnListeners();

        ManagementRulesPanel managementRulesPanel = new ManagementRulesPanel();
        FlowPanel flowPanel = new FlowPanel();
        mcdPanel = new MCDPanel();
        mldPanel = new MLDPanel();
        MPDPanel mpdPanel = new MPDPanel();
        JTabbedPane jTabbedPane = new JTabbedPane();
        jTabbedPane.add("Régles de gestion", managementRulesPanel);
        jTabbedPane.add("flow", flowPanel);
        jTabbedPane.add("mcd", mcdPanel);
        jTabbedPane.add("mld", mldPanel);
        jTabbedPane.add("mpd", mpdPanel);
        add(jTabbedPane, BorderLayout.CENTER);
//        add(jpn, BorderLayout.WEST);
        add(toolBar, BorderLayout.NORTH );
//        setJMenuBar(jMenuBar);





//        long currentTimeMillis = System.currentTimeMillis();
//        mcdPanel = new MCDPanel();
//        transform = new Transform();
//        MCDGraph mcdGraph = mcdPanel.getMcdGraph();
//        MLDGraph mldGraph = transform.mcdToMld(mcdGraph);
        //System.out.println(mldGraph);
        //System.out.println(mcdGraph);
        //System.out.println(transform.mpdToSQL(mldGraph));
        //System.out.println(System.currentTimeMillis() - currentTimeMillis+" ms");

        /*DataDictionary dataDictionary = new DataDictionary();
        List<Property> propertyList = new ArrayList<>();
        propertyList.add(new Property("id", Property.Types.INT, 11, Arrays.asList(Property.Constraints.PRIMARY_KEY, Property.Constraints.AUTO_INCREMENT)));
        propertyList.add(new Property("nom", Property.Types.INT, 11, List.of(Property.Constraints.NOT_NULL)));
        dataDictionary.setDictionary(new HashMap<>(){{ put("test", propertyList); }});
        dataDictionary.addData("test", new Property("nom", Property.Types.INT, 11, List.of(Property.Constraints.NOT_NULL)));
        dataDictionary.removeData("test", "nom");
        System.out.println(dataDictionary);*/

        /*DataDictionary dataDictionary = new DataDictionary();
        GDFGraph gdfGraph = new GDFGraph();

        dataDictionary
                .addData(new Property("id", Property.Types.DIGITAL, 11, List.of(Property.Constraints.AUTO_INCREMENT)))
                .addData(new Property("nom", Property.Types.ALPHABETICAL, 11, List.of(Property.Constraints.NOT_NULL)))
                .addData(new Property("prénom", Property.Types.ALPHABETICAL, 11, List.of(Property.Constraints.NOT_NULL)))
                .addData(new Property("adresse", Property.Types.ALPHANUMERIC, 11, List.of(Property.Constraints.NOT_NULL)))
                .addData(new Property("id article", Property.Types.DIGITAL, 11, List.of(Property.Constraints.AUTO_INCREMENT)))
                .addData(new Property("prix d'achat", Property.Types.ALPHANUMERIC, 11, List.of(Property.Constraints.NOT_NULL)))
                .addData(new Property("prix de vente", Property.Types.ALPHANUMERIC, 30, List.of(Property.Constraints.NOT_NULL)))
                .addData(new Property("designation", Property.Types.ALPHABETICAL, 11, List.of(Property.Constraints.NOT_NULL)));

        Property id = dataDictionary.searchProperty("id");
        Property nom = dataDictionary.searchProperty("nom");
        Property adresse = dataDictionary.searchProperty("adresse");

        DFNode dfNode = new DFNode(id.getName());
        dfNode.addTarget(nom.getName());
        dfNode.addTarget(adresse.getName());

        //DFNode dfNode = new DFNode(id.getName());
        //TODO recursive declaration, DFNode or Property directly

        gdfGraph.addDfNodes(dfNode);

        System.out.println(gdfGraph);*/

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
            //MPDGraph mpdGraph = transform.mldToMpd(mldGraph);
//            System.out.println(mldGraph);
//            System.out.println(mcdGraph);
            mldPanel.setMldGraph(mldGraph);
            //mpdPanel.setMpdGraph(mpdGraph);
        });
        btnExit.addActionListener((ActionEvent e) -> this.dispose());

    }
}
