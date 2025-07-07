package application;

public class Listing {

	private Book book;
	private String condition;
	private float generatedPrice;
	private User seller;
	private boolean isActive;
	private boolean isPurchased;

	public Listing(Book book, String condition, float generatedPrice, User user) {
		this.book = book;
		this.condition = condition;
		this.generatedPrice = generatedPrice;
		this.seller = user;
		isActive = true;
		isPurchased = false;
	}

	public Book getBook() {
		return book;
	}

	public String getCondition() {
		return condition;
	}

	public float getGeneratedPrice() {
		return generatedPrice;
	}
	
	public User getSeller() {
		return seller;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public boolean getIsPurchased() {
		return isPurchased;
	}

	public void setActive() {
		isActive = true;
	}

	public void setInactive() {
		isActive = false;
	}

	public void setPurchased() {
		isPurchased = true;
	}

	public void setNotPurchased() {
		isPurchased = false;
	}

	public void purchaseListing() {
		setPurchased();
		// create record of transaction here
	}
}
