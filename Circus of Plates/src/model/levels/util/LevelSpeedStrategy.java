package model.levels.util;

import controller.MainController;

public abstract class LevelSpeedStrategy {

	private final MainController mainController;
	private final int speed;

	public LevelSpeedStrategy(final int speed, final MainController mainController) {
		this.speed = speed;
		this.mainController = mainController;
		// we have to pass this speed to the MainController --> change Constructor
		// Then pass it to the ShapesMovements to change speed
	}

	public void start() {
		mainController.initialize();
	}
}
