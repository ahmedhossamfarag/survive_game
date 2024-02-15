package views.scene;

import views.component.XPanel;

import java.awt.*;
import java.awt.event.KeyListener;

public abstract class Scene extends XPanel {

    /**
     *
     */
    private static final long serialVersionUID = 220204853894590290L;

    public Scene(LayoutManager l) {
        super(l);
        setVisible(false);
    }

    public abstract KeyListener getKeyListener();
}
