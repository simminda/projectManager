package poised;

public class Project {
	// Attributes
	Integer projectNumber;
	String projectName;
	String buildingType;
	String projectAddress;
	String erfNumber;
	Double projectFee;
	Double projectBalance;
	String deadline;
	Persons architect;
	Persons contractor;
	Persons customer;
	
    // Methods
    public Project(Integer projectNumber, String projectName, String buildingType, String projectAddress,
    				String erfNumber, Double projectFee, Double projectBalance, String deadline) {
    	this.projectNumber = projectNumber;
    	this.projectName = projectName;
    	this.buildingType = buildingType;
    	this.projectAddress = projectAddress;
    	this.erfNumber = erfNumber;
    	this.projectFee = projectFee;
    	this.projectBalance = projectBalance;
    	this.deadline = deadline;
    }
    
    public String toString() {
		String output = "Project";
		output += "\nProject Number\t: " + projectNumber;
		output += "\nProject Name\t: " + projectName;
		output += "\nBuilding Type\t: " + buildingType;
		output += "\nProject Address\t: " + projectAddress;
		output += "\nerf Number\t: " + erfNumber;
		output += "\nProject Fee\t: " + projectFee;
		output += "\nProject Balance\t: " + projectBalance;
		output += "\nDeadline\t: " + deadline;
			   
		return output;
	}
    
    // set the due date for project
    public void setDeadline(String newDeadline) {
    	deadline = newDeadline;
    }
    
    // set the outstanding balance for project
    public void setBalance(Double newBalance) {
    	projectBalance = newBalance;
    }
    
    public String oneLine(Project currProject) {
		String output = projectNumber + ", " + projectName + ", " + buildingType + ", " + projectAddress + ", " + erfNumber + ", " + projectFee + ", " + projectBalance + ", " + deadline;
			   
		return output;
	}

}
