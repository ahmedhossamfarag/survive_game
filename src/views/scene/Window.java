package views.scene;

import engine.*;
import objects.Hero;
import views.layout.FullLayout;
import views.settings.Settings;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.Serial;

public class Window extends JFrame {
    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 5513923706240736035L;
    public static LaunchScene launchScene;
    public static Settings settings;
    public static GameScene gameScene;

    public static ProgressScene progressScene;

    public static IntroScene introScene;
    public Scene currentScene;


    public Window() {
        addWindowListener(new WindowListener());
        setLayout(new FullLayout(true));
        Game.launchGame();
        launchScene = new LaunchScene();
        settings = new Settings();
        progressScene = new ProgressScene();
        introScene = new IntroScene();
        add(launchScene);
        add(progressScene);
        add(introScene);
        setScene(introScene);
        setSize(700, 700);
        playAudio(Constant.NavSound);
        setVisible(true);
    }

    public static void playAudio(String s) {
        if (!Value.soundEnabled.getValue()) return;
        try {
            File file = new File(Constant.SoundsDirectory + s);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception ignored) {

        }
    }

    public void play(Hero h) {
        gameScene = new GameScene(h);
        add(gameScene);
        progress(gameScene);
    }

    public void progress(Scene s) {
        setScene(progressScene);
        progressScene.showProgress(() -> {
            setScene(s);
            Main.window.setVisible(false);
            Main.window.setVisible(true);
        });
    }

    public void showMassage(String m) {
        gameScene.showMassage(m);
    }

    public void hideMassage() {
        gameScene.showMassage(Constant.InitialMassage);
    }

    public void gameOver() {
        gameScene.setGameOver();
        gameEnd();
    }

    public void gameWin() {
        gameScene.setGameWin();
        gameEnd();
    }

    public void gameEnd() {
        removeKeyListener(Game.actionListener);
        Animation.of(1000,
                e -> {
                    if (Game.create)
                        goHome();
                    else {
                        introScene.set(introScene.waitScene);
                        Main.window.progress(Window.introScene);
                        Window.introScene.waitScene.listen();
                    }
                    remove(gameScene);
                });
    }

    public void showSetting() {
        settings.setLocation(getLocation());
        settings.setVisible(true);
    }

    public void goHome() {
        setScene(launchScene);
    }

    public void goToStart() {
        Network.exit();
        Game.gameId = false;
        Game.create = true;
        introScene.set(introScene.chooseScene);
        Main.window.progress(Window.introScene);
    }

    public void setScene(Scene scene) {
        if (currentScene != null) {
            currentScene.setVisible(false);
            if (currentScene.getKeyListener() != null)
                removeKeyListener(currentScene.getKeyListener());
        }
        scene.setVisible(true);
        if (scene.getKeyListener() != null)
            addKeyListener(scene.getKeyListener());
        currentScene = scene;
    }

    public void showHelp(){
        String[] massages = Constant.HelpMassage.split(",");
        JPanel panel = new JPanel(new GridLayout(massages.length, 2));
        for (String m : massages){
            String[] s = m.split("~");
            JLabel name = new JLabel(s[1]);
            name.setFont(Constant.TextFont1);
            panel.add(name);
            JLabel value = new JLabel(s[0].toUpperCase());
            value.setFont(Constant.TextFont1);
            value.setHorizontalAlignment(SwingConstants.RIGHT);
            panel.add(value);
        }
        JOptionPane.showMessageDialog(this, panel, "Key Events", JOptionPane.INFORMATION_MESSAGE);
    }
}
