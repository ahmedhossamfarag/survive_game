package views.animation;

import java.awt.*;

public class Animation {
    protected Image[] images;
    protected int count;
    private Animator animator;

    public Animation(Image[] images) {
        this.images = images;
    }

    public Animator getAnimator() {
        return animator;
    }

    public void setAnimator(Animator animator) {
        this.animator = animator;
    }

    public Image getImage() {
        return images[count++ % images.length];
    }
}
