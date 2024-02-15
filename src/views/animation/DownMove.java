package views.animation;

import java.awt.*;

public class DownMove extends MoveAnimation {
    public DownMove(Image[] images) {
        super(images);
    }

    @Override
    protected void move() {
        getAnimator().getPlayer().toY(ds);
    }
}
