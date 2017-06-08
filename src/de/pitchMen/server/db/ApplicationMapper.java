package de.pitchMen.server.db;

import java.sql.*;
import java.util.ArrayList;

import de.pitchMen.shared.bo.Application;
//PartnerProfileID und JobPostingID FK als getter in Application.java implementiert

/**
 * Die Klasse ApplicationMapper bildet Application-Objekte auf einer relationale
 * Datenbank ab. Ebenfalls ist es m�glich aus Datenbank-Tupel Java-Objekte zu
 * erzeugen.
 * 
 * Zur Verwaltung der Objekte implementiert die Mapper-Klasse entsprechende
 * Methoden (Speichern, Suchen, L�schen, Bearbeiten).
 */

public class ApplicationMapper {

	/**
	 * Die Klasse ApplicationMapper wird nur einmal instantiiert
	 * (Singelton-Eigenschaft). Die Variable ist mit static gekennzeichnet, da
	 * sie die einzige Instanz dieser Klasse speichert.
	 */
	private static ApplicationMapper applicationMapper = null;

	/**
	 * Ein gesch�tzter Konstrukter verhindert eine neue Instanz dieser Klasse zu
	 * erzeugen.
	 */
	protected ApplicationMapper() {
	}

	/**
	 * Methode zum sicherstellen der Singleton-Eigenschaft. Es wird somit
	 * sichergestellt, dass nur eine einzige Instanz der ApplicationMappers
	 * existiert.
	 * 
	 * @return applicationMapper
	 */
	public static ApplicationMapper applicationMapper() {
		if (applicationMapper == null) {
			applicationMapper = new ApplicationMapper();
		}
		return applicationMapper;
	}

	/**
	 * F�gt ein Application-Objekt der Datenbank hinzu. Und gibt das korrigierte
	 * Application-Objekt zur�ck.
	 * 
	 * @param application
	 * @return application
	 */
	public Application insert(Application application) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			/**
			 * Abfrage des als letztes hinzugef�gten Prim�rschl�ssels des
			 * Datensatzes. Der aktuelle Prim�rschl�ssel wird um eins erh�ht.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid FROM application");

			if (rs.next()) {
				application.setId(rs.getInt("maxid") + 1);
				stmt = con.createStatement();

				/**
				 * Ausf�hren der Einf�geoperation
				 */
				stmt.executeUpdate("INSERT INTO appilcation (id, text, dateCreated, jobPosting_id, partnerProfil_id)"
						+ "VALUES ( " + application.getId() + ", '" + application.getText() + "' ,'"
						+ application.getDateCreated() + "' ,'" + application.getJobPostingId() + "' ,'"
						+ application.getPartnerProfileId() + "')");
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return application;
	}

	/**
	 * Aktualisiert ein Application-Objekt in der Datenbank.
	 * 
	 * @param application
	 * @return application
	 */
	public Application update(Application application) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE application SET text='" + application.getText() + "', dateCreated= '"
					+ application.getDateCreated() + application.getStatus() + "WHERE id= " + application.getId());
		}

		catch (SQLException e2) {
			e2.printStackTrace();
		}

		return application;
	}

	/**
	 * L�scht ein Application-Objekt aus der Datenbank.
	 * 
	 * @param application
	 */
	public void delete(Application application) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM application " + "WHERE id=" + application.getId());
		}

		catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Findet ein Application-Objekt anhand der �bergebenen Id in der Datenbank.
	 * 
	 * @param id
	 * @return application
	 */
	public Application findById(int id) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT id, text, dateCreated, jobPosting_id, partnerProfil_id, status FROM application "
							+ "WHERE id =" + id);

			/**
			 * Der Prim�rschl�ssel (id) wird als eine Tupel zur�ckgegeben. Es
			 * wird gepr�ft ob ein Ergebnis vorliegt Das Ergebnis-Tupel wird in
			 * ein Objekt umgewandelt.
			 * 
			 */
			if (rs.next()) {
				Application application = new Application();
				application.setId(rs.getInt("id"));
				application.setText(rs.getString("text"));
				application.setDateCreated(rs.getDate("dateCreated"));
				application.setJobPostingId(rs.getInt("jobPosting_id"));
				application.setPartnerProfileId(rs.getInt("partnerProfil_id"));
				application.setStatus(rs.getString("status"));
				// Methodenaufruf FindByFK von Rating zur �bergaben des
				// Ratingobjekts

				return application;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}

		return null;
	}

	/**
	 * Findet alle Application-Objekte in der Datenbank.
	 * 
	 * @return ArrayList<Application>
	 */
	public ArrayList<Application> findAll() {
		Connection con = DBConnection.connection();

		ArrayList<Application> result = new ArrayList<Application>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT id, text, dateCreated, jobPosting_id, partnerProfil_id, status FROM application " + "ORDER BY id");

			/**
			 * Der Prim�rschl�ssel (id) wird als eine Tupel zur�ckgegeben. Es
			 * wird gepr�ft ob ein Ergebnis vorliegt Das Ergebnis-Tupel wird in
			 * ein Objekt umgewandelt.
			 * 
			 */
			while (rs.next()) {
				Application application = new Application();
				application.setId(rs.getInt("id"));
				application.setText(rs.getString("text"));
				application.setDateCreated(rs.getDate("dateCreated"));
				application.setJobPostingId(rs.getInt("jobPosting_id"));
				application.setPartnerProfileId(rs.getInt("partnerProfil_id"));
				application.setStatus(rs.getString("status"));

				result.add(application);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return result;
	}

	/**
	 * Findet ein Application-Objekt anhand des �bergebenen Namens in der
	 * Datenbank.
	 * 
	 * @param name
	 * @return ArryList<Application>
	 */
	public ArrayList<Application> findByText(String text) {
		Connection con = DBConnection.connection();

		ArrayList<Application> result = new ArrayList<Application>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT id, text, dateCreated, jobPosting_id, partnerProfil_id, status FROM application "
							+ "WHERE text LIKE " + text + "ORDER BY id");

			/**
			 * Der Prim�rschl�ssel (id) wird als eine TUpel zur�ck gegeben. Das
			 * Ergebnis-Tupel wird in ein Objekt umgewandelt.
			 * 
			 */
			while (rs.next()) {
				Application application = new Application();
				application.setId(rs.getInt("id"));
				application.setText(rs.getString("text"));
				application.setDateCreated(rs.getDate("dateCreated"));
				application.setJobPostingId(rs.getInt("jobPosting_id"));
				application.setPartnerProfileId(rs.getInt("partnerProfil_id"));
				application.setStatus(rs.getString("status"));

				result.add(application);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return result;
	}

	/**
	 * Eine JOIN-Klausel wird verwendet, um Zeilen aus zwei oder mehr Tabellen
	 * zu kombinieren, basierend auf einer verwandten Spalte zwischen ihnen.
	 * LEFT JOIN: Gib alle Datens�tze aus der linken Tabelle und die
	 * abgestimmten Datens�tze aus der rechten Tabelle zur�ck.
	 * 
	 * @return ArryList<Application>
	 */
	public ArrayList<Application> findAllAsJoin() {
		Connection con = DBConnection.connection();

		ArrayList<Application> result = new ArrayList<Application>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT application.id, application.text, "
					+ "application.dateCreated, application.jobPosting_id, application.partnerProfil_id, application.status "
					+ "rating.id, rating.statement, rating.score "
					+ "FROM application LEFT JOIN rating ON application.id = rating.id " + "ORDER BY application.id");

			/**
			 * Der Prim�rschl�ssel (id) wird als eine Tupel zur�ckgegeben. Es
			 * wird gepr�ft ob ein Ergebnis vorliegt Das Ergebnis-Tupel wird in
			 * ein Objekt umgewandelt.
			 * 
			 */
			while (rs.next()) {
				ArrayList<String> applicationRating = new ArrayList<String>();
				applicationRating.add("application.id");
				applicationRating.add("application.text");
				applicationRating.add("application.dateCreated");
				applicationRating.add("application.jobPosting_id");
				applicationRating.add("application.partnerProfil_id");
				applicationRating.add("application.status");
				applicationRating.add("rating.id");
				applicationRating.add("rating.statement");
				applicationRating.add("rating.score");

			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * Bei einer JOIN-Klausel werden Zeilen aus zwei Tabellen zusammengef�hrt.
	 * Bei dem INNER JOIN verbundenen Tabellen werden nur die Datens�tze
	 * �bernommen / angezeigt die in beiden Tabellen einen Treffer haben.
	 * Methode u.a. f�r Aufgabenstellung Nr. 6
	 * 
	 * @return ArryList<Application>
	 */

	public ArrayList<Application> findApplicationByPersonId(int personId) {
		Connection con = DBConnection.connection();
		
		ArrayList<Application> result = new ArrayList<Application>();
		
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM application INNER JOIN partnerProfile"
					+ "ON application.partnerProfil_id = partnerProfile.id WHERE partnerProfile.person_id = "
					+ personId);

			/**
			 * Anhand der �bergebenen PersonId werden die dazugeh�rigen
			 * Application-Tupel (Bewerbungen) aus der Datenbank abgefragt.
			 */

			while (rs.next()) {
				Application application = new Application();
				application.setId(rs.getInt("id"));
				application.setText(rs.getString("text"));
				application.setDateCreated(rs.getDate("dateCreated"));
				application.setJobPostingId(rs.getInt("jobPosting_id"));
				application.setPartnerProfileId(rs.getInt("partnerProfil_id"));
				application.setStatus(rs.getString("status"));

				result.add(application);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return result;
	}
	

	/**
	 * 
	 * Methode ist f�r das herausfinden, ob Beziehungen zwischen Application und JobPostings bestehen, 
	 * um Applications l�schen zu k�nnen. Da zu einem JobPosting (Ausschreibung) mehrere Bewerbungen 
	 * bestehen k�nnen, muss die R�ckgabe in einer ArrayList mit den jeweiligen Application-Objekten, erfolgen. 
	 * 
	 * @param jobPostingId
	 * @return ArryList<Application>
	 */

	public ArrayList<Application> findApplicationByJobPostingId(int jobPostingId) {
		Connection con = DBConnection.connection();
		
		ArrayList<Application> result = new ArrayList<Application>();
		
		/**
		 * Anhand der �bergebenen jobPostingId werden die dazugeh�rigen
		 * Application-Tupel (Bewerbungen) aus der Datenbank abgefragt.
		 */
		
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM application "
					+ "WHERE jobPosting_id = " + jobPostingId);

		
		/**
		 * Die in dem ResultSet gespeicherten DB-Tupel werden in ein Applicationobjekt 
		 * gespeichert und anschlie�end das Tupel der ArrayList hinzugef�gt.
		 */
			while (rs.next()) {
				Application application = new Application();
				application.setId(rs.getInt("id"));
				application.setText(rs.getString("text"));
				application.setDateCreated(rs.getDate("dateCreated"));
				application.setJobPostingId(rs.getInt("jobPosting_id"));
				application.setPartnerProfileId(rs.getInt("partnerProfil_id"));
				application.setStatus(rs.getString("status"));
				//hinzuf�gen des Application-Java Objets der ArrayList result
				result.add(application);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return result;
	}
}
