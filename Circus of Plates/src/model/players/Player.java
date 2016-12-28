package model.players;

import java.io.File;

public class Player {

	private final String imagePath;
	private final File image;

	public Player() {
		imagePath = "ClownImage" + File.separator + "clown.png";
		image = new File(imagePath);
	}

	public File getImage() {
		return image;
	}
}
