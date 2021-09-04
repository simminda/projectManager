package poised;

public class Assignee {
	// Attributes
	String role;
	String name;
	String tel;
	String email;
	String address;
			
	// Methods
	public Assignee(String role, String name, String tel, String email, String address) {
		this.role = role;
		this.name = name;
		this.tel = tel;
		this.email = email;
		this.address = address;
		}
		
	public String getRole() {
		return role;
	}
		   
	public String toString() {
		String output = role;
		output += "\nName\t: " + name;
		output += "\nTel No\t: " + tel;
		output += "\nEmail\t: " + email;
		output += "\nAddress\t: " + address;
			   
		return output;
	}

}
