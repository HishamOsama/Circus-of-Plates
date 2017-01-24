package model.levels;

import controller.MainController;
import model.levels.util.LevelSpeedStrategy;

public class HighSpeed extends LevelSpeedStrategy{

	private static final int highSpeed = 14;
	public HighSpeed(final MainController mainController) {
		super(highSpeed, mainController);
	}

}
