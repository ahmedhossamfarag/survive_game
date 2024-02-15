package views.scene;

import engine.Constant;
import engine.Main;
import views.component.OpaquePanel;
import views.component.RootButton;
import views.layout.StackLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serial;

public class ChooseScene extends OpaquePanel {
    /**
     *
     */
    @Serial
    private static final long serialVersionUID = -6111901767845005828L;
    private final JLabel label;
    public ChooseScene() {
        super(new StackLayout(), false);
        label = new JLabel();
        label.setBackground(OpaquePanel.backColor);
        label.setForeground(Color.WHITE);
        add(label);
        add(getButton("Single Player",
                Constant.ImagesDirectory + "single.png",
                e -> Main.window.progress(Window.launchScene)));
        add(getButton("Two Player",
                Constant.ImagesDirectory + "multiple.png",
                e -> Window.introScene.set(Window.introScene.nameScene)));
        add(getButton("Options",
                Constant.ImagesDirectory + "options.png",
                e -> Main.window.showSetting()));
        add(getButton("Quit",
                Constant.ImagesDirectory + "exit.png",
                e -> {
                    Window.introScene.backPanel.exit();
                    System.exit(0);
        }));
    }

    public RootButton getButton(String s, String img, ActionListener actionListener) {
        RootButton b = new RootButton(img, actionListener);
        b.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                actionListener.actionPerformed(null);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                label.setText(s);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setText("");
            }
        });
        return b;
    }
}
