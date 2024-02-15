package objects;

import java.awt.*;

public abstract class Collectable extends GameObject {

    public Collectable(Point p) {
        setLocation(p);
    }

    @Override
    public GameObject clone_() {
        try {
            return (Collectable) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }


}
