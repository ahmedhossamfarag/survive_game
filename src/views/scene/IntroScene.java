package views.scene;

import views.component.BackPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.io.Serial;

public class IntroScene extends Scene {

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = -4550005401359380583L;
    public BackPanel backPanel;
    public ChooseScene chooseScene;

    public NameScene nameScene;

    public NetScene netScene;


    public GameIdScene gameIdScene;
    public GameInScene gameInScene;

    public WaitScene waitScene;

    private JPanel current;

    public IntroScene() {
        super(new BorderLayout());
        backPanel = new BackPanel();
        add(backPanel);
        chooseScene = new ChooseScene();
        nameScene = new NameScene();
        netScene = new NetScene();
        gameIdScene = new GameIdScene();
        gameInScene = new GameInScene();
        waitScene = new WaitScene();
        backPanel.add(chooseScene);
        backPanel.add(nameScene);
        backPanel.add(netScene);
        backPanel.add(gameIdScene);
        backPanel.add(gameInScene);
        backPanel.add(waitScene);
        current = chooseScene;
        current.setVisible(true);
    }

    public void set(JPanel scene) {
        current.setVisible(false);
        current = scene;
        scene.setVisible(true);
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

    @Override
    public KeyListener getKeyListener() {
        return null;
    }
}
