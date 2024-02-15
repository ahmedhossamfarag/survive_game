package views.layout;

import java.awt.*;
import java.io.Serializable;

public class SpaceLayout implements LayoutManager, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1263611914276307368L;
    private final boolean spaceAround;

    public SpaceLayout(boolean spaceAround) {
        this.spaceAround = spaceAround;
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
            for (Component c : parent.getComponents()) {
                c.setSize(c.getPreferredSize());
                c.addPropertyChangeListener("preferredSize", e -> layoutContainer(parent));
            }
            int w = parent.getWidth(), h = parent.getHeight();
            int i = 0, j = parent.getComponentCount() - 1;
            if (j == -1) return;
            if (i == j) {
                alignSingle(parent.getComponent(i), 0, w, h);
                return;
            }
            int xi, xf, g;
            if (spaceAround) {
                g = (parent.getWidth() - preferredLayoutSize(parent).width) / (parent.getComponentCount() + 1);
                xi = g;
                xf = w - g;
            } else {
                xi = 0;
                xf = w;
                g = (parent.getWidth() - preferredLayoutSize(parent).width) / (parent.getComponentCount() - 1);
            }
            while (i <= j && xi < xf) {
                if (i == j) {
                    alignSingle(parent.getComponent(i), xi, xf, h);
                } else {
                    Component c1 = parent.getComponent(i);
                    Component c2 = parent.getComponent(j);
                    c1.setLocation(xi, (h - c1.getHeight()) / 2);
                    c2.setLocation(xf - c2.getWidth(), (h - c1.getHeight()) / 2);
                    xi += c1.getWidth() + g;
                    xf -= c2.getWidth() + g;
                }
                i++;
                j--;
            }
        }
    }

    private void alignSingle(Component c, int xi, int xf, int h) {
        c.setLocation(xi + ((xf - xi - c.getWidth()) / 2), (h - c.getHeight()) / 2);
    }
}

