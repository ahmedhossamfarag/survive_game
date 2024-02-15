package views.layout;

import java.awt.*;
import java.io.Serializable;

public class DirectionLayout implements LayoutManager, Serializable {
    public static final int Centre = 2, Start = 3, End = 4;
    /**
     *
     */
    private static final long serialVersionUID = -3434981870184263414L;
    private final int align;
    private final boolean vertical;
    private final boolean expand;
    private int crossAlign;
    private int gap;
    private boolean inverse;

    public DirectionLayout() {
        this(0, true, Start);
    }

    public DirectionLayout(int i) {
        this(i, true, Start);

    }

    public DirectionLayout(int i, boolean ex, int alx) {
        this.align = Start;
        this.vertical = true;
        crossAlign = alx;
        expand = ex;
        gap = i;
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {

    }

    @Override
    public void removeLayoutComponent(Component comp) {

    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        if (vertical) {
            int maxWidth = 0;
            int height = 0;
            for (int i = 0; i < parent.getComponentCount(); i++) {
                Dimension d = parent.getComponent(i).getPreferredSize();
                height += d.height;
                if (i != 0) height += gap;
                maxWidth = Math.max(maxWidth, d.width);
            }
            return new Dimension(maxWidth, height);
        }
        int maxHeight = 0;
        int width = 0;
        for (int i = 0; i < parent.getComponentCount(); i++) {
            Dimension d = parent.getComponent(i).getPreferredSize();
            width += d.width;
            if (i != 0) width += gap;
            maxHeight = Math.max(maxHeight, d.width);
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
            if (parent.getComponentCount() == 0) return;
            for (Component c : parent.getComponents())
                c.addPropertyChangeListener("preferredSize", e -> layoutContainer(parent));
            int w = parent.getWidth(), h = parent.getHeight();
            Dimension d = preferredLayoutSize(parent);
            int start = 0, end = parent.getComponentCount() - 1;
            if (inverse) {
                start = end;
                end = 0;
            }
            if (vertical) {
                int yi;
                switch (align) {
                    case Centre:
                        yi = (h - d.height) / 2;
                        break;
                    case End:
                        yi = h - d.height;
                        break;
                    default:
                        yi = 0;
                }
                for (int i = start; ; ) {
                    Component c = parent.getComponent(i);
                    if (expand)
                        c.setSize(w, c.getPreferredSize().height);
                    else
                        c.setSize(c.getPreferredSize());
                    c.setLocation(getX(c, w), yi);
                    yi += c.getHeight() + gap;
                    if (i < end && start <= end) i++;
                    else if (i > end && start >= end) i--;
                    else break;
                }
            } else {
                int xi;
                switch (align) {
                    case Centre:
                        xi = (w - d.width) / 2;
                        break;
                    case End:
                        xi = w - d.width;
                        break;
                    default:
                        xi = 0;
                }
                for (int i = start; ; ) {
                    Component c = parent.getComponent(i);
                    if (expand)
                        c.setSize(c.getPreferredSize().width, h);
                    else
                        c.setSize(c.getPreferredSize());
                    c.setLocation(xi, getY(c, h));
                    xi += c.getWidth() + gap;
                    if (i < end && start <= end) i++;
                    else if (i > end && start >= end) i--;
                    else break;
                }
            }
        }
    }

    private int getX(Component c, int w) {
        return switch (crossAlign) {
            case Centre -> (w - c.getWidth()) / 2;
            case End -> w - c.getWidth();
            default -> 0;
        };
    }

    private int getY(Component c, int h) {
        return switch (crossAlign) {
            case Centre -> (h - c.getHeight()) / 2;
            case End -> h - c.getHeight();
            default -> 0;
        };
    }
}
