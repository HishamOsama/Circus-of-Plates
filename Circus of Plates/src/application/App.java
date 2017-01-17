package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

	/// The main method to launch the paint application
	
	@Override
	public void start(Stage primaryStage) throws Exception {
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