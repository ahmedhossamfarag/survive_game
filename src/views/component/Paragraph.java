package views.component;

import engine.Constant;
import views.layout.DirectionLayout;

import javax.swing.*;
import java.awt.*;

public class Paragraph extends XPanel {

    /**
     *
     */
    private static final long serialVersionUID = -123636489412372562L;

    public Paragraph(String par,
                     Font titleFont, Font bodyFont,
                     int titleAlign, int bodyAlign,
                     Color titleColor, Color bodyColor,
                     int gap) {
        super(new DirectionLayout());
        String[] ss = par.split("\n");
        if (ss.length == 0) return;
        add(new XLabel(ss[0], titleFont, titleAlign, titleColor));
        JLabel g = new JLabel();
        g.setPreferredSize(new Dimension(0, gap));
        add(g);
        for (int i = 1; i < ss.length; i++)
            add(new XLabel(ss[i], bodyFont, bodyAlign, bodyColor));
    }

    public Paragraph(String s) {
        this(s,
                Constant.TextFont2, Constant.TextFont1,
                Label.LEFT, Label.LEFT,
                Constant.TextColor, Constant.TextColor,
                10);
    }
}
