package com;

import com.mcd.*;
import com.mld.MLDGraph;
import com.mld.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Transform {

    MLDGraph mldGraph;
    public Transform() {
        mldGraph = new MLDGraph();
    }

    public MLDGraph toMLD(MCDGraph mcdGraph){

        mcdGraph.getNodeList().forEach( node -> {
            if (node instanceof Association association){
                // Toute relation porteuse de propriétés se transformera en entité et absorbera comme clé étrangère les
                //identifiants des entités qui lui sont liées.

                // régle 3 et 5
                if (!association.getPropertyList().isEmpty() || association.getLinks().size() > 2){
                    createTableFromAssociation(association);
                    System.out.println("régle 3 et 5");
                }
                else {
                    // Si la relation n'est pas porteuse de propriétés
                    Map<Entity, Cardinalities> links = association.getLinks();
                    ArrayList<Entity> entities = new ArrayList<>(association.getLinks().keySet());
                    // 
                    if (links.get(entities.get(0)).toString().endsWith("n") && links.get(entities.get(1)).toString().endsWith("n")){
                        createTableFromAssociation(association);
                        /*
                        Table tableA = createMLDTable(entities.get(0));
                        Table tableB = createMLDTable(entities.get(1));
                        Table tableAssoc = createMLDTable(association);
                        tableAssoc.addProperty(entities.get(0).getPrimaryKey());
                        tableAssoc.addProperty(entities.get(1).getPrimaryKey());
                        mldGraph.getNodeList().add(tableA);
                        mldGraph.getNodeList().add(tableB);
                        mldGraph.getNodeList().add(tableAssoc);
                        mldGraph.getForeignKeys().put(tableA, tableAssoc.getName());
                        mldGraph.getForeignKeys().put(tableB, tableAssoc.getName());
                        */
                    }
                    else if (links.get(entities.get(0)).toString().endsWith("1") && links.get(entities.get(1)).toString().endsWith("1")){
                        Table tableA = createMLDTable(entities.get(0));
                        Table tableB = createMLDTable(entities.get(1));
                        tableB.addProperty(createForeignKey(entities.get(0).getId()));
                        mldGraph.getNodeList().add(tableA);
                        mldGraph.getNodeList().add(tableB);
                        mldGraph.getForeignKeys().put(tableA.getName(), tableB.getName());
                    }
                    else{
                        Table tableA = createMLDTable(entities.get(0));
                        Table tableB = createMLDTable(entities.get(1));
                        if (links.get(entities.get(0)).toString().endsWith("n")) {
                            tableB.addProperty(createForeignKey(entities.get(0).getId()));
                            mldGraph.getForeignKeys().put(tableB.getName(), tableA.getName());
                        }
                        else {
                            tableA.addProperty(createForeignKey(entities.get(1).getId()));
                            mldGraph.getForeignKeys().put(tableA.getName(), tableB.getName());
                        }
                        mldGraph.getNodeList().add(tableA);
                        mldGraph.getNodeList().add(tableB);
                    }
                }
            }
            else{
                mldGraph.getNodeList().add(createMLDTable(node));
                System.out.println("last else");
            }
        });

        return mldGraph;
    }

    private void createTableFromAssociation(Association association) {
        Table associationTable = createMLDTable(association);
        association.getLinks().forEach((entity, cardinality) -> {
            mldGraph.getNodeList().add(createMLDTable(entity));
            mldGraph.getForeignKeys().put(entity.getName(), associationTable.getName());
            associationTable.addProperty(createForeignKey(entity.getId()));
        });
        mldGraph.getNodeList().add(associationTable);
    }


    private Property createForeignKey(Property pkProp) {
        Property fkProp = new Property();
        fkProp.setName(pkProp.getName());
        fkProp.setType(pkProp.getType());
        fkProp.setLength(pkProp.getLength());
        fkProp.setConstraints(List.of(Property.Constraints.FOREIGN_KEY));
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
}
