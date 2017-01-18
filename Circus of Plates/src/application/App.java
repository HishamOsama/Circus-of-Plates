package application;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URLEncoder;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.shapes.dynamicloading.Loader;
import model.shapes.interfaces.Shape;

public class App extends Application {

	/// The main method to launch the paint application

	@Override
	public void start(final Stage primaryStage) throws Exception {

		final Loader loader = new Loader();
        final String path = System.getProperty("user.dir") + File.separator + "shapesJARS" + File.separator;
        loader.setPath(path);
        final ArrayList<Constructor<Shape>> loaded = loader.invokeClassMethod();
        for(int i = 0 ; i < loaded.size() ; i++){
        	try {
				Class.forName(loaded.get(i).getName());
			} catch (final ClassNotFoundException e) {
				e.printStackTrace();
			}
        }

		final Parent root = FXMLLoader.load(getClass().getResource("/view/GameDesign.fxml"));
		
		primaryStage.setTitle("Game");
		primaryStage.setScene(new Scene(root));
		primaryStage.setMinWidth(1200);
		primaryStage.setMinHeight(700);
		primaryStage.setResizable(false);
		primaryStage.show();

	}

	public static void main(final String[] args) {
		launch(args);
	}
}