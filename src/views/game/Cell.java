package views.game;

import engine.Game;
import engine.Update;
import objects.Character;
import objects.*;

import java.awt.*;

public class Cell implements Cloneable {
    private final int x, y;
    private CellState state;
    private int VN;

    private boolean visible;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        state = new CellState(this);
        setVisible(false);
    }

    public static void execute(Update u) {
        String[] arr = u.Content.split(",");
        int x = Integer.parseInt(arr[0]), y = Integer.parseInt(arr[1]);
        Cell c = Game.map.getCell(new Point(x, y));
        switch (u.UpdateAction) {
            case 'v' -> c.state._setVisible(Boolean.parseBoolean(arr[2]));
            case 'o' -> {
                if (arr[2].equals("null"))
                    c.state._setObject(null);
                else
                    c.state._setObject(Map.getObject(Integer.parseInt(arr[2])));
            }
            case 'r' -> c.state._setReserved();
        }
    }

    public void hide_() {
        state.hide();
    }

    public int getX_() {
        return x;
    }

    public int getY_() {
        return y;
    }

    public CellState getState() {
        return state;
    }

    public GameObject getObject() {
        return state.getObject();
    }

    public void setObject(GameObject object) {
        state.setObject(object);
    }

    public boolean isReserved() {
        return state.isReserved();
    }

    public void setReserved() {
        state.setReserved();
    }

    public void setVisible_(boolean b) {
        state.setVisible(b);
    }

    public boolean isOccupied() {
        return getObject() != null;
    }

    public boolean isCharacter() {
        return isOccupied() && getObject() instanceof Character;
    }

    public boolean isHero() {
        return isOccupied() && getObject() instanceof Hero;
    }

    public boolean isZombie() {
        return isOccupied() && getObject() instanceof Zombie;
    }

    public boolean isTrap() {
        return isOccupied() && getObject() instanceof Trap;
    }

    public boolean isCollectable() {
        return isOccupied() && getObject() instanceof Collectable;
    }

    public boolean isVaccine() {
        return isOccupied() && getObject() instanceof Vaccine;
    }

    public boolean isSupply() {
        return isOccupied() && getObject() instanceof Supply;
    }

    public boolean isNotAvailable() {
        return isCharacter() || isReserved();
    }

    public Point getCellLocation() {
        return new Point(x, y);
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean aFlag) {
        visible = aFlag;
        if (aFlag)
            VN++;
    }

    public int getVN() {
        return VN;
    }

    public Cell clone() {
        try {
            Cell c = (Cell) super.clone();
            c.state = state.clone();
            c.state.cell = c;
            return c;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
