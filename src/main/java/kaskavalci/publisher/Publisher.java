package kaskavalci.publisher;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import kaskavalci.items.Item;
import kaskavalci.utils.MaintainREST;

public class Publisher {

	public static String getTweaker() {
		String retVal = null;

		try {
			
			Element rootElement = MaintainREST.getElementFromCurl(MaintainREST.getURL("tweaker", null));

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
