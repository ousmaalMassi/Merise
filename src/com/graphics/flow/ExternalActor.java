package com.graphics.flow;

import java.awt.*;

public class ExternalActor extends Actor {
    
    public ExternalActor(int x, int y, String name) {
        super(x, y, name);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setStroke(DASHED_STROKE);
        super.draw(g);
    }

    @Override
    public String toString() {
        return super.toString()+", t: " + name +"}";
    }

}
