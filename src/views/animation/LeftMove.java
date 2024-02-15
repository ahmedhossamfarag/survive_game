package views.animation;

import java.awt.*;

public class LeftMove extends MoveAnimation {
    public LeftMove(Image[] images) {
        super(images);
    }

    @Override
    protected void move() {
        getAnimator().getPlayer().toX(-ds);
    }
}
