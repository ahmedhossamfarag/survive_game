package views.game;

import engine.Value;
import objects.Character;
import views.component.XPanel;
import views.layout.ConstrainsLayout;

import java.awt.*;

public class StatusBar extends XPanel {
    /**
     *
     */
    private static final long serialVersionUID = 1035258271816397028L;
    private Status current;

    public StatusBar() {
        super(new ConstrainsLayout());
        this.setPreferredSize(new Dimension(600, 30));
        setVisible(Value.statusVisible.getValue());
    }

    public void setItem(Status status) {
        if (current != null) {
            current.setVisible(false);
        }
        if (status != null) {
            current = status;
            status.setVisible(true);
        }
    }

    @Override
    public void remove(Component com) {
        if (current == com)
            current = null;
        super.remove(com);
    }

    public void setItemOf(Cell cell) {
        if (cell.isCharacter()) {
            setItem(((Character) cell.getObject()).getInfo());
        } else
            setItem(null);
    }

    public void setCurrent(Status current) {
        this.current = current;
    }
}
