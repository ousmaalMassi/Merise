package com.mcd;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MCDPanel extends JPanel {
    MCDGraph mcdGraph;

    public MCDPanel() {
        this.mcdGraph = this.showExampleGraph();
        //System.out.println(mcdGraph);
    }

    /**
     *
     */
    public MCDGraph showExampleGraph() {
        MCDGraph graph = new MCDGraph();

        Entity entity1 = new Entity("Client");
        entity1.setPropertyList(
                Stream.of(
                        new Property("id", Property.Types.INT, 11, Arrays.asList(Property.Constraints.PRIMARY_KEY, Property.Constraints.AUTO_INCREMENTS)),
                        new Property("nom", Property.Types.INT, 11, List.of(Property.Constraints.NOT_NULL)),
                        new Property("prenom", Property.Types.INT, 11, List.of(Property.Constraints.NOT_NULL)),
                        new Property("adresse", Property.Types.INT, 11, List.of(Property.Constraints.NOT_NULL))
                ).collect(Collectors.toList())
        );

        Entity entity2 = new Entity("Article");
        entity2.setPropertyList(
                Stream.of(
                        new Property("id_article", Property.Types.INT, 11, Arrays.asList(Property.Constraints.PRIMARY_KEY, Property.Constraints.AUTO_INCREMENTS)),
                        new Property("prix_achat", Property.Types.INT, 11, List.of(Property.Constraints.NOT_NULL)),
                        new Property("prix_vente", Property.Types.VARCHAR, 30, List.of(Property.Constraints.NOT_NULL)),
                        new Property("designation", Property.Types.INT, 11, List.of(Property.Constraints.NOT_NULL))
                ).collect(Collectors.toList())
        );

        Association association = new Association("Commander");
        /*association.setPropertyList(
                Stream.of(
                        new Property("quantity", Property.Types.INT, 11, List.of(Property.Constraints.NOT_NULL))
                ).collect(Collectors.toList())
        );*/

        association.addLink(entity1, Cardinalities.ONE_MANY);
        association.addLink(entity2, Cardinalities.ONE_ONE);

        // graph.addNode(entity1);
        // graph.addNode(entity2);
        graph.addNode(association);

        return graph;
    }

    public MCDGraph getMcdGraph() {
        return mcdGraph;
    }
}
