package com.graphics;

import java.awt.*;
import java.io.Serial;
import java.io.Serializable;

public abstract class GObject implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    protected static final Font FONT_SELECT = new Font(Font.DIALOG, Font.BOLD, 16);
    protected static final Font FONT_UNSELECT = new Font(Font.DIALOG, Font.PLAIN, 16);
    protected static Stroke DEFAULT_STROKE = new BasicStroke(1.5f);
    protected static Stroke SELECTED_STROKE = new BasicStroke(2);
    protected transient Stroke stroke;

    public GObject() {
        this.stroke = SELECTED_STROKE;
    }

    public abstract void draw(Graphics2D g);

    protected abstract void setSelected(boolean selected);
}
