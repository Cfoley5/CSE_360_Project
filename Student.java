package application;

public class Student extends User {
	
	private boolean IsSeller;
	
	public Student(String AsuriteID, String password, boolean IsSeller) {
		super(AsuriteID, password);
		this.IsSeller = IsSeller;
	}

}
