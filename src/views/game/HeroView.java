package views.game;

import engine.Constant;
import objects.Hero;
import views.component.OpaquePanel;
import java.awt.*;
import java.io.Serial;

public class HeroView extends OpaquePanel {

    public static final Dimension maxDimension = new Dimension(200, 600);
    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 6782420483114199569L;

    private final double MaxHp = 150.0, MaxAC = 10.0, MaxATT = 40.0;
    private final Hero hero;
    private boolean focus;

    public HeroView(Hero hero) {
        super(new FlowLayout());
        this.hero = hero;
        this.setPreferredSize(maxDimension);
        this.setForeground(Constant.ForeColor);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public Hero getHero() {
        return hero;
    }

    @Override
    public void paint(Graphics g) {
        synchronized (this) {
            Dimension d = getPreferredSize();
            g.drawImage(hero.getViewImage(), 0, d.height / 3,
                    d.width, d.height * 2 / 3, null);
            g.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
            if (focus){
                drawString(g, hero.getName(), 5, 15);

                g.setFont(new Font(Font.DIALOG, Font.ITALIC, 20));

                drawString(g, hero.getType() , 5, 40);

                double hp = (getWidth() *  hero.getMaxHp() / MaxHp);
                drawBar(g, "HP ", 60, (int) hp, new Color(28, 136, 57, 255));
                drawBar(g, "Action Points", 100, (int) (getWidth() *  hero.getMaxActions() / MaxAC), new Color(9, 128, 220));
                drawBar(g, "DMG", 140, (int) (getWidth() *  hero.getAttackDmg() / MaxATT), new Color(166, 16, 16));
            }
        }
    }

    private void drawString(Graphics g, String s, int x, int y) {
        g.setColor(Constant.TextColor);
        g.drawString(s, x, y);
    }

    private void drawBar(Graphics g, String s, int y, int w, Color c){
        g.setColor(Constant.TextColor);
        g.drawString(s, 5, y + 15);
        g.setColor(c);
        g.fillRoundRect(0, y + 20, w, 10, 10, 10);
    }

    public void focus() {
        focus = true;
    }

    public void endFocus() {
        focus = false;
    }
}
