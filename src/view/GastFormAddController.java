package view;


/**
 * Sample Skeleton for 'GastForm_add.fxml' Controller Class
 */



import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;

import verwaltung.Gast;
import util.PropertiesTable;
import view.util.ErrorAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.GastModel;

public class GastFormAddController {

	GastModel gastModel;

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="lblTitel"
	private Label lblTitel; // Value injected by FXMLLoader

	@FXML // fx:id="lblGastVorname"
	private Label lblGastVorname; // Value injected by FXMLLoader

	@FXML // fx:id="txtGastVorname"
	private TextField txtGastVorname; // Value injected by FXMLLoader

	@FXML // fx:id="lblGastNachname"
	private Label lblGastNachname; // Value injected by FXMLLoader

	@FXML // fx:id="txtGastNachname"
	private TextField txtGastNachname; // Value injected by FXMLLoader

	@FXML // fx:id="lblGastEMail"
	private Label lblGastEMail; // Value injected by FXMLLoader

	@FXML // fx:id="txtGastEMail"
	private TextField txtGastEMail; // Value injected by FXMLLoader

	@FXML // fx:id="lblGastTelefon"
	private Label lblGastTelefon; // Value injected by FXMLLoader

	@FXML // fx:id="txtGastTelefon"
	private TextField txtGastTelefon; // Value injected by FXMLLoader

	@FXML // fx:id="dpGastGeburtsdatum"
	private DatePicker dpGastGeburtsdatum; // Value injected by FXMLLoader

	@FXML // fx:id="lblGastGeburtsdatum"
	private Label lblGastGeburtsdatum; // Value injected by FXMLLoader

	@FXML // fx:id="txtGastStrasse"
	private TextField txtGastStrasse; // Value injected by FXMLLoader

	@FXML // fx:id="lblGastHausNr"
	private Label lblGastHausNr; // Value injected by FXMLLoader

	@FXML // fx:id="lblGastStrasse"
	private Label lblGastStrasse; // Value injected by FXMLLoader

	@FXML // fx:id="lblGastOrt"
	private Label lblGastOrt; // Value injected by FXMLLoader

	@FXML // fx:id="txt_gast_Stadt"
	private TextField txtGastOrt; // Value injected by FXMLLoader

	@FXML // fx:id="lblGastPlz"
	private Label lblGastPlz; // Value injected by FXMLLoader

	@FXML // fx:id="lblGastLand"
	private Label lblGastLand; // Value injected by FXMLLoader

	@FXML // fx:id="txtGastPlz"
	private TextField txtGastPlz; // Value injected by FXMLLoader

	@FXML // fx:id="txtGastLand"
	private TextField txtGastLand; // Value injected by FXMLLoader

	@FXML // fx:id="txtGastHausNr"
	private TextField txtGastHausNr; // Value injected by FXMLLoader

	@FXML // fx:id="txtGastNr"
	private TextField txtGastNr; // Value injected by FXMLLoader

	@FXML // fx:id="btnAddGast"
	private Button btnAddGast; // Value injected by FXMLLoader

	@FXML // fx:id="btnEditGast"
	private Button btnEditGast; // Value injected by FXMLLoader

	@FXML // fx:id="btnSearchGast"
	private Button btnSearchGast; // Value injected by FXMLLoader

	@FXML // fx:id="txtAreaGastAnlegen"
	private TextArea txtAreaGastAnlegen; // Value injected by FXMLLoader
	
	Gast gast;
	
	@FXML
	void addGast(ActionEvent event) throws IOException, SQLException {

		/**
		 * 
		 * @throws Ein ErrorAlert wird generiert, wenn der Vorname null, leer oder
		 *             gr��er als die maximal zul�ssige L�nge ist. Die L�nge ist in der
		 *             Klasse PropertiesTable definiert.
		 * 
		 * @param vorname Weist dem Vorname den �bergebenen Wert zu.
		 */
		if (txtGastVorname.getText() == null || txtGastVorname.getText().isEmpty()) {
			new ErrorAlert("Vorname", "Der Vorname muss mindestens ein Zeichen haben.", false);
			txtGastVorname.requestFocus();
		}
		if (txtGastVorname.getText().length() > PropertiesTable.MAXVORNAME) {
			new ErrorAlert("Vorname",
					"Der Vorname darf eine L�nge von " + PropertiesTable.MAXVORNAME + " Zeichen nicht �berschreiten.",
					false);
			txtGastVorname.requestFocus();
		}
		String vorname = txtGastVorname.getText();

		/**
		 * 
		 * @throws Ein ErrorAlert wird generiert, wenn der Nachname null, leer oder
		 *             gr��er als die maximal zul�ssige L�nge ist. Die L�nge ist in der
		 *             Klasse PropertiesTable definiert.
		 *
		 */

		if (txtGastNachname.getText() == null || txtGastNachname.getText().isEmpty()) {
			new ErrorAlert("Nachname", "Der Nachname muss mindestens ein Zeichen haben.", false);
			txtGastNachname.requestFocus();
		}
		if (txtGastNachname.getText().length() > PropertiesTable.MAXNACHNAME) {
			new ErrorAlert("Nachname",
					"Der Nachname darf eine L�nge von " + PropertiesTable.MAXNACHNAME + " Zeichen nicht �berschreiten.",
					false);
			txtGastNachname.requestFocus();
		}
		String nachname = txtGastNachname.getText();

		/**
		 * 
		 * @throws Ein ErrorAlert wird generiert, wenn der Geburtstag null oder der
		 *             buchende Gast nicht mindestens 18 Jahre alt ist.
		 *
		 * @param geburtsdatum Weist dem Geburtstag den �bergebenen Wert zu.
		 */

		// Geburtsdatum in einer neuen Variablen speichern, weil Period.between nicht
		// mit
		// einen DatePicker arbeiten kann.
		LocalDate geburtsdatum1 = dpGastGeburtsdatum.getValue();

		if (geburtsdatum1 == null) {
			new ErrorAlert("Geburtsdatum", "Das Geburtsdatum darf nicht leer sein.", false);
			dpGastGeburtsdatum.requestFocus();
		}

		Period p = Period.between(geburtsdatum1, LocalDate.now());
		if (p.getYears() < 18) {
			new ErrorAlert("Geburtsdatum", "Der buchende Gast muss �ber 18 jahre alt sein.", false);
			dpGastGeburtsdatum.requestFocus();
		}
		LocalDate geburtsdatum = dpGastGeburtsdatum.getValue();

		/**
		 * 
		 * @throws Ein ErrorAlert wird generiert, wenn die Strasse gr��er als die
		 *             maximal zul�ssige L�nge ist. Die L�nge ist in der Klasse
		 *             PropertiesTable definiert.
		 *
		 * @param strasse Weist der Strasse den �bergebenen Wert zu.
		 */

		if (txtGastStrasse.getText().length() > PropertiesTable.MAXSTRASSE) {
			new ErrorAlert("Strasse",
					"Die Strasse darf eine L�nge von " + PropertiesTable.MAXSTRASSE + " Zeichen nicht �berschreiten.",
					false);
			txtGastStrasse.requestFocus();
		}
		String strasse = txtGastStrasse.getText();

		/**
		 * 
		 * @throws Ein ErrorAlert wird generiert, wenn die HausNr gr��er als die maximal
		 *             zul�ssige L�nge ist. Die L�nge ist in der Klasse PropertiesTable
		 *             definiert.
		 *
		 * @param hausNr Weist der HausNr den �bergebenen Wert zu.
		 * 
		 */
		if (txtGastHausNr.getText().length() > PropertiesTable.MAXHAUSNR) {
			new ErrorAlert("HausNr",
					"Die HausNr darf eine L�nge von " + PropertiesTable.MAXHAUSNR + " Zeichen nicht �berschreiten.",
					false);
			txtGastHausNr.requestFocus();
		}
		String hausNr = txtGastHausNr.getText();

		/**
		 * 
		 * @throws Ein ErrorAlert wird generiert, wenn die Postleitzahl gr��er als die
		 *             maximal zul�ssige L�nge ist. Die L�nge ist in der Klasse
		 *             PropertiesTable definiert.
		 *
		 * @param plz Weist der Postleitzahl den �bergebenen Wert zu.
		 */
		if (txtGastPlz.getText().length() > PropertiesTable.MAXPLZ) {
			new ErrorAlert("HausNr",
					"Die HausNr darf eine L�nge von " + PropertiesTable.MAXPLZ + " Zeichen nicht �berschreiten.",
					false);
			txtGastPlz.requestFocus();
		}
		String plz = txtGastPlz.getText();

		/**
		 * 
		 * @throws Ein ErrorAlert wird generiert, wenn der Ort null, leer oder gr��er
		 *             als die maximal zul�ssige L�nge ist. Die L�nge ist in der Klasse
		 *             PropertiesTable definiert.
		 *
		 * @param ort Weist dem Ort den �bergebenen Wert zu.
		 */
		if (txtGastOrt.getText() == null || txtGastOrt.getText().isEmpty()) {
			new ErrorAlert("Ort", "Ort muss mindestens ein Zeichen haben.", false);
			txtGastOrt.requestFocus();
		}
		if (txtGastOrt.getText().length() > PropertiesTable.MAXORT) {
			new ErrorAlert("Ort", "Ort darf eine L�nge von " + PropertiesTable.MAXORT + " Zeichen nicht �berschreiten.",
					false);
			txtGastOrt.requestFocus();
		}
		String ort = txtGastOrt.getText();

		/**
		 * 
		 * @throws Ein ErrorAlert wird generiert, wenn das Land null, leer oder gr��er
		 *             als die maximal zul�ssige L�nge ist. Die L�nge ist in der Klasse
		 *             PropertiesTable definiert.
		 *
		 * @param land Weist dem Land den �bergebenen Wert zu.
		 */
		if (txtGastLand.getText() == null || txtGastLand.getText().isEmpty()) {
			new ErrorAlert("Land", "Das Land muss mindestens ein Zeichen haben.", false);
			txtGastLand.requestFocus();
		}
		if (txtGastLand.getText().length() > PropertiesTable.MAXLAND) {
			new ErrorAlert("Land",
					"Das Land darf eine L�nge von " + PropertiesTable.MAXLAND + " Zeichen nicht �berschreiten.", false);
			txtGastLand.requestFocus();
		}
		String land = txtGastLand.getText();

		/**
		 * 
		 * @throws Ein ErrorAlert wird generiert, wenn EMail gr��er als die maximal
		 *             zul�ssige L�nge ist. Die L�nge ist in der Klasse PropertiesTable
		 *             definiert.
		 *
		 * @param email Weist der EMail den �bergebenen Wert zu.
		 */
		if (txtGastEMail.getText().length() > PropertiesTable.MAXEMAIL) {
			new ErrorAlert("EMail",
					"Die EMail darf eine L�nge von " + PropertiesTable.MAXEMAIL + " Zeichen nicht �berschreiten.",
					false);
			txtGastEMail.requestFocus();
		}
		String email = txtGastEMail.getText();

		/**
		 * 
		 * @throws Ein ErrorAlert wird generiert, wenn die TelefonNr gr��er als die
		 *             maximal zul�ssige L�nge ist. Die L�nge ist in der Klasse
		 *             PropertiesTable definiert.
		 *
		 * @param telefon Weist der TelefonNr den �bergebenen Wert zu.
		 */
		if (txtGastTelefon.getText().length() > PropertiesTable.MAXTELEFONNUMBER) {
			new ErrorAlert("TelefonNr", "Die TelefonNr darf eine L�nge von " + PropertiesTable.MAXTELEFONNUMBER
					+ " Zeichen nicht �berschreiten.", false);
			txtGastTelefon.requestFocus();
		}
		String telefon = txtGastTelefon.getText();
		

		try {
			gast = new Gast(vorname, nachname, geburtsdatum, strasse, hausNr, plz, ort, land, email, telefon);
		} catch (IllegalArgumentException e) {
			new ErrorAlert("Eingabefehler", null, e.getMessage(), false);
			/*
			 * // Je nach Fehler entsprechendes Feld setzen if
			 * (e.getMessage().contains("Vorname")) { txtGastVorname.selectAll();
			 * txtGastVorname.requestFocus(); } else if
			 * (e.getMessage().contains("Nachname")) { txtGastNachname.selectAll();
			 * txtGastNachname.requestFocus(); } else if
			 * (e.getMessage().contains("Geburtsdatum")) {
			 * dpGastGeburtsdatum.requestFocus(); } else if (e.getMessage().contains("Ort")
			 * || e.getMessage().contains("Stadt")) { txtGastOrt.selectAll();
			 * txtGastOrt.requestFocus(); } else if (e.getMessage().contains("Land")) {
			 * txtGastLand.selectAll(); txtGastLand.requestFocus(); } return;
			 */
		}

		// Gast zum GastTabelle hinzuf�gen
		gastModel.addGast(gast);

		// Textfelder leeren
		txtGastVorname.setText("");
		txtGastNachname.setText("");
		dpGastGeburtsdatum.setValue(LocalDate.now());
		txtGastStrasse.setText("");
		txtGastHausNr.setText("");
		txtGastPlz.setText("");
		txtGastOrt.setText("");
		txtGastLand.setText("");
		txtGastEMail.setText("");
		txtGastTelefon.setText("");
	}

	@FXML
	void closeView(ActionEvent event) throws IOException {
		Parent mainForm = FXMLLoader.load(getClass().getResource("HotelZimmerSystemView.fxml"));
		Scene mainScene = new Scene(mainForm);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(mainScene);
		window.show();
	}

	@FXML
	void exitForm(MouseEvent event) {

	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {

		assert lblTitel != null : "fx:id=\"lblTitel\" was not injected: check your FXML file 'GastForm_add.fxml'.";
		assert lblGastVorname != null : "fx:id=\"lblGastVorname\" was not injected: check your FXML file 'GastForm_add.fxml'.";
		assert txtGastVorname != null : "fx:id=\"txtGastVorname\" was not injected: check your FXML file 'GastForm_add.fxml'.";
		assert lblGastNachname != null : "fx:id=\"lblGastNachname\" was not injected: check your FXML file 'GastForm_add.fxml'.";
		assert txtGastNachname != null : "fx:id=\"txtGastNachname\" was not injected: check your FXML file 'GastForm_add.fxml'.";
		assert lblGastEMail != null : "fx:id=\"lblGastEMail\" was not injected: check your FXML file 'GastForm_add.fxml'.";
		assert txtGastEMail != null : "fx:id=\"txtGastEMail\" was not injected: check your FXML file 'GastForm_add.fxml'.";
		assert lblGastTelefon != null : "fx:id=\"lblGastTelefon\" was not injected: check your FXML file 'GastForm_add.fxml'.";
		assert txtGastTelefon != null : "fx:id=\"txtGastTelefon\" was not injected: check your FXML file 'GastForm_add.fxml'.";
		assert dpGastGeburtsdatum != null : "fx:id=\"dpGastGeburtsdatum\" was not injected: check your FXML file 'GastForm_add.fxml'.";
		assert lblGastGeburtsdatum != null : "fx:id=\"lblGastGeburtsdatum\" was not injected: check your FXML file 'GastForm_add.fxml'.";
		assert txtGastStrasse != null : "fx:id=\"txtGastStrasse\" was not injected: check your FXML file 'GastForm_add.fxml'.";
		assert lblGastHausNr != null : "fx:id=\"lblGastHausNr\" was not injected: check your FXML file 'GastForm_add.fxml'.";
		assert lblGastStrasse != null : "fx:id=\"lblGastStrasse\" was not injected: check your FXML file 'GastForm_add.fxml'.";
		assert lblGastOrt != null : "fx:id=\"lblGastOrt\" was not injected: check your FXML file 'GastForm_add.fxml'.";
		assert txtGastOrt != null : "fx:id=\"txtGastOrt\" was not injected: check your FXML file 'GastForm_add.fxml'.";
		assert lblGastPlz != null : "fx:id=\"lblGastPlz\" was not injected: check your FXML file 'GastForm_add.fxml'.";
		assert lblGastLand != null : "fx:id=\"lblGastLand\" was not injected: check your FXML file 'GastForm_add.fxml'.";
		assert txtGastPlz != null : "fx:id=\"txtGastPlz\" was not injected: check your FXML file 'GastForm_add.fxml'.";
		assert txtGastLand != null : "fx:id=\"txtGastLand\" was not injected: check your FXML file 'GastForm_add.fxml'.";
		assert txtGastHausNr != null : "fx:id=\"txtGastHausNr\" was not injected: check your FXML file 'GastForm_add.fxml'.";
		assert txtAreaGastAnlegen != null : "fx:id=\"txtAreaGastAnlegen\" was not injected: check your FXML file 'GastFormadd.fxml'.";

		gastModel = new GastModel();
		
		final Tooltip tooltipbtnAddGast = new Tooltip();
		tooltipbtnAddGast.setText(
				"Folgende Felder m�ssen bef�llt sein:\r\n" + " Vorname, Nachname, Geburtsdatum, Ort und Land.");
		btnAddGast.setTooltip(tooltipbtnAddGast);
		
	}
}
