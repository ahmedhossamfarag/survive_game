package views.settings;

import engine.Constant;
import engine.Value;

import javax.swing.*;
import java.awt.*;

public class BooleanValueView extends ValueView<Boolean> {
    /**
     *
     */
    private static final long serialVersionUID = 8821961305845334376L;
    private final JCheckBox control;

    public BooleanValueView(Value<Boolean> value) {
        super(value, new BorderLayout());
        setBackground(Constant.BackColor);
        control = new JCheckBox();
        control.setSelected(value.getValue());
        control.setBackground(Constant.BackColor);
        this.add(control, BorderLayout.EAST);
    }

    @Override
    public void save() {
        value.setValue(control.isSelected());
    }
}
