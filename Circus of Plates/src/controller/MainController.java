package controller;

import java.awt.image.BufferedImage;
import java.io.File;

import controller.util.Enumrations.Players;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import model.gamestates.GameState;
import model.gamestates.PausedState;
import model.gamestates.Player1WinState;
import model.gamestates.Player2WinsState;
import model.gamestates.TiedState;
import model.players.AbstractPlayer;
import model.save.PlayersStacksData;
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

	@FXML
	private Label pauseLabel;

	@FXML
	private Button saveButton;

	private ResourcesManager resourcesManager;
	private ScoreManager scoreManager;
	private AbstractPlayer player1, player2;
	private Integer countingNumbers = 60;
	private boolean halfSecond = true;
	private boolean initialize = true;
	private int difficulty;

	@FXML
	public void initialize() {

		// Calling Appropriate Mangers
		resourcesManager = new ResourcesManager(paneFXid);
		scoreManager = ScoreManager.getInstance();

		// Load Background Images
		loadImages();

		// Load Players
		final ImageView player1 = createP1();
		final ImageView player2 = createP2();

		// Make Players Movable
		move(paneFXid, player1, player2);

		// Set Labels with appropriate data
		setLabels();

		// Make Labels following updates
		updateLabels();

		// Handling Save Button
		save();
	}

	private void loadImages() {
		// Background...
		String backgroundPath = System.getProperty("user.dir") + File.separator + "Resources" + File.separator
				+ "wallpaper.jpg" + File.separator;
		backgroundPath = new File(backgroundPath).toURI().toString();
		Image backgroundImage = new Image(backgroundPath);
		imageView.setImage(backgroundImage);
		imageView.setFitWidth(DimensionsConstants.XBoundary);
		imageView.setFitHeight(DimensionsConstants.YBoundary);

		// Shelf...
		String shelfPath = System.getProperty("user.dir") + File.separator + "Resources" + File.separator + "shelf.png"
				+ File.separator;
		shelfPath = new File(shelfPath).toURI().toString();
		Image shelfImage = new Image(shelfPath);

		// Left Shelf...
		ImageView leftShelf = new ImageView(shelfImage);
		leftShelf.setX(-50);
		leftShelf.setY(-120);
		leftShelf.setFitWidth(470);
		paneFXid.getChildren().add(leftShelf);

		// Right Shelf...
		ImageView RightShelf = new ImageView(shelfImage);
		RightShelf.setX(830);
		RightShelf.setY(-120);
		RightShelf.setFitWidth(420);
		paneFXid.getChildren().add(RightShelf);
	}

	private ImageView createP1() {

		player1 = resourcesManager.getFirstPlayer();
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
		player2 = resourcesManager.getSecondPlayer();
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
		final PlayersMovement playersMovement = new PlayersMovement(pane, resourcesManager, player1Image, player2Image);
		playersMovement.start();
	}

	private void setLabels() {

		// Pause Label...
		pauseLabel.setText("");
		pauseLabel.setFont(new Font(40));
		pauseLabel.setLayoutX(510);
		pauseLabel.setLayoutY(105);

		// Counter Label...
		counter.setText(countingNumbers.toString());
		counter.setFont(new Font(60));
		counter.setLayoutX(590);
		counter.setLayoutY(35);

		// Player1 Label...
		score1.setText("Player 1");
		score1.setFont(new Font(60));
		score1.setLayoutX(235);
		score1.setLayoutY(125);

		scoreValue1.setText("0");
		scoreValue1.setFont(new Font(60));
		scoreValue1.setLayoutX(310);
		scoreValue1.setLayoutY(200);

		// Player2 Label...
		score2.setText("Player 2");
		score2.setFont(new Font(60));
		score2.setLayoutX(800);
		score2.setLayoutY(125);

		scoreValue2.setText("0");
		scoreValue2.setFont(new Font(60));
		scoreValue2.setLayoutX(875);
		scoreValue2.setLayoutY(200);
	}

	// Setting Stars initially
	private void generateStars() {

		final ShapesMovements shape = new ShapesMovements(paneFXid, resourcesManager, difficulty);
		shape.start("Shapes Movement Thread");

	}

	private void updateLabels() {

		final Timeline oneSecond = new Timeline(new KeyFrame(Duration.seconds(0.5), new EventHandler<ActionEvent>() {

			@Override
			public void handle(final ActionEvent event) {
				if (!Paused.getState()) {

					if (initialize) {
						System.out.println(difficulty);
						generateStars();
						initialize = false;
					}

					// Setting Time Label
					if (!halfSecond) {
						countingNumbers--;

						if (countingNumbers >= 0) {
							counter.setText(countingNumbers.toString());
						} else if (countingNumbers == -1) {
							Paused.changeState();
							terminate();
						}
					}
					halfSecond = !halfSecond;

					// Setting Score Label
					final Integer x = scoreManager.getScore(Players.player1);
					final Integer y = scoreManager.getScore(Players.player2);

					scoreValue1.setText(x.toString());
					scoreValue2.setText(y.toString());

					pauseLabel.setText("");
				} else {
					GameState state = new PausedState();
					pauseLabel.setText(state.printProperMessage());
				}
			}
		}));
		oneSecond.setCycleCount(Timeline.INDEFINITE);
		oneSecond.play();

	}

	private void terminate() {
		paneFXid.getChildren().clear();
		paneFXid.getChildren().add(imageView);
		Label l = new Label();
		l.setText("");
		l.setFont(new Font(30));
		l.setLayoutX(600);
		l.setLayoutY(65);
		paneFXid.getChildren().add(l);

		int x = scoreManager.getScore(Players.player1);
		int y = scoreManager.getScore(Players.player2);

		GameState state;
		if (x > y) {
			state = new Player1WinState();
		} else if (x < y) {
			state = new Player2WinsState();
		} else {
			state = new TiedState();
		}
		l.setText(state.printProperMessage());
	}

	public void setDifficulty(final int level) {
		difficulty = level;
	}

	public int getDifficulty() {
		return difficulty;
	}

	private ImageView convertImage(final BufferedImage image) {
		final Image imageF = SwingFXUtils.toFXImage(image, null);

		final ImageView dispaly = new ImageView(imageF);

		return dispaly;
	}

	private void save() {
		saveButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {

				// LOGIC HERE...
				int len = Players.values().length;
				int[] scores = new int[len];
				for (Players t : Players.values()) {
					scores[t.ordinal()] = scoreManager.getScore(t);
				}
				// GameState g = new GameState(scores, countingNumbers,
				// difficulty);
				PlayersStacksData[] data = new PlayersStacksData[len];
				AbstractPlayer p1 = resourcesManager.getFirstPlayer();
				AbstractPlayer p2 = resourcesManager.getSecondPlayer();
				data[0] = new PlayersStacksData(p1.getStackList(0), p1.getStackList(1));
				data[1] = new PlayersStacksData(p2.getStackList(0), p2.getStackList(1));
				// Snapshot save = new Snapshot(g, data);
				// save.saveShot(System.getProperty("user.dir"), "test");
				// Snapshot load = new Snapshot();
				// load.LoadDate(System.getProperty("user.dir"), "test");

			}
		});

	}

}