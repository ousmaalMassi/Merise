package com;

import com.mcd.*;
import com.mld.MLDGraph;
import com.mld.Table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Transform {

    MLDGraph mldGraph;

    public Transform() {
        mldGraph = new MLDGraph();
    }

    public MLDGraph mcdToMld(MCDGraph mcdGraph) {

        mcdGraph.getNodeList().forEach(node -> {
            if (node instanceof Association association) {
                // Toute relation porteuse de propriétés se transformera en entité et absorbera comme clé étrangère les
                //identifiants des entités qui lui sont liées.

                // régle 3 et 5
                if (!association.getPropertyList().isEmpty() || association.getLinks().size() > 2) {
                    createTableFromAssociation(association);
                } else {
                    // Si la relation n'est pas porteuse de propriétés
                    Map<Entity, Cardinalities> links = association.getLinks();
                    ArrayList<Entity> entities = new ArrayList<>(association.getLinks().keySet());
                    Table tableA = createMLDTable(entities.get(0));
                    Table tableB = createMLDTable(entities.get(1));

                    if (links.get(entities.get(0)).toString().endsWith("n") && links.get(entities.get(1)).toString().endsWith("n")) {
                        createTableFromAssociation(association);
                    } else if (links.get(entities.get(0)).toString().endsWith("1") && links.get(entities.get(1)).toString().endsWith("1")) {
                        mldGraph.getTables().put(tableA, null);
                        mldGraph.getTables().put(tableB, this.addForeignKey(tableA, tableB, entities.get(0).getId()));
                    } else {
                        if (links.get(entities.get(0)).toString().endsWith("n")) {
                            mldGraph.getTables().put(tableA, null);
                            mldGraph.getTables().put(tableB, this.addForeignKey(tableA, tableB, entities.get(0).getId()));
                        } else {
                            mldGraph.getTables().put(tableB, null);
                            mldGraph.getTables().put(tableA, this.addForeignKey(tableB, tableA, entities.get(1).getId()));
                        }
                    }
                }
            } else {
                mldGraph.getTables().put(createMLDTable(node), null);
                System.out.println("last else");
            }
        });

        return mldGraph;
    }

    private Map<String, String> addForeignKey(Table tableA, Table tableB, Property id) {
        tableB.addProperty(createForeignKey(id));
        return new HashMap<>() {{
            put(tableA.getPrimaryKey().getName(), tableA.getName());
        }};
    }

    private void createTableFromAssociation(Association association) {
        Map<String, String> fkConstraints = new HashMap<>();
        Table associationTable = createMLDTable(association);

        association.getLinks().forEach((entity, cardinality) -> {
            fkConstraints.put(entity.getId().getName(), entity.getName());
            associationTable.addProperty(createForeignKey(entity.getId()));
            mldGraph.getTables().put(createMLDTable(entity), null);

        });
        mldGraph.getTables().put(associationTable, fkConstraints);
    }


    private Property createForeignKey(Property pkProp) {
        Property fkProp = new Property();
        fkProp.setName(pkProp.getName());
        fkProp.setType(pkProp.getType());
        fkProp.setLength(pkProp.getLength());
        fkProp.setConstraints(List.of());
        return fkProp;
    }

    /**
     * @param node to convert to table
     * @return MLD table
     */
    private Table createMLDTable(Node node) {
        Table table = new Table(node.getName());
        table.setPropertyList(node.getPropertyList());
        return table;
    }

    public String mldToMpd(MLDGraph mldGraph) {
        StringBuilder stringBuilder = new StringBuilder();
        mldGraph.getTables().forEach((table, foreignKeys) -> {
            stringBuilder
                    .append("DROP TABLE IF EXISTS `")
                    .append(table.getName())
                    .append("`;\n");
            stringBuilder
                    .append("CREATE TABLE `")
                    .append(table.getName())
                    .append("` (\n");
            table.getPropertyList().forEach(property -> {
                StringBuilder constraints = new StringBuilder();
                property.getConstraints().forEach(constraint -> constraints.append(constraint.toString()));
                stringBuilder.append("`")
                        .append(property.getName()).append("` ")
                        .append(property.getType()).append(" (")
                        .append(property.getLength()).append(") ")
                        .append(constraints).append(",\n");
            });
            if (foreignKeys != null)
                foreignKeys.forEach((prop, tableRef) -> stringBuilder
                        .append("FOREIGN KEY (`")
                        .append(prop)
                        .append("`) REFERENCES ")
                        .append(tableRef)
                        .append("(`")
                        .append(prop)
                        .append("`)\n"));
            stringBuilder.append(") ENGINE=InnoDB;\n\n");
        });
        return stringBuilder.toString();
    }
}
