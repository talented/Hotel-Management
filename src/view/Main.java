package view;

import java.io.IOException;
import view.util.ErrorAlert;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {

		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("HotelZimmerSystemView.fxml"));
			Scene scene = new Scene(root);

			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();

		} catch (IOException e) {
			new ErrorAlert("Fehler aufgetreten", e.toString(), true);
		}
	}
}