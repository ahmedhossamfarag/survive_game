package views.animation;

import java.awt.*;

public class UpMove extends MoveAnimation {
    public UpMove(Image[] images) {
        super(images);
    }

    @Override
    protected void move() {
        getAnimator().getPlayer().toY(-ds);
    }
}
