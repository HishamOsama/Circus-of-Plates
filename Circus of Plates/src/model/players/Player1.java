package model.players;

import java.io.File;

public class Player1 extends PlayerIF {


    public Player1() {
        imageName = "Clown1.png";
        imagePath = System.getProperty("user.dir") + File.separator + "ClownImages" + File.separator
                + imageName;
        stacksCenter = new float[][]{{12,-56},{12+117,-93}};
        playerPosition = new int[][]{{400,520}};
        loadImage();
    }
}
