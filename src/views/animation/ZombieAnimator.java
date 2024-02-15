package views.animation;

import engine.Action;

import java.awt.event.KeyEvent;
import java.util.Random;

public class ZombieAnimator extends Animator {
    private final int n;

    public ZombieAnimator() {
        super(ImagesDir.Zombie_down[0]);
        addAnimation(new AnimationByN(ImagesDir.Zombie_S_right, 5), 0);
        addAnimation(new AnimationByN(ImagesDir.Zombie_S_left, 5), 0);
        addAnimation(new AttackAnimation(ImagesDir.Zombie_up), KeyEvent.VK_UP);
        addAnimation(new AttackAnimation(ImagesDir.Zombie_down), KeyEvent.VK_DOWN);
        addAnimation(new AttackAnimation(ImagesDir.Zombie_right), KeyEvent.VK_RIGHT);
        addAnimation(new AttackAnimation(ImagesDir.Zombie_left), KeyEvent.VK_LEFT);
        n = new Random().nextInt(2);
        reset();
    }

    @Override
    public void riseEvent(int event, Action prev, Action action) {
        currentAnimation = null;
        super.riseEvent(event, prev, action);
    }

    @Override
    public void reset() {
        currentAnimation = animations.get(n);
    }
}
