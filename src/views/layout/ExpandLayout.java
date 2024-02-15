package views.layout;

import views.game.HeroView;

import java.awt.*;

public class ExpandLayout implements LayoutManager {
    private final boolean vertical;
    private final int div, begin;
    private int skip;
    private float start;

    public ExpandLayout(boolean vertical) {
        this.vertical = vertical;
        div = 6;
        begin = 3;
        skip = 6;
    }

    public void setStart(float start) {
        this.start = start;
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {

    }

    @Override
    public void removeLayoutComponent(Component comp) {

    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        int maxHeight = 0;
        int width = 0;
        for (Component com : parent.getComponents()) {
            Dimension d = com.getPreferredSize();
            width += d.width;
            maxHeight = Math.max(maxHeight, d.height);
        }
        return new Dimension(width, maxHeight);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return preferredLayoutSize(parent);
    }

    @Override
    public void layoutContainer(Container parent) {
        synchronized (parent.getTreeLock()) {
            if (parent.getWidth() > HeroView.maxDimension.width * 4)
                skip  = 2;
            else if (parent.getWidth() > HeroView.maxDimension.width * 3)
                skip = 3;
            else
                skip = 6;
            int n = parent.getComponentCount();
            int w = parent.getWidth();
            int h = parent.getHeight();
            if (vertical) {
                double yi = ((h / ((double) div/ begin)) + (start * ((double) h * skip / div)));
                double dy = (double) h * skip / div;
                for (int i = 0; i < n; i++) {
                    Component c = parent.getComponent(i);
                    c.setSize(c.getPreferredSize());
                    c.setLocation((w - c.getWidth()) / 2, (int)( yi + (dy * i) - (c.getHeight() / 2)));
                }
            } else {
                double xi = ((w / ((double) div / begin)) + (start * ((double) w * skip / div)));
                double dx = (double) w * skip / div;
                for (int i = 0; i < n; i++) {
                    Component c = parent.getComponent(i);
                    c.setSize(c.getPreferredSize());
                    c.setLocation((int) (xi + (dx * i) - (c.getWidth() / 2)), (h - c.getHeight()) / 2);
                }
            }
        }
    }
}
