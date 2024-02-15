package ai;

import engine.Game;
import exceptions.InvalidTargetException;
import objects.Hero;
import objects.Zombie;
import views.game.Cell;

import java.awt.*;

public abstract class AIAction {

    protected Hero hero;

    protected Result directResult;
    protected Result inDirectResult;

    public AIAction(Hero hero) {
        this.hero = hero;
        directResult = new Result();
        inDirectResult  = new Result();
    }

    public abstract void setResult();

    public int getDamage(Point p){
        int d = 0;
        for (Cell c : Game.map.getRegion(p)){
            if (c.getVN() > 0 && c.isZombie())
                d += ((Zombie) c.getObject()).getAttackDmg();
        }
        return d;
    }

    public void apply() {
        if (hero != Game.hero) {
            Game.actionListener.select(Game.map.getCell(hero.getLocation()));
            try {
                Game.chooseHero();
            } catch (InvalidTargetException ignored) {
            }
        }
    }

    public abstract boolean isActionValid();

    @Override
    public String toString() {
        return hero != null ? hero.getName() : "";
    }
}
