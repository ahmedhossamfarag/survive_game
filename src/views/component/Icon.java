package views.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

public class Icon extends JComponent {
    /**
     *
     */
    private static final long serialVersionUID = 545371927608261261L;
    private final Image icon;
    private final int size;

    private String title;

    public Icon(Image icon, int size) {
        this.icon = icon;
        this.size = size;
        setPreferredSize(new Dimension(size, size));
    }

    public Icon(Image icon, int size, MouseListener listener) {
        this(icon, size);
        addMouseListener(listener);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public Icon(Image icon, int size, MouseListener listener, String title) {
        this(icon, size, listener);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(icon, 0, 0, size, size, null);
    }
}
