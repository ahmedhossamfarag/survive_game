package ai;

import engine.Constant;
import engine.Game;
import engine.Valid;
import exceptions.GameActionException;
import objects.*;
import views.game.Cell;

import java.awt.*;

public class Move extends AIAction {

    protected Point p;

    public Move(Hero hero, Point p) {
        super(hero);
        this.p = p;
    }


    @Override
    public void setResult() {
        directResult.add(ResultType.Cell, getUnknownCells(p));
        if (Game.map.getCell(p).isVaccine())
            directResult.add(ResultType.Vaccine, 1);
        else if (Game.map.getCell(p).isSupply())
            directResult.add(ResultType.Supply, 1);
        if (hero.getActionsAvailable() == 1)
            inDirectResult.add(ResultType.Hp, getDamage(hero.getLocation()) - getDamage(p));
        if (!AI.vaccines.isEmpty())
        {
            Vaccine vaccine = AI.getNearestVaccine(p);
            if (AI.getNearestHero(vaccine.getLocation()) == hero)
                inDirectResult.add(ResultType.Vaccine, 1, AI.distance(AI.getNearestVaccine(p).getLocation(), p));
        }
        if (!AI.supplies.isEmpty())
        {
            Supply supply = AI.getNearestSupply(p);
            if (AI.getNearestHero(supply.getLocation()) == hero)
                inDirectResult.add(ResultType.Supply, 1, AI.distance(AI.getNearestSupply(p).getLocation(), p));
        }
        if (hero.getVaccineInventory() > 0 && !AI.zombies.isEmpty())
            inDirectResult.add(ResultType.Hero, 1, AI.distance(AI.getNearestZombie(p).getLocation(), p));
        if (hero.getSupplyInventory() > 0)
            if (hero instanceof Medic && Game.heroes.size() > 1){
                Hero h = AI.getNearestHero(hero);
                inDirectResult.add(ResultType.Hp, h.getMaxHp() - h.getCurrentHp(), AI.distance(h.getLocation(), p));
            }
        inDirectResult.add(ResultType.Cell, getUnknownCells(hero.getLocation(), p));
    }

    private int getUnknownCells(Point location, Point p) {
        if (AI.vaccines.size() == Game.RemainVaccine)
            return 0;
        int dx = p.x - location.x, dy = p.y - location.y;
        int x = location.x, y = location.y + dy;
        int n = 0;
        int h = 1;
        if (dx == 0 && dy == -1){
            for (int i = 0; i < Constant.MapSize; i++) {
                for (int j = 0; j < y; j++) {
                    if (Game.map.getCell(new Point(i, j)).getVN() == 0)
                        n++;
                    else if (Game.map.getCell(new Point(i, j)).isHero())
                        h++;
                }
            }
            return n / h;
        }

        if (dx == 0 && dy == 1){
            for (int i = 0; i < Constant.MapSize; i++) {
                for (int j = Constant.MapSize - 1; j > y; j--) {
                    if (Game.map.getCell(new Point(i, j)).getVN() == 0)
                        n++;
                    else if (Game.map.getCell(new Point(i, j)).isHero())
                        h++;
                }
            }
            return n / h;
        }

        if (dx == 1){
            for (int i = 0; i < Constant.MapSize; i++) {
                for (int j = Constant.MapSize - 1; j > x; j--) {
                    if (Game.map.getCell(new Point(j, i)).getVN() == 0)
                        n++;
                    else if (Game.map.getCell(new Point(i, j)).isHero())
                        h++;
                }
            }
            return n / h;
        }

        for (int i = 0; i < Constant.MapSize; i++) {
            for (int j = 0; j < x; j++) {
                if (Game.map.getCell(new Point(j, i)).getVN() == 0)
                    n++;
                else if (Game.map.getCell(new Point(i, j)).isHero())
                    h++;
            }
        }
        return n / h;

    }

    public int getUnknownCells(Point p){
        int i = 0;
        for (Cell c : Game.map.getRegion(p))
            if (c.getVN() == 0)
                i++;
        return i;
    }

    @Override
    public void apply() {
        super.apply();
        try {
            Game.move(p);
        } catch (GameActionException ignored) {
        }
    }

    @Override
    public boolean isActionValid() {
        Hero tmp = Game.hero;
        Game.hero = hero;
        try {
            Valid.checkMoveValidity(p);
            Game.hero = tmp;
            return true;
        } catch (GameActionException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return super.toString() + " Move " + p;
    }
}
