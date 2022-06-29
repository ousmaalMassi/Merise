package com.graphics.flow;

import java.awt.*;

public class InternalActor extends Actor {
    
    public InternalActor(int x, int y, String name) {
        super(x, y, name);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setStroke(DEFAULT_STROKE);
        super.draw(g);
    }

    @Override
    public String toString() {
        return super.toString()+", t: " + name +"}";
    }

}
