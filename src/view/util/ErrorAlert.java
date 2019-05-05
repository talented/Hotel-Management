package view.util;


import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Diese Klasse zeigt ein neues Alert-Fenster im ERROR-Stil an.
 */
public class ErrorAlert {
	
	/**
	 * �bernimmt die �bergebenen Informationen und stellt sie in einem Error-Alert-Fenster dar. Die Header-Mitteilung ist null.
	 * 
	 * @param titel Titel des Fensters
	 * @param message Mitteilung
	 * @param exitProgram wenn auf true gesetzt, wird die komplette Anwendung sofort geschlossen
	 */
	public ErrorAlert(String titel, String message, boolean exitProgram) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(titel);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
		
		if (exitProgram) {
			Platform.exit();
		}
	}
	
	
	/**
	 * �bernimmt die �bergebenen Informationen und stellt sie in einem Error-Alert-Fenster dar.
	 * 
	 * @param titel Titel des Fensters
	 * @param header Header-Mitteilung
	 * @param message Mitteilung
	 * @param exitProgram wenn auf true gesetzt, wird die komplette Anwendung sofort geschlossen
	 */
	public ErrorAlert(String titel, String header, String message, boolean exitProgram) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(titel);
		alert.setHeaderText(header);
		alert.setContentText(message);
		alert.showAndWait();
		
		if (exitProgram) {
			Platform.exit();
		}
	}

}
