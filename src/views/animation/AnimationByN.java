package views.animation;

import java.awt.*;

public class AnimationByN extends Animation {
    private final int N;

    public AnimationByN(Image[] images, int n) {
        super(images);
        N = n;
    }

    @Override
    public Image getImage() {
        return images[(count++ / N) % images.length];
    }
}
