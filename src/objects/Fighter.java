package objects;

import engine.Constant;
import views.animation.ImagesDir;
import views.animation.Player;

import java.awt.*;

public class Fighter extends Hero {

    public static final Image IMAGE = getImageOf(Constant.Fighter);

    public Fighter(String name, int maxHp, int attackDmg, int maxActions) {
        super(name, maxHp, attackDmg, maxActions);
    }

    @Override
    public Image getViewImage() {
        return IMAGE;
    }

    @Override
    public String toString() {
        return "f," + super.toString();
    }

    @Override
    public GameObject clone_() {
        Hero h = (Hero) super.clone_();
        h.setPlayer(Player.of(h, ImagesDir.Fighter_up, ImagesDir.Fighter_down, ImagesDir.Fighter_right, ImagesDir.Fighter_left,
                ImagesDir.Fighter_att_up, ImagesDir.Fighter_att_down, ImagesDir.Fighter_att_right, ImagesDir.Fighter_att_left));
        return h;
    }
}
