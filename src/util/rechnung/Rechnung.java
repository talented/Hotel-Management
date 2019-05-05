package util.rechnung;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xwpf.usermodel.LineSpacingRule;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TableWidthType;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.VerticalAlign;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHeight;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTrPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVerticalJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;

import model.GastModel;
import model.ZimmerModel;
import verwaltung.Buchung;
import verwaltung.Gast;
import verwaltung.Zimmer;
import util.Konstanten;

/**Die Klasse generiert  und schreibt in der Datei des Typs .docx die Rechnung für Aplication "Hotelzummerverwaltung".
 * Die Name von der Datei wird : Word "rechnung " plus Buchungsnummer zusammen gesetzt. 
 * 
 * Es wird Librarie  Apache POI benötigt
 * und java-time-backport-1.0.0.jar
 */


public class Rechnung {

	
	private String txtformatter1 = "yyyy-MM-dd";		// Format für Datenvorstellung
	private String txtformatter2 = "dd. MMMM yyyy hh:mm";// Format für Datenvorstellung
	
	
	private  String txtGastVorname ="";					//Der Vorname des Gastes
	private  String txtGastName ="";					//Der Name des Gastes
	private String txtGastStrasse ="";					//Strasse des Gastes
	private String txtGastHausnr ="";					//Hausnummer des Gastes
	private String txtGastOrt ="";					    //Ort des Gastes
	private String txtGastPLZ ="";						//Postleitzahl des Gastes
	private String txtGastLand ="";					    //Land des Gastes
	private String txtGastEmail ="";					//EMail des Gastes
	private String txtGastTelefon ="";					//Telefonnummer des Gastes
	
	private String txtHotelRechnungsnummer ="";			//Rechnungsnummer = Buchungsnummer
	private String txtHotelZimmerKategory ="";			//Zimmer beschreibung
	private int intHotelZimmerpreis ;					// Zimmerpreis
	private int intHotelAnzahlPerson = 0;				//Anzahl Personen für diese Buchung
	private LocalDate ldHotelanreise ;					//Anreisedatum des Gastes
	private LocalDate ldHotelabreise ;					//Abreisedatum des Gastes
	private boolean blHotelKinderBett = false;			//ein Kinderbett war mitbeschtelt
	private boolean blHotelFruehstueck = false;			//mit/ohne Fruehstueck
	
	private  XWPFDocument docx;							//  new document from scratch
	
	
	/**
	 * Dieses  Konstruktor generiert docx mit Daten  DEFAULT definierd
	 */
	public Rechnung()  {
		super();
		//Zum Testen
		this.txtHotelRechnungsnummer = "20191000";
		this.intHotelAnzahlPerson = 4;
		this.blHotelFruehstueck = true;
		this.blHotelKinderBett = true;
		this.txtHotelZimmerKategory = Konstanten.HOTELZIMMERKATEGORY_SUITE;
		this.intHotelZimmerpreis = Konstanten.SUITE_PREIS;
		this.ldHotelanreise = LocalDate.of(2019, 4, 04);
		this.ldHotelabreise = LocalDate.of(2019, 5, 23);
		
		
		this.txtGastVorname ="MusterVorname  ";
		this.txtGastName ="MusterName";
		this.txtGastStrasse ="Musterstrasse";
		this.txtGastHausnr ="1";
		this.txtGastOrt ="Stadt";
		this.txtGastPLZ ="00000";
		this.txtGastLand ="Deutschland";
		this.txtGastEmail ="m.mustername@muster.com";
		this.txtGastTelefon ="00000000000";
		
		
		
	}
	
	/**
	 *  Konstruktor, der die alle Daten für die Rechnung erstellung zusammensetzt 
	 * @param buchung Information über die Buchung
	 * @throws SQLException wenn SQL-Fehler auftreten
	 */
	public Rechnung(Buchung buchung) throws SQLException  {
		super();
		
			// Daten über die Buchung nach Buchungsnummer wird gesetzt

			this.txtHotelRechnungsnummer = buchung.getBuchungsNr();
			this.ldHotelabreise = buchung.getAbreise();
			this.ldHotelanreise = buchung.getAnreise();
			this.intHotelAnzahlPerson = buchung.getAnzahlPersonen();
			this.blHotelFruehstueck = buchung.isFruehstueck();
			this.blHotelKinderBett = buchung.isKinderbett();
		
		//Daten über die Zimmer wird rausbekommen
		ZimmerModel zimmerbeans = new ZimmerModel();	
		Zimmer zimmer = zimmerbeans.getZimmerByZimmerNr(buchung.getZimmerNr()); //Kriegen ZimmerKategory , fest gelegt
		this.txtHotelZimmerKategory = zimmer.getKategorie().getName();
		
		switch (zimmer.getKategorieNr()) {
        case 1:
        	txtHotelZimmerKategory = Konstanten.HOTELZIMMERKATEGORY_EZ;
        	break;
        case 2:
        	txtHotelZimmerKategory = Konstanten.HOTELZIMMERKATEGORY_DZ;
        	break;
        case 3:
        	txtHotelZimmerKategory = Konstanten.HOTELZIMMERKATEGORY_FZ;
        	break;
        default:
        	txtHotelZimmerKategory = Konstanten.HOTELZIMMERKATEGORY_SUITE;
}
		
		
		//information über der Gast
		GastModel gastbeans = new GastModel();
		Gast gast = gastbeans.getGastByGastNr(buchung.getGastNr());
		this.txtGastVorname =gast.getVorname();
		this.txtGastName = gast.getNachname();
		this.txtGastStrasse = gast.getStrasse();
		this.txtGastHausnr = gast.getHausNr();
		this.txtGastOrt = gast.getOrt();
		this.txtGastPLZ = gast.getPlz();
		this.txtGastLand = gast.getLand();
		if (gast.getEmail()!= null) {
			this.txtGastEmail = gast.getEmail(); //wird Feld  Email leer sein , wird  Email = ""
		}
		if (gast.getTelefon()!= null) {
			this.txtGastTelefon = gast.getTelefon();// wird Feld Telefon leer sein , wird Telefon = ""
		}
		
		
		
	}
	/**
	 * Diese Methode WordDOCXPOI bildet den Inhalt des Dokumentes.
	 *  
	 * Ruft die Methode welches der Verzeichnis für der  Schpeicherung die Datei vorbereitet.
	 * Ruft die Methode  welches die Datei schpeichert . 
	 * Ruft die Methode welches die Datei aufrufst.
	 * @param None
	 * @return None
	 */
	
	public void WordDOCXPOI() {
		
		boolean exf = false; // docx datei nicht existiert
		
		try {
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(txtformatter1);
			
			//Der Ordner, wo die Datei wird geshpeichert , auf die Existenz zu prüfen oder, die Neuer zu schaffen
			
			PathtoHotelRechnung ph = new PathtoHotelRechnung(ldHotelanreise);
			String txtPath = ph.PathtoHotelRechnungExists();
			
			// Schreiben docx Name  = Jahr des Buchung plus "rechung" plus Buchungsnummer
			
			 txtPath = txtPath  + "\\rechnung" +txtHotelRechnungsnummer + ".docx" ;
			 if ( new File(txtPath).exists()) {
				exf = true;
			 }
			 if (!exf) {
			// Create a new document from scratch
			docx = new XWPFDocument();
			
			
			// Wird daten gesetst
			addText(docx.createParagraph(), txtGastVorname + " " + txtGastName, "", Konstanten.H4, false, false, false);
			addText(docx.createParagraph(), txtGastStrasse + " " + txtGastHausnr, "", Konstanten.H4, false, false,
					false);
			addText(docx.createParagraph(), txtGastPLZ + " " + txtGastOrt, "", Konstanten.H4, false, false, false);
			addText(docx.createParagraph(), txtGastLand, "", Konstanten.H4, false, false, false);
			addText(docx.createParagraph(), "", "", Konstanten.H4, false, false, false);
			addText(docx.createParagraph(), "Email:  " + txtGastEmail, "", Konstanten.H3, false, false, false);
			addText(docx.createParagraph(), "Tel.: " + txtGastTelefon, "", Konstanten.H3, false, false, false);
			addText(docx.createParagraph(), "", "", Konstanten.H2, false, false, false);
			
			addText(docx.createParagraph(), "Rechnung", "", Konstanten.H2, true, false, false);
			addText(docx.createParagraph(), "Rechnungsnummer: " + txtHotelRechnungsnummer, "", Konstanten.H4, true, false, true);
			
			//Rechnungsdatum wird als laufende Datum mit Uhrzeit genommen
			String txt1 = "Rechnungsdatum: " + (new SimpleDateFormat(txtformatter2).format(new Date()));
			addText(docx.createParagraph(), txt1, "", Konstanten.H4, false, false, false);

			String txt2 = "Ankunft : " + (ldHotelanreise.format(formatter)) + "  Abreise : " + (ldHotelabreise.format(formatter)) + " Zimmer: 1 " + " Personen: "
					+ intHotelAnzahlPerson;
			addText(docx.createParagraph(), txt2, "", Konstanten.H4, false, false, false);
			addText(docx.createParagraph(), "", "", Konstanten.H4, false, false, false);
	
			// Rechnen die Anzahl der Tage/Nächte des Aufenhaltes im Hotel
			long aufenhalttagen = ChronoUnit.DAYS.between(ldHotelanreise, ldHotelabreise);
			
			
			// Bilden die erste Tabelle für die Rechnung. docx

			ArrayList<String> list1 = new ArrayList<>();
			list1.add("Datum      ");
			list1.add("Menge      ");
			list1.add("Leistung ");

			ArrayList<String> list2 = new ArrayList<>();
			list2.add(ldHotelanreise.format(formatter));
			list2.add(Long.toString(aufenhalttagen));
			list2.add(txtHotelZimmerKategory);

			ArrayList<ArrayList<String>> listOLists = new ArrayList<ArrayList<String>>();
			listOLists.add(list1);
			listOLists.add(list2);

			addToTable(listOLists,1);

			// Leere text

			addText(docx.createParagraph(), "", "", Konstanten.H5, false, false, true);
			
			// Bilden die zweite Tabelle für die Rechnung

			ArrayList<String> list1_1 = new ArrayList<>();
			list1_1.add("                                                                           ");
			list1_1.add("Einzelpreis,€");
			list1_1.add("Gesamtpreis,€");

			ArrayList<String> list2_1 = new ArrayList<>();
			list2_1.add("Aufteilung Endpreis wie folgt:");
			list2_1.add("  ");
			list2_1.add("  ");

			ArrayList<String> list3_1 = new ArrayList<>();
			String txtaufenhalttagen = "";

			if (aufenhalttagen == 1) {
				txtaufenhalttagen = Long.toString(aufenhalttagen) + " Übernachtung";
			} else {
				txtaufenhalttagen = Long.toString(aufenhalttagen) + " Übernachtungen";
			}

			list3_1.add(txtaufenhalttagen);
			list3_1.add("" + intHotelZimmerpreis);
			list3_1.add("" + intHotelZimmerpreis * aufenhalttagen);

			ArrayList<ArrayList<String>> listOLists1 = new ArrayList<ArrayList<String>>();
			listOLists1.add(list1_1);
			listOLists1.add(list2_1);
			listOLists1.add(list3_1);
			
			long summeFprA = 0;
			if (blHotelFruehstueck) {
				String txtFruehstueck = "";
				ArrayList<String> list4_1 = new ArrayList<>();

				txtFruehstueck = Long.toString(aufenhalttagen) + " mal Frühstück  pro " + intHotelAnzahlPerson + " Person";
				list4_1.add(txtFruehstueck);
				list4_1.add("" + Konstanten.FRUEHSTUECK_PREIS);
				summeFprA = Konstanten.FRUEHSTUECK_PREIS * aufenhalttagen * intHotelAnzahlPerson;
				list4_1.add("" + summeFprA );
				listOLists1.add(list4_1);
			}
			
			long summeKBPrA = 0;

			if (blHotelKinderBett) {

				String txtKinderBett = "";

				ArrayList<String> list5_1 = new ArrayList<>();

				txtKinderBett = "1 Kinderbett für " + aufenhalttagen + "xÜbernachtung";
				list5_1.add(txtKinderBett);
				summeKBPrA = Konstanten.KINDERBETT_PREIS * aufenhalttagen;
				list5_1.add("" + Konstanten.KINDERBETT_PREIS);
				list5_1.add("" + summeKBPrA);
				listOLists1.add(list5_1);

			}
			ArrayList<String> list6_1 = new ArrayList<>();

			
			list6_1.add("Gesamtsumme");
			list6_1.add("");
			list6_1.add("" + (summeKBPrA + summeFprA + intHotelZimmerpreis * aufenhalttagen));
			listOLists1.add(list6_1);
			addToTable(listOLists1,3);
			
			addText(docx.createParagraph(), "", "", Konstanten.H4, true, false, false);
			String txtStrike = "_____________________________________________________________________________";
			addText(docx.createParagraph(), txtStrike, "", Konstanten.H5, true, false, false);
			
			// Bilden die 3 Tabelle für die Rechnung
			//Hotel info 
			
			ArrayList<String> list1H_2 = new ArrayList<>();
			
			list1H_2.add(" ");                                                                          
			list1H_2.add(" ");
			list1H_2.add(" ");
			
			ArrayList<String> list1_2 = new ArrayList<>();
			
			list1_2.add(Konstanten.HOTELNAME);                                                                          
			list1_2.add(Konstanten.HOTELINTERNET);
			
			ArrayList<String> list2_2 = new ArrayList<>();
			
			list2_2.add(Konstanten.HOTELADRESSE);
			list2_2.add(Konstanten.HOTELTELEFONNR);
			
			ArrayList<String> list3_2 = new ArrayList<>();
			
			list3_2.add(Konstanten.HOTELADRESSEPLZ);
			list3_2.add(Konstanten.HOTELEMAIL);
			
			
			ArrayList<ArrayList<String>> listOLists2 = new ArrayList<ArrayList<String>>();
			listOLists2.add(list1H_2);
			listOLists2.add(list1_2);
			listOLists2.add(list2_2);
			listOLists2.add(list3_2);
			
			addToTable(listOLists2,1);
			
			// write the file
			
			
			
			/*zum Test
			 * ***************************************
			
			 txtPath = txtPath  + "\\rechnung" +txtHotelRechnungsnummer + Math.random()+".docx" ;
			 */	
			
			
			closeWordDOCX(txtPath);// Die Methode welches die Datei schpeichert .
			}
			 
			 //war schon Datei generiert , wird er nur geöffnet
			openWordDOCX(txtPath);//Die Methode welches die Datei aufrufst.
			//printWordDOCX(txtPath);
			
		} catch (Exception ex) {
			
			new IllegalArgumentException( ex.toString() );
			
		}
	}
	/**
	 * Method for addition of the text
	 * Method vorbereitet text als Paragraph  ins docx zufügen 
	 * 
	 * @param p - region(Paragraph) ins docxbody
	 * @param text - Text welche wird hinzudefügt
	 * @param fontSize - Fond Size
	 * @param color - Color texts
	 * @param bold - Schrift  Bold Ja/nein
	 * @param italic - Schrift italic Ja/Nein
	 * @param strike - Schrift strike Ja/Nein
	 * @return XWPFParagraph fertige Paragraph zum hinzufügen
	 */
	public  XWPFParagraph addText(XWPFParagraph p, String text,String color, int fontSize, boolean bold, boolean italic,  boolean strike) {

		KrXWPFRun krXWPFRun = new KrXWPFRun(p.createRun());

		krXWPFRun.setText(text);
		krXWPFRun.setBold(bold);
		krXWPFRun.setItalic(italic);
		krXWPFRun.setFontFamily(Konstanten.DEFAULT_FONT_NAME);
		if(strike ) {
			krXWPFRun.setUnderline(UnderlinePatterns.SINGLE);
		}else {
			krXWPFRun.setUnderline(UnderlinePatterns.NONE);
		}
		krXWPFRun.setColor(color);
		krXWPFRun.setFontSize(fontSize);
		p.setAlignment(ParagraphAlignment.LEFT);
		p.setSpacingLineRule(LineSpacingRule.EXACT);//AUTO

		return p;
	}	
	/**
	 * Diese Methode setzt text ins celle von Tabelle
	 * @param rh Paragraph ins Celle 
	 * @param text - text zum hinfügen ins celle von Tabelle
	 * @return None
	 */
	
	private  void addTableText(XWPFRun rh, String text) {
		if (text == null) {
			rh.setText("null");
			return;
		}
		
		String[] split = text.split("\n");
		int size = split.length;
		for (int i = 0; i < size; i++) {
			String d = split[i];
			rh.setText(d);
			if (i < size - 1) {
				rh.addBreak();
			}
		}
		
	}
	
	/**
	 * Greatet tabelle ins docx, setzt die Property von tabelle 
	 * @param list - der  text-Inhalt  in der Tabelle
	 * @param richtung Formatirung : 1 - setzt text ins 1st paragraph in cell's paragraph list Richtung Left
	 * 								 2 - setzt text ins 1st paragraph in cell's paragraph list Richtung Center
	 * 								 3 - setzt text ins 1st paragraph in cell's paragraph list Richtung Richt
	 * 								 alle andere  Richtung Left
	 * @return generierte Tablle 
	 */

	public XWPFTable addToTable(ArrayList<ArrayList<String>> list, int richtung) {

		if (list == null || list.isEmpty()) {
			return null;
		}

		int rowSize = list.size();
		int colSize = list.get(0).size();

		XWPFTable table = docx.createTable(rowSize, colSize);
		table.removeBorders();
		

		//  If the style is not defined, the table style
		// will become "Normal".

		/* Table Width */
		{
			CTTbl ctTbl = table.getCTTbl(); // Object von xml schema
			CTTblPr tblPr = ctTbl.getTblPr(); // Properti von table
			CTTblWidth tblW = tblPr.getTblW();

			// WIDTH

			tblW.setW(BigInteger.valueOf(5000));
			tblW.setType(STTblWidth.PCT);
		}
		
		// Get a list of the rows in the table

		int rowCt = 0;
		int colCt = 0;

		for (List<String> set : list) {
			XWPFTableRow row = table.getRow(rowCt);
			CTTrPr trPr = row.getCtRow().addNewTrPr();
			// set row height; units = twentieth of a point, 360 = 0.25"
			CTHeight ht = trPr.addNewTrHeight();
			ht.setVal(BigInteger.valueOf(120)); /* 240 , 360 */

			// get the cells in this row
			List<XWPFTableCell> cells = row.getTableCells();
			Iterator<String> it = set.iterator();

			while (it.hasNext()) {
				if (cells.size() == colCt) {
					row.createCell();
				}
				// get a table cell properties element (tcPr)
				XWPFTableCell cell = cells.get(colCt);
				CTTcPr tcpr = cell.getCTTc().addNewTcPr();
				cell.setWidthType(TableWidthType.DXA);//DXA
				

				// set vertical alignment 

				CTVerticalJc va = tcpr.addNewVAlign();
				va.setVal(STVerticalJc.BOTH);

				// get 1st paragraph in cell's paragraph list
				XWPFParagraph para = cell.getParagraphs().get(0);
				
				// create a run to contain the content
				KrXWPFRun rh = new KrXWPFRun(para.createRun());
				
				rh.setFontFamily(Konstanten.DEFAULT_FONT_NAME);
				
				// style cell as desired
				if (colCt == colSize - 1) {
					
					rh.setFontSize(Konstanten.H3);

				}
				if (colCt == 0) {

					rh.setFontSize(Konstanten.H3);
					
				}
				
				switch (richtung) {
		        case 1:
		        	para.setAlignment(ParagraphAlignment.LEFT);
		        	break;
		        case 2:
		        	para.setAlignment(ParagraphAlignment.CENTER); 
		        	break;
		        case 3:
		        	para.setAlignment(ParagraphAlignment.RIGHT);  
		        	break;
		        default:
		        	para.setAlignment(ParagraphAlignment.LEFT);  
		}

				String content = it.next();
				
				if (rowCt == 0 ) {
					// header row
					rh.setText(content);
					rh.setFontSize(Konstanten.H3);
					rh.setBold(true);
					
					table.setInsideHBorder(XWPFTable.XWPFBorderType.SINGLE, 0, 0, "");
		
				
				}  else  if (rowCt % 2 == 0 ){
					// even row
					rh.setFontSize(Konstanten.H3);
					addTableText(rh, content);
					rh.setSubscript(VerticalAlign.BASELINE);
					table.setInsideHBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, "");

					if (colCt ==0) {
						para.setAlignment(ParagraphAlignment.LEFT);
					}
				}else  {
					
					// odd row
					rh.setFontSize(Konstanten.H3);
					addTableText(rh, content);
					rh.setSubscript(VerticalAlign.BASELINE);
	
					if (colCt ==0) {
						para.setAlignment(ParagraphAlignment.LEFT);
						}
					}

				colCt++;
			} // for cell
			colCt = 0;
			
			rowCt++;

		}

		return table;
	}
	
/**
 * Diese Methode schreibt vorbereitete body von docx file ins Datei mit docxname
 * @param docxname name von generierte File
 * @throws IOException  Ein ErrorAlert wird generiert falls die Datei mit docx Name existiert schon.
 */
	
	public  void closeWordDOCX(String docxname)  {
		// write the file
		try
	    {
			
			FileOutputStream out = new FileOutputStream( new File(docxname));
			
			docx.write(out);
			out.close();
			
			
			new File(docxname).setWritable(false); //setst docx File nur zum lesen
			
			if (new File(docxname).exists()) throw new FileNotFoundException("Datei");
			new File(docxname).setWritable(false); //setst docx File nur zum lesen
			Desktop.getDesktop().open(new File(docxname)); // offnet docx auf Desktop
//			System.out.println("createdocument.docx written successully");
	    }
		 catch(IOException ex)
	    {
			 new IllegalArgumentException(ex.toString()  );

	    }
	 }
		/**
		 * Diese Methode offnen Datei/Rechnung .docx
		 * @param docxname name von Datei
		 * @throws IOException Ein ErrorAlert wird generiert falls die Datei mit docx Name nicht existiert oder probleme IO File gibts
		 */
		public  void openWordDOCX(String docxname) throws IOException  {
			try
		    {
				if (!new File(docxname).exists()) throw new FileNotFoundException("Datei exitiert nich!"  );
				Desktop.getDesktop().open(new File(docxname)); // offnet docx auf Desktop
		    }
			catch(IOException ex)
		    {
				 new IllegalArgumentException(ex.toString()  );

		    }
			
		}
		/**
		 * Diese Methode für ausdrucken Datei/ Rechnung .docx
		 * @param docxname name von Datei
		 * @throws IOException Ein ErrorAlert wird generiert falls die Datei mit docx Name nicht existiert oder probleme IO File gibts
		 */
		public  void printWordDOCX(String docxname) throws IOException  {
			try
		    {
				if (!new File(docxname).exists()) throw new FileNotFoundException("Datei exitiert nich!"  );
				Desktop.getDesktop().print(new File(docxname)); // zum ausdrucken docx 
		    }
			catch(IOException ex)
		    {
					new IllegalArgumentException(ex.toString()  );

		    }
			
		}
	

}


