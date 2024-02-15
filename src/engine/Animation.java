package engine;

import java.util.function.IntConsumer;
import java.util.function.IntPredicate;

public class Animation implements Runnable {

    private final int SLEEP;

    private final int frameNum;
    private final IntConsumer animate;
    private final IntConsumer onFinish;
    private final IntPredicate isRunning;

    public Animation(int numOfFrames, int sleep, IntConsumer animate, IntPredicate isRunning, IntConsumer onFinish) {
        frameNum = numOfFrames;
        this.animate = animate;
        this.onFinish = onFinish;
        SLEEP = sleep;
        this.isRunning = isRunning;
    }

    public static void of(int numOfFrames, IntConsumer animate, IntConsumer onFinish) {
        new Animation(numOfFrames, 5, animate, null, onFinish).play();
    }

    public static void of(int sleep, IntConsumer onFinish) {
        new Animation(2, sleep, null, null, onFinish).play();
    }

    @Override
    public final void run() {
        int n = 0;
        while ((isRunning != null && isRunning.test(n) && n < frameNum) || (isRunning == null && n < frameNum)) {
            if (animate != null)
                animate.accept(n);
            n++;
            try {
                Thread.sleep(SLEEP);
            } catch (InterruptedException ignored) {

            }
        }
        if (onFinish != null)
            onFinish.accept(n);
    }

    public final void play() {
        Thread thread = new Thread(this);
        thread.start();
    }


}
