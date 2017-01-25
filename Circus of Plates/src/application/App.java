package application;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.shapes.dynamicloading.Loader;

public class App extends Application {

	public Stage stage;

	private static final Font FONT = Font.font("", FontWeight.BOLD, 18);

	private VBox menuBox;
	private int currentItem = 0;

	private ScheduledExecutorService bgThread = Executors.newSingleThreadScheduledExecutor();

	private Parent createContent() {
		Pane root = new Pane();
		root.setPrefSize(900, 600);

		Rectangle bg = new Rectangle(900, 600);

		HBox hbox = new HBox(15);
		hbox.setTranslateX(120);
		hbox.setTranslateY(50);

		MenuItem easyItem = new MenuItem("EASY");
		easyItem.setOnActivate(() -> loadGameScene(2));

		MenuItem mediumItem = new MenuItem("MEDIUM");
		mediumItem.setOnActivate(() -> loadGameScene(5));

		MenuItem hardItem = new MenuItem("HARD");
		hardItem.setOnActivate(() -> loadGameScene(10));

		MenuItem itemExit = new MenuItem("EXIT");
		itemExit.setOnActivate(() -> System.exit(0));

		menuBox = new VBox(10, easyItem, mediumItem, hardItem, itemExit);
		menuBox.setAlignment(Pos.TOP_CENTER);
		menuBox.setTranslateX(360);
		menuBox.setTranslateY(300);

		Text about = new Text("MKXMenuApp\n\tby\n    AlmasB");
		about.setTranslateX(50);
		about.setTranslateY(500);
		about.setFill(Color.WHITE);
		about.setFont(FONT);
		about.setOpacity(0.2);

		getMenuItem(0).setActive(true);

		root.getChildren().addAll(bg, hbox, menuBox, about);
		return root;
	}

	private MenuItem getMenuItem(int index) {
		return (MenuItem) menuBox.getChildren().get(index);
	}

	private static class MenuItem extends HBox {
		private TriCircle c1 = new TriCircle(), c2 = new TriCircle();
		private Text text;
		private Runnable script;

		public MenuItem(String name) {
			super(15);
			setAlignment(Pos.CENTER);

			text = new Text(name);
			text.setFont(FONT);
			text.setEffect(new GaussianBlur(2));

			getChildren().addAll(c1, text, c2);
			setActive(false);
			setOnActivate(() -> System.out.println(name + " activated"));
		}

		public void setActive(boolean b) {
			c1.setVisible(b);
			c2.setVisible(b);
			text.setFill(b ? Color.WHITE : Color.GREY);
		}

		public void setOnActivate(Runnable r) {
			script = r;
		}

		public void activate() {
			if (script != null)
				script.run();
		}
	}

	private static class TriCircle extends Parent {
		public TriCircle() {
			Shape shape1 = Shape.subtract(new Circle(5), new Circle(2));
			shape1.setFill(Color.WHITE);

			Shape shape2 = Shape.subtract(new Circle(5), new Circle(2));
			shape2.setFill(Color.WHITE);
			shape2.setTranslateX(5);

			Shape shape3 = Shape.subtract(new Circle(5), new Circle(2));
			shape3.setFill(Color.WHITE);
			shape3.setTranslateX(2.5);
			shape3.setTranslateY(-5);

			getChildren().addAll(shape1, shape2, shape3);

			setEffect(new GaussianBlur(2));
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		loadMenuScene(primaryStage);
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void loadMenuScene(Stage primaryStage) {
		currentItem = 0;
		stage = primaryStage;
		Scene scene = new Scene(createContent());
		scene.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.UP) {
				if (currentItem > 0) {
					getMenuItem(currentItem).setActive(false);
					getMenuItem(--currentItem).setActive(true);
				}
			}

			if (event.getCode() == KeyCode.DOWN) {
				if (currentItem < menuBox.getChildren().size() - 1) {
					getMenuItem(currentItem).setActive(false);
					getMenuItem(++currentItem).setActive(true);
				}
			}

			if (event.getCode() == KeyCode.ENTER) {
				getMenuItem(currentItem).activate();
			}
		});
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(event -> {
			bgThread.shutdownNow();
		});
		primaryStage.show();
	}

	public void loadGameScene(int level) {

		Button b = new Button("BACK");
		b.setLayoutX(300);
		b.setLayoutY(300);
		b.setOnAction(e -> loadMenuScene(stage));
		final Loader loader = new Loader();
		final String path = System.getProperty("user.dir") + File.separator + "shapesJARS" + File.separator;
		loader.setPath(path);
		final ArrayList<Constructor<model.shapes.interfaces.Shape>> loaded = loader.invokeClassMethod();
		for (int i = 0; i < loaded.size(); i++) {
			try {
				Class.forName(loaded.get(i).getName());
			} catch (final ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		Parent root = null;
		try {
			FXMLLoader newLoader = new FXMLLoader(getClass().getResource("/view/GameDesign.fxml"));
			root = (Parent) newLoader.load();
			MainController controller = newLoader.<MainController> getController();
			controller.setDifficulty(level);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		stage.setTitle("Game");
		stage.setScene(new Scene(root));
		stage.setMinWidth(1200);
		stage.setMinHeight(700);
		stage.setResizable(false);
		stage.show();

	}

}