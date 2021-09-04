package poised;

import java.io.*;
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
				Scanner details = new Scanner(System.in);
				// capture project
				System.out.println("Enter project Number: ");
				Integer projectNumber = details.nextInt();
				details.nextLine();
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
				Project currProject = new Project(projectNumber, projectName, buildingType, projectAddress,
	    											erfNumber, projectFee, projectBalance, deadline);
				System.out.println("\nThank you. Project Succesfully Captured.\n"); 
			    System.out.println(currProject + "\n");

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
			    
			    // go back to main menu or exit
			    System.out.println("Enter any key to continue or enter 'e' to exit: ");
			    Scanner in = new Scanner(System.in);
			    char choice = in.next().charAt(0);
			    if (choice == 'e') {
			    	System.out.println("\nThank You!!!");
			    	System.exit(0);			                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
			    } 
			} else if (input == 2) {
				System.out.println("Enter project number: ");
				Scanner in = new Scanner(System.in);
				Integer projectNumber = in.nextInt();
				// check existing project number on text/data file and then return confirmation to user
				System.out.println("\nProject 1024"); //example of found project number
				System.out.println("'1' - edit due date");
				System.out.println("'2' - edit balance");
				System.out.println("'3' - update contractor details");
				System.out.println("'4' - Finalize project");
				
				// accept user input and call on applicable function
				Scanner sc = new Scanner(System.in);
				Integer edit = sc.nextInt();
				if (edit == 1) {
					// edit due date
					System.out.println("Enter Due Date: ");
					Scanner st = new Scanner(System.in);
					String date = st.nextLine();
					// below line is a placeholder to demonstrate functionality in the absence of file handling
					Project currProject = new Project(1024, "Mike House", "House", "1 Main Str, Florida",
		    				"000000001234", 1000000.00, 750000.00, date);	
					currProject.setDeadline(date);
					System.out.println("\nThank you. Date change saved.\n"); 
				    System.out.println(currProject + "\n");
				    
				    // go back to main menu or exit
				    System.out.println("Enter any key to continue or enter 'e' to exit: ");
				    Scanner scan = new Scanner(System.in);
				    char choice = scan.next().charAt(0);
				    if (choice == 'e') {
				    	System.out.println("\nThank You!!!");
				    	System.exit(0);			                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
				    } 
				} else if (edit == 2) {
					// edit balance
					System.out.println("Enter new balance: ");
					Scanner st = new Scanner(System.in);
					Double balance = st.nextDouble();
					st.nextLine();
					// below line is a placeholder to demonstrate functionality in the absence of file handling
					Project currProject = new Project(1024, "Mike House", "House", "1 Main Str, Florida",
		    											"000000001234", 1000000.00, balance, "31 August 2021");	
					currProject.setBalance(balance);
					System.out.println("\nThank you. Balance updated.\n"); 
				    System.out.println(currProject + "\n");
				    
				    // go back to main menu or exit
				    System.out.println("Enter any key to continue or enter 'e' to exit: ");
				    Scanner scan = new Scanner(System.in);
				    char choice = scan.next().charAt(0);
				    if (choice == 'e') {
				    	System.out.println("\nThank You!!!");
				    	System.exit(0);	
				    }
				} else if (edit == 3) {
					// edit contractor details
					// below line is a placeholder to demonstrate functionality in the absence of file handling
					Assignee con = new Assignee("Contractor", "Midmar Consulting", "0334001234", 
												"info@midmar.co.za", "1 Farm Drive, Waterfalls, Howick");
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
					System.out.println("\nThank you. Contractor details Succesfully updated.\n"); 
				    System.out.println(conCurr + "\n");
				    
				    // go back to main menu or exit
				    System.out.println("Enter any key to continue or enter 'e' to exit: ");
				    Scanner scan = new Scanner(System.in);
				    char choice = scan.next().charAt(0);
				    if (choice == 'e') {
				    	System.out.println("\nThank You!!!");
				    	System.exit(0);	
				    }
				} else if (edit == 4) {
					// finalize project
					// below line is a placeholder to demonstrate functionality in the absence of file handling
					Project currProject = new Project(1024, "Mike House", "House", "1 Main Str, Florida",
		    											"000000001234", 1000000.00, 750000.00, "31 August 2021");
					Assignee con = new Assignee("Mike Meyers", "Midmar Consulting", "0834001234", 
														"mike@zonke.co.za", "1 Farm Drive, Waterfalls, Midrand");
					// add completion date
					System.out.println("Enter Completion Date: ");
					Scanner st = new Scanner(System.in);
					String date = st.nextLine();
					// generate invoice details
					int invoiceNo;
					try {
						FileReader fileIn = new FileReader("invCount.txt");
						BufferedReader br = new BufferedReader(fileIn);
						
						String line = "";
						while((line=br.readLine()) != null) {
		
				            invoiceNo = Integer.parseInt(line) + 1;
							Invoice invoice = new Invoice(invoiceNo, date, con, 1000000.00);
							
							System.out.println("\nThank you.\n"); 
							System.out.println(invoice + "\n");
							FileWriter fileOut = new FileWriter("invCount.txt");
							BufferedWriter bw = new BufferedWriter(fileOut);
							bw.write(""+ invoiceNo);
							bw.close();
		                  
						} // end while loop
						br.close();
						
					} catch (IOException e) {
						System.out.println("");
					  }

				    // go back to main menu or exit
				    System.out.println("Enter any key to continue or enter 'e' to exit: ");
				    Scanner scan = new Scanner(System.in);
				    char choice = scan.next().charAt(0);
				    if (choice == 'e') {
				    	System.out.println("\nThank You!!!");
				    	System.exit(0);	
				    }
				}
			
			} else if (input == 3) {
				// finalize project from main menu
				selectProject();
//				System.out.println("Enter project number: ");
//				Scanner in = new Scanner(System.in);
//				Integer projectNumber = in.nextInt();
				// check existing project number on text/data file and then return confirmation to user
				System.out.println("\nProject 1024"); //example
//				// project to bill
//				Project currProject = new Project(1024, "Mike House", "House", "1 Main Str, Florida",
//	    											"000000001234", 1000000.00, 750000.00, "31 August 2021");
//				// customer to bill
				Assignee cont = new Assignee("Mike Meyers", "Midmar Consulting", "0834001234", 
													"mike@zonke.co.za", "1 Farm Drive, Waterfalls, Midrand");
				// add completion date
				System.out.println("Enter Completion Date: ");
				Scanner st = new Scanner(System.in);
				String date = st.nextLine();
				// generate invoice details
				int invoiceNo;
				try {
					FileReader fileIn = new FileReader("invCount.txt");
					BufferedReader br = new BufferedReader(fileIn);
					
					String line = "";
					while((line=br.readLine()) != null) {
	
			            invoiceNo = Integer.parseInt(line) + 1;
						Invoice invoice = new Invoice(invoiceNo, date, cont, 1000000.00);
						
						System.out.println("\nThank you.\n"); 
						System.out.println(invoice + "\n");
						FileWriter fileOut = new FileWriter("invCount.txt");
						BufferedWriter bw = new BufferedWriter(fileOut);
						bw.write(""+ invoiceNo);
						bw.close();
	                  
					} // end while loop
					br.close();
					
				} catch (IOException e) {
					System.out.println("");
				  }

			    
			    // go back to main menu or exit
			    System.out.println("Enter any key to continue or enter 'e' to exit: ");
			    Scanner scan = new Scanner(System.in);
			    char choice = scan.next().charAt(0);
			    if (choice == 'e') {
			    	System.out.println("\nThank You!!!");
			    	System.exit(0);	
			    }
				
			}else {
			   	// if user enters any number on main menu other than those assigned to a function
				System.out.println("Sorry, that function is not yet available. Please contact helpdesk.\n\n");
			   	System.exit(0);
				}
		}
	}
		
	public static void mainMenu() {
		// Present main menu to user
		System.out.println(" *** Welcome to Poised PMS ***\n");
		System.out.println("What would you like to do?");
		System.out.println("'1' - Capture new project");
		System.out.println("'2' - Update project");
		System.out.println("'3' - Finalize project");
		System.out.println("'4' - View Outstanding projects");
		System.out.println("'5' - View Overdue projects\n");
	}
	
	public static void selectProject() throws IOException {
		// search for an existing project
		FileReader fileIn = new FileReader("projects.txt");
		BufferedReader br = new BufferedReader(fileIn);
		
		String line = "";
		
		System.out.println("Enter project number: ");
		Scanner sc = new Scanner(System.in);
		Integer input = sc.nextInt();
		sc.nextLine();
		while((line=br.readLine())!= null) {
			String[] lineArray = line.split(", ");		
			System.out.println(line);
			
			
			
			
			if (input == Integer.parseInt(lineArray[0])) {
				Project currProject = new Project(Integer.parseInt(lineArray[0]), lineArray[1], lineArray[2], lineArray[3], lineArray[4],
										Double.parseDouble(lineArray[5]), Double.parseDouble(lineArray[6]), lineArray[7]);
				System.out.println(currProject);
			} else {
				System.out.println("Project number not found");
			} // after which go back to main menu  or exit
		
		}
	}
}
