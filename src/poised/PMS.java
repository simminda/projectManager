package poised;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class PMS {

	public static void main(String[] args) throws IOException {
		// loop through main menu using while
		while (true) {
			mainMenu();
			
			Scanner s = new Scanner(System.in);
			int input = s.nextInt();
			
			if (input == 1) {
				captureProject();
			    
			    mainOrExit(); // go back to main menu or exit
			} else if (input == 2) {
				System.out.println("Enter project number: ");
				Scanner sc = new Scanner(System.in);
				String in = sc.nextLine();
				
				Project currProject = selectProject(in);
				
				if (currProject == null) {
					mainOrExit();
				} else {
					System.out.println(currProject); 	// show user current details before asking them to edit
					Assignee currContractor = selectContractor(in);
					System.out.println(currContractor); 
					
					System.out.println("\nWhat would you like to do? ");
					System.out.println("'1' - edit due date");
					System.out.println("'2' - edit balance");
					System.out.println("'3' - update contractor details");
					System.out.println("'4' - Finalize project");
					
					// accept user input and call on applicable function
					Scanner scan = new Scanner(System.in);
					Integer edit = scan.nextInt();
					
					if (edit == 1) {
						// edit due date
						System.out.println("Enter Due Date: ");
						Scanner st = new Scanner(System.in);
						String date = st.nextLine();
						
						// create new project object to manipulate using selectProject method which retrieves existing projects from file
						Project currProjectDate = selectProject(in);
						currProjectDate.setDeadline(date);
						
						String newStr = currProjectDate.oneLine(currProjectDate); // final details to be saved to file
						
						// print confirmation output
						System.out.println("\nThank you. Date change saved.\n"); 
						
					    commitProject(in, newStr); // arguments are input string and output string
					    
					    mainOrExit(); 
					} else if (edit == 2) {
						// edit balance
						System.out.println("Enter new balance e.g '100000': ");
						Scanner st = new Scanner(System.in);
						Double balance = st.nextDouble();
						st.nextLine();
						
						// create new project object to manipulate using selectProject method which retrieves existing projects from file
						Project currProjectBal = selectProject(in);	
						currProjectBal.setBalance(balance);
						
						String newStr = currProjectBal.oneLine(currProjectBal); // final details to be saved to file
						
						// print confirmation output
						System.out.println("\nThank you. Balance updated.\n"); 
						System.out.println(currProjectBal); 
					   
						commitProject(in, newStr); // arguments are input string and output string
					    
					    mainOrExit(); 
					} else if (edit == 3) {
						// edit contractor details
						Assignee con = selectContractor(in);
						System.out.println(con + "\n");
						Scanner st = new Scanner(System.in);
						String roleCon = "Contractor";
						System.out.println("Enter updated contractor name: ");
						String nameCon = st.nextLine();
						System.out.println("Enter updated Tel No: ");
						String telCon = st.nextLine();
						System.out.println("Enter updated email: ");
						String emailCon = st.nextLine();
						System.out.println("Enter updated Address: ");
						String addressCon = st.nextLine();
						
						// assign user inputs to new object and print confirmation output
						Assignee conCurr = new Assignee(roleCon, nameCon, telCon, emailCon, addressCon);
						String newStr = conCurr.oneLine(conCurr, in); // final details to be saved to file
						
						System.out.println("\nThank you. Contractor details Succesfully updated.\n"); 
					    System.out.println(conCurr + "\n");
					    
					    // save changes to file
					    ArrayList<String> contractorList = new ArrayList<String>();
						
						FileReader fileIn = new FileReader("contractors.txt");
						BufferedReader br = new BufferedReader(fileIn);
						
						String line = "";
						
						while ((line = br.readLine()) != null) {
							String[] lineArray = line.split(", ");		
							if (lineArray[0].equals(in)) {
								line = newStr;	// replace the line containing desired contractor with newStr
							}
							contractorList.add(line + "\n");
						}
						br.close();
						
						String finalStr = Arrays.toString(contractorList.toArray()).replace("[", "").replace("]", "");
						finalStr = finalStr.replace("\n, ", "\n");	// remove the comma space separator for the arraylist elements	
						
						FileWriter file = new FileWriter("contractors.txt");
						BufferedWriter bw = new BufferedWriter(file);
							
						bw.write(finalStr);
						bw.close();
					    
					    mainOrExit(); // go back to main menu or exit
					} else if (edit == 4) {
						// finalize project
						finalizeProject();
						
					    mainOrExit(); // go back to main menu or exit
					}
				}
			
			} else if (input == 3) {
				// finalize project from main menu
				finalizeProject();

			    // go back to main menu or exit
			    mainOrExit();
			    
			}else if (input == 4){
			   	// print all outstanding projects
				FileReader fileIn = new FileReader("projects.txt");
				BufferedReader br = new BufferedReader(fileIn);
				
				String line = "";
				
				while ((line = br.readLine()) != null) {
					String[] lineArray = line.split(", ");	
					System.out.println("Project Number\t: " + lineArray[0]);
					System.out.println("Project Name\t: " + lineArray[1]);
					System.out.println("Project Type\t: " + lineArray[2]);
					System.out.println("Address\t\t: " + lineArray[3]);
					System.out.println("erf Number\t: " + lineArray[4]);
					System.out.println("Project Fee\t: R" + lineArray[5]);
					System.out.println("Balance\t\t: R" + lineArray[6]);
					System.out.println("Due Date\t: " + lineArray[7] + "\n");
				}
				
			}else if (input == 5){
			   	// print all overdue projects
				
				// get today's date
				LocalDate today = LocalDate.now();
				
				FileReader fileIn = new FileReader("projects.txt");
				BufferedReader br = new BufferedReader(fileIn);
				
				String line = "";
				
				while ((line = br.readLine()) != null) {
					String[] lineArray = line.split(", ");	
					
					// get due date
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
					String date = lineArray[7];
					//convert String to LocalDate
					LocalDate dueDate = LocalDate.parse(date, formatter);
					
					if (dueDate.isBefore(today)) {
						System.out.println("Project Number\t: " + lineArray[0]);
						System.out.println("Project Name\t: " + lineArray[1]);
						System.out.println("Project Type\t: " + lineArray[2]);
						System.out.println("Address\t\t: " + lineArray[3]);
						System.out.println("erf Number\t: " + lineArray[4]);
						System.out.println("Project Fee\t: R" + lineArray[5]);
						System.out.println("Balance\t\t: R" + lineArray[6]);
						System.out.println("Due Date\t: " + lineArray[7] + "\n");
					}
				}
				br.close();
			
			}else {
			   	// if user enters any number on main menu other than those assigned to a function
				System.out.println("Sorry, that function is not yet available. Please contact helpdesk.\n\n");
			   	System.exit(0);
				}
		}
	}

	// this method asks for an invoice date and generates a new invoice 
	private static void finalizeProject() throws IOException {
		System.out.println("Enter project number: ");
		Scanner sc = new Scanner(System.in);
		String in = sc.nextLine();
		
		Project fProject = selectProject(in); 
		Assignee con = selectContact(in);
		System.out.println(fProject + "\n");
		System.out.println(con + "\n");

		// add completion date
		System.out.println("Enter Completion Date: ");
		Scanner st = new Scanner(System.in);
		String date = st.nextLine();
		
		// generate unique invoice number
		int invoiceNo;

		FileReader fileIn = new FileReader("invCount.txt");
		BufferedReader br = new BufferedReader(fileIn);
			
		String line = "";
		
		while ((line = br.readLine()) != null) {
		    invoiceNo = Integer.parseInt(line) + 1;
			Invoice invoice = new Invoice(invoiceNo, date, con, getBalance(in));
				
			System.out.println("\nThank you.\n"); 
			System.out.println(invoice + "\n");
				
			// save current invoice number to file
			FileWriter fileOut = new FileWriter("invCount.txt");
			BufferedWriter bw = new BufferedWriter(fileOut);
			bw.write(""+ invoiceNo);
			bw.close();
				
			// save completed project to file 
			String newStr = fProject.oneLine(fProject); // final details to be saved to file
				
			FileWriter file = new FileWriter("completeProject.txt", true);
			BufferedWriter buffer = new BufferedWriter(file);
					
			buffer.write(newStr + "\n");
			buffer.close();
		}
		
		// remove completed project from current projects file
		removeLineFromFile(in);
			
	    mainOrExit(); // go back to main menu or exit
		
	}

	// this method asks the user to capture all details and writes to file
	private static void captureProject() throws IOException {
		// capture project details
		// auto-generate project number
		int projectNo = 0;

		FileReader fileIn = new FileReader("projectCount.txt");
		BufferedReader br = new BufferedReader(fileIn);
			
		String line = "";
		while ((line = br.readLine()) != null) {
		    projectNo = Integer.parseInt(line) + 1;
		}
		
		// ask user to capture rest of details
		Scanner details = new Scanner(System.in);
		System.out.println("Enter project Name (e.g. 'House Tom'): ");
		String projectName = details.nextLine();
		System.out.println("Enter building type: ");
		String buildingType = details.nextLine();
		System.out.println("Enter project address: ");
		String projectAddress = details.nextLine();
		System.out.println("Enter erf number: ");
		String erfNumber = details.nextLine();
		System.out.println("Enter project Fee: ");
		Double projectFee = details.nextDouble();
		System.out.println("Enter Outstanding balance: ");
		Double projectBalance = details.nextDouble();
		details.nextLine();
		System.out.println("Enter project deadline: ");
		String deadline = details.nextLine();
		
		// assign user inputs to new object and print confirmation output
		Project currProject = new Project(projectNo, projectName, buildingType, projectAddress,
											erfNumber, projectFee, projectBalance, deadline);
		System.out.println("\nThank you. Project Succesfully Captured.\n"); 
		System.out.println(currProject + "\n");
		
		// save changes to file 
		String newStr = currProject.oneLine(currProject); // final details to be saved to file
		
		FileWriter file = new FileWriter("projects.txt", true);
		BufferedWriter buffer = new BufferedWriter(file);
			
		buffer.write(newStr);
		buffer.close();    

		// capture customer person
		String role = "Contact";
		System.out.println("Enter customer name: ");
		String name = details.nextLine();
		System.out.println("Enter Tel No: ");
		String tel = details.nextLine();
		System.out.println("Enter contact email: ");
		String email = details.nextLine();
		System.out.println("Enter Address: ");
		String address = details.nextLine();
		
		// assign user inputs to new object and print confirmation output
		Assignee person = new Assignee(role, name, tel, email, address);
		System.out.println("\nThank you. Details Succesfully Captured.\n"); 
		System.out.println(person + "\n");
		
		// save changes to file 
		String newStr1 = person.oneLine(person, ("" + projectNo)); // final details to be saved to file
				
		FileWriter file1 = new FileWriter("contacts.txt", true);
		BufferedWriter buffer1 = new BufferedWriter(file1);
					
		buffer1.write(newStr1);
		buffer1.close(); 	
		
		// capture architect
		String roleArc = "Architect";
		System.out.println("Enter architect name: ");
		String nameArc = details.nextLine();
		System.out.println("Enter Tel No: ");
		String telArc = details.nextLine();
		System.out.println("Enter contact email: ");
		String emailArc = details.nextLine();
		System.out.println("Enter Address: ");
		String addressArc = details.nextLine();
		
		// assign user inputs to new object and print confirmation output
		Assignee arc = new Assignee(roleArc, nameArc, telArc, emailArc, addressArc);
		System.out.println("\nThank you. Details Succesfully Captured.\n"); 
		System.out.println(arc + "\n");
		
		// save changes to file 
		String newStr2 = arc.oneLine(arc, ("" + projectNo)); // final details to be saved to file
				
		FileWriter file2 = new FileWriter("architects.txt", true);
		BufferedWriter buffer2 = new BufferedWriter(file2);
					
		buffer2.write(newStr2);
		buffer2.close(); 
		
		// capture contractor
		String roleCon = "Contractor";
		System.out.println("Enter contractor name: ");
		String nameCon = details.nextLine();
		System.out.println("Enter Tel No: ");
		String telCon = details.nextLine();
		System.out.println("Enter contact email: ");
		String emailCon = details.nextLine();
		System.out.println("Enter Address: ");
		String addressCon = details.nextLine();
		
		// assign user inputs to new object and print confirmation output
		Assignee con = new Assignee(roleCon, nameCon, telCon, emailCon, addressCon);
		System.out.println("\nThank you. Details Succesfully Captured.\n"); 
		System.out.println(con + "\n");
		
		// save changes to file 
		String newStr3 = con.oneLine(con, ("" + projectNo)); // final details to be saved to file
				
		FileWriter file3 = new FileWriter("architects.txt", true);
		BufferedWriter buffer3 = new BufferedWriter(file3);
					
		buffer3.write(newStr3);
		buffer3.close(); 
	}

	// this method allows user to exit or go back to the main menu
	private static void mainOrExit() {
		System.out.println("Enter any key to continue or enter 'e' to exit: ");
		Scanner scan = new Scanner(System.in);
		char choice = scan.next().charAt(0);
		if (choice == 'e') {
			System.out.println("\nThank You!!!");
			System.exit(0);	
		}
	}
	
	// this method presents the main menu 
	public static void mainMenu() {
		System.out.println(" *** Welcome to Poised PMS ***\n");
		System.out.println("What would you like to do?");
		System.out.println("'1' - Capture new project");
		System.out.println("'2' - Update project");
		System.out.println("'3' - Finalize project");
		System.out.println("'4' - View Outstanding projects");
		System.out.println("'5' - View Overdue projects\n");
	}
	
	// this method gets and returns the project details for a given project number or project name
	public static Project selectProject(String projectNumOrName) throws IOException {
		Project currProject = null;
		FileReader fileIn = new FileReader("projects.txt");
		BufferedReader br = new BufferedReader(fileIn);
		
		int counter = 0;
		String line = "";
		while ((line=br.readLine())!= null) {
			String[] lineArray = line.split(", ");		
			if (lineArray[0].equals(projectNumOrName) || lineArray[1].equals(projectNumOrName)) {
				counter += 1;
				currProject = new Project(Integer.parseInt(projectNumOrName), lineArray[1], lineArray[2], lineArray[3], lineArray[4],
						Double.parseDouble(lineArray[5]), Double.parseDouble(lineArray[6]), lineArray[7]);
				selectContractor(projectNumOrName);
			}
		}
		if (counter == 0) {
				System.out.println("Project number not found\n");
				return null;
		}
		return currProject; 
	}
	
	// this method gets and returns the contractor for a given project number
	public static Assignee selectContractor(String projectNumber) throws IOException {
		Assignee currContractor = null;
		FileReader fileIn = new FileReader("contractors.txt");
		BufferedReader br = new BufferedReader(fileIn);
		
		String line = "";	
		while ((line=br.readLine())!= null) {
			String[] lineArray = line.split(", ");		
			if (line.contains(projectNumber)) {
				currContractor = new Assignee(lineArray[1], lineArray[2], lineArray[3], lineArray[4], lineArray[5]);
			}
		}
		return currContractor; 
	}
	
	// this method gets and returns the customer/contact for a given project number
	public static Assignee selectContact(String projectNumber) throws IOException {
		Assignee currContact = null;
		FileReader fileIn = new FileReader("contacts.txt");
		BufferedReader br = new BufferedReader(fileIn);
		
		String line = "";	
		while ((line=br.readLine())!= null) {
			String[] lineArray = line.split(", ");		
			if (line.contains(projectNumber)) {
				currContact = new Assignee(lineArray[1], lineArray[2], lineArray[3], lineArray[4], lineArray[5]);
			}
		}
		return currContact; 
	}
	
	// this method gets the balance customer is still owing
	public static Double getBalance(String projectNum) throws IOException {
		Project currProject = null;
		FileReader fileIn = new FileReader("projects.txt");
		BufferedReader br = new BufferedReader(fileIn);
		
		Double balance = null;
		String line = "";
		while ((line=br.readLine())!= null) {
			String[] lineArray = line.split(", ");		
			if (line.contains(projectNum)) {
				currProject = new Project(Integer.parseInt(projectNum), lineArray[1], lineArray[2], lineArray[3], lineArray[4],
						Double.parseDouble(lineArray[5]), Double.parseDouble(lineArray[6]), lineArray[7]);
				balance = Double.parseDouble(lineArray[6]);
			}
		}
		return balance; 
	}
	
	// this method saves/commits changes to file 
	public static void commitProject(String projectNum, String newStr) throws IOException {
		// Declare ArrayList to store and manipulate projects
		ArrayList<String> projectList = new ArrayList<String>();
		
		FileReader fileIn = new FileReader("projects.txt");
		BufferedReader br = new BufferedReader(fileIn);
		
		String line = "";
		
		while ((line = br.readLine()) != null) {
			if (line.contains(projectNum)) {
				line = newStr;	// replace the line containing desired project num with newStr
			}
			projectList.add(line + "\n");
		}
		br.close();
		
		String finalStr = Arrays.toString(projectList.toArray()).replace("[", "").replace("]", "");
		finalStr = finalStr.replace("\n, ", "\n");	// remove the comma space separator for the arraylist elements	
		
		FileWriter file = new FileWriter("projects.txt");
		BufferedWriter bw = new BufferedWriter(file);
			
		bw.write(finalStr);
		bw.close();
	}
	
	// this method deletes line from a text file
	public static void removeLineFromFile(String projectNum) throws FileNotFoundException, IOException {
		ArrayList<String> projectList = new ArrayList<String>();
		
		FileReader fileIn = new FileReader("projects.txt");
		BufferedReader br = new BufferedReader(fileIn);
		
		String line = "";
		
		while ((line = br.readLine()) != null) {
			if (line.contains(projectNum)) {
				continue;
			}
			projectList.add(line + "\n");
		}
		br.close();
		
		String finalStr = Arrays.toString(projectList.toArray()).replace("[", "").replace("]", "");
		finalStr = finalStr.replace("\n, ", "\n");	// remove the comma space separator for the arraylist elements	
		
		FileWriter file = new FileWriter("projects.txt");
		BufferedWriter bw = new BufferedWriter(file);
			
		bw.write(finalStr);
		bw.close();
	}
	
}