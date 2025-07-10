package application;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class User {
	
	private String AsuriteID;
	private String password;
	private boolean isAdmin;
	
	public User(String AsuriteID, String password, boolean isAdmin) {
		this.AsuriteID = AsuriteID;
		this.password = password;
		this.isAdmin = isAdmin;
	}
	
	public void toggleIsAdmin() {
		isAdmin = !isAdmin;
	}
	
	public void changeScene(Scene scene, ActionEvent event) {
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.setScene(scene);
	}
	
	public String getAsuriteID() {
		return AsuriteID;
	}
	
	public String getPassword() {
		return password;
	}
	
	public boolean getIsAdmin() {
		return isAdmin;
	}
	
	
}
