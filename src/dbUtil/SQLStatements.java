package dbUtil;

import util.Konstanten;

public class SQLStatements {
	
	// Versionierung
	static final int DB_VERSION = 1;
	
	// Auslesen und Setzen der Datenbankversion
	static final String GET_DB_VERSION = "PRAGMA user_version";
	static final String SET_DB_VERSION = "PRAGMA user_version = ";

	// +-------------------------------------------------------------------------------------------------------------------------+
	// |                                                  VERSION 1																 |                                                             
	// +-------------------------------------------------------------------------------------------------------------------------+
	// Erstellen der Tabellen
	
	
	static final String CREATE_TABLE_BUCHUNG = "CREATE TABLE IF NOT EXISTS \"Buchung\" "+
			"(id             INTEGER      PRIMARY KEY AUTOINCREMENT NOT NULL, " + 
			"buchungsNr     VARCHAR (50) NOT NULL, " + 
			"anreise        TEXT         NOT NULL, " + 
			"abreise        TEXT         NOT NULL, " + 
			"fruehstueck    BOOLEAN      NOT NULL, " + 
			"zahlungsart    VARCHAR (50) NOT NULL, " + 
			"anzahlPersonen INT          NOT NULL, " + 
			"kinderbett     BOOLEAN      NOT NULL, " + 
			"gastNr         INT          NOT NULL, " + 
			"zimmerNr       INT          NOT NULL, " + 
			"storniert      BOOLEAN      DEFAULT (false));";
	
	static final String CREATE_TABLE_GAST = "CREATE TABLE IF NOT EXISTS \"Gast\" " + 
			"(gastNr       INTEGER       NOT NULL PRIMARY KEY AUTOINCREMENT, " + 
			" vorname      VARCHAR (50)  NOT NULL, " + 
			" nachname     VARCHAR (50)  NOT NULL, " + 
			" geburtsdatum TEXT          NOT NULL, " + 
			" strasse      VARCHAR (255), " + 
			" hausNr       VARCHAR (10), " + 
			" plz          VARCHAR (10), " + 
			" stadt        VARCHAR (50), " + 
			" land         VARCHAR (50)  NOT NULL, " + 
			" email        VARCHAR (50), " + 
			" telefon      VARCHAR (50));";
	
	static final String CREATE_TABLE_ZIMMER = "CREATE TABLE IF NOT EXISTS \"zimmer\" " + 
			"(zimmerNr    INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + 
			" kategorieNr INT );";
	
	static final String CREATE_TABLE_ZIMMERKATEGORIE = "CREATE TABLE IF NOT EXISTS \"zimmerKategorie\" " + 
			"(kategorieNr   INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + 
			" name          VARCHAR (50), " + 
			" anzahlBetten  INT, " + 
			" preisProNacht DOUBLE);";
	
	// Insert f�r Zimmerkategorien
	static final String INSERT_TABLE_ZIMMERKATEGORIE_EZ = "INSERT INTO zimmerKategorie (name, anzahlBetten, preisProNacht) VALUES ('"+ Konstanten.EZ_NAME +"', "+Konstanten.EZ_BETTEN+", "+Konstanten.EZ_PREIS+");";
	static final String INSERT_TABLE_ZIMMERKATEGORIE_DZ = "INSERT INTO zimmerKategorie (name, anzahlBetten, preisProNacht) VALUES ('"+ Konstanten.DZ_NAME +"', "+Konstanten.DZ_BETTEN+", "+Konstanten.DZ_PREIS+");";
	static final String INSERT_TABLE_ZIMMERKATEGORIE_FZ = "INSERT INTO zimmerKategorie (name, anzahlBetten, preisProNacht) VALUES ('"+ Konstanten.FZ_NAME +"', "+Konstanten.FZ_BETTEN+", "+Konstanten.FZ_PREIS+");";
	static final String INSERT_TABLE_ZIMMERKATEGORIE_BS = "INSERT INTO zimmerKategorie (name, anzahlBetten, preisProNacht) VALUES ('"+ Konstanten.SUITE_NAME +"', "+Konstanten.SUITE_BETTEN+", "+Konstanten.SUITE_PREIS+");";

	
	// Insert f�r Zimmer
	static final String INSERT_TABLE_ZIMMER_EZ = "INSERT INTO zimmer (kategorieNr) VALUES (1);";
	static final String INSERT_TABLE_ZIMMER_DZ = "INSERT INTO zimmer (kategorieNr) VALUES (2);";
	static final String INSERT_TABLE_ZIMMER_FZ = "INSERT INTO zimmer (kategorieNr) VALUES (3);";
	static final String INSERT_TABLE_ZIMMER_BS = "INSERT INTO zimmer (kategorieNr) VALUES (4);";
}
