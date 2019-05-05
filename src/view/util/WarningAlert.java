package view.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class WarningAlert {

	/**
	 * �bernimmt die �bergebenen Informationen und stellt sie in einem Warning-Alert-Fenster dar. Die Header-Mitteilung ist null.
	 * 
	 * @param titel Titel des Fensters
	 * @param message Mitteilung
	 */
	public WarningAlert(String titel, String message) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(titel);
		alert.setHeaderText(null);
		alert.setContentText(message);
		
		alert.showAndWait();
	}
	
	
	/**
	 * �bernimmt die �bergebenen Informationen und stellt sie in einem Warning-Alert-Fenster dar.
	 * 
	 * @param titel Titel des Fensters
	 * @param header Header-Mitteilung
	 * @param message Mitteilung
	 */
	public WarningAlert(String titel, String header, String message) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(titel);
		alert.setHeaderText(header);
		alert.setContentText(message);
		
		alert.showAndWait();
	}
}
