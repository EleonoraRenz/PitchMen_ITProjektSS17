package de.pitchMen.shared.report;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

import de.pitchMen.client.ClientsideSettings;

/**
 * Subklasse von ReportWriter.
 * 
 * "Ein <code>ReportWriter</code>, der Reports mittels HTML formatiert. Das im
 * Zielformat vorliegende Ergebnis wird in der Variable <code>reportText</code>
 * abgelegt und kann nach Aufruf der entsprechenden Prozessierungsmethode mit
 * <code>getReportText()</code> ausgelesen werden.
 * 
 * @quelle Thies."
 * 
 * 
 * @author
 */
public class HTMLReportWriter extends ReportWriter {

	/**
	 * Dieser String wird später die Ausgabe des ReportWriters enthalten. Er
	 * wird leer deklariert.
	 */
	private String reportText = "";

	/**
	 * Setzt die Ausgabe des ReportWriters zurück.
	 */
	public void resetReportText() {
		this.reportText = "";
	}

	/**
	 * Gibt die Ausgabe des ReportWriters zurück.
	 */

	public String paragraphToHtml(Paragraph p) {
		if (p instanceof CompositeParagraph) {
			return this.paragraphToHtml((CompositeParagraph) p);
		} else {
			return this.paragraphToHTML((SimpleParagraph) p);
		}
	}

	public String paragraphToHTML(SimpleParagraph s) {
		return "<p>" + s.toString() + "</p>";
	}

	public String paragraph2HTML(CompositeParagraph p) {
		StringBuffer result = new StringBuffer();

		for (int i = 0; i < p.getNumParagraphs(); i++) {
			result.append("<p>" + p.getParagraphAt(i) + "</p>");
		}

		return result.toString();
	}

	/**
	 * HTML Header schreiben
	 * 
	 * @return HTML
	 */
	public String getHead() {
		StringBuffer buff = new StringBuffer();

		buff.append("<html><head><title></title></head><body>");
		return buff.toString();
	}

	/**
	 * HTML Trailer schreiben
	 * 
	 * @return HTML
	 */
	public String getTrailer() {
		return "</body></html>";
	}

	// ---------- process(AllJobPostings)

	public void process(AllJobPostings a) {
		// zurücksetzen des Ausgabe-Strings
		this.resetReportText();

		// StringBuffer erzeugen
		StringBuffer buff = new StringBuffer();

		buff.append("<h4>" + a.getTitle() + "</h4>");
		buff.append("<p><table border=1px rules=all> <tr>");
		// buff.append("<td> ID </td> ");
		// buff.append("<td> Titel </td>");
		// buff.append("<td> Bewerbungstext </td>");
		// buff.append("<td> ProjektID </td>");
		// buff.append("<td> Enddatum </td>");
		// buff.append("<td> Status </td></tr>");
		// buff.append("<tr><td>" + a.getJobPostingId() + "</td>");

		ArrayList<Row> rows = a.getRows();
		if (rows != null) {
			ClientsideSettings.getLogger().info("Rows in Array ist ungleich Null");
		}

		for (int i = 0; i < rows.size(); i++) {
			buff.append("<tr>");
			Row row = rows.get(i);

			for (int x = 0; x < row.getNumberOfColumns(); x++) {

				buff.append("<td>" + row.getColumnAt(x) + "</td>");

			}
			buff.append("</tr>");
		}

		buff.append("</table>");
		this.reportText = buff.toString();
	}

	// ---------- process(ApplicationsRelatedToJobPostingsOfUser)

	public void process(AllJobPostingsMatchingPartnerProfileOfUser a) {
		// zurücksetzen des Ausgabe-Strings
		this.resetReportText();

		// StringBuffer erzeugen
		StringBuffer buff = new StringBuffer();
		buff.append("<h4>" + a.getTitle() + "</h4>");
		buff.append("<p><table border=1px rules=all> <tr>");
		// table und tr und td öffnen

		ArrayList<Row> rows = a.getRows();
		if (rows != null) {
			ClientsideSettings.getLogger().info("Es sind Zeilen im Array enthalten");
			
			}
		
		for (int i = 0; i < rows.size(); i++) {
			Row row = rows.get(i);
			buff.append("<tr>");
			for (int x = 0; x < row.getNumberOfColumns(); x++) {
				buff.append("<td>" + row.getColumnAt(x) + "</td>");
			}
			buff.append("</tr>");
		}

		buff.append("</table>");
		this.reportText = buff.toString();
	}

	// ---------- process(ApplicationsRelatedToJobPostingsOfUser)

	public void process(ApplicationsRelatedToJobPostingsOfUser a) {
		// zurücksetzen des Ausgabe-Strings
		this.resetReportText();

		// StringBuffer erzeugen
		StringBuffer buff = new StringBuffer();

		// Titel des Reports ausgeben
		buff.append("<h4>" + a.getTitle() + "</h4>");

		// ./p f�r einen Abstand und Tabelle �ffnen. Zuerst eine Row �ffnen um
		// anschlie�end der Row Columns hinzuzuf�gen.
		buff.append("<p><table border=1px rules=all> <tr>");

		// Erzeugen einer ArrayList f�r die Reihen der AUsgabe. Zuweisung der
		// Rows aus dem �bergabeobjekt.

		/**
		 * For Schleife zum Abfragen der einzelnen Reihen des �bergebenen
		 * Objekts. Erstellen der Reihen durch
		 * <tr>
		 * und in der nachfolgenden Schleife die jeweiligen Spalten mit
		 * <td>
		 * 
		 */
		for (int i = 0; i < a.getNumSubReports(); i++) {

			AllApplicationsToOneJobPostingOfUser subReport = (AllApplicationsToOneJobPostingOfUser) a.getSubReportAt(i);

			this.process(subReport);

			buff.append(this.reportText + "\n");

			this.resetReportText();
		}

		this.reportText = buff.toString();
	}

	// ---------- process(AllApplicationsToOneJobPostingOfUser)
	public void process(AllApplicationsToOneJobPostingOfUser a) {
		// zurücksetzen des Ausgabe-Strings
		this.resetReportText();

		// StringBuffer erzeugen
		StringBuffer buff = new StringBuffer();

		buff.append("<h4>" + a.getTitle() + "</h4>");
		buff.append("<p><table border=1px rules=all> <tr>");

		ArrayList<Row> rows = a.getRows();
		if (rows != null) {
			ClientsideSettings.getLogger().info("Rows in Array ist ungleich Null");
		}

		for (int i = 0; i < rows.size(); i++) {
			buff.append("<tr>");
			Row row = rows.get(i);

			for (int x = 0; x < row.getNumberOfColumns(); x++) {

				buff.append("<td>" + row.getColumnAt(x) + "</td>");

			}
			buff.append("</tr>");
		}

		buff.append("</table>");
		this.reportText = buff.toString();
	}

	// ---------- process(AllApplicationsOfUser)

	public void process(AllParticipationsOfOneUser a) {
		// zurücksetzen des Ausgabe-Strings
		this.resetReportText();

		// StringBuffer erzeugen
		StringBuffer buff = new StringBuffer();

		// Titel des Reports ausgeben
		buff.append("<h4>" + a.getTitle() + "</h4>");

		// ./p f�r einen Abstand und Tabelle �ffnen. Zuerst eine Row �ffnen um
		// anschlie�end der Row Columns hinzuzuf�gen.
		buff.append("<p><table border=1px rules=all> <tr>");

		// Erzeugen einer ArrayList f�r die Reihen der AUsgabe. Zuweisung der
		// Rows aus dem �bergabeobjekt.
		ArrayList<Row> rows = a.getRows();

		// Pr�fung, ob in dem Array was drinne ist.
		if (rows == null) {
			ClientsideSettings.getLogger().info("Das Array beinhaltet keine Rows");
		}

		/**
		 * For Schleife zum Abfragen der einzelnen Reihen des �bergebenen
		 * Objekts. Erstellen der Reihen durch
		 * <tr>
		 * und in der nachfolgenden Schleife die jeweiligen Spalten mit
		 * <td>
		 * 
		 */
		for (int i = 0; i < rows.size(); i++) {
			buff.append("<tr>");
			Row row = rows.get(i);

			for (int x = 0; x < row.getNumberOfColumns(); x++) {
				buff.append("<td>" + row.getColumnAt(x) + "</td>");
			}
			buff.append("</tr>");
		}

		buff.append("</table");

		// abschlie�end wird der erzeuge und in buff gespeicherte Text in einen
		// String transformiert
		this.reportText = buff.toString();

	}

	// ---------- process(AllApplicationsOfUser)

	public void process(AllApplicationsOfUser a) {
		// zurücksetzen des Ausgabe-Strings
		this.resetReportText();

		// StringBuffer erzeugen
		StringBuffer buff = new StringBuffer();

		// Titel des Reports ausgeben
		buff.append("<h4>" + a.getTitle() + "</h4>");

		// ./p f�r einen Abstand und Tabelle �ffnen. Zuerst eine Row �ffnen um
		// anschlie�end der Row Columns hinzuzuf�gen.
		buff.append("<p><table border=1px rules=all> <tr>");

		// Erzeugen einer ArrayList f�r die Reihen der AUsgabe. Zuweisung der
		// Rows aus dem �bergabeobjekt.
		ArrayList<Row> rows = a.getRows();

		// Pr�fung, ob in dem Array was drinne ist.
		if (rows == null) {
			ClientsideSettings.getLogger().info("Das Array beinhaltet keine Rows");
		}

		/**
		 * For Schleife zum Abfragen der einzelnen Reihen des �bergebenen
		 * Objekts. Erstellen der Reihen durch
		 * <tr>
		 * und in der nachfolgenden Schleife die jeweiligen Spalten mit
		 * <td>
		 * 
		 */
		for (int i = 0; i < rows.size(); i++) {
			buff.append("<tr>");
			Row row = rows.get(i);

			for (int x = 0; x < row.getNumberOfColumns(); x++) {
				buff.append("<td>" + row.getColumnAt(x) + "</td>");
			}
			buff.append("</tr>");
		}

		buff.append("</table");

		// abschlie�end wird der erzeuge und in buff gespeicherte Text in einen
		// String transformiert
		this.reportText = buff.toString();

	}

	// ---------- process(ProjectInterweavingsWithParticipationsAndApplications)

	public void process(ProjectInterweavingsWithParticipationsAndApplications a) {
		// zurücksetzen des Ausgabe-Strings
		this.resetReportText();

		// StringBuffer erzeugen
		StringBuffer buff = new StringBuffer();

		// Titel des Reports ausgeben
		buff.append("<h4>" + a.getTitle() + "</h4>");

		// Erzeugen einer ArrayList f�r die Reihen der AUsgabe. Zuweisung der
		// Rows aus dem �bergabeobjekt.

		/**
		 * For Schleife zum Abfragen der einzelnen Reihen des �bergebenen
		 * Objekts. Erstellen der Reihen durch
		 * <tr>
		 * und in der nachfolgenden Schleife die jeweiligen Spalten mit
		 * <td>
		 * 
		 */
		for (int i = 0; i < a.getNumSubReports(); i++) {

			this.processSimpleReport(a.getSubReportAt(i));

			buff.append(this.reportText + "\n");

			this.resetReportText();
		}

		this.reportText = buff.toString();
	}

	// ---------- process(FanInJobPostingsOfUser)

	public void process(FanInJobPostingsOfUser a) {
		// zurücksetzen des Ausgabe-Strings
		this.resetReportText();

		// StringBuffer erzeugen
		StringBuffer buff = new StringBuffer();
		buff.append("<p><strong>" + a.getDatecreated().toString() + "</strong></p>");
		// table und tr und td öffnen
		buff.append("<table><tr>");
		buff.append("<td>" + paragraphToHtml(a.getHeaderData()) + "</td>");
		buff.append("</tr>");

		ArrayList<Row> rows = a.getRows();

		for (int i = 0; i < rows.size(); i++) {
			Row row = rows.get(i);
			buff.append("<tr>");
			for (int x = 0; x < row.getNumberOfColumns(); x++) {
				buff.append("<td>" + row.getColumnAt(x) + "</td");
			}
			buff.append("</tr");
		}

		buff.append("</table");
		this.reportText = buff.toString();
	}

	// ---------- process(FanOutApplicationsOfUser)

	public void process(FanOutApplicationsOfUser a) {
		// zurücksetzen des Ausgabe-Strings
		this.resetReportText();

		// StringBuffer erzeugen
		StringBuffer buff = new StringBuffer();
		buff.append("<p><strong>" + a.getDatecreated().toString() + "</strong></p>");
		// table und tr und td öffnen
		buff.append("<table><tr>");
		buff.append("<td>" + paragraphToHtml(a.getHeaderData()) + "</td>");
		buff.append("</tr>");

		ArrayList<Row> rows = a.getRows();

		for (int i = 0; i < rows.size(); i++) {
			Row row = rows.get(i);
			buff.append("<tr>");
			for (int x = 0; x < row.getNumberOfColumns(); x++) {
				buff.append("<td>" + row.getColumnAt(x) + "</td");
			}
			buff.append("</tr");
		}

		buff.append("</table");
		this.reportText = buff.toString();
	}

	// ---------- process(FanInAndOutReport)

	public void process(FanInAndOutReport a) {
		// zurücksetzen des Ausgabe-Strings
		this.resetReportText();

		// StringBuffer erzeugen
		StringBuffer buff = new StringBuffer();
		// // Anzahl der Bewerbungen mit "laufend"
		// int x;
		// // Anzahl der Bewerbungen mit "abgelehnt"
		// int y;
		// ArrayList<Application> applications =
		// ClientsideSettings.getPitchMenAdmin().getApplications(new
		// AsyncCallback<ArrayList<Application>>);

		// HTML titel = new HTML("<p>" + a.getTitle() + "</p>");
		// titel.setStyleName("header");
		// buff.append("<p>" + a.getTitle() + "</p>");
		// buff.append("<table><tr>");
		// buff.append("<td>" + paragraphToHtml(a.getHeaderData()) + "</td>");
		// buff.append("</tr>");
		//

		buff.append("<h4>" + a.getTitle() + "</h4>");
		// Fan-In-Ausgabe
		buff.append("<p><h5>Bewerbungen</h5>");
		buff.append("<table border = 1px rules = all><tr>");
		buff.append("<th> Status </th>");
		buff.append("<th> Anzahl Ihrer Bewerbungen </th></tr>");

		buff.append("<tr><td>Laufend</td>");
		//+ 
		//"<td>" + this.processSimpleReport(a.getSubReportAt(0)) + "</td></tr>");
		buff.append("<tr><td>Abgelehnt</td>");
		//+ "<td>"
		// + for(Application application : applications){
		// if(application.getStatus() == "laufend")
		// x++;
		// }
		//		+ "</td></tr>");
		buff.append("<tr><td>Angenommen</td>" + "<td>Zahl</td></tr></p>");

		// Fan-Out-Analyse
		buff.append("<p><h5>Ausschreibungen</h5>");
		buff.append("<table border = 1px rules = all><tr>");
		buff.append("<th>Status</th>");
		buff.append("<th>Anzahl Ihrer Ausschreibungen</th></tr></p>");

		buff.append("<tr><td>Besetzt</td>" + "<td>Zahl</td></tr>");
		buff.append("<tr><td>Abgebrochen</td>" + "<td>Zahl</td></tr>");
		buff.append("<tr><td>Laufend</td>" + "<td>Zahl</td></tr></p>");

		// for(int i=0; i< a.getNumSubReports();i++){
		// FanInJobPostingsOfUser subReportone = (FanInJobPostingsOfUser)
		// a.getSubReportAt(i);
		//
		// this.process(subReportone);
		//
		// buff.append(reportText + "\n");
		//
		// resetReportText();
		// }

		buff.append("</table>");
		this.reportText = buff.toString();
	}

	// TODO Beschreibung
	public String getReportText() {
		return this.getHead() + this.reportText + this.getTrailer();
	}

	@Override
	public void process(AllApplicationsOfOneUser a) {
		// TODO Auto-generated method stub

	}

	public void processSimpleReport(Report report) {

		SimpleReport r = (SimpleReport) report;

		// L�schen des Ergebnisses der vorherigen Prozessierung
		this.resetReportText();

		/**
		 * In diesen Buffer schreiben wir w�hrend der Prozessierung sukzessive
		 * unsere Ergebnisse.
		 */
		StringBuffer result = new StringBuffer();

		/**
		 * Nun werden Schritt f�r Schritt die einzelnen Bestandteile des Reports
		 * ausgelesen und in HTML-Form �bersetzt.
		 */
		result.append("<h4>" + r.getTitle() + "</h4>");
		result.append("<p><table border=1px rules=all> <tr>");

		ArrayList<Row> rows = r.getRows();

		for (int i = 0; i < rows.size(); i++) {
			Row row = rows.get(i);
			result.append("<tr>");
			for (int k = 0; k < row.getNumberOfColumns(); k++) {

				if (i > 1) {
					result.append("<td>" + row.getColumnAt(k) + "</td>");
				}

			}
			result.append("</tr>");
		}

		result.append("</table>");

		/**
		 * Zum Schluss wird unser Arbeits-Buffer in einen String umgewandelt und
		 * der reportText-Variable zugewiesen. Dadurch wird es m�glich,
		 * anschlie�end das Ergebnis mittels getReportText() auszulesen.
		 */
		this.reportText = result.toString();
	}

}
