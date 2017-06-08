package de.pitchMen.server.db;

import java.sql.*;
import java.util.ArrayList;

import de.pitchMen.shared.bo.Project;
//MarcetplaceId und PersonId FK als getter in Person.java implementiert

/**
 * Bildet Project-Objekte auf eine relationale Datenbank ab. Ebenfalls ist es
 * m�glich aus Datenbank-Tupel Java-Objekte zu erzeugen.
 *
 * @author Lars
 */
public class ProjectMapper {

	/**
	 * Die Klasse ProjectMapper wird nur einmal instantiiert
	 * (Singleton-Eigenschaft). Damit diese Eigenschaft erf�llt werden kann,
	 * wird zun�chst eine Variable mit dem Schl�sselwort static und dem
	 * Standardwert null erzeugt. Sie speichert die Instanz dieser Klasse.
	 */

	private static ProjectMapper projectMapper = null;

	/**
	 * Ein gesch�tzter Konstruktor verhindert das erneute erzeugen von weiteren
	 * Instanzen dieser Klasse.
	 */

	protected ProjectMapper() {

	}

	/**
	 * Methode zum Sicherstellen der Singleton-Eigenschaft. Diese sorgt daf�r,
	 * dass nur eine einzige Instanz der ProjectMapper-Klasse existiert.
	 * Aufgerufen wird die Klasse somit �ber ProjectMapper.projectMapper() und
	 * nicht �ber den New-Operator.
	 * 
	 * @return projectMapper
	 */

	public static ProjectMapper projectMapper() {
		if (projectMapper == null) {
			projectMapper = new ProjectMapper();
		}
		return projectMapper;

	}

	/**
	 * F�gt ein Project-Objekt der Datenbank hinzu.
	 * 
	 * @param project
	 * @param marketplace
	 * @param person
	 * 
	 * @return project
	 */
	public Project insert(Project project) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			/**
			 * Abfrage des zuletzt hinzugef�gten Prim�rschl�ssels (id). Die
			 * aktuelle id wird um eins erh�ht.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid FROM project");

			project.setId(rs.getInt("maxid") + 1);
			stmt = con.createStatement();

			/**
			 * SQL-Anweisung zum Einf�gen des neuen Project-Tupels in die
			 * Datenbank
			 */
			stmt.executeUpdate(
					"INSERT INTO project (id, title, description, dateOpened, dateClosed, marketplace_id, person_id)"
							+ "VALUES (" + project.getId() + ", '" + project.getTitle() + "', '"
							+ project.getDescription() + "', '" + project.getDateOpened() + "', '"
							+ project.getDateClosed() + "', '" + project.getMarketplaceId() + "', '"
							+ project.getPersonId() + "')");

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return project;
	}

	/**
	 * Aktualisiert ein Project-Objekt in der Datenbank.
	 * 
	 * @param project
	 * @return project
	 */
	public Project update(Project project) {

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("UPDATE project SET title='" + project.getTitle() + "', description= '"
					+ project.getDescription() + "', dateOpened= '" + project.getDateOpened() + "', dateClosed= '"
					+ project.getDateClosed() + "' WHERE id= " + project.getId());
		}

		catch (SQLException e2) {
			e2.printStackTrace();
		}
		return project;
	}

	/**
	 * L�scht ein Project-Objekt aus der Datenbank.
	 * 
	 * @param project
	 */
	public void delete(Project project) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM project WHERE id=" + project.getId());
		}

		catch (SQLException e2) {
			e2.printStackTrace();
		}

	}

	/**
	 * Findet ein Project-Objekt anhand der �bergebenen ID in der Datenbank.
	 * 
	 * @param id
	 * @return person
	 */
	public Project findById(int id) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT id, title, description, dateOpened, dateClosed ,marketplace_id, person_id "
							+ "FROM project WHERE id=" + id);

			/**
			 * Zu einem Prim�rschl�ssel exisitiert nur max ein Datenbank-Tupel,
			 * somit kann auch nur einer zur�ckgegeben werden. Es wird mit einer
			 * IF-Abfragen gepr�ft, ob es f�r den angefragten Prim�rschl�ssel
			 * ein DB-Tupel gibt.
			 */

			if (rs.next()) {
				Project project = new Project();
				project.setId(rs.getInt("id"));
				project.setTitle(rs.getString("title"));
				project.setDescription(rs.getString("description"));
				project.setDateOpened(rs.getDate("dateOpened"));
				project.setDateClosed(rs.getDate("dateClosed"));
				project.setMarketplaceId(rs.getInt("marketplace_id"));
				project.setPersonId(rs.getInt("person_id"));

				return project;
			}

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return null;
	}

	/**
	 * Findet alle Project-Objekte in der Datenbank.
	 * 
	 * @return ArrayList<Project>
	 */
	public ArrayList<Project> findAll() {
		Connection con = DBConnection.connection();

		ArrayList<Project> result = new ArrayList<Project>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT id, title, description, dateOpened, dateClosed, marketplace_id, person_id"
							+ " FROM project ORDER BY id");

			while (rs.next()) {
				Project project = new Project();
				project.setId(rs.getInt("id"));
				project.setTitle(rs.getString("title"));
				project.setDescription(rs.getString("description"));
				project.setDateOpened(rs.getDate("dateOpened"));
				project.setDateClosed(rs.getDate("dateClosed"));
				project.setMarketplaceId(rs.getInt("marketplace_id"));
				project.setPersonId(rs.getInt("person_id"));

				result.add(project);

			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return result;
	}

	/**
	 * Findet Project-Objekte anhand des �bergebenen Start-Datums in der
	 * Datenbank.
	 * 
	 * @param dateOpened
	 * @return ArrayList<Project>
	 */
	public ArrayList<Project> findByDateOpened(Date dateOpened) {
		Connection con = DBConnection.connection();

		ArrayList<Project> result = new ArrayList<Project>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT id, title, description, dateOpened, dateClosed, marketplace_id, person_id"
							+ " FROM project WHERE dateOpened= '" + dateOpened + "' ORDER BY id");

			while (rs.next()) {
				Project project = new Project();
				project.setId(rs.getInt("id"));
				project.setTitle(rs.getString("title"));
				project.setDescription(rs.getString("description"));
				project.setDateOpened(rs.getDate("dateOpened"));
				project.setDateClosed(rs.getDate("dateClosed"));
				project.setMarketplaceId(rs.getInt("marketplace_id"));
				project.setPersonId(rs.getInt("person_id"));

				result.add(project);

			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return result;
	}

	/**
	 * Findet ein Project-Objekt anhand des �bergebenen End-Datums in der
	 * Datenbank.
	 * 
	 * @param dateClosed
	 * @return ArrayList<Project>
	 */
	public ArrayList<Project> findByDateClosed(Date dateClosed) {
		Connection con = DBConnection.connection();

		ArrayList<Project> result = new ArrayList<Project>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT id, title, description, dateOpened, dateClosed, marketplace_id, person_id"
							+ " FROM project WHERE dateClosed= '" + dateClosed + "' ORDER BY id");

			while (rs.next()) {
				Project project = new Project();
				project.setId(rs.getInt("id"));
				project.setTitle(rs.getString("title"));
				project.setDescription(rs.getString("description"));
				project.setDateOpened(rs.getDate("dateOpened"));
				project.setDateClosed(rs.getDate("dateClosed"));
				project.setMarketplaceId(rs.getInt("marketplace_id"));
				project.setPersonId(rs.getInt("person_id"));

				result.add(project);

			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return result;
	}

	/**
	 * Findet ein Project-Objekt anhand des �bergebenen Titels in der Datenbank.
	 * 
	 * @param title
	 * @return ArrayList<Project>
	 */
	public ArrayList<Project> findByTitle(String title) {
		Connection con = DBConnection.connection();

		ArrayList<Project> result = new ArrayList<Project>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT id, title, description, dateOpened, dateClosed, marketplace_id, person_id"
							+ " FROM project WHERE title= '" + title + "' ORDER BY id");

			while (rs.next()) {
				Project project = new Project();
				project.setId(rs.getInt("id"));
				project.setTitle(rs.getString("title"));
				project.setDescription(rs.getString("description"));
				project.setDateOpened(rs.getDate("dateOpened"));
				project.setDateClosed(rs.getDate("dateClosed"));
				project.setMarketplaceId(rs.getInt("marketplace_id"));
				project.setPersonId(rs.getInt("person_id"));

				result.add(project);

			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Die Methode findParticipationByPersonId sucht anhand der �bergebenen personId in der Datenbank
	 * die dazugeh�rigen Participations (Beteiligungen)-Tupel ab.
	 * Die Methode dient zur Aufgabenbew�ltung aus Aufgabe Nummer 7. 
	 * 
	 * @param personId
	 * @return ArrayList<Project>
	 */
	
	public ArrayList<Project> findProjectsByPersonId(int personId) {
		Connection con = DBConnection.connection();

		ArrayList<Project> result = new ArrayList<Project>();
		/**
		 * Das SQL-Statement sucht anhand des �bergebenen Parameters die
		 * Beteiligungen ab und grouped sie anhand der ProjectId sodass jeder
		 * EIntrag nur einmal vorkommt
		 * 
		 */
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM project " + "INNER JOIN person_has_participation"
					+ "ON (person_has_participation.person_id = project.person_id)" + "WHERE project.person_id ="
					+ personId + " GROUP BY project.id;");

			while (rs.next()) {
				Project project = new Project();
				project.setId(rs.getInt("id"));
				project.setTitle(rs.getString("title"));
				project.setDescription(rs.getString("description"));
				project.setDateOpened(rs.getDate("dateOpened"));
				project.setDateClosed(rs.getDate("dateClosed"));
				project.setMarketplaceId(rs.getInt("marketplace_id"));
				project.setPersonId(rs.getInt("person_id"));

				result.add(project);

			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Die Methode findProjectsByMarketplaceId sucht alle marketPlace-Tupel
	 * zu der �bergebenen marketplaceId in der Datenbank ab und setzt diese in eine ArrayList.
	 * Die Methode ist zur Umsetzung der Anforderung, ein Marketplace zu l�schen, aber davor dazugeh�rige 
	 * Tabellen-Beziehungen ebenfalls zu l�schen..
	 * 
	 * @param marketplaceId
	 * @return ArrayList<Project>
	 */
	
	public ArrayList<Project> findProjectsByMarketplaceId(int marketplaceId) {
		Connection con = DBConnection.connection();

		ArrayList<Project> result = new ArrayList<Project>();
		/**
		 * Das SQL-Statement sucht anhand des �bergebenen Parameters die
		 * Projekte ab.
		 * 
		 */
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM project"
					+ "INNER JOIN marketplace"
					+ "ON marketplace.id = project.marketplace_id"
					+ "WHERE marketplace.id ="+ marketplaceId);

			while (rs.next()) {
				Project project = new Project();
				project.setId(rs.getInt("id"));
				project.setTitle(rs.getString("title"));
				project.setDescription(rs.getString("description"));
				project.setDateOpened(rs.getDate("dateOpened"));
				project.setDateClosed(rs.getDate("dateClosed"));
				project.setMarketplaceId(rs.getInt("marketplace_id"));
				project.setPersonId(rs.getInt("person_id"));

				result.add(project);

			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return result;
	}

}