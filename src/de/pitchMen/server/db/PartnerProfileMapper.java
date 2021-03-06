package de.pitchMen.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.pitchMen.shared.bo.PartnerProfile;

/**
 * Bildet PartnerProfile-Objekte auf eine relationale Datenbank ab. Ebenfalls
 * ist es möglich aus Datenbank-Tupel Java-Objekte zu erzeugen.
 * 
 * Zur Verwaltung der Objekte implementiert die Mapper-Klasse entsprechende
 * Methoden (insert, search, delete, update).
 * 
 * @author Lars
 */
public class PartnerProfileMapper {

	/**
	 * Die Klasse PartnerProfileMapper wird nur einmal instantiiert
	 * (Singleton-Eigenschaft). Damit diese Eigenschaft erfüllt werden kann,
	 * wird zunächst eine Variable mit dem Schlüsselwort static und dem
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
	 * Methode zum Sicherstellen der Singleton-Eigenschaft. Diese sorgt dafür,
	 * dass nur eine einzige Instanz der PartnerProfileMapper-Klasse existiert.
	 * Aufgerufen wird die Klasse somit über
	 * PartnerProfileMapper.partnerProfileMapper() und nicht über den
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
	 * F�gt ein PartnerProfile-Objekt (auf Basis einer Person) der Datenbank hinzu.
	 * 
	 * @param partnerProfile
	 * @return partnerProfile
	 */
	public PartnerProfile insertPartnerProfileForPerson(PartnerProfile partnerProfile) {
		/**
		 *  DB-Verbindung holen.
		 */
		Connection con = DBConnection.connection();

		try {
			/**
			 * leeres SQL-Statement (JDBC) anlegen.
			 */
			Statement stmt = con.createStatement();
			/**
			 * Abfrage des zuletzt hinzugefügten Primärschlüssels (id). Die
			 * aktuelle id wird um eins erhöht.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid FROM partnerProfile");

			if(rs.next()) {
				partnerProfile.setId(rs.getInt("maxid") + 1);	
			}
			stmt = con.createStatement();
			/**
			 * SQL-Anweisung zum Einfügen des neuen PartnerProfile-Tupels in die Datenbank
			 */
			stmt.executeUpdate("INSERT INTO partnerProfile (id, dateCreated, dateChanged, person_id) VALUES (" + partnerProfile.getId() + ", '"
					+ partnerProfile.getDateCreated().toString() + "', '" + partnerProfile.getDateChanged().toString() + "', "
					+ partnerProfile.getPersonId() + ")");
		/**
		 * Das Aufrufen des printStackTrace bietet die M�glichkeit, die
		 * Fehlermeldung genauer zu analyisieren. Es werden Informationen dazu
		 * ausgegeben, was passiert ist und wo im Code es passiert ist.
		 */	
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return partnerProfile;
	}

	/**
	 * Fügt ein PartnerProfile-Objekt, auf Basis einer Company, der Datenbank hinzu.
	 * 
	 * @param partnerProfile
	 * @return partnerProfile
	 */
	public PartnerProfile insertPartnerProfileForCompany(PartnerProfile partnerProfile) {
		/**
		 *  DB-Verbindung holen.
		 */
		Connection con = DBConnection.connection();

		try {
			/**
			 * leeres SQL-Statement (JDBC) anlegen.
			 */
			Statement stmt = con.createStatement();
			/**
			 * Abfrage des zuletzt hinzugefügten Primärschlüssels (id). Die
			 * aktuelle id wird um eins erhöht.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid FROM partnerProfile");

			if(rs.next()) {
				partnerProfile.setId(rs.getInt("maxid") + 1);	
			}
			stmt = con.createStatement();
			/**
			 * SQL-Anweisung zum Einfügen des neuen PartnerProfile-Tupels in die Datenbank
			 */
			stmt.executeUpdate("INSERT INTO partnerProfile (id, dateCreated, dateChanged, company_id, person_id) VALUES (" + partnerProfile.getId() + ", '"
					+ partnerProfile.getDateCreated().toString() + "', '" + partnerProfile.getDateChanged().toString() + "', "
					+ partnerProfile.getCompanyId() + ", " + partnerProfile.getPersonId() + ")");
		/**
		 * Das Aufrufen des printStackTrace bietet die M�glichkeit, die
		 * Fehlermeldung genauer zu analyisieren. Es werden Informationen dazu
		 * ausgegeben, was passiert ist und wo im Code es passiert ist.
		 */	
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return partnerProfile;
	}

	/**
	 * F�gt ein PartnerProfile-Objekt, auf Basis des Teams, der Datenbank hinzu.
	 * 
	 * @param partnerProfile
	 * @return partnerProfile
	 */
	public PartnerProfile insertPartnerProfileForTeam(PartnerProfile partnerProfile) {
		/**
		 *  DB-Verbindung holen.
		 */
		Connection con = DBConnection.connection();

		try {
			/**
			 * leeres SQL-Statement (JDBC) anlegen.
			 */
			Statement stmt = con.createStatement();
			/**
			 * Abfrage des zuletzt hinzugefügten Primärschlüssels (id). Die
			 * aktuelle id wird um eins erhöht.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid FROM partnerProfile");

			if(rs.next()) {
				partnerProfile.setId(rs.getInt("maxid") + 1);	
			}
			stmt = con.createStatement();
			/**
			 * SQL-Anweisung zum Einfügen des neuen PartnerProfile-Tupels in die Datenbank
			 */
			stmt.executeUpdate("INSERT INTO partnerProfile (id, dateCreated, dateChanged, team_id, person_id"
					+ ") VALUES (" + partnerProfile.getId() + ", '"
					+ partnerProfile.getDateCreated().toString() + "', '" + partnerProfile.getDateChanged().toString() + "', "
					+ partnerProfile.getTeamId() + ", " + partnerProfile.getPersonId() + ")");
		/**
		 * Das Aufrufen des printStackTrace bietet die M�glichkeit, die
		 * Fehlermeldung genauer zu analyisieren. Es werden Informationen dazu
		 * ausgegeben, was passiert ist und wo im Code es passiert ist.
		 */	
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return partnerProfile;
	}

	public PartnerProfile insertPartnerProfileForJobPosting(PartnerProfile partnerProfile) {
		/**
		 *  DB-Verbindung holen.
		 */
		Connection con = DBConnection.connection();

		try {
			/**
			 * leeres SQL-Statement (JDBC) anlegen.
			 */
			Statement stmt = con.createStatement();
			/**
			 * Abfrage des zuletzt hinzugefügten Primärschlüssels (id). Die
			 * aktuelle id wird um eins erhöht.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid FROM partnerProfile");

			if(rs.next()) {
				partnerProfile.setId(rs.getInt("maxid") + 1);	
			}
			stmt = con.createStatement();
			/**
			 * SQL-Anweisung zum Einfügen des neuen PartnerProfile-Tupels in die Datenbank
			 */
			stmt.executeUpdate("INSERT INTO partnerProfile (id, dateCreated, dateChanged, jobPosting_id) VALUES (" + partnerProfile.getId() + ", '"
					+ partnerProfile.getDateCreated().toString() + "', '" + partnerProfile.getDateChanged().toString() + "', "
					+ partnerProfile.getJobPostingId() + ")");
		/**
		 * Das Aufrufen des printStackTrace bietet die M�glichkeit, die
		 * Fehlermeldung genauer zu analyisieren. Es werden Informationen dazu
		 * ausgegeben, was passiert ist und wo im Code es passiert ist.
		 */	
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
			/**
			 * SQL-Anweisung zur Aktualisierung des übergebenen Datensatzes in der Datenbank.
			 */
			stmt.executeUpdate("UPDATE partnerProfile SET dateCreated='" + partnerProfile.getDateCreated()
					+ "', dateChanged= '" + partnerProfile.getDateChanged() + "' WHERE id= " + partnerProfile.getId());
		}
		/**
		 * Das Aufrufen des printStackTrace bietet die M�glichkeit, die
		 * Fehlermeldung genauer zu analyisieren. Es werden Informationen dazu
		 * ausgegeben, was passiert ist und wo im Code es passiert ist.
		 */	
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
			/**
			 * SQL-Anweisung zum Löschen des übergebenen Datensatzes in der Datenbank.
			 */
			stmt.executeUpdate("DELETE FROM partnerProfile WHERE id=" + partnerProfile.getId());
		}
		/**
		 * Das Aufrufen des printStackTrace bietet die M�glichkeit, die
		 * Fehlermeldung genauer zu analyisieren. Es werden Informationen dazu
		 * ausgegeben, was passiert ist und wo im Code es passiert ist.
		 */	
		catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Findet ein PartnerProfile-Objekt anhand der �bergebenen Id in der Datenbank.
	 * 
	 * @param id
	 * @return partnerProfil
	 */
	public PartnerProfile findById(int id) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			/**
			 * SQL-Anweisung zum Finden des Datensatzes, anhand der übergebenen Id, in der Datenbank.
			 */
			ResultSet rs = stmt.executeQuery("SELECT id, dateCreated, dateChanged, company_id, team_id, "
					+ "person_id, jobPosting_id FROM partnerProfile WHERE id=" + id);
			/**
			 * Zu einem Primärschlüssel exisitiert nur max ein Datenbank-Tupel,
			 * somit kann auch nur einer zurückgegeben werden. Es wird mit einer
			 * IF-Abfrage geprüft, ob es für den angefragten Primärschlüssel ein
			 * DB-Tupel gibt.
			 */
			if (rs.next()) {
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
			/**
			 * Das Aufrufen des printStackTrace bietet die M�glichkeit, die
			 * Fehlermeldung genauer zu analyisieren. Es werden Informationen dazu
			 * ausgegeben, was passiert ist und wo im Code es passiert ist.
			 */	
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
		/**
		 * Erzeugen einer ArrayList.
		 */
		ArrayList<PartnerProfile> result = new ArrayList<PartnerProfile>();

		try {
			Statement stmt = con.createStatement();
			/**
			 * SQL-Anweisung zum Finden aller Datensatzes in der Datenbank, sortiert nach der Id.
			 */
			ResultSet rs = stmt.executeQuery("SELECT id, dateCreated, dateChanged, company_id, team_id, "
					+ "person_id, jobPosting_id FROM partnerProfile ORDER BY id");
			/**
			 * Da es sein kann, dass mehr als nur ein Datenbank-Tupel in der
			 * Tabelle partnerProfile vorhanden ist, muss das Abfragen des ResultSet so
			 * oft erfolgen (while-Schleife), bis alle Tupel durchlaufen wurden.
			 * Die DB-Tupel werden in Java-Objekte transformiert und
			 * anschließend der ArrayList hinzugefügt.
			 */
			while (rs.next()) {
				PartnerProfile partnerProfile = new PartnerProfile();
				partnerProfile.setId(rs.getInt("id"));
				partnerProfile.setDateCreated(rs.getDate("dateCreated"));
				partnerProfile.setDateChanged(rs.getDate("dateChanged"));
				partnerProfile.setCompanyId(rs.getInt("company_id"));
				partnerProfile.setTeamId(rs.getInt("team_id"));
				partnerProfile.setPersonId(rs.getInt("person_id"));
				partnerProfile.setJobPostingId(rs.getInt("jobPosting_id"));
				result.add(partnerProfile);
			}
			/**
			 * Das Aufrufen des printStackTrace bietet die M�glichkeit, die
			 * Fehlermeldung genauer zu analyisieren. Es werden Informationen dazu
			 * ausgegeben, was passiert ist und wo im Code es passiert ist.
			 */	
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Findet ein PartnerProfile-Tupel anhand der übergebenen jobPostingId in der
	 * Datenbank. Anschließend wird es als Java-Objekt in der Variablen partnerProfile 
	 * vom Typ PartnerProfile gespeichert. Methode zum Suchen von Partnerprofilen 
	 * bestehende Beziehungen/Datensätze zu löschen.
	 * 
	 * Mit der Inner-Join-Klausel wird erreicht, dass nur die Datensätze zusammengefügt
	 * werden, zu den es jeweils auch ein Gegenstück in der verknüpften 
	 * Tabelle gibt. Da es möglich ist, dass ein Partnerprofil mehrere Eigenschaften hat,
	 * mössen die PartnerProfile-Objekte in einer ArrayList gespeichert werden.
	 * 
	 * @param jobPostingId
	 * @return partnerProfil
	 */
	public PartnerProfile findPartnerProfileByJobPostingId(int jobPostingId) {
		Connection con = DBConnection.connection();

		try {
			
			Statement stmt = con.createStatement();
			/**
			 * SQL-Anweisung zum Finden des Datensatzes, nach der gesuchten jobPostingId, in der Datenbank.
			 */
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM partnerProfile "
					+ "WHERE jobPosting_id = " + jobPostingId);
			/**
			 * Zu einem JobPosting exisitiert nur max ein Datenbank-Tupel,
			 * somit kann auch nur einer zurückgegeben werden. Es wird mit einer
			 * IF-Abfrage geprüft, ob es für den angefragten Primärschlüssel ein
			 * DB-Tupel gibt.
			 */
			if (rs.next()) {
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
			/**
			 * Das Aufrufen des printStackTrace bietet die M�glichkeit, die
			 * Fehlermeldung genauer zu analyisieren. Es werden Informationen dazu
			 * ausgegeben, was passiert ist und wo im Code es passiert ist.
			 */	
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Findet ein PartnerProfile-Tupel anhand der übergebenen companyId in der
	 * Datenbank. Anschließend wird es als Java-Objekt in der Variablen partnerProfile 
	 * vom Typ PartnerProfile gespeichert. Methode zum Suchen von Partnerprofilen 
	 * bestehende Beziehungen/Datensätze zu löschen.
	 *
	 * Mit der Inner-Join-Klausel wird erreicht, dass nur die Datensätze zusammengefügt
	 * werden, zu den es jeweils auch ein Gegenstück in der verknüpften 
	 * Tabelle gibt. Da es möglich ist, dass ein Partnerprofil mehrere Eigenschaften hat,
	 * müssen 
	 * 
	 * @param companyId
	 * @return partnerProfil
	 */
	public PartnerProfile findPartnerProfileByCompanyId(int companyId) {
		Connection con = DBConnection.connection();

		try {
			
			Statement stmt = con.createStatement();
			/**
			 * SQL-Anweisung zum Finden des Datensatzes, nach der gesuchten companyId, in der Datenbank.
			 */
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM partnerProfile WHERE company_id = " + companyId);
			/**
			 * Zu einer companyId exisitiert nur max ein Datenbank-Tupel,
			 * somit kann auch nur einer zurückgegeben werden. Es wird mit einer
			 * IF-Abfrage geprüft, ob es für den angefragten Primärschlüssel ein
			 * DB-Tupel gibt.
			 */
			if (rs.next()) {
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
			/**
			 * Das Aufrufen des printStackTrace bietet die M�glichkeit, die
			 * Fehlermeldung genauer zu analyisieren. Es werden Informationen dazu
			 * ausgegeben, was passiert ist und wo im Code es passiert ist.
			 */	
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Methode zum Suchen von Partnerprofilen in Bezug zu teamIds um
	 * bestehende Beziehungen/Datensätze zu löschen.
	 * 
	 * Mit der Inner-Join-Klausel wird erreicht, dass nur die Datensätze zusammengefügt
	 * werden, zu den es jeweils auch ein Gegenstück in der verknüpften 
	 * Tabelle gibt. Da es möglich ist, dass ein Partnerprofil mehrere Eigenschaften hat,
	 * müssen 
	 * 
	 * @param teamId
	 * @return partnerProfil
	 */
	public PartnerProfile findPartnerProfileByTeamId(int teamId) {
		Connection con = DBConnection.connection();

		try {
			
			Statement stmt = con.createStatement();
			/**
			 * SQL-Anweisung zum Finden des Datensatzes, nach der gesuchten companyId, in der Datenbank.
			 */
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM partnerProfile WHERE company_id = " + teamId);
			/**
			 * Zu einer companyId exisitiert nur max ein Datenbank-Tupel,
			 * somit kann auch nur einer zurückgegeben werden. Es wird mit einer
			 * IF-Abfrage geprüft, ob es für den angefragten Primärschlüssel ein
			 * DB-Tupel gibt.
			 */
			if (rs.next()) {
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
			/**
			 * Das Aufrufen des printStackTrace bietet die M�glichkeit, die
			 * Fehlermeldung genauer zu analyisieren. Es werden Informationen dazu
			 * ausgegeben, was passiert ist und wo im Code es passiert ist.
			 */	
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Methode zum Suchen von Partnerprofilen in Bezug zu personIds um
	 * bestehende Beziehungen/Datensätze zu löschen.
	 * 
	 * Mit der Inner-Join-Klausel wird erreicht, dass nur die Datensätze zusammengefügt
	 * werden, zu den es jeweils auch ein Gegenstück in der verknüpften 
	 * Tabelle gibt. Da es möglich ist, dass ein Partnerprofil mehrere Eigenschaften hat,
	 * müssen 
	 * 
	 * @param PersonId
	 * @return partnerProfil
	 */
	public PartnerProfile findPartnerProfileByPersonId(int personId) {
		Connection con = DBConnection.connection();

		try {
			
			Statement stmt = con.createStatement();
			/**
			 * SQL-Anweisung zum Finden des Datensatzes, nach der gesuchten personId, in der Datenbank.
			 */
			ResultSet rs = stmt.executeQuery("SELECT * FROM partnerProfile WHERE person_id = " +  personId);
			/**
			 * Zu einer companyId exisitiert nur max ein Datenbank-Tupel,
			 * somit kann auch nur einer zurückgegeben werden. Es wird mit einer
			 * IF-Abfrage geprüft, ob es für den angefragten Primärschlüssel ein
			 * DB-Tupel gibt.
			 */
			if (rs.next()) {
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
			/**
			 * Das Aufrufen des printStackTrace bietet die M�glichkeit, die
			 * Fehlermeldung genauer zu analyisieren. Es werden Informationen dazu
			 * ausgegeben, was passiert ist und wo im Code es passiert ist.
			 */	
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return null;
	}


}
