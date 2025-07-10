package application;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;



public class FileSys {
	

	public static void fileWrite(String path, String string) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))){
			bw.write(string);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void fileAppend(String path, String string) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))){
			bw.write(string);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	
}
