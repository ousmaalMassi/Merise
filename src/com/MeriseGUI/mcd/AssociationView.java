package com.MeriseGUI.mcd;

import java.awt.*;
import java.io.Serial;
import java.util.HashMap;

public class AssociationView extends GraphicalMCDNode {

    @Serial
    private static final long serialVersionUID = 5396347452110584370L;
    
    public static int associationNbr = 1;
    private HashMap<EntityView,String> entityList;
    
    public AssociationView(int x, int y, String text) {
        super(x, y, text);
        super.arc = 30;
        associationNbr++;
        entityList = new HashMap<>();
    }
    
    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
    }

    public HashMap<EntityView,String> getEntityList() {
        return entityList;
    }

    public void setEntityList(HashMap<EntityView,String> entityList) {
        this.entityList = entityList;
    }

    public void addEntityList(EntityView e, String c) {
        this.entityList.put(e, c);
    }
    
    @Override
    public String toString() {
            return super.toString() + " "+entityList ;
    }

    
}
//class Card {
//        private String minCard;
//        private String maxCard;
//
//        public Card(String minCard, String maxCard) {
//            this.minCard = minCard;
//            this.maxCard = maxCard;
//        }
//
//        public String getMinCard() {
//            return minCard;
//        }
//
//        public void setMinCard(String minCard) {
//            this.minCard = minCard;
//        }
//
//        public String getMaxCard() {
//            return maxCard;
//        }
//
//        public void setMaxCard(String maxCard) {
//            this.maxCard = maxCard;
//        }
//
//        @Override
//        public String toString() {
//            return "{"+minCard+", "+maxCard + "}";
//        }
//
//    }