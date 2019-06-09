package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import dbUtil.DBHelper;
import verwaltung.Buchung;
import view.util.InformationAlert;

public class BuchungModel extends DBHelper {

	private PreparedStatement pr = null;
	private ResultSet rs = null;
	Buchung buchung;
	ZimmerModel zimmerModel;

	public BuchungModel() throws SQLException {
		buchung = new Buchung();
		zimmerModel = new ZimmerModel();
	}

	/**
	 * Diese Methode pr�ft dass die gleiche gastNr schon existiert.
	 *
	 * return boolean
	 * 
	 * @param BuchungsNr
	 */
	public boolean existBuchung(String buchungsNr) throws SQLException {

		String sql = "SELECT * FROM Buchung WHERE buchungsNr = ?";

		try {
			this.pr = this.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pr.setString(1, buchungsNr);
			rs = pr.executeQuery();

//			int autoGeneratedID = rs.getInt(1);
//			System.out.println(autoGeneratedID);

			if (rs.next()) {
				System.out.println("this Buchung is already in Database..");
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
	 * Diese Methode gibt alle Buchungen zur�ck.
	 *
	 * @return TreeMap als {YYYY-1=Buchung_Object_1, YYYY-2=Buchung_Object_2}
	 * @param gastNr
	 */
	public ArrayList<Buchung> getBuchungsListe() throws SQLException {

		String fetchSql = "SELECT * FROM Buchung";

//		TreeMap<String, Buchung> buchungsListe = new TreeMap<String, Buchung>();

		ArrayList<Buchung> buchungsListe = new ArrayList<>();

		try {
			pr = this.getConnection().prepareStatement(fetchSql);

			rs = pr.executeQuery();
			while (rs.next()) {

				buchung = new Buchung(rs.getString(2), LocalDate.parse(rs.getString(3)),
						LocalDate.parse(rs.getString(4)), rs.getBoolean(5), rs.getString(6), rs.getInt(7),
						rs.getBoolean(8), rs.getInt(9), rs.getInt(10), rs.getBoolean(11));
//				

				buchung.setBuchungsNr(rs.getString(2));
				buchung.setAnreise(LocalDate.parse(rs.getString(3)));
				buchung.setAbreise(LocalDate.parse(rs.getString(4)));
				buchung.setFruehstueck(rs.getBoolean(5));
				buchung.setZahlungsart(rs.getString(6));
				buchung.setAnzahlPersonen(rs.getInt(7));
				buchung.setKinderbett(rs.getBoolean(8));
				buchung.setGastNr(rs.getInt(9));
				buchung.setZimmerNr(rs.getInt(10));
				buchung.setStorniert(rs.getBoolean(11));

				buchungsListe.add(buchung);

//				buchungsListe.put(rs.getString(2), buchung);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			rs.close();
			pr.close();
		}

		return buchungsListe;

	}

	public Buchung getBuchungByBuchungsNr(String buchungsNr) throws SQLException {

		String getSql = "SELECT * FROM Buchung WHERE buchungsNr = ?";

		try {
			this.pr = this.getConnection().prepareStatement(getSql, Statement.RETURN_GENERATED_KEYS);
			pr.setString(1, buchungsNr);
			rs = pr.executeQuery();

			if (rs.next()) {

				buchung = new Buchung(rs.getString(2), LocalDate.parse(rs.getString(3)),
						LocalDate.parse(rs.getString(4)), rs.getBoolean(5), rs.getString(6), rs.getInt(7),
						rs.getBoolean(8), rs.getInt(9), rs.getInt(10), rs.getBoolean(11));

//				buchung.setBuchungsNr(rs.getString(2));
//				buchung.setAnreise(LocalDate.parse(rs.getString(3)));
//				buchung.setAbreise(LocalDate.parse(rs.getString(4)));
//				buchung.setFruehstueck(rs.getBoolean(5));
//				buchung.setZahlungsart(rs.getString(6));
//				buchung.setAnzahl_Personen(rs.getInt(7));
//				buchung.setKinderbett(rs.getBoolean(8));
//				buchung.setGastNr(rs.getInt(9));
//				buchung.setZimmerNr(rs.getInt(10));
//				buchung.setStorniert(rs.getBoolean(11));

			} else {
				System.out.println("Buchung not found in database!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pr.close();
			rs.close();
		}

		return buchung;

	}

	/**
	 * Diese Methode f�gt eine neue Buchung im Datenbank.
	 *
	 * @param Buchung objekt
	 * @throws InterruptedException
	 */
	public void addBuchung(Buchung buchung) throws SQLException {

		String addSql = "INSERT INTO Buchung (buchungsNr, anreise, abreise, fruehstueck, zahlungsart, anzahlPersonen"
				+ ", kinderbett, gastNr, zimmerNr, storniert)" + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		String year = String.valueOf(LocalDate.now().getYear());
		int maxID = 0;

		String idSql = "SELECT MAX(id) FROM Buchung";

		try {

			pr = this.getConnection().prepareStatement(idSql);
			rs = pr.executeQuery();

			if (rs.next()) {
				maxID = rs.getInt(1);
			}

			pr = this.getConnection().prepareStatement(addSql, Statement.RETURN_GENERATED_KEYS);

			pr.setString(1, year + "-" + String.valueOf(maxID + 1));
			pr.setString(2, String.valueOf(buchung.getAnreise()));
			pr.setString(3, String.valueOf(buchung.getAbreise()));
			pr.setBoolean(4, buchung.isFruehstueck());
			pr.setString(5, buchung.getZahlungsart());
			pr.setInt(6, buchung.getAnzahlPersonen());
			pr.setBoolean(7, buchung.isKinderbett());
			pr.setInt(8, buchung.getGastNr());
			pr.setInt(9, buchung.getZimmerNr());
			pr.setBoolean(10, buchung.isStorniert());

			pr.executeUpdate();
//			pr.executeQuery();

			rs = pr.getGeneratedKeys();

			if (rs.next()) {
				new InformationAlert("Erfolgreich!", "Buchung ist erfolgreich erstellt!");
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

	public void updateBuchung(Buchung buchung) throws SQLException {

		String updateSql = "UPDATE Buchung SET anreise = ?, abreise = ?, fruehstueck = ?, zahlungsart = ?, "
				+ "anzahlPersonen = ?, kinderbett = ?, gastNr = ?,"
				+ " zimmerNr = ?, storniert = ? WHERE buchungsNr = ?";

		try {
			this.pr = this.getConnection().prepareStatement(updateSql, Statement.RETURN_GENERATED_KEYS);

			pr.setString(1, String.valueOf(buchung.getAnreise()));
			pr.setString(2, String.valueOf(buchung.getAbreise()));
			pr.setBoolean(3, buchung.isFruehstueck());
			pr.setString(4, buchung.getZahlungsart());
			pr.setInt(5, buchung.getAnzahlPersonen());
			pr.setBoolean(6, buchung.isKinderbett());
			pr.setInt(7, buchung.getGastNr());
			pr.setInt(8, buchung.getZimmerNr());
			pr.setBoolean(9, buchung.isStorniert());
			pr.setString(10, buchung.getBuchungsNr());

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

	}

	public void setStorniertTrue(String buchungsNr) throws SQLException {

		String setSql = "UPDATE Buchung SET storniert = true WHERE buchungsNr = ?";

		try {

			this.pr = this.getConnection().prepareStatement(setSql);
			pr.setString(1, buchungsNr);

			pr.executeUpdate();

			rs = pr.getGeneratedKeys();

			if (rs.next()) {
				System.out.println("Storniert is set to True in database!");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void switchStorniert(String buchungsNr) throws SQLException {

		boolean isStorniert = getBuchungByBuchungsNr(buchungsNr).isStorniert();

		String setSql = "UPDATE Buchung SET storniert = ? WHERE buchungsNr = ?";

		try {

			this.pr = this.getConnection().prepareStatement(setSql);
			boolean status = isStorniert ? false : true;

			pr.setBoolean(1, status);
			pr.setString(2, buchungsNr);

			pr.executeUpdate();

			rs = pr.getGeneratedKeys();

			if (rs.next()) {
				System.out.println("Storniert is set in database!");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
//	public void setStorniert(String buchungsNr, boolean storniert) {
//		try {
//			pstmtStorniereBuchung.setBoolean(1,  storniert);
//			pstmtStorniereBuchung.setString(2, buchungsNr);
//			pstmtStorniereBuchung.executeUpdate();
//
//			System.out.println("Storniert is set to " + storniert + " in database!");
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Liefert den letzten Eintrag (sortiert nach der ID) aus der SQL-Datenbank und gibt dieses als Buchungs-Objekt zur¸ck.
	 * 
	 * @return Buchung mit der zuletzt hinzugef¸gten ID oder null falls kein Eintrag vorhanden
	 * @throws SQLException bei Problemen mir der SQL-Datenbank
	 */
//	public Buchung getLastBuchung() {
//		Buchung result = null;
//
//		try {
//			ResultSet rs = pstmtGetLastBuchung.executeQuery();
//
//			if (rs.next()) {
//				result = new Buchung(rs.getString(2), LocalDate.parse(rs.getString(3)),
//						LocalDate.parse(rs.getString(4)), rs.getBoolean(5), rs.getString(6), rs.getInt(7),
//						rs.getBoolean(8), rs.getInt(9), rs.getInt(10), rs.getBoolean(11));
//			}
//			rs.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return result;
//	}

}