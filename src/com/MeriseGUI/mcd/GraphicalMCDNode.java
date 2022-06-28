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
    protected static int PADDING = 20;
    private FontMetrics fm;
    private int attrNbr;

    public GraphicalMCDNode(int x, int y, String name) {
        super(x, y, name);
        
        this.arc = 0;
        this.width = 100;
        this.headHeight = 30;
        this.height = 50;
        this.fm = null;
        this.attributes = new ArrayList<>();
    }
    
    public List<String> getAttributes() {
        return attributes;
    }

    @Override
    public boolean contains(double x, double y) {
        if (this.shiftedX > x || this.shiftedY > y) return false;
        return ( Math.abs(this.x-x) <= width/2 ) && ( Math.abs(this.y-y) <= height/2 );
    }

    @Override
    public void draw(Graphics2D g) {
        
        //if (isEntityMoved()) {
            attrNbr = attributes.size();
            
            fm = g.getFontMetrics();
            if (attrNbr>0) 
                width = calculateWidth() + PADDING * 2;
            height = headHeight+attrNbr*fm.getHeight()+PADDING;

            shiftedX = x-width/2;
            shiftedY = y-height/2;
            //saveLastPosition();
        //}
            g.setColor(Color.WHITE);
            g.fillRoundRect(shiftedX, shiftedY, width, height, arc, arc);

            g.setColor(strokeColor);
            g.setStroke(stroke);

            g.drawRoundRect(shiftedX, shiftedY, width, height, arc, arc);
            g.drawLine(shiftedX, shiftedY +headHeight, shiftedX +width, shiftedY +headHeight);

            drawAttributes(g);
    }

    @Override
    public String toString() {
        return "{name: " + name+", attributes: " + attributes + "}";
    }

    private void drawAttributes(Graphics2D g) {
        int sx = shiftedX + PADDING;
        int sy = shiftedY + headHeight + PADDING;
        int lineHeight = fm.getHeight() + 3;
        int nameY = shiftedY + lineHeight;

        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 17));
        g.drawString(name, sx, nameY);

        if (attrNbr>0) {
            if (this instanceof EntityView) {
                g.setFont(new Font("Thoma", Font.BOLD, 13));
                g.drawString(attributes.get(0), sx, sy);
                g.drawLine(sx, sy+5, sx+fm.stringWidth(attributes.get(0)), sy+5);
            }else{
                g.drawString(attributes.get(0), sx, sy);
            }
            for (int i = 1; i < attrNbr; i++) {
                g.drawString(attributes.get(i), sx, sy+i*lineHeight);
            }

        }  
    }
    
    private int calculateWidth(){
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
    public void setSelected(boolean selected) {
        if (selected) {
            strokeColor = Color.red;
            stroke = SELECTED_STROKE;
            font = FONT_SELECT;
        }
        else{
            strokeColor = Color.black;
            stroke = UNSELECTED_STROKE;
            font = FONT_UNSELECT;
        }
    }

}
