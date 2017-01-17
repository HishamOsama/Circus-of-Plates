package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.BitSet;

import javax.swing.Timer;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import model.shapes.interfaces.Shape;

public class MainController {

	@FXML
	private Pane paneFXid;

	private BitSet keyboardBitSet = new BitSet();
	private static final int KEYBOARD_MOVEMENT_DELTA = 25;

	@FXML
	public void initialize() {
		final ImageView player1 = createP1();
		final ImageView player2 = createP2();
		generateStars();
		move(paneFXid, player1, player2);

	}

	private ImageView createP1() {
		final String imagePath = "http://www.clipartlord.com/wp-content/uploads/2015/07/clown16.png";
		final ImageView imageView = new ImageView(imagePath);
		imageView.setFitHeight(150);
		imageView.setFitWidth(150);
		imageView.setX(400);
		imageView.setY(500);
		paneFXid.getChildren().add(imageView);
		return imageView;
	}

	private ImageView createP2() {
		final String imagePath = "http://www.clipartlord.com/wp-content/uploads/2015/07/clown16.png";
		final ImageView imageView = new ImageView(imagePath);
		imageView.setFitHeight(150);
		imageView.setFitWidth(150);
		imageView.setX(100);
		imageView.setY(500);
		paneFXid.getChildren().add(imageView);
		return imageView;
	}

	private void move(Pane pane, final ImageView player1, final ImageView player2) {

		pane.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {

				keyboardBitSet.set(event.getCode().ordinal(), true);
				for (KeyCode keyCode : KeyCode.values()) {
					if (keyboardBitSet.get(keyCode.ordinal())) {
						if (keyCode == KeyCode.RIGHT) {
							player1.setX(player1.getX() + KEYBOARD_MOVEMENT_DELTA);
						}
						if (keyCode == KeyCode.LEFT) {
							player1.setX(player1.getX() - KEYBOARD_MOVEMENT_DELTA);
						}
						if (keyCode == KeyCode.D) {
							player2.setX(player2.getX() + KEYBOARD_MOVEMENT_DELTA);
						}
						if (keyCode == KeyCode.A) {
							player2.setX(player2.getX() - KEYBOARD_MOVEMENT_DELTA);
						}
					}
				}

			}

		});

		pane.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				keyboardBitSet.set(event.getCode().ordinal(), false);
			}
		});

		pane.setFocusTraversable(true);

	}

	// Setting Stars initially
	private void generateStars() {
		System.out.println("HERE!!!!");

		PlatesPool platesPool = new PlatesPool();

		Shape star = platesPool.getPlate();

		BufferedImage starImage = star.getImage();

		Image image = SwingFXUtils.toFXImage(starImage, null);

		ImageView dispaly = new ImageView(image);
		dispaly.setFitHeight(50);
		dispaly.setFitWidth(50);
		dispaly.setX(50);
		dispaly.setY(-50);

		

		Timer timer = new Timer(30, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispaly.setY(dispaly.getY() + 1);
			}
		});
		
        timer.start();


		paneFXid.getChildren().add(dispaly);

	}

}