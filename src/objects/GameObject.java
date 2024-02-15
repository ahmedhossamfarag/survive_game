package objects;

import engine.Constant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public abstract class GameObject implements Cloneable {
    private int Id;
    private Point location;

    public static Image getImageOf(String src) {
        return new ImageIcon(Constant.ImagesDirectory + src).getImage();
    }

    public static GameObject of(String s) {
        return switch (s.charAt(0)) {
            case 'v' -> Vaccine.of(s);
            case 's' -> Supply.of(s);
            case 't' -> Trap.of(s);
            case 'x' -> TrapHero.of(s);
            default -> Character.of(s);
        };
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public abstract Image getImage();

    @Override
    public String toString() {
        return "%s,%s,%s".formatted(Id, location.x, location.y);
    }

    public int LocationToCode(Point p) {
        int dx = location.x - p.x, dy = location.y - p.y;
        int[] dxa = {1, 0, -1, 1, 0, -1, 1, 0, -1};
        int[] dya = {1, 1, 1, 0, 0, 0, -1, -1, -1};
        int[] code = {KeyEvent.VK_LEFT, KeyEvent.VK_UP, KeyEvent.VK_RIGHT,
                KeyEvent.VK_LEFT, KeyEvent.VK_UP, KeyEvent.VK_RIGHT,
                KeyEvent.VK_LEFT, KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT};
        for (int i = 0; i < dxa.length; i++) {
            if (dx == dxa[i] && dy == dya[i])
                return code[i];
        }
        return 0;
    }

    public abstract GameObject clone_();
}
