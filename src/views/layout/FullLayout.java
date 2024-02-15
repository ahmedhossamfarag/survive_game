package views.layout;

import java.awt.*;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;

public class FullLayout implements LayoutManager {
    private boolean full;

    public FullLayout(boolean full) {
        this.full = full;
    }

    public FullLayout() {
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {

    }

    @Override
    public void removeLayoutComponent(Component comp) {

    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return parent.getComponent(parent.getComponentCount() - 1).getPreferredSize();
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return preferredLayoutSize(parent);
    }

    @Override
    public void layoutContainer(Container parent) {
        synchronized (parent.getTreeLock()) {
            parent.addContainerListener(new ContainerListener() {
                @Override
                public void componentAdded(ContainerEvent e) {
                    e.getChild().setSize(parent.getSize());
                }

                @Override
                public void componentRemoved(ContainerEvent e) {
                }
            });
            if (full) {
                for (Component c : parent.getComponents())
                    c.setSize(parent.getSize());
                return;
            }
            int n = parent.getComponentCount();
            if (n == 0) return;
            parent.getComponent(n - 1).setSize(parent.getSize());
            for (int i = 0; i < parent.getComponentCount() - 1; i++) {
                Component c = parent.getComponent(i);
                c.setSize(c.getPreferredSize());
                c.setLocation((parent.getWidth() - c.getWidth()) / 2,
                        (parent.getHeight() - c.getHeight()) / 2);
            }
        }
    }
}
