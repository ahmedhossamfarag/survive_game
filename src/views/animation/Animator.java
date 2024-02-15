package views.animation;

import engine.Action;

import java.awt.*;
import java.util.ArrayList;

public class Animator {
    protected final ArrayList<Animation> animations;
    private final ArrayList<Integer> events;

    private final ArrayList<Integer> waitingEvents;

    private final ArrayList<Action> prevActions;
    private final ArrayList<Action> actions;
    protected Animation currentAnimation;

    private Image staticImage;

    private Player player;

    public Animator(Image staticImage) {
        this.staticImage = staticImage;
        animations = new ArrayList<>();
        events = new ArrayList<>();
        waitingEvents = new ArrayList<>();
        prevActions = new ArrayList<>();
        actions = new ArrayList<>();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void addAnimation(Animation animation, int event) {
        animation.setAnimator(this);
        animations.add(animation);
        events.add(event);
    }

    public void riseEvent(int event, Action prev, Action action) {
        if (currentAnimation != null)
            addEvent(event, prev, action);
        else {
            prevActions.add(prev);
            actions.add(action);
            _riseEvent(event);
        }
    }

    public void riseEvent(int event, Action action) {
        riseEvent(event, null, action);
    }

    public void riseEvent(int event) {
        riseEvent(event, null);
    }


    public void _riseEvent(int event) {
        Action action = prevActions.remove(0);
        if (action != null)
            action.apply();
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i) == event) {
                currentAnimation = animations.get(i);
                currentAnimation.count = 0;
                break;
            }
        }
    }

    public Image getImage() {
        if (currentAnimation == null)
            return staticImage;
        return currentAnimation.getImage();
    }

    public void setStaticImage(Image staticImage) {
        this.staticImage = staticImage;
    }

    public void reset() {
        Action action = actions.remove(0);
        if (action != null)
            action.apply();
        currentAnimation = null;
        if (waitingEvents.size() > 0) {
            _riseEvent(waitingEvents.remove(0));
        }
    }

    public void addEvent(int code, Action prev, Action action) {
        waitingEvents.add(code);
        prevActions.add(prev);
        actions.add(action);
    }

    public void end(){
        currentAnimation = null;
    }
}
