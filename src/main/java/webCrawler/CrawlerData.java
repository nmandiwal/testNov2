package webCrawler;

import java.util.HashSet;
import java.util.Set;

public class CrawlerData {
	String pageURL;
	Set<String> externalURLList = new HashSet<>();
	Set<String> staticContentURLList = new HashSet<>();

}
