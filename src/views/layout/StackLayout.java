package views.layout;

import java.awt.*;

public class StackLayout implements LayoutManager {
    @Override
    public void addLayoutComponent(String name, Component comp) {

    }

    @Override
    public void removeLayoutComponent(Component comp) {

    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return null;
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return null;
    }

    @Override
    public void layoutContainer(Container parent) {
        synchronized (parent.getTreeLock()){

            int x = parent.getWidth() / 2, y = parent.getHeight() / 2;


            Component center = parent.getComponent(0);
            center.setSize(center.getPreferredSize());
            center.setLocation(x - (center.getWidth() / 2), y - (center.getHeight() / 2));

            int w = parent.getWidth() / 10, h = parent.getHeight() / 10;
            w = Math.max(w, 30);
            h = Math.max(h, 30);

            int[] dx = {-1, 1, -1, 1}, dy =  {-1, -1, 1, 1};
            int l = Math.max(Math.max(w, h), 50);

            for (int i = 0; i < dx.length  && i < parent.getComponentCount() - 1; i++) {
                Component c = parent.getComponent(i + 1);
                c.setSize(w, h);
                c.setLocation(x + (l * dx[i]) - (c.getWidth() / 2), y + (l * dy[i]) - (c.getHeight() / 2));
            }
        }
    }
}
