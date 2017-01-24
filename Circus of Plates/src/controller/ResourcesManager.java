package controller;

import model.players.Player1;
import model.players.Player2;
import model.players.AbstractPlayer;
import model.shapes.PlatesPool;
import model.shapes.interfaces.Shape;

public class ResourcesManager {

    PlatesPool platesPool;
    AbstractPlayer player1;
    AbstractPlayer player2;

    public ResourcesManager() {
        platesPool = new PlatesPool();
        player1 = new Player1();
        player2 = new Player2();
    }

    public Shape getPlate() {
        return platesPool.getPlate();
    }

    public void returnPlate(final Shape finishedShape) {
        platesPool.returnPlate(finishedShape);
    }

    public AbstractPlayer getFirstPlayer() {
        return player1;
    }

    public AbstractPlayer getSecondPlayer() {
        return player2;
    }
}
