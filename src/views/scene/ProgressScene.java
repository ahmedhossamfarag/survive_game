package views.scene;

import engine.Action;
import engine.Animation;

import java.awt.*;
import java.awt.event.KeyListener;

public class ProgressScene extends Scene {
    /**
     *
     */
    private static final long serialVersionUID = -39777363210937837L;
    private double pl;

    public ProgressScene() {
        super(new FlowLayout());
        setVisible(false);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        double pw = 0.8, ph = 0.05;
        int w = getWidth(), h = getHeight();
        int x = (int) ((1 - pw) * (w / 2)), y = (int) ((1 - ph) * (h / 2)), l = (int) (w * pw * pl), r = (int) (h * ph);
        g.setColor(Color.WHITE);
        g.fillRoundRect(x, y, l, r, r, r);
    }

    public void showProgress(Action action) {
        pl = 0;
        Animation.of(10,
                i -> {
                    pl += 0.1;
                    repaint();
                }, i -> action.apply());
    }

    @Override
    public KeyListener getKeyListener() {
        return null;
    }
}
