package views.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RootButton extends JButton {
    Image image;
    public RootButton(String img, ActionListener listener){
        setBackground(OpaquePanel.backColor);
        image = new ImageIcon(img).getImage();
        addActionListener(listener);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBorderPainted(false);
        setPreferredSize(new Dimension(30,30));
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        super.paint(g);
    }
}
