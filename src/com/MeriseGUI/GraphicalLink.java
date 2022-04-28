/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MeriseGUI;

import java.awt.*;

/**
 *
 * @author rpc
 */
public class GraphicalLink {

    protected static BasicStroke UNSELECTED_STROKE = new BasicStroke(1.2f);
    protected static BasicStroke SELECTED_STROKE = new BasicStroke(2);

    protected Node nodeA;
    protected Node nodeB;
    private Color strokeColor;
    private Stroke strokeWidth;
    private boolean selected;
    private int lxa;
    private int lya;
    private int lxb;
    private int lyb;
    public int xa;
    public int ya;
    public int xb;
    public int yb;

    public GraphicalLink(Node nodeA, Node nodeB) {
        this.nodeA = nodeA;
        this.nodeB = nodeB;
        
        this.strokeColor = Color.black;
        this.strokeWidth = UNSELECTED_STROKE;
    }

    public boolean contains(double mx, double my) {
        
        if( mx < Math.min(nodeA.getX(), nodeB.getX()) ||
            mx > Math.max(nodeA.getX(), nodeB.getX()) ) {
            return false;
        }
        
        int A = nodeB.getY() - nodeA.getY();
        int B = nodeB.getX() - nodeA.getX();

        return Math.abs(A*mx - B*my + nodeB.getX()*nodeA.getY() - nodeB.getY()*nodeA.getX())/Math.sqrt(A*A+B*B) <= 5;
    }

    public Node getNodeA() {
        return this.nodeA;
    }

    public Node getNodeB() {
        return this.nodeB;
    }

    public void draw(Graphics2D g) {
        
        //if (isNodesMoved()) { 
        
        xa = this.nodeA.getX();
        ya = this.nodeA.getY();
        xb = this.nodeB.getX();
        yb = this.nodeB.getY();
            
        g.setColor(strokeColor);
        g.setStroke(strokeWidth);
        g.drawLine(xa, ya, xb, yb);
        
            /*int wa, wb, ha, hb;
            double lAngle, cAngle = Math.tan((double)(nodeA.getHeight()/2)/(double)Math.abs(nodeA.getWidth()/2));
            
            try{
                lAngle = Math.tan((double)(ya-yb)/(double)Math.abs(xa-xb));
            }catch(ArithmeticException e){
                lAngle = 0;
            }

            
            if (lAngle <= cAngle) {
                ya -= (int) (Math.tan(lAngle) * (nodeA.getWidth()/2));
                //yb += (int) (Math.tan(lAngle) * (nodeB.getWidth()/2));
                xa += nodeA.getWidth()/2;
                //xb -= nodeB.getWidth()/2;
            }else{
                System.out.println("spp");
                ya = nodeA.getPulledY();
                //yb += nodeB.getHeight()/2;
                ///xa += (int) (Math.tan(lAngle) * (nodeA.getWidth()/2));
                //xb -= (int) (Math.tan(lAngle) * (nodeB.getWidth()/2));
            }  */
            
            //saveLastPosition();
        //}
            
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        
        if (selected) {
            strokeColor = Color.blue;
            strokeWidth = SELECTED_STROKE;
        }else{
            strokeColor = Color.black;
            strokeWidth = UNSELECTED_STROKE;
        }
    }

    
    protected void changeStroke(int step) {
    }

    protected void setColor(Color c) {
    }

    protected void move(int dx, int dy) {
        nodeA.move(dx, dy);
        nodeB.move(dx, dy);
    }

    protected void saveLastPosition() {
        lxa = xa;
        lya = ya;
        lxb = xb;
        lyb = yb;
    }

    protected boolean isNodesMoved() {
        return (lxa != nodeA.getX() && lya != nodeA.getY() || lxb != nodeB.getX() && lyb != nodeB.getY()); 
    }
	
    
}
