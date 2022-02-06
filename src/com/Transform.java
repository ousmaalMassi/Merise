package com;

import com.mcd.*;
import com.mld.MLDGraph;
import com.mld.Table;

import java.util.ArrayList;
import java.util.List;

public class Transform {

    public Transform() {
    }

    public void toMLD(MCDGraph mcdGraph){
        MLDGraph mldGraph = new MLDGraph();
        List<String> weakEntities = new ArrayList<>();

        mcdGraph.getNodeList().forEach(node -> {

            if (node instanceof Entity entity)
            {
                mldGraph.getNodeList().add(createMLDTable(entity));
            }
            else if (node instanceof Association association)
            {
                association.getLinks().forEach((s, cardinalities) -> {
                    if (cardinalities == Cardinalities.ONE_ONE || cardinalities == Cardinalities.ZERO_ONE){
                        weakEntities.add(s);
                    }else{
                        mldGraph.getForeignKeys().put(s, mcdGraph.search(s).getPrimaryKey());
                    }
                });

                if (weakEntities.isEmpty()){
                    Table table = createMLDTable(association);
                    mldGraph.getForeignKeys().forEach(
                            (entityName, fk) -> {
                                table.addProperty(fk);
                            });
                    mldGraph.getNodeList().add(table);
                }else{

                }
            }
        });

        System.out.println(weakEntities);
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
