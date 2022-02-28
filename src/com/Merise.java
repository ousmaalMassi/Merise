package com;

import com.mcd.MCDPanel;
import com.mld.MLDGraph;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        MLDGraph mldGraph = transform.mcdToMld(mcdPanel.getMcdGraph());
        //System.out.println(mldGraph);
        System.out.println(transform.mpdToSQL(mldGraph));
        //System.out.println(System.currentTimeMillis() - currentTimeMillis+" ms");

        
        /*DataDictionary dataDictionary = new DataDictionary();
        List<Property> propertyList = new ArrayList<>();
        propertyList.add(new Property("id", Property.Types.INT, 11, Arrays.asList(Property.Constraints.PRIMARY_KEY, Property.Constraints.AUTO_INCREMENT)));
        propertyList.add(new Property("nom", Property.Types.INT, 11, List.of(Property.Constraints.NOT_NULL)));
        dataDictionary.setDictionary(new HashMap<>(){{ put("test", propertyList); }});
        dataDictionary.addData("test", new Property("nom", Property.Types.INT, 11, List.of(Property.Constraints.NOT_NULL)));
        dataDictionary.removeData("test", "nom");
        System.out.println(dataDictionary);*/
    }
}
