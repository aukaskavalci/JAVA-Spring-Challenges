package kaskavalci.publisher;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import kaskavalci.items.Item;

public class Publisher {

	public static String getTweaker() {
		String retVal = null;

		try {

			String url = "https://tweakers.net/feeds/mixed.xml";

			URL obj = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
			conn.setReadTimeout(5000);
			conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
			conn.addRequestProperty("User-Agent", "Mozilla");
			conn.addRequestProperty("Referer", "google.com");

			System.out.println("Request URL ... " + url);

			boolean redirect = false;

			// normally, 3xx is redirect
			int status = conn.getResponseCode();
			if (status != HttpURLConnection.HTTP_OK) {
				if (status == HttpURLConnection.HTTP_MOVED_TEMP || status == HttpURLConnection.HTTP_MOVED_PERM
						|| status == HttpURLConnection.HTTP_SEE_OTHER)
					redirect = true;
			}

			System.out.println("Response Code ... " + status);

			if (redirect) {

				// get redirect url from "location" header field
				String newUrl = conn.getHeaderField("Location");

				// get the cookie if need, for login
				String cookies = conn.getHeaderField("Set-Cookie");

				// open the new connnection again
				conn = (HttpURLConnection) new URL(newUrl).openConnection();
				conn.setRequestProperty("Cookie", cookies);
				conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
				conn.addRequestProperty("User-Agent", "Mozilla");
				conn.addRequestProperty("Referer", "google.com");

				System.out.println("Redirect to URL : " + newUrl);

			}

			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer html = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				html.append(inputLine);
			}
			in.close();

			System.out.println("URL Content... \n" + html.toString());

			String xml = html.toString();

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new StringReader(xml)));
			Element rootElement = document.getDocumentElement();

			NodeList list = rootElement.getElementsByTagName("item");
			String title, category, pubDate;
			List<Item> itemList = new ArrayList<Item>();
			if (list != null && list.getLength() > 0) {
				for (int i = 0; i < list.getLength(); i++) {
					NodeList subList = list.item(i).getChildNodes();
					title = subList.item(0).getTextContent();
					category = subList.item(4).getTextContent();
					pubDate = subList.item(7).getTextContent();
					itemList.add(new Item(title, category, pubDate));

				}
			}
			retVal = itemList.get(0).getTitle().toString();
			System.out.println(retVal);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return retVal;

	}
}
