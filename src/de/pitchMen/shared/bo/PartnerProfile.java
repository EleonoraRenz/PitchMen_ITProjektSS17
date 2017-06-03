package de.pitchMen.shared.bo;

import java.util.Date;

/**
 * Jedes PartnerProfile-Objekt gehört zu genau einem Teilnehmer des Projektmarktplatzes bzw. einer Ausschreibung.
 * 
 * @author JuliusDigel
 */ 
public class PartnerProfile extends BusinessObject {

	
	private Date dateCreated = null;

	private Date dateChanged = null;

	private static final long serialVersionUID = 1L;
	/**
	 * Realisierung der Beziehung zu einer Person durch einen
	    Fremdschl�ssel.
	 */
	private int personId = 0;
	/**
	 * Realisierung der Beziehung zu einem Team durch einen
	    Fremdschl�ssel.
	 */
	private int teamId = 0;
	/**
	 * Realisierung der Beziehung zu eine Unternehmen durch einen
	    Fremdschl�ssel.
	 */
	private int companyId = 0;
	/**
	 * Realisierung der Beziehung zu einem JobPosting durch einen
	    Fremdschl�ssel.
	 */
	private int jopPostingId = 0;

	/**
	 * @return
	 */
	public Date getDateCreated() {
		return dateCreated;
	}
	/**
	 * @return
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	/**
	 * @return
	 */
	public Date getDateChanged() {
		return dateChanged;
	}
	/**
	 * @return
	 */
	public void setDateChanged(Date dateChanged) {
		this.dateChanged = dateChanged;
	}
	/**
	 * @return personId
	 */
	public int getPersonId() {
		return personId;
	}
	/**
	 * @param personId
	 */
	public void setPersonId(int personId) {
		this.personId = personId;
	}
	/**
	 * @return teamId
	 */	
	public int getTeamId() {
		return teamId;
	}
	/**
	 * @param teamId
	 */
	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
	/**
	 * @return companyId
	 */	
	public int getCompanyId() {
		return companyId;
	}
	/**
	 * @param companyId
	 */
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	/**
	 * @return jopPostingId
	 */	
	public int getJobPostingId() {
		return jopPostingId;
	}
	/**
	 * @param jopPostingId
	 */
	public void setJobPostingId(int jopPostingId) {
		this.jopPostingId = jopPostingId;
	}
	
	/**
	 * Vergleicht das aufrufende PartnerProfile-Objekt mit dem übergebenen PartnerProfil-Objekt und ermittelt einen Übereinstimmungswert.
	 * 
	 * @param partnerProfile 
	 * @return
	 
	public float compareWith(PartnerProfile partnerProfile) {
		
		return 0.0f;
	}*/








}