package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class TerminatingController {

	@FXML
	private Pane paneFXid;

	@FXML
	private ImageView imageView;

	@FXML
	private Label winner;

	@FXML
	public void initialize() {
		final Image image = new Image("http://eskipaper.com/images/circus-wallpaper-2.jpg");
		imageView.setImage(image);
		imageView.setFitWidth(1200);
		imageView.setFitHeight(700);
		winner.setText("PLAYER 1");
		winner.setFont(new Font(60));
		winner.setLayoutX(600);
		winner.setLayoutY(35);
	}

}
