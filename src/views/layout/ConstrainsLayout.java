package views.layout;

import java.awt.*;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ConstrainsLayout implements LayoutManager2 {
    private final HashMap<Component, Constrains> constrains;

    public ConstrainsLayout() {
        constrains = new HashMap<>();
    }

    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
        if (constraints == null || constraints instanceof Constrains)
            constrains.put(comp, (Constrains) constraints);
    }

    @Override
    public Dimension maximumLayoutSize(Container target) {
        return preferredLayoutSize(target);
    }

    @Override
    public float getLayoutAlignmentX(Container target) {
        return 0;
    }

    @Override
    public float getLayoutAlignmentY(Container target) {
        return 0;
    }

    @Override
    public void invalidateLayout(Container target) {

    }

    @Override
    public void addLayoutComponent(String name, Component comp) {

    }

    @Override
    public void removeLayoutComponent(Component comp) {
        constrains.remove(comp);
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        AtomicInteger pw = new AtomicInteger();
        AtomicInteger ph = new AtomicInteger();
        constrains.forEach((c, con) -> {
            Dimension d = c.getPreferredSize();
            int w = d.width, h = d.height;
            if (con != null) {
                if (con.up() != null) h += con.up();
                if (con.down() != null) h += con.down();
                if (con.left() != null) w += con.left();
                if (con.right() != null) w += con.right();
            }
            pw.set(Math.max(pw.get(), w));
            ph.set(Math.max(ph.get(), h));
        });
        return new Dimension(pw.get(), ph.get());
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return preferredLayoutSize(parent);
    }

    @Override
    public void layoutContainer(Container parent) {
        synchronized (parent.getTreeLock()) {
            int pw = parent.getWidth(), ph = parent.getHeight();
            constrains.forEach((c, con) -> {
                Dimension d = c.getPreferredSize();
                int x = 0, y = 0, w = d.width, h = d.height;
                if (con != null) {
                    if (con.up() != null) {
                        if (con.down() != null) {
                            if (con.expand()) {
                                y = con.up();
                                h = ph - y - con.down();
                            } else {
                                if (con.up() == 0 && con.down() == 0)
                                    y = (ph - h) / 2;
                                else
                                    y = (ph - h) * con.up() / (con.up() + con.down());
                            }
                        } else {
                            y = con.up();
                        }
                    } else {
                        if (con.down() != null) {
                            y = ph - h - con.down();
                        }
                    }
                    if (con.left() != null) {
                        if (con.right() != null) {
                            if (con.expand()) {
                                x = con.left();
                                w = pw - x - con.right();
                            } else {
                                if (con.left() == 0 && con.right() == 0)
                                    x = (pw - w) / 2;
                                else
                                    x = (pw - w) * con.left() / (con.left() + con.right());
                            }
                        } else {
                            x = con.left();
                        }
                    } else {
                        if (con.right() != null) {
                            x = pw - w - con.right();
                        }
                    }
                    c.setLocation(x, y);
                }
                c.setSize(w, h);
            });
        }
    }
}
