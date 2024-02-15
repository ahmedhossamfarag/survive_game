package views.settings;

import engine.Value;
import views.component.XPanel;

import java.awt.*;

@SuppressWarnings("all")
public abstract class ValueView<T> extends XPanel {
    /**
     *
     */
    private static final long serialVersionUID = 2725004307265274563L;
    protected final Value<T> value;

    public ValueView(Value<T> value, LayoutManager l) {
        super(l);
        this.value = value;
    }

    public static ValueView getView(Value<?> value) {
        if (value.getValue() instanceof Boolean)
            return new BooleanValueView((Value<Boolean>) value);
        if (value.getValue() instanceof Color)
            return new ColorValueView((Value<Color>) value);
        return null;
    }

    public abstract void save();
}
