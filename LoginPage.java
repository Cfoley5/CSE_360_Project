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

public class LoginPage extends Application {
	
	public static void main( String[] args )
	{
		 launch(args);
	}
	
	@Override
    public void start(Stage primaryStage) {
		
		//Label studentLbl = new Label("Student");
		//Label adminLbl = new Label("Admin");
		
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
		
		
		// MEAL BUTTON ACTIONS //
		
		loginBtn.setOnAction(a -> {
			if(idField.getText().equalsIgnoreCase("") || passwordField.getText().equalsIgnoreCase("")) {
				errorTxt.setText("Incorrect ASU ID or password");
			}
			else if(studentAdmin.getSelectedToggle() == null) {
				errorTxt.setText("Please select Student or Admin role to continue");
			}
			else {
				errorTxt.setText("Tada! You logged in, this concludes the prototype");
				primaryStage.setScene(createSellerScene());
			}
        });
	
		// add to scene //
		
		GridPane grid = new GridPane();
		grid.setAlignment(null);
		grid.setHgap(5);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Scene scene = new Scene(grid, 500, 350);
		primaryStage.setScene(scene);
		
		grid.add(logoTxt, 0, 0);
		grid.add(storeNameTxt, 1, 1);
		grid.add(loginTxt, 1, 2);
		
		grid.add(idField, 1, 3);
		grid.add(passwordField, 1, 4);
		
		grid.add(studentBtn, 0, 5);
		grid.add(adminBtn, 2, 5);
		
		grid.add(loginBtn, 1, 6);
		
		grid.add(errorTxt, 1, 7);
		
        primaryStage.setTitle("Login Page");
        primaryStage.show();
        

    }
	
	private Scene createSellerScene() {
		
		VBox root = new VBox();
		GridPane grid = new GridPane();
		
		HBox listing = new HBox();
		
		TextField generatedPrice = new TextField("Price");
		
		root.getChildren().addAll(
				new Label("Seller"),
				grid, 
				listing,
				generatedPrice );
		
		//Grid items
		Label bookLabel = new Label("Book Information");
		HBox title = new HBox(24);
		title.getChildren().addAll(
				new Label("Title:"),
				new TextField()
				);
		HBox author = new HBox(10);
		author.getChildren().addAll(
				new Label("Author:"),
				new TextField()
				);
		HBox year = new HBox(23);
		year.getChildren().addAll(
				new Label("Year:"),
				new TextField()
				);
		
		Label priceLabel = new Label("Pricing Information");
		
		ChoiceBox<String> categoryBox = new ChoiceBox<>();
		categoryBox.getItems().addAll("Natural Science", "Computer", "Math", "English Language", "Other");
		ChoiceBox<String> conditionBox = new ChoiceBox<>();
		conditionBox.getItems().addAll("Used like new", "Moderately used", "Heavily used");
		TextField originalPrice = new TextField("Original Price");
		
		
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
	

}
