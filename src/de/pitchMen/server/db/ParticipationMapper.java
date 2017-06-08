package de.pitchMen.server.db;

import java.sql.*;
import java.util.ArrayList;

import de.pitchMen.shared.bo.Participation;

/**
 * Bildet Participation-Objekte auf eine relationale Datenbank ab. Ebenfalls ist
 * es m�glich aus Datenbank-Tupel Java-Objekte zu erzeugen.
 *
 * @author Heike
 */

public class ParticipationMapper {

	/**
	 * Die Klasse ParticipationMapper wird nur einmal instantiiert
	 * (Singleton-Eigenschaft). Damit diese Eigenschaft erf�llt werden kann,
	 * wird zun�chst eine Variable mit dem Schl�sselwort static und dem
	 * Standardwert null erzeugt. Sie speichert die Instanz dieser Klasse.
	 */

	private static ParticipationMapper participationMapper = null;

	/**
	 * Ein gesch�tzter Konstruktor verhindert das erneute erzeugen von weiteren
	 * Instanzen dieser Klasse.
	 */

	protected ParticipationMapper() {

	}

	/**
	 * Methode zum Sicherstellen der Singleton-Eigenschaft. Diese sorgt daf�r,
	 * dass nur eine einzige Instanz der ProjectMapper-Klasse existiert.
	 * Aufgerufen wird die Klasse somit �ber
	 * ParticipationMapper.participationMapper() und nicht �ber den
	 * New-Operator.
	 * 
	 * @return ParticipationMapper
	 */

	public static ParticipationMapper participationMapper() {
		if (participationMapper == null) {
			participationMapper = new ParticipationMapper();
		}
		return participationMapper;

	}

	/**
	 * F�gt ein Participation-Objekt der Datenbank hinzu.
	 * 
	 * @param participation
	 * @param marketplace
	 * @param person
	 * 
	 * @return participation
	 */
	public Participation insert(Participation participation) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
		//	Statement stmt2 = con.createStatement();
		//	Statement stmt3 = con.createStatement();
			
			/**
			 * Abfrage des zuletzt hinzugef�gten Prim�rschl�ssels (id). Die
			 * aktuelle id wird um eins erh�ht.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid FROM participation");

			participation.setId(rs.getInt("maxid") + 1);
			stmt = con.createStatement();
		//	stmt2 = con.createStatement();
		//	stmt3 = con.createStatement();

			/**
			 * SQL-Anweisung zum Einf�gen des neuen Participation-Tupels in die
			 * Datenbank
			 */
			stmt.executeUpdate("INSERT INTO participation (id, workload, dateOpened, dateClosed)" + "VALUES ("
					+ participation.getId() + "', '" + participation.getDateOpened() + "', '"
					+ participation.getDateClosed());
			
		//	stmt2.executeUpdate("INSERT INTO participation_has_project (participation_id, project_id)" + "VALUES ("
		//			+ participation.getId() + project.getId() + "' WHERE id= " + participation.getId());
			
		//	stmt3.executeUpdate("INSERT INTO person_has_participation (person_id, participation_id)" + "VALUES ("
		//			+ person.getId() + participation.getId());
		}

		catch (SQLException e2) {
			e2.printStackTrace();
		}

		return participation;
	}

	/**
	 * Aktualisiert ein Participation-Objekt in der Datenbank.
	 * 
	 * @param participation
	 * @return participation
	 */
	public Participation update(Participation participation) {

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("UPDATE participation SET workload= '" + participation.getWorkload() + "' dateOpened= '"
					+ participation.getDateOpened() + "', dateClosed= '" + participation.getDateClosed()
					+ "' WHERE id= " + participation.getId());
		}

		catch (SQLException e2) {
			e2.printStackTrace();
		}
		return participation;
	}

	/**
	 * L�scht ein Participation-Objekt aus der Datenbank.
	 * 
	 * @param participation
	 */
	public void delete(Participation participation) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM participation WHERE id=" + participation.getId());
		}

		catch (SQLException e2) {
			e2.printStackTrace();
		}

	}

	/**
	 * Findet ein Participation-Objekt anhand der �bergebenen ID in der
	 * Datenbank.
	 * 
	 * @param id
	 * @return person
	 */
	public Participation findById(int id) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT id, workload, dateOpened, dateClosed FROM participation WHERE id=" + id);

			/**
			 * Zu einem Prim�rschl�ssel exisitiert nur max ein Datenbank-Tupel,
			 * somit kann auch nur einer zur�ckgegeben werden. Es wird mit einer
			 * IF-Abfragen gepr�ft, ob es f�r den angefragten Prim�rschl�ssel
			 * ein DB-Tupel gibt.
			 */

			if (rs.next()) {
				Participation participation = new Participation();
				participation.setId(rs.getInt("id"));
				participation.setWorkload(rs.getFloat("workload"));
				participation.setDateOpened(rs.getDate("dateOpened"));
				participation.setDateClosed(rs.getDate("dateClosed"));

				return participation;
			}

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return null;
	}

	/**
	 * Findet alle Participation-Objekte in der Datenbank.
	 * 
	 * @return ArrayList<Participation>
	 */
	public ArrayList<Participation> findAll() {
		Connection con = DBConnection.connection();

		ArrayList<Participation> result = new ArrayList<Participation>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT id, workload, dateOpened, dateClosed FROM participation ORDER BY id");

			while (rs.next()) {
				Participation participation = new Participation();
				participation.setId(rs.getInt("id"));
				participation.setWorkload(rs.getFloat("workload"));
				participation.setDateOpened(rs.getDate("dateOpened"));
				participation.setDateClosed(rs.getDate("dateClosed"));

				result.add(participation);

			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return result;
	}

	/**
	 * Findet Participation-Objekte anhand des �bergebenen Start-Datums in der
	 * Datenbank.
	 * 
	 * @param dateOpened
	 * @return ArrayList<Participation>
	 */
	public ArrayList<Participation> findByDateOpened(Date dateOpened) {
		Connection con = DBConnection.connection();

		ArrayList<Participation> result = new ArrayList<Participation>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT id, workload, dateOpened, dateClosed FROM participation WHERE dateOpened= '"
							+ dateOpened + "' ORDER BY id");

			while (rs.next()) {
				Participation participation = new Participation();
				participation.setId(rs.getInt("id"));
				participation.setWorkload(rs.getFloat("workload"));
				participation.setDateOpened(rs.getDate("dateOpened"));
				participation.setDateClosed(rs.getDate("dateClosed"));

				result.add(participation);

			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Findet Participation-Objekte anhand des �bergebenen personId in der
	 * Datenbank. Mit der Inner-Join-Klausel wird erreicht, dass nur die Datens�tze zusammengef�gt werden,
	 * zu den es jeweils auch ein Gegenst�ck in der verkn�pften Tabelle gibt. 
	 * Da es m�glich ist, dass eine Person mehrere Participations (Beteiligungen) hat, m�ssen die 
	 * Participation-Objekte in einer ArrayList gespeichert werden
	 * 
	 * @param personId
	 * @return ArrayList<Participation>
	 */
	public ArrayList<Participation> findParticipationsByPersonId(int personId) {
		Connection con = DBConnection.connection();

		ArrayList<Participation> result = new ArrayList<Participation>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery(
							"SELECT * FROM participation "
							+ "INNER JOIN person_has_participation"
							+ "ON participation.id = person_has_participation.participation_id"
							+ "WHERE person_has_participation.person_id = " + personId);

			while (rs.next()) {
				Participation participation = new Participation();
				participation.setId(rs.getInt("id"));
				participation.setWorkload(rs.getFloat("workload"));
				participation.setDateOpened(rs.getDate("dateOpened"));
				participation.setDateClosed(rs.getDate("dateClosed"));

				result.add(participation);

			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Findet Participation-Objekte anhand der �bergebenen teamId in der
	 * Datenbank. Mit der Inner-Join-Klausel wird erreicht, dass nur die Datens�tze zusammengef�gt werden,
	 * zu den es jeweils auch ein Gegenst�ck in der verkn�pften Tabelle gibt. 
	 * Da es m�glich ist, dass ein Team mehrere Participations (Beteiligungen) hat, m�ssen die 
	 * Participation-Objekte in einer ArrayList gespeichert werden
	 * 
	 * @param teamId
	 * @return ArrayList<Participation>
	 */
	public ArrayList<Participation> findParticipationsByTeamId(int teamId) {
		Connection con = DBConnection.connection();

		ArrayList<Participation> result = new ArrayList<Participation>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery(
							"SELECT * FROM participation"
							+ " INNER JOIN participation_has_team "
							+ "ON participation.id = participation_has_team.participation_id "
							+ "WHERE participation_has_team.team_id = " + teamId);


			while (rs.next()) {
				Participation participation = new Participation();
				participation.setId(rs.getInt("id"));
				participation.setWorkload(rs.getFloat("workload"));
				participation.setDateOpened(rs.getDate("dateOpened"));
				participation.setDateClosed(rs.getDate("dateClosed"));

				result.add(participation);

			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return result;
	}
}
