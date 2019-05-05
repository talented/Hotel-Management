package verwaltung;

import java.io.Serializable;
import java.sql.SQLException;
import java.time.LocalDate;
import model.ZimmerModel;

/**
 * Diese Klasse verwaltet die Buchung eines Zimmers. Die Klasse weist den G�sten
 * Zimmer zu. In Abh�nggikeit von An- und Abreise kann die Verf�gbarkeit gepr�ft
 * werden.
 * 
 * Die Klasse muss serialisierbar sein
 *
 */
public class Buchung implements Serializable {

	private static final long serialVersionUID = 4342957483204047836L;

	// Es werden die ben�tigten Variablen declariert
	private String buchungsNr; // Format: YYYY-X (x sind pos. int-Werte)
	private LocalDate anreise;
	private LocalDate abreise;
	private boolean fruehstueck; // Fr�hst�ck ist optional
	private int anzahlPersonen;
	private boolean kinderbett; // Kinderbett optional
	private int zimmerNr;
	private String zahlungsart;
	private int gastNr;
	private boolean storniert;

	// Gast, Zimmer Objekte werden declariert
	Gast gast;
	ZimmerModel zimmermodel;

	/**
	 * Konstruktor mit die initializierung von Gast und Zimmermodel
	 * 
	 * @throws SQLException
	 */
	public Buchung() throws SQLException {

		gast = new Gast();
		zimmermodel = new ZimmerModel();
	}

	/**
	 * �berladener Konstruktor, der die Variablen �bergeben bekommt und daraus das
	 * Objekt Buchung erzeugt
	 * 
	 * @param anreise        Anreisedatum des Gastes
	 * @param abreise        Abreisedatum des Gastes
	 * @param fruehstueck    M�glichkeit Fr�hst�ck zu w�hlen
	 * @param anzahlPersonen Anzahl Personen f�r diese Buchung
	 * @param kinderbett     M�glichkeit ein Kinderbett zu w�hlen
	 * @param zimmerNr       Gew�hlte Zimmernummer des Gastes
	 * @param zahlungsart    Zahlungsart des Gastes
	 * @param gastNr         Gastnummer
	 * @throws SQLException
	 */
	public Buchung(LocalDate anreise, LocalDate abreise, boolean fruehstueck, String zahlungsart, int anzahlPersonen,
			boolean kinderbett, int gastNr, int zimmerNr) {
		super();

		setAnreise(anreise);
		setAbreise(abreise);
		setFruehstueck(fruehstueck);
		setAnzahlPersonen(anzahlPersonen);
		setKinderbett(kinderbett);
		setZimmerNr(zimmerNr);
		setZahlungsart(zahlungsart);
		setGastNr(gastNr);
		this.storniert = false;
	}

	/**
	 * �berladener Konstruktor, der die Variablen �bergeben bekommt und daraus das
	 * Objekt Buchung erzeugt
	 * 
	 * @param buchungsNr     Buchungsnummer der Buchung
	 * @param anreise        Anreisedatum des Gastes
	 * @param abreise        Abreisedatum des Gastes
	 * @param fruehstueck    M�glichkeit Fr�hst�ck zu w�hlen
	 * @param anzahlPersonen Anzahl Personen f�r diese Buchung
	 * @param kinderbett     M�glichkeit ein Kinderbett zu w�hlen
	 * @param zimmerNr       Gew�hlte Zimmernummer des Gastes
	 * @param zahlungsart    Zahlungsart des Gastes
	 * @param gastNr         Gastnummer
	 * @param storniert      Etabliert ob die Buchung storniert ist
	 * @throws SQLException
	 */
	public Buchung(String buchungsNr, LocalDate anreise, LocalDate abreise, boolean fruehstueck, String zahlungsart,
			int anzahlPersonen, boolean kinderbett, int gastNr, int zimmerNr, boolean storniert) {
		super();

		setBuchungsNr(buchungsNr);
		setAnreise(anreise);
		setAbreise(abreise);
		setFruehstueck(fruehstueck);
		setAnzahlPersonen(anzahlPersonen);
		setKinderbett(kinderbett);
		setZimmerNr(zimmerNr);
		setZahlungsart(zahlungsart);
		setGastNr(gastNr);
		setStorniert(storniert);
	}

	/**
	 * Getter f�r die Buchungsnummer
	 * 
	 * @return Gibt die Buchungsnummer zur�ck
	 */
	public String getBuchungsNr() {

		return buchungsNr;
	}

	/**
	 * Getter f�r das Anreisedatum
	 * 
	 * @return Gibt das Anreisedatum zur�ck
	 */
	public LocalDate getAnreise() {
		return anreise;
	}

	/**
	 * Getter f�r das Abreisedatum
	 * 
	 * @return Gibt das Abreisedatum zur�ck
	 */
	public LocalDate getAbreise() {
		return abreise;
	}

	/**
	 * Getter f�r das optionale Fr�hst�ck
	 * 
	 * @return Gibt true oder false zur�ck
	 */
	public boolean isFruehstueck() {
		return fruehstueck;
	}

	/**
	 * Getter f�r die Personenanzahl, die in dem Zimmer �bernachten
	 * 
	 * @return Gibt die Anzahl der Personen zur�ck
	 */
	public int getAnzahlPersonen() {
		return anzahlPersonen;
	}

	/**
	 * Getter f�r das optionale Kinderbett
	 * 
	 * @return Gibt true oder false zur�ck
	 */
	public boolean isKinderbett() {
		return kinderbett;
	}

	/**
	 * Getter f�r die Zimmernummer
	 * 
	 * @return Gibt die Zimmernummer zur�ck
	 */
	public int getZimmerNr() {
		return zimmerNr;
	}

	/**
	 * Getter f�r die Zahlungsart
	 * 
	 * @return Gibt die Zahlungsart zur�ck
	 */
	public String getZahlungsart() {
		return zahlungsart;
	}

	/**
	 * Getter f�r die gastNr
	 * 
	 * @return Gibt die Information �ber den Gast zur�ck
	 */
	public int getGastNr() {
		return gastNr;
	}

	/**
	 * Getter f�r der Storno der Buchung
	 * 
	 * @return Gibt true oder false zur�ck
	 */
	public boolean isStorniert() {
		return storniert;
	}

	/**
	 * Diese Methode setzt die Buchungsnummer.
	 * 
	 * @param buchungsNr
	 */
	public void setBuchungsNr(String buchungsNr) {
		this.buchungsNr = buchungsNr;
	}

	/**
	 * Diese Methode setzt die Anreise.
	 * 
	 * @param anreise
	 */
	public void setAnreise(LocalDate anreise) {
		this.anreise = anreise;

	}

	/**
	 * Diese Methode setzt die Abreise.
	 * 
	 * @param abreise
	 */
	public void setAbreise(LocalDate abreise) {
		this.abreise = abreise;
	}

	/**
	 * Diese Methode setzt das optionale Fr�hst�ck als boolean.
	 * 
	 * @param fruehstueck
	 */
	public void setFruehstueck(boolean fruehstueck) {
		this.fruehstueck = fruehstueck;
	}

	/**
	 * Diese Methode setzt die Anzahl der Personen f�r die Buchung.
	 * 
	 * @param anzahlPersonen
	 */
	public void setAnzahlPersonen(int anzahlPersonen) {
		this.anzahlPersonen = anzahlPersonen;

	}

	/**
	 * Diese Methode setzt die option Kinderbett.
	 * 
	 * @param kinderbett
	 */
	public void setKinderbett(boolean kinderbett) {
		this.kinderbett = kinderbett;
	}

	/**
	 * Diese Methode setzt die Zimmernummer f�r diese Buchung.
	 * 
	 * @param zimmerNr
	 */
	public void setZimmerNr(int zimmerNr) {
		this.zimmerNr = zimmerNr;
	}

	/**
	 * Diese Methode setzt die Zahlungsart.
	 * 
	 * @param zahlungsart
	 */
	public void setZahlungsart(String zahlungsart) {
		this.zahlungsart = zahlungsart;

	}

	/**
	 * Diese Methode setzt die Gastnummer.
	 * 
	 * @param gastNr
	 */
	public void setGastNr(int gastNr) {
		this.gastNr = gastNr;
	}

	/**
	 * Diese Methode setzt ob die Buchung storniert ist oder nicht
	 * 
	 * @param storniert
	 */
	public void setStorniert(boolean storniert) {
		this.storniert = storniert;
	}

	/**
	 * Gibt alle daten von Buchung in ein String, wenn die Buchungsnummer ist nicht
	 * null
	 * 
	 */
	public String getAll() {

		String result = String.format(" Buchungsnummer: " + this.getBuchungsNr() + " Anreise: " + this.getAnreise()
				+ " Abreise: " + this.getAbreise() + " Fr�hst�ck: " + this.isFruehstueck() + " Anzahl Personen: "
				+ this.getAnzahlPersonen() + " Kinderbett: " + this.isKinderbett() + " Zimmernummer: "
				+ this.getZimmerNr() + " Zahlungsart: " + this.getZahlungsart() + " Gastnummer: " + this.getGastNr()
				+ " Storniert: " + this.isStorniert() + "\n");
		return result;

	}
}
