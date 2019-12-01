package webCrawler;

import java.util.HashSet;
import java.util.Set;

public class CrawlerData {
	private Set<String> externalURLList = new HashSet<>();
	private Set<String> staticContentURLList = new HashSet<>();

	public Set<String> getExternalURLList() {
		return externalURLList;
	}

	public Set<String> getStaticContentURLList() {
		return staticContentURLList;
	}

}
