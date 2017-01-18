package model.players;

import java.io.File;

public class Player2 extends PlayerIF {

    public Player2() {
        imageName = "Clown2.png";
        imagePath = System.getProperty("user.dir") + File.separator + "ClownImages" + File.separator + imageName;
        stacksCenter = new float[][] {{21, -61}, {21 + 104, -73}};
        loadImage();
    }

}
