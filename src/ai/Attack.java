package ai;

import engine.Game;
import objects.Hero;
import objects.Zombie;

public class Attack extends AIAction {

    protected Zombie target;

    public Attack(Hero hero, Zombie target) {
        super(hero);
        this.target = target;
    }


    @Override
    public void setResult() {
        directResult.add(ResultType.Hp, - target.getAttackDmg() / 2);
        if (hero.getActionsAvailable() == 1) {
            if (target.getCurrentHp() <= hero.getAttackDmg())
                inDirectResult.add(ResultType.Hp, -getDamage(hero.getLocation()) + target.getAttackDmg());
            else
                inDirectResult.add(ResultType.Hp, -getDamage(hero.getLocation()));
        }
    }

    @Override
    public boolean isActionValid() {
        return true;
    }

    @Override
    public void apply() {
        super.apply();
        Game.actionListener.select(Game.map.getCell(target.getLocation()));
        try {
            Game.attack();
        } catch (Exception ignored) {
        }
    }

    @Override
    public String toString() {
        return super.toString() + " Attack";
    }
}
