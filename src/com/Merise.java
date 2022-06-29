package com;

import com.MeriseGUI.ddd.DDPanel;
import com.MeriseGUI.flow.FlowPanel;
import com.MeriseGUI.gdf.GDFPanel;
import com.MeriseGUI.mcd.MCDPanel;
import com.MeriseGUI.mld.MLDPanel;
import com.MeriseGUI.mpd.MPDPanel;
import com.MeriseGUI.rules.ManagementRulesPanel;
import com.MeriseGUI.sql.SQLPanel;
import com.graphics.GArrow;
import com.graphics.GNode;
import com.graphics.gdf.DF;
import com.graphics.gdf.GDFAttribute;
import com.graphics.mcd.GMCDLink;
import com.graphics.mcd.GMCDNode;
import com.models.Transform;
import com.models.gdf.GDFGraph;
import com.models.mcd.MCDGraph;
import com.models.mld.MLDGraph;
import com.models.mpd.MPDGraph;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Merise extends JFrame {
    private JFileChooser fc;
    private static final String FILE_EXTENSION = "merise";
    private static final String AUTO_SAVE_FILE = "merise_auto_saved";
    private JMenuBar jMenuBar;
    private FlowPanel flowPanel;
    private DDPanel ddPanel;
    private GDFPanel gdfPanel;
    private MCDPanel mcdPanel;
    private MLDPanel mldPanel;
    private MPDPanel mpdPanel;
    private SQLPanel sqlPanel;
    private Transform transform;
    private JButton btnNew;
    private JButton btnOpen;
    private JButton btnSaveAs;
    private JButton btnExit;
    private JButton btnGenerate;
    private JButton btnGdfToMcd;
    private JButton btnGrid;
    private JToolBar toolBar;
    private Map<String, Object> meriseData;

    public Merise() {
        initComponents();
        setTitle("Merise_v2_pfe");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        System.out.println("\uD83D\uDE00");

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                int resp = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to exit?",
                        "Exit?", JOptionPane.YES_NO_OPTION);
                if (resp == JOptionPane.YES_OPTION) {
                    try {
                        serialize(AUTO_SAVE_FILE.replaceAll(FILE_EXTENSION,FILE_EXTENSION));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                } else {
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }

            @Override
            public void windowClosed(WindowEvent event) {
            }
        });
    }

    private void initComponents() {
        createToolBar();
        addBtnListeners();

        fc = new JFileChooser();
        fc.setAcceptAllFileFilterUsed(false);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.addChoosableFileFilter(new FileNameExtensionFilter("merise diagrams", FILE_EXTENSION));

        meriseData = new HashMap<>();

        transform = new Transform();

        flowPanel = new FlowPanel();
        ManagementRulesPanel managementRulesPanel = new ManagementRulesPanel();
        ddPanel = new DDPanel();
        gdfPanel = new GDFPanel();
        mcdPanel = new MCDPanel();
        mldPanel = new MLDPanel();
        mpdPanel = new MPDPanel();
        sqlPanel = new SQLPanel();

        JTabbedPane jTabbedPane = new JTabbedPane();
        jTabbedPane.add("Diagramme de flux", flowPanel);
        jTabbedPane.add("Régles de gestion", managementRulesPanel);
        jTabbedPane.add("Dictionnaire de données", ddPanel);
        jTabbedPane.add("GDF", gdfPanel);
        jTabbedPane.add("MCD", mcdPanel);
        jTabbedPane.add("MLD", mldPanel);
        jTabbedPane.add("MPD", mpdPanel);
        jTabbedPane.add("SQL", sqlPanel);
        add(jTabbedPane, BorderLayout.CENTER);
//        add(jpn, BorderLayout.WEST);
        add(toolBar, BorderLayout.NORTH);
//        setJMenuBar(jMenuBar);

        pack();
    }

    private void createToolBar() {
        toolBar = new JToolBar();
        btnNew = initToolBarBtn("new_file", "nouveau fichier (CTRL+N)", true);
        btnOpen = initToolBarBtn("open_folder", "Ouvrir un nouveau projet (CTRL+O)", true);
        btnSaveAs = initToolBarBtn("save_as", "Enregistrer sous (CTRL+SHIFT+S)", true);
        btnGdfToMcd = initToolBarBtn("generate2", "Générer MCD à partir de GDF", true);
        btnGenerate = initToolBarBtn("generate", "Générer le MLD & le MPD (F6)", true);
//        btnExit = initToolBarBtn("exit", "Exit (ALT+F4)", true);
        btnGrid = initToolBarBtn("grid", "Grille (CTRL+G)", false);

        //toolBar.setBackground(Color.DARK_GRAY);
        toolBar.setOrientation(SwingConstants.HORIZONTAL);
    }

    private JButton initToolBarBtn(String icon, String toolTip, Boolean separator) {
        JButton btn = new JButton(new ImageIcon("icons/" + icon + ".png"));
        btn.setFocusable(false);
        btn.setToolTipText(toolTip);
        toolBar.add(btn);
        if (separator)
            toolBar.addSeparator();
        return btn;
    }

    private void addBtnListeners() {
        btnGrid.addActionListener((ActionEvent e) -> {
        });
        btnNew.addActionListener((ActionEvent e) -> {
            //gridEnabled = !gridEnabled;
        });
        btnOpen.addActionListener((ActionEvent e) -> openDiagrams());

        btnSaveAs.addActionListener((ActionEvent e) -> askToSave());

        btnGenerate.addActionListener((ActionEvent e) -> {
            MLDGraph mldGraph = transform.mcdToMld(mcdPanel.getGraph());
            MPDGraph mpdGraph = transform.mldToMpd(mldGraph);
            mldPanel.setMldGraph(mldGraph);
            mpdPanel.setMpdGraph(mpdGraph);
            sqlPanel.setSQLScript(transform.mpdToSQL(mpdGraph));
            JOptionPane.showMessageDialog(this, "Terminé!");
        });

        btnGdfToMcd.addActionListener((ActionEvent e) -> {
            GDFGraph gdfGraph = gdfPanel.getGraph();
            MCDGraph mcdGraph = transform.gdfToMcd(gdfGraph);
            mcdPanel.setMcdGraph(mcdGraph);
            JOptionPane.showMessageDialog(this, "Terminé!");
        });

//        btnExit.addActionListener((ActionEvent e) -> this.dispose());

    }

    private void openDiagrams() {
        if (fc.showOpenDialog(this) == JFileChooser.CANCEL_OPTION)
            return;

        File file = fc.getSelectedFile();
        System.out.println("Opening: " + file.getName());
        try {
            deserialize(file);
        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void askToSave() {
        fc.setDialogTitle("Specify a file to save");
        int option = fc.showSaveDialog(this);
        if(option == JFileChooser.APPROVE_OPTION){
            String fileName = fc.getSelectedFile().getAbsolutePath();
            try {
                serialize(fileName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("File Saved as: " + fileName);
        }else{
            System.out.println("Save command canceled");
        }
    }

    public void serialize(String fileName) throws IOException {

        fileName = normalizeName(fileName);

        meriseData.put("flowNodes", flowPanel.getNodes());
        meriseData.put("flowLinks", flowPanel.getLinks());

        meriseData.put("DataDictionary", ddPanel.getData());

        meriseData.put("gdfNodes", gdfPanel.getNodes());
        meriseData.put("gdfLinks", gdfPanel.getLinks());
        meriseData.put("gdfGraph", gdfPanel.getGraph());

        meriseData.put("mcdNodes", mcdPanel.getNodes());
        meriseData.put("mcdLinks", mcdPanel.getLinks());
        meriseData.put("mcdGraph", mcdPanel.getGraph());

        ObjectOutputStream oos;
        oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
        oos.writeObject(meriseData);
        oos.close();
    }

    private String normalizeName(String fileName) {
        String suffix = "." + FILE_EXTENSION;
        if (!fileName.endsWith(suffix))
            return fileName.trim()+suffix;
        return fileName.trim().replaceAll("(.merise)+", suffix);
    }

    public void deserialize(File file) throws IOException, ClassNotFoundException {
        this.meriseData = deserializeObject(file);

        System.out.println("opening ...");

        flowPanel.setNodes((List<GNode>) this.meriseData.get("flowNodes"));
        flowPanel.setLinks((List<GArrow>) this.meriseData.get("flowLinks"));
        flowPanel.repaint();

        ddPanel.setData((Object[][]) this.meriseData.get("DataDictionary"));

        gdfPanel.setNodes((List<GDFAttribute>) this.meriseData.get("gdfNodes"));
        gdfPanel.setLinks((List<DF>) this.meriseData.get("gdfLinks"));
        gdfPanel.setGraph((GDFGraph) this.meriseData.get("flowGraph"));
        gdfPanel.repaint();

        mcdPanel.setNodes((List<GMCDNode>) this.meriseData.get("mcdNodes"));
        mcdPanel.setLinks((List<GMCDLink>) this.meriseData.get("mcdLinks"));
        mcdPanel.setGraph((MCDGraph) this.meriseData.get("mcdGraph"));
        mcdPanel.repaint();
    }

    private Map<String, Object> deserializeObject(File file) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(
                new BufferedInputStream(
                        new FileInputStream(file)));
        return (Map<String, Object>) ois.readObject();
    }

}
