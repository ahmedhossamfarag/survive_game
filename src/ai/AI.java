package ai;

import engine.Game;
import engine.PlayerAction;
import engine.Valid;
import objects.*;
import views.game.Cell;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AI {
    public static ArrayList<Vaccine> vaccines;
    public static ArrayList<Zombie> zombies;

    public static ArrayList<Supply> supplies;
    static int[] dir = {KeyEvent.VK_DOWN, KeyEvent.VK_UP, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT};

    public static void initialize() {
        zombies = new ArrayList<>();
        vaccines = new ArrayList<>();
        supplies = new ArrayList<>();
        for (Cell[] cells : Game.map.getCells())
            for (Cell c : cells) {
                if (c.getVN() > 0) {
                    if (c.isZombie()) zombies.add((Zombie) c.getObject());
                    else if (c.isCollectable()) {
                        if (c.getObject() instanceof Vaccine) vaccines.add((Vaccine) c.getObject());
                        else supplies.add((Supply) c.getObject());
                    }
                }
            }
    }

    public static ArrayList<AIAction> getAvailableActions() {
        ArrayList<AIAction> arr = new ArrayList<>();
        for (Hero h : Game.heroes) {
            arr.addAll(getActionsFor(h, Game.map.getRegion(h.getLocation())));
        }
        return arr;
    }

    public static ArrayList<AIAction> getActionsFor(Hero h, Cell[] cells) {
        ArrayList<AIAction> arr = new ArrayList<>();
        if (h.getActionsAvailable() > 0) {
            for (int d : dir) {
                Point p = PlayerAction.getPoint(h, d);
                assert p != null;
                if (Valid.isLocationInValid(p) || Game.map.getCell(p).isCharacter()) continue;
                arr.add(new Move(h, p));
            }
        }
        for (Cell c : cells) {
            if (c.isZombie()) {
                if (h.getActionsAvailable() > 0 || (h instanceof Fighter && h.isSpecialAction()))
                    arr.add(new Attack(h, (Zombie) c.getObject()));
                if (h.getActionsAvailable() > 0 && h.getVaccineInventory() > 0)
                    arr.add(new Cure(h, (Zombie) c.getObject()));
            } else if (c.isHero() && h instanceof Medic && h.getSupplyInventory() > 0) {
                Hero he = (Hero) c.getObject();
                if (he.getCurrentHp() < he.getMaxHp()) arr.add(new Special(h, he));
            }
        }
        if (!(h instanceof Medic) && h.getSupplyInventory() > 0 && !h.isSpecialAction())
            arr.add(new Special(h));
        return arr;
    }

    public static void stepForward() {
        initialize();
        AIAction action = getBestAction();
        action.apply();
    }

    public static AIAction getBestAction(){
        ArrayList<AIAction> actions = getAvailableActions();
        for (AIAction action  : actions)
            action.setResult();
        //Check For Actions
        if (actions.size() == 0)
            return new End();

        //Filter Negative HP
        List<AIAction> actionStream = actions.stream().filter(a ->
                        a.directResult.getValue(ResultType.Hp) + a.inDirectResult.getValue(ResultType.Hp) >= 0).toList();

        actionStream = actionStream.stream().filter(a -> !a.directResult.isEmpty() || !a.inDirectResult.isEmpty()).toList();

        if (actionStream.isEmpty())
        {
            //Filter For HP <= END Turn HP
            actionStream = actions.stream().filter(a ->
                    a.directResult.getValue(ResultType.Hp) +
                            a.inDirectResult.getValue(ResultType.Hp)
                            > a.getDamage(a.hero.getLocation())).toList();
            if (actionStream.isEmpty())
                return new End();
            return actionStream.stream().max(Comparator.comparingInt(a -> a.directResult.getValue(ResultType.Hp) + a.inDirectResult.getValue(ResultType.Hp))).get();
        }
        Algorithm algorithm = new Algorithm(actionStream);

        //Filter For HP

        algorithm.addWithMax(a -> true, a -> a.directResult.getValue(ResultType.Hp) + a.inDirectResult.getValue(ResultType.Hp));

        //Filter For Direct Hero

        algorithm.add(a -> a.directResult.satisfy(ResultType.Hero));

        //Filter For Direct Vaccine

        algorithm.add(a -> a.directResult.satisfy(ResultType.Vaccine));

        //Filter For Direct Supply

        algorithm.add(a -> a.directResult.satisfy(ResultType.Supply));

        //Filter For Indirect Hero | Vaccine | Supply

        algorithm.addWithMin(a -> a.inDirectResult.satisfy(ResultType.Hero)
                        || a.inDirectResult.satisfy(ResultType.Vaccine) ||
                        a.inDirectResult.satisfy(ResultType.Supply),
                a ->  Math.min(a.inDirectResult.getDepth(ResultType.Hero),
                        Math.min(a.inDirectResult.getDepth(ResultType.Vaccine), a.inDirectResult.getDepth(ResultType.Supply))));

        //Filter For Cells

        algorithm.addWithMax(a -> a.directResult.satisfy(ResultType.Cell) || a.inDirectResult.satisfy(ResultType.Cell),
                a -> a.directResult.getValue(ResultType.Cell)
                        + a.inDirectResult.getValue(ResultType.Cell));
        return algorithm.operate();
    }


    public static int distance(Point p1, Point p2){
        return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
    }

    public static Vaccine getNearestVaccine(Point p){
        return vaccines.stream().min(Comparator.comparingInt(o -> distance(p, o.getLocation()))).get();
    }

    public static Supply getNearestSupply(Point p){
        return supplies.stream().min(Comparator.comparingInt(o -> distance(p, o.getLocation()))).get();
    }

    public static Zombie getNearestZombie(Point p){
        return zombies.stream().min(Comparator.comparingInt(o -> distance(p, o.getLocation()))).get();
    }

    public static Hero getNearestHero(Hero h){
        ArrayList<Hero> heroes = new ArrayList<>(Game.heroes);
        heroes.remove(h);
        return heroes.stream().min(Comparator.comparingInt(o -> distance(h.getLocation(), o.getLocation()))).get();
    }
    public static Hero getNearestHero(Point point){
        return Game.heroes.stream().min(Comparator.comparingInt(o -> distance(point, o.getLocation()))).get();
    }

}
