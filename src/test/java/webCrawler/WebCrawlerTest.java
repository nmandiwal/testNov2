package webCrawler;

import junit.framework.TestCase;
import webCrawler.Crawler;

public class WebCrawlerTest extends TestCase {

	public void test1() {
		Crawler spider = new Crawler();
		spider.search("http://wiprodigital.com");
	}
}
