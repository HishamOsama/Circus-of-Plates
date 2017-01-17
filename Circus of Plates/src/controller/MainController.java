package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.BitSet;

import javax.swing.Timer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import model.players.Player;
import model.shapes.interfaces.Shape;


public class MainController {

	@FXML
	private Pane paneFXid;


	private BitSet keyboardBitSet = new BitSet();
	private static final int KEYBOARD_MOVEMENT_DELTA = 25;
	private Logger logger;

	@FXML
	public void initialize() {
	    logger = LogManager.getLogger();
	    logger.debug("Hello");
		final ImageView player1 = createP1();
		final ImageView player2 = createP2();
		generateStars();
		move(paneFXid, player1, player2);

	}

	private ImageView createP1() {

		Player player1 = new Player("Clown1.png");
		BufferedImage image = player1.getImage();
		ImageView imageView = convertImage(image);
		imageView.setFitHeight(150);
		imageView.setFitWidth(150);
		imageView.setX(400);
		imageView.setY(500);
		paneFXid.getChildren().add(imageView);
		return imageView;
	}

	private ImageView createP2() {
		Player player2 = new Player("Clown2.png");
		BufferedImage image = player2.getImage();
		ImageView imageView = convertImage(image);
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

		ImageView[] imageView = new ImageView[4];

		for (int i = 0; i < 4; i++) {
			PlatesPool platesPool = new PlatesPool();
			Shape star = platesPool.getPlate();
			BufferedImage starImage = star.getImage();
			Image image = SwingFXUtils.toFXImage(starImage, null);

			ImageView dispaly = new ImageView(image);
			dispaly.setFitHeight(50);
			dispaly.setFitWidth(50);
			dispaly.setX(50 + (i * 100));
			dispaly.setY(-50);
			imageView[i] = dispaly;
			paneFXid.getChildren().add(imageView[i]);
		}

		Timer timer = new Timer(30, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < 4; i++) {
					imageView[i].setY(imageView[i].getY() + 2);
				}

			}
		});

		timer.start();

	}

	private ImageView convertImage(BufferedImage image) {
		Image imageF = SwingFXUtils.toFXImage(image, null);

		ImageView dispaly = new ImageView(imageF);

		return dispaly;
	}

}