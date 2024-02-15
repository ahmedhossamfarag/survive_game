package views.component;

import javax.swing.*;
import java.awt.*;

public class OpaquePanel extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = -3681182471385741378L;
    public static Color backColor = new Color(0, 0, 0, 0);

    public OpaquePanel(LayoutManager layoutManager, boolean b) {
        super(layoutManager);
        setBackground(backColor);
        setVisible(b);
    }

    public OpaquePanel(LayoutManager layoutManager) {
        this(layoutManager, true);
    }
}
