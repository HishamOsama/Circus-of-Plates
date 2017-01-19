package controller;

import java.awt.image.BufferedImage;
import java.util.BitSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.players.Player1;
import model.players.Player2;
import model.players.PlayerIF;
import model.shapes.ShapesMovements;

public class MainController {

    @FXML
    private Pane paneFXid;

    @FXML
    private ImageView imageView;

    private final BitSet keyboardBitSet = new BitSet();
    private static final int KEYBOARD_MOVEMENT_DELTA = 25;
    private int[][] stacksCenters;
    private Logger logger;
    private PlayerIF player1, player2;

    @FXML
    public void initialize() {
        // Background...
        // String path = System.getProperty("user.dir") + File.separator +
        // "Resources" + File.separator + "wallpaper.jpeg";
        final Image image = new Image("http://eskipaper.com/images/circus-wallpaper-2.jpg");
        imageView.setImage(image);
        imageView.setFitWidth(1200);
        imageView.setFitHeight(700);

        logger = LogManager.getLogger();
        logger.debug("Hello");
        final ImageView player1 = createP1();
        final ImageView player2 = createP2();
        move(paneFXid, player1, player2);
        generateStars();

    }

    private ImageView createP1() {

        player1 = new Player1();
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
}