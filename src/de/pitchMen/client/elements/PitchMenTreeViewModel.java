package de.pitchMen.client.elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;

import de.pitchMen.client.ClientsideSettings;
import de.pitchMen.shared.PitchMenAdminAsync;
import de.pitchMen.shared.bo.BusinessObject;
import de.pitchMen.shared.bo.JobPosting;
import de.pitchMen.shared.bo.Marketplace;
import de.pitchMen.shared.bo.Project;

/**
 * Anstatt der ursprünglich vorgesehenen Navigation,
 * einem VerticalPanel gefüllt mit Buttons, wurde nach 
 * dem Gespräch mit Herrn Rathke am 30.05.2017 
 * überlegt, die Auswahl von Projektmarktplätzen,
 * Projekten und Ausschreibungen durch eine navigierbare
 * Baumstruktur zu realisieren. Hierfür bietet GWT Cell-
 * Widgets an, die in Baumstrukturen angelegt werden. Die
 * vorliegende Klasse <code>PitchMenTreeViewModel</code>
 * steuert das Verhalten der Baumstruktur. Die Ausgabe 
 * der einzelnen Elemente wird durch die entsprechenden
 * Cell-Klassen gerendert. 
 * 
 * Die Hierarchie des Baumes ist Projektmarktplatz > Projekt
 * > Ausschreibung.
 * 
 * @author Simon
 */

public class PitchMenTreeViewModel implements TreeViewModel {
		
	/**
	 * Um in der Lage zu sein, Abfragen an die Applikations-
	 * schicht stellen zu können, benötigt PitchMenTreeViewModel
	 * Zugriff auf die PitchMenAdmin.
	 */
	private PitchMenAdminAsync pitchMenAdmin = null;
	
	/**
	 * ListDataProvider-Objekte verwalten das Datenmodell
	 * auf Client-Seite. Dies ist der ListDataProvider für
	 * die Projektmarktplätze der PitchMen-Applikation.
	 */
	private ListDataProvider<Marketplace> marketplaceLDP = null;
	
	/**
	 * Diese Map speichert die ListDataProviders für die 
	 * Projektmarktplätze, die im Navigationsbaum expandiert
	 * wurden.
	 */
	private Map<Marketplace, ListDataProvider<Project>> projectDataProviders = null;
	
	/**
	 * Diese Map speichert die ListDataProviders für die 
	 * Projekte, die im Navigationsbaum expandiert
	 * wurden.
	 */
	private Map<Project, ListDataProvider<JobPosting>> jobPostingDataProviders = null;
	
	/*
	 * Im Baum kann es einen ausgewählten Marktplatz, ein 
	 * ausgewähltes Projekt und eine ausgewählte Ausschreibung
	 * geben. 
	 */
	private Marketplace selectedMarketplace = null;
	private Project selectedProject = null;
	private JobPosting selectedJobPosting = null;
	
	/*
	 * Entsprechend gibt es Formulare, mit deren Hilfe die zuvor
	 * selektierten Business-Objekte manipuliert werden können.
	 */
	private MarketplaceForm marketplaceForm = null;
	private ProjectForm projectForm = null;
	private JobPostingForm jobPostingForm = null;
	
	/**
	 * Die genestete Klasse <code>BusinessObjectKeyProvider</code>
	 * dient dazu, Baumknoten des TreeViewModels eindeutige Schlüssel
	 * auf Basis der in ihnen enthaltenen BusinessObjects 
	 * zuzuweisen. Auf Basis dieser Schlüssel arbeitet das 
	 * SelectionModel. Diese Vorgehensweise wurde aus der gleichnamigen
	 * Klasse im bankProjek (Rathke C., 2016) übernommen. Allerdings
	 * konnte die Vorgehensweise mit positiven und negativen Integers
	 * nicht übernommen werden, da es sich nicht um zwei, sondern drei
	 * Hierarchieebenen handelt. Deshalb wird mit Strings gearbeitet, die 
	 * sich aus einem einzelnen Buchstaben als Identifier, einem
	 * Bindestrich und der id des Business-Objekts zusammensetzen.
	 * 
	 * @author Simon
	 */
	private class BusinessObjectKeyProvider implements ProvidesKey<BusinessObject> {

		@Override
		public String getKey(BusinessObject bo) {
			if (bo == null) {
				/*
				 *  Wofür dieser Sonderfall gebraucht wird, ist mir aktuell 
				 *  nicht klar. Übernommen. Simon, 07.06.2017, 20:10 Uhr
				 */
				return null;
			} else if (bo instanceof Marketplace) {
				// Es handelt sich um ein Marktplatz-Objekt. Präfix M
				return "M-" + bo.getId();
			} else if (bo instanceof Project) {
				// Es handelt sich um ein Projekt-Objekt. Präfix P
				return "P-" + bo.getId();
			} else {
				// Es handelt sich um ein Ausschriebungs-Objekt. Präfix J
				return "J-" + bo.getId();
			}
		}
		
	}
	
	/**
	 * Die zuvor lokal definierte Klasse {@link BusinessObjectKeyProvider}
	 * wird nun als Variable der Klasse {@link PitchMenTreeViewModel} 
	 * hinzugefügt.
	 */
	private BusinessObjectKeyProvider boKeyProvider = null;
	
	/**
	 * Das <code>SingleSelectionObject</code> wird von GWT vordefiniert
	 * und beschreibt die Auswahl von Objekten im Baum.
	 */
	private SingleSelectionModel<BusinessObject> selectionModel = null;
	
	/**
	 * Die genestete Klasse <code>SelectionChangeEventHandler</code>
	 * dient dazu, Events in Bezug auf den Wechsel einer Auswahl im
	 * Baum zu bearbeiten. Diese Vorgehensweise wurde aus der gleichnamigen
	 * Klasse im bankProjek (Rathke C., 2016) übernommen. 
	 * 
	 * @author Simon
	 */
	private class SelectionChangeEventHandler implements SelectionChangeEvent.Handler {

		@Override
		public void onSelectionChange(SelectionChangeEvent event) {
			
			// Beim Wechsel der Auswahl wird zunächst das ausgewählte Objekt abgefragt
			BusinessObject selection = selectionModel.getSelectedObject();
			
			if (selection instanceof Marketplace) {
				// Handelt es sich dabei um ein Marktplatz-Objekt, wird ein entsprechendes Formular erstellt
				RootPanel.get("content").add(new MarketplaceForm((Marketplace) selection));
			} else if (selection instanceof Project) {
				// Handelt es sich stattdessen um ein Projekt-Objekt, wird ein entsprechendes Formular erstellt
				RootPanel.get("content").add(new ProjectForm((Project) selection));
			} else {
				// Handelt es sich um ein Ausschreibungs-Objekt, wird ein entsprechendes Formular erstellt
				RootPanel.get("content").add(new JobPostingForm((JobPosting) selection));
			}
			
		}
		
	}
	
	/**
	 * Konstruktor für Instanzen der Klasse. Hier wird z. B.
	 * die Verbindung zur PitchMenAdministration hergestellt. 
	 */
	public PitchMenTreeViewModel() {
		this.pitchMenAdmin = ClientsideSettings.getPitchMenAdmin();
		boKeyProvider = new BusinessObjectKeyProvider();
		selectionModel = new SingleSelectionModel<BusinessObject>(boKeyProvider);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEventHandler());
		projectDataProviders = new HashMap<Marketplace, ListDataProvider<Project>>();
		jobPostingDataProviders = new HashMap<Project, ListDataProvider<JobPosting>>();
	}
	
	/*
	 * Setter für die Formulare
	 */
	public void setMarketplaceForm(MarketplaceForm marketplaceForm) {
		this.marketplaceForm = marketplaceForm;
	}

	public void setProjectForm(ProjectForm projectForm) {
		this.projectForm = projectForm;
	}

	public void setJobPostingForm(JobPostingForm jobPostingForm) {
		this.jobPostingForm = jobPostingForm;
	}
	
	/**
	 * Methode zum Abfragen des aktuell selektierten Projekt-Marktplatzes.
	 * @return den aktuell selektierten Marktplatz
	 */
	public Marketplace getSelectedMarketplace() {
		return selectedMarketplace;
	}

	/**
	 * Diese Methode wird aufgerufen, wenn der Nutzer im Baum einen 
	 * Projektmarktplatz selektiert. Der Aufruf geschieht dabei durch
	 * die genestete Klasse {@link SelectionChangeEventHandler}. Da 
	 * damit die höchste Hierarchie-Stufe angesprochen wird, werden 
	 * die darunter liegenden Instanzen von Projekten und Ausschreibungen 
	 * in dieser Methode <code>null</code> gesetzt.
	 * 
	 * @param selectedMarketplace, der neu selektierte Projektmarktplatz
	 */
	public void setSelectedMarketplace(Marketplace selectedMarketplace) {
		/*
		 *  Wird ein Marktplatz ausgewählt, wird das sowohl
		 *  dem TreeViewModel als auch dem MarketplaceForm
		 *  mitgeteilt.
		 */
		this.selectedMarketplace = selectedMarketplace;
		this.marketplaceForm.setSelectedMarketplace(selectedMarketplace);
		this.projectForm.setSelectedMarketplace(selectedMarketplace);
		
		/*
		 * Gleichzeitig werden die evt. noch vorhandenen Projekte
		 * und Ausschreibungen, die zuvor angezeigt wurden, entfernt.
		 */
		this.selectedProject = null;
		this.projectForm.setSelectedProject(null);
		this.selectedJobPosting = null;
		this.jobPostingForm.setSelectedJobPosting(null);	
		
	}

	/**
	 * Methode zum Abfragen des aktuell selektierten Projekts.
	 * @return das aktuell selektierte Projekt
	 */
	public Project getSelectedProject() {
		return selectedProject;
	}

	/**
	 * Diese Methode wird aufgerufen, wenn der Nutzer im Baum ein 
	 * Projekt selektiert. Der Aufruf geschieht dabei durch
	 * die genestete Klasse {@link SelectionChangeEventHandler}. Da 
	 * damit die zweithöchste Hierarchie-Stufe angesprochen wird, wird 
	 * die evt. darunter liegenden Instanz der selektierten Ausschreibung 
	 * in dieser Methode <code>null</code> gesetzt. Da Marktplätze sich
	 * hierarchisch allerdings <em>über</em> Projekten befinden, muss
	 * u. U. der selektierte Projektmarktplatz angepasst werden. Hierfür
	 * erfolgt in dieser Methode ein <strong>RPC</strong>, um den zum
	 * Projekt gehörenden Projekt-Marktplatz abzufragen.
	 * 
	 * @param selectedProject, das neu selektierte Projekt
	 */
	public void setSelectedProject(Project selectedProject) {
		/*
		 * Die Asuwahl eines Projekts hat die folgenden
		 * Updates zur Folge:
		 */
		this.selectedProject = selectedProject;
		this.projectForm.setSelectedProject(selectedProject);
		
		/*
		 * Ausschreibungen liegen hierarchisch unter Projekten,
		 * deshalb wird hier erst einmal kein Element mehr 
		 * selektiert.
		 */
		this.selectedJobPosting = null;
		this.jobPostingForm.setSelectedJobPosting(null);
		
		/*
		 * Projektmarktplätze liegen hierarchisch über Projekten.
		 * Deshalb sollte der selektierte Projektmarktplatz 
		 * auf den aktuellen Stand gebracht werden.
		 */
		if(selectedProject != null) {
			/*
			 *  Auf Basis des selektierten Projekts soll der entsprechende,
			 *  darüber liegende Projektmarktplatz selektiert werden.
			 *  Dies soll nur geschehen, wenn es auch wirklich ein 
			 *  Projekt-Objekt gibt.
			 */
			this.pitchMenAdmin.getMarketplaceByID(this.selectedProject.getMarketplaceId(), new AsyncCallback<Marketplace>() {

				@Override
				public void onFailure(Throwable caught) {
					ClientsideSettings.getLogger().severe("Ein Fehler bei der Abfrage des Marktplatzes ist aufgetreten");
				}

				@Override
				public void onSuccess(Marketplace result) {
					selectedMarketplace = result;
					marketplaceForm.setSelectedMarketplace(result);
				}
				
			});
		}
	}

	/**
	 * Methode zum Abfragen der aktuell selektierten Ausschreibung.
	 * @return die aktuell selektierte Ausschreibung
	 */
	public JobPosting getSelectedJobPosting() {
		return selectedJobPosting;
	}

	/**
	 * Diese Methode wird aufgerufen, wenn der Nutzer im Baum eine 
	 * Auschreibung selektiert. Der Aufruf geschieht dabei durch
	 * die genestete Klasse {@link SelectionChangeEventHandler}. Da 
	 * damit die unterste Hierarchie-Stufe angesprochen wird, muss
	 * u. U. das selektierte Projekt sowie, darüber liegend, der 
	 * selektierte Projektmarktplatz angepasst werden. Hierfür
	 * erfolgt in dieser Methode ein <strong>RPC</strong>, um das zur
	 * Ausschreibung gehörende Projekt und dann, geschachtelt, den zum
	 * Projekt gehörenden Projekt-Marktplatz abzufragen.
	 * 
	 * @param selectedProject, das neu selektierte Projekt
	 */
	public void setSelectedJobPosting(JobPosting selectedJobPosting) {
		/*
		 * Der Aufruf dieser Methode ändert das aktuell
		 * selektierte Ausschreibungs-Objekt.
		 */
		this.selectedJobPosting = selectedJobPosting;
		this.jobPostingForm.setSelectedJobPosting(selectedJobPosting);
		
		/*
		 * Gleichzeitig muss das darüberliegende Projekt 
		 * aktualisiert werden.
		 */
		this.pitchMenAdmin.getProjectByID(selectedJobPosting.getProjectId(), new AsyncCallback<Project>() {

			@Override
			public void onFailure(Throwable caught) {
				ClientsideSettings.getLogger().severe("Fehler bei der Abfrage des Projekts");
			}

			@Override
			public void onSuccess(Project result) {
				selectedProject = result;
				projectForm.setSelectedProject(result);
				
				/*
				 * Mitunter muss auch der Marktplatz des Projekts
				 * aktualisiert werden.
				 */
				pitchMenAdmin.getMarketplaceByID(result.getMarketplaceId(), new AsyncCallback<Marketplace>() {

					@Override
					public void onFailure(Throwable caught) {
						ClientsideSettings.getLogger().severe("Ein Fehler bei der Abfrage des Marktplatzes ist aufgetreten");
					}

					@Override
					public void onSuccess(Marketplace result) {
						selectedMarketplace = result;
						marketplaceForm.setSelectedMarketplace(result);
					}
					
				});
			}
			
		});
	}
	
	/**
	 * Füge einen neuen Projektmarktplatz zur Baumstruktur
	 * hinzu.
	 * 
	 * @param der neue Projektmarktplatz
	 */
	public void addMarketplace(Marketplace marketplace) {
		this.marketplaceLDP.getList().add(marketplace);
		// Selektiere den neuen Marktplatz auch direkt
		this.selectionModel.setSelected(marketplace, true);
	}
	
	/**
	 * Wenn der Nutzer einen Marktplatz ändert und speichert,
	 * sollte sich (bei der Änderung des Titels z. B.) auch das
	 * im Baum hinterlegte Objekt aktualisieren. Das wird mit
	 * <code>updateMarketplace()</code> realisiert.
	 * 
	 * @param der upzudatende Marktplatz
	 */
	public void updateMarketplace(Marketplace marketplace) {
		List<Marketplace> marketplaceList = this.marketplaceLDP.getList();
		int i = 0;
		for(Marketplace current : marketplaceList) {
			if(current.getId() == marketplace.getId()) {
				marketplaceList.set(i, marketplace);
				break;
			} else {
				i++;
			}
		}
		this.marketplaceLDP.refresh();
	}
	
	/**
	 * Ein gelöschtes Marktplatz-Objekt fliegt aus der Baumstruktur.
	 * Das hat allerdings nichts mit der Löschung von Objekten in der
	 * Applikations- und Tupeln in der Datenbank-Schicht zu tun. Es ist
	 * durchaus möglich, dass die Objekte dort nicht "hart" gelöscht,
	 * sondern nur auf inaktiv gesetzt werden o. ä. Wichtig ist bei 
	 * dieser Methode insbesondere, dass vor der Löschung des Marktplatzes
	 * selbst erst noch die ihm untergeordneten Projekte und Ausschreibungen
	 * gelöscht werden. Das ist zwar nicht (wie z. B. bei Datenbanken)
	 * notwendig, der Nachvollziehbarkeit wegen wurde allerdings das
	 * kaskadierende Löschen so implementiert.
	 * 
	 * @param der zu löschende Marktplatz
	 */
	public void deleteMarketplace(Marketplace marketplace) {		
		// Abfrage einer Liste aller Projekte, die zu diesem Marktplatz gehören
		List<Project> projectList = this.projectDataProviders.get(marketplace).getList();
		
		/*
		 * Um alle JobPostings löschen zu können, die sich unterhalb 
		 * eines Projekts befinden, das sich wiederum unterhalb eines
		 * zu löschenden Projektmarktplatzes befindet, muss durch die 
		 * zuvor abgefragte Liste an Projekten iteriert und jeweils das
		 * aktuelle Project-Objekt als Key auf die Methode 
		 * remove() angewendet werden. 
		 */
		for(Project project : projectList) {
			this.jobPostingDataProviders.remove(project);
		}
		
		/*
		 * Dank der Map-Struktur können alle Projekt-ListDataProvider
		 * durch den Key (den zu löschenden Marktplatz) entfernt werden.
		 */
		this.projectDataProviders.remove(marketplace);
		
		// Marktplatz aus dem ListDataProvider für Marktplätze entfernen
		this.marketplaceLDP.getList().remove(marketplace);
	}
	
	/**
	 * Füge ein neues Projekt zur Baumstruktur hinzu.
	 * 
	 * @param das neue Projekt
	 */
	public void addProject(Project project, Marketplace marketplace) {
		// Wenn der Baumknoten noch nicht angelegt wurde, gibt's nicht zu tun
		if(!this.projectDataProviders.containsKey(marketplace)) {
			return;
		}
		// Erstelle ListDataProvider mit Projekten des ausgewählten Marktplatzes
		ListDataProvider<Project> projectProvider = this.projectDataProviders.get(marketplace);
		if(!projectProvider.getList().contains(project)) {
			// wenn noch nicht enthalten, füge hinzu
			projectProvider.getList().add(project);
		}
		// Wähle neu erstelltes Projekt aus
		this.selectionModel.setSelected(project, true);
		
	}

	/**
	 * Wenn der Nutzer ein Projekt ändert und speichert,
	 * sollte sich (bei der Änderung des Titels z. B.) auch das
	 * im Baum hinterlegte Objekt aktualisieren. Das wird mit
	 * <code>updateProject()</code> realisiert.
	 * 
	 * @param das upzudatende Projekt
	 */
	public void updateProject(final Project project) {
		this.pitchMenAdmin.getMarketplaceByID(project.getMarketplaceId(), new AsyncCallback<Marketplace>() {

			@Override
			public void onFailure(Throwable caught) {
				ClientsideSettings.getLogger().severe("Fehler bei der Abfrage des Projektmarktplatzes");
			}

			@Override
			public void onSuccess(Marketplace result) {
				List<Project> projectList = projectDataProviders.get(result).getList();
				
				for(int i = 0; i < projectList.size(); i++) {
					if(project.getId() == projectList.get(i).getId()) {
						projectList.set(i, project);
						break;
					}
				}
			}
			
		});
		
	}
	
	/**
	 * Ein gelöschtes Projekt-Objekt fliegt aus der Baumstruktur.
	 * Das hat allerdings nichts mit der Löschung von Objekten in der
	 * Applikations- und Tupeln in der Datenbank-Schicht zu tun. Es ist
	 * durchaus möglich, dass die Objekte dort nicht "hart" gelöscht,
	 * sondern nur auf inaktiv gesetzt werden o. ä. Wichtig ist bei 
	 * dieser Methode insbesondere, dass vor der Löschung des Projekts
	 * selbst erst noch die ihm untergeordneten Ausschreibungen
	 * gelöscht werden. Das ist zwar nicht (wie z. B. bei Datenbanken)
	 * notwendig, der Nachvollziehbarkeit wegen wurde allerdings das
	 * kaskadierende Löschen so implementiert.
	 * 
	 * @param das zu löschende Projekt
	 */
	public void deleteProject(Project project, Marketplace marketplace) {
		// Wenn der Baumknoten noch nicht angelegt wurde, gibt's nicht zu tun
		if (!this.projectDataProviders.containsKey(marketplace)) {
			return;
		}		
		
		projectDataProviders.get(marketplace).getList().remove(project);
		selectionModel.setSelected(marketplace, true);
	}
	
	/**
	 * Füge eine neue Ausschreibung zur Baumstruktur hinzu.
	 * 
	 * @param die neue Ausschreibung
	 * @param das übergeordnete Projekt
	 */
	public void addJobPosting(JobPosting jobPosting, Project project) {
		// Wenn der Baumknoten noch nicht angelegt wurde, gibt's nicht zu tun
		if(!this.jobPostingDataProviders.containsKey(project)) {
			return;
		}
		// Erstelle ListDataProvider mit Ausschreibungen des ausgewählten Projekts
		ListDataProvider<JobPosting> jobPostingProvider = this.jobPostingDataProviders.get(project);
		if(!jobPostingProvider.getList().contains(jobPosting)) {
			// wenn noch nicht enthalten, füge hinzu
			jobPostingProvider.getList().add(jobPosting);
		}
		// Wähle neu erstelltes Projekt aus
		this.selectionModel.setSelected(jobPosting, true);
	}
	
	/**
	 * Wenn der Nutzer eine Ausschreibung ändert und speichert,
	 * sollte sich (bei der Änderung des Titels z. B.) auch das
	 * im Baum hinterlegte Objekt aktualisieren. Das wird mit
	 * <code>updateJobPosting()</code> realisiert.
	 * 
	 * @param die upzudatende Ausschreibung
	 */
	public void updateJobPosting(final JobPosting jobPosting) {
		this.pitchMenAdmin.getProjectByID(jobPosting.getProjectId(), new AsyncCallback<Project>() {

			@Override
			public void onFailure(Throwable caught) {
				ClientsideSettings.getLogger().severe("Fehler bei der Abfrage des Projektmarktplatzes");
			}

			@Override
			public void onSuccess(Project result) {
				List<JobPosting> jobPostingList = jobPostingDataProviders.get(result).getList();
				
				for(int i = 0; i < jobPostingList.size(); i++) {
					if(jobPosting.getId() == jobPostingList.get(i).getId()) {
						jobPostingList.set(i, jobPosting);
						break;
					}
				}
			}
			
		});
		
	}
	
	public void deleteJobPosting(JobPosting jobPosting, Project project) {
		// Wenn der Baumknoten noch nicht angelegt wurde, gibt's nicht zu tun
		if (!this.jobPostingDataProviders.containsKey(project)) {
			return;
		}		
		
		jobPostingDataProviders.get(project).getList().remove(jobPosting);
		selectionModel.setSelected(project, true);
	}

	/**
	 * Die Methode <code>getNodeInfo()</code> stellt Informationen
	 * über den aktuell angefragten Knoten der Baumstruktur zur
	 * Verfügung.
	 */
	@Override
	public <T> NodeInfo<?> getNodeInfo(T value) {
		
		/*
		 * Wenn value == null gilt, bedeutet das, dass die
		 * Wurzel der Baumstruktur abgefragt wird. In diesem
		 * Fall soll eine Ausgabe von MarketplaceCells 
		 * erfolgen.
		 */
		if(value == null) {
			// Erzeugen eines neuen ListDataProviders für Marketplaces
			this.marketplaceLDP = new ListDataProvider<Marketplace>();
			
			// Abfrage aller Marketplaces über die Applikationsschicht
			this.pitchMenAdmin.getMarketplaces(new AsyncCallback<ArrayList<Marketplace>>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
				}

				@Override
				public void onSuccess(ArrayList<Marketplace> result) {
					// Übertragen aller Marketplace-Objekte in den ListDataProvider
					for(Marketplace marketplace : result) {
						marketplaceLDP.getList().add(marketplace);
					}
				}
				
			});
			
			return new DefaultNodeInfo<Marketplace>(marketplaceLDP, new MarketplaceCell(), selectionModel, null);
		}
		
		/*
		 * Ist der value-Parameter vom Typ Marketplace, wird die 
		 * darunterliegende Hierarchie-Ebene der zu diesem 
		 * Marktplatz gehörenden Projekte in einen ListDataProvider
		 * geschrieben.
		 */
		if(value instanceof Marketplace) {
			final ListDataProvider<Project> projectLDP = new ListDataProvider<Project>();
			this.projectDataProviders.put((Marketplace) value, projectLDP); 			
			int marketplaceId = ((Marketplace) value).getId();
			
			this.pitchMenAdmin.getProjectsByMarketplaceId(marketplaceId, new AsyncCallback<ArrayList<Project>>()  {

				@Override
				public void onFailure(Throwable caught) {
					ClientsideSettings.getLogger().severe("Projekte konnten nicht abgefragt werden");
				}

				@Override
				public void onSuccess(ArrayList<Project> result) {
					for(Project project : result) {
						projectLDP.getList().add(project);
					}
				}
			 	
			});
			
			return new DefaultNodeInfo<Project>(projectLDP, new ProjectCell(), selectionModel, null);
		} 
		/*
		 * Ist der value-Parameter vom Typ Project, wird die 
		 * darunterliegende Hierarchie-Ebene der zu diesem 
		 * Projekt gehörenden Ausschreibungen in einen ListDataProvider
		 * geschrieben.
		 */
		if(value instanceof Project) {
			final ListDataProvider<JobPosting> jobPostingLDP = new ListDataProvider<JobPosting>();
			this.jobPostingDataProviders.put((Project) value, jobPostingLDP); 			
			int projectId = ((Project) value).getId();
			
			this.pitchMenAdmin.getJobPostingsByProjectId(projectId, new AsyncCallback<ArrayList<JobPosting>>()  {

				@Override
				public void onFailure(Throwable caught) {
					ClientsideSettings.getLogger().severe("Projekte konnten nicht abgefragt werden");
				}

				@Override
				public void onSuccess(ArrayList<JobPosting> result) {
					for(JobPosting project : result) {
						jobPostingLDP.getList().add(project);
					}
				}
			 	
			});
			
			return new DefaultNodeInfo<JobPosting>(jobPostingLDP, new JobPostingCell(), selectionModel, null);
		}
		return null;
	}
	
	/**
	 * Die Methode <code>isLeaf()</code> überprüft, ob es sich
	 * bei dem aufgerufenen Baumknoten um ein Blatt handelt (d. h.
	 * der Knoten hat keine Kindknoten). Im Falle der PitchMen-
	 * Applikation ist das der Fall, wenn der Knoten vom Typ 
	 * Ausschreibung ({@link JobPosting}) ist. 
	 */

	@Override
	public boolean isLeaf(Object value) {
		// Prüfe ob der aufgerufene Knoten ein jobPosting-Objekt ist
		return (value instanceof JobPosting);
	}

}
