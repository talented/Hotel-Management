package view;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;


import verwaltung.Buchung;
import view.util.WarningAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.BuchungModel;
import util.rechnung.Rechnung;
import javafx.scene.Node;

public class HotelZimmerSystemViewController {

	@FXML
	private TextField tfBuchungsStornieren;

	@FXML
	private TextField tfBuchungsDruecken;

	@FXML
	private AnchorPane rootPane;

	@FXML
	private TableView<Buchung> tableview;

	@FXML
	private TableColumn<Buchung, String> columnBuchungsNr;

	@FXML
	private TableColumn<Buchung, Integer> columnGastNr;

	@FXML
	private TableColumn<Buchung, LocalDate> columnAnreise;

	@FXML
	private TableColumn<Buchung, LocalDate> columnAbreise;

	@FXML
	private TableColumn<Buchung, Integer> columnZimmerNummer;

	@FXML
	private TableColumn<Buchung, Boolean> columnFruehstueck;

	@FXML
	private TableColumn<Buchung, Boolean> columnKinderbett;

	@FXML
	private TableColumn<Buchung, Integer> columnAnzahlPersonen;

	@FXML
	private TableColumn<Buchung, String> columnZahlungsart;

	BuchungModel bm;
	Rechnung rechnung;

	/**
	 * Wenn man den Button Buchung_bearbeiten bet�tigt, �ffnet sich ein neues
	 * Fenster mit dem dazu geh�rigen GUI
	 * 
	 * @param event wenn den Button gew�hlt wurde
	 */
	@FXML
	void btnBuchungBearbeiten(ActionEvent event) {
		
		Parent buchungaendern;
		try {

			buchungaendern = FXMLLoader.load(getClass().getResource("BuchungFormUpdate.fxml"));
			Scene buchungaendernscene = new Scene(buchungaendern);
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(buchungaendernscene);
			window.show();
		} catch (IOException e) {
			new WarningAlert("Fehler aufgetreten", "Buchung-bearbeiten Fenster wurde nicht gefunden");
		}
	}

	/**
	 * Wenn man die Buchungsnummer hinzuf�gt und den Button Buchung_stornieren
	 * bet�tigt, wird zu erst gefragt ob man diese Nummer stornieren will. Wenn Ja,
	 * wird in die Database die Buchung storniert und wenn nein, wird der Vorgang
	 * abgebrochen.
	 * 
	 * @param event wenn das Button gew�hlt wurde
	 * @throws SQLException 
	 */
	@FXML
	void btnBuchungStornieren(ActionEvent event) throws SQLException {
		String bnrst = tfBuchungsStornieren.getText();
		bm = new BuchungModel();

		// Pr�ft ob die Buchungsnummer schon existiert.
		if (bm.existBuchung(bnrst) == true) {
			// Fragt ob die Buchung mit der bestimmten Nummer stornieren will
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Buchungsnummer stornieren");
			alert.setHeaderText(null);
			alert.setContentText(
					"Sind Sie sicher das sie wollen die Buchung mit der Nummer:\n" + bnrst + " stornieren?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				new WarningAlert("Buchungsnummer stornieren",
						"Die Buchungs mit der Nummer:\n " + bnrst + " wurde storniert");
				bm.setStorniertTrue(bnrst);
				initializeTable();
			}
		} else {
			new WarningAlert("Buchung stornieren fehlgeschlagen",
					"Die Buchungs mit der Nummer:\n " + bnrst + " existiert nicht");
		}
	}

	/**
	 * Wenn man den Button Gast_bearbeiten bet�tigt, �ffnet sich ein neues Fenster
	 * mit dem dazu geh�rigen GUI
	 * 
	 * @param event wenn das Button gew�hlt wurde
	 */
	@FXML
	void btnGastBearbeiten(ActionEvent event) {
		Parent gastendern;
		try {
			gastendern = FXMLLoader.load(getClass().getResource("GastFormUpdate.fxml"));
			Scene gastendernscene = new Scene(gastendern);
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(gastendernscene);
			window.show();
		} catch (IOException e) {
			new WarningAlert("Fehler aufgetreten", "Gast-bearbeiten Fenster wurde nicht gefunden");
		}

	}

	/**
	 * Wenn man den Button Neue_Buchung_anlegen bet�tigt, �ffnet sich ein neues
	 * Fenster mit dem dazu geh�rigen GUI
	 * 
	 * @param event wenn das Button gew�hlt wurde
	 */
	@FXML
	void btnNeueBuchung(ActionEvent event) {
		Parent neuebuchung;
		try {
			neuebuchung = FXMLLoader.load(getClass().getResource("BuchungFormAdd.fxml"));
			Scene neuebuhungscene = new Scene(neuebuchung);
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(neuebuhungscene);
			window.show();
		} catch (IOException e) {
			new WarningAlert("Fehler aufgetreten", "Neue-buchung Fenster wurde nicht gefunden");
		}

	}

	/**
	 * Wenn man den Button Neuen_Gast_anlegen bet�tigt, �ffnet sich ein neues
	 * Fenster mit dem dazu geh�rigen GUI
	 * 
	 * @param event wenn das Button gew�hlt wurde
	 */
	@FXML
	void btnNeueGast(ActionEvent event) {
		Parent neuengast;
		try {
			neuengast = FXMLLoader.load(getClass().getResource("GastFormAdd.fxml"));
			Scene neuengastscene = new Scene(neuengast);
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(neuengastscene);
			window.show();
		} catch (IOException e) {
			new WarningAlert("Fehler aufgetreten", "Neuen-gast Fenster wurde nicht gefunden");
		}

	}

	/**
	 * Wenn man den Button Rechnung_erstellen bet�tigt, erstellt eine neue Rechnung
	 * in ein spezifischen path mit docx format
	 * 
	 * @param event wenn das Button gew�hlt wurde
	 */
	@FXML
	void btnRechnungErstellen(ActionEvent event) {
		try {
			bm = new BuchungModel();
			String bnrer = tfBuchungsDruecken.getText();
			// Pr�ft ob die Buchungsnummer schon existiert.
			if (bm.existBuchung(bnrer) == true) {
				if (bnrer != null && !bnrer.isEmpty()) {
					
					rechnung = new Rechnung(bm.getBuchungByBuchungsNr(bnrer));
					rechnung.WordDOCXPOI();

				}
			} else {
				new WarningAlert("Rechnung erstellen fehlgeschlagen",
						"Die Buchungs mit der Nummer:\n " + bnrer + " existiert nicht");
			}
		} catch (Exception e) {
			new WarningAlert("Fehler augetreten", e.getMessage());
		}
	}

	@FXML
	void initialize() throws SQLException {
		assert tfBuchungsStornieren != null : "fx:id=\"tfBuchungsStornieren\" was not injected: check your FXML file 'HotelZimmerSystemView.fxml'.";
		assert tfBuchungsDruecken != null : "fx:id=\"tfBuchungsDruecken\" was not injected: check your FXML file 'HotelZimmerSystemView.fxml'.";
		assert tableview != null : "fx:id=\"tableview\" was not injected: check your FXML file 'HotelZimmerSystemView.fxml'.";
		assert columnBuchungsNr != null : "fx:id=\"columnBuchungsNr\" was not injected: check your FXML file 'HotelZimmerSystemView.fxml'.";
		assert columnGastNr != null : "fx:id=\"columnGastNr\" was not injected: check your FXML file 'HotelZimmerSystemView.fxml'.";
		assert columnAnreise != null : "fx:id=\"columnAnreise\" was not injected: check your FXML file 'HotelZimmerSystemView.fxml'.";
		assert columnAbreise != null : "fx:id=\"columnAbreise\" was not injected: check your FXML file 'HotelZimmerSystemView.fxml'.";
		assert columnZimmerNummer != null : "fx:id=\"columnZimmerNummer\" was not injected: check your FXML file 'HotelZimmerSystemView.fxml'.";
		assert columnFruehstueck != null : "fx:id=\"columnFruehstueck\" was not injected: check your FXML file 'HotelZimmerSystemView.fxml'.";
		assert columnKinderbett != null : "fx:id=\"columnKinderbett\" was not injected: check your FXML file 'HotelZimmerSystemView.fxml'.";
		assert columnAnzahlPersonen != null : "fx:id=\"columnAnzahlPersonen\" was not injected: check your FXML file 'HotelZimmerSystemView.fxml'.";
		assert columnZahlungsart != null : "fx:id=\"columnZahlungsart\" was not injected: check your FXML file 'HotelZimmerSystemView.fxml'.";

		initializeTable();
	}

	/**
	 * Diese Methode initializiert die Tableview
	 * @throws SQLException 
	 */
	private void initializeTable() {
		// Set von die verschiedene
		columnBuchungsNr.setCellValueFactory(new PropertyValueFactory<Buchung, String>("buchungsNr"));
		columnGastNr.setCellValueFactory(new PropertyValueFactory<Buchung, Integer>("GastNr"));
		columnAnreise.setCellValueFactory(new PropertyValueFactory<Buchung, LocalDate>("anreise"));
		columnAbreise.setCellValueFactory(new PropertyValueFactory<Buchung, LocalDate>("abreise"));
		columnZimmerNummer.setCellValueFactory(new PropertyValueFactory<Buchung, Integer>("zimmerNr"));
		columnFruehstueck.setCellValueFactory(new PropertyValueFactory<Buchung, Boolean>("fruehstueck"));
		columnKinderbett.setCellValueFactory(new PropertyValueFactory<Buchung, Boolean>("kinderbett"));
		columnAnzahlPersonen.setCellValueFactory(new PropertyValueFactory<Buchung, Integer>("anzahlPersonen"));
		columnZahlungsart.setCellValueFactory(new PropertyValueFactory<Buchung, String>("zahlungsart"));
		try {
			bm = new BuchungModel();
			tableview.setItems(getList());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Diese Methode bekommt eine liste von alle Buchungen in der Database und f�gt
	 * die Daten in der Tableview hinzu
	 * @throws SQLException 
	 */
	private ObservableList<Buchung> getList() throws SQLException {
//		try {
//			bm = new BuchungModel();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		ObservableList<Buchung> buchungen;
		ArrayList<Buchung> gesamtliste = bm.getBuchungsListe();
		ArrayList<Buchung> liste = new ArrayList<>();
		// Nur die Buchungen die nicht Storniert sind, werden gezeigt
		for (Buchung bu : gesamtliste) {
			if (bu.isStorniert() == false) {
				liste.add(bu);
			}
		}
		buchungen = FXCollections.observableArrayList(liste);

		return buchungen;
	}

}
