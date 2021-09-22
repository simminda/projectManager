package poised;

public class CompleteProject {
	// Attributes
	Integer projectNumber;
	String projectName;
	String buildingType;
	String projectAddress;
	String erfNumber;
	Double projectFee;
	Double projectBalance;
	String completionDate;
	Double invoiceNo;
	Assignee architect;
	Assignee contractor;
	Assignee customer;
	
    // Methods
    public CompleteProject(Integer projectNumber, String projectName, String buildingType, String projectAddress,
    				String erfNumber, Double projectFee, Double projectBalance, String completionDate, Double invoiceNo) {
    	this.projectNumber = projectNumber;
    	this.projectName = projectName;
    	this.buildingType = buildingType;
    	this.projectAddress = projectAddress;
    	this.erfNumber = erfNumber;
    	this.projectFee = projectFee;
    	this.projectBalance = projectBalance;
    	this.completionDate = completionDate;
    	this.invoiceNo = invoiceNo;
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
		output += "\nCompletion Date\t: " + completionDate;
		output += "\nInvoice Number\t: " + invoiceNo;
			   
		return output;
    }
    
    public String oneLine(Project currProject) {
		String output = projectNumber + ", " + projectName + ", " + buildingType + ", " + projectAddress + ", " + erfNumber + ", " 
						+ projectFee + ", " + projectBalance + ", " + completionDate + ", " + invoiceNo;
			   
		return output;
	}
    
}
