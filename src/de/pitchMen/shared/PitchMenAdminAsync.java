package de.pitchMen.shared;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;
import de.pitchMen.shared.bo.*;

/**
 * Das asynchrone Gegenstück des Interface PitchMenAdmin. Es wird
 * semiautomatisch durch das Google Plugin erstellt und gepflegt.
 * 
 * @author Eleonora Renz
 *
 */
public interface PitchMenAdminAsync {

	void init(AsyncCallback<Void> callback);

	// ---------- APPLICATION

	void addApplication(Date dateCreated, String text, int jobPostingId, int partnerProfileId,
			AsyncCallback<Application> callback);

	void updateApplication(Application application, AsyncCallback<Void> callback);

	void deleteApplication(Application application, AsyncCallback<Void> callback);

	void getApplications(AsyncCallback<ArrayList<Application>> callback);

	void getApplicationByID(int id, AsyncCallback<Application> callback);

	// ---------- COMPANY
	void addCompany(AsyncCallback<Company> callback);

	void updateCompany(Company company, AsyncCallback<Void> callback);

	void deleteCompany(Company company, AsyncCallback<Void> callback);

	void getCompanyByID(int id, AsyncCallback<Company> callback);

	// ---------- JOBPOSTING

	void addJobPosting(String title, String text, Date deadline, int projectId, AsyncCallback<JobPosting> callback)
			throws IllegalArgumentException;

	void updateJobPosting(JobPosting jobPosting, AsyncCallback<Void> callback);

	void deleteJobPosting(JobPosting jobPosting, AsyncCallback<Void> callback);

	void getJobPostings(AsyncCallback<ArrayList<JobPosting>> callback);

	void getJobPostingByID(int id, AsyncCallback<JobPosting> callback);

	void setJobPosting(JobPosting jobPosting, AsyncCallback<Void> callback);

	// ---------- MARKETPLACE

	void addMarketplace(String title, String description, ArrayList<OrganisationUnit> organisationUnits,
			ArrayList<Project> projects, AsyncCallback<Marketplace> callback);

	void updateMarketplace(Marketplace m, AsyncCallback<Void> callback);

	void deleteMarketplace(Marketplace m, AsyncCallback<Void> callback);

	void getMarketplaces(AsyncCallback<ArrayList<Marketplace>> callback);

	void getMarketplaceByID(int id, AsyncCallback<Marketplace> callback);

	void setMarketplaces(Marketplace m, AsyncCallback<Void> callback);

	// ---------- PARTICIPATION

	void addParticipation(Date dateOpened, Date dateClosed, float workload, Rating rating,
			OrganisationUnit associatedApplicant, Project associatedProject, AsyncCallback<Participation> callback);
	// FIXME In Participation ist Project und OrgaUnit 2 mal

	void updateParticipation(Participation participation, AsyncCallback<Void> callback);

	void deleteParticipation(Participation participation, AsyncCallback<Void> callback);

	void getParticipations(AsyncCallback<ArrayList<Participation>> callback);

	void getParticipationByID(int id, AsyncCallback<Participation> callback);

	// ---------- PARTNERPROFILE

	void addPartnerProfile(ArrayList<Trait> traits, OrganisationUnit organisationUnit, Date dateCreated,
			Date dateChanged, PartnerProfile partnerprofile, JobPosting jobPosting,
			AsyncCallback<PartnerProfile> callback);

	void updatePartnerProfile(PartnerProfile PartnerProfile, AsyncCallback<Void> callback);

	void deletePartnerProfile(PartnerProfile partnerProfile, AsyncCallback<Void> callback);

	void getPartnerProfiles(AsyncCallback<ArrayList<PartnerProfile>> callback);

	void getPartnerProfileByID(int id, AsyncCallback<PartnerProfile> callback);

	// ---------- PERSON

	void addPerson(String firstName, ArrayList<Project> projetcs, String eMail, AsyncCallback<Person> callback);

	void updatePerson(Person person, AsyncCallback<Void> callback);

	void deletePerson(Person person, AsyncCallback<Void> callback);

	void getPersonByID(int id, AsyncCallback<Person> callback);

	// ---------- PROJECT

	void addProject(Date dateOpened, Date dateClosed, String title, String description, int personId, int marketplaceId,
			AsyncCallback<Project> callback);

	void updateProject(Project p, AsyncCallback<Void> callback);

	void deleteProject(Project p, AsyncCallback<Void> callback);

	void getProject(AsyncCallback<ArrayList<Project>> callback);

	void setProject(Project p, AsyncCallback<Void> callback);

	void getProjectByID(int id, AsyncCallback<Project> callback);

	// ---------- RATING

	void addRating(String statement, float scrore, AsyncCallback<Rating> callback);

	void updateRating(Rating rating, AsyncCallback<Void> callback);

	void deleteRating(Rating rating, AsyncCallback<Void> callback);

	void getRatings(AsyncCallback<ArrayList<Rating>> callback);

	void getRatingByID(int id, AsyncCallback<Rating> callback);

	// ---------- TEAM
	void addTeam(AsyncCallback<Team> callback);

	void updateTeam(Team team, AsyncCallback<Void> callback);

	void deleteTeam(Team team, AsyncCallback<Void> callback);

	void getTeamByID(int id, AsyncCallback<Team> callback);

	// ---------- TRAIT

	void addTrait(String name, String value, AsyncCallback<Trait> callback);

	void updateTrait(Trait Trait, AsyncCallback<Void> callback);

	void deleteTrait(Trait trait, AsyncCallback<Void> callback);

	void getTraits(AsyncCallback<ArrayList<Trait>> callback);

	void getTraitByID(int id, AsyncCallback<Trait> callback);

	// --------------------------- LOGIN
	void login(String requestUri, AsyncCallback<Person> async);

}