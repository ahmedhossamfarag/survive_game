package objects;

import engine.Constant;
import engine.Game;
import engine.Valid;
import exceptions.InvalidTargetException;
import views.animation.ImagesDir;
import views.animation.Player;
import views.scene.Window;

import java.awt.*;

public class Medic extends Hero {

    public static final Image IMAGE = getImageOf(Constant.Medic);

    public Medic(String name, int maxHp, int attackDmg, int maxActions) {
        super(name, maxHp, attackDmg, maxActions);
    }

    @Override
    public Image getViewImage() {
        return IMAGE;
    }

    @Override
    public void useSpecial() throws InvalidTargetException {
        Valid.checkMedicSpecialValid();
        ((Hero) Game.selected.getObject()).resetHp();
        Window.playAudio(Constant.HpSound);
        super.useSpecial();
    }

    @Override
    public String toString() {
        return "m," + super.toString();
    }

    @Override
    public GameObject clone_() {
        Hero h = (Hero) super.clone_();
        h.setPlayer(Player.of(h, ImagesDir.Medic_up, ImagesDir.Medic_down, ImagesDir.Medic_right, ImagesDir.Medic_left,
                ImagesDir.Medic_att_up, ImagesDir.Medic_att_down, ImagesDir.Medic_att_right, ImagesDir.Medic_att_left));
        return h;
    }
}
