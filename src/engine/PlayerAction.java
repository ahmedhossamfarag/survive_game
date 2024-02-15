package engine;

import ai.AI;
import exceptions.GameActionException;
import exceptions.MovementException;
import objects.Hero;
import views.game.Cell;
import views.scene.Window;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PlayerAction implements KeyListener, MouseListener {
    public static boolean listen = true;

    public static Point getPoint(Hero hero, int i) {
        int newX = hero.getLocation().x, newY = hero.getLocation().y;
        switch (i) {
            case KeyEvent.VK_UP: {
                newY--;
                break;
            }
            case KeyEvent.VK_DOWN: {
                newY++;
                break;
            }
            case KeyEvent.VK_RIGHT: {
                newX++;
                break;
            }
            case KeyEvent.VK_LEFT: {
                newX--;
                break;
            }
            default:
                return null;
        }
        return new Point(newX, newY);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (!listen) return;
        try {
            switch (e.getKeyChar()) {
                case 's' -> Game.useSpecial();
                case 'e' -> Game.endTurn();
                case 'a' -> Game.attack();
                case 'q' -> Game.cure();
                case 'x' -> Game.chooseHero();
                case 'h' -> AI.stepForward();
            }
        } catch (GameActionException ex) {
            Window.playAudio(Constant.ErrorSound);
            Main.window.showMassage(ex.getMessage());
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!listen) return;
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) Main.window.gameOver();
        if (e.getKeyCode() == KeyEvent.VK_F1)
            Main.window.showHelp();

        Point p = getPoint(Game.hero, e.getKeyCode());
        if (p != null) {
            try {
                Game.move(p);
            }catch (MovementException ex){
                Window.playAudio(Constant.MoveExSound);
                Main.window.showMassage(ex.getMessage());
            } catch (GameActionException ex) {
                Window.playAudio(Constant.ErrorSound);
                Main.window.showMassage(ex.getMessage());
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!listen) return;
        int x = e.getX() / (e.getComponent().getWidth() / Constant.MapSize),
                y = e.getY() / (e.getComponent().getHeight() / Constant.MapSize);
        Cell c = Game.map.getCell(new Point(x, y));
        //if (!c.isVisible()) return;
        select(c);
        if (e.getButton() == MouseEvent.BUTTON3) {
            Window.gameScene.actionsList.set();
        }
    }

    public void select(Cell c) {
        Game.endSelect();
        Game.selected = c;
        if (Game.selected.getObject() != Game.hero) {
            Game.selectedStatus.setItemOf(Game.selected);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
