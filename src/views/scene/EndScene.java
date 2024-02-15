package views.scene;

import engine.Constant;
import views.component.XLabel;

public class EndScene extends XLabel {
    /**
     *
     */
    private static final long serialVersionUID = 8763616334850657096L;

    public EndScene() {
        super("Game Over", Constant.OpaqueColor, Constant.TextFont2);
        setAlignment(CENTER);
        setVisible(false);
    }
}
