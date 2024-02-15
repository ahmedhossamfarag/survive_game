package views.settings;

import engine.Value;
import views.component.XButton;

import javax.swing.*;
import java.awt.*;

public class ColorValueView extends ValueView<Color> {

    /**
     *
     */
    private static final long serialVersionUID = 8685859743437832515L;
    private JButton control;

    public ColorValueView(Value<Color> value) {
        super(value, new FlowLayout(FlowLayout.RIGHT, 3, 3));
        control = new XButton(new Dimension(15, 15), value.getValue(), (e) ->
                control.setBackground(JColorChooser.showDialog(null, "Pick Cell Color", value.getValue())));
        this.add(control);
    }

    @Override
    public void save() {
        value.setValue(control.getBackground());
    }
}
