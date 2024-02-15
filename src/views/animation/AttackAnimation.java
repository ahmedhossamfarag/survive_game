package views.animation;

import java.awt.*;

public class AttackAnimation extends Animation {
    public AttackAnimation(Image[] images) {
        super(images);
    }

    @Override
    public Image getImage() {
        if (count == 8) {
            getAnimator().reset();
        }
        return images[(count++ / 2) % images.length];
    }
}
