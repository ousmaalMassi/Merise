package com.MeriseGUI.mpd;

import com.MeriseGUI.GraphicalNode;
import com.mcd.Property;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;


public class GraphicalMPDTable extends GraphicalNode {
    protected List<Property> attributes;
    protected int headHeight;
    protected static int PADDING = 20;
    protected static int SPACING = 18;
    private FontMetrics fm;
    private int attrNbr;
    private List<Property> primaryKeys;
    private List<Property> foreignKeys;
    private int sx;
    private int sy;

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

        sx = pulledX + PADDING;
        sy = pulledY + headHeight + PADDING;
        int lineHeight = fm.getHeight() + 3;
        int nameY = pulledY + lineHeight;

//        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 17));
        g.drawString(name, sx, nameY);


        drawAttributes("Pk_Primary", primaryKeys, g);
        drawAttributes("Attributes", attributes, g);
        drawAttributes("Pk_Foreign", foreignKeys, g);
    }

    private void drawAttributes(String constName, List<Property> list, Graphics2D g) {
        if (list.isEmpty())
            return;

        int lineX1 = sx - 5;
        int vLineY1 = sy;
        int lineY2 = sy;
        int hLineX2 = 0;
        int maxLine = 0;

        g.setFont(new Font(Font.DIALOG, Font.ITALIC, 11));
        g.drawString(constName, sx - 10, sy);
        g.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
        sy += SPACING;

        for (Property property : list) {
            String line = property.code+" "+property.type+"("+property.length+")";
            g.drawString(line, sx, sy);
            sy += SPACING;
            lineY2 += 20;
            maxLine = Math.max(fm.stringWidth(property.code), maxLine);
        }
        hLineX2 = sx + maxLine;
        g.drawLine(lineX1, vLineY1, lineX1, lineY2);
        g.drawLine(lineX1, lineY2, hLineX2, lineY2);

    }

    private int calculateWidth(Graphics2D g){
        int stringWidth;
        int maxWidth = 0;
        int entityNameWidth = fm.stringWidth(name);
        for (Property property : attributes) {
            stringWidth = fm.stringWidth(property.getCode());
            if (maxWidth < stringWidth) {
                maxWidth = stringWidth;
            }
        }
        if (maxWidth < entityNameWidth) maxWidth = entityNameWidth;

        return maxWidth;
    }

    @Override
    public boolean contains(double x, double y) {
        return ( Math.abs(this.x-x) <= width/2 ) && ( Math.abs(this.y-y) <= height/2 );
    }

    @Override
    public boolean inCorner(int x, int y) {
        return false;
    }

    @Override
    public void resize(int x, int y) {}

    public void setPrimaryKey(List<Property> primaryKeys) {
        this.primaryKeys = primaryKeys;
    }

    public void addForeignKeys(Property foreignKey) {
        this.foreignKeys.add(foreignKey);
    }

    public List<Property> getAttributes() {
        return attributes;
    }

    @Override
    public String toString() {
        return """
                name: %s,
                x: %s,
                y: %s,
                """.formatted(name, x, y);
    }
}
