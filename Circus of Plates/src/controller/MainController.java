package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
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
import model.shapes.PlatesPool;
import model.shapes.interfaces.Shape;


public class MainController {

	@FXML
	private Pane paneFXid;
	
	@FXML
	private ImageView imageView;


	private final BitSet keyboardBitSet = new BitSet();
	private static final int KEYBOARD_MOVEMENT_DELTA = 25;
	private Logger logger;

	@FXML
	public void initialize() {
		// Background...
		//String path = System.getProperty("user.dir")  + File.separator + "Resources" + File.separator + "wallpaper.jpeg";
		Image image = new Image("http://eskipaper.com/images/circus-wallpaper-2.jpg");
        imageView.setImage(image);
        imageView.setFitWidth(1200);
        imageView.setFitHeight(700);
        
	
	    logger = LogManager.getLogger();
	    logger.debug("Hello");
		final ImageView player1 = createP1();
		final ImageView player2 = createP2();
		generateStars();
		move(paneFXid, player1, player2);

	}

	private ImageView createP1() {

		final Player player1 = new Player("Clown1.png");
		final BufferedImage image = player1.getImage();
		final ImageView imageView = convertImage(image);
		imageView.setFitHeight(150);
		imageView.setFitWidth(150);
		imageView.setX(400);
		imageView.setY(520);
		paneFXid.getChildren().add(imageView);
		return imageView;
	}

	private ImageView createP2() {
		final Player player2 = new Player("Clown2.png");
		final BufferedImage image = player2.getImage();
		final ImageView imageView = convertImage(image);
		imageView.setFitHeight(150);
		imageView.setFitWidth(150);
		imageView.setX(100);
		imageView.setY(520);
		paneFXid.getChildren().add(imageView);
		return imageView;
	}

	private void move(final Pane pane, final ImageView player1, final ImageView player2) {

		pane.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(final KeyEvent event) {

				keyboardBitSet.set(event.getCode().ordinal(), true);
				for (final KeyCode keyCode : KeyCode.values()) {
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
			public void handle(final KeyEvent event) {
				keyboardBitSet.set(event.getCode().ordinal(), false);
			}
		});

		pane.setFocusTraversable(true);

	}

	// Setting Stars initially
	private void generateStars() {

		final ImageView[] imageView = new ImageView[4];

		for (int i = 0; i < 4; i++) {
			final PlatesPool platesPool = new PlatesPool();
			final Shape star = platesPool.getPlate();
			final BufferedImage starImage = star.getImage();
			final Image image = SwingFXUtils.toFXImage(starImage, null);

			final ImageView dispaly = new ImageView(image);
			dispaly.setFitHeight(50);
			dispaly.setFitWidth(50);
			dispaly.setX(50 + (i * 100));
			dispaly.setY(-50);
			imageView[i] = dispaly;
			paneFXid.getChildren().add(imageView[i]);
		}

		final Timer timer = new Timer(30, new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				for (int i = 0; i < 4; i++) {
					imageView[i].setY(imageView[i].getY() + 2);
				}

			}
		});

		timer.start();

	}

	private ImageView convertImage(final BufferedImage image) {
		final Image imageF = SwingFXUtils.toFXImage(image, null);

		final ImageView dispaly = new ImageView(imageF);

		return dispaly;
	}

}