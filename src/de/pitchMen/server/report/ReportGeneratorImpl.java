package de.pitchMen.server.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import com.google.gwt.thirdparty.javascript.jscomp.Result;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.pitchMen.client.PitchMen;
import de.pitchMen.server.*;
import de.pitchMen.shared.PitchMenAdmin;
import de.pitchMen.shared.ReportGenerator;
import de.pitchMen.shared.bo.Application;
import de.pitchMen.shared.bo.JobPosting;
import de.pitchMen.shared.bo.PartnerProfile;
import de.pitchMen.shared.bo.Person;
import de.pitchMen.shared.bo.Project;
import de.pitchMen.shared.report.AllApplicationsOfOneUser;
import de.pitchMen.shared.report.AllApplicationsOfUser;
//import de.pitchMen.shared.report.AllApplicationsToOneJobPostingOfUser;
import de.pitchMen.shared.report.AllJobPostings;
import de.pitchMen.shared.report.AllJobPostingsMatchingPartnerProfileOfUser;
import de.pitchMen.shared.report.AllParticipationsOfOneUser;
import de.pitchMen.shared.report.ApplicationsRelatedToJobPostingsOfUser;
import de.pitchMen.shared.report.Column;
import de.pitchMen.shared.report.FanInAndOutReport;
import de.pitchMen.shared.report.FanInJobPostingsOfUser;
import de.pitchMen.shared.report.FanOutApplicationsOfUser;
import de.pitchMen.shared.report.ProjectInterweavingsWithParticipationsAndApplications;
import de.pitchMen.shared.report.Row;
import de.pitchMen.shared.report.SimpleParagraph;
import de.pitchMen.shared.report.SimpleReport;

/**
 * Implemetierungsklasse des Interface ReportGenerator.  Sie enth�lt die Applikationslogik, stellt die Zusammenh�nge konstistent dar und ist zust�ndig f�r einen geordneten Ablauf.
 * 
 * @author JuliusDigel
 */
public class ReportGeneratorImpl extends RemoteServiceServlet implements ReportGenerator {

	private static final long serialVersionUID = 1L;
	//private PitchMenAdminImpl administration = null;
	private PitchMenAdmin pitchMenAdmin = null;
	public ReportGeneratorImpl() throws IllegalArgumentException{}


	@Override
	public void init() throws IllegalArgumentException{
		/**	
		 Ein ReportGeneratorImpl-Objekt instantiiert f�r seinen Eigenbedarf eine
		 * PitchMenAdministration-Instanz.
		 */
		PitchMenAdminImpl a = new PitchMenAdminImpl();
		a.init();
		pitchMenAdmin = a;	
	}

	/**
	 * Auslesen der zugehörigen PitchMenAdministration (interner Gebrauch).
	 * 
	 * @return PitchMenAdmin Objekt
	 */
	protected PitchMenAdmin getPitchMenAdmin() {
		return this.pitchMenAdmin;
	}

	/**
	 * Hinzufügen des Report-Impressums. Diese Methode ist aus den
	 * <code>create...</code>-Methoden ausgegliedert, da jede dieser Methoden
	 * diese Tätigkeiten redundant auszuführen hätte. Stattdessen rufen die
	 * <code>create...</code>-Methoden diese Methode auf.
	 * 
	 * @param r der um das Impressum zu erweiternde Report.
	 */
	// AUSKOMMENTIERT WEIL F�R TEST NOCH NICHT NOTWENDIG
	/* protected void addImprint(Report r) {
		    /*
	 * Das Impressum soll wesentliche Informationen über die Bank enthalten.
	 */
	/* Bank bank = this.administration.getBank();

		    /*
	 * Das Imressum soll mehrzeilig sein.
	 */
	/* CompositeParagraph imprint = new CompositeParagraph();

		    imprint.addSubParagraph(new SimpleParagraph(bank.getName()));
		    imprint.addSubParagraph(new SimpleParagraph(bank.getStreet()));
		    imprint.addSubParagraph(new SimpleParagraph(bank.getZip() + " "
		        + bank.getCity()));

		    // Das eigentliche Hinzufügen des Impressums zum Report.
		    r.setImprint(imprint);

		  }*/


	@Override
	public AllJobPostings showAllJobPostings(JobPosting jopPosting) throws IllegalArgumentException {
		if (pitchMenAdmin == null) {
			return null;
		}
		AllJobPostings result = new AllJobPostings();


		result.setTitle("Alle Job Postings");

		result.setDatecreated(new Date());


		Row headline = new Row(); //Erste Zeile im Report

		headline.addColumn(new Column("JobPosting Titel"));
		headline.addColumn(new Column("JobPosting Text"));
		headline.addColumn(new Column("dazugeh�riges Projekt"));
		headline.addColumn(new Column("Deadline"));

		result.addRow(headline);

		ArrayList<JobPosting> jobPostings = pitchMenAdmin.getJobPostings();

		for (JobPosting jobPosting : jobPostings) {
			Row jobPostingZeile = new Row();

			jobPostingZeile.addColumn(new Column(jobPosting.getTitle()));
			jobPostingZeile.addColumn(new Column(jobPosting.getText()));
			jobPostingZeile.addColumn(new Column(jobPosting.getProjectId()));
			jobPostingZeile.addColumn(new Column(jobPosting.getDeadline().toString()));
			result.addRow(jobPostingZeile);

		}
		return result;
	}


	@Override
	public AllJobPostingsMatchingPartnerProfileOfUser showAllJobPostingsMatchingPartnerProfileOfUser(PartnerProfile partnerProfile)
			throws IllegalArgumentException {
		if (pitchMenAdmin == null) {
			return null;
		}
		return null;
		// TODO Auto-generated method stub
	}

	@Override
	public ApplicationsRelatedToJobPostingsOfUser showApplicationsRelatedToJobPostingsOfUser(Person p)
			throws IllegalArgumentException {
		// TODO Fehlersuche
		if (pitchMenAdmin == null) {
			return null;
		}
		ApplicationsRelatedToJobPostingsOfUser result = new ApplicationsRelatedToJobPostingsOfUser();

		result.setTitle("Alle Bewerbungen auf eine Ausschreibung des Users");
		result.setDatecreated(new Date());
		Row headline = new Row();
		headline.addColumn(new Column("Erstellungsdatum"));
		headline.addColumn(new Column("Bewerbungstext"));
		
		result.addRow(headline);

		ArrayList<Application> applications = pitchMenAdmin.getApplications();	
		for (Application a : applications) {

			if(a.getPartnerProfileId() == p.getId()) {};
			Row applicationsrow = new Row();

			applicationsrow.addColumn(new Column(a.getDateCreated().toString()));
			applicationsrow.addColumn(new Column(a.getText()));

			result.addRow(applicationsrow);
		}


		return result;
	}
	/* public AllApplicationsToOneJobPostingOfUser showAllApplicationsToOneJobPostingOfUser(int jobPostingId) throws IllegalArgumentException{
		if (pitchMenAdmin == null) {
			return null;
		}
		JobPosting jobPosting = pitchMenAdmin.getJobPostingByID(jobPostingId);

		AllApplicationsToOneJobPostingOfUser result = new AllApplicationsToOneJobPostingOfUser();

		result.setTitle("Alle Bewerbungen auf die Ausschreibung " + jobPosting.getTitle() + "mit der ID: " + jobPosting.getId());

		result.setDatecreated(new Date());

		Row headline = new Row();

		headline.addColumn(new Column("Erstellungsdatum"));
		headline.addColumn(new Column("Bewerbungstext"));
		result.addRow(headline);
		//TODO getApplicationsByJobPostingId in PitchmenAdmin implementieren
		ArrayList<Application> applications = pitchMenAdmin.getApplicationsByJobPostingId(jobPostingId);

		for(Application a : applications){

			Row applicationRow = new Row();

			applicationRow.addColumn(new Column(a.getDateCreated().toString()));
			applicationRow.addColumn(new Column(a.getText()));



			result.addRow(applicationRow);
		}

		return result;
	};
*/


	@Override
	public AllApplicationsOfUser showAllApplicationsOfUser(Person p) throws IllegalArgumentException {
		
		if (pitchMenAdmin == null) {
			return null;
		}
		AllApplicationsOfUser result = new AllApplicationsOfUser();

		result.setTitle("Alle Bewerbungen eines Nutzers mit den dazugeh�rigen Ausschreibungen");
		result.setDatecreated(new Date());

		Row headline = new Row(); //Erste Zeile im Report

		headline.addColumn(new Column("Erstellungsdatum"));
		headline.addColumn(new Column("Bewerbungstext"));
		headline.addColumn(new Column("Ersteller der Ausschreibung"));
		headline.addColumn(new Column("Beschreibung der Ausschreibung"));
		result.addRow(headline);

		ArrayList<Application> applications = pitchMenAdmin.getApplicationsByPerson(p.getId());	
		for (Application a : applications) {
			
			
			Application application = pitchMenAdmin.getApplicationByID(a.getJobPostingId());
			Person jobPoster = pitchMenAdmin.getPersonByID(application.getJobPostingId());
			
			Row applicationsrow = new Row();


			applicationsrow.addColumn(new Column(a.getDateCreated().toString()));
			applicationsrow.addColumn(new Column(a.getText()));
			applicationsrow.addColumn(new Column(jobPoster.getFirstName() + " " + jobPoster.getName()));
			applicationsrow.addColumn(new Column(jobPoster.getDescription()));

			result.addRow(applicationsrow);

		}

		return null;
	}
	
	
	/**
	 * eventuell unn�tig
	@Override
	public AllApplicationsOfOneUser showAllApplicationsOfOneUser(int id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	*/
	
	@Override
	public AllParticipationsOfOneUser showAllParticipationsOfOneUser(Person p) throws IllegalArgumentException {
		
		if(this.getPitchMenAdmin() == null){
			return null;
		}
		
		AllParticipationsOfOneUser result = new AllParticipationsOfOneUser();
		
		result.setTitle("Report f�r Alle Beteiligungen eines Nutzers");
		result.setDatecreated(new Date());
		
		Row headline = new Row();
		
		headline.addColumn(new Column("Projekt"));
		headline.addColumn(new Column("Startdatum"));
		headline.addColumn(new Column("Enddatum"));
		headline.addColumn(new Column("Projektbeschreibung"));
		
		result.addRow(headline);
		//TODO getProjectsByPerson in PitchMenAdminImpl erstellen
		ArrayList<Project> allProjects = pitchMenAdmin.getProj;
		
		for(Project project : allProjects){
			
			Row projectRow = new Row();
			
			projectRow.addColumn(new Column(project.getTitle()));
			projectRow.addColumn(new Column(project.getDateOpened()));
			projectRow.addColumn(new Column(project.getDateClosed()));
			projectRow.addColumn(new Column(project.getDescription()));
			
			result.addRow(projectRow);
		}
		return result;
	}


	
	
	@Override
	public ProjectInterweavingsWithParticipationsAndApplications showProjectInterweavingsWithParticipationsAndApplications(
			Person p) throws IllegalArgumentException {
	
		if(this.getPitchMenAdmin() == null){
			return null;
		}
		
		ProjectInterweavingsWithParticipationsAndApplications result = new ProjectInterweavingsWithParticipationsAndApplications();
		
		result.setTitle("Des Nutzers Projektverflechtungen");
		result.setDatecreated(new Date());
		
		result.addSubReport(this.showAllApplicationsOfUser(p));
		result.addSubReport(this.showAllParticipationsOfOneUser(p));
		
		return result;
	}


	@Override
	public FanInJobPostingsOfUser showFanInJobPostingsOfUser() throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
		if(this.getPitchMenAdmin() == null){
			return null;
		}
		
		FanInJobPostingsOfUser result = new FanInJobPostingsOfUser();
		
		result.setTitle("Die FanIn-Analyse");
		result.setDatecreated(new Date());

		Row headline = new Row();
		headline.addColumn(new Column("ID"));
		headline.addColumn(new Column("Person"));
		headline.addColumn(new Column("Bewerbungsstatus"));
		
		result.addRow(headline);
		
		//ArrayList<Person> allPersons = pitchMenAdmin.getAllPeople();
		
		//for(Person person : allPersons) {
			
		ArrayList<Application> allApplications = pitchMenAdmin.getApplications();
		
			ArrayList<Application> ongoing = new ArrayList<Application>();
			ArrayList<Application> declined = new ArrayList<Application>();
			ArrayList<Application> accepted = new ArrayList<Application>();
		
			for(Application ap : allApplications){
				
								
				if(ap.getStatus().equals("laufend")){
					ongoing.add(ap);
				}
				else if(ap.getStatus().equals("abgelehnt")){
					declined.add(ap);
				}
				else if(ap.getStatus().equals("angenommen")){
					accepted.add(ap);
				};
				
				Row applicationCount = new Row();
				
				applicationCount.addColumn(new Column(String.valueOf(ongoing.size())));
				applicationCount.addColumn(new Column(String.valueOf(declined.size())));
				applicationCount.addColumn(new Column(String.valueOf(accepted.size())));
				
				result.addRow(applicationCount);
				
			}
			
			
		//}
		
		
		return result;
		
		
		
	}


	@Override
	public FanOutApplicationsOfUser showFanOutApplicationsOfUser() throws IllegalArgumentException {
	
		if(this.getPitchMenAdmin() == null){
			return null;
		}
		
		FanOutApplicationsOfUser  result = new FanOutApplicationsOfUser();
		
		result.setTitle("Die FanOut-Analyse");
		result.setDatecreated(new Date());

		Row headline = new Row();
		headline.addColumn(new Column("ID"));
		headline.addColumn(new Column("Person"));
		headline.addColumn(new Column("laufend"));
		headline.addColumn(new Column("abgebrochen"));
		headline.addColumn(new Column("besetzt"));
		
		result.addRow(headline);
		
		//ArrayList<Person> allPersons = pitchMenAdmin.getAllPeople();
		
		//for(Person person : allPersons) {
			
		ArrayList<JobPosting> allJobPostings = pitchMenAdmin.getJobPostings();
		
			ArrayList<JobPosting> ongoing = new ArrayList<JobPosting>();
			ArrayList<JobPosting> deleted = new ArrayList<JobPosting>();
			ArrayList<JobPosting> occupied = new ArrayList<JobPosting>();
		
			for(JobPosting j : allJobPostings){
				
								
				if(j.getStatus().equals("laufend")){
					ongoing.add(j);
				}
				else if(j.getStatus().equals("abgelehnt")){
					deleted.add(j);
				}
				else if(j.getStatus().equals("angenommen")){
					occupied.add(j);
				};
				
				Row jobPostingCount = new Row();
				
				jobPostingCount.addColumn(new Column(String.valueOf(ongoing.size())));
				jobPostingCount.addColumn(new Column(String.valueOf(deleted.size())));
				jobPostingCount.addColumn(new Column(String.valueOf(occupied.size())));
				
				result.addRow(jobPostingCount);
				
			}
			
			
		//}
		
		
		return result;
		
	}

	@Override
	public FanInAndOutReport showFanInAndOutReport() throws IllegalArgumentException {
		
		if(this.getPitchMenAdmin() == null){
			return null;
		}
		
		FanInAndOutReport result = new FanInAndOutReport();
		
		result.setTitle("Report f�r die FanIn bzw FanOut Analyse");
		result.setDatecreated(new Date());
		
		result.addSubReport(this.showFanInJobPostingsOfUser());
		result.addSubReport(this.showFanOutApplicationsOfUser());
		
		
		return result;
		
		
	}
	/**
	 * Default constructor
	 */

}