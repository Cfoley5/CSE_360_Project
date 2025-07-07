package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LoginPage extends Application {
	
	public static void main( String[] args )
	{
		 launch(args);
	}
	
	@Override
    public void start(Stage primaryStage) {
		
		User user = new User("Name", "Pass");
		
		Library library = new Library();
		
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
			}
			else if(studentAdmin.getSelectedToggle() == adminBtn){
				user.changeScene(createAdminScene(user, library), a);
			}
			else if(studentAdmin.getSelectedToggle() == studentBtn) {
				user.changeScene(createSelectionScene(user, library), a);
			}
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
			
			library.createNewListing(titleText.getText(), authText.getText(), Integer.parseInt(yearText.getText()), categoryBox.getValue(), conditionBox.getValue(),
					Float.parseFloat(originalPrice.getText()), Float.parseFloat(generatedPrice.getText()), user);
			
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
		
		grid.setHgap(100);
		grid.setVgap(10);
		grid.setPadding(new Insets(60));
		
		root.setAlignment(Pos.CENTER);
		
		root.setPadding(new Insets(10));
		generatedPrice.setMaxWidth(200);
		
		return new Scene(root, 600, 400);
	}
	
	
	private Scene createBuyerScene(User user, Library library) {

		Text buyerLogoTxt = new Text("ASU");
		Text buyerStoreNameTxt = new Text("Sun Devil Used Bookstore");

		ChoiceBox<String> categoryBox = new ChoiceBox<>();
		categoryBox.setValue("Select a category");
		categoryBox.getItems().addAll("Natural Science", "Computer", "Math", "English Language", "Other");

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

		return new Scene(grid, 600, 400);
	}
	
	private Scene createAdminScene(User user, Library library) {
		
		GridPane root = new GridPane();
		root.setHgap(10);
		root.setVgap(40);
		root.setPadding(new Insets(10));
		
		Text asu = new Text("ASU");
		Text adminView = new Text("Sun Devil Used Bookstore Admin");
		Text dashSummary = new Text("Dashboard Summary");
		Text dashListings = new Text("Active Listings:");
		Text dashUsers = new Text("Total Users:");
		Text dashBooks = new Text("Total Sales:");
		
		dashSummary.setFont(new Font(18));
		
		Button mngListings = new Button("Manage Listings");
		Button mngUsers = new Button("Manager Users");
		mngUsers.setMinHeight(10);
		mngUsers.setMinWidth(10);
		
		Button logOut = new Button("Logout");
		
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
	

}
