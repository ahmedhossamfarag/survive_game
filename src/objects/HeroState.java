package objects;

import engine.Action;
import engine.Network;
import engine.Update;
import views.game.HeroStatus;

import java.awt.*;

public class HeroState extends ObjectState implements Cloneable {
    private final String name;
    private final int maxActions;
    protected Hero hero;
    protected HeroStatus status;
    private int hp;
    private int actionsAvailable;
    private int vaccineInventory;
    private int supplyInventory;
    private boolean specialAction;

    private boolean inTrap;

    public HeroState(Hero hero, String name, int maxActions) {
        this.hero = hero;
        this.name = name;
        this.maxActions = maxActions;
        this.hp = hero.getMaxHp();
        this.actionsAvailable = maxActions;
        this.specialAction = false;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public boolean isInTrap() {
        return inTrap;
    }

    public void setHp(int hp) {
        _setHp(hp);
        Network.send(new Update('h', 'h', "%s,%s".formatted(hero.getId(), hp)));
    }

    public void setInTrap(boolean inTrap) {
        Network.send(new Update('h', 't', "%s,%s".formatted(hero.getId(), inTrap)));
        _setInTrap(inTrap);
    }
    public int getActionsAvailable() {
        return actionsAvailable;
    }

    public void setActionsAvailable(int actionsAvailable) {
        _setActionsAvailable(actionsAvailable);
        Network.send(new Update('h', 'a', "%s,%s".formatted(hero.getId(), actionsAvailable)));
    }

    public int getMaxActions() {
        return maxActions;
    }

    public int getVaccineInventory() {
        return vaccineInventory;
    }

    public void setVaccineInventory(int vaccineInventory) {
        _setVaccineInventory(vaccineInventory);
        Network.send(new Update('h', 'v', "%s,%s".formatted(hero.getId(), vaccineInventory)));
    }

    public int getSupplyInventory() {
        return supplyInventory;
    }

    public void setSupplyInventory(int supplyInventory) {
        _setSupplyInventory(supplyInventory);
        Network.send(new Update('h', 's', "%s,%s".formatted(hero.getId(), supplyInventory)));
    }

    public boolean isSpecialAction() {
        return specialAction;
    }

    public void setSpecialAction(boolean specialAction) {
        _setSpecialAction(specialAction);
        Network.send(new Update('h', 'p', "%s,%s".formatted(hero.getId(), specialAction)));
    }
    public void _setHp(int hp) {
        this.hp = hp;
        status.setHp(hp);
        if (hp <= 0)
            hero.destroy();
    }

    public void _setActionsAvailable(int actionsAvailable) {
        this.actionsAvailable = actionsAvailable;
        status.setActions(actionsAvailable);
    }

    public void _setVaccineInventory(int vaccineInventory) {
        this.vaccineInventory = vaccineInventory;
        status.setVaccine(vaccineInventory);
    }

    public void _setSupplyInventory(int supplyInventory) {
        this.supplyInventory = supplyInventory;
        status.setSupply(supplyInventory);
        if (!specialAction || hero instanceof Medic)
            setSpecialAction(false);
    }

    public void _setSpecialAction(boolean specialAction) {
        this.specialAction = specialAction;
        status.setSpecial(!specialAction && supplyInventory > 0);
    }

    public void _setLocation(Point p, Action prev, Action action) {
        hero.getPlayer().getAnimator().riseEvent(hero.LocationToCode(p), ()->{
            if (prev != null)
                prev.apply();
            hero.setLocation(p);
        }, action);

    }

    public void _setInTrap(boolean inTrap) {
        this.inTrap = inTrap;
    }

    public void _attack(Point location, Action a) {
        hero.getPlayer().getAnimator().riseEvent(-hero.LocationToCode(location), a);
    }

    public void setLocation(Point p, Action prev, Action action) {
        Network.send(new Update('h', 'l', "%s,%s,%s".formatted(hero.getId(), p.x, p.y)));
        _setLocation(p, prev, action);
    }

    public void attack(Point p, Action a) {
        Network.send(new Update('h', 'k', "%s,%s,%s".formatted(hero.getId(), p.x, p.y)));
        _attack(p, a);
    }

    public HeroStatus getStatus() {
        return status;
    }

    public void setStatus(HeroStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "%s,%s,%s,%s".formatted(name, hero.getMaxHp(), hero.getAttackDmg(), maxActions);
    }

    @Override
    public HeroState clone() {
        try {
            return (HeroState) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
