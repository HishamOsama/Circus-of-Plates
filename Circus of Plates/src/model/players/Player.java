package model.players;

import java.io.File;

public class Player {

	private final String imagePath;
	private final File image;

	public Player(String imageName) {
		imagePath = "C:\\Users\\Hesham\\git\\circus-of-plates\\Circus of Plates\\" + "ClownImage" + File.separator + imageName;
		image = new File(imagePath);
	}

	public File getImage() {
		return image;
	}
}
