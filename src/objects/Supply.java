package objects;

import engine.Constant;

import java.awt.*;

public class Supply extends Collectable {
    public static final Image IMAGE = getImageOf(Constant.Supply);

    public Supply(Point p) {
        super(p);
    }

    public static GameObject of(String s) {
        String[] arr = s.split(",");
        int id = Integer.parseInt(arr[1]);
        int x = Integer.parseInt(arr[2]), y = Integer.parseInt(arr[3]);
        Supply u = new Supply(new Point(x, y));
        u.setId(id);
        return u;
    }

    @Override
    public Image getImage() {
        return IMAGE;
    }

    @Override
    public String toString() {
        return "s," + super.toString();
    }
}
