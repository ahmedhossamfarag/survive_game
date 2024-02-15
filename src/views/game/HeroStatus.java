package views.game;

import objects.Hero;
import objects.Supply;
import objects.Vaccine;
import views.component.Icon;
import views.component.XLabel;
import views.component.XPanel;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;

public class HeroStatus extends Status {
    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 1842715121911036394L;
    private final JProgressBar hp;
    private final JProgressBar actions;

    private final XLabel vaccine, supply;

    private final JPanel special;

    public HeroStatus(Hero h) {
        Icon icon = new Icon(h.getViewImage(), 15);
        this.add(icon);
        this.add(new XLabel(h.getType(), new Dimension(50, 15), Label.CENTER));
        this.add(new XLabel(h.getName(), new Dimension(100, 15), Label.CENTER));
        hp = getBar(h.getMaxHp(), h.getCurrentHp(), "HP");
        this.add(hp);
        actions = getBar(h.getMaxActions(), h.getActionsAvailable(), "AC " + h.getMaxActions());
        this.add(actions);
        this.add(new XLabel("DM " + h.getAttackDmg()));
        this.add(new Icon(Vaccine.IMAGE, 15));
        vaccine = new XLabel(String.valueOf(h.getVaccineInventory()));
        this.add(vaccine);
        this.add(new Icon(Supply.IMAGE, 15));
        supply = new XLabel(String.valueOf(h.getSupplyInventory()));
        this.add(supply);
        special = new XPanel(new Dimension(10, 10), Color.RED);
        this.add(special);
    }

    @Override
    public void setHp(int hp) {
        this.hp.setValue(hp);
        double d = (double) hp / this.hp.getMaximum();
        this.hp.setForeground(d < 0.4 ? Color.RED : (d < 0.7 ? Color.ORANGE : Color.GREEN));
    }

    public void setActions(int actions) {
        this.actions.setValue(actions);
        double d = (double) actions / this.actions.getMaximum();
        this.actions.setForeground(d < 0.4 ? Color.RED : (d < 0.7 ? Color.ORANGE : Color.GREEN));
        this.actions.setString("AC " + actions);
    }

    public void setVaccine(int vaccine) {
        this.vaccine.setText(String.valueOf(vaccine));
    }

    public void setSupply(int supply) {
        this.supply.setText(String.valueOf(supply));
    }

    public void setSpecial(boolean flag) {
        if (flag)
            special.setBackground(Color.GREEN);
        else
            special.setBackground(Color.RED);
    }

    private JProgressBar getBar(int max, int value, String str) {
        JProgressBar pb = new JProgressBar(0, max);
        pb.setValue(value);
        pb.setForeground(Color.GREEN);
        pb.setPreferredSize(new Dimension(120, 15));
        pb.setString(str);
        pb.setStringPainted(true);
        return pb;
    }
}
