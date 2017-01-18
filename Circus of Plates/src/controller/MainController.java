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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.players.Player1;
import model.players.Player2;
import model.players.PlayerIF;
import model.shapes.PlatesPool;
import model.shapes.ShapesMovements;
import model.shapes.interfaces.Shape;


public class MainController {

	@FXML
	private Pane paneFXid;
	
	@FXML
	private ImageView imageView;


	private final BitSet keyboardBitSet = new BitSet();
	private static final int KEYBOARD_MOVEMENT_DELTA = 25;
	private int[][] stacksCenters ;
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

		final PlayerIF player1 = new Player1();
		final BufferedImage image = player1.getImage();
		final ImageView imageView = convertImage(image);
		imageView.setFitHeight(150);
		imageView.setFitWidth(150);
		imageView.setX(400);//400
		imageView.setY(520);//520
		player1.setPoints(imageView.getX(), imageView.getY(),150);
		paneFXid.getChildren().add(imageView);
		return imageView;
	}

	private ImageView createP2() {
		final PlayerIF player2 = new Player2();
		final BufferedImage image = player2.getImage();
		final ImageView imageView = convertImage(image);
		imageView.setFitHeight(150);
		imageView.setFitWidth(150);
		imageView.setX(100);
		imageView.setY(520);
		player2.setPoints(imageView.getX(), imageView.getY(),150);
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
						if (keyCode == KeyCode.RIGHT  && player1.getX()<1050) {
							player1.setX(player1.getX() + KEYBOARD_MOVEMENT_DELTA);
						}
						if (keyCode == KeyCode.LEFT && player1.getX()>0) {
							player1.setX(player1.getX() - KEYBOARD_MOVEMENT_DELTA);
						}
						if (keyCode == KeyCode.D  && player2.getX()<1050) {
							player2.setX(player2.getX() + KEYBOARD_MOVEMENT_DELTA);
						}
						if (keyCode == KeyCode.A && player2.getX()>0) {
							player2.setX(player2.getX() - KEYBOARD_MOVEMENT_DELTA);
						}
					}
				}

			}

		});
		
		pane.setOnMousePressed(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
                // TODO Auto-generated method stub
                logger.debug("Current X: " +event.getScreenX());
                logger.debug("Current Y : "+event.getScreenY());
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
	    
//		final ShapesMovements[] imageView = new ShapesMovements[4];
//		final PlatesPool platesPool = new PlatesPool();
//		for (int i = 0; i < 4; i++) {		
//			final Shape star = platesPool.getPlate();
//			imageView[i] = new ShapesMovements(star);
//			ImageView tmp = imageView[i].getImageView();
//			tmp.setFitHeight(50);
//			tmp.setFitWidth(50);
//			tmp.setX(50+i*50);
//			tmp.setY(-50);
//			paneFXid.getChildren().add(tmp);
//			imageView[i].start("Thread-"+i);
//		}
	    ShapesMovements shape = new ShapesMovements(paneFXid);
	    shape.start("Naggar :* ");
	}
    private ImageView convertImage(final BufferedImage image) {
        final Image imageF = SwingFXUtils.toFXImage(image, null);

        final ImageView dispaly = new ImageView(imageF);

        return dispaly;
    }
}