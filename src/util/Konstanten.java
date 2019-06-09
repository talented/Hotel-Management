package util;



/**
 * @author Team Hotelverwaltung
 * 
 *Es werden die wesentlichen KONSTANTEN deklariert und initialisiert.
 *
 *@param EZ_ANZAHL ist die Anzahl der vorhandenen Einzelzimmer.
 *@param DZ_ANZAHL ist die Anzahl der vorhandenen Doppelzimmer.
 *@param FZ_ANZAHL ist die Anzahl der vorhandenen Familienzimmer;
 *@param SUITE_ANZAHL ist die Anzahl der vorhandenen Suiten.
 *
 *@param EZ_BETTEN ist die Anzahl der vorhandenen Betten pro Einzelzimmer.
 *@param DZ_BETTEN ist die Anzahl der vorhandenen Betten pro Doppelzimmer.
 *@param FZ_BETTEN ist die Anzahl der vorhandenen Betten pro Familienzimmer.
 *@param SUITE_BETTEN ist die Anzahl der vorhandenen Betten pro Suite.
 *
 *@param MAX_KINDERBETT_ZIMMER ist die maximale Anzahl an zubuchbaren Kinderbetten pro zimmer.
 *
 *@param EZ_PREIS ist der Preis pro Einzelzimmer pro Nacht.
 *@param DZ_PREIS ist der Preis pro Doppelzimmer pro Nacht.
 *@param FZ_PREIS ist der Preis pro Familienzimmer pro Nacht.
 *@param SUITE_PREIS ist der Preis pro Suite pro Nacht.
 *
 *@param KINDERBETTEN_ANZ ist die Anzahl der im Hotel vorhandenen Kinderbetten.
 *@param KINDERBETT_PREIS ist der Preis f�r ein zugebuchtes Kinderbett pro Nacht.
 *
 *@param FRUEHSTUECK_PREIS ist der Preis f�r ein Fr�hst�ck pro Person.
 *
 *@param RECHNUNGSVERMERK ist eine Konstante, die auf der Rechnung "Bezahlt" vermerken kann.
 *
 *
 *Information �ber  Hotel
 *
 *@param HOTELNAME ist der Name des Hotels
 *@param HOTELADRESSE ist die Adresse Hotells
 *@param HOTELADRESSEPLZ ist der PLZ des Hotels
 *@param HOTELADRESSELAND ist das Land des Hotels
 *@param HOTELZALUNGDATEN  ist die Zahlungsdaten des Hotels
 *@param HOTELTELEFONNR ist die telefonische Nummer des Hotels
 *@param HOTELEMAIL ist Email des Hotels
 *@param HOTELINTERNET ist die Webadresse Hotels
 *
 *Die Hotelzimmer Beschreibung
 *
 *@param HOTELZIMMERKATEGORY_EZ ist die Beschreibung des Einsbettzimmers
 *@param HOTELZIMMERKATEGORY_DZ ist die Beschreibung des Doppeltbettzimmers
 *@param HOTELZIMMERKATEGORY_FZ ist die Beschreibung des Familienzimmers
 *@param HOTELZIMMERKATEGORY_SUITE ist die Beschreibung des Luxiszimmers
 *
 *@param PAHT ist der Name des Verzeichnesses wo werden die generierte Rechnungsdatein geschpeichert
 *
 *Font Size 
 *
 *@param DEFAULT_FONT_SIZE ist Fondsize als Voreinstellug
 *@param DEFAULT_FONT_NAME ist Fondname als Voreinstellug
 *@param H1 ist Fondsize
 *@param H2 ist Fondsize
 *@param H3 ist Fondsize
 *@param H4 ist Fondsize
 *@param H5 ist Fondsize
 */




public class Konstanten {
	public static final String EZ_NAME = "Einzelzimmer";
	public static final String DZ_NAME = "Doppelzimmer";
	public static final String FZ_NAME = "Familienzimmer";
	public static final String SUITE_NAME = "Business-Suite";
	
	public static final int EZ_ANZAHL = 5;
	public static final int DZ_ANZAHL = 5;
	public static final int FZ_ANZAHL = 3;
	public static final int SUITE_ANZAHL = 2;
	
	public static final int EZ_BETTEN = 1;
	public static final int DZ_BETTEN = 2;
	public static final int FZ_BETTEN = 4;
	public static final int SUITE_BETTEN = 2;
	
	public static final int MAX_KINDERBETT_ZIMMER = 1;
	
	public static final int EZ_PREIS = 80;
	public static final int DZ_PREIS = 120;
	public static final int FZ_PREIS = 175;
	public static final int SUITE_PREIS = 150;
	
	public static final int KINDERBETTEN_ANZ = 10;
	public static final int KINDERBETT_PREIS = 5;
	
	public static final int FRUEHSTUECK_PREIS = 10;
	
	public static final String RECHNUNGSVERMERK = "Bezahlt";
	
	public static final String HOTELNAME = "alfatraining GmbH";
	public static final String HOTELADRESSE = "Kriegsstr. 100";
	public static final String HOTELADRESSEPLZ = "76133 Karlsruhe";
	public static final String HOTELADRESSELAND = "Deutschland";
	public static final String HOTELZALUNGDATEN = "IBAN DE92 6605 01080792 11";
	public static final String HOTELTELEFONNR = "Tel.0721 35450 -0";
	public static final String HOTELEMAIL = "info.alfatraining.de";
	public static final String HOTELINTERNET = "www.alfatraining.de";

	public static final String HOTELZIMMERKATEGORY_EZ = "Einzeizimmer (mit kostenfreiem W-LAN, Badewanne/WC)";
	public static final String HOTELZIMMERKATEGORY_DZ = "Doppelbettzimmer (mit kostenfreiem W-LAN, Badewanne/WC)";
	public static final String HOTELZIMMERKATEGORY_FZ = "FamilienZimmer (extra grosse Zimmer mit kostenfreiem W-LAN, Badewanne/WC)";
	public static final String HOTELZIMMERKATEGORY_SUITE = "Suite  in der größe zwischen 50 - 55 m2 hat ein Schlafzimmer, Wohnzimmer (mit kostenfreiem W-LAN, Badewanne/WC)";

	public static final int H1 = 36;
	public static final int H2 = 24;
	public static final int H3 = 12;
	public static final int H4 = 11;
	public static final int H5 = 9;
	public static final int DEFAULT_FONT_SIZE = 10;
	public static final String DEFAULT_FONT_NAME = "Arial";

	public static final String PATH = "Z:\\Oezguer_Yarikkas";
	public static final String FORMATIER = "yyyy-MM-dd";
}
