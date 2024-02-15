package views.scene;

import engine.Constant;
import engine.Game;
import engine.Network;
import objects.Hero;
import views.animation.MapAnimator;
import views.component.ActionsList;
import views.component.XLabel;
import views.component.XPanel;
import views.layout.Constrains;
import views.layout.ConstrainsLayout;
import views.layout.DirectionLayout;

import java.awt.*;
import java.awt.event.KeyListener;
import java.io.Serial;

public class GameScene extends Scene {

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 2288588216003020783L;

    private final EndScene gameOver;

    private final WinScene gameWin;
    private final XLabel massage;
    private final MapAnimator animator;
    public ActionsList actionsList;

    public GameScene(Hero h) {
        super(new ConstrainsLayout());
        XPanel panel = new XPanel(new BorderLayout());
        Game.startGame(h);
        panel.add(Game.heroStatus, BorderLayout.NORTH);
        panel.add(Game.map, BorderLayout.CENTER);
        animator = new MapAnimator(Game.map);
        XPanel panel1 = new XPanel(new DirectionLayout());
        massage = new XLabel(Constant.InitialMassage, Label.CENTER);
        panel1.addAll(Game.selectedStatus, massage);
        panel.add(panel1, BorderLayout.SOUTH);
        gameOver = new EndScene();
        add(gameOver, new Constrains(0, 0, 0, 0, false));
        gameWin = new WinScene();
        add(gameWin, new Constrains(0, 0, 0, 0, false));
        actionsList = new ActionsList();
        add(actionsList, null);
        add(panel, new Constrains(0, 0, 0, 0, true));
        animator.play();
    }

    public void setGameOver() {
        gameOver.setVisible(true);
        Window.playAudio(Constant.GameOverSound);
        animator.stop();
        Network.exit();
    }

    public void showMassage(String m) {
        massage.setText(m);
    }

    public void setGameWin() {
        gameWin.setVisible(true);
        Window.playAudio(Constant.WinSound);
        animator.stop();
        Network.exit();
    }

    @Override
    public KeyListener getKeyListener() {
        return Game.actionListener;
    }
}
