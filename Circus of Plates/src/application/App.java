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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import logging.Logging;
import model.levels.HighSpeed;
import model.levels.LowSpeed;
import model.levels.MediumSpeed;
import model.levels.util.LevelSpeedStrategy;
import model.shapes.dynamicloading.Loader;

public class App extends Application {

    public Stage stage;

    private static final Font FONT = Font.font("", FontWeight.BOLD, 18);

    private VBox menuBox;
    private int currentItem = 0;
    private final static String path = System.getProperty("user.dir") + File.separator + "bin" + File.separator
            + "model" + File.separator + "shapes";
    private ScheduledExecutorService bgThread = Executors.newSingleThreadScheduledExecutor();

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(900, 600);

        Rectangle bg = new Rectangle(900, 600);

        HBox hbox = new HBox(15);
        hbox.setTranslateX(120);
        hbox.setTranslateY(50);

        MenuItem easyItem = new MenuItem("EASY");
        easyItem.setOnActivate(() -> loadGameScene(1));

        MenuItem mediumItem = new MenuItem("MEDIUM");
        mediumItem.setOnActivate(() -> loadGameScene(2));

        MenuItem hardItem = new MenuItem("HARD");
        hardItem.setOnActivate(() -> loadGameScene(3));

        MenuItem load = new MenuItem("LOAD");
        // load.setOnActivate(() -> CALL HERE);

        MenuItem itemExit = new MenuItem("EXIT");
        itemExit.setOnActivate(() -> System.exit(0));

        menuBox = new VBox(10, easyItem, mediumItem, hardItem, load, itemExit);
        menuBox.setAlignment(Pos.TOP_CENTER);
        menuBox.setTranslateX(360);
        menuBox.setTranslateY(300);

        Text about = new Text("Circus of Plates");
        about.setTranslateX(50);
        about.setTranslateY(500);
        about.setFill(Color.WHITE);
        about.setFont(FONT);
        about.setOpacity(0.2);

        getMenuItem(0).setActive(true);

        root.getChildren().addAll(bg, hbox, menuBox, about);

        String pausePath = System.getProperty("user.dir") + File.separator + "Resources" + File.separator + "circus.png"
                + File.separator;
        pausePath = new File(pausePath).toURI().toString();
        Image pause = new Image(pausePath);

        ImageView pauseImage = new ImageView(pause);
        pauseImage.setX(290);
        pauseImage.setY(20);
        pauseImage.setFitWidth(250);
        pauseImage.setFitHeight(250);
        root.getChildren().add(pauseImage);

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
            setOnActivate(() -> Logging.debug(name + " activated"));
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
        final Loader loader = Loader.getInstance();
        final File file = new File(path);
        for (File f : file.listFiles()) {
            loader.invokeClassMethod(f);
        }

        Parent root = null;
        try {
            FXMLLoader newLoader = new FXMLLoader(getClass().getResource("/view/GameDesign.fxml"));
            root = (Parent) newLoader.load();
            MainController controller = newLoader.<MainController> getController();

            LevelSpeedStrategy strategy;

            if (level == 1) {
                strategy = new LowSpeed(controller);
            } else if (level == 2) {
                strategy = new MediumSpeed(controller);
            } else {
                strategy = new HighSpeed(controller);
            }

            strategy.start();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        stage.setTitle("Game");
        stage.setScene(new Scene(root));
        stage.setMinWidth(1200);
        stage.setMinHeight(700);
        stage.setX(0);
        stage.setY(0);
        stage.setResizable(false);
        stage.show();

    }

}