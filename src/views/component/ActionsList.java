package views.component;

import engine.Constant;
import engine.Game;
import engine.Valid;
import objects.Medic;
import views.layout.DirectionLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class ActionsList extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = -3480536836586361675L;
    private final ActionLabel attack, cure, choose, special, end;

    public ActionsList() {
        super(new DirectionLayout());
        attack = new ActionLabel("Attack", () -> Game.actionListener.keyTyped(getKey('a')));
        cure = new ActionLabel("Cure", () -> {
            Game.actionListener.keyTyped(getKey('q'));
            setVisible(false);
        });
        choose = new ActionLabel("Choose", () -> {
            Game.actionListener.keyTyped(getKey('x'));
            setVisible(false);
        });
        special = new ActionLabel("Special", () -> {
            Game.actionListener.keyTyped(getKey('s'));
            setVisible(false);
        });
        end = new ActionLabel("End", () -> Game.actionListener.keyTyped(getKey('e')));
        setVisible(false);
    }

    private KeyEvent getKey(char c) {
        return new KeyEvent(this, 0, 0, 0, 0, c);
    }

    public void set() {
        removeAll();
        locate();
        try {
            Valid.checkAttackValid();
            add(attack);
        } catch (Exception ignored) {
        }
        try {
            Valid.checkCureValid();
            add(cure);
        } catch (Exception ignored) {
        }
        try {
            Valid.checkChooseHeroValid();
            add(choose);
        } catch (Exception ignored) {
        }
        try {
            Valid.checkSpecialValid();
            if (Game.hero instanceof Medic)
                Valid.checkMedicSpecialValid();
            add(special);
        } catch (Exception ignored) {
        }
        add(end);
        setSize(getPreferredSize());
        setVisible(true);
    }

    public void locate() {
        if (Game.selected == null) return;
        Point p = Game.selected.getCellLocation();
        Dimension d = new Dimension(Game.map.getWidth() / Constant.MapSize, Game.map.getHeight() / Constant.MapSize);
        setLocation((p.x * d.width) + (d.width / 2), (p.y * d.height) + (d.height / 2) + Game.map.getY());
    }
}
