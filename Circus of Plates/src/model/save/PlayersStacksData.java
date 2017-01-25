package model.save;

import java.util.ArrayList;

import model.shapes.interfaces.Shape;

public class PlayersStacksData {

    private int rightStackSize;
    private int leftStackSize;
    private ArrayList<Shape> rightHandshapes;
    private ArrayList<Shape> leftHandshapes;

    public PlayersStacksData(ArrayList<Shape> rightHandshapes, ArrayList<Shape> leftHandshapes) {
        this.rightHandshapes = rightHandshapes;
        this.leftHandshapes = leftHandshapes;
        rightStackSize = rightHandshapes.size();
        leftStackSize = leftHandshapes.size();
    }

    public int getRightStackSize() {
        return rightStackSize;
    }

    public int getLeftStackSize() {
        return leftStackSize;
    }

    public ArrayList<Shape> getRightHandshapes() {
        return rightHandshapes;
    }

    public ArrayList<Shape> getlefttHandshapes() {
        return leftHandshapes;
    }

}
