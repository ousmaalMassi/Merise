package com.MeriseGUI.mpd;

import com.MeriseGUI.GraphicalNode;
import com.mcd.Property;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;


public class GraphicalMPDTable extends GraphicalNode {
    protected List<String> attributes;
    protected int headHeight;
    protected static int PADDING = 20;
    private FontMetrics fm;
    private int attrNbr;
    private List<Property> primaryKeys;
    private List<String> foreignKeys;

    public GraphicalMPDTable(int x, int y, String name) {
        super(x, y, name);
        foreignKeys = new ArrayList<>();
        attributes = new ArrayList<>();
        this.width = 100;
        this.headHeight = 30;
        this.height = 50;
        this.fm = null;
    }

    @Override
    public void draw(Graphics2D g) {


        attrNbr = attributes.size();

        fm = g.getFontMetrics();
        if (attrNbr>0)
            width = calculateWidth(g) + PADDING * 2;
        height = headHeight+attrNbr*fm.getHeight()+PADDING;

        pulledX = x-width/2;
        pulledY = y-height/2;

        g.setColor(Color.WHITE);
        g.fillRect(pulledX, pulledY, width, height);

        g.setColor(strokeColor);
        g.setStroke(strokeWidth);

        g.drawRect(pulledX, pulledY, width, height);
        g.drawLine(pulledX, pulledY+headHeight, pulledX+width, pulledY+headHeight);
        drawAttributes(g);

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
            g.setFont(new Font("Tahoma", Font.BOLD, 13));
            g.drawString(attributes.get(0), sx, sy);
            g.drawLine(sx, sy+5, sx+fm.stringWidth(attributes.get(0)), sy+5);
            g.setFont(oldFont);
            for (int i = 1; i < attrNbr; i++) {
                g.drawString(attributes.get(i), sx, sy+i*lineHeight);
            }

        }
    }
    @Override
    public boolean contains(double x, double y) {
        return ( Math.abs(this.x-x) <= width/2 ) && ( Math.abs(this.y-y) <= height/2 );
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

    @Override
    public String toString() {
        return """
                name: %s,
                x: %s,
                y: %s,
                """.formatted(name, x, y);
    }

    public void setPrimaryKey(List<Property> primaryKeys) {
        this.primaryKeys = primaryKeys;
    }

    public List<Property> getPrimaryKey() {
        return primaryKeys;
    }

    public void addForeignKeys(String foreignKey) {
        this.foreignKeys.add(foreignKey);
    }

    public List<String> getAttributes() {
        return attributes;
    }
}
