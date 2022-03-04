package com.mcd;

import com.exception.DuplicateNode;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MCDPanel extends JPanel {
    MCDGraph mcdGraph;

    public MCDPanel() {
        this.mcdGraph = this.showExampleGraph();
    }

    /**
     *
     */
    public MCDGraph showExampleGraph() {
        MCDGraph graph = new MCDGraph();

        Entity entity1 = new Entity("Client");
        entity1.setPropertyList(
                Stream.of(
                        new Property("id", Property.Types.DIGITAL, 11, Arrays.asList(Property.Constraints.PRIMARY_KEY, Property.Constraints.AUTO_INCREMENT)),
                        new Property("nom", Property.Types.ALPHABETICAL, 11, List.of(Property.Constraints.NOT_NULL)),
                        new Property("prénom", Property.Types.ALPHABETICAL, 11, List.of(Property.Constraints.NOT_NULL)),
                        new Property("adresse", Property.Types.ALPHANUMERIC, 11, List.of(Property.Constraints.NOT_NULL))
                ).collect(Collectors.toList())
        );

        Entity entity2 = new Entity("Article");
        entity2.setPropertyList(
                Stream.of(
                        new Property("id article", Property.Types.DIGITAL, 11, Arrays.asList(Property.Constraints.PRIMARY_KEY, Property.Constraints.AUTO_INCREMENT)),
                        new Property("prix d'achat", Property.Types.ALPHANUMERIC, 11, List.of(Property.Constraints.NOT_NULL)),
                        new Property("prix de vente", Property.Types.ALPHANUMERIC, 30, List.of(Property.Constraints.NOT_NULL)),
                        new Property("designation", Property.Types.ALPHABETICAL, 11, List.of(Property.Constraints.NOT_NULL))
                ).collect(Collectors.toList())
        );

        Association association = new Association("Commander");
        /*association.setPropertyList(
                Stream.of(
                        new Property("quantity", Property.Types.INT, 11, List.of(Property.Constraints.NOT_NULL))
                ).collect(Collectors.toList())
        );*/

        try {
            graph.addEntities(entity1);
            graph.addEntities(entity2);
            graph.addAssociation(association);
        } catch (DuplicateNode e) {
            e.printStackTrace();
        }

        association.getLinks().put(entity1.getName(), Cardinalities.ONE_MANY);
        association.getLinks().put(entity2.getName(), Cardinalities.ONE_MANY);

        /*Entity entity3 = new Entity("Client");
        entity3.setPropertyList(
                Stream.of(
                        new Property("identifier", Property.Types.DIGITAL, 11, Arrays.asList(Property.Constraints.PRIMARY_KEY, Property.Constraints.AUTO_INCREMENT)),
                        new Property("name", Property.Types.ALPHABETICAL, 11, List.of(Property.Constraints.NOT_NULL)),
                        new Property("phone", Property.Types.ALPHABETICAL, 11, List.of(Property.Constraints.NOT_NULL)),
                        new Property("email", Property.Types.ALPHANUMERIC, 11, List.of(Property.Constraints.NOT_NULL))
                ).collect(Collectors.toList())
        );*/

        return graph;
    }

    public MCDGraph getMcdGraph() {
        return mcdGraph;
    }
}
