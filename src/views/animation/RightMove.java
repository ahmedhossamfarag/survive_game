package views.animation;

import java.awt.*;

public class RightMove extends MoveAnimation {
    public RightMove(Image[] images) {
        super(images);
    }

    @Override
    protected void move() {
        getAnimator().getPlayer().toX(ds);
    }
}
