package views.component;

import engine.Action;
import engine.Animation;
import engine.Constant;
import views.layout.ExpandLayout;
import views.scene.Window;

import java.io.Serial;

public class NavPanel extends OpaquePanel {
    /**
     *
     */
    @Serial
    private static final long serialVersionUID = -1692770728245691255L;
    private final ExpandLayout layout;
    private boolean active;
    private int count;

    public NavPanel(boolean vertical) {
        super(new ExpandLayout(vertical));
        layout = (ExpandLayout) getLayout();
        setLayout(layout);
        active = true;
    }

    public void navPos(Action a) {
        if (active) {
            active = false;
            animate(1, a);
        }
    }

    public void navNeg(Action a) {
        if (active) {
            active = false;
            animate(-1, a);
        }
    }

    public boolean isActive() {
        return active;
    }

    private void animate(int d, Action a) {
        Animation.of(
                30,
                i -> {
                    count += d;
                    layout.setStart((float) (count / 30.0));
                    layout.layoutContainer(this);
                    repaint();
                },
                n -> {
                    if (a != null)
                        a.apply();
                    active = true;
                    Window.playAudio(Constant.NavSound);
                }
        );
    }
}
