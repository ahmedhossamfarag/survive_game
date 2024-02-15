package objects;

import java.awt.*;
import java.util.Random;

public class Trap extends GameObject {

    private final int trapDamage;

    public Trap(Point p) {
        setLocation(p);
        int[] x = {10, 20, 30};
        Random r = new Random();
        int result = r.nextInt(3);
        trapDamage = x[result];
    }

    public Trap(Point p, int d) {
        setLocation(p);
        trapDamage = d;
    }

    public static GameObject of(String s) {
        String[] arr = s.split(",");
        int id = Integer.parseInt(arr[1]);
        int x = Integer.parseInt(arr[2]), y = Integer.parseInt(arr[3]);
        int d = Integer.parseInt(arr[4]);
        Trap t = new Trap(new Point(x, y), d);
        t.setId(id);
        return t;
    }

    @Override
    public Image getImage() {
        return null;
    }

    public void affect(Hero hero) {
        hero.decreaseHp(trapDamage);
    }

    @Override
    public String toString() {
        return "t," + super.toString() + ',' + trapDamage;
    }

    @Override
    public GameObject clone_() {
        try {
            return (Trap) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
