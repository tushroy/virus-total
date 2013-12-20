package com.api.virustotal;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class VirusTotal {
	private static final String URL_VIRUS_TOTAL = "https://www.virustotal.com/en/file/%s/analysis/";

	/**
	 * Retrieves the virus scan results, given the SHA256 value of the file
	 * contents
	 * 
	 * @param keyword
	 *            - The SHA256 value of the file contents
	 * @return A set of reports. Each report represents the results from an
	 *         Anti-Virus vendor
	 * @throws IOException
	 */
	public static Set<Report> scan(String keyword) throws IOException {
		Set<Report> reports = new HashSet<>();

		String VT_URL = String.format(URL_VIRUS_TOTAL, keyword);
		Document doc; // will contain the HTML response

		doc = Jsoup
				.connect(VT_URL)
				.userAgent(
						"Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
				.referrer("http://www.google.com").get();

		Element content = doc.getElementById("active-tab");

		// Means there were no results / file has never been scanned by
		// VirusTotal
		if (content == null) {
			return reports;
		}

		Elements links = content.getElementsByTag("td");

		Object[] lines = links.toArray();

		int idx = 0;
		Report report = new Report();

		for (Object line : lines) {
			Element e = (Element) line;

			switch (idx) {
			case 0:
				report.setVendor(e.text());
				break;
			case 1:
				if (!e.text().equals("-")) {
					report.setMalwarename(e.text());
				} else {
					report.setMalwarename("");
				}
				break;
			case 2:
				report.setUpdate(e.text());
				reports.add(report);
				report = new Report();
				idx = -1;
				break;
			}

			idx++;
		}

		return reports;
	}

}
