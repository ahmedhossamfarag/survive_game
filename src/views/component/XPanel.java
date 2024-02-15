package views.component;

import engine.Constant;
import views.layout.SpaceLayout;

import javax.swing.*;
import java.awt.*;

public class XPanel extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 5146414305848180482L;

    public XPanel(LayoutManager l) {
        this(l, Constant.BackColor);
    }

    public XPanel(Dimension dimension, Color color) {
        setPreferredSize(dimension);
        setBackground(color);
    }

    public XPanel(Component c, Dimension d) {
        this(new SpaceLayout(true));
        setPreferredSize(d);
        add(c);
    }

    public XPanel(LayoutManager l, Color color) {
        super(l);
        setBackground(color);
    }

    public XPanel(Component... cc) {
        this(new SpaceLayout(false));
        addAll(cc);
    }

    public XPanel(LayoutManager l, Component c, Color color) {
        this(l, color);
        add(c);
    }

    public void addAll(Component... cc) {
        for (Component c : cc)
            add(c);
    }
}
