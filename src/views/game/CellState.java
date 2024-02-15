package views.game;

import engine.Network;
import engine.Update;
import objects.GameObject;
import objects.Trap;

public class CellState implements Cloneable {
    protected Cell cell;
    private GameObject object;
    private boolean visible;
    private int count;
    private boolean reserved;

    public CellState(Cell cell) {
        this.cell = cell;
    }

    public void setVisible(boolean visible) {
        if (visible == this.visible) return;
        this.visible = visible;
        _setVisible(visible);
        Network.send(new Update('c', 'v', "%s,%s,%s".formatted(cell.getX_(), cell.getY_(), visible)));
    }

    public void _setVisible(boolean visible) {
        if (visible) count++;
        else count--;
        cell.setVisible(count != 0);
    }

    public GameObject getObject() {
        return object;
    }

    public void setObject(GameObject object) {
        _setObject(object);
        Network.send(new Update('c', 'o', "%s,%s,%s".formatted(cell.getX_(), cell.getY_(), object == null ? "null" : object.getId())));
    }

    public void _setObject(GameObject object) {
        this.object = object;
        if (object == null || object instanceof Trap)
            reserved = false;
    }

    public void hide() {
        if (visible) setVisible(false);
        else if (count == 0) cell.setVisible(false);
    }

    public boolean isReserved() {
        return reserved;
    }

    public void _setReserved() {
        this.reserved = true;
    }

    public void setReserved() {
        _setReserved();
        Network.send(new Update('c', 'r', "%s,%s".formatted(cell.getX_(), cell.getY_())));
    }

    public CellState clone() {
        try {
            CellState state = (CellState) super.clone();
            state.object = object.clone_();
            return state;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
