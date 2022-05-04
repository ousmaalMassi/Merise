package com.MeriseGUI.flow;

import java.awt.*;
import java.io.Serial;

public class ExternalActor extends Actor {

    @Serial
    private static final long serialVersionUID = 5396347452110584370L;
    
    public ExternalActor(int x, int y, String name) {
        super(x, y, name);
        nodeType = NodeType.EXTERNAL_NODE;
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
