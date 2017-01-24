package controller;

import java.util.BitSet;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import model.players.AbstractPlayer;

public class PlayersMovement extends ImageView implements Runnable {

	private final Pane fPane;
	private final AbstractPlayer player1;
	private final AbstractPlayer player2;
	private final ImageView player1Image;
	private final ImageView player2Image;
	private Thread thread, bigThread;
	private final static String threadName = "PlayersMover";
	private final BitSet keyboardBitSet = new BitSet();
	private static final int KEYBOARD_MOVEMENT_DELTA = 25;
	private final int counter = 0;

	public PlayersMovement(final Pane fPane, final AbstractPlayer player1, final AbstractPlayer player2,
			final ImageView player1Image, final ImageView player2Imgae) {
		this.fPane = fPane;
		this.player1 = player1;
		this.player2 = player2;
		this.player1Image = player1Image;
		this.player2Image = player2Imgae;
	}

	public void start() {
		bigThread = new Thread() {
			@Override
			public void run() {
				if (thread == null) {
					thread = new Thread(threadName) {
						@Override
						public void run() {
							while (true) {
								// System.out.println("In PlayersMovments #" +
								// (counter++) + " Action...");
								//CheckPause(fPane);
								if (!Paused.getState())
									move(fPane, (player1Image), (player2Image));
								try {
									sleep(100);
								} catch (final InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

						}
					};
					thread.setDaemon(true);
					thread.start();
				}
			}
		};
		bigThread.setDaemon(true);
		bigThread.start();

	}

	private void move(final Pane pane, final ImageView player1, final ImageView player2) {

		pane.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(final KeyEvent event) {

				keyboardBitSet.set(event.getCode().ordinal(), true);
				for (final KeyCode keyCode : KeyCode.values()) {
					if (keyboardBitSet.get(keyCode.ordinal())) {
						if (keyCode == KeyCode.P) {
							Paused.changeState();
						}
						if (!Paused.getState()) {
							if (keyCode == KeyCode.RIGHT && player1.getX() < 1050) {
								player1.setX(player1.getX() + KEYBOARD_MOVEMENT_DELTA);
								PlayersMovement.this.player1.playerPosition((int) player1.getX(), (int) player1.getY());
							}
							if (keyCode == KeyCode.LEFT && player1.getX() > 0) {
								player1.setX(player1.getX() - KEYBOARD_MOVEMENT_DELTA);
								PlayersMovement.this.player1.playerPosition((int) player1.getX(), (int) player1.getY());
							}
							if (keyCode == KeyCode.D && player2.getX() < 1050) {
								player2.setX(player2.getX() + KEYBOARD_MOVEMENT_DELTA);
								PlayersMovement.this.player2.playerPosition((int) player2.getX(), (int) player2.getY());
							}
							if (keyCode == KeyCode.A && player2.getX() > 0) {
								player2.setX(player2.getX() - KEYBOARD_MOVEMENT_DELTA);
								PlayersMovement.this.player2.playerPosition((int) player2.getX(), (int) player2.getY());
							}
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

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Platform.runLater(thread);

	}

	public void CheckPause(final Pane pane) {
		pane.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {

		});
	}

}