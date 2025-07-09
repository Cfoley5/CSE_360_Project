package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FileSys {
	
	public static FileChooser fileChoose(String title) {
		FileChooser chooser = new FileChooser();
		chooser.setTitle(title);
		chooser.getExtensionFilters().add(
				new FileChooser.ExtensionFilter("Text Files", "*.txt")
				);
		return chooser;
	}
	
	public static void fileWrite(FileChooser chooser, Stage stage, String string) {
		File file = chooser.showSaveDialog(stage);
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))){
			bw.write(string);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
