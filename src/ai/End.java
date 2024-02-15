package ai;

import engine.Game;

public class End extends AIAction {
    public End() {
        super(null);
    }

    @Override
    public void setResult() {
    }

    @Override
    public boolean isActionValid() {
        return true;
    }

    @Override
    public void apply() {
        Game.endTurn();
    }

    @Override
    public String toString() {
        return "END";
    }
}
