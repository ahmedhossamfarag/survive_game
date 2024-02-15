package views.scene;

import engine.Constant;
import views.component.Icon;

import javax.swing.*;

public class WinScene extends Icon {

    /**
     *
     */
    private static final long serialVersionUID = -6739787544135355977L;

    public WinScene() {
        super(new ImageIcon(Constant.ImagesDirectory + Constant.Cup).getImage(), 500);
        setVisible(false);
    }
}
