package view;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.ResourceBundle;

import verwaltung.Buchung;
import verwaltung.Zimmer;
import util.Konstanten;
import view.util.ErrorAlert;
import view.util.WarningAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.BuchungModel;
import model.GastModel;
import model.ZimmerModel;

public class NeueBuchungGUIController {

	private LocalDate abreise;
	private LocalDate anreise;
	private boolean fruehstueck;
	private boolean kinderbett;
	private Zimmer ausgewaehltesZimmer;
	private int zimmerNr;
	private String ausgewaehlteZahlungsart;
	private int anzahlPersonen;
	private int gastNr;
	private boolean storniert;
	private ArrayList<Zimmer> verfuegbareZimmer;
	private BuchungModel buchungModel;
//	private Buchung buchung;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private DatePicker dpAnreise;

	@FXML
	private DatePicker dpAbreise;

	@FXML
	private ChoiceBox<String> cmbZahlungsart;

	@FXML
	private ChoiceBox<Zimmer> cmbZimmer;

	@FXML
	private ChoiceBox<Integer> cmbAnzPersonen;

//    @FXML
//    private TextField tfPersonenanzahl;

	// @FXML
	// private TextField tfBuchungsnummer;

	@FXML
	private Button btnBuchen;

	@FXML
	private TextField tfGastnummer;

	@FXML
	private CheckBox chbFruehstueck;

	@FXML
	private CheckBox chbKinderbett;

	@FXML
	private Button btnAbbrechen;

	@FXML
	void addBuchung(ActionEvent event) throws InterruptedException {
		
		Buchung buchung = null;
		try {
			buchungModel = new BuchungModel();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		anreise = dpAnreise.getValue();
		abreise = dpAbreise.getValue();

		try {
			setValues();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

//		if (geburtsdatum1 == null) {
//			new ErrorAlert("Geburtsdatum", "Das Geburtsdatum darf nicht leer sein.", false);
//			dpGastGeburtsdatum.requestFocus();
//		}

//		System.out.println(anreise);
//		System.out.println(abreise);
//		System.out.println(gastNr);
//		System.out.println(zimmerNr);
//		System.out.println(ausgewaehltesZimmer);
//		System.out.println(ausgewaehlteZahlungsart);
//		System.out.println(fruehstueck);
//		System.out.println(kinderbett);
//		System.out.println(anzahlPersonen);
//		System.out.println(storniert);

		buchung = new Buchung(anreise, abreise, fruehstueck, ausgewaehlteZahlungsart, anzahlPersonen, kinderbett,
				gastNr, zimmerNr);

		try {
			System.out.println(buchung.getAll());
			buchungModel.addBuchung(buchung);

		} catch (SQLException e) {
			new ErrorAlert("Buchung anlegen",
					"Es ist ein Fehler bei der SQL-Datenbank-Verbindung aufgetreteten.\nDas Programm wird beendet",
					true);
		}
		;

	}

	@FXML
	void auswahlAbreise(ActionEvent event) {
		abreise = dpAbreise.getValue();
		clearCombo();
		if (anreise != null && abreise != null && anzahlPersonen != 0) {
			reloadCombo();
		}
	}

	@FXML
	void auswahlAnreise(ActionEvent event) {
		anreise = dpAnreise.getValue();
		clearCombo();
		if (anreise != null && abreise != null && anzahlPersonen != 0) {
			reloadCombo();
		}

	}

	@FXML
	void abbrechen(ActionEvent event) {
		try {
			Parent menu = FXMLLoader.load(getClass().getResource("HotelZimmerSystemView.fxml"));
			Scene menuscene = new Scene(menu);
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(menuscene);
			window.show();
		} catch (IOException e) {
			new WarningAlert("Fehler aufgetreten", "Das Fenster konnte nicht geschlossen werden");
		}
	}

	@FXML
	void initialize() {
		assert dpAnreise != null : "fx:id=\"dpAnreise\" was not injected: check your FXML file 'NeueBuchungGUI.fxml'.";
		assert dpAbreise != null : "fx:id=\"dpAbreise\" was not injected: check your FXML file 'NeueBuchungGUI.fxml'.";
		assert cmbZahlungsart != null : "fx:id=\"cmbZahlungsart\" was not injected: check your FXML file 'NeueBuchungGUI.fxml'.";
		assert cmbZimmer != null : "fx:id=\"cmbZimmer\" was not injected: check your FXML file 'NeueBuchungGUI.fxml'.";
		// assert tfPersonenanzahl != null : "fx:id=\"tfPersonenanzahl\" was not
		// injected: check your FXML file 'NeueBuchungGUI.fxml'.";
		// assert tfBuchungsnummer != null : "fx:id=\"tfBuchungsnummer\" was not
		// injected: check your FXML file 'NeueBuchungGUI.fxml'.";
		assert btnBuchen != null : "fx:id=\"btnBuchen\" was not injected: check your FXML file 'NeueBuchungGUI.fxml'.";
		assert tfGastnummer != null : "fx:id=\"tfGastnummer\" was not injected: check your FXML file 'NeueBuchungGUI.fxml'.";
		assert chbFruehstueck != null : "fx:id=\"chbFruehstueck\" was not injected: check your FXML file 'NeueBuchungGUI.fxml'.";
		assert chbKinderbett != null : "fx:id=\"chbKinderbett\" was not injected: check your FXML file 'NeueBuchungGUI.fxml'.";
		assert btnAbbrechen != null : "fx:id=\"btnAbbrechen\" was not injected: check your FXML file 'NeueBuchungGUI.fxml'.";
		assert cmbAnzPersonen != null : "fx:id=\"cmbAnzPersonen\" was not injected: check your FXML file 'NeueBuchungGUI.fxml'.";

		initializeComboBoxes();
//		try {
//			buchungModel = new BuchungModel();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

	/**
	 * Diese Methode ermittelt die in der ComboBox ausgew�hlte Zahlungsart,damit
	 * diese auf der Rechnung erscheinen kann.
	 */
	private void auswahlZahlungsart() {
		ausgewaehlteZahlungsart = cmbZahlungsart.getSelectionModel().getSelectedItem();
	}

	/**
	 * Diese Methode ermittelt das in der ComboBox ausgew�hlte Zimmer, damit es
	 * gebucht werden kann.
	 */
	private void auswahlZimmer() {
		ausgewaehltesZimmer = cmbZimmer.getSelectionModel().getSelectedItem();
	}

	/**
	 * Diese Methode ermittelt das in der ComboBox ausgew�hlte Zimmer, damit es
	 * gebucht werden kann.
	 */
	private void auswahlPersonen() {
		anzahlPersonen = cmbAnzPersonen.getSelectionModel().getSelectedItem();
	}

	private void initializeComboBoxes() {
		// CmbBx Zahlungsart
		cmbZahlungsart.getItems().addAll("Bar", "Rechnung", "Kreditkarte", "EC-Karte", "PayPal");
		cmbZahlungsart.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> auswahlZahlungsart());

		// CmbBx AnzahlPersonen
		cmbAnzPersonen.getItems().addAll(Konstanten.EZ_BETTEN, Konstanten.DZ_BETTEN, Konstanten.FZ_BETTEN);
		cmbAnzPersonen.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			auswahlPersonen();
			clearCombo();
			if (anreise != null && abreise != null && anzahlPersonen != 0)
				reloadCombo();
		});
	}

	private void reloadCombo() {

		ArrayList<Zimmer> alleVerfuegbareZimmer = new ArrayList<>();
		verfuegbareZimmer = new ArrayList<>();
		try {
			ZimmerModel ziMo = new ZimmerModel();
			alleVerfuegbareZimmer = ziMo.getVerfuegbareZimmer(anreise, abreise);
		} catch (IllegalArgumentException e) {
			if (anreise.isBefore(LocalDate.now())) {
				new WarningAlert("Buchung anlegen",
						"Die Anreise darf nicht in der Vergangenheit liegen.\nBitte korrigieren Sie ihr Eingabe.");
			}
			if (anreise.isAfter(abreise) || anreise.equals(abreise)) {
				new WarningAlert("Buchung anlegen",
						"Die Abreise muss mindestens einen Tag sp�ter als die Anreise sein.\nBitte korrigieren Sie ihr Eingabe.");
			}
			if (anreise.plusDays(120).isBefore(abreise)) {
				new WarningAlert("Buchung anlegen",
						"Die Dauer des Aufenthalts darf 120 Tage nicht �berschreiten.\nBitte korrigieren Sie ihr Eingabe.");
			}
		} catch (SQLException e) {
			new ErrorAlert("Buchung anlegen",
					"Es ist ein Fehler bei der SQL-Datenbank-Verbindung aufgetreteten.\nDas Programm wird beendet",
					true);
		}

		try {
			for (Zimmer zi : alleVerfuegbareZimmer) {
				if (zi.getKategorie().getAnzahlBetten() >= anzahlPersonen) {
					verfuegbareZimmer.add(zi);
				}
			}
		} catch (NullPointerException ignored) {
		}

		cmbZimmer.getItems().addAll(verfuegbareZimmer);

		cmbZimmer.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> auswahlZimmer());
	}

	private void clearCombo() {
		cmbZimmer.getItems().clear();
	}

	private void setValues() throws SQLException {

		if (ausgewaehltesZimmer == null) {
			new WarningAlert("Buchung anlegen",
					"Es wurde kein Zimmer ausgew�hlt.\nBitte korrigieren Sie Ihre Eingabe.");
		} else {
			zimmerNr = ausgewaehltesZimmer.getZimmerNr();
		}

		if (ausgewaehlteZahlungsart == null) {
			new WarningAlert("Buchung anlegen",
					"Es wurde keine Zahlungsart ausgew�hlt.\nBitte korrigieren Sie Ihre Eingabe.");
		} else {
			auswahlZahlungsart();
		}

		if (!tryParseInt(tfGastnummer.getText())) {
			new WarningAlert("Buchung anlegen",
					"Es wurde keine g�ltige Gastnummer angegeben.\nBitte korrigieren Sie Ihre Eingabe.");
		} else {
			gastNr = Integer.valueOf(tfGastnummer.getText());
		}

//		if (!gm.existGast(gastNr)) {
//			new WarningAlert("Buchung anlegen",
//					"BUCHEs wurde keine g�ltige Gastnummer angegeben.\nBitte korrigieren Sie Ihre Eingabe.");
//			return false;
//		}
		
		auswahlPersonen();
		auswahlZimmer();
		fruehstueck = chbFruehstueck.isSelected();
		kinderbett = chbKinderbett.isSelected();
		storniert = false;

	}

	boolean tryParseInt(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}