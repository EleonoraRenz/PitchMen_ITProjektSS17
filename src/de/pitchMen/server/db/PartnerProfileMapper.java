package de.pitchMen.server.db;

import java.sql.*;
import java.util.ArrayList;

import de.pitchMen.shared.bo.PartnerProfile;
//PersonId, TamId, CompanyID und JobPostingID FK als getter in PartnerProfil.java implementiert

/**
 * Bildet PartnerProfile-Objekte auf eine relationale Datenbank ab. Ebenfalls
 * ist es m�glich aus Datenbank-Tupel Java-Objekte zu erzeugen.
 * 
 * @author Lars
 */

public class PartnerProfileMapper {

	/**
	 * Die Klasse PartnerProfileMapper wird nur einmal instantiiert
	 * (Singleton-Eigenschaft). Damit diese Eigenschaft erf�llt werden kann,
	 * wird zun�chst eine Variable mit dem Schl�sselwort static und dem
	 * Standardwert null erzeugt. Sie speichert die Instanz dieser Klasse.
	 */

	private static PartnerProfileMapper partnerProfileMapper = null;

	/**
	 * Ein gesch�tzter Konstruktor verhindert das erneute erzeugen von weiteren
	 * Instanzen dieser Klasse.
	 */

	protected PartnerProfileMapper() {
	}

	/**
	 * Methode zum Sicherstellen der Singleton-Eigenschaft. Diese sorgt daf�r,
	 * dass nur eine einzige Instanz der PartnerProfileMapper-Klasse existiert.
	 * Aufgerufen wird die Klasse somit �ber
	 * PartnerProfileMapper.partnerProfileMapper() und nicht �ber den
	 * New-Operator.
	 * 
	 * @return partnerProfileMapper
	 */

	public static PartnerProfileMapper partnerProfileMapper() {
		if (partnerProfileMapper == null) {
			partnerProfileMapper = new PartnerProfileMapper();
		}
		return partnerProfileMapper;

	}

	/**
	 * F�gt ein PartnerProfile-Objekt der Datenbank hinzu.
	 * 
	 * @param partnerProfile
	 * @param company
	 * @param team
	 * @param person
	 * @param jobPosting
	 * @return partnerProfile
	 */
	public PartnerProfile insert(PartnerProfile partnerProfile) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			/**
			 * Abfrage des zuletzt hinzugef�gten Prim�rschl�ssels (id). Die
			 * aktuelle id wird um eins erh�ht.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid FROM partnerProfile");

			partnerProfile.setId(rs.getInt("maxid") + 1);
			stmt = con.createStatement();

			/**
			 * SQL-Anweisung zum Einf�gen des neuen PartnerProfile-Tupels in die
			 * Datenbank
			 */
			stmt.executeUpdate("INSERT INTO partnerProfile (id, dateCreated, dateChanged, company_id, team_id, "
					+ "person_id, jobPosting_id) VALUES (" + partnerProfile.getId() + ", '"
					+ partnerProfile.getDateCreated() + "', '" + partnerProfile.getDateChanged() + ", '"
					+ partnerProfile.getCompanyId() + ", '" + partnerProfile.getTeamId() + ", '"
					+ partnerProfile.getPersonId() + ", '" + partnerProfile.getJobPostingId() + "')");

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return partnerProfile;
	}

	/**
	 * Aktualisiert ein PartnerProfile-Objekt in der Datenbank.
	 * 
	 * @param partnerProfile
	 * @return partnerProfile
	 */
	public PartnerProfile update(PartnerProfile partnerProfile) {

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("UPDATE partnerProfile SET dateCreated='" + partnerProfile.getDateCreated()
					+ "', dateChanged= '" + partnerProfile.getDateChanged() + "' WHERE id= " + partnerProfile.getId());
		}

		catch (SQLException e2) {
			e2.printStackTrace();
		}
		return partnerProfile;
	}

	/**
	 * L�scht ein PartnerProfile-Objekt aus der Datenbank.
	 * 
	 * @param partnerProfile
	 */
	public void delete(PartnerProfile partnerProfile) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM partnerProfile WHERE id=" + partnerProfile.getId());
		}

		catch (SQLException e2) {
			e2.printStackTrace();
		}

	}

	/**
	 * Findet ein PartnerProfile-Objekt anhand der �bergebenen Id in der
	 * Datenbank.
	 * 
	 * @param id
	 * @return partnerProfil
	 */
	public PartnerProfile findById(int id) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {
			// leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausf�llen und als Query an die Datenbank senden
			ResultSet rs = stmt.executeQuery("SELECT id, dateCreated, dateChanged, company_id, team_id, "
					+ "person_id, jobPosting_id FROM partnerProfile WHERE id=" + id);

			/**
			 * Zu einem Prim�rschl�ssel exisitiert nur max ein Datenbank-Tupel,
			 * somit kann auch nur einer zur�ckgegeben werden. Es wird mit einer
			 * IF-Abfrage gepr�ft, ob es f�r den angefragten Prim�rschl�ssel ein
			 * DB-Tupel gibt.
			 */

			if (rs.next()) {
				// Ergebnis-Tupel in Objekt umwandeln
				PartnerProfile partnerProfile = new PartnerProfile();
				partnerProfile.setId(rs.getInt("id"));
				partnerProfile.setDateCreated(rs.getDate("dateCreated"));
				partnerProfile.setDateChanged(rs.getDate("dateChanged"));
				partnerProfile.setCompanyId(rs.getInt("company_id"));
				partnerProfile.setTeamId(rs.getInt("team_id"));
				partnerProfile.setPersonId(rs.getInt("person_id"));
				partnerProfile.setJobPostingId(rs.getInt("jobPosting_id"));

				return partnerProfile;
			}

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return null;
	}

	/**
	 * Findet alle PartnerProfile-Objekte in der Datenbank.
	 * 
	 * @return ArrayList<PartnerProfile>
	 */
	public ArrayList<PartnerProfile> findAll() {
		Connection con = DBConnection.connection();

		// Ergebnis-ArraList vorbereiten
		ArrayList<PartnerProfile> result = new ArrayList<PartnerProfile>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, dateCreated, dateChanged, company_id, team_id, "
					+ "person_id, jobPosting_id FROM partnerProfile ORDER BY id");

			// F�r jeden Eintrag wird ein PartnerProfil-Objekt erstellt.
			if (rs.next()) {
				PartnerProfile partnerProfile = new PartnerProfile();
				partnerProfile.setId(rs.getInt("id"));
				partnerProfile.setDateCreated(rs.getDate("dateCreated"));
				partnerProfile.setDateChanged(rs.getDate("dateChanged"));
				partnerProfile.setCompanyId(rs.getInt("company_id"));
				partnerProfile.setTeamId(rs.getInt("team_id"));
				partnerProfile.setPersonId(rs.getInt("person_id"));
				partnerProfile.setJobPostingId(rs.getInt("jobPosting_id"));

				// Hinzuf�gen des neuen Objekts zur Ergebnis-ArrayList
				result.add(partnerProfile);
			}

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return result;
	}

}
