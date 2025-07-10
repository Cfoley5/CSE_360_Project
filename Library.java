package application;

import java.util.ArrayList;

public class Library {
	
	private ArrayList<Book> bookList = new ArrayList<>();
	private ArrayList<Listing> activeListings = new ArrayList<>();
	private ArrayList<Listing> inactiveListings = new ArrayList<>();
	private ArrayList<Listing> purchasedListings = new ArrayList<>();
	private ArrayList<User> allUsers = new ArrayList<>();
	
	public Library() {
		// load any books and listings from text file / database
	}
	
	public int getNumberActiveListings() {
		return activeListings.size();
	}

	public int getNumberPurchasedListings() {
		return purchasedListings.size();
	}

	public int getNumberUsers() {
		return allUsers.size();
	}
	
	public ArrayList<User> loadRelevantUsers(String rolesSelected) {
		if(rolesSelected.equals("Filter by role")) {
			return allUsers;
		} else {
			ArrayList<User> relevantUsers = new ArrayList<>();

			User currUser;

			if(allUsers.size() != 0) {
				for(int i = 0; i < allUsers.size(); i++) {
					currUser = allUsers.get(i);
					if(currUser.getIsAdmin() == rolesSelected.equals("Admin")) {
						relevantUsers.add(currUser);
					}
				}
			}

			return relevantUsers;
		} 
	}

	public void addNewUser(String AsuriteID, String password, boolean isAdmin) {
		if(!userAlreadyExists(AsuriteID)) {
			allUsers.add(new User(AsuriteID, password, isAdmin));
		}
	}

	private boolean userAlreadyExists(String AsuriteID) {
		if(allUsers.size() > 0) {
			User currUser;
			for(int i = 0; i < allUsers.size(); i++) {
				currUser = allUsers.get(i);
				if(currUser.getAsuriteID().equals(AsuriteID)) {
					return true;
				}
			}
		}
		return false;
	}

	public int getUser(String AsuriteID) {
		if(allUsers.size() > 0) {
			User currUser;
			int i;
			for(i = 0; i < allUsers.size(); i++) {
				currUser = allUsers.get(i);
				if(currUser.getAsuriteID().equals(AsuriteID)) {
					return i;
				}
			}
		}
		return -1;
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
		availableListingsFileWrite();
	}
	
	public ArrayList<Listing> loadRelevantListings(String category, boolean likeNewBool, boolean moderateBool, boolean heavyBool) {
		ArrayList<Listing> relevantListings = new ArrayList<>();
		
		Listing currListing;
		
		if(activeListings.size() != 0) {
			for(int i = 0; i < activeListings.size(); i++) {
				currListing = activeListings.get(i);
				if(category.equals("Select a category") || currListing.getBook().getCategory().equals(category)) {
					if((currListing.getCondition().equals("Used like new") && likeNewBool) || 
							(currListing.getCondition().equals("Moderately used") && moderateBool) ||
							(currListing.getCondition().equals("Heavily used") && heavyBool)) {
						relevantListings.add(currListing);
					}
				}
			}
		}
		
		return relevantListings;
	}
	
	public void purchaseListing(Listing listing) {
		listing.purchaseListing();
		activeListings.remove(listing);
		inactiveListings.add(listing);
		purchasedListings.add(listing);
		availableListingsFileWrite();
		salesRecordFileWrite(listing);
		buyerRecordFileWrite(listing);
	}
	
	public void removeListing(Listing listing) {
		listing.setInactive();
		activeListings.remove(listing);
		inactiveListings.add(listing);
		availableListingsFileWrite();
	}
	
	public void removeUser(User user) {
		allUsers.remove(user);
	}

	public void toggleUserRole(User user) {
		user.toggleIsAdmin();
	}
	
	public int authenticateLogin(String AsuriteID, String password, boolean isAdminSelected, boolean isStudentSelected) {
		if(!userAlreadyExists(AsuriteID)) {
			return -1;
		} else if(!isAdminSelected && !isStudentSelected) {
				return -2;
			} else {
				User user = allUsers.get(getUser(AsuriteID));
				if((!user.getPassword().equals(password)) || (user.getIsAdmin() != isAdminSelected)) {
				return -1;
			} else {
				return 0;
			}
		}
	}
	
	public void availableListingsFileWrite() {
		Listing currListing;
		Book currBook;
		FileSys.fileWrite("C:\\ASU Used Bookstore Files\\Books available for selling.txt", "");
		if(activeListings.size() != 0) {
			for(int i = 0; i < activeListings.size(); i++) {
				currListing = activeListings.get(i);
				currBook = currListing.getBook();
				String listingFormat = String.format("Title: %s\nAuthor: %s\nYear: %s\nCondition: %s\n-------------------------------------------------------\n",
						currBook.getTitle(), currBook.getAuthor(),currBook.getYear(),currListing.getCondition());
				FileSys.fileAppend("C:\\ASU Used Bookstore Files\\Books available for selling.txt", listingFormat);
			}
		}
	}
	
	public void salesRecordFileWrite(Listing listing) {
		Float earned = listing.getGeneratedPrice() * .1f;
		Book book = listing.getBook();
		String saleFormat = String.format("Title: %s\nEarned: $%.2f\nCategory: %s\n---------------------------------------------\n"
				, book.getTitle(), earned, book.getCategory());
		
		FileSys.fileAppend("C:\\ASU Used Bookstore Files\\Sales records.txt", saleFormat);
	}
	
	public void buyerRecordFileWrite(Listing listing) {
		Float toSeller = listing.getGeneratedPrice() * .9f;
		Book book = listing.getBook();
		String buyerFormat = String.format("Title: %s\nSeller: %s\nEarned: $%.2f\nCategory: %s\n---------------------------------------------\n"
				, book.getTitle(), listing.getSeller().getAsuriteID(), toSeller, book.getCategory());
		
		FileSys.fileAppend("C:\\ASU Used Bookstore Files\\Buying record.txt", buyerFormat);
	}
}