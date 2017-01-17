package controller;

import model.players.Player;
import model.shapes.interfaces.Shape;

public class ResourcesManager {

	PlatesPool platesPool;
	Player player1;
	Player player2;

	public ResourcesManager() {
		platesPool = new PlatesPool();
		player1 = new Player("Clown1");
		player2 = new Player("Clown2");
	}

	public Shape getPlate() {
		return platesPool.getPlate();
	}

	public void returnPlate(final Shape finishedShape) {
		platesPool.returnPlate(finishedShape);
	}

	public Player getFirstPlayer() {
		return player1;
	}

	public Player getSecondPlayer() {
		return player2;
	}
}
