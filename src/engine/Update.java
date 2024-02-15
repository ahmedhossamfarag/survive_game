package engine;

import objects.Hero;
import objects.Zombie;
import views.game.Cell;
import views.game.Map;

public class Update {
    public char UpdateType;

    public char UpdateAction;

    public String Content;

    public Update(char updateType, char updateAction, String content) {
        UpdateType = updateType;
        UpdateAction = updateAction;
        Content = content;
    }

    public static void execute(String s) {
        String[] arr = s.split(",", 3);
        char ut = arr[0].charAt(0);
        char ua = arr[1].charAt(0);
        String c = arr[2];
        try {
            run(new Update(ut, ua, c));
        }catch (Exception ignored){

        }
    }

    public static void run(Update u) {
        switch (u.UpdateType) {
            case 'g' -> Game.execute(u);
            case 'm' -> Map.execute(u);
            case 'c' -> Cell.execute(u);
            case 'h' -> Hero.execute(u);
            case 'z' -> Zombie.execute(u);
        }
    }

    public String toString() {
        return UpdateType + "," + UpdateAction + "," + Content;
    }
}
