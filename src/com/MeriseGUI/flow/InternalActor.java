package com.MeriseGUI.flow;

import java.awt.*;
import java.io.Serial;

public class InternalActor extends Actor {

    @Serial
    private static final long serialVersionUID = 5396347452110584370L;
    
    public InternalActor(int x, int y, String name) {
        super(x, y, name);
        nodeType = NodeType.INTERNAL_NODE;
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
    }

    @Override
    public String toString() {
        return super.toString()+", t: " + name +"}";
    }

}
