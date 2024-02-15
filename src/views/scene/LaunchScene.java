package views.scene;

import engine.Constant;
import engine.Game;
import views.component.*;
import views.component.Icon;
import views.game.HeroView;
import views.layout.SpaceLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.io.Serial;

public class LaunchScene extends Scene {

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = -826664995206508771L;
    public HeroView[] heroes;
    public NavListener listener;
    NavPanel navPanel;

    BackPanel backPanel;
    int selectedHero;

    public LaunchScene() {
        super(new BorderLayout());
        OpaquePanel p = new OpaquePanel(new BorderLayout());
        heroes = new HeroView[Game.existingHeroes.size()];
        if (heroes.length == 0) return;
        listener = new NavListener(this);
        OpaquePanel panel = new OpaquePanel(new SpaceLayout(false));
        panel.add(new Icon(new ImageIcon(Constant.ImagesDirectory + Constant.Setting).getImage(), 20, listener));
        panel.add(new Icon(new ImageIcon(Constant.ImagesDirectory + Constant.Home).getImage(), 20, listener, "home"));
        p.add(panel, BorderLayout.NORTH);
        navPanel = new NavPanel(false);
        for (int i = 0; i < heroes.length; i++) {
            heroes[i] = new HeroView(Game.existingHeroes.get(i));
            navPanel.add(new XPanel(heroes[i], HeroView.maxDimension));
        }
        heroes[0].focus();
        p.add(navPanel);
        backPanel = new BackPanel();
        add(backPanel);
        backPanel.add(p);
    }

    @Override
    public KeyListener getKeyListener() {
        return listener;
    }

    @Override
    public void setVisible(boolean aFlag) {
        if (backPanel != null){
            if (aFlag)
                backPanel.goOn();
            else
                backPanel.exit();
        }
        super.setVisible(aFlag);
    }
}