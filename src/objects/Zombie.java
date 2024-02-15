package objects;

import engine.Constant;
import engine.Game;
import engine.Update;
import views.animation.ZombieAnimator;
import views.game.Cell;
import views.game.Map;
import views.game.Status;
import views.game.ZombieStatus;
import views.scene.Window;

import javax.swing.*;
import java.awt.*;

public class Zombie extends Character {
    public static final int ZombieHp = 40, ZombieAttDamage = 10;
    public static Image IMAGE = new ImageIcon(Constant.ImagesDirectory + Constant.Zombie).getImage();
    public final ZombieAnimator animator;
    private ZombieState state;

    public Zombie(Point p) {
        super(ZombieHp, ZombieAttDamage);
        setLocation(p);
        state = new ZombieState(this);
        animator = new ZombieAnimator();
        state.setStatus(new ZombieStatus(this));
    }

    public static void execute(Update u) {
        String[] arr = u.Content.split(",");
        int id = Integer.parseInt(arr[0]);
        Zombie z = (Zombie) Map.getObject(id);
        if (u.UpdateAction == 'h')
            z.state._setHp(Integer.parseInt(arr[1]));
        else if (u.UpdateAction == 'a')
            z.state._attack(new Point(Integer.parseInt(arr[1]), Integer.parseInt(arr[2])));
    }

    public static GameObject of(String s) {
        String[] arr = s.split(",");
        int id = Integer.parseInt(arr[1]);
        int x = Integer.parseInt(arr[2]), y = Integer.parseInt(arr[3]);
        Zombie z = new Zombie(new Point(x, y));
        z.setId(id);
        return z;
    }

    @Override
    public Image getImage() {
        return animator.getImage();
    }

    public void attack() {
        for (Cell c : Game.getRegion(this)) {
            if (c.isHero()) {
                super.attack((Hero) c.getObject());
                Window.playAudio(Constant.ZombieSound);
                if (c.getObject() != null)
                    state.attack(c.getObject().getLocation());
                return;
            }
        }
    }

    @Override
    public void destroy() {
        Game.destroy(this);
    }

    @Override
    public int getCurrentHp() {
        return state.getHp();
    }

    @Override
    public void setCurrentHp(int hp) {
        state.setHp(hp);
    }

    @Override
    public Status getInfo() {
        return state.getStatus();
    }

    @Override
    public String toString() {
        return "z," + super.toString();
    }

    @Override
    public GameObject clone_() {
        try {
            Zombie z = (Zombie) super.clone();
            z.state = state.clone();
            z.state.zombie = z;
            z.state.status = new ZombieStatus(z);
            return z;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
