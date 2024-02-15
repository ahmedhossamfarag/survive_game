package views.component;

import engine.Action;
import engine.Value;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ActionLabel extends Label implements MouseListener {
    /**
     *
     */
    private static final long serialVersionUID = 7658977090547604048L;
    private final Action action;

    public ActionLabel(String s, Action a) {
        super(s);
        action = a;
        setAlignment(Label.CENTER);
        setPreferredSize(new Dimension(100, 20));
        setBackground(Value.menuColor.getValue());
        addMouseListener(this);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        action.apply();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        setBackground(Value.cellColor.getValue());
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setBackground(Value.menuColor.getValue());
    }
}
