package kaskavalci.items;

public class Item {
	private String title;
	private String category;
	private String pubDate;

	public Item(String title, String category, String pubDate) {
		super();
		this.title = title;
		this.category = category;
		this.pubDate = pubDate;
	}

	public String getTitle() {
		return title;
	}

	public String getCategory() {
		return category;
	}

	public String getPubDate() {
		return pubDate;
	}
}