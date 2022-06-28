package com.MeriseGUI;

import java.awt.*;

public interface GLinkText {

    void setText(String string);

    void drawText(Graphics2D g);

    boolean underText(double mx, double my);
}
