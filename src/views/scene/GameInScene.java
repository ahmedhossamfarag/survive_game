package views.scene;

import engine.Constant;
import engine.Game;
import engine.Main;
import engine.Network;
import views.component.OpaquePanel;
import views.component.XButton;
import views.layout.DirectionLayout;
import views.layout.SpaceLayout;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.UnknownHostException;

public class GameInScene extends OpaquePanel {
    /**
     *
     */
    private static final long serialVersionUID = -5507847192704090940L;

    public GameInScene() {
        super(new BorderLayout(), false);
        OpaquePanel panel = new OpaquePanel(new SpaceLayout(false));
        panel.add(new views.component.Icon(new ImageIcon(Constant.ImagesDirectory + Constant.Home).getImage(), 20, NameScene.listener(), "home"));
        add(panel, BorderLayout.NORTH);
        OpaquePanel content = new OpaquePanel(new SpaceLayout(true));
        OpaquePanel p = new OpaquePanel(new DirectionLayout(10, false, DirectionLayout.Centre));
        JLabel label = new JLabel("Enter Game Host.");
        label.setForeground(Constant.TextColor);
        label.setBackground(backColor);
        p.add(label);
        JTextField txt = new JTextField();
        txt.setPreferredSize(new Dimension(200, 20));
        p.add(txt);
        p.add(new XButton("Next", e -> {
            try {
                Network.enterGame(txt.getText());
                Game.listenNet();
                Game.gameId = true;
                Game.create = false;
                Window.introScene.set(Window.introScene.waitScene);
                Window.introScene.waitScene.listen();
            } catch (UnknownHostException ex) {
                JOptionPane.showMessageDialog(Main.window, "UnKnown Host Name!!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(Main.window, "Connection Problem!!");
            }
        }));
        content.add(p);
        add(content);
    }

}
