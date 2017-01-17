package model.players;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player {

    private final String imagePath;
    private File imageFile;
    private BufferedImage image;

    public Player(String imageName) {
        imagePath = "C:\\Users\\Hesham\\git\\circus-of-plates\\Circus of Plates\\" + "ClownImages" + File.separator
                + imageName;
        loadImage();
    }

    public BufferedImage getImage() {
        return image;
    }

    private void loadImage() {
        imageFile = new File(imagePath);
        try {
            image = ImageIO.read(imageFile);
            System.out.println("Image Loaded Successfully");
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
