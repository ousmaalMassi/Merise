package com.graphics.mcd;

import com.graphics.GNode;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GMCDNode extends GNode {
    protected static int HEAD_HEIGHT = 30;
    protected static int PADDING = 20;
    protected List<String> attributes;
    protected int borderRadius;
    private FontMetrics fm;
    private final GMCDNodeType type;

    public GMCDNode(int x, int y, String name, GMCDNodeType type) {
        super(x, y, name);
        this.width = 100;
        this.height = 50;
        this.fm = null;
        this.attributes = new ArrayList<>();
        this.type = type;
        this.borderRadius = this.type.equals(GMCDNodeType.ENTITY) ? 0 : 30;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }

    public GMCDNodeType getType() {
        return type;
    }

    @Override
    public boolean contains(double x, double y) {
        if (this.shiftedX > x || this.shiftedY > y) return false;
        return (Math.abs(this.x - x) <= width / 2) && (Math.abs(this.y - y) <= height / 2);
    }

    @Override
    public void draw(Graphics2D g) {

        //if (isEntityMoved()) {
        int attrNbr = attributes.size();

        fm = g.getFontMetrics();
        if (attrNbr > 0)
            width = calculateWidth(attrNbr) + PADDING * 2;
        height = HEAD_HEIGHT + attrNbr * fm.getHeight() + PADDING;

        shiftedX = x - width / 2;
        shiftedY = y - height / 2;
        //saveLastPosition();
        //}
        g.setColor(Color.WHITE);
        g.fillRoundRect(shiftedX, shiftedY, width, height, borderRadius, borderRadius);

        g.setColor(strokeColor);
//            g.setStroke(stroke);

        g.drawRoundRect(shiftedX, shiftedY, width, height, borderRadius, borderRadius);
        g.drawLine(shiftedX, shiftedY + HEAD_HEIGHT, shiftedX + width, shiftedY + HEAD_HEIGHT);

        drawAttributes(g, attrNbr);
    }

    @Override
    public String toString() {
        return "{name: " + name + ", attributes: " + attributes + "}";
    }

    private void drawAttributes(Graphics2D g, int attrNbr) {
        int sx = shiftedX + PADDING;
        int sy = shiftedY + HEAD_HEIGHT + PADDING;
        int lineHeight = fm.getHeight() + 3;
        int nameY = shiftedY + lineHeight;

        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 17));
        g.drawString(name, sx, nameY);

        if (attrNbr > 0) {
            if (this.type.equals(GMCDNodeType.ENTITY)) {
                g.setFont(new Font("Thoma", Font.BOLD, 13));
                g.drawString(attributes.get(0), sx, sy);
                g.drawLine(sx, sy + 5, sx + fm.stringWidth(attributes.get(0)), sy + 5);
            } else {
                g.drawString(attributes.get(0), sx, sy);
            }
            for (int i = 1; i < attrNbr; i++) {
                g.drawString(attributes.get(i), sx, sy + i * lineHeight);
            }
        }
    }

    private int calculateWidth(int attrNbr) {
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
}
