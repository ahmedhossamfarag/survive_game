package engine;

import exceptions.GameActionException;
import exceptions.InvalidTargetException;
import objects.Character;
import objects.*;
import views.game.Cell;
import views.game.Map;
import views.game.StatusBar;
import views.scene.Window;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Game {

    public static final PlayerAction actionListener = new PlayerAction();
    private static final Random random = new Random();
    public static boolean gameId;
    public static boolean create = true;
    public static Map map;
    public static ArrayList<Hero> existingHeroes;
    public static ArrayList<Hero> availableHeroes;
    public static ArrayList<Hero> heroes;
    public static ArrayList<Zombie> zombies;
    public static Hero hero;
    public static Cell selected;

    public static StatusBar heroStatus;
    public static StatusBar selectedStatus;

    public static int CuredHeroesN;

    public static int RemainSupply = Constant.CollectableN;
    public static int RemainVaccine = Constant.CollectableN;


    public static void loadHeroes(String filePath) throws IOException {
        existingHeroes = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line = br.readLine();
        while (line != null) {
            String[] content = line.split(",");
            Hero hero = switch (content[1]) {
                case "FIGH" ->
                        new Fighter(content[0], Integer.parseInt(content[2]), Integer.parseInt(content[4]), Integer.parseInt(content[3]));
                case "MED" ->
                        new Medic(content[0], Integer.parseInt(content[2]), Integer.parseInt(content[4]), Integer.parseInt(content[3]));
                case "EXP" ->
                        new Explorer(content[0], Integer.parseInt(content[2]), Integer.parseInt(content[4]), Integer.parseInt(content[3]));
                default -> null;
            };
            existingHeroes.add(hero);
            line = br.readLine();

        }
        br.close();
    }


    public static void launchGame() {
        try {
            loadHeroes(Constant.HeroesFile);
        } catch (IOException ignored) {
        }
    }


    public static void initialize() {
        // Initialize Variables
        hero = null;
        heroes = new ArrayList<>();
        availableHeroes = new ArrayList<>();
        zombies = new ArrayList<>();
        heroStatus = new StatusBar();
        selectedStatus = new StatusBar();
        CuredHeroesN = 0;
        map = new Map();
    }

    public static void _startGame(Hero h, int x, int y) {
        //Set objects.Hero
        for (Hero her : existingHeroes)
            if (her != h)
                availableHeroes.add((Hero) her.clone_());
        h = (Hero) h.clone_();
        heroes.add(h);
        h.setLocation(new Point(x, y));
        changeHero(h);
        h.getInfo().setVisible(true);
        map.add(h);
        showRegion(h.getLocation());
    }

    public static void startGame(Hero h) {
        if (!create) {
            _startGame(h, Constant.MapSize - 1, 0);
            return;
        }
        Network.send(new Update('g', 's', ""));
        initialize();
        _startGame(h, 0, Constant.MapSize - 1);
        if (gameId) {
            map.getCell(new Point(Constant.MapSize - 1, 0)).getState()._setObject(new Zombie(null));
        }
        //Fill views.game.Map
        for (int i = 0; i < Constant.CollectableN; i++) {
            Point p = getRandomPosition();
            map.add(new Supply(p));
            p = getRandomPosition();
            map.add(new Vaccine(p));
            p = getRandomPosition();
            map.add(new Trap(p));
        }
        for (int i = 0; i < Constant.ZombieN; i++)
            addZombie();
        if (gameId) {
            map.getCell(new Point(Constant.MapSize - 1, 0)).getState()._setObject(null);
        }
        if (gameId)
            listenNet();
        Network.send(new Update('g', 'e', ""));
    }


    private static Point getRandomPosition() {
        Point p;
        do {
            p = new Point(random.nextInt(Constant.MapSize), random.nextInt(Constant.MapSize));
        } while (map.getCell(p).getObject() != null);
        return p;
    }

    private static void addZombie() {
        Point p = getRandomPosition();
        Zombie z = new Zombie(p);
        zombies.add(z);
        map.add(z);

    }


    public static Cell[] getRegion(Character c) {
        return map.getRegion(c.getLocation());
    }

    public static void showRegion(Point p) {
        map.showRegion(p);
    }

    public static void destroy(Hero h) {
        map.remove(h);
        heroes.remove(h);
        Window.playAudio(Constant.SoundsDirectory + Constant.HeroDeathSound);
        if (heroes.size() == 0)
            gameOver();
        else if (h == hero) {
            changeHero(heroes.get(0));
            hero.getInfo().setVisible(true);
        }
        endSelect();
        selectedStatus.remove(h.getInfo());
    }

    public static void changeHero(Hero h) {
        selectedStatus.remove(h.getInfo());
        heroStatus.add(h.getInfo());
        if (hero != null) {
            heroStatus.remove(hero.getInfo());
            selectedStatus.add(hero.getInfo());
            selectedStatus.setCurrent(hero.getInfo());
        }
        hero = h;
        heroStatus.setCurrent(h.getInfo());
    }

    public static void destroy(Zombie z) {
        map.remove(z);
        zombies.remove(z);
        addZombie();
        endSelect();
        selectedStatus.remove(z.getInfo());
    }

    public static void gameOver() {
        Main.window.gameOver();
        Network.send(new Update('g', 'o', ""));
    }

    public static void gameWin() {
        Main.window.gameWin();
        Network.send(new Update('g', 'w', ""));
    }

    public static void move(Point p) throws GameActionException {
        Valid.checkMoveValidity(p);
        Cell c = new Cell(-1, -1);
        c.getState()._setObject(map.getCell(p).getObject());
        hero.move();
        map.getCell(p).setReserved();
        hero.setLocation(p, () -> {
                    endSelect();
                    showRegion(p);
                    map.getCell(hero.getLocation()).setObject(null);
                    if (hero.isInTrap()){
                        hero.setInTrap(false);
                    }
                }
                , () -> {
            //Move And Collect
            if (c.isCollectable()) {
                collect(c);
                map.getCell(p).setObject(hero);
            }
            else if (c.isTrap()) {
                Trap trap = (Trap) c.getObject();
                hero.setInTrap(true);
                trap.affect(hero);
                if (hero.getCurrentHp() <= 0)
                    map.getCell(p).setObject(trap);
                else
                    map.getCell(p).setObject(hero);
                Main.window.showMassage("You Fall In A Trap");
                Window.playAudio(Constant.TrapSound);
            }
            else
                map.getCell(p).setObject(hero);
        });

    }

    public static void attack() throws GameActionException {
        Valid.checkAttackValid();
        Window.playAudio(Constant.AttackSound);
        hero.attack((Zombie) selected.getObject());
    }

    public static void cure() throws GameActionException {
        Valid.checkCureValid();
        Window.playAudio(Constant.CureSound);
        hero.cure();
        //Remove objects.Zombie
        Zombie z = (Zombie) selected.getObject();
        zombies.remove(z);
        //Add objects.Hero
        int index = random.nextInt(availableHeroes.size());
        Hero h = availableHeroes.remove(index);
        heroes.add(h);
        h.setLocation(z.getLocation());
        //UpdateMap
        map.remove(z);
        map.add(h);
        showRegion(h.getLocation());
        selectedStatus.setItem(h.getInfo());
        //Network
        Network.send(new Update('g', 'h', ""));
        //Check Win | Over
        CuredHeroesN++;
        if (CuredHeroesN == Constant.CollectableN) {
            if (!gameId) {
                if (heroes.size() >= Constant.CollectableN)
                    gameWin();
                else
                    gameOver();
            } else {
                int n1 = heroes.size();
                int n2 = map.state.getPlayers().size() - n1;
                if (n1 > n2)
                    gameWin();
                else if (n1 < n2)
                    gameOver();
                else
                {
                    Main.window.gameWin();
                    Network.send(new Update('g', 'o', ""));
                }
            }
        }
    }

    public static void collect(Cell selected) {
        Collectable c = (Collectable) selected.getObject();
        if (c instanceof Supply) {
            RemainSupply--;
            Window.playAudio(Constant.SupplySound);
        }
        else {
            RemainVaccine--;
            Window.playAudio(Constant.VaccineSound);
        }
        hero.pickUp(c);
    }

    public static void useSpecial() throws GameActionException {
        if (!hero.isSpecialAction()) {
            Valid.checkSpecialValid();
            hero.useSpecial();
        }
    }

    public static void endTurn() {
        zombies.stream().toList().forEach(Zombie::attack);
        map.hide_();
        heroes.forEach(h -> {
            h.reset();
            showRegion(h.getLocation());
        });
        addZombie();
        endSelect();
    }

    public static void endSelect() {
        selected = null;
        selectedStatus.setItem(null);
        Main.window.hideMassage();
        Window.gameScene.actionsList.setVisible(false);
    }

    public static void chooseHero() throws InvalidTargetException {
        Valid.checkChooseHeroValid();
        changeHero((Hero) selected.getObject());
        endSelect();
    }

    public static void execute(Update u) {
        switch (u.UpdateAction) {
            case 's' -> initialize();
            case 'e' -> {
                Window.introScene.waitScene.endListen();
                Main.window.progress(Window.launchScene);
            }
            case 'w' -> Main.window.gameOver();
            case 'h' -> CuredHeroesN++;
            case 'x' -> {
                Window.introScene.waitScene.endListen();
                Main.window.goToStart();
            }
            default -> Main.window.gameWin();
        }
    }

    public static void listenNet() {
        Network.read();
    }
}
