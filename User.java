package application;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class User {
	
	public User(String AsuriteID, String password) {
	}
	
	public void changeScene(Scene scene, ActionEvent event) {
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.setScene(scene);
	}
	
	public void logOut() {
		System.out.println("Hi");
	}
	
	
}
