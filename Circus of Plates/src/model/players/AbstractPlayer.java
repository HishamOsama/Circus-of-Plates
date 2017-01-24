package model.players;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import controller.PlateFetching;
import controller.PlateFetching.CheckResult;
import controller.PlayersStack;
import javafx.scene.image.ImageView;
import model.players.util.Observable;
import model.players.util.Observer;
import model.shapes.interfaces.Shape;

public abstract class AbstractPlayer implements Observable{

    protected String imagePath;
    protected File imageFile;
    protected BufferedImage image;
    protected String imageName;
    protected float[][] stacksCenter;
    protected int[][] playerPosition;
    protected PlayersStack[] stacks;
    protected PlateFetching checker;
    protected ArrayList<Observer> observers;
    protected int playerID;

    public AbstractPlayer() {


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

    public void receivePlate(final int index, final Shape plate, final ImageView image) {
        System.out.println(index);
        stacks[index].addShape(plate.getColor(), image, index);

    }

    public BufferedImage getImage() {
        return image;
    }

    public float[][] getStacksCenters() {
        return stacksCenter;
    }
    public CheckResult check(final int x, final int y){
        return checker.CheckMe(x, y);
    }

    public int[][] getPlayerPosition() {
        return playerPosition;
    }

    public void setPoints(final double x, final double y, final float hight) {
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

    public void updateHight(final float increment, final int index) {
        stacksCenter[index][1] -= increment;
    }

    public void move(final float DeltaX) {
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

    public void playerPosition(final int x, final int y) {
        playerPosition[0][0] = x;
        playerPosition[0][1] = y;
    }

    public void setPlayerID(final int id) {
    	playerID = id;
    }

    public int getStackHeight(final int index){
    	return stacks[index].getHeight();
    }

}
