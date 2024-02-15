package ai;

import engine.Game;
import objects.Hero;
import objects.Zombie;

public class Cure extends AIAction {
    protected Zombie target;

    public Cure(Hero hero, Zombie target) {
        super(hero);
        this.target = target;
    }

    @Override
    public void setResult() {
        directResult.add(ResultType.Hero, 1);
        if (Game.RemainVaccine == 0 && Game.heroes.size() >= 4) return;
        if (hero.getActionsAvailable() == 1)
            inDirectResult.add(ResultType.Hp, -getDamage(hero.getLocation()) + target.getAttackDmg());
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
            Game.cure();
        } catch (Exception ignored) {
        }
    }

    @Override
    public String toString() {
        return super.toString() + " Cure";
    }
}
