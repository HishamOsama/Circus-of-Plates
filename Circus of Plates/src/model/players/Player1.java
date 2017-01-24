package model.players;

import java.io.File;

import model.players.util.Observer;

public class Player1 extends AbstractPlayer {


    public Player1() {
        imageName = "Clown1.png";
        imagePath = System.getProperty("user.dir") + File.separator + "ClownImages" + File.separator
                + imageName;
        stacksCenter = new float[][]{{12,-56},{12+117,-93}};
        playerPosition = new int[][]{{400,520}};
        loadImage();
    }

	@Override
	public void addObserver(final Observer observer) {
		observers.add(observer);
	}

	@Override
	public void notifyObserver() {
		for (final Observer observer : observers) {
			observer.update(0);
		}
	}
}
