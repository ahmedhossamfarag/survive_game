package objects;

import engine.Action;
import engine.Game;
import engine.Update;
import exceptions.InvalidTargetException;
import views.animation.Animation;
import views.animation.ImagesDir;
import views.animation.Player;
import views.game.HeroStatus;
import views.game.Map;
import views.game.Status;
import views.layout.Constrains;

import java.awt.*;

public abstract class Hero extends Character {

    private Animation trapAnimation;
    private HeroState state;
    private Player player;



    public Hero(String name, int maxHp, int attackDmg, int maxActions) {
        super(maxHp, attackDmg);
        state = new HeroState(this, name, maxActions);
    }

    public static void execute(Update u) {
        String[] arr = u.Content.split(",");
        int id = Integer.parseInt(arr[0]);
        Hero h = (Hero) Map.getObject(id);
        if (h != null)
            switch (u.UpdateAction) {
                case 'h' -> h.state._setHp(Integer.parseInt(arr[1]));
                case 'a' -> h.state._setActionsAvailable(Integer.parseInt(arr[1]));
                case 'v' -> h.state._setVaccineInventory(Integer.parseInt(arr[1]));
                case 's' -> h.state._setSupplyInventory(Integer.parseInt(arr[1]));
                case 'p' -> h.state._setSpecialAction(Boolean.parseBoolean(arr[1]));
                case 'l' ->
                        h.state._setLocation(new Point(Integer.parseInt(arr[1]), Integer.parseInt(arr[2])), null, null);
                case 'k' -> h.state._attack(new Point(Integer.parseInt(arr[1]), Integer.parseInt(arr[2])), null);
                case 't' -> h.state._setInTrap(Boolean.parseBoolean(arr[1]));
        }
    }

    public static GameObject of(String s) {
        String[] arr = s.split(",");
        int id = Integer.parseInt(arr[1]);
        int x = Integer.parseInt(arr[2]), y = Integer.parseInt(arr[3]);
        String n = arr[4] + " $";
        int mh = Integer.parseInt(arr[5]), ad = Integer.parseInt(arr[6]), ma = Integer.parseInt(arr[7]);
        Hero h;
        switch (arr[0].charAt(0)) {
            case 'f' -> h = new Fighter(n, mh, ad, ma);
            case 'e' -> h = new Explorer(n, mh, ad, ma);
            default -> h = new Medic(n, mh, ad, ma);
        }
        h = (Hero) h.clone_();
        h.setLocation(new Point(x, y));
        h.setId(id);
        return h;
    }

    public abstract Image getViewImage();

    public void move() {
        decrementActions();
    }

    public void pickUp(Collectable c) {
        if (c instanceof Vaccine)
            setVaccine(getVaccineInventory() + 1);
        else {
            setSupply(getSupplyInventory() + 1);
            if (this instanceof Medic || !state.isSpecialAction())
                setSpecial(false);
        }
    }

    public void useSpecial() throws InvalidTargetException {
        removeSupply();
        if (this instanceof Medic)
            setSpecial(getSupplyInventory() == 0);
        else
            setSpecial(true);
    }

    private void removeSupply() {
        setSupply(getSupplyInventory() - 1);
    }

    private void setSupply(int i) {
        state.setSupplyInventory(i);
    }

    public void setSpecial(boolean b) {
        state.setSpecialAction(b);
    }

    public void cure() {
        decrementActions();
        removeVaccine();
    }

    private void removeVaccine() {
        setVaccine(getVaccineInventory() - 1);
    }

    private void setVaccine(int i) {
        state.setVaccineInventory(i);
    }

    public void attack(Zombie z) {
        if (!(this instanceof Fighter && state.isSpecialAction())) decrementActions();
        state.attack(z.getLocation(), () -> super.attack(z));
    }

    @Override
    public void decreaseHp(int damage) {
        super.decreaseHp(damage);
    }

    public int getVaccineInventory() {
        return state.getVaccineInventory();
    }

    public int getSupplyInventory() {
        return state.getSupplyInventory();
    }

    public void decrementActions() {
        setActions(state.getActionsAvailable() - 1);
    }

    private void setActions(int i) {
        state.setActionsAvailable(i);
    }

    public void resetHp() {
        state.setHp(getMaxHp());
    }

    public void reset() {
        setActions(state.getMaxActions());
        state.setSpecialAction(false);
    }


    public String getName() {
        return state.getName();
    }

    public int getActionsAvailable() {
        return state.getActionsAvailable();
    }

    @Override
    public Status getInfo() {
        return state.getStatus();
    }

    public String getType() {
        if (this instanceof Fighter) {
            return "Fighter";
        }
        if (this instanceof Medic) {
            return "Medic";
        }
        return "Explorer";
    }

    @Override
    public Image getImage() {
        if (isInTrap())
            return trapAnimation.getImage();
        return null;
    }

    public boolean isInTrap() {
        return state.isInTrap();
    }

    public void setInTrap(boolean inTrap) {
        state.setInTrap(inTrap);
    }

    public boolean isSpecialAction() {
        return state.isSpecialAction();
    }

    @Override
    public int getCurrentHp() {
        return state.getHp();
    }

    @Override
    public void setCurrentHp(int hp) {
        state.setHp(hp);
    }

    @Override
    public void destroy() {
        Game.destroy(this);
    }

    public int getMaxActions() {
        return state.getMaxActions();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setLocation(Point location, Action prev, Action action) {
        state.setLocation(location, prev, action);
    }

    @Override
    public void setLocation(Point location) {
        if (getLocation() == null) {
            player.setLocation(location);
            Game.selectedStatus.add(state.getStatus(), new Constrains(0, 0, 0, 0, true));
        }
        super.setLocation(location);
    }

    @Override
    public String toString() {
        return super.toString() + ',' + state.toString();
    }

    @Override
    public GameObject clone_() {
        try {
            Hero h = (Hero) super.clone();
            h.state = state.clone();
            h.state.hero = h;
            h.state.status = new HeroStatus(h);
            h.trapAnimation = new Animation(ImagesDir.Trap);
            return h;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Image getTrapImage() {
        return trapAnimation.getImage();
    }
}
