package views.game;

import engine.Network;
import engine.Update;
import objects.GameObject;
import objects.Hero;
import views.animation.Player;
import java.awt.*;
import java.util.ArrayList;

public class MapState implements Cloneable {
    protected Map map;
    private ArrayList<GameObject> objects;
    private ArrayList<Player> players;

    public MapState(Map map) {
        this.map = map;
        this.objects = new ArrayList<>();
        this.players = new ArrayList<>();
    }

    public ArrayList<GameObject> getObjects() {
        return objects;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void add(GameObject object) {
        object.setId(++Map.count);
        Network.send(new Update('m', 'a', object.toString()));
        _add(object);
    }

    public void remove(GameObject object) {
        Network.send(new Update('m', 'r', String.valueOf(object.getId())));
        _remove(object);
    }

    public void _add(GameObject object) {
        objects.add(object);
        if (object instanceof Hero h)
            players.add(h.getPlayer());
        Point p = object.getLocation();
        map.getCells()[p.x][p.y].getState()._setObject(object);
    }

    public void _remove(GameObject object) {
        objects.remove(object);
        if (object instanceof Hero h)
            players.remove(h.getPlayer());
        Point p = object.getLocation();
        map.getCells()[p.x][p.y].getState()._setObject(null);

    }

    @Override
    public MapState clone() {
        try {
            MapState clone = (MapState) super.clone();
            clone.objects = new ArrayList<>();
            clone.players = new ArrayList<>();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
