package views.animation;

import engine.PlayerAction;

import java.awt.*;

public abstract class MoveAnimation extends Animation {
    public static final double ds = (MapAnimator.sleep / 1000.0) * 2;

    public MoveAnimation(Image[] images) {
        super(images);
    }


    @Override
    public Image getImage() {
        if (count >= 1.0 / ds) {
            PlayerAction.listen = true;
            getAnimator().setStaticImage(images[0]);
            Player p = getAnimator().getPlayer();
            p.setLocation(p.getHero().getLocation());
            getAnimator().reset();
        } else
            move();
        return super.getImage();
    }

    protected abstract void move();
}
