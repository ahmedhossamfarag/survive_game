package engine;

import exceptions.*;
import objects.Fighter;
import objects.Hero;

import java.awt.*;

public class Valid {

    public static boolean isLocationInValid(Point p) {
        return p.x < 0 || p.x >= 15 || p.y < 0 || p.y >= 15;
    }

    public static boolean isActionNotValid() {
        return Game.hero.getActionsAvailable() == 0;
    }

    public static boolean isTargetNotValid() {
        if (Game.selected == null) return true;
        Point location = Game.hero.getLocation();
        Point p = Game.selected.getCellLocation();
        int dx = p.x - location.x, dy = p.y - location.y;
        return (dx != 1 && dx != -1 && dx != 0) ||
                (dy != 1 && dy != -1 && dy != 0);
    }

    public static void checkMoveValidity(Point p) throws GameActionException {
        if (isLocationInValid(p) ||
                Game.map.getCell(p).isNotAvailable())
            throw new MovementException();
        if (isActionNotValid() && !Game.map.getCell(p).isZombie())
            throw new NotEnoughActionsException();
    }

    public static void checkCureValid() throws GameActionException {
        if (Game.hero.getVaccineInventory() == 0) throw new NoAvailableResourcesException();
        if (isActionNotValid()) throw new NotEnoughActionsException();
        if (isTargetNotValid() ||
                !Game.selected.isZombie())
            throw new InvalidTargetException();
    }

    public static void checkAttackValid() throws GameActionException {
        if (isTargetNotValid() ||
                !Game.selected.isZombie())
            throw new InvalidTargetException();
        if (Game.hero instanceof Fighter && Game.hero.isSpecialAction())
            return;
        if (isActionNotValid())
            throw new NotEnoughActionsException();
    }

    public static void checkSpecialValid() throws GameActionException {
        if (Game.hero.getSupplyInventory() == 0)
            throw new NoAvailableResourcesException();
    }

    public static void checkChooseHeroValid() throws InvalidTargetException {
        if (Game.selected == null || !Game.selected.isHero() || !Game.heroes.contains(((Hero) Game.selected.getObject())) || Game.selected.getObject() == Game.hero)
            throw new InvalidTargetException();
    }

    public static void checkMedicSpecialValid() throws InvalidTargetException {
        if (isTargetNotValid() || !Game.selected.isHero()) throw new InvalidTargetException();
    }
}
