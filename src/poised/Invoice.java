package poised;

public class Invoice {
	Integer invoiceNo;
	String invoiceDate;
	Persons customer;
	Double projectFee;

	public Invoice(Integer invoiceNo, String invoiceDate, Persons customer, Double projectFee) {
		this.invoiceNo = invoiceNo;
		this.invoiceDate = invoiceDate;
		this.customer = customer;
		this.projectFee = projectFee;
	}
	
    public String toString() {
		String output = "Tax Invoice";
		output += "\n\nInvoice Number\t: " + invoiceNo;
		output += "\nInvoice Date\t: " + invoiceDate;
		output += "\nCustomer\t: " + customer;
		output += "\nAmount\t\t: R" + projectFee;
			   
		return output;
    }
    
	public String oneLine(Invoice invoiceNo, String projectNum) {
		String output = projectNum + ", " + invoiceNo + ", " + invoiceDate + ", " + customer + ", " + projectFee;
		
		return output;
	}
      
}
