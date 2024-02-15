package views.settings;

import engine.Constant;
import engine.Value;
import views.component.XButton;
import views.component.XLabel;
import views.component.XPanel;
import views.layout.SpaceLayout;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;

@SuppressWarnings("all")
public class Settings extends JFrame {
    /**
     *
     */
    private static final long serialVersionUID = -6171361345878241487L;
    private final ArrayList<ValueView> values;

    public Settings() {
        values = new ArrayList<>();
        try {
            Field[] fields = Class.forName("engine.Value").getFields();
            JPanel panel = new JPanel(new GridLayout(fields.length, 2));
            for (Field f : fields) {
                Value v = (Value) f.get(null);
                panel.add(new XLabel(v.getName()));
                ValueView view = ValueView.getView(v);
                values.add(view);
                panel.add(view);
            }
            this.add(panel, BorderLayout.NORTH);
            XPanel control = new XPanel(new SpaceLayout(true));
            control.add(new XButton("save", (e) -> {
                values.forEach(ValueView::save);
                this.setVisible(false);
            }));
            this.add(control, BorderLayout.SOUTH);
            setSize(300, panel.getPreferredSize().height + control.getPreferredSize().height + 80);
            getContentPane().setBackground(Constant.BackColor);
            setResizable(false);
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }
}
