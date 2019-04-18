package org.openjfx.todolist;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		System.out.println("doing");
		String url = "/todolist.fxml";
		Parent root = FXMLLoader.load(getClass().getResource(url));

		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

		primaryStage.setTitle("Todo List Unlimited 2019");
		primaryStage.setScene(scene);
		primaryStage.show();

	}
}
