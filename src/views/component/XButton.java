package views.component;

import engine.Constant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.Serial;

public class XButton extends JButton {
    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 286736477972986876L;
    private boolean round;

    public XButton() {
        setBackground(Constant.BackColor);
        setForeground(Constant.TextColor);
        setFont(Constant.TextFont1);
        setBorderPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public XButton(String s) {
        this();
        setText(s);
        round = true;
    }

    public XButton(String s, ActionListener a) {
        this(s);
        addActionListener(a);
    }

    public XButton(Dimension d, Color c, ActionListener a) {
        setPreferredSize(d);
        addActionListener(a);
        setBackground(c);
        setBorderPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (round) {
            GradientPaint gp = new GradientPaint(0, 0, Constant.ForeColor, 15, 25, Color.WHITE, true);
            ((Graphics2D) g).setPaint(gp);
            ((Graphics2D) g).setStroke(new BasicStroke(5));
            g.drawRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getHeight());
        }
    }
}
