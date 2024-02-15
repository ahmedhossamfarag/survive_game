package views.game;

import objects.Zombie;
import views.component.Icon;
import views.component.XLabel;

import javax.swing.*;
import java.awt.*;

public class ZombieStatus extends Status {
    /**
     *
     */
    private static final long serialVersionUID = 2492076368474082697L;
    private final JProgressBar hp;

    public ZombieStatus(Zombie z) {
        Icon icon = new Icon(Zombie.IMAGE, 15);
        hp = new JProgressBar(0, z.getMaxHp());
        hp.setValue(z.getCurrentHp());
        hp.setPreferredSize(new Dimension(120, 15));
        hp.setToolTipText("Hp Points");
        hp.setString("HP");
        hp.setStringPainted(true);
        this.add(icon);
        this.add(hp);
        this.add(new XLabel("DM " + z.getAttackDmg()));
    }

    @Override
    public void setHp(int hp) {
        this.hp.setValue(hp);
    }
}
