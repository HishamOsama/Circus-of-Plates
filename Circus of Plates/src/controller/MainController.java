package controller;

import java.awt.image.BufferedImage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.util.Enumrations.Players;
import controller.util.StackRemover;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.util.Duration;
import model.players.AbstractPlayer;
import model.players.Player1;
import model.players.Player2;
import model.shapes.ShapesMovements;
import util.DimensionsConstants;

public class MainController {

	@FXML
	private Pane paneFXid;

	@FXML
	private ImageView imageView;

	@FXML
	private Label counter;

	@FXML
	private Label score1;
	@FXML
	private Label score2;
	@FXML
	private Label scoreValue1;
	@FXML
	private Label scoreValue2;

	private Logger logger;
	private AbstractPlayer player1, player2;
	private ScoreManager scoreManager;
	private Integer countingNumbers = 60;
	private boolean halfSecond = true;

	@FXML
	public void initialize() {

		scoreManager = ScoreManager.getInstance();
		// Setting the pane to the Stack Remover
		StackRemover.setPane(paneFXid);

		// Background...
		// String path = System.getProperty("user.dir") + File.separator +
		// "Resources" + File.separator + "wallpaper.jpeg";
		final Image image = new Image("http://eskipaper.com/images/circus-wallpaper-2.jpg");
		imageView.setImage(image);
		imageView.setFitWidth(DimensionsConstants.XBoundary);
		imageView.setFitHeight(DimensionsConstants.YBoundary);
		logger = LogManager.getLogger();
		logger.debug("Hello");
		final ImageView player1 = createP1();
		final ImageView player2 = createP2();
		move(paneFXid, player1, player2);
		setLabels();
		updateLabels();
		generateStars();

	}

	private void setLabels() {
		// Counter Label...
		counter.setText(countingNumbers.toString());
		counter.setFont(new Font(60));
		counter.setLayoutX(600);
		counter.setLayoutY(35);

		// Player1 Label...
		score1.setText("Player 1");
		score1.setFont(new Font(60));
		score1.setLayoutX(235);
		score1.setLayoutY(45);

		scoreValue1.setText("0");
		scoreValue1.setFont(new Font(60));
		scoreValue1.setLayoutX(310);
		scoreValue1.setLayoutY(120);

		// Player2 Label...
		score2.setText("Player 2");
		score2.setFont(new Font(60));
		score2.setLayoutX(800);
		score2.setLayoutY(45);

		scoreValue2.setText("0");
		scoreValue2.setFont(new Font(60));
		scoreValue2.setLayoutX(875);
		scoreValue2.setLayoutY(120);
	}

	private ImageView createP1() {

		player1 = new Player1();
		player1.addObserver(scoreManager);
		final BufferedImage image = player1.getImage();
		final ImageView imageView = convertImage(image);
		imageView.setFitHeight(150);
		imageView.setFitWidth(150);
		imageView.setX(400);// 400
		imageView.setY(520);// 520
		player1.setPoints(imageView.getX(), imageView.getY(), 150);
		paneFXid.getChildren().add(imageView);
		return imageView;
	}

	private ImageView createP2() {
		player2 = new Player2();
		player2.addObserver(scoreManager);
		final BufferedImage image = player2.getImage();
		final ImageView imageView = convertImage(image);
		imageView.setFitHeight(150);
		imageView.setFitWidth(150);
		imageView.setX(100);
		imageView.setY(520);
		player2.setPoints(imageView.getX(), imageView.getY(), 150);
		paneFXid.getChildren().add(imageView);
		return imageView;
	}

	private void move(final Pane pane, final ImageView player1Image, final ImageView player2Image) {
		final PlayersMovement playersMovement = new PlayersMovement(pane, player1, player2, player1Image, player2Image);
		playersMovement.start();
	}

	// Setting Stars initially
	private void generateStars() {

		final ShapesMovements shape = new ShapesMovements(paneFXid, player1, player2);
		shape.start("Naggar :* ");

	}

	private ImageView convertImage(final BufferedImage image) {
		final Image imageF = SwingFXUtils.toFXImage(image, null);

		final ImageView dispaly = new ImageView(imageF);

		return dispaly;
	}

	private void updateLabels() {

		final Timeline oneSecond = new Timeline(new KeyFrame(Duration.seconds(0.5), new EventHandler<ActionEvent>() {

			@Override
			public void handle(final ActionEvent event) {
				if (!Paused.getState()) {

					// Setting Time Label
					if (!halfSecond) {
						countingNumbers--;
						if (countingNumbers >= 0)
						counter.setText(countingNumbers.toString());
					}
					halfSecond = !halfSecond;

					// Setting Score Label
					final Integer x = scoreManager.getScore(Players.player1);
					final Integer y = scoreManager.getScore(Players.player2);

					scoreValue1.setText(x.toString());
					scoreValue2.setText(y.toString());

				}
			}
		}));
		oneSecond.setCycleCount(Timeline.INDEFINITE);
		oneSecond.play();

	}

}