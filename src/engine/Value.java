package engine;

import java.awt.*;

public class Value<T> {


    public static final Value<Boolean> soundEnabled = new Value<>("Sound Enabled", true);
    public static final Value<Boolean> netEnabled = new Value<>("Net Notifications", true);
    public static final Value<Boolean> statusVisible = new Value<>("Status Visible", true);
    public static final Value<Color> cellColor = new Value<>("Cell Color", Color.WHITE);
    public static final Value<Color> selectColor = new Value<>("Select Color", Color.BLUE);

    public static final Value<Color> menuColor = new Value<>("PopMenu Color", Color.BLUE);


    private final String name;
    private T value;

    public Value(String name, T value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
