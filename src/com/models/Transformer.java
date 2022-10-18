package com.models;

import com.exceptions.DuplicateMeriseObject;
import com.models.gdf.GDFGraph;
import com.models.mcd.Association;
import com.models.mcd.Cardinality;
import com.models.mcd.Entity;
import com.models.mcd.MCDGraph;
import com.models.mld.MLDGraph;
import com.models.mld.MLDTable;
import com.models.mpd.MPDGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Transformer {

    private final MLDGraph mldGraph;
    private final MPDGraph mpdGraph;
    private final List<String> foreignKeyConstraint;

    public Transformer() {
        mldGraph = new MLDGraph();
        mpdGraph = new MPDGraph();
        foreignKeyConstraint = new ArrayList<>();
    }

    public MCDGraph gdfToMcd(GDFGraph gdfGraph) {
        MCDGraph mcdGraph = new MCDGraph();
        AtomicInteger num = new AtomicInteger();
        gdfGraph.getDfNodes().stream().filter(gdfNode -> !gdfNode.getTargets().isEmpty()).forEach(gdfNode -> {
            num.getAndIncrement();
            Entity entity = new Entity("entity "+num);
            entity.addProperty(new Property(gdfNode.getName(), Property.Types.ALPHABETICAL, 11));
            gdfNode.getTargets().forEach(s ->
                    entity.addProperty(new Property(s, Property.Types.ALPHABETICAL, 11)));
            try {
                mcdGraph.addEntity(entity);
            } catch (DuplicateMeriseObject e) {
                throw new RuntimeException(e);
            }
        });
        return mcdGraph;
    }


    public MLDGraph mcdToMld(MCDGraph mcdGraph) {
        mldGraph.getTables().clear();
        mcdGraph.getEntities().forEach(entity -> mldGraph.getTables().add(createMLDTable(entity)));

        mcdGraph.getAssociations().forEach(association -> {
            if (!association.getPropertyList().isEmpty() || association.getLinks().size() > 2) {
                mldGraph.getTables().add(createAssociationTable(association));
                return;
            }

            Map<Entity, Cardinality> links = association.getLinks();
            List<Entity> entities = association.getLinks().keySet().stream().toList();

            if (entities.size() < 2)
                return;

            Entity entity1 = entities.get(0);
            Entity entity2 = entities.get(1);

            // these tables have been created when the association was created
            MLDTable table1 = mldGraph.getTable(entity1.getName());
            MLDTable table2 = mldGraph.getTable(entity2.getName());

            Cardinality entityCardinality1 = links.get(entity1);
            Cardinality entityCardinality2 = links.get(entity2);

            switch (getRelationShipType(entityCardinality1, entityCardinality2)) {
                case ONE_TO_ONE -> table1.setForeignKey(table2);
                case MANY_TO_MANY -> mldGraph.getTables().add(createAssociationTable(association));
                case ONE_TO_MANY -> {
                    if (isWeak(entityCardinality1))
                        table2.setForeignKey(table1);
                    else
                        table1.setForeignKey(table2);
                }
            }
        });

        return mldGraph;
    }

    private boolean isWeak(Cardinality cardinality1) {
        return cardinality1.equals(Cardinality.ZERO_MANY) || cardinality1.equals(Cardinality.ONE_MANY);
    }

    private Relationship getRelationShipType(Cardinality cardinality1, Cardinality cardinality2) {

        Relationship relationShip;
        if ((cardinality1 == Cardinality.ZERO_MANY || cardinality1 == Cardinality.ONE_MANY) &&
                (cardinality2 == Cardinality.ZERO_MANY || cardinality2 == Cardinality.ONE_MANY))
            relationShip = Relationship.MANY_TO_MANY;
        else if ((cardinality1 == Cardinality.ONE_ONE || cardinality1 == Cardinality.ZERO_ONE) &&
                (cardinality2 == Cardinality.ONE_ONE || cardinality2 == Cardinality.ZERO_ONE))
            relationShip = Relationship.ONE_TO_ONE;
        else
            relationShip = Relationship.ONE_TO_MANY;

        return relationShip;
    }

    private MLDTable createAssociationTable(Association association) {
        MLDTable associationTable = createMLDTable(association);
        association.getLinks().forEach((entity, cardinality) -> {
            MLDTable refTable = mldGraph.getTable(entity.getName());
            if (refTable == null)
                return;
            Property primaryKey = refTable.getPrimaryKey();
            if (primaryKey == null)
                return;
            associationTable.getPropertyList().add(primaryKey);
            associationTable.setPrimaryKey(primaryKey.getCode());
            associationTable.setForeignKey(refTable);
        });
//        associationTable.getPropertyList().addAll(associationTable.getPrimaryKeys());
        return associationTable;
    }

    /**
     * @param node to convert to table
     * @return MLD table
     */
    private MLDTable createMLDTable(EntityObject node) {
        MLDTable table = new MLDTable(node.getName());
        table.setPropertyList(node.getPropertyList());
        if (node instanceof Entity entity) {
            if (entity.getId() != null)
                table.setPrimaryKey(entity.getId().getCode());
        }

        return table;
    }

    @SuppressWarnings("unused")
    public MPDGraph mldToMpd(MLDGraph mldGraph) {
//        this.mldGraph.getTables().forEach(mldTable -> {
//            MPDTable mpdTable = new MPDTable(mldTable.getName());
//            mpdTable.setPrimaryKey(mldTable.getPrimaryKeys());
//            mpdTable.getPropertyList().forEach(property -> mpdTable.addProperty(property));
//            mldTable.getForeignKeys().forEach((s, mldTable1) -> );
//            this.mpdGraph.getTables().add(mpdTable);
//
//        });
//        return this.mpdGraph;
        this.mpdGraph.getTables().clear();
        this.mldGraph.getTables().forEach(mldTable -> this.mpdGraph.getTables().add(mldTable));
        return this.mpdGraph;
    }

    public String mpdToSQL(MPDGraph mpdGraph) {
        this.foreignKeyConstraint.clear();
        //TODO specify the targeted DBMS
        StringBuilder stringBuilder = new StringBuilder();
        mpdGraph.getTables().forEach(table -> stringBuilder
                .append("DROP TABLE IF EXISTS `").append(table.getName()).append("`;\n")
                .append("CREATE TABLE `").append(table.getName()).append("` (\n")
                .append(this.createColumn(table)).append("\n")
                .append(")").append("ENGINE=InnoDB;").append("\n\n")
        );
        stringBuilder.append(String.join(",\n", this.foreignKeyConstraint));
        return stringBuilder.toString();
    }

    private String createColumn(MLDTable table) {
        List<String> list = new ArrayList<>();
        table.getPropertyList().forEach(property -> list.add(
                "`" + property.getCode() + "`" +
                        " " + property.getType().toString() + " " +
                        "(" + property.getLength() + ")" + " " +
                        Property.Constraints.NOT_NULL
        ));
        list.add(this.createPrimaryKey(table.getPrimaryAllKeys()));
        if (!table.getForeignKeys().isEmpty())
            this.foreignKeyConstraint.add(this.createForeignKeyColumn(table.getName(), table.getForeignKeys()));
        return String.join(",\n", list);
    }

    private String createPrimaryKey(List<Property> primaryKeys) {
        List<String> list = new ArrayList<>();
        primaryKeys.forEach(primaryKey ->
                list.add("PRIMARY KEY (" + primaryKey.getCode() + ")")
        );
        return String.join(",\n", list);
    }

    private String createForeignKeyColumn(String tableName, Map<Property, MLDTable> foreignKeys) {
        List<String> list = new ArrayList<>();
        foreignKeys.forEach((property, tableRef) -> list.add(
                "ALTER TABLE " + tableName + " FOREIGN KEY (`" + property.getCode() + "`) REFERENCES " + tableRef.getName() + "(`" + property.getCode() + "`)")
        );
        return String.join(",\n", list);
    }

}
