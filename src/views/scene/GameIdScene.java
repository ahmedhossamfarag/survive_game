package views.scene;

import engine.Animation;
import engine.Constant;
import engine.Main;
import engine.Network;
import views.component.OpaquePanel;
import views.component.XPanel;
import views.layout.DirectionLayout;
import views.layout.SpaceLayout;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class GameIdScene extends OpaquePanel {
    /**
     *
     */
    @Serial
    private static final long serialVersionUID = -4092727094268724840L;
    private final XPanel[] progress;
    private boolean success;

    public GameIdScene() {
        super(new SpaceLayout(true), false);
        OpaquePanel content = new OpaquePanel(new DirectionLayout(20));
        JLabel label = new JLabel("Waiting..");
        label.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 50));
        label.setForeground(Constant.TextColor);
        label.setBackground(OpaquePanel.backColor);
        content.add(label);
        JLabel id = new JLabel();
        id.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 20));
        try {
            id.setText(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException ignored) {
        }
        id.setHorizontalAlignment(SwingConstants.CENTER);
        id.setForeground(Constant.TextColor);
        id.setBackground(OpaquePanel.backColor);
        content.add(id);
        OpaquePanel p = new OpaquePanel(new SpaceLayout(false));
        progress = new XPanel[5];
        for (int i = 0; i < progress.length; i++) {
            progress[i] = new XPanel(new Dimension(10, 10), Color.WHITE);
            p.add(progress[i]);
        }
        content.add(p);
        add(content);
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void listen() {
        for (XPanel p : progress) p.setVisible(false);
        Animation a = new Animation(200, 500, j -> {
            int i = j % (progress.length + 1);
            if (i == progress.length)
                for (XPanel p : progress) p.setVisible(false);
            else
                progress[i].setVisible(true);
        }, i -> Network.run, i -> {
            if (success) {
                Main.window.progress(Window.launchScene);
            }
        });
        a.play();
    }
}
