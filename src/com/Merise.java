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
import com.graphics.gdf.GNodeGDF;
import com.graphics.gdf.GSimpleDF;
import com.graphics.mcd.GMCDLink;
import com.graphics.mcd.GMCDNode;
import com.models.Transform;
import com.models.gdf.GDFGraph;
import com.models.mcd.MCDGraph;
import com.models.mld.MLDGraph;
import com.models.mpd.MPDGraph;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Merise extends JFrame {
    private static final String WINDOW_TITLE = "New Merise_v2_pfe";
    private static final String FILE_EXTENSION = "merise";
    private static final String AUTO_SAVE_FILE = "merise_auto_saved";
    private static final int DEFAULT_SAVE = 0;
    private static final int SAVE_AS = 1;
    private JFileChooser fc;
    private FlowPanel flowPanel;
    private ManagementRulesPanel managementRulesPanel;
    private DDPanel ddPanel;
    private GDFPanel gdfPanel;
    private MCDPanel mcdPanel;
    private MLDPanel mldPanel;
    private MPDPanel mpdPanel;
    private SQLPanel sqlPanel;
    private Transform transform;
    private JButton btnNew;
    private JButton btnOpen;
    private JButton btnSave;
    private JButton btnSaveAs;
    private JButton btnGenerate;
    private JButton btnGdfToMcd;
    private JButton btnGrid;
    private JToolBar toolBar;
    private File currentFile;

    public Merise() {
        initComponents();
        setTitle(WINDOW_TITLE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        addWindowListener(getWindowAdapter());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initComponents() {
        createToolBar();
        AddButtonActionListeners();

        fc = new JFileChooser();
        fc.setAcceptAllFileFilterUsed(false);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.addChoosableFileFilter(new FileNameExtensionFilter("merise diagrams", FILE_EXTENSION));

        transform = new Transform();

        initPanels();

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

    private WindowListener getWindowAdapter() {
        return new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                int resp = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to exit?",
                        "Exit?", JOptionPane.YES_NO_OPTION);

                if (resp == JOptionPane.NO_OPTION) {
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                    return;
                }

                try {
                    serialize(getMeriseData(), normalizeFileName(AUTO_SAVE_FILE));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }

            @Override
            public void windowClosed(WindowEvent event) {
            }
        };
    }

    private void initPanels() {
        flowPanel = new FlowPanel();
        managementRulesPanel = new ManagementRulesPanel();
        ddPanel = new DDPanel();
        gdfPanel = new GDFPanel();
        mcdPanel = new MCDPanel();
        mldPanel = new MLDPanel();
        mpdPanel = new MPDPanel();
        sqlPanel = new SQLPanel();
    }

    private void createToolBar() {
        toolBar = new JToolBar();
        btnNew = createToolBarBtn("new_file", "nouveau fichier (CTRL+N)", true);
        btnOpen = createToolBarBtn("open_folder", "Ouvrir un nouveau projet (CTRL+O)", true);
        btnSave = createToolBarBtn("save", "Enregistrer (CTRL+S)", true);
        btnSaveAs = createToolBarBtn("save_as", "Enregistrer sous (CTRL+SHIFT+S)", true);
        btnGdfToMcd = createToolBarBtn("generate2", "Générer MCD à partir de GDF", true);
        btnGenerate = createToolBarBtn("generate", "Générer le MLD & le MPD (F6)", true);
        btnGrid = createToolBarBtn("grid", "Grille (CTRL+G)", false);

        toolBar.setOrientation(SwingConstants.HORIZONTAL);
    }

    private JButton createToolBarBtn(String icon, String toolTip, Boolean separator) {
        JButton btn = new JButton(new ImageIcon("icons/" + icon + ".png"));
        btn.setFocusable(false);
        btn.setToolTipText(toolTip);
        toolBar.add(btn);
        if (separator)
            toolBar.addSeparator();
        return btn;
    }

    private void AddButtonActionListeners() {

        btnNew.addActionListener((ActionEvent e) -> {
            currentFile = null;
            if (diagramsAreEmpty())
                return;

            int confirmDialog = JOptionPane.showConfirmDialog(
                    this,
                    "Voulez vous enregistrer les modifications?",
                    "Enregistrer?", JOptionPane.YES_NO_OPTION);
            if (confirmDialog == JOptionPane.YES_OPTION)
                saveFile(DEFAULT_SAVE);
            resetDiagrams();
            repaint();
            this.setTitle(WINDOW_TITLE);
        });

        btnOpen.addActionListener((ActionEvent e) -> openFile());

        btnSaveAs.addActionListener((ActionEvent e) -> saveFile(SAVE_AS));

        btnGdfToMcd.addActionListener((ActionEvent e) -> {
            GDFGraph gdfGraph = gdfPanel.getGraph();
            MCDGraph mcdGraph = transform.gdfToMcd(gdfGraph);
            mcdPanel.setGraph(mcdGraph);
            JOptionPane.showMessageDialog(this, "Terminé!");
        });

        btnGenerate.addActionListener((ActionEvent e) -> {
            MLDGraph mldGraph = transform.mcdToMld(mcdPanel.getGraph());
            MPDGraph mpdGraph = transform.mldToMpd(mldGraph);
            mldPanel.setMldGraph(mldGraph);
            mpdPanel.setMpdGraph(mpdGraph);
            sqlPanel.setSQLScript(transform.mpdToSQL(mpdGraph));
            JOptionPane.showMessageDialog(this, "Terminé!");
        });

        btnSave.addActionListener((ActionEvent e) -> saveFile(DEFAULT_SAVE));

    }

    private boolean diagramsAreEmpty() {
        // TODO check if no data in all diagrams
        return false;
    }

    private void resetDiagrams() {
        transform = new Transform();

        flowPanel.getNodes().clear();
        flowPanel.getLinks().clear();

        managementRulesPanel.setData(new String[][]{});

        ddPanel.setData(new Object[][]{});

        gdfPanel.getNodes().clear();
        gdfPanel.getLinks().clear();
        gdfPanel.setGraph(new GDFGraph());

        mcdPanel.getNodes().clear();
        mcdPanel.getLinks().clear();
        mcdPanel.setGraph(new MCDGraph());

        mldPanel.setMldGraph(new MLDGraph());

        mpdPanel.setMpdGraph(new MPDGraph());

        sqlPanel.setSQLScript("");
    }

    private void openFile() {
        Map<String, Object> meriseData;

        if (fc.showOpenDialog(this) == JFileChooser.CANCEL_OPTION)
            return;

        currentFile = fc.getSelectedFile();
        System.out.println("Opening: " + currentFile.getName());
        this.setTitle(currentFile.getName());
        try {
            meriseData = deserialize(currentFile);
            this.loadMeriseData(meriseData);
        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void saveFile(int type) {
        String fileName;
        if (currentFile != null && type == DEFAULT_SAVE)
            fileName = currentFile.getAbsolutePath();
        else {
            fc.setDialogTitle("Specifier le nom de fichier");

            if (fc.showSaveDialog(this) == JFileChooser.CANCEL_OPTION) {
                System.out.println("Save command canceled");
                return;
            }

            fileName = fc.getSelectedFile().getAbsolutePath();
        }

        fileName = normalizeFileName(fileName);
        try {
            Map<String, Object> meriseData = getMeriseData();
            serialize(meriseData, fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("File Saved as: " + fileName);
    }

    private @NotNull String normalizeFileName(String fileName) {
        String suffix = "." + FILE_EXTENSION;
        fileName = fileName.trim();
        if (!fileName.endsWith(suffix))
            return fileName + suffix;
        return fileName;
    }

    @NotNull
    private Map<String, Object> getMeriseData() {

        Map<String, Object> meriseData = new HashMap<>();

        meriseData.put("flowNodes", flowPanel.getNodes());
        meriseData.put("flowLinks", flowPanel.getLinks());

        meriseData.put("managementRules", managementRulesPanel.getData());
        meriseData.put("DataDictionary", ddPanel.getData());

        meriseData.put("gdfNodes", gdfPanel.getNodes());
        meriseData.put("gdfLinks", gdfPanel.getLinks());
        meriseData.put("gdfGraph", gdfPanel.getGraph());

        meriseData.put("mcdNodes", mcdPanel.getNodes());
        meriseData.put("mcdLinks", mcdPanel.getLinks());
        meriseData.put("mcdGraph", mcdPanel.getGraph());

        return meriseData;
    }

    @SuppressWarnings("unchecked")
    public void loadMeriseData(@NotNull Map<String, Object> meriseData) throws IOException, ClassNotFoundException {

        System.out.println("opening ...");

        flowPanel.setNodes((List<GNode>) meriseData.get("flowNodes"));
        flowPanel.setLinks((List<GArrow>) meriseData.get("flowLinks"));
        flowPanel.repaint();

        managementRulesPanel.setData((Object[][]) meriseData.get("managementRules"));

        ddPanel.setData((Object[][]) meriseData.get("DataDictionary"));

        gdfPanel.setNodes((List<GNodeGDF>) meriseData.get("gdfNodes"));
        gdfPanel.setLinks((List<GSimpleDF>) meriseData.get("gdfLinks"));
        gdfPanel.setGraph((GDFGraph) meriseData.get("gdfGraph"));
        gdfPanel.repaint();

        mcdPanel.setNodes((List<GMCDNode>) meriseData.get("mcdNodes"));
        mcdPanel.setLinks((List<GMCDLink>) meriseData.get("mcdLinks"));
        mcdPanel.setGraph((MCDGraph) meriseData.get("mcdGraph"));
        mcdPanel.repaint();
    }

    private void serialize(Map<String, Object> meriseData, String fileName) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
        oos.writeObject(meriseData);
        oos.close();
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> deserialize(File file) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
        Map<String, Object> meriseData = (Map<String, Object>) ois.readObject();
        ois.close();
        return meriseData;
    }

}
