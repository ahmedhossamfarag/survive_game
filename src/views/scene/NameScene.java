package views.scene;

import engine.Constant;
import views.component.OpaquePanel;
import views.component.XButton;
import views.layout.DirectionLayout;
import views.layout.SpaceLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class NameScene extends OpaquePanel {
    /**
     *
     */
    private static final long serialVersionUID = -8341348389557602734L;

    public NameScene() {
        super(new BorderLayout(), false);
        OpaquePanel panel = new OpaquePanel(new SpaceLayout(false), true);
        panel.add(new views.component.Icon(new ImageIcon(Constant.ImagesDirectory + Constant.Home).getImage(), 20, listener(), "home"));
        add(panel, BorderLayout.NORTH);
        OpaquePanel content = new OpaquePanel(new SpaceLayout(true));
        OpaquePanel p = new OpaquePanel(new DirectionLayout(10, false, DirectionLayout.Centre));
        JLabel label = new JLabel("Enter Your Name.");
        label.setForeground(Constant.TextColor);
        label.setBackground(backColor);
        p.add(label);
        JTextField txt = new JTextField();
        txt.setPreferredSize(new Dimension(200, 20));
        p.add(txt);
        p.add(new XButton("Next", e -> {
            Window.introScene.set(Window.introScene.netScene);
        }));
        content.add(p);
        add(content);
    }

    public static MouseListener listener() {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Window.introScene.set(Window.introScene.chooseScene);
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        };
    }
}
