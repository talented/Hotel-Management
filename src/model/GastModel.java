package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import dbUtil.DBHelper;
import verwaltung.Gast;
import view.util.InformationAlert;
import view.util.WarningAlert;

public class GastModel extends DBHelper {

	private PreparedStatement pr = null;
	private ResultSet rs = null;
	Gast gast;

	public GastModel() {
		super();
		gast = new Gast();

	}
	
	/**
	 * Methode pr�ft, ob einen Gast mit gleicher Gast-Nr. bereits exisitiert. 
	 *
	 * return boolean true, wenn Gast bereits existiert. False andernfalls.
	 * @param gastNr zu pr�fende Gast-Nr.
	 */
	public boolean existGast(int gastNr) throws SQLException {

		String sql = "SELECT * FROM Gast WHERE gastNr = ?";

		try {
			this.pr = this.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pr.setInt(1, gastNr);
			rs = pr.executeQuery();

			if (rs.next()) {
				System.out.println("Dieser Gast ist bereits in der Datenbank!");
				pr.close();
				rs.close();
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Liefert eine Liste von alle Gaeste-Objekten in der Datenbank
	 * 
	 * @param None
	 * @return ArrayList<Gast> alle Gaeste
	 * @throws SQLException wenn SQL-Fehler auftreten
	 */
	public ArrayList<Gast> getGaesteListe() throws SQLException {

		String fetchSql = "SELECT * FROM Gast";

		ArrayList<Gast> gaesteListe = new ArrayList<>();

		try {
			this.pr = this.getConnection().prepareStatement(fetchSql);

			rs = pr.executeQuery();
			while (rs.next()) {
				
				gast = new Gast(rs.getInt(1), rs.getString(2), rs.getString(3), LocalDate.parse(rs.getString(4)), 
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),
						rs.getString(9), rs.getString(10), rs.getString(11));
				
				gaesteListe.add(gast);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			rs.close();
			pr.close();
		}

		return gaesteListe;

	}

	/**
	 * Diese Methode bekommt das angegebene Gast-Objekt und speichert es in der Gasttabelle in der Datenbank
	 * 
	 * @param Gast-Objekt
	 * @return none
	 * @throws SQLException wenn SQL-Fehler auftreten
	 */
	public void addGast(Gast gast) throws SQLException {

		String addSql = "INSERT INTO Gast (vorname, nachname, geburtsdatum, strasse, hausNr, plz, stadt, land, email, telefon)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			pr = this.getConnection().prepareStatement(addSql, Statement.RETURN_GENERATED_KEYS);

			pr.setString(1, gast.getVorname());
			pr.setString(2, gast.getNachname());
			pr.setString(3, String.valueOf(gast.getGeburtsdatum()));
			pr.setString(4, gast.getStrasse());
			pr.setString(5, gast.getHausNr());
			pr.setString(6, gast.getPlz());
			pr.setString(7, gast.getOrt());
			pr.setString(8, gast.getLand());
			pr.setString(9, gast.getEmail());
			pr.setString(10, gast.getTelefon());

			pr.executeUpdate();
//			pr.executeQuery();

			rs = pr.getGeneratedKeys();

			if (rs.next()) {
				gast = getGast(gast.getVorname(), gast.getNachname(), gast.getGeburtsdatum());
//				new InformationAlert("Erfolgreich!", "Erfolgreich in der Datenbank mit Gastnummer " + gast.getGastNr() + " gespeichert!");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL fehler");
		}

		finally {
			pr.close();
			rs.close();
		}
	}

	/**
	 * Liefert ein Gast-Objekt mit der angegebenen Gast-Nummer aus der Datenbank zur�ck.
	 * 
	 * @param gastNr zu suchende Gast-Nummer
	 * @return Gast gesuchtes Gast-Objekt
	 * @throws SQLException wenn SQL-Fehler auftreten
	 */
	public Gast getGastByGastNr(int gastNr) throws SQLException {

		String getSql = "SELECT * FROM Gast WHERE gastNr = ?";

		try {
			this.pr = this.getConnection().prepareStatement(getSql, Statement.RETURN_GENERATED_KEYS);
			pr.setInt(1, gastNr);
			rs = pr.executeQuery();

			if (rs.next()) {
				
				gast = new Gast(rs.getInt(1), rs.getString(2), rs.getString(3), LocalDate.parse(rs.getString(4)), 
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),
						rs.getString(9), rs.getString(10), rs.getString(11));
					
			} else {
				System.out.println("Dieser Gast ist nicht in der Datenbank angemeldet!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pr.close();
			rs.close();
		}

		return gast;
	}
	
	/**
	 * Liefert ein Gast-Objekt mit der angegebenen Gast-vorname, Gast-nachname und Gast-geburtsDatum
	 *  aus der Datenbank zur�ck.
	 * 
	 * @param vorname, nachname, geburtsDatum
	 * @return Gast gesuchtes Gast-Objekt
	 * @throws SQLException wenn SQL-Fehler auftreten
	 */
	public Gast getGast(String vorname, String nachname, LocalDate geburtsDatum) throws SQLException {

		String getSql = "SELECT * FROM Gast WHERE vorname = ? AND nachname = ? AND geburtsdatum = ?";
		
		try {
			this.pr = this.getConnection().prepareStatement(getSql, Statement.RETURN_GENERATED_KEYS);
			pr.setString(1, vorname);
			pr.setString(2, nachname);
			pr.setString(3, String.valueOf(geburtsDatum));
			rs = pr.executeQuery();

			if (rs.next()) {
				gast.setGastNr(rs.getInt(1));
				gast.setVorname(rs.getString(2));
				gast.setNachname(rs.getString(3));
				gast.setGeburtsdatum(LocalDate.parse(rs.getString(4)));
				gast.setStrasse(rs.getString(5));
				gast.setHausNr(rs.getString(6));
				gast.setPlz(rs.getString(7));
				gast.setOrt(rs.getString(8));
				gast.setLand(rs.getString(9));
				gast.setEmail(rs.getString(10));
				gast.setTelefon(rs.getString(11));				
			} else {
				System.out.println("User not found in database!");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pr.close();
			rs.close();
		}

		return gast;
	}
	
	
	/**
	 * Diese Methode bekommt das angegebene Gast-Objekt und aktualisiert es in der Gasttabelle in der Datenbank
	 * 
	 * @param Gast-Objekt
	 * @return none
	 * @throws SQLException wenn SQL-Fehler auftreten
	 */
	public Gast updateGast(Gast gast) throws SQLException {

		String updateSql = "UPDATE Gast SET vorname = ?, nachname = ?, geburtsdatum = ?, strasse = ?, hausNr = ?, plz = ?, stadt = ?,"
				+ " land = ?, email = ?, telefon = ? WHERE gastNr = ?";

		try {
			this.pr = this.getConnection().prepareStatement(updateSql, Statement.RETURN_GENERATED_KEYS);
			
			pr.setString(1, gast.getVorname());
			pr.setString(2, gast.getNachname());
			pr.setString(3, String.valueOf(gast.getGeburtsdatum()));
			pr.setString(4, gast.getStrasse());
			pr.setString(5, gast.getHausNr());
			pr.setString(6, gast.getPlz());
			pr.setString(7, gast.getOrt());
			pr.setString(8, gast.getLand());
			pr.setString(9, gast.getEmail());
			pr.setString(10, gast.getTelefon());
			
			pr.setInt(11, gast.getGastNr());

			pr.executeUpdate();
//			pr.executeQuery();

			rs = pr.getGeneratedKeys();

			if (rs.next()) {
				System.out.println("Successfully updated in Database!");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL fehler");
		}

		finally {
			pr.close();
			rs.close();
		}
		return gast;
		
	}

}
