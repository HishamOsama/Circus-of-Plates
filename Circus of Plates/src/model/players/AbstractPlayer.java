package model.players;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import controller.PlateFetching;
import controller.PlayersStack;
import controller.PlateFetching.CheckResult;
import javafx.scene.image.ImageView;
import model.shapes.interfaces.Shape;

public abstract class PlayerIF {

    protected String imagePath;
    protected File imageFile;
    protected BufferedImage image;
    protected String imageName;
    protected float[][] stacksCenter;
    protected int[][] playerPosition;
    protected PlayersStack[] stacks;
    protected PlateFetching checker;

    public PlayerIF() {
        
        
        stacks = new PlayersStack[2];
        initialize();
        checker = new PlateFetching(this);
    }
    public void initialize(){
        for(int i = 0 ; i < stacks.length;i++){
            stacks[i] = new PlayersStack(this, i);
            stacks[i].start();
        }
        
    }

    public void receivePlate(int index, Shape plate, ImageView image) {
        System.out.println(index);
        stacks[index].addShape(plate.getColor(), image, index);

    }

    public BufferedImage getImage() {
        return image;
    }

    public float[][] getStacksCenters() {
        return stacksCenter;
    }
    public CheckResult check(int x, int y){
        return checker.CheckMe(x, y);
    }

    public int[][] getPlayerPosition() {
        return playerPosition;
    }

    public void setPoints(double x, double y, float hight) {
        for (int i = 0; i < stacksCenter.length; i++) {
            for (int j = 0; j < stacksCenter[i].length; j++) {
                if (j == 0) {
                    stacksCenter[i][j] += x;

                } else {
                    stacksCenter[i][j] += (y + 4.5 * hight);
                }

            }
        }
    }

    public void updateHight(float increment, int index) {
        stacksCenter[index][1] -= increment;
    }

    public void move(float DeltaX) {
        for (int i = 0; i < stacksCenter.length; i++) {
            stacksCenter[i][0] += DeltaX;
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

    public void playerPosition(int x, int y) {
        playerPosition[0][0] = x;
        playerPosition[0][1] = y;
    }

}
