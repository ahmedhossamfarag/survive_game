package views.game;

import engine.Constant;
import engine.Game;
import engine.Update;
import engine.Value;
import objects.GameObject;
import objects.Zombie;
import views.animation.Player;
import views.scene.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.Serial;
import java.util.ArrayList;

public class Map extends JComponent implements Cloneable {
    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 7986637974849316009L;
    public static int count;
    public MapState state;
    private Cell[][] cells;

    public Map() {
        setBackground(Constant.BackColor);
        state = new MapState(this);
        cells = new Cell[15][15];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells.length; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
        addMouseListener(Game.actionListener);
        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                Window.gameScene.actionsList.locate();
            }

            @Override
            public void componentMoved(ComponentEvent e) {
            }

            @Override
            public void componentShown(ComponentEvent e) {
            }

            @Override
            public void componentHidden(ComponentEvent e) {
            }
        });
    }

    public static void execute(Update u) {
        if (u.UpdateAction == 'a') {
            GameObject object = GameObject.of(u.Content);
            Game.map.state._add(object);
            if (object instanceof Zombie z)
                Game.zombies.add(z);
            count++;
        } else if (u.UpdateAction == 'r') {
            GameObject object = getObject(Integer.parseInt(u.Content));
            Game.map.state._remove(object);
            if (object instanceof Zombie z)
                Game.zombies.remove(z);
        }
    }

    public static GameObject getObject(int id) {
        for (GameObject o : Game.map.state.getObjects())
            if (o.getId() == id)
                return o;
        return null;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void add(GameObject object) {
        state.add(object);
    }

    public void remove(GameObject object) {
        state.remove(object);
    }

    public Cell getCell(Point p) {
        return cells[p.x][p.y];
    }

    public void setVisible(boolean visible) {
        for (Cell[] a : cells) {
            for (Cell c : a) {
                c.setVisible(visible);
            }
        }
    }

    public Cell[] getRegion(Point location) {
        ArrayList<Cell> available = new ArrayList<>();
        for (int i = location.x - 1; i <= location.x + 1; i++) {
            if (i >= 0 && i < cells.length)
                for (int j = location.y - 1; j <= location.y + 1; j++) {
                    if (j >= 0 && j < cells[0].length)
                        available.add(cells[i][j]);
                }
        }
        return available.toArray(Cell[]::new);
    }

    public void showRegion(Point p) {
        for (Cell c : getRegion(p))
            c.setVisible_(true);
    }

    public void hide_() {
        for (Cell[] a : cells) {
            for (Cell c : a) {
                c.hide_();
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        int w = getWidth() / cells.length, h = getHeight() / cells.length;
        int x = 0, y = 0;
        g.setColor(Value.cellColor.getValue());
        ((Graphics2D) g).setStroke(new BasicStroke(2));
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells.length; j++) {
                Cell cell = cells[i][j];
                if (cell.isVisible()) {
                    g.fillRect(x, y, w, h);
                    if (cell.getObject() != null) {
                        Image image = cell.getObject().getImage();
                        if (image != null) {
                            g.drawImage(image, x, y, w, h, null);
                        }
                        if (cell == Game.selected) {
                            g.setColor(Value.selectColor.getValue());
                            g.drawRect(x, y, w - 1, h - 1);
                            g.setColor(Value.cellColor.getValue());
                        }
                    }
                }
                y += h;
            }
            y = 0;
            x += w;
        }
        ArrayList<Player> players = new ArrayList<>(state.getPlayers());
        for (Player player : players) {
            int xp = (int) (player.getX() * w), yp = (int) (player.getY() * h);
            g.drawImage(player.getImage(), xp, yp, w, h, null);
        }
    }

    @Override
    public Map clone() {
        try {
            Map clone = (Map) super.clone();
            clone.state = state.clone();
            clone.state.map = clone;
            clone.cells = new Cell[Constant.MapSize][Constant.MapSize];
            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells.length; j++) {
                    clone.cells[i][j] = cells[i][j].clone();
                }
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
