package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.scene.layout.GridPane;

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

}
