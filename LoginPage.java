package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;

public class LoginPage extends Application {
	
	public static void main( String[] args )
	{
		 launch(args);
	}
	
	@Override
    public void start(Stage primaryStage) {
		
		User user = new User("Name", "Pass", true);
		
		Library library = new Library();
		
		library.addNewUser("testStudent", "password", false);
		library.addNewUser("testAdmin", "password123", true);
		
		library.createNewListing("Words of Radiance", "Brandon Sanderson", 2014, "Other", "Used like new",
				19.99f, 17.99f, user);
		
		library.createNewListing("two", "Brandon Sanderson", 2014, "Other", "Used like new",
				19.99f, 17.99f, user);
		library.createNewListing("three", "Brandon Sanderson", 2014, "Other", "Used like new",
				19.99f, 17.99f, user);
		library.createNewListing("four", "Brandon Sanderson", 2014, "Other", "Used like new",
				19.99f, 17.99f, user);
		library.createNewListing("five", "Brandon Sanderson", 2014, "Other", "Used like new",
				19.99f, 17.99f, user);
		library.createNewListing("six", "Brandon Sanderson", 2014, "Other", "Used like new",
				19.99f, 17.99f, user);
		library.createNewListing("seven", "Brandon Sanderson", 2014, "Other", "Used like new",
				19.99f, 17.99f, user);
		library.createNewListing("eight", "Brandon Sanderson", 2014, "Other", "Used like new",
				19.99f, 17.99f, user);
		library.createNewListing("nine", "Brandon Sanderson", 2014, "Other", "Moderately used",
				19.99f, 17.99f, user);
		
		FileSys.fileWrite("C:\\ASU Used Bookstore Files\\Sales records.txt", "");
		FileSys.fileWrite("C:\\ASU Used Bookstore Files\\Buying record.txt", "");

		primaryStage.setScene(createLoginScene(user, library));
		
        primaryStage.show();

    }
	
	public Scene createLoginScene(User user, Library library) {
		TextField idField = new TextField();
		idField.setPromptText("ASU ID");
		TextField passwordField = new TextField();
		passwordField.setPromptText("Password");
		
		ToggleGroup studentAdmin = new ToggleGroup();
		
		RadioButton studentBtn = new RadioButton("Student");
		studentBtn.setToggleGroup(studentAdmin);
		RadioButton adminBtn = new RadioButton("Admin");
		adminBtn.setToggleGroup(studentAdmin);
		
		Button loginBtn = new Button();
		loginBtn.setText("Log in");

		Text logoTxt = new Text("ASU");		
		Text storeNameTxt = new Text("Sun Devil Used Bookstore");	
		storeNameTxt.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		Text loginTxt = new Text("Login");	
		Text errorTxt = new Text("");
		
		// LOGIN BUTTON ACTIONS //
		
		loginBtn.setOnAction(a -> {
			if(idField.getText().equalsIgnoreCase("") || passwordField.getText().equalsIgnoreCase("")) {
				errorTxt.setText("Incorrect ASU ID or password");
			}
			else if(studentAdmin.getSelectedToggle() == null) {
				errorTxt.setText("Please select Student or Admin role to continue");
			} else {
				int loginAttempt = library.authenticateLogin(idField.getText(), passwordField.getText(), studentAdmin.getSelectedToggle() == adminBtn, studentAdmin.getSelectedToggle() == studentBtn);
				if(loginAttempt == 0) {
					if(studentAdmin.getSelectedToggle() == adminBtn){
						user.changeScene(createAdminScene(user, library), a);
					}
					else if(studentAdmin.getSelectedToggle() == studentBtn) {
						user.changeScene(createSelectionScene(user, library), a);
					}
				} else {
					errorTxt.setText("Incorrect ASU ID or password");
				}
			}
			/*
			if(idField.getText().equalsIgnoreCase("") || passwordField.getText().equalsIgnoreCase("")) {
				errorTxt.setText("Incorrect ASU ID or password");
			}
			else if(studentAdmin.getSelectedToggle() == null) {
				errorTxt.setText("Please select Student or Admin role to continue");
			}
			else if(studentAdmin.getSelectedToggle() == adminBtn){
				user.changeScene(createAdminScene(user, library), a);
			}
			else if(studentAdmin.getSelectedToggle() == studentBtn) {
				user.changeScene(createSelectionScene(user, library), a);
			}
			*/
        });
	
		// add to scene //
		
		GridPane grid = new GridPane();
		grid.setAlignment(null);
		grid.setHgap(5);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		grid.add(logoTxt, 0, 0);
		grid.add(storeNameTxt, 1, 1);
		grid.add(loginTxt, 1, 2);
		
		grid.add(idField, 1, 3);
		grid.add(passwordField, 1, 4);
		
		grid.add(studentBtn, 0, 5);
		grid.add(adminBtn, 2, 5);
		
		grid.add(loginBtn, 1, 6);
		
		grid.add(errorTxt, 1, 7);
		
		return new Scene(grid, 500, 350);
	}
	
	public Scene createSelectionScene(User user, Library library) {
		VBox root = new VBox(50);
		HBox choices = new HBox(250);
		RadioButton buyer = new RadioButton("Buyer");
		RadioButton seller = new RadioButton("Seller");
		choices.getChildren().addAll(buyer, seller);
		
		ToggleGroup group = new ToggleGroup();
		buyer.setToggleGroup(group);
		seller.setToggleGroup(group);
		Text errorTxt = new Text("");
		
		Button cont = new Button("Continue");
		cont.setOnAction(e -> {
				if(seller.isSelected()) {
					user.changeScene(createSellerScene(user, library), e);
				} else if(buyer.isSelected()) {
					user.changeScene(createBuyerScene(user, library), e);
				} else {
					errorTxt.setText("Select a field");
				}
		});
		
		root.getChildren().addAll(
				new Label("Sun Devil Used Bookstore"),
				new Label("Select"),
				choices,
				errorTxt,
				cont
				);
		
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(50));
		return new Scene(root, 500, 300);
	}
	
	private Scene createSellerScene(User user, Library library) {
		
		VBox root = new VBox();
		GridPane grid = new GridPane();
		
		HBox listing = new HBox(10);
		
		
		
		// Book info
		Label bookLabel = new Label("Book Information");
		HBox title = new HBox(24);
		Label titleLabel = new Label("Title:");
		TextField titleText = new TextField();
		titleText.setPromptText("Title");
		title.getChildren().addAll(titleLabel, titleText);
		
		HBox author = new HBox(10);
		Label authLabel = new Label("Author:");
		TextField authText = new TextField();
		authText.setPromptText("Author");
		author.getChildren().addAll(authLabel, authText);
		
		HBox year = new HBox(23);
		Label yearLabel = new Label("Year:");
		TextField yearText = new TextField();
		yearText.setPromptText("Year");
		year.getChildren().addAll(yearLabel, yearText);
		
		Label priceLabel = new Label("Pricing Information");
		
		// Pricing info
		
		ChoiceBox<String> categoryBox = new ChoiceBox<>();
		categoryBox.setValue("Select a category");
		categoryBox.getItems().addAll("Natural Science", "Computer", "Math", "English Language", "Other");
		ChoiceBox<String> conditionBox = new ChoiceBox<>();
		conditionBox.setValue("Select a condition");
		conditionBox.getItems().addAll("Used like new", "Moderately used", "Heavily used");
		TextField originalPrice = new TextField();
		originalPrice.setPromptText("Original Price");
		
		// Price generating and listing
		Button listBook = new Button("List Book");
		Button reset = new Button("Reset");
		listBook.setDisable(true);
		
		TextField generatedPrice = new TextField();
		generatedPrice.setPromptText("$USD");
		generatedPrice.setEditable(false);
		
		Text errorTxt = new Text("");
		
		Button logoutBtn = new Button("Logout");
		
		Button genPrice = new Button("Generate Price");
		genPrice.setOnAction(e->{
			
			if(conditionBox.getSelectionModel().isEmpty() || categoryBox.getSelectionModel().isEmpty() || originalPrice.getText() == "" || 
					titleText.getText() == "" || authText.getText() == "" || yearText.getText() == "") {
				errorTxt.setText("Fill out all required fields");
				return;
			}
			
			final String inputPrice  = originalPrice.getText();
			double price;
			price = Double.parseDouble(inputPrice);
			
			if(conditionBox.getValue() == "Used like new") {
				price = price * 0.9;
			}
			if(conditionBox.getValue() == "Moderately used") {
				price = price * 0.7;
			}
			if(conditionBox.getValue() == "Heavily used") {
				price = price * 0.5;
			}
			if(categoryBox.getValue() == "Natural Science") {
				price = price * 0.99;
			}
			if(categoryBox.getValue() == "Computer") {
				price = price * 0.98;
			}
			if(categoryBox.getValue() == "Math") {
				price = price * 0.97;
			}
			if(categoryBox.getValue() == "English Language") {
				price = price * 0.95;
			}
			
			authText.setEditable(false);
			yearText.setEditable(false);
			titleText.setEditable(false);
			conditionBox.setDisable(true);
			categoryBox.setDisable(true);
			originalPrice.setEditable(false);
			
			generatedPrice.setText(String.format("%.2f", price));
			listBook.setDisable(false);
		});
		
		listBook.setOnAction(e->{
			
			library.createNewListing(titleText.getText(), authText.getText(), Integer.parseInt(yearText.getText()), categoryBox.getValue(), conditionBox.getValue(),
					Float.parseFloat(originalPrice.getText()), Float.parseFloat(generatedPrice.getText()), user);
			
			authText.setEditable(true);
			yearText.setEditable(true);
			titleText.setEditable(true);
			conditionBox.setDisable(false);
			categoryBox.setDisable(false);
			originalPrice.setEditable(true);
			
			authText.setText("");
			yearText.setText("");
			titleText.setText("");
			conditionBox.setValue("");
			categoryBox.setValue("");
			originalPrice.setText("");
			
			generatedPrice.setText("");
			
			
		});
		
		reset.setOnAction(e->{
			
			authText.setEditable(true);
			yearText.setEditable(true);
			titleText.setEditable(true);
			conditionBox.setDisable(false);
			categoryBox.setDisable(false);
			originalPrice.setEditable(true);
			
			authText.setText("");
			yearText.setText("");
			titleText.setText("");
			conditionBox.setValue("");
			categoryBox.setValue("");
			originalPrice.setText("");
			
			generatedPrice.setText("");
		});
		

		logoutBtn.setOnAction(e->{
			user.changeScene(createLoginScene(user, library), e);
		});
		
		root.getChildren().addAll(
				new Label("Seller"),
				grid, 
				listing,
				generatedPrice,
				errorTxt );
		
		VBox.setMargin(generatedPrice, new Insets(10));
	
		listing.getChildren().addAll(genPrice, listBook, reset);
		listing.setAlignment(Pos.CENTER);
		
		grid.add(bookLabel, 0, 0);
		grid.add(title, 0, 1);
		grid.add(author, 0, 2);
		grid.add(year, 0, 3);
		
		grid.add(priceLabel, 1, 0);
		grid.add(categoryBox, 1, 1);
		grid.add(conditionBox, 1, 2);
		grid.add(originalPrice, 1, 3);
		grid.add(logoutBtn, 1, 5);
		
		grid.setHgap(100);
		grid.setVgap(10);
		grid.setPadding(new Insets(60));
		
		root.setAlignment(Pos.CENTER);
		
		root.setPadding(new Insets(10));
		generatedPrice.setMaxWidth(200);
		
		return new Scene(root, 600, 400);
	}
	
	
	int pageNumber = 0;
	ArrayList<Listing> relevantListings;
	private Scene createBuyerScene(User user, Library library) {

		Text buyerLogoTxt = new Text("ASU");
		Text buyerStoreNameTxt = new Text("Sun Devil Used Bookstore");

		ChoiceBox<String> categoryBox = new ChoiceBox<>();
		categoryBox.setValue("Select a category");
		categoryBox.getItems().addAll("Select a category", "Natural Science", "Computer", "Math", "English Language", "Other");

		CheckBox conditionNewCheck = new CheckBox("Used like new");
		conditionNewCheck.setSelected(true);
		CheckBox conditionModerateCheck = new CheckBox("Moderately used");
		conditionModerateCheck.setSelected(true);
		CheckBox conditionHeavyCheck = new CheckBox("Heavily used");
		conditionHeavyCheck.setSelected(true);
		
		Text displayedListing1TitleAndYearTxt = new Text("Book Title" + " (Year)");
		Text displayedListing1AuthorTxt = new Text("Author");
		Text displayedListing1ConditionTxt = new Text("Condition");
		Text displayedListing1PriceTxt = new Text("Price");
		Button displayedListing1PurchaseBtn = new Button("Purchase");

		Text displayedListing2TitleAndYearTxt = new Text("Book Title" + " (Year)");
		Text displayedListing2AuthorTxt = new Text("Author");
		Text displayedListing2ConditionTxt = new Text("Condition");
		Text displayedListing2PriceTxt = new Text("Price");
		Button displayedListing2PurchaseBtn = new Button("Purchase");

		Text displayedListing3TitleAndYearTxt = new Text("Book Title" + " (Year)");
		Text displayedListing3AuthorTxt = new Text("Author");
		Text displayedListing3ConditionTxt = new Text("Condition");
		Text displayedListing3PriceTxt = new Text("Price");
		Button displayedListing3PurchaseBtn = new Button("Purchase");

		Text displayedListing4TitleAndYearTxt = new Text("Book Title" + " (Year)");
		Text displayedListing4AuthorTxt = new Text("Author");
		Text displayedListing4ConditionTxt = new Text("Condition");
		Text displayedListing4PriceTxt = new Text("Price");
		Button displayedListing4PurchaseBtn = new Button("Purchase");

		Button scrollBackBtn = new Button("<");
		Button scrollForwardBtn = new Button(">");
		Button logoutBtn = new Button("Logout");
		
		relevantListings = library.loadRelevantListings(categoryBox.getValue(), conditionNewCheck.isSelected(), conditionModerateCheck.isSelected(), conditionHeavyCheck.isSelected());
		pageNumber = 0; // helps keep track of which four listings to display
		
		updateAllListingDisplayText(relevantListings, pageNumber, 
				displayedListing1TitleAndYearTxt, displayedListing1AuthorTxt, displayedListing1ConditionTxt, displayedListing1PriceTxt, 
				displayedListing2TitleAndYearTxt, displayedListing2AuthorTxt, displayedListing2ConditionTxt, displayedListing2PriceTxt,
				displayedListing3TitleAndYearTxt, displayedListing3AuthorTxt, displayedListing3ConditionTxt, displayedListing3PriceTxt,
				displayedListing4TitleAndYearTxt, displayedListing4AuthorTxt, displayedListing4ConditionTxt, displayedListing4PriceTxt);

		GridPane grid = new GridPane();
		grid.setAlignment(null);
		grid.setHgap(5);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		grid.add(buyerLogoTxt, 0, 0);
		grid.add(buyerStoreNameTxt, 2, 0);
		grid.add(categoryBox, 0, 1);
		grid.add(conditionNewCheck, 2, 1);
		grid.add(conditionModerateCheck, 3, 1);
		grid.add(conditionHeavyCheck, 4, 1);
		grid.add(displayedListing1TitleAndYearTxt, 0, 2);
		grid.add(displayedListing1AuthorTxt, 0, 3);
		grid.add(displayedListing1ConditionTxt, 3, 2);
		grid.add(displayedListing1PriceTxt, 3, 3);
		grid.add(displayedListing1PurchaseBtn, 4, 3);
		grid.add(displayedListing2TitleAndYearTxt, 0, 4);
		grid.add(displayedListing2AuthorTxt, 0, 5);
		grid.add(displayedListing2ConditionTxt, 3, 4);
		grid.add(displayedListing2PriceTxt, 3, 5);
		grid.add(displayedListing2PurchaseBtn, 4, 5);
		grid.add(displayedListing3TitleAndYearTxt, 0, 6);
		grid.add(displayedListing3AuthorTxt, 0, 7);
		grid.add(displayedListing3ConditionTxt, 3, 6);
		grid.add(displayedListing3PriceTxt, 3, 7);
		grid.add(displayedListing3PurchaseBtn, 4, 7);
		grid.add(displayedListing4TitleAndYearTxt, 0, 8);
		grid.add(displayedListing4AuthorTxt, 0, 9);
		grid.add(displayedListing4ConditionTxt, 3, 8);
		grid.add(displayedListing4PriceTxt, 3, 9);
		grid.add(displayedListing4PurchaseBtn, 4, 9);
		grid.add(scrollBackBtn, 2, 10);
		grid.add(scrollForwardBtn, 3, 10);
		grid.add(logoutBtn, 4, 10);
		
		scrollBackBtn.setOnAction(a -> {
			if(pageNumber > 0) {
				pageNumber -= 1;
				updateAllListingDisplayText(relevantListings, pageNumber, 
						displayedListing1TitleAndYearTxt, displayedListing1AuthorTxt, displayedListing1ConditionTxt, displayedListing1PriceTxt, 
						displayedListing2TitleAndYearTxt, displayedListing2AuthorTxt, displayedListing2ConditionTxt, displayedListing2PriceTxt,
						displayedListing3TitleAndYearTxt, displayedListing3AuthorTxt, displayedListing3ConditionTxt, displayedListing3PriceTxt,
						displayedListing4TitleAndYearTxt, displayedListing4AuthorTxt, displayedListing4ConditionTxt, displayedListing4PriceTxt);
			}
        });
		
		scrollForwardBtn.setOnAction(a -> {
			if(relevantListings.size() > (pageNumber+1)*4) {
				pageNumber += 1;
				updateAllListingDisplayText(relevantListings, pageNumber, 
						displayedListing1TitleAndYearTxt, displayedListing1AuthorTxt, displayedListing1ConditionTxt, displayedListing1PriceTxt, 
						displayedListing2TitleAndYearTxt, displayedListing2AuthorTxt, displayedListing2ConditionTxt, displayedListing2PriceTxt,
						displayedListing3TitleAndYearTxt, displayedListing3AuthorTxt, displayedListing3ConditionTxt, displayedListing3PriceTxt,
						displayedListing4TitleAndYearTxt, displayedListing4AuthorTxt, displayedListing4ConditionTxt, displayedListing4PriceTxt);
			}
        });
		
		conditionNewCheck.setOnAction(a -> {
			pageNumber = 0;
			relevantListings = library.loadRelevantListings(categoryBox.getValue(), conditionNewCheck.isSelected(), conditionModerateCheck.isSelected(), conditionHeavyCheck.isSelected());
			updateAllListingDisplayText(relevantListings, pageNumber, 
					displayedListing1TitleAndYearTxt, displayedListing1AuthorTxt, displayedListing1ConditionTxt, displayedListing1PriceTxt, 
					displayedListing2TitleAndYearTxt, displayedListing2AuthorTxt, displayedListing2ConditionTxt, displayedListing2PriceTxt,
					displayedListing3TitleAndYearTxt, displayedListing3AuthorTxt, displayedListing3ConditionTxt, displayedListing3PriceTxt,
					displayedListing4TitleAndYearTxt, displayedListing4AuthorTxt, displayedListing4ConditionTxt, displayedListing4PriceTxt);
        });
		
		conditionModerateCheck.setOnAction(a -> {
			pageNumber = 0;
			relevantListings = library.loadRelevantListings(categoryBox.getValue(), conditionNewCheck.isSelected(), conditionModerateCheck.isSelected(), conditionHeavyCheck.isSelected());
			updateAllListingDisplayText(relevantListings, pageNumber, 
					displayedListing1TitleAndYearTxt, displayedListing1AuthorTxt, displayedListing1ConditionTxt, displayedListing1PriceTxt, 
					displayedListing2TitleAndYearTxt, displayedListing2AuthorTxt, displayedListing2ConditionTxt, displayedListing2PriceTxt,
					displayedListing3TitleAndYearTxt, displayedListing3AuthorTxt, displayedListing3ConditionTxt, displayedListing3PriceTxt,
					displayedListing4TitleAndYearTxt, displayedListing4AuthorTxt, displayedListing4ConditionTxt, displayedListing4PriceTxt);
        });
		
		conditionHeavyCheck.setOnAction(a -> {
			pageNumber = 0;
			relevantListings = library.loadRelevantListings(categoryBox.getValue(), conditionNewCheck.isSelected(), conditionModerateCheck.isSelected(), conditionHeavyCheck.isSelected());
			updateAllListingDisplayText(relevantListings, pageNumber, 
					displayedListing1TitleAndYearTxt, displayedListing1AuthorTxt, displayedListing1ConditionTxt, displayedListing1PriceTxt, 
					displayedListing2TitleAndYearTxt, displayedListing2AuthorTxt, displayedListing2ConditionTxt, displayedListing2PriceTxt,
					displayedListing3TitleAndYearTxt, displayedListing3AuthorTxt, displayedListing3ConditionTxt, displayedListing3PriceTxt,
					displayedListing4TitleAndYearTxt, displayedListing4AuthorTxt, displayedListing4ConditionTxt, displayedListing4PriceTxt);
        });
		
		categoryBox.setOnAction(a -> {
			pageNumber = 0;
			relevantListings = library.loadRelevantListings(categoryBox.getValue(), conditionNewCheck.isSelected(), conditionModerateCheck.isSelected(), conditionHeavyCheck.isSelected());
			updateAllListingDisplayText(relevantListings, pageNumber, 
					displayedListing1TitleAndYearTxt, displayedListing1AuthorTxt, displayedListing1ConditionTxt, displayedListing1PriceTxt, 
					displayedListing2TitleAndYearTxt, displayedListing2AuthorTxt, displayedListing2ConditionTxt, displayedListing2PriceTxt,
					displayedListing3TitleAndYearTxt, displayedListing3AuthorTxt, displayedListing3ConditionTxt, displayedListing3PriceTxt,
					displayedListing4TitleAndYearTxt, displayedListing4AuthorTxt, displayedListing4ConditionTxt, displayedListing4PriceTxt);
        });
		
		displayedListing1PurchaseBtn.setOnAction(a -> {
			//pageNumber = 0;
			if(!displayedListing1TitleAndYearTxt.getText().equals("")) {
				library.purchaseListing(relevantListings.get(pageNumber*4 + 0));
				relevantListings = library.loadRelevantListings(categoryBox.getValue(), conditionNewCheck.isSelected(), conditionModerateCheck.isSelected(), conditionHeavyCheck.isSelected());
				if(displayedListing2TitleAndYearTxt.getText().equals("")) {
					pageNumber -= 1;
				}
				
				updateAllListingDisplayText(relevantListings, pageNumber, 
						displayedListing1TitleAndYearTxt, displayedListing1AuthorTxt, displayedListing1ConditionTxt, displayedListing1PriceTxt, 
						displayedListing2TitleAndYearTxt, displayedListing2AuthorTxt, displayedListing2ConditionTxt, displayedListing2PriceTxt,
						displayedListing3TitleAndYearTxt, displayedListing3AuthorTxt, displayedListing3ConditionTxt, displayedListing3PriceTxt,
						displayedListing4TitleAndYearTxt, displayedListing4AuthorTxt, displayedListing4ConditionTxt, displayedListing4PriceTxt);
			}
		});
		
		displayedListing2PurchaseBtn.setOnAction(a -> {
			//pageNumber = 0;
			if(!displayedListing2TitleAndYearTxt.getText().equals("")) {
				library.purchaseListing(relevantListings.get(pageNumber*4 + 1));
				relevantListings = library.loadRelevantListings(categoryBox.getValue(), conditionNewCheck.isSelected(), conditionModerateCheck.isSelected(), conditionHeavyCheck.isSelected());
				updateAllListingDisplayText(relevantListings, pageNumber, 
						displayedListing1TitleAndYearTxt, displayedListing1AuthorTxt, displayedListing1ConditionTxt, displayedListing1PriceTxt, 
						displayedListing2TitleAndYearTxt, displayedListing2AuthorTxt, displayedListing2ConditionTxt, displayedListing2PriceTxt,
						displayedListing3TitleAndYearTxt, displayedListing3AuthorTxt, displayedListing3ConditionTxt, displayedListing3PriceTxt,
						displayedListing4TitleAndYearTxt, displayedListing4AuthorTxt, displayedListing4ConditionTxt, displayedListing4PriceTxt);
			}
		});
		
		displayedListing3PurchaseBtn.setOnAction(a -> {
			//pageNumber = 0;
			if(!displayedListing3TitleAndYearTxt.getText().equals("")) {
				library.purchaseListing(relevantListings.get(pageNumber*4 + 2));
				relevantListings = library.loadRelevantListings(categoryBox.getValue(), conditionNewCheck.isSelected(), conditionModerateCheck.isSelected(), conditionHeavyCheck.isSelected());
				updateAllListingDisplayText(relevantListings, pageNumber, 
						displayedListing1TitleAndYearTxt, displayedListing1AuthorTxt, displayedListing1ConditionTxt, displayedListing1PriceTxt, 
						displayedListing2TitleAndYearTxt, displayedListing2AuthorTxt, displayedListing2ConditionTxt, displayedListing2PriceTxt,
						displayedListing3TitleAndYearTxt, displayedListing3AuthorTxt, displayedListing3ConditionTxt, displayedListing3PriceTxt,
						displayedListing4TitleAndYearTxt, displayedListing4AuthorTxt, displayedListing4ConditionTxt, displayedListing4PriceTxt);
			}
		});
		
		displayedListing4PurchaseBtn.setOnAction(a -> {
			//pageNumber = 0;
			if(!displayedListing4TitleAndYearTxt.getText().equals("")) {
				library.purchaseListing(relevantListings.get(pageNumber*4 + 3));
				relevantListings = library.loadRelevantListings(categoryBox.getValue(), conditionNewCheck.isSelected(), conditionModerateCheck.isSelected(), conditionHeavyCheck.isSelected());
				updateAllListingDisplayText(relevantListings, pageNumber, 
						displayedListing1TitleAndYearTxt, displayedListing1AuthorTxt, displayedListing1ConditionTxt, displayedListing1PriceTxt, 
						displayedListing2TitleAndYearTxt, displayedListing2AuthorTxt, displayedListing2ConditionTxt, displayedListing2PriceTxt,
						displayedListing3TitleAndYearTxt, displayedListing3AuthorTxt, displayedListing3ConditionTxt, displayedListing3PriceTxt,
						displayedListing4TitleAndYearTxt, displayedListing4AuthorTxt, displayedListing4ConditionTxt, displayedListing4PriceTxt);
			}
		});
		
		logoutBtn.setOnAction(e->{
			user.changeScene(createLoginScene(user, library), e);
		});
		
		return new Scene(grid, 600, 400);
	}
	
	public void updateAllListingDisplayText(ArrayList<Listing> relevantListings, int pageNumber, 
			Text displayedListing1TitleAndYearTxt, Text displayedListing1AuthorTxt, Text displayedListing1ConditionTxt, Text displayedListing1PriceTxt,
			Text displayedListing2TitleAndYearTxt, Text displayedListing2AuthorTxt, Text displayedListing2ConditionTxt, Text displayedListing2PriceTxt,
			Text displayedListing3TitleAndYearTxt, Text displayedListing3AuthorTxt, Text displayedListing3ConditionTxt, Text displayedListing3PriceTxt,
			Text displayedListing4TitleAndYearTxt, Text displayedListing4AuthorTxt, Text displayedListing4ConditionTxt, Text displayedListing4PriceTxt) {
		updateListingDisplayText(relevantListings, pageNumber, 0, displayedListing1TitleAndYearTxt, displayedListing1AuthorTxt, displayedListing1ConditionTxt, displayedListing1PriceTxt);
		updateListingDisplayText(relevantListings, pageNumber, 1, displayedListing2TitleAndYearTxt, displayedListing2AuthorTxt, displayedListing2ConditionTxt, displayedListing2PriceTxt);
		updateListingDisplayText(relevantListings, pageNumber, 2, displayedListing3TitleAndYearTxt, displayedListing3AuthorTxt, displayedListing3ConditionTxt, displayedListing3PriceTxt);
		updateListingDisplayText(relevantListings, pageNumber, 3, displayedListing4TitleAndYearTxt, displayedListing4AuthorTxt, displayedListing4ConditionTxt, displayedListing4PriceTxt);
	}
	
	public void updateListingDisplayText(ArrayList<Listing> relevantListings, int pageNumber, int position, 
			Text titleAndYearTxt, Text authorTxt, Text conditionTxt, Text priceTxt) {
		int index = pageNumber*4 + position;
	
		if(index <= relevantListings.size() - 1 && relevantListings.size() != 0) {
			Listing displayListing = relevantListings.get(index);
			titleAndYearTxt.setText(displayListing.getBook().getTitle() + " (" + displayListing.getBook().getYear() + ")");
			authorTxt.setText(displayListing.getBook().getAuthor());
			conditionTxt.setText(displayListing.getCondition());
			priceTxt.setText("$" + String.format("%,.2f", displayListing.getGeneratedPrice()));
		} else {
			titleAndYearTxt.setText("");
			authorTxt.setText("");
			conditionTxt.setText("");
			priceTxt.setText("");
		}
	}
	
	private Scene createAdminScene(User user, Library library) {
		
		GridPane root = new GridPane();
		root.setHgap(10);
		root.setVgap(40);
		root.setPadding(new Insets(10));
		
		Text asu = new Text("ASU");
		Text adminView = new Text("Sun Devil Used Bookstore Admin");
		Text dashSummary = new Text("Dashboard Summary");
		Text dashListings = new Text("Active Listings: " + library.getNumberActiveListings());
		Text dashUsers = new Text("Total Users: " + library.getNumberUsers());
		Text dashBooks = new Text("Total Sales: " + library.getNumberPurchasedListings());
		
		dashSummary.setFont(new Font(18));
		
		Button mngListings = new Button("Manage Listings");
		Button mngUsers = new Button("Manager Users");
		mngUsers.setMinHeight(10);
		mngUsers.setMinWidth(10);
		
		Button logOut = new Button("Logout");
		
		mngListings.setOnAction(e->{
			user.changeScene(createManageListingsScene(user, library), e);
		});
		
		mngUsers.setOnAction(e->{
			
		});

		logOut.setOnAction(e->{
			user.changeScene(createLoginScene(user, library), e);
		});
		
		root.add(asu, 0, 0);
		root.add(adminView, 3, 0);
		root.add(dashSummary, 5, 1);
		root.add(dashListings, 5, 2);
		root.add(dashUsers, 5, 3);
		root.add(dashBooks, 5, 4);
		root.add(mngListings, 1, 2);
		root.add(mngUsers, 1, 4);
		root.add(logOut, 6, 6);
	
		
		
		return new Scene(root, 600, 400);
	}
	
	private Scene createManageListingsScene(User user, Library library) {

		Text logoTxt = new Text("ASU");
		Text storeNameTxt = new Text("Sun Devil Used Bookstore");

		ChoiceBox<String> categoryBox = new ChoiceBox<>();
		categoryBox.setValue("Select a category");
		categoryBox.getItems().addAll("Select a category", "Natural Science", "Computer", "Math", "English Language", "Other");

		CheckBox conditionNewCheck = new CheckBox("Used like new");
		conditionNewCheck.setSelected(true);
		CheckBox conditionModerateCheck = new CheckBox("Moderately used");
		conditionModerateCheck.setSelected(true);
		CheckBox conditionHeavyCheck = new CheckBox("Heavily used");
		conditionHeavyCheck.setSelected(true);

		Text displayedListing1TitleAndYearTxt = new Text("Book Title" + " (Year)");
		Text displayedListing1AuthorTxt = new Text("Author");
		Text displayedListing1ConditionTxt = new Text("Condition");
		Text displayedListing1PriceTxt = new Text("Price");
		Button displayedListing1RemoveBtn = new Button("Remove");

		Text displayedListing2TitleAndYearTxt = new Text("Book Title" + " (Year)");
		Text displayedListing2AuthorTxt = new Text("Author");
		Text displayedListing2ConditionTxt = new Text("Condition");
		Text displayedListing2PriceTxt = new Text("Price");
		Button displayedListing2RemoveBtn = new Button("Remove");

		Text displayedListing3TitleAndYearTxt = new Text("Book Title" + " (Year)");
		Text displayedListing3AuthorTxt = new Text("Author");
		Text displayedListing3ConditionTxt = new Text("Condition");
		Text displayedListing3PriceTxt = new Text("Price");
		Button displayedListing3RemoveBtn = new Button("Remove");

		Text displayedListing4TitleAndYearTxt = new Text("Book Title" + " (Year)");
		Text displayedListing4AuthorTxt = new Text("Author");
		Text displayedListing4ConditionTxt = new Text("Condition");
		Text displayedListing4PriceTxt = new Text("Price");
		Button displayedListing4RemoveBtn = new Button("Remove");

		Button scrollBackBtn = new Button("<");
		Button scrollForwardBtn = new Button(">");
		Button backPageBtn = new Button("Back");
		Button logoutBtn = new Button("Logout");

		relevantListings = library.loadRelevantListings(categoryBox.getValue(), conditionNewCheck.isSelected(), conditionModerateCheck.isSelected(), conditionHeavyCheck.isSelected());
		pageNumber = 0; // helps keep track of which four listings to display

		updateAllListingDisplayText(relevantListings, pageNumber, 
				displayedListing1TitleAndYearTxt, displayedListing1AuthorTxt, displayedListing1ConditionTxt, displayedListing1PriceTxt, 
				displayedListing2TitleAndYearTxt, displayedListing2AuthorTxt, displayedListing2ConditionTxt, displayedListing2PriceTxt,
				displayedListing3TitleAndYearTxt, displayedListing3AuthorTxt, displayedListing3ConditionTxt, displayedListing3PriceTxt,
				displayedListing4TitleAndYearTxt, displayedListing4AuthorTxt, displayedListing4ConditionTxt, displayedListing4PriceTxt);

		GridPane grid = new GridPane();
		grid.setAlignment(null);
		grid.setHgap(5);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		grid.add(logoTxt, 0, 0);
		grid.add(storeNameTxt, 2, 0);
		grid.add(categoryBox, 0, 1);
		grid.add(conditionNewCheck, 2, 1);
		grid.add(conditionModerateCheck, 3, 1);
		grid.add(conditionHeavyCheck, 4, 1);
		grid.add(displayedListing1TitleAndYearTxt, 0, 2);
		grid.add(displayedListing1AuthorTxt, 0, 3);
		grid.add(displayedListing1ConditionTxt, 3, 2);
		grid.add(displayedListing1PriceTxt, 3, 3);
		grid.add(displayedListing1RemoveBtn, 4, 3);
		grid.add(displayedListing2TitleAndYearTxt, 0, 4);
		grid.add(displayedListing2AuthorTxt, 0, 5);
		grid.add(displayedListing2ConditionTxt, 3, 4);
		grid.add(displayedListing2PriceTxt, 3, 5);
		grid.add(displayedListing2RemoveBtn, 4, 5);
		grid.add(displayedListing3TitleAndYearTxt, 0, 6);
		grid.add(displayedListing3AuthorTxt, 0, 7);
		grid.add(displayedListing3ConditionTxt, 3, 6);
		grid.add(displayedListing3PriceTxt, 3, 7);
		grid.add(displayedListing3RemoveBtn, 4, 7);
		grid.add(displayedListing4TitleAndYearTxt, 0, 8);
		grid.add(displayedListing4AuthorTxt, 0, 9);
		grid.add(displayedListing4ConditionTxt, 3, 8);
		grid.add(displayedListing4PriceTxt, 3, 9);
		grid.add(displayedListing4RemoveBtn, 4, 9);
		grid.add(scrollBackBtn, 2, 10);
		grid.add(scrollForwardBtn, 3, 10);
		grid.add(logoutBtn, 4, 10);
		grid.add(backPageBtn, 0, 10);

		scrollBackBtn.setOnAction(a -> {
			if(pageNumber > 0) {
				pageNumber -= 1;
				updateAllListingDisplayText(relevantListings, pageNumber, 
						displayedListing1TitleAndYearTxt, displayedListing1AuthorTxt, displayedListing1ConditionTxt, displayedListing1PriceTxt, 
						displayedListing2TitleAndYearTxt, displayedListing2AuthorTxt, displayedListing2ConditionTxt, displayedListing2PriceTxt,
						displayedListing3TitleAndYearTxt, displayedListing3AuthorTxt, displayedListing3ConditionTxt, displayedListing3PriceTxt,
						displayedListing4TitleAndYearTxt, displayedListing4AuthorTxt, displayedListing4ConditionTxt, displayedListing4PriceTxt);
			}
        });

		scrollForwardBtn.setOnAction(a -> {
			if(relevantListings.size() > (pageNumber+1)*4) {
				pageNumber += 1;
				updateAllListingDisplayText(relevantListings, pageNumber, 
						displayedListing1TitleAndYearTxt, displayedListing1AuthorTxt, displayedListing1ConditionTxt, displayedListing1PriceTxt, 
						displayedListing2TitleAndYearTxt, displayedListing2AuthorTxt, displayedListing2ConditionTxt, displayedListing2PriceTxt,
						displayedListing3TitleAndYearTxt, displayedListing3AuthorTxt, displayedListing3ConditionTxt, displayedListing3PriceTxt,
						displayedListing4TitleAndYearTxt, displayedListing4AuthorTxt, displayedListing4ConditionTxt, displayedListing4PriceTxt);
			}
        });

		conditionNewCheck.setOnAction(a -> {
			pageNumber = 0;
			relevantListings = library.loadRelevantListings(categoryBox.getValue(), conditionNewCheck.isSelected(), conditionModerateCheck.isSelected(), conditionHeavyCheck.isSelected());
			updateAllListingDisplayText(relevantListings, pageNumber, 
					displayedListing1TitleAndYearTxt, displayedListing1AuthorTxt, displayedListing1ConditionTxt, displayedListing1PriceTxt, 
					displayedListing2TitleAndYearTxt, displayedListing2AuthorTxt, displayedListing2ConditionTxt, displayedListing2PriceTxt,
					displayedListing3TitleAndYearTxt, displayedListing3AuthorTxt, displayedListing3ConditionTxt, displayedListing3PriceTxt,
					displayedListing4TitleAndYearTxt, displayedListing4AuthorTxt, displayedListing4ConditionTxt, displayedListing4PriceTxt);
        });

		conditionModerateCheck.setOnAction(a -> {
			pageNumber = 0;
			relevantListings = library.loadRelevantListings(categoryBox.getValue(), conditionNewCheck.isSelected(), conditionModerateCheck.isSelected(), conditionHeavyCheck.isSelected());
			updateAllListingDisplayText(relevantListings, pageNumber, 
					displayedListing1TitleAndYearTxt, displayedListing1AuthorTxt, displayedListing1ConditionTxt, displayedListing1PriceTxt, 
					displayedListing2TitleAndYearTxt, displayedListing2AuthorTxt, displayedListing2ConditionTxt, displayedListing2PriceTxt,
					displayedListing3TitleAndYearTxt, displayedListing3AuthorTxt, displayedListing3ConditionTxt, displayedListing3PriceTxt,
					displayedListing4TitleAndYearTxt, displayedListing4AuthorTxt, displayedListing4ConditionTxt, displayedListing4PriceTxt);
        });

		conditionHeavyCheck.setOnAction(a -> {
			pageNumber = 0;
			relevantListings = library.loadRelevantListings(categoryBox.getValue(), conditionNewCheck.isSelected(), conditionModerateCheck.isSelected(), conditionHeavyCheck.isSelected());
			updateAllListingDisplayText(relevantListings, pageNumber, 
					displayedListing1TitleAndYearTxt, displayedListing1AuthorTxt, displayedListing1ConditionTxt, displayedListing1PriceTxt, 
					displayedListing2TitleAndYearTxt, displayedListing2AuthorTxt, displayedListing2ConditionTxt, displayedListing2PriceTxt,
					displayedListing3TitleAndYearTxt, displayedListing3AuthorTxt, displayedListing3ConditionTxt, displayedListing3PriceTxt,
					displayedListing4TitleAndYearTxt, displayedListing4AuthorTxt, displayedListing4ConditionTxt, displayedListing4PriceTxt);
        });

		categoryBox.setOnAction(a -> {
			pageNumber = 0;
			relevantListings = library.loadRelevantListings(categoryBox.getValue(), conditionNewCheck.isSelected(), conditionModerateCheck.isSelected(), conditionHeavyCheck.isSelected());
			updateAllListingDisplayText(relevantListings, pageNumber, 
					displayedListing1TitleAndYearTxt, displayedListing1AuthorTxt, displayedListing1ConditionTxt, displayedListing1PriceTxt, 
					displayedListing2TitleAndYearTxt, displayedListing2AuthorTxt, displayedListing2ConditionTxt, displayedListing2PriceTxt,
					displayedListing3TitleAndYearTxt, displayedListing3AuthorTxt, displayedListing3ConditionTxt, displayedListing3PriceTxt,
					displayedListing4TitleAndYearTxt, displayedListing4AuthorTxt, displayedListing4ConditionTxt, displayedListing4PriceTxt);
        });

		displayedListing1RemoveBtn.setOnAction(a -> {
			//pageNumber = 0;
			if(!displayedListing1TitleAndYearTxt.getText().equals("")) {
				library.removeListing(relevantListings.get(pageNumber*4 + 0));
				relevantListings = library.loadRelevantListings(categoryBox.getValue(), conditionNewCheck.isSelected(), conditionModerateCheck.isSelected(), conditionHeavyCheck.isSelected());
				if(displayedListing2TitleAndYearTxt.getText().equals("")) {
					pageNumber -= 1;
				}

				updateAllListingDisplayText(relevantListings, pageNumber, 
						displayedListing1TitleAndYearTxt, displayedListing1AuthorTxt, displayedListing1ConditionTxt, displayedListing1PriceTxt, 
						displayedListing2TitleAndYearTxt, displayedListing2AuthorTxt, displayedListing2ConditionTxt, displayedListing2PriceTxt,
						displayedListing3TitleAndYearTxt, displayedListing3AuthorTxt, displayedListing3ConditionTxt, displayedListing3PriceTxt,
						displayedListing4TitleAndYearTxt, displayedListing4AuthorTxt, displayedListing4ConditionTxt, displayedListing4PriceTxt);
			}
		});

		displayedListing2RemoveBtn.setOnAction(a -> {
			//pageNumber = 0;
			if(!displayedListing2TitleAndYearTxt.getText().equals("")) {
				library.removeListing(relevantListings.get(pageNumber*4 + 1));
				relevantListings = library.loadRelevantListings(categoryBox.getValue(), conditionNewCheck.isSelected(), conditionModerateCheck.isSelected(), conditionHeavyCheck.isSelected());
				updateAllListingDisplayText(relevantListings, pageNumber, 
						displayedListing1TitleAndYearTxt, displayedListing1AuthorTxt, displayedListing1ConditionTxt, displayedListing1PriceTxt, 
						displayedListing2TitleAndYearTxt, displayedListing2AuthorTxt, displayedListing2ConditionTxt, displayedListing2PriceTxt,
						displayedListing3TitleAndYearTxt, displayedListing3AuthorTxt, displayedListing3ConditionTxt, displayedListing3PriceTxt,
						displayedListing4TitleAndYearTxt, displayedListing4AuthorTxt, displayedListing4ConditionTxt, displayedListing4PriceTxt);
			}
		});

		displayedListing3RemoveBtn.setOnAction(a -> {
			//pageNumber = 0;
			if(!displayedListing3TitleAndYearTxt.getText().equals("")) {
				library.removeListing(relevantListings.get(pageNumber*4 + 2));
				relevantListings = library.loadRelevantListings(categoryBox.getValue(), conditionNewCheck.isSelected(), conditionModerateCheck.isSelected(), conditionHeavyCheck.isSelected());
				updateAllListingDisplayText(relevantListings, pageNumber, 
						displayedListing1TitleAndYearTxt, displayedListing1AuthorTxt, displayedListing1ConditionTxt, displayedListing1PriceTxt, 
						displayedListing2TitleAndYearTxt, displayedListing2AuthorTxt, displayedListing2ConditionTxt, displayedListing2PriceTxt,
						displayedListing3TitleAndYearTxt, displayedListing3AuthorTxt, displayedListing3ConditionTxt, displayedListing3PriceTxt,
						displayedListing4TitleAndYearTxt, displayedListing4AuthorTxt, displayedListing4ConditionTxt, displayedListing4PriceTxt);
			}
		});

		displayedListing4RemoveBtn.setOnAction(a -> {
			//pageNumber = 0;
			if(!displayedListing4TitleAndYearTxt.getText().equals("")) {
				library.removeListing(relevantListings.get(pageNumber*4 + 3));
				relevantListings = library.loadRelevantListings(categoryBox.getValue(), conditionNewCheck.isSelected(), conditionModerateCheck.isSelected(), conditionHeavyCheck.isSelected());
				updateAllListingDisplayText(relevantListings, pageNumber, 
						displayedListing1TitleAndYearTxt, displayedListing1AuthorTxt, displayedListing1ConditionTxt, displayedListing1PriceTxt, 
						displayedListing2TitleAndYearTxt, displayedListing2AuthorTxt, displayedListing2ConditionTxt, displayedListing2PriceTxt,
						displayedListing3TitleAndYearTxt, displayedListing3AuthorTxt, displayedListing3ConditionTxt, displayedListing3PriceTxt,
						displayedListing4TitleAndYearTxt, displayedListing4AuthorTxt, displayedListing4ConditionTxt, displayedListing4PriceTxt);
			}
		});

		logoutBtn.setOnAction(e->{
			user.changeScene(createLoginScene(user, library), e);
		});

		backPageBtn.setOnAction(e->{
			user.changeScene(createAdminScene(user, library), e);
		});

		return new Scene(grid, 600, 400);

	}

}