package view;

import java.io.IOException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.BuchungModel;
import model.ZimmerModel;

public class BuchungFormUpdateController {

	@FXML
	private DatePicker dpAnreise;

	@FXML
	private DatePicker dpAbreise;

	@FXML
	private TextField tfBuchungsnummer;

	@FXML
	private CheckBox chbKinderbett;
	
	@FXML
	private Button btnAbbrechen;

	@FXML
	private CheckBox chbFruehstueck;

	@FXML
	private ChoiceBox<Integer> cmbAnzahlPersonen;

	@FXML
	private Button btnSuchen;

	@FXML
	private ChoiceBox<Integer> cmbZimmer;
	
	@FXML
	private Button btnaendern;

	@FXML
	private ChoiceBox<String> cmbZahlungsart;
	
	@FXML
    private TextField tfGastNummer;

    @FXML
    private Label lblGebuchteZimmerNr;

	BuchungModel buchungModel;
	ZimmerModel zimmerModel;
	Buchung buchung;
	LocalDate abreise;
	LocalDate anreise;
	boolean fruehstueck;
	boolean kinderbett;
	Zimmer ausgewaehltesZimmer;
	String ausgewaehlteZahlungsart;
	int anzahlPersonen;
	int gastNr;
	boolean stroniert;
	ArrayList<Integer> verfuegbareZimmer;

	/**
	 * Beim Dr�cken dieses Buttons bricht die �nderung an der Buchung ab und kehrt
	 * ohne �nderung der Daten in der Datenbank in das Hauptmen� zur�ck.
	 * 
	 * Dabei wird dieses modale Fenster geschlossen.
	 * 
	 * @param event wenn der Button gedr�ckt wurde
	 */
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

	/**
	 * Diese Methode ermittelt aus dem im DatePicker ausgew�hlten Datum das
	 * Abreisedatum
	 * 
	 * @param event
	 */
	@FXML
	void auswahlAbreise(ActionEvent event) {
		abreise = dpAbreise.getValue();
		clearCombo();
		if (anreise != null && abreise != null && anzahlPersonen != 0) {
			reloadCombo();
		}
	}

	/**
	 * Diese Methode ermittelt aus dem im DatePicker ausgew�hlten Datum das
	 * Anreisedatum
	 * 
	 * @param event
	 */
	@FXML
	void auswahlAnreise(ActionEvent event) {
		anreise = dpAnreise.getValue();
		clearCombo();
		if (anreise != null && abreise != null && anzahlPersonen != 0) {
			reloadCombo();
		}
	}

	/**
	 * Beim Klicken dieses Buttons wird gepr�ft, ob die im Textfeld eingegebene
	 * Buchungsnummer existiert.
	 * 
	 * Au�erdem werden bei existierender Buchungsnummer die zu dieser Buchung in der
	 * Datenbank vorhandenen Daten geladen und angezeigt
	 * 
	 * Sollte die Buchungsnummer nicht existieren oder die Buchung nicht mehr
	 * �nderbar sein, soll ein ErrorAlert eingeblendet werden.
	 * 
	 * @param event wenn der Button gedr�ckt wurde
	 * @throws SQLException
	 */
	@FXML
	void buchungsnummerSuchen(ActionEvent event) throws SQLException {

		if (!tfBuchungsnummer.getText().isEmpty()) {
		}
		String buchungsNr = tfBuchungsnummer.getText();
		System.out.println(buchungsNr);

		buchung = buchungModel.getBuchungByBuchungsNr(buchungsNr);
		System.out.println(buchung.getAll());
		if (!buchungsNr.equals(buchung.getBuchungsNr())) {
			System.out.println("Diese Buchungsnummer existiert nicht!");
			new ErrorAlert("Eingabefehler", null, "Die Buchungsnummer existiert nicht!", false);
		} else {
			anzeigen(buchung);
			btnSuchen.setDisable(true);
			tfBuchungsnummer.setDisable(true);
		}
		
	}

	/**
	 * Zeigt die aktuell in der internen Datenstruktur ausgew�hlten Werte in
	 * Abh�ngigkeit von der Buchungsnummer in der Oberfl�che an
	 */
	private void anzeigen(Buchung buchung) {

		tfGastNummer.setText(String.valueOf(buchung.getGastNr()));
		lblGebuchteZimmerNr.setText(String.valueOf(buchung.getZimmerNr()));
		//tfBuchungsnummer.setText(buchung.getBuchungsNr());
		dpAnreise.setValue(buchung.getAnreise());
		dpAbreise.setValue(buchung.getAbreise());
		chbFruehstueck.setSelected(buchung.isFruehstueck());
		chbKinderbett.setSelected(buchung.isKinderbett());
		cmbAnzahlPersonen.setValue(buchung.getAnzahlPersonen());
		cmbZimmer.setValue(buchung.getZimmerNr());
		cmbZahlungsart.setValue(buchung.getZahlungsart());

	}

	/**
	 * Beim Klicken dieses Buttons werden alle vorgenommenen �nderungen in der
	 * Datenbank gespeichert und dieses modale Fenster geschlossen.
	 * 
	 * @param event wenn der Button gedr�ckt wurde
	 * @throws SQLException 
	 */
	@FXML
	void buchungAendern(ActionEvent event) throws SQLException {

		buchungModel = new BuchungModel();
		String buchungsNr = tfBuchungsnummer.getText();

		// try {
		if (buchungModel.existBuchung(buchungsNr) == true) {

			/**
			 * @throws Ein ErrorAlert wird generiert, wenn das Anreisedatum fr�her als heute
			 *             ist.
			 *
			 * @param anreise Weist der Anreise den �bergebenen Wert zu.
			 */
			anreise = dpAnreise.getValue();
			clearCombo();

			if (anreise.isBefore(LocalDate.now())) {
				dpAnreise.requestFocus();
				reloadCombo();
			} else {

			}

			new ErrorAlert("Anreisedatum", "Die Anreise darf fr�hestens heute erfolgen", false);

			/**
			 * @throws Ein ErrorAlert wird generiert, wenn das Abreisedatum fr�her als das
			 *             Anreisedatum ist oder mehr als 120 Tage nach dem Anreisedatum
			 *             liegt.
			 *
			 * @param abreise Weist der Abreise den �bergebenen Wert zu.
			 */
			abreise = dpAbreise.getValue();
			clearCombo();

			if (abreise.isBefore(anreise) || anreise.plusDays(120).isBefore(abreise)) {
	
				dpAbreise.requestFocus();
	
				reloadCombo();
			} else {

			}

			new ErrorAlert("Abreisedatum", "Die Abreise muss maximal 120 Tage nach der Anreise erfolgen", false);

			/**
			 * @param fruehstueck Weist dem Fr�hst�ck true oder false zu.
			 */
			//String fruehstueck = chbFruehstueck.getText();

			/**
			 * @param kinderbett Weist dem Kinderbett true oder false zu.
			 */
			//String kinderbett = chbKinderbett.getText();

			/**
			 * @throws Ein ErrorAlert wird generiert, wenn das gew�nschte Zimmer nicht
			 *             verf�gbar ist.
			 *
			 * @param ausgewaehltesZimmer Weist dem gew�hlten Zimmer den �bergebenen Wert
			 *                            zu.
			 */

			if (ausgewaehltesZimmer == null) {
				new ErrorAlert("Zimmer", "Das Zimmer ist nicht verf�gbar", false);
				cmbZimmer.requestFocus();
			}
			//ObservableList<Integer> zimmer = cmbZimmer.getItems();

			new ErrorAlert("Ausgew�hltes Zimmer", "Das Zimmer ist nicht verf�gbar", false);

			/**
			 * @throws Ein ErrorAlert wird generiert, wenn eine falsche Zahlungsart
			 *             ausgew�hlt wurde.
			 *
			 * @param ausgewaehlteZahlungsart Weist der gew�hlten Zahlungsart den
			 *                                �bergebenen Wert zu.
			 */

			if (ausgewaehlteZahlungsart.isEmpty() || ausgewaehlteZahlungsart == null) {
				// new ErrorAlert("Zahlungsart", "Die Zahlungsart ist nicht verf�gbar", false);
				cmbZahlungsart.requestFocus();

				//ObservableList<String> zahlungsart = cmbZahlungsart.getItems();
			} else {

			}

			new ErrorAlert("Zahlungsart", "Die Zahlungsart ist nicht verf�gbar", false);

		}
		//neueBuchung.setBuchungsNr(buchungsNr);
		//neueBuchung.setAnreise(anreise);
//		neueBuchung.setAbreise(abreise);
//		neueBuchung.setFruehstueck(this.fruehstueck);
//		neueBuchung.setAnzahlPersonen(anzahlPersonen);
//		neueBuchung.setKinderbett(this.kinderbett);
//		neueBuchung.setZimmerNr(ausgewaehltesZimmer.getZimmerNr());
//		neueBuchung.setZahlungsart(ausgewaehlteZahlungsart);

		// bm.updateBuchung(neueBuchung);

		// buchungModel.updateBuchung(neueBuchung);
//		if (neueBuchung.equals(buchung)) {
//			return;
//		} else {
//			buchungModel.updateBuchung(neueBuchung);
//		}

		/*
		 * else { new WarningAlert("Buchung �ndern fehlgeschlagen",
		 * "Die Buchung mit der Nummer:\n " + buchungsNr +
		 * " konnte nicht ge�ndert werden"); }
		 */

		/*
		 * if ( buchungsNr == null || anreise == null || abreise == null || fruehstueck
		 * == null || kinderbett == null || anzahlPersonen <= 1 || zimmer == null ||
		 * zahlungsart == null ) { return; }
		 */

	}

	/**
	 * Diese Methode initialisiert die Elemente der GUI.
	 * @throws SQLException 
	 * 
	 */
	@FXML
	void initialize() throws SQLException {
		assert dpAnreise != null : "fx:id=\"dpAnreise\" was not injected: check your FXML file 'BuchungAendernGUI.fxml'.";
		assert dpAbreise != null : "fx:id=\"dpAbreise\" was not injected: check your FXML file 'BuchungAendernGUI.fxml'.";
		assert tfBuchungsnummer != null : "fx:id=\"tfBuchungsnummer\" was not injected: check your FXML file 'BuchungAendernGUI.fxml'.";
		assert cmbZahlungsart != null : "fx:id=\"cmbZahlungsart\" was not injected: check your FXML file 'BuchungAendernGUI.fxml'.";
		assert cmbZimmer != null : "fx:id=\"cmbZimmer\" was not injected: check your FXML file 'BuchungAendernGUI.fxml'.";
		assert chbKinderbett != null : "fx:id=\"chbKinderbett\" was not injected: check your FXML file 'BuchungAendernGUI.fxml'.";
		assert chbFruehstueck != null : "fx:id=\"chbFruehstueck\" was not injected: check your FXML file 'BuchungAendernGUI.fxml'.";
		assert btnSuchen != null : "fx:id=\"btnSuchen\" was not injected: check your FXML file 'BuchungAendernGUI.fxml'.";
		assert btnaendern != null : "fx:id=\"btnaendern\" was not injected: check your FXML file 'BuchungAendernGUI.fxml'.";
		assert btnAbbrechen != null : "fx:id=\"btnAbbrechen\" was not injected: check your FXML file 'BuchungAendernGUI.fxml'.";
		assert cmbAnzahlPersonen != null : "fx:id=\"cmbAnzahlPersonen\" was not injected: check your FXML file 'BuchungAendernGUI.fxml'.";

		buchungModel = new BuchungModel();
				
		initializeComboBox();
		// initializeCheckBox();

		if (tfBuchungsnummer.getText().isEmpty()) {
			btnSuchen.setDisable(false);
			tfBuchungsnummer.setDisable(false);
			btnaendern.setDisable(true);
		} else {
			btnSuchen.setDisable(true);
			tfBuchungsnummer.setDisable(true);
			btnaendern.setDisable(false);
		}
	}

	/**
	 * Diese Methode initialisiert die CheckBoxen f�r die frei w�hlbaren Extras
	 * Fr�hst�ck und Kinderbett.
	 * 
	 * Dabei wird gepr�ft, ob in der jeweiligen CheckBox ein H�kchen gesetzt wurde.
	 */
	/*
	 * private void initializeCheckBox() { if (chbFruehstueck.isSelected()) {
	 * fruehstueck = true; } else { fruehstueck = false; }
	 * 
	 * if (chbKinderbett.isSelected()) { kinderbett = true; } else { kinderbett =
	 * false; }
	 */
	// }

	/**
	 * Diese Methode initialisiert die ComboBoxen f�r die Auswahl der
	 * Personenanzahl, der Zimmernummer und der Zahlungsart.
	 * 
	 * F�r die Auswahl der Zimmernummer wird auf die Anzahl an Zimmern der
	 * jeweiligen Kategorie zur�ckgegriffen und diese mit einer for-Schleife
	 * generiert.
	 */
	private void initializeComboBox() {
		/**
		 * Initialisierung der ComboBox f�r die Auswahl der Personenanzahl.
		 */
		cmbAnzahlPersonen.getItems().addAll(Konstanten.EZ_BETTEN, Konstanten.DZ_BETTEN, Konstanten.FZ_BETTEN);
		// cmbAnzahlPersonen.getItems().add(Konstanten.EZ_BETTEN);
		cmbAnzahlPersonen.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			auswahlPersonen();
			clearCombo();
			if (anreise != null && abreise != null && anzahlPersonen != 0)
				reloadCombo();
		});

		/**
		 * Initialisierung der Combobox f�r die Auswahl der Zimmer.
		 */
		int gesamtzimmer = Konstanten.EZ_ANZAHL + Konstanten.DZ_ANZAHL + Konstanten.FZ_ANZAHL + Konstanten.SUITE_ANZAHL;
		for (int i = 1; i <= gesamtzimmer; i++) {
			cmbZimmer.getItems().addAll(i);
		}
		cmbZimmer.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> {
					try {
						auswahlZimmer();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				});

		/**
		 * Initialisierung der ComboBox f�r die Auswahl der Zahlungsart.
		 */
		cmbZahlungsart.getItems().addAll("Bar", "Rechnung", "Kreditkarte", "EC-Karte", "PayPal");
		cmbZahlungsart.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> auswahlZahlungsart());

	}

	/**
	 * Diese Methode ermittelt die in der ComboBox ausgew�hlte Personenanzahl, damit
	 * damit die passenden Zimmer angezeigt werden k�nnen.
	 */
	private void auswahlPersonen() {
		// anzahlPersonen = Integer.valueOf(tfPersonenanzahl.getText());
		anzahlPersonen = (int) cmbAnzahlPersonen.getSelectionModel().getSelectedItem();
	}

	/**
	 * Diese Methode ermittelt das in der ComboBox ausgew�hlte Zimmer, damit es
	 * gebucht werden kann.
	 * @throws SQLException 
	 */
	private void auswahlZimmer() throws SQLException {
		ausgewaehltesZimmer = zimmerModel.getZimmerByZimmerNr(cmbZimmer.getSelectionModel().getSelectedItem());
		System.out.println(ausgewaehltesZimmer);
	}

	/**
	 * Diese Methode ermittelt die in der ComboBox ausgew�hlte Zahlungsart,damit
	 * diese auf der Rechnung erscheinen kann.
	 */
	private void auswahlZahlungsart() {
		ausgewaehlteZahlungsart = cmbZahlungsart.getSelectionModel().getSelectedItem();
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
					verfuegbareZimmer.add(zi.getZimmerNr());
				}
			}
		} catch (NullPointerException ignored) {
		}

		cmbZimmer.getItems().addAll(verfuegbareZimmer);

		cmbZimmer.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> {
					try {
						auswahlZimmer();
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
				});
	}

	private void clearCombo() {
		cmbZimmer.getItems().clear();
	}

	@SuppressWarnings("unused")
	private boolean setValues() throws SQLException {

		buchungModel = new BuchungModel();

		boolean result = false;

		if (ausgewaehltesZimmer == null) {
			new WarningAlert("Buchung anlegen",
					"Es wurde kein Zimmer ausgew�hlt.\nBitte korrigieren Sie Ihre Eingabe.");
			return false;
		}

		int zimmerNr = ausgewaehltesZimmer.getZimmerNr();

		if (ausgewaehlteZahlungsart == null) {
			new WarningAlert("Buchung anlegen",
					"Es wurde keine Zahlungsart ausgew�hlt.\nBitte korrigieren Sie Ihre Eingabe.");
			return false;
		}
		/*
		 * if (!tryParseInt(tfGastnummer.getText())) { new
		 * WarningAlert("Buchung anlegen",
		 * "TRY Es wurde keine g�ltige Gastnummer angegeben.\nBitte korrigieren Sie Ihre Eingabe."
		 * ); return false; }
		 */
		String buchungsNr = tfBuchungsnummer.getText();
		if (!buchungModel.existBuchung(buchungsNr)) {
			new WarningAlert("Buchung anlegen",
					"BUCHEs wurde keine g�ltige Gastnummer angegeben.\nBitte korrigieren Sie Ihre Eingabe.");
			return false;
		}
		boolean fruehstueck = chbFruehstueck.isSelected();
		boolean kinderbett = chbKinderbett.isSelected();
		boolean storniert = false;

		result = true;

		return result;
	}

	boolean tryParseInt(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public void setBuchung(Buchung buchung) {
		this.buchung = buchung;	
	}

}
