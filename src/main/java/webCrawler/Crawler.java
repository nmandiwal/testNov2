package webCrawler;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Crawler {

	// Fields
	private Set<String> pagesVisited = new HashSet<>();
	private Set<String> pagesToVisit = new HashSet<>();

	/**
	 * This method removes http/https for the start of URL.
	 * 
	 * @param url
	 * @return
	 */
	private String getDomainName(String url) {
		return url.replaceFirst("https://", "").replaceFirst("http://", "");
	}

	public void search(String url) {
		String urlDomain;
		urlDomain = getDomainName(url);
		do {
			String currentUrl;
			PageScanner leg = new PageScanner();
			if (this.pagesToVisit.isEmpty()) {
				currentUrl = url;
			} else {
				currentUrl = this.pagesToVisit.iterator().next();
				this.pagesToVisit.remove(currentUrl);
			}
			this.pagesVisited.add(currentUrl);

			this.pagesToVisit.addAll(
					leg.collectURLs(currentUrl).stream().filter(line -> getDomainName(line).startsWith(urlDomain))
							.filter(line -> !this.pagesVisited.contains(line)).collect(Collectors.toSet()));
		} while (this.pagesToVisit.size() > 0);

		for (String urlVisited : this.pagesVisited) {
			System.out.println(urlVisited);
		}
	}
}
