package com;

import com.mcd.*;
import com.mld.MLDGraph;
import com.mld.MLDTable;
import com.mpd.MPDGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Transform {

    private final MLDGraph mldGraph;
    private final MPDGraph mpdGraph;
    private final List<String> foreignKeyConstraint;

    public Transform() {
        mldGraph = new MLDGraph();
        mpdGraph = new MPDGraph();
        foreignKeyConstraint = new ArrayList<>();
    }

    public MLDGraph mcdToMld(MCDGraph mcdGraph) {

        mcdGraph.getEntities().forEach(entity -> mldGraph.getTables().add(createMLDTable(entity)));

        mcdGraph.getAssociations().forEach(association -> {
            if (!association.getPropertyList().isEmpty() || association.getLinks().size() > 2) {
                mldGraph.getTables().add(createAssociationTable(association));
                return;
            }

            Map<Entity, Cardinality> links = association.getLinks();
            Set<Entity> entities = association.getLinks().keySet();

            Entity entity1 = entities.iterator().next();
            Entity entity2 = entities.iterator().next();

            // these tables have been created when the association was created
            MLDTable table1 = mldGraph.getTable(entity1.getName());
            MLDTable table2 = mldGraph.getTable(entity2.getName());

            Cardinality entityCardinality1 = links.get(entity1);
            Cardinality entityCardinality2 = links.get(entity2);

            switch (getRelationShipType(entityCardinality1, entityCardinality2)) {
                case ONE_TO_ONE -> table2.addForeignKey(table1);
                case MANY_TO_MANY -> mldGraph.getTables().add(createAssociationTable(association));
                case ONE_TO_MANY -> {
                    if (isWeak(entityCardinality1))
                        table2.addForeignKey(table1);
                    else
                        table1.addForeignKey(table2);
                }
            }
        });

        return mldGraph;
    }

    private boolean isWeak(Cardinality cardinality1) {
        return cardinality1.equals(Cardinality.ZERO_MANY) || cardinality1.equals(Cardinality.ONE_MANY);
    }

    private RelationShip getRelationShipType(Cardinality cardinality1, Cardinality cardinality2) {

        RelationShip relationShip;

        boolean e1EndsWithOne = cardinality1.equals(Cardinality.ZERO_ONE) || cardinality1.equals(Cardinality.ONE_ONE);
        boolean e2EndsWithOne = cardinality2.equals(Cardinality.ZERO_ONE) || cardinality2.equals(Cardinality.ONE_ONE);
        boolean e2EndsWithMany = cardinality2.equals(Cardinality.ZERO_MANY) || cardinality2.equals(Cardinality.ONE_MANY);

        if (e1EndsWithOne && e2EndsWithOne)
            relationShip = RelationShip.ONE_TO_ONE;
        else if (e1EndsWithOne && e2EndsWithMany)
            relationShip = RelationShip.MANY_TO_MANY;
        else
            relationShip = RelationShip.ONE_TO_MANY;

        return relationShip;
    }

    private MLDTable createAssociationTable(Association association) {
        MLDTable associationTable = createMLDTable(association);
        association.getLinks().forEach((entity, cardinality) -> {
            associationTable.addPrimaryKey(entity.getPropertyList().get(0));
            associationTable.addForeignKey(mldGraph.getTable(entity.getName()));
        });
        associationTable.getPropertyList().addAll(associationTable.getPrimaryKeys());
        return associationTable;
    }

    /**
     * @param node to convert to table
     * @return MLD table
     */
    private MLDTable createMLDTable(MeriseObject node) {
        MLDTable table = new MLDTable(node.getName());
        table.setPropertyList(node.getPropertyList());
        return table;
    }

    public MPDGraph mldToMpd(MLDGraph mldGraph) {
//        this.mldGraph.getTables().forEach(mldTable -> {
//            MPDTable mpdTable = new MPDTable(mldTable.getName());
//            mpdTable.addPrimaryKey(mldTable.getPrimaryKeys());
//            mpdTable.getPropertyList().forEach(property -> mpdTable.addProperty(property));
//            mldTable.getForeignKeys().forEach((s, mldTable1) -> );
//            this.mpdGraph.getTables().add(mpdTable);
//
//        });
//        return this.mpdGraph;
        this.mldGraph.getTables().forEach(mldTable -> this.mpdGraph.getTables().add(mldTable));
        return this.mpdGraph;
    }

    public String mpdToSQL(MPDGraph mpdGraph) {
        //TODO specify the targeted DBMS
        StringBuilder stringBuilder = new StringBuilder();
        mpdGraph.getTables().forEach(table -> stringBuilder
                .append("DROP TABLE IF EXISTS `").append(table.getName()).append("`;\n")
                .append("CREATE TABLE `").append(table.getName()).append("` (\n")
                .append(this.createColumn(table))
                .append(") ").append("ENGINE=InnoDB;").append("\n\n")
        );
        stringBuilder.append(String.join(",\n", this.foreignKeyConstraint));
        return stringBuilder.toString();
    }

    private String createColumn(MLDTable table) {
        List<String> list = new ArrayList<>();
        table.getPropertyList().forEach(property -> list.add(
                "`" + property.getCode() + "`" +
                        " " + property.getType().toString() +
                        " (" + property.getLength() + ")" +
                        " " + this.createConstraints(property.getConstraints())
        ));
        list.add(this.createPrimaryKey(table.getName(), table.getPrimaryKeys()));
        if (!table.getForeignKeys().isEmpty())
            this.foreignKeyConstraint.add(this.createForeignKeyColumn(table.getName(), table.getForeignKeys()));
        return String.join(",\n", list);
    }

    private String createPrimaryKey(String tableName, List<Property> primaryKeys) {
        List<String> list = new ArrayList<>();
        primaryKeys.forEach(primaryKey ->
                list.add("CONSTRAINT PK_" + tableName + " ADD PRIMARY KEY (" + primaryKey.getCode() + ")")
        );
        return String.join(",\n", list);
    }

    private String createConstraints(List<Property.Constraints> constraints) {
        List<String> list = new ArrayList<>();
        constraints.forEach(
                constraint -> list.add(constraint.toString())
        );
        return String.join(" ", list);
    }

    private String createForeignKeyColumn(String tableName, Map<String, MLDTable> foreignKeys) {
        List<String> list = new ArrayList<>();
        foreignKeys.forEach((prop, tableRef) -> list.add(
                "ALTER TABLE " + tableName + " FOREIGN KEY (`" + prop + "`) REFERENCES " + tableRef.getName() + "(`" + prop + "`)")
        );
        return String.join(",\n", list);
    }

    public enum RelationShip {
        ONE_TO_ONE,
        ONE_TO_MANY,
        MANY_TO_MANY
    }
}
