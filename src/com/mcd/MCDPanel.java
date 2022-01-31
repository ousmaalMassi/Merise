package com.mcd;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public class MCDPanel extends JPanel {
    MCDGraph mcdGraph;

    public MCDPanel() {
        this.mcdGraph = new MCDGraph();
        this.showExampleGraph();
    }

    public void showExampleGraph() {
        MCDGraph graph = new MCDGraph();

        Entity entity1 = new Entity("Client");
        Entity entity2 = new Entity("Article");
        Association association = new Association("Commander");

        entity1.setPropertyList(Arrays.asList(
                new Property("id", Property.Types.INT, 11, Arrays.asList(Property.Constraints.PRIMARY_KEY, Property.Constraints.AUTO_INCREMENTS)),
                new Property("nom", Property.Types.INT, 11, List.of(Property.Constraints.NOT_NULL)),
                new Property("prenom", Property.Types.INT, 11, List.of(Property.Constraints.NOT_NULL)),
                new Property("adresse", Property.Types.INT, 11, List.of(Property.Constraints.NOT_NULL))
        ));

        entity2.setPropertyList(Arrays.asList(
                new Property("id_article", Property.Types.INT, 11, Arrays.asList(Property.Constraints.PRIMARY_KEY, Property.Constraints.AUTO_INCREMENTS)),
                new Property("prix_achat", Property.Types.INT, 11, List.of(Property.Constraints.NOT_NULL)),
                new Property("prix_vente", Property.Types.VARCHAR, 30, List.of(Property.Constraints.NOT_NULL)),
                new Property("designation", Property.Types.INT, 11, List.of(Property.Constraints.NOT_NULL))
        ));

        association.setPropertyList(List.of(new Property("quantity", Property.Types.INT, 11, List.of(Property.Constraints.NOT_NULL))));

        association.addParty(entity1.name, Cardinalities.ONE_MANY);
        association.addParty(entity2.name, Cardinalities.ONE_ONE);

        graph.addNode(entity1);
        graph.addNode(entity2);
        graph.addNode(association);

        // System.out.println(graph.toString());

        // graph.search("Article");

        //repaint();
    }
}
