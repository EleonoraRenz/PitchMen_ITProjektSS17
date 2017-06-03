package de.pitchMen.server.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import com.google.gwt.thirdparty.javascript.jscomp.Result;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import de.pitchMen.server.*;
import de.pitchMen.shared.PitchMenAdmin;
import de.pitchMen.shared.ReportGenerator;
import de.pitchMen.shared.bo.JobPosting;
import de.pitchMen.shared.bo.Person;
import de.pitchMen.shared.report.AllApplicationsOfUser;
import de.pitchMen.shared.report.AllJobPostings;
import de.pitchMen.shared.report.AllJobPostingsMatchingPartnerProfileOfUser;
import de.pitchMen.shared.report.ApplicationsRelatedToJobPostingsOfUser;
import de.pitchMen.shared.report.Column;
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
		
		result.addRow(headline);
		
		ArrayList<JobPosting> jobPostings = pitchMenAdmin.getJobPostings();
		
		for (JobPosting jobPosting : jobPostings) {
			Row jobPostingZeile = new Row();
			
			jobPostingZeile.addColumn(new Column(jobPosting.getTitle()));
			jobPostingZeile.addColumn(new Column(jobPosting.getText()));
			jobPostingZeile.addColumn(new Column(jobPosting.getProjectId()));
			
			result.addRow(jobPostingZeile);
		
		}
		return result;
	}


	@Override
	public AllJobPostingsMatchingPartnerProfileOfUser showAllJobPostingsMatchingPartnerProfileOfUser(Person p)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public AllApplicationsOfUser showAllApplicationsOfUser(Person p) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ProjectInterweavingsWithParticipationsAndApplications showProjectInterweavingsWithParticipationsAndApplications(
			Person p) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public FanInJobPostingsOfUser showFanInJobPostingsOfUser(Person p) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public FanOutApplicationsOfUser showFanOutApplicationsOfUser(Person p) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ApplicationsRelatedToJobPostingsOfUser showApplicationsRelatedToJobPostingsOfUser(JobPosting j)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Default constructor
	 */

}