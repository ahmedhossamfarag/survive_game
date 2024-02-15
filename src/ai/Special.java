package ai;

import engine.Constant;
import engine.Game;
import exceptions.GameActionException;
import objects.Explorer;
import objects.Hero;
import objects.Medic;

public class Special extends AIAction {
    protected Hero target;


    public Special(Hero hero, Hero target) {
        super(hero);
        this.target = target;
    }

    public Special(Hero hero) {
        super(hero);
    }

    @Override
    public void setResult() {
        if (hero instanceof Explorer)
            directResult.add(ResultType.Cell, Constant.MapSize * Constant.MapSize);
        else if (hero instanceof Medic)
            directResult.add(ResultType.Hp, target.getMaxHp() - target.getCurrentHp());
        else
            directResult.add(ResultType.Hp, -getDamage(hero.getLocation()) / 2);
    }


    @Override
    public boolean isActionValid() {
        return true;
    }

    @Override
    public void apply() {
        super.apply();
        if (target != null) {
            Game.actionListener.select(Game.map.getCell(target.getLocation()));
        }

        try {
            Game.useSpecial();
        } catch (GameActionException ignored) {
        }
    }

    @Override
    public String toString() {
        return super.toString() + " Special";
    }
}
