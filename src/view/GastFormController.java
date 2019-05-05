package view;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

import verwaltung.Gast;
import util.PropertiesTable;
import view.util.ErrorAlert;
import view.util.InformationAlert;
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

public class GastFormController {

	GastModel gastModel;

	@FXML // fx:id="lblGastNr"
	private Label lblGastNr; // Value injected by FXMLLoader

	@FXML // fx:id="lblTitel"
	private Label lblTitel; // Value injected by FXMLLoader

	@FXML // fx:id="lbl_gast_Vorname"
	private Label lblGastVorname; // Value injected by FXMLLoader

	@FXML // fx:id="txt_gast_Vorname"
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

	@FXML // fx:id="btnEditGast"
	private Button btnEditGast; // Value injected by FXMLLoader

	@FXML // fx:id="btnSearchGast"
	private Button btnSearchGast; // Value injected by FXMLLoader

	@FXML
	private Button btnZurueck;

	@FXML
	private TextArea txtAreaGastAendern;

	@FXML
	private TextArea txtAreaGastSuchen;

	@FXML
	private Button btnLeer;

	@FXML
	void closeView(ActionEvent event) {
		try {
			Parent mainForm = FXMLLoader.load(getClass().getResource("HotelZimmerSystemView.fxml"));
			Scene mainScene = new Scene(mainForm);
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(mainScene);
			window.show();
		}catch (IOException e) {
			new ErrorAlert(null,e.getMessage(), false);
		}
		
	}

	@FXML
	void editGast(ActionEvent event){

		int gastNr = Integer.parseInt(txtGastNr.getText());

		/**
		 * 
		 * Ein ErrorAlert wird generiert, wenn der Vorname null, leer oder
		 *             gr��er als die maximal zul�ssige L�nge ist. Die L�nge ist in der
		 *             Klasse PropertiesTable definiert.
		 * 
		 * @param vorname Weist dem Vorname den �bergebenen Wert zu.
		 */
		String vorname = txtGastVorname.getText();
		if (vorname == null || vorname.isEmpty()) {
			new ErrorAlert("Vorname", "Der Vorname muss mindestens ein Zeichen haben.", false);
			txtGastVorname.selectAll();
			txtGastVorname.requestFocus();
		}
		else if (vorname.length() > PropertiesTable.MAXVORNAME) {
			new ErrorAlert("Vorname",
					"Der Vorname darf eine L�nge von " + PropertiesTable.MAXVORNAME + " Zeichen nicht �berschreiten.",false);
			txtGastVorname.selectAll();
			txtGastVorname.requestFocus();
		}
			


		/**
		 * 
		 * Ein ErrorAlert wird generiert, wenn der Nachname null, leer oder
		 *             gr��er als die maximal zul�ssige L�nge ist. Die L�nge ist in der
		 *             Klasse PropertiesTable definiert.
		 * @param nachname Weist dem nachname den �bergebenen Wert zu.
		 */
		String nachname = txtGastNachname.getText();
		if (nachname == null || nachname.isEmpty()) {
			new ErrorAlert("Nachname", "Der Nachname muss mindestens ein Zeichen haben.", false);
			txtGastNachname.selectAll();
			txtGastNachname.requestFocus();
		}else if (nachname.length() > PropertiesTable.MAXNACHNAME) {
			new ErrorAlert("Nachname",
					"Der Nachname darf eine L�nge von " + PropertiesTable.MAXNACHNAME + " Zeichen nicht �berschreiten.",false);
			txtGastNachname.selectAll();
			txtGastNachname.requestFocus();
		}
		

		/**
		 * 
		 * Ein ErrorAlert wird generiert, wenn der Geburtstag null oder der
		 *             buchende Gast nicht mindestens 18 Jahre alt ist.
		 *
		 * @param geburtsdatum Weist dem Geburtstag den �bergebenen Wert zu.
		 */

		// Geburtsdatum in einer neuen Variablen speichern, weil Period.between nicht
		// mit einen DatePicker arbeiten kann.
		dpGastGeburtsdatum.setValue(dpGastGeburtsdatum.getConverter().fromString(dpGastGeburtsdatum.getEditor().getText()));
		LocalDate geburtsdatum = dpGastGeburtsdatum.getValue();

		if (geburtsdatum == null) {
			new ErrorAlert("Geburtsdatum", "Das Geburtsdatum darf nicht leer sein.", false);
			dpGastGeburtsdatum.requestFocus();
		}

		Period p = Period.between(geburtsdatum, LocalDate.now());
		if (p.getYears() < 18) {
			new ErrorAlert("Geburtsdatum", "Der buchende Gast muss �ber 18 jahre alt sein.", false);
			dpGastGeburtsdatum.requestFocus();
		}
		
		/**
		 * 
		 * Ein ErrorAlert wird generiert, wenn die Strasse gr��er als die
		 *             maximal zul�ssige L�nge ist. Die L�nge ist in der Klasse
		 *             PropertiesTable definiert.
		 *
		 * @param strasse Weist der Strasse den �bergebenen Wert zu.
		 */
		String strasse = txtGastStrasse.getText();
		if (strasse.length() > PropertiesTable.MAXSTRASSE) {
			new ErrorAlert("Strasse",
					"Die Strasse darf eine L�nge von " + PropertiesTable.MAXSTRASSE + " Zeichen nicht �berschreiten.",false);
			txtGastStrasse.selectAll();
			txtGastStrasse.requestFocus();
		}
		

		/**
		 * 
		 * Ein ErrorAlert wird generiert, wenn die HausNr gr��er als die maximal
		 *             zul�ssige L�nge ist. Die L�nge ist in der Klasse PropertiesTable
		 *             definiert.
		 *
		 * @param hausNr Weist der HausNr den �bergebenen Wert zu.
		 * 
		 */
		String hausNr = txtGastHausNr.getText();
		if (hausNr.length() > PropertiesTable.MAXHAUSNR) {
			new ErrorAlert("HausNr",
					"Die HausNr darf eine L�nge von " + PropertiesTable.MAXHAUSNR + " Zeichen nicht �berschreiten.",false);
			txtGastHausNr.selectAll();
			txtGastHausNr.requestFocus();
		}
		

		/**
		 * 
		 * Ein ErrorAlert wird generiert, wenn die Postleitzahl gr��er als die
		 *             maximal zul�ssige L�nge ist. Die L�nge ist in der Klasse
		 *             PropertiesTable definiert.
		 *
		 * @param plz Weist der Postleitzahl den �bergebenen Wert zu.
		 */
		String plz = txtGastPlz.getText();
		if (plz.length() > PropertiesTable.MAXPLZ) {
			new ErrorAlert("PLZ",
					"Die PLZ darf eine L�nge von " + PropertiesTable.MAXPLZ + " Zeichen nicht �berschreiten.",false);
			txtGastPlz.selectAll();
			txtGastPlz.requestFocus();
		}
		

		/**
		 * 
		 * Ein ErrorAlert wird generiert, wenn der Ort null, leer oder gr��er
		 *             als die maximal zul�ssige L�nge ist. Die L�nge ist in der Klasse
		 *             PropertiesTable definiert.
		 *
		 * @param ort Weist dem Ort den �bergebenen Wert zu.
		 */
		String ort = txtGastOrt.getText();
		if (ort == null || ort.isEmpty()) {
			new ErrorAlert("Ort", "Ort muss mindestens ein Zeichen haben.", false);
			txtGastOrt.selectAll();
			txtGastOrt.requestFocus();
		}
		if (ort.length() > PropertiesTable.MAXORT) {
			new ErrorAlert("Ort", "Ort darf eine L�nge von " + PropertiesTable.MAXORT + " Zeichen nicht �berschreiten.",false);
			txtGastOrt.selectAll();
			txtGastOrt.requestFocus();
		}
		

		/**
		 * 
		 * Ein ErrorAlert wird generiert, wenn das Land null, leer oder gr��er
		 *             als die maximal zul�ssige L�nge ist. Die L�nge ist in der Klasse
		 *             PropertiesTable definiert.
		 *
		 * @param land Weist dem Land den �bergebenen Wert zu.
		 */
		String land = txtGastLand.getText();
		if (land == null || land.isEmpty()) {
			new ErrorAlert("Land", "Das Land muss mindestens ein Zeichen haben.", false);
			txtGastLand.selectAll();
			txtGastLand.requestFocus();
		}
		if (land.length() > PropertiesTable.MAXLAND) {
			new ErrorAlert("Land",
					"Das Land darf eine L�nge von " + PropertiesTable.MAXLAND + " Zeichen nicht �berschreiten.", false);
			txtGastLand.selectAll();
			txtGastLand.requestFocus();
		}
		

		/**
		 * 
		 * Ein ErrorAlert wird generiert, wenn EMail gr��er als die maximal
		 *             zul�ssige L�nge ist. Die L�nge ist in der Klasse PropertiesTable
		 *             definiert.
		 *
		 * @param email Weist der EMail den �bergebenen Wert zu.
		 */
		String email = txtGastEMail.getText();
		if (email.length() > PropertiesTable.MAXEMAIL) {
			new ErrorAlert("EMail",
					"Die EMail darf eine L�nge von " + PropertiesTable.MAXEMAIL + " Zeichen nicht �berschreiten.",
					false);
			txtGastEMail.selectAll();
			txtGastEMail.requestFocus();
		}
		

		/**
		 * 
		 * Ein ErrorAlert wird generiert, wenn die TelefonNr gr��er als die
		 *             maximal zul�ssige L�nge ist. Die L�nge ist in der Klasse
		 *             PropertiesTable definiert.
		 *
		 * @param telefon Weist der TelefonNr den �bergebenen Wert zu.
		 */
		String telefon = txtGastTelefon.getText();
		if (telefon.length() > PropertiesTable.MAXTELEFONNUMBER) {
			new ErrorAlert("TelefonNr", "Die TelefonNr darf eine L�nge von " + PropertiesTable.MAXTELEFONNUMBER
					+ " Zeichen nicht �berschreiten.", false);
			txtGastTelefon.selectAll();
			txtGastTelefon.requestFocus();
		}

		Gast gast;

		try {
			gast = new Gast(gastNr, vorname, nachname, geburtsdatum, strasse, hausNr, plz, ort, land, email, telefon);
		} catch (IllegalArgumentException e) {
			//new ErrorAlert("Eingabefehler", null, e.getMessage(), false);

			// Je nach Fehler entsprechendes Feld setzen
			if (e.getMessage().contains("Vorname")) {
				txtGastVorname.selectAll();
				txtGastVorname.requestFocus();
			} else if (e.getMessage().contains("Nachname")) {
				txtGastNachname.selectAll();
				txtGastNachname.requestFocus();
			} else if (e.getMessage().contains("Geburtsdatum")) {
				dpGastGeburtsdatum.requestFocus();
			} else if (e.getMessage().contains("Ort") || e.getMessage().contains("Stadt")) {
				txtGastOrt.selectAll();
				txtGastOrt.requestFocus();
			} else if (e.getMessage().contains("Land")) {
				txtGastLand.selectAll();
				txtGastLand.requestFocus();
			}

			return;
		}

		Gast result = null;
		try {
			result = gastModel.updateGast(gast);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (result.getGastNr() == gast.getGastNr()) {
			new InformationAlert("aktualisieren","Der gast erfolgreich aktualisiert");
		}else {
			new ErrorAlert("aktualisieren", null,"Der gast aktualisiert nicht" , true);
		}
	}

	@FXML
	void exitForm(MouseEvent event) {

	}

	
	@FXML
	void searchGast(ActionEvent event) throws SQLException {
		Gast gast = new Gast();
    	if (!txtGastNr.getText().isEmpty()){
    		try {
    			
    			int gastNr = Integer.parseInt(txtGastNr.getText());
    			gast = gastModel.getGastByGastNr(gastNr);
    			if (gastNr != gast.getGastNr()) {
    				new ErrorAlert("Eingabefehler", null, "Diese Gastnummer ist falsch!", false);
    			} else {
    				anzeigen(gast);
    			}
    		}
    		catch (NumberFormatException e) {
    			new ErrorAlert("Eingabefehler", null, "Der Gastnummer muss nur Nummer ohne Charakter sein!", false);
    		}
    	}else if (!txtGastVorname.getText().isEmpty() && !txtGastNachname.getText().isEmpty() && !dpGastGeburtsdatum.getEditor().getText().isEmpty() ) {	
    		String vorname = txtGastVorname.getText();
    		String nachname = txtGastNachname.getText();
    		dpGastGeburtsdatum.setValue(dpGastGeburtsdatum.getConverter().fromString(dpGastGeburtsdatum.getEditor().getText()));
    		LocalDate geburtsDatum = dpGastGeburtsdatum.getValue();
    		gast = gastModel.getGast(vorname, nachname, geburtsDatum);
    		if (!vorname.equals(gast.getVorname()) && !nachname.equals(gast.getNachname()) && !geburtsDatum.equals(gast.getGeburtsdatum())) {
    			new ErrorAlert("Eingabefehler", null, "Diese Gastinformation ist falsch!", false);
    		} else {
    			anzeigen(gast);
    		}
    	}else {
    		new ErrorAlert("Eingabefehler", null, "Folgende Suchkriterien sind m�glich:\n" + 
    				" - Gast-Nr.\n" + 
    				" oder\n" + 
    				"- Vorname, Nachname, Geburtsdatum.", false);
    	}
	}

	/**
	 * Zeigt den aktuell in der internen Datenstruktur ausgew�hlten Wert in der Oberfl�che an
	 * Die Buttons btnSearchGast und TextField txtGastNr werden je nach Position deaktiviert.
	 * Die Buttons btnLeer und btnEditGast werden je nach Position aktiviert.
	 */
	private void anzeigen(Gast gast) {

		txtGastNr.setText(gast.getGastNr().toString());
		txtGastVorname.setText(gast.getVorname());
		txtGastNachname.setText(gast.getNachname());
		dpGastGeburtsdatum.setValue(gast.getGeburtsdatum());
		txtGastStrasse.setText(gast.getStrasse());
		txtGastHausNr.setText(gast.getHausNr());
		txtGastPlz.setText(gast.getPlz());
		txtGastOrt.setText(gast.getOrt());
		txtGastLand.setText(gast.getLand());
		txtGastEMail.setText(gast.getEmail());
		txtGastTelefon.setText(gast.getTelefon());
		
		btnSearchGast.setDisable(true);
    	btnLeer.setDisable(false);
    	txtGastNr.setDisable(true);
    	btnEditGast.setDisable(false);
    	deaktiviert();
	}

	/**
	 * Die TextField txtGastOrt,txtGastStrasse,txtGastHausNr,txtGastPlz,txtGastLand,txtGastEMail und txtGastTelefon werden je nach Position aktiviert.
	 */
	private void deaktiviert() {

		txtGastOrt.setDisable(false);
    	txtGastStrasse.setDisable(false);
    	txtGastHausNr.setDisable(false);
    	txtGastPlz.setDisable(false);
    	txtGastLand.setDisable(false);
    	txtGastEMail.setDisable(false);
    	txtGastTelefon.setDisable(false);
	}
	
	/**
	 * Die TextField txtGastOrt,txtGastStrasse,txtGastHausNr,txtGastPlz,txtGastLand,txtGastEMail und txtGastTelefon werden je nach Position deaktiviert.
	 */
	private void aktiviert() {

		txtGastOrt.setDisable(true);
    	txtGastStrasse.setDisable(true);
    	txtGastHausNr.setDisable(true);
    	txtGastPlz.setDisable(true);
    	txtGastLand.setDisable(true);
    	txtGastEMail.setDisable(true);
    	txtGastTelefon.setDisable(true);
	}
	
	@FXML
	void leerTextField() {
		// Textfelder leeren
		txtGastNr.setText("");
		txtGastVorname.setText("");
		txtGastNachname.setText("");
		dpGastGeburtsdatum.setValue(null);
		txtGastStrasse.setText("");
		txtGastHausNr.setText("");
		txtGastPlz.setText("");
		txtGastOrt.setText("");
		txtGastLand.setText("");
		txtGastEMail.setText("");
		txtGastTelefon.setText("");
		btnSearchGast.setDisable(false);
		btnLeer.setDisable(true);
		txtGastNr.setDisable(false);
		btnEditGast.setDisable(true);
		aktiviert();
	}

	@FXML
	void initialize() {
		assert lblGastNr != null : "fx:id=\"lblGastNr\" was not injected: check your FXML file 'GastForm.fxml'.";
		assert btnZurueck != null : "fx:id=\"btnZurueck\" was not injected: check your FXML file 'GastForm.fxml'.";
		assert btnEditGast != null : "fx:id=\"btnEditGast\" was not injected: check your FXML file 'GastForm.fxml'.";
		assert btnSearchGast != null : "fx:id=\"btnSearchGast\" was not injected: check your FXML file 'GastForm.fxml'.";
		assert lblTitel != null : "fx:id=\"lblTitel\" was not injected: check your FXML file 'GastForm.fxml'.";
		assert lblGastVorname != null : "fx:id=\"lblGastVorname\" was not injected: check your FXML file 'GastForm.fxml'.";
		assert txtGastVorname != null : "fx:id=\"txtGastVorname\" was not injected: check your FXML file 'GastForm.fxml'.";
		assert lblGastNachname != null : "fx:id=\"lblGastNachname\" was not injected: check your FXML file 'GastForm.fxml'.";
		assert txtGastNachname != null : "fx:id=\"txtGastNachname\" was not injected: check your FXML file 'GastForm.fxml'.";
		assert lblGastEMail != null : "fx:id=\"lblGastEMail\" was not injected: check your FXML file 'GastForm.fxml'.";
		assert txtGastEMail != null : "fx:id=\"txtGastEMail\" was not injected: check your FXML file 'GastForm.fxml'.";
		assert lblGastTelefon != null : "fx:id=\"lblGastTelefon\" was not injected: check your FXML file 'GastForm.fxml'.";
		assert txtGastTelefon != null : "fx:id=\"txtGastTelefon\" was not injected: check your FXML file 'GastForm.fxml'.";
		assert dpGastGeburtsdatum != null : "fx:id=\"dpGastGeburtsdatum\" was not injected: check your FXML file 'GastForm.fxml'.";
		assert lblGastGeburtsdatum != null : "fx:id=\"lblGastGeburtsdatum\" was not injected: check your FXML file 'GastForm.fxml'.";
		assert txtGastStrasse != null : "fx:id=\"txtGastStrasse\" was not injected: check your FXML file 'GastForm.fxml'.";
		assert lblGastHausNr != null : "fx:id=\"lblGastHausNr\" was not injected: check your FXML file 'GastForm.fxml'.";
		assert lblGastStrasse != null : "fx:id=\"lblGastStrasse\" was not injected: check your FXML file 'GastForm.fxml'.";
		assert lblGastOrt != null : "fx:id=\"lblGastOrt\" was not injected: check your FXML file 'GastForm.fxml'.";
		assert txtGastOrt != null : "fx:id=\"txtGastOrt\" was not injected: check your FXML file 'GastForm.fxml'.";
		assert lblGastPlz != null : "fx:id=\"lblGastPlz\" was not injected: check your FXML file 'GastForm.fxml'.";
		assert lblGastLand != null : "fx:id=\"lblGastLand\" was not injected: check your FXML file 'GastForm.fxml'.";
		assert txtGastPlz != null : "fx:id=\"txtGastPlz\" was not injected: check your FXML file 'GastForm.fxml'.";
		assert txtGastLand != null : "fx:id=\"txtGastLand\" was not injected: check your FXML file 'GastForm.fxml'.";
		assert txtGastHausNr != null : "fx:id=\"txtGastHausNr\" was not injected: check your FXML file 'GastForm.fxml'.";
		assert txtGastNr != null : "fx:id=\"txtGastNr\" was not injected: check your FXML file 'GastForm.fxml'.";
		assert txtAreaGastAendern != null : "fx:id=\"txtAreaGastAendern\" was not injected: check your FXML file 'GastForm.fxml'.";
		assert txtAreaGastSuchen != null : "fx:id=\"txtAreaGastSuchen\" was not injected: check your FXML file 'GastForm.fxml'.";
		assert btnLeer != null : "fx:id=\"btnLeer\" was not injected: check your FXML file 'GastForm.fxml'.";

		gastModel = new GastModel();

		if (txtGastVorname.getText().isEmpty() || txtGastNachname.getText().isEmpty()
				|| txtGastNr.getText().isEmpty()) {
			btnSearchGast.setDisable(false);
			btnLeer.setDisable(true);
			txtGastNr.setDisable(false);
			btnEditGast.setDisable(true);
			aktiviert();
		} else {
			btnSearchGast.setDisable(true);
			btnLeer.setDisable(false);
			txtGastNr.setDisable(true);
			btnEditGast.setDisable(false);
			deaktiviert();
		}

		final Tooltip tooltipbtnEditGast = new Tooltip();
		tooltipbtnEditGast.setText(
				"Folgende Felder m�ssen bef�llt sein:\r\n" + " Vorname, Nachname, Geburtsdatum, Ort und Land.");
		btnEditGast.setTooltip(tooltipbtnEditGast);

		final Tooltip tooltipbtnSearchGast = new Tooltip();
		tooltipbtnSearchGast.setText("Folgende Suchkriterien sind m�glich:\r\n" + "       - Gast-Nr.\r\n"
				+ " oder\r\n" + "       - Vorname, Nachname, Geburtsdatum.");
		btnSearchGast.setTooltip(tooltipbtnSearchGast);

	}
}
