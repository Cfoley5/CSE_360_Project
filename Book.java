package application;

public class Book {
	
	private String category;
	private float originalPrice;
	private String title;
	private String author;
	private int year;

	public Book(String category, float originalPrice, String title, String author, int year) {

		this.category = category;
		this.originalPrice = originalPrice;
		this.title = title;
		this.author = author;
		this.year = year;
	}

	public String getCategory() {
		return category;
	}

	public float getOriginalPrice() {
		return originalPrice;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public int getYear() {
		return year;
	}

}
