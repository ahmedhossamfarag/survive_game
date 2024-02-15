package objects;

import views.game.Map;

import java.awt.*;

public class TrapHero extends GameObject {
    private final Hero hero;
    private final Trap trap;

    public TrapHero(Hero hero, Trap trap) {
        this.trap = trap;
        this.hero = hero;
        setLocation(hero.getLocation());
    }

    public static GameObject of(String s) {
        String[] arr = s.split(",");
        int id = Integer.parseInt(arr[1]);
        Hero h = (Hero) Map.getObject(Integer.parseInt(arr[4]));
        Trap t = (Trap) Map.getObject(Integer.parseInt(arr[5]));
        assert h != null;
        TrapHero x = new TrapHero(h, t);
        x.setId(id);
        return x;
    }

    @Override
    public Image getImage() {
        return hero.getTrapImage();
    }

    @Override
    public String toString() {
        return "x," + super.toString() + "," + hero.getId()  +","+ trap.getId();
    }

    @Override
    public GameObject clone_() {
        try {
            return (TrapHero) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public GameObject getTrap() {
        return trap;
    }
}
