package application;

import java.util.ArrayList;

public class Library {

	private ArrayList<Book> bookList = new ArrayList<>();
	private ArrayList<Listing> activeListings = new ArrayList<>();
	private ArrayList<Listing> inactiveListings = new ArrayList<>();

	public Library() {
		// load any books and listings from text file / database
	}

	public void createNewListing(String title, String author, int year, String category, String condition,
			float originalPrice, float generatedPrice, User seller) {
		if(!bookAlreadyExists(title, author, year)) {
			createNewBook(category, originalPrice, title, author, year);
		}
		createListing(findBook(title, author, year), condition, generatedPrice, seller);
	}

	private boolean bookAlreadyExists(String title, String author, int year) {
		if(findBook(title, author, year) < 0) {
			return false;
		}
		else {
			return true;
		}
	}

	private int findBook(String title, String author, int year) {
		if(bookList.size() > 0) {
			Book currBook;
			int i;
			for(i = 0; i < bookList.size(); i++) {
				currBook = bookList.get(i);
				if(currBook.getTitle().equalsIgnoreCase(title) &&
						currBook.getAuthor().equalsIgnoreCase(author) &&
						currBook.getYear() == year) {
					return i;
				}
			}
		}
		return -1;
	}

	private void createNewBook(String category, float originalPrice, String title, String author, int year) {
		bookList.add(new Book(category, originalPrice, title, author, year));
	}

	private void createListing(int book, String condition, float generatedPrice, User seller) {
		activeListings.add(new Listing(bookList.get(book), condition, generatedPrice, seller));

	}

}
