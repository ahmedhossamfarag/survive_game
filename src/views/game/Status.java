package views.game;

import views.component.XPanel;

import java.awt.*;

public abstract class Status extends XPanel {
    /**
     *
     */
    private static final long serialVersionUID = 5841654184968965056L;

    public Status() {
        super(new FlowLayout(FlowLayout.LEFT, 10, 5));
        setVisible(false);
    }

    public abstract void setHp(int hp);
}
