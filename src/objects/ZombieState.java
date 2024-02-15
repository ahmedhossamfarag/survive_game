package objects;

import engine.Game;
import engine.Network;
import engine.Update;
import views.game.Status;
import views.game.ZombieStatus;
import views.layout.Constrains;

import java.awt.*;

public class ZombieState extends ObjectState implements Cloneable {
    protected Zombie zombie;
    protected ZombieStatus status;
    private int hp;

    public ZombieState(Zombie zombie) {
        this.zombie = zombie;
        hp = zombie.getMaxHp();
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        _setHp(hp);
        Network.send(new Update('z', 'h', "%s,%s".formatted(zombie.getId(), hp)));
    }

    public void attack(Point p) {
        Network.send(new Update('z', 'a', "%s,%s,%s".formatted(zombie.getId(), p.x, p.y)));
        _attack(p);
    }

    public void _attack(Point p) {
        zombie.animator.riseEvent(zombie.LocationToCode(p));
    }

    public void _setHp(int hp) {
        this.hp = hp;
        status.setHp(hp);
        if (hp <= 0)
            zombie.destroy();
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(ZombieStatus status) {
        this.status = status;
        Game.selectedStatus.add(status, new Constrains(0, 0, 0, 0, true));
    }

    public ZombieState clone() {
        try {
            return (ZombieState) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
