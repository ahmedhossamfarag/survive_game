package views.component;

import engine.Constant;

import java.awt.*;
import java.awt.event.MouseListener;

public class XLabel extends Label {

    /**
     *
     */
    private static final long serialVersionUID = 4599243919841186907L;

    public XLabel(String s) {
        this(s, Constant.TextFont1, Constant.TextColor);
    }

    public XLabel(String s, Font f, Color c) {
        super(s);
        setFont(f);
        setBackground(Constant.BackColor);
        setForeground(c);
    }

    public XLabel(String s, Color c, Font f) {
        super(s);
        setFont(f);
        setBackground(c);
        setForeground(Constant.TextColor);
    }

    public XLabel(String s, Font f, int a, Color c) {
        this(s, f, c);
        setAlignment(a);
    }

    public XLabel(String s, Dimension dimension, int a) {
        this(s, Constant.TextFont1, a, Constant.TextColor);
        setPreferredSize(dimension);
    }

    public XLabel(String s, MouseListener l) {
        this(s);
        addMouseListener(l);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public XLabel(String s, int a) {
        this(s);
        setAlignment(a);
    }
}
