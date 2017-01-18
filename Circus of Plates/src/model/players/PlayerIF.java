package model.players;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class PlayerIF {

    protected String imagePath;
    protected File imageFile;
    protected BufferedImage image;
    protected String imageName;
    protected float[][] stacksCenter;

    
    
    public BufferedImage getImage() {
        return image;
    }

    public float[][] getStacksCenters() {
        return stacksCenter;
    }

    public void setPoints(double x, double y, float hight) {
        for (int i = 0; i < stacksCenter.length; i++) {
            for (int j = 0; j < stacksCenter[i].length; j++) {
                if (j == 0) {
                    stacksCenter[i][j] += x;
                    
                } else {
                    stacksCenter[i][j] += (y + 4.5*hight);
                }
                
            }
        }
    }
    
    public void updateHight(float increment , int index){
        stacksCenter[index][1]-=increment;
    }
    public void move(float DeltaX){
        for(int i = 0 ; i < stacksCenter.length ; i++){
            stacksCenter[i][0]+=DeltaX;
            System.out.println("stacksCenters : " + stacksCenter[i][1]);
        }
    }
    
    protected void loadImage() {
        imageFile = new File(imagePath);
        try {
            image = ImageIO.read(imageFile);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

}
