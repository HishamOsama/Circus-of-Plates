package application;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.shapes.dynamicloading.Loader;
import model.shapes.interfaces.Shape;

public class App extends Application {

	/// The main method to launch the paint application
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		final Loader loader = new Loader();
        final String path = "C:\\Users\\Hesham\\git\\circus-of-plates\\Circus of Plates\\shapesJARS\\";
        loader.setPath(path);
        final ArrayList<Constructor<Shape>> loaded = loader.invokeClassMethod();
        for(int i = 0 ; i < loaded.size() ; i++){
        	try {
				Class.forName(loaded.get(i).getName());
			} catch (final ClassNotFoundException e) {
				e.printStackTrace();
			}
        }
		
		Parent root = FXMLLoader.load(getClass().getResource("/view/GameDesign.fxml"));
		primaryStage.setTitle("Game");
		primaryStage.setScene(new Scene(root));
		primaryStage.setMinWidth(700);
		primaryStage.setMinHeight(700);
		primaryStage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}