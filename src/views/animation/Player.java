package views.animation;

import objects.GameObject;
import objects.Hero;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends GameObject {
    private final Animator animator;
    private double x, y;

    private Hero hero;

    public Player(Animator animator, Hero hero) {
        this.animator = animator;
        this.hero = hero;
        this.animator.setPlayer(this);
    }

    public static Player of(Hero hero, Image[] up, Image[] down, Image[] right, Image[] left,
                            Image[] attUp, Image[] attDown, Image[] attRight, Image[] attLeft) {
        Animator a = new Animator(up[0]);
        a.addAnimation(new UpMove(up), KeyEvent.VK_UP);
        a.addAnimation(new DownMove(down), KeyEvent.VK_DOWN);
        a.addAnimation(new RightMove(right), KeyEvent.VK_RIGHT);
        a.addAnimation(new LeftMove(left), KeyEvent.VK_LEFT);
        a.addAnimation(new AttackAnimation(attUp), -KeyEvent.VK_UP);
        a.addAnimation(new AttackAnimation(attDown), -KeyEvent.VK_DOWN);
        a.addAnimation(new AttackAnimation(attRight), -KeyEvent.VK_RIGHT);
        a.addAnimation(new AttackAnimation(attLeft), -KeyEvent.VK_LEFT);
        return new Player(a, hero);
    }

    public void setLocation(Point p) {
        x = p.x;
        y = p.y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public Image getImage() {
        return animator.getImage();
    }

    @Override
    public GameObject clone_() {
        return null;
    }

    public void toY(double ds) {
        y += ds;
    }

    public void toX(double ds) {
        x += ds;
    }

    public Animator getAnimator() {
        return animator;
    }

    public Hero getHero() {
        return hero;
    }
}
