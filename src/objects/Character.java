package objects;

import views.game.Status;

public abstract class Character extends GameObject {
    private final int maxHp;
    private final int attackDmg;


    public Character(int maxHp, int attackDmg) {
        this.maxHp = maxHp;
        this.attackDmg = attackDmg;
    }

    public static GameObject of(String s) {
        if (s.charAt(0) == 'z')
            return Zombie.of(s);
        return Hero.of(s);
    }

    public void attack(Character c) {
        c.decreaseHp(attackDmg);
        defend(c);
    }

    public void defend(Character c) {
        decreaseHp(c.attackDmg / 2);
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getAttackDmg() {
        return attackDmg;
    }

    public abstract int getCurrentHp();

    public abstract void setCurrentHp(int hp);

    public abstract Status getInfo();

    public void decreaseHp(int damage) {
        setCurrentHp(getCurrentHp() - damage);
    }

    abstract public void destroy();

}
