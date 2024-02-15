package views.scene;

import engine.Main;
import engine.Network;
import engine.Update;
import views.component.Icon;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class NavListener implements KeyListener, MouseListener {
    private final LaunchScene scene;

    public NavListener(LaunchScene scene) {
        this.scene = scene;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT -> {
                if (scene.selectedHero < scene.heroes.length - 1) {
                    scene.heroes[scene.selectedHero].endFocus();
                    if (scene.navPanel.isActive()) {
                        scene.selectedHero++;
                        scene.navPanel.navNeg(() -> scene.heroes[scene.selectedHero].focus());
                    }
                }
            }
            case KeyEvent.VK_LEFT -> {
                if (scene.selectedHero > 0) {
                    scene.heroes[scene.selectedHero].endFocus();
                    if (scene.navPanel.isActive()) {
                        scene.selectedHero--;
                        scene.navPanel.navPos(() -> scene.heroes[scene.selectedHero].focus());
                    }
                }
            }
            case KeyEvent.VK_ENTER -> Main.window.play(scene.heroes[scene.selectedHero].getHero());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getComponent() instanceof Icon i) {
            if (i.getTitle() == null)
                Main.window.showSetting();
            else {
                Main.window.goToStart();
                Network.send(new Update('g', 'x', ""));
            }
        }
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
}

