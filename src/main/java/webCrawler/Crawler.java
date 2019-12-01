package webCrawler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

public class Crawler {

	// Fields
	private Set<String> pagesVisited = new HashSet<>();
	private Set<String> pagesToVisit = new HashSet<>();
	private Map<String, CrawlerData> crawlerDataMap = new HashMap<>();

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
			PageScanner pageScanner = new PageScanner();
			if (this.pagesToVisit.isEmpty()) {
				currentUrl = url;
			} else {
				currentUrl = this.pagesToVisit.iterator().next();
				this.pagesToVisit.remove(currentUrl);
			}
			this.pagesVisited.add(currentUrl);

			this.pagesToVisit.addAll(pageScanner.collectURLs(currentUrl).stream().peek(streamURL -> {
				CrawlerData crawlerData;
				if (!getDomainName(streamURL).startsWith(urlDomain)) {
					crawlerData = getCrawlerData(currentUrl);
					crawlerData.getExternalURLList().add(streamURL);
				} else if (new String(streamURL).endsWith(".png")) {
					crawlerData = getCrawlerData(currentUrl);
					crawlerData.getStaticContentURLList().add(streamURL);
				}

			})

					.filter(line -> getDomainName(line).startsWith(urlDomain))
					.filter(line -> !this.pagesVisited.contains(line)).collect(Collectors.toSet()));
		} while (this.pagesToVisit.size() > 0);

		for (Entry<String, CrawlerData> entry : this.crawlerDataMap.entrySet()) {
			System.out.println(String.format("\nDomain URL No %d:  %s", entry.getKey()));
			System.out.println("ExternalURLs:");
			for (String externalURL : entry.getValue().getExternalURLList()) {
				System.out.println("   " + externalURL);
			}
			System.out.println("Static ContentURLs:");
			for (String externalURL : entry.getValue().getStaticContentURLList()) {
				System.out.println("   " + externalURL);
			}
		}
	}

	/**
	 * This method set the CrawlerData if not available and then returns it.
	 * 
	 * @param currentUrl
	 * @return
	 */
	private CrawlerData getCrawlerData(String currentUrl) {
		if (null == crawlerDataMap.get(currentUrl)) {
			crawlerDataMap.put(currentUrl, new CrawlerData());
		}
		return crawlerDataMap.get(currentUrl);
	}
}
