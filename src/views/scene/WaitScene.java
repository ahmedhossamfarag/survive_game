package views.scene;

import engine.Animation;
import engine.Game;
import engine.Main;
import views.component.OpaquePanel;
import views.component.XPanel;
import views.layout.SpaceLayout;

import java.awt.*;

public class WaitScene extends OpaquePanel {
    /**
     *
     */
    private static final long serialVersionUID = 3118534500834996325L;
    private final XPanel[] progress;
    private boolean listen;

    public WaitScene() {
        super(new SpaceLayout(true), false);
        OpaquePanel p = new OpaquePanel(new SpaceLayout(false));
        p.setPreferredSize(new Dimension(300, 20));
        progress = new XPanel[5];
        for (int i = 0; i < progress.length; i++) {
            progress[i] = new XPanel(new Dimension(10, 10), Color.WHITE);
            p.add(progress[i]);
        }
        add(p);
    }

    public void listen() {
        listen = true;
        for (XPanel p : progress) p.setVisible(false);
        new Animation(200, 500, j -> {
            int i = j % (progress.length + 1);
            if (i == progress.length)
                for (XPanel p : progress) p.setVisible(false);
            else
                progress[i].setVisible(true);
        }, i -> listen, i -> {
            if (Game.gameId) Main.window.progress(Window.launchScene);
        }).play();
    }

    public void endListen() {
        listen = false;
    }
}
