package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dbUtil.DBHelper;
import verwaltung.Zimmerkategorie;

public class ZimmerkategorieModel extends DBHelper{
	
		private PreparedStatement pr = null;
		private ResultSet rs = null;

		public ZimmerkategorieModel() {
		}
		
		/**
		 * Liefert eine ArrayList aller Zimmerkategorie-Objekte aus der SQL-Datenbank.
		 * 
		 * @return ArrayList<Zimmerkategorie> 
		 * @throws SQLException wenn SQL-Fehler auftreten
		 */
		public ArrayList<Zimmerkategorie>  getZimmerKategorieListe() throws SQLException {
			
			String sql = "SELECT * FROM zimmerKategorie ;";	

			ArrayList<Zimmerkategorie> zimmerKategorieListe = new ArrayList<Zimmerkategorie>();
			
			try {
				pr = this.getConnection().prepareStatement(sql);
				ResultSet rs = pr.executeQuery();			
				while (rs.next()) {
					
					Zimmerkategorie kategorie = new Zimmerkategorie(
							rs.getInt("kategorieNr"),
							rs.getString("name"),
							rs.getInt("anzahlBetten"),
							rs.getDouble("preisProNacht"));
					
					zimmerKategorieListe.add(kategorie);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

			finally {
				pr.close();
				rs.close();
			}
			
			return zimmerKategorieListe;
		}
		
		/**
		 * Liefert ein Zimmerkategorie-Objekt mit der angegebenen Kategorie-Nummer aus der Datenbank zur�ck.
		 * 
		 * @param kategorieNr Kategorie-Nummer des gesuchten Zimmerkategorie-Objekts.
		 * @return Zimmerkategorie gesuchtes Zimmerkategorie-Objekt
		 * @throws SQLException wenn SQL-Fehler auftreten
		 */
		public Zimmerkategorie getZimmerKategorie(int kategorieNr) throws SQLException {
			
			Zimmerkategorie kategorie = null;
			String sql = "SELECT * FROM zimmerKategorie WHERE kategorieNr = ?;";
			
			try {

				pr = this.getConnection().prepareStatement(sql);
				pr.setInt(1, kategorieNr);
				ResultSet rs = pr.executeQuery();
				
				while (rs.next()) {
	
					kategorie = new Zimmerkategorie(
							rs.getInt("kategorieNr"),
							rs.getString("name"),
							rs.getInt("anzahlBetten"),
							rs.getDouble("preisProNacht"));
				}
				rs.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}

			finally {
				pr.close();
			}
			
			return kategorie;
			
		}
		
//		/**
//		 * F�gt ein Zimmerkategorie-Objekt der SQL-Datenbank hinzu.
//		 * 
//		 * @param zuSpeichern zu speicherndes Zimmerkategorie-Objekt
//		 * @throws SQLException wenn SQL-Fehler auftreten
//		 */
//		public void addDatabase(Zimmerkategorie zuSpeichern) throws SQLException {
//
//			String sql = "INSERT INTO zimmerKategorie (kategorieNr, name, anzahlBetten, preisProNacht) VALUES (?,?,?,?);";
//			try {
//				pr = this.getConnection().prepareStatement(sql);
//				pr.setInt(1, zuSpeichern.getKategorieNr());
//				pr.setString(2,zuSpeichern.getName());;
//				pr.setInt(3, zuSpeichern.getAnzahlBetten());
//				pr.setDouble(4, zuSpeichern.getPreisProNacht());
//
//
//				pr.executeUpdate();
//
//				ResultSet rs = pr.getGeneratedKeys();
//
//				if (rs.next()) {
//					System.out.println("Successfully saved into Database!");
//				}
//
//			} catch (SQLException e) {
//				if(e.getMessage().startsWith("[SQLITE_CONSTRAINT]  Abort due to")) {
//					throw new IllegalArgumentException("Die Kategorie-Nummer exisitiert bereits");
//				}
//				
//				e.printStackTrace();
//				System.out.println("SQL fehler");
//			}
//
//			finally {
//				pr.close();
//				rs.close();
//			}
//		}
//		
//		public ArrayList<Zimmerkategorie> getZimmerKategorieAnzahlPersonen(int anzahlPersonen) throws SQLException {
//			ArrayList<Zimmerkategorie> kategorienAnzahlPersonen = new ArrayList<>();
//			Zimmerkategorie kategorie;
//			try {
//				pstmtGetKategorieAnzahlPersonen = this.getConnection().prepareStatement(GET_KATEGORIE_ANZAHL_PERSONEN);
//				pstmtGetKategorieAnzahlPersonen.setInt(1, anzahlPersonen);
//				
//				
//				ResultSet rs = pstmtGetKategorieAnzahlPersonen.executeQuery();
//				
//				while (rs.next()) {
//					
//					kategorie = new Zimmerkategorie(
//							rs.getInt("kategorieNr"),
//							rs.getString("name"),
//							rs.getInt("anzahlBetten"),
//							rs.getDouble("preisProNacht"));
//					
//					kategorienAnzahlPersonen.add(kategorie);
//				}
//				rs.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//
//			finally {
//				pstmtGetKategorieAnzahlPersonen.close();
//			}
//			
//			return kategorienAnzahlPersonen;
//			
//		}

		


}
