package views.animation;

import views.game.Map;

import java.util.Timer;
import java.util.TimerTask;

public class MapAnimator {
    public static final int sleep = 50;
    private final Map map;
    private Timer timer;

    public MapAnimator(Map map) {
        this.map = map;
    }

    public void play() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Thread(() -> map.repaint()).start();
            }
        }, 0, sleep);
    }

    public void stop() {
        timer.cancel();
    }
}
