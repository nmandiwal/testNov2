package webCrawler;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PageScanner {
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";

	/**
	 * This performs all the work. It makes an HTTP request, checks the response,
	 * and then gathers up all the links on the page.
	 * 
	 * @param url - The URL to visit
	 * @return list of collected URLs
	 */
	public List<String> collectURLs(String url) {
		List<String> links = new LinkedList<String>();
		try {
			Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
			Document htmlDocument = connection.get();

			if (!connection.response().contentType().contains("text/html")) {
				System.out.println("**Failure** Retrieved something other than HTML");
				return links;
			}
			Elements linksOnPage = htmlDocument.select("a[href]");
			for (Element link : linksOnPage) {
				links.add(link.absUrl("href"));
			}
		} catch (IOException ioe) {
			// eat it up. not interested in collecting non working URLs
			ioe.printStackTrace();
		}
		return links;
	}

}
