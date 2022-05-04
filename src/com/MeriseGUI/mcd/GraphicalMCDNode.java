/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MeriseGUI.mcd;

import com.MeriseGUI.GraphicalNode;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rpc
 */
public class GraphicalMCDNode extends GraphicalNode {
    protected List<String> attributes;

    protected int arc;
    protected int headHeight;
    protected int bodyHeight;
    protected static int PADDING = 20;
    private FontMetrics fm;
    private int attrNbr;
    private int lx;
    private int ly;

    public GraphicalMCDNode(int x, int y, String name) {
        super(x, y, name);
        
        this.arc = 0;
        this.width = 100;
        this.headHeight = 30;
        this.height = 50;
        this.fm = null;
        
        attributes = new ArrayList<>();
    }
    
    public List<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }
    
    @Override
    public boolean contains(double x, double y) {
        return ( Math.abs(this.x-x) <= width/2 ) && ( Math.abs(this.y-y) <= height/2 );
    }

    @Override
    public void draw(Graphics2D g) {
        
        //if (isEntityMoved()) {
            attrNbr = attributes.size();
            
            fm = g.getFontMetrics();
            if (attrNbr>0) 
                width = calculateWidth(g) + PADDING * 2;
            height = headHeight+attrNbr*fm.getHeight()+PADDING;

            pulledX  = x-width/2;
            pulledY  = y-height/2;
            //saveLastPosition();
        //}
            g.setColor(Color.WHITE);
            g.fillRoundRect(pulledX, pulledY, width, height, arc, arc);

            g.setColor(strokeColor);
            g.setStroke(strokeWidth);

            g.drawRoundRect(pulledX, pulledY, width, height, arc, arc);
            g.drawLine(pulledX, pulledY+headHeight, pulledX+width, pulledY+headHeight);

            drawAttributes(g);
    }

    @Override
    public String toString() {
        return "{name: " + name+", attributes: " + attributes + "}";
    }

    private void drawAttributes(Graphics2D g) {
        int sx = pulledX + PADDING;
        int sy = pulledY + headHeight + PADDING;
        int lineHeight = fm.getHeight() + 3;
        int nameY = pulledY + lineHeight;

        Font oldFont = g.getFont();

        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 17));
        g.drawString(name, sx, nameY);

        if (attrNbr>0) {
            if (this instanceof EntityView) {
                g.setFont(new Font("Tahoma", Font.BOLD, 13));
                g.drawString(attributes.get(0), sx, sy);
                g.drawLine(sx, sy+5, sx+fm.stringWidth(attributes.get(0)), sy+5);
                g.setFont(oldFont);
            }else{
                g.setFont(oldFont);
                g.drawString(attributes.get(0), sx, sy);
            }
            for (int i = 1; i < attrNbr; i++) {
                g.drawString(attributes.get(i), sx, sy+i*lineHeight);
            }

        }  
    }
    
    private int calculateWidth(Graphics2D g){
        int stringWidth;
        int maxWidth = 0;
        int entityNameWidth = fm.stringWidth(name);
        for (int i = 0; i < attrNbr; i++) {
            stringWidth = fm.stringWidth(attributes.get(i));
            if (maxWidth < stringWidth) {
                maxWidth = stringWidth;
            } 
        }
        if (maxWidth < entityNameWidth) maxWidth = entityNameWidth;
        
        return maxWidth;
        
    }

    @Override
    public boolean inCorner(int x, int y) {
        return false;
    }

    @Override
    public void resize(int x, int y) {
    }

    protected void saveLastPosition() {
        lx = x;
        ly = y;
    }

    protected boolean isEntityMoved() {
        return lx != x && ly != y; 
    }



}
