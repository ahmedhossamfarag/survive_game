package objects;

import engine.Constant;
import engine.Game;
import exceptions.InvalidTargetException;
import views.animation.ImagesDir;
import views.animation.Player;

import java.awt.*;

public class Explorer extends Hero {

    public static final Image IMAGE = getImageOf(Constant.Explorer);

    public Explorer(String name, int maxHp, int attackDmg, int maxActions) {
        super(name, maxHp, attackDmg, maxActions);
    }

    @Override
    public Image getViewImage() {
        return IMAGE;
    }

    @Override
    public void useSpecial() throws InvalidTargetException {
        super.useSpecial();
        Game.map.setVisible(true);
    }

    @Override
    public String toString() {
        return "e," + super.toString();
    }

    @Override
    public GameObject clone_() {
        Hero h = (Hero) super.clone_();
        h.setPlayer(Player.of(h, ImagesDir.Explorer_up, ImagesDir.Explorer_down, ImagesDir.Explorer_right, ImagesDir.Explorer_left,
                ImagesDir.Explorer_att_up, ImagesDir.Explorer_att_down, ImagesDir.Explorer_att_right, ImagesDir.Explorer_att_left));
        return h;
    }
}
