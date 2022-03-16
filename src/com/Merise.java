package com;

import com.gdf.DFNode;
import com.gdf.GDFGraph;
import com.mcd.MCDGraph;
import com.mcd.MCDPanel;
import com.mcd.Property;
import com.mld.MLDGraph;

import javax.swing.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Merise /*extends JFrame */{

    MCDPanel mcdPanel;
    Transform transform;

    public Merise() {
        initComponents();
        /*setTitle("Merice_v2_pfe");
        setSize(192, 108);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);*/
    }

    private void initComponents() {
        long currentTimeMillis = System.currentTimeMillis();
        mcdPanel = new MCDPanel();
        transform = new Transform();
        MCDGraph mcdGraph = mcdPanel.getMcdGraph();
        MLDGraph mldGraph = transform.mcdToMld(mcdGraph);
        //System.out.println(mldGraph);
        //System.out.println(mcdGraph);
        //System.out.println(transform.mpdToSQL(mldGraph));
        //System.out.println(System.currentTimeMillis() - currentTimeMillis+" ms");

        /*List<String> s = new ArrayList<>();
        s.add("Massi");
        s.add("sami");
        System.out.println(s);
        s.remove("Massi");
        System.out.println(s);*/

        /*DataDictionary dataDictionary = new DataDictionary();
        List<Property> propertyList = new ArrayList<>();
        propertyList.add(new Property("id", Property.Types.INT, 11, Arrays.asList(Property.Constraints.PRIMARY_KEY, Property.Constraints.AUTO_INCREMENT)));
        propertyList.add(new Property("nom", Property.Types.INT, 11, List.of(Property.Constraints.NOT_NULL)));
        dataDictionary.setDictionary(new HashMap<>(){{ put("test", propertyList); }});
        dataDictionary.addData("test", new Property("nom", Property.Types.INT, 11, List.of(Property.Constraints.NOT_NULL)));
        dataDictionary.removeData("test", "nom");
        System.out.println(dataDictionary);*/

        DataDictionary dataDictionary = new DataDictionary();
        GDFGraph gdfGraph = new GDFGraph();

        dataDictionary.addData(new Property("id", Property.Types.DIGITAL, 11, List.of(Property.Constraints.AUTO_INCREMENT)))
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
        //TODO recursive declaration, DFNade or Property directly

        gdfGraph.addDfNodes(dfNode);

        System.out.println(gdfGraph);
    }
}
