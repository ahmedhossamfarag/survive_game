package views.scene;

import engine.Constant;
import engine.Game;
import engine.Network;
import views.component.OpaquePanel;
import views.component.XButton;
import views.layout.SpaceLayout;

import javax.swing.*;
import java.awt.*;

public class NetScene extends OpaquePanel {
    /**
     *
     */
    private static final long serialVersionUID = 3924195539895291756L;

    public NetScene() {
        super(new BorderLayout(), false);
        OpaquePanel panel = new OpaquePanel(new SpaceLayout(false));
        panel.add(new views.component.Icon(new ImageIcon(Constant.ImagesDirectory + Constant.Home).getImage(), 20, NameScene.listener(), "home"));
        add(panel, BorderLayout.NORTH);
        OpaquePanel content = new OpaquePanel(new SpaceLayout(true));
        content.add(new XButton("Create New Game", e -> {
            Game.gameId = true;
            Window.introScene.set(Window.introScene.gameIdScene);
            Window.introScene.gameIdScene.listen();
            Network.createGame();
        }));
        content.add(new XButton("Enter Game ID", e -> Window.introScene.set(Window.introScene.gameInScene)));
        add(content);
    }
}
