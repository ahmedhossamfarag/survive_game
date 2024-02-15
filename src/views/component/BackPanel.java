package views.component;

import engine.Constant;
import views.layout.FullLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serial;
import java.util.TimerTask;

public class BackPanel extends JPanel {
    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 858097591495196509L;
    private final BufferedImage[] images;
    private int currentImageIndex;
    private java.util.Timer timer;

    public BackPanel() {
        super(new FullLayout(true));
        images = new BufferedImage[6]; // Update with the number of images you have
        currentImageIndex = 0;
        // Load the images from file
        for (int i = 0; i < images.length; i++) {
            images[i] = loadImage(Constant.ImagesDirectory + "aa" + (i + 1) + ".png");
        }
    }

    public void animate() {
        currentImageIndex++;
        repaint();
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        BufferedImage currentImage = images[(currentImageIndex / 10) % images.length];
        g2d.drawImage(currentImage, 0, 0, getWidth(), getHeight(), null);
        super.paintChildren(g);
    }

    private BufferedImage loadImage(String imagePath) {
        try {
            return ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void goOn() {
        timer = new java.util.Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                animate();
                Toolkit.getDefaultToolkit().sync(); // Synchronize the painting of frames
            }
        }, 0, 10);
    }

    public void exit() {
        timer.cancel();
    }
}
