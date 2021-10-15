package poised;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class PMS {

	public static void main(String[] args) throws IOException {
		// enclose code in loop to take user back to main menu after completing tasks
		while (true) {
			mainMenu();
			
			try {
				Scanner s = new Scanner(System.in);
				int input = s.nextInt();
				s.nextLine(); // clears the buffer
			
				if (input == 1) {
					captureProject();
				    
				    mainOrExit(); // offer option to go back to main menu or quit program
				} else if (input == 2) {
					System.out.println("Enter project number: ");
					Scanner sc = new Scanner(System.in);
					String projNumInput = sc.nextLine();
					
					Project currProject = selectProject(projNumInput);
					
					if (currProject == null) {
						mainOrExit();
					} else {
						System.out.println(currProject); 	// show user current details before asking them to edit
						Persons currContractor = selectContractor(projNumInput); // get details of contractor for this project number
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
							System.out.println("Enter Due Date e.g '2020-12-31': ");
							Scanner st = new Scanner(System.in);
							String date = st.nextLine();							

							try {
								// Connect to the poisepms database, via the jdbc:mysql:
								Connection connection = DriverManager.getConnection(
									"jdbc:mysql://localhost:3306/poisepms?useSSL=false",
									"otheruser",
									"swordfish"
									);
								// Create a direct line to the database for running queries
								Statement statement = connection.createStatement();
								ResultSet results;
								int rowsAffected;
								// executeQuery: runs a SELECT statement and returns the results.
								String inputStr = "SELECT * FROM projects WHERE P_NUM =" + projNumInput;
								results = statement.executeQuery(inputStr);
								// 
								String outputStr = "UPDATE projects SET DUE_DATE ='" + date + "' WHERE P_NUM =" + projNumInput;
								rowsAffected = statement.executeUpdate(outputStr); 
								// Close connections
								results.close();
								statement.close();
								connection.close();
							} catch (SQLException e) {
								//catch a SQLException 
								e.printStackTrace();
							}
							
							// print confirmation output
							System.out.println("\nThank you. Date change saved.\n"); 
						    
						    mainOrExit(); 
						} else if (edit == 2) {
							// edit balance
							System.out.println("Enter new balance e.g '100000': ");
							Scanner st = new Scanner(System.in);
							Double balance = st.nextDouble();
							st.nextLine();
							
							try {
								// Connect to the poisepms database, via the jdbc:mysql:
								Connection connection = DriverManager.getConnection(
									"jdbc:mysql://localhost:3306/poisepms?useSSL=false",
									"otheruser",
									"swordfish"
									);
								// Create a direct line to the database for running queries
								Statement statement = connection.createStatement();
								ResultSet results;
								int rowsAffected;
								// executeQuery: runs a SELECT statement and returns the results.
								String inputStr = "SELECT * FROM projects WHERE P_NUM =" + projNumInput;
								results = statement.executeQuery(inputStr);
								// 
								String outputStr = "UPDATE projects SET P_BAL ='" + balance + "' WHERE P_NUM =" + projNumInput;
								rowsAffected = statement.executeUpdate(outputStr); 
								// Close connections
								results.close();
								statement.close();
								connection.close();
							} catch (SQLException e) {
								//catch a SQLException 
								e.printStackTrace();
							}
							
							// print confirmation output
							System.out.println("\nThank you. Balance updated.\n"); 
						    
						    mainOrExit(); 
						} else if (edit == 3) {
							// edit contractor details
							Persons con = selectContractor(projNumInput);
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
							String addressConF = addressCon.replace(",", "_");		// ensure user does not capture commas 
							
							// assign user inputs to new object and print confirmation output
							Persons conCurr = new Persons(roleCon, nameCon, telCon, emailCon, addressConF);
							
							System.out.println("\nThank you. Contractor details Succesfully updated.\n"); 
						    System.out.println(conCurr + "\n");
						    
						    // save changes to database
						    try {
								// Connect to the poisepms database, via the jdbc:mysql:
								Connection connection = DriverManager.getConnection(
									"jdbc:mysql://localhost:3306/poisepms?useSSL=false",
									"otheruser",
									"swordfish"
									);
								// Create a direct line to the database for running queries
								Statement statement = connection.createStatement();
								ResultSet results;
								int rowsAffected;
								// executeQuery: runs a SELECT statement and returns the results.
								String inputStr = "SELECT * FROM assignees WHERE P_NUM =" + projNumInput + " AND P_ROLE = '" + roleCon + "'";
								results = statement.executeQuery(inputStr);
								// 
								String outputStr = "UPDATE assignees SET P_ROLE ='" + roleCon + "', NAME = '" + nameCon + "', NUMBR = '" + telCon + "', EMAIL = '" +
										 			emailCon + "', ADDR = '" + addressConF + "' WHERE P_NUM =" + projNumInput + " AND P_ROLE = '" + roleCon + "'";
								rowsAffected = statement.executeUpdate(outputStr); 
								// Close connections
								results.close();
								statement.close();
								connection.close();
							} catch (SQLException e) {
								//catch a SQLException 
								e.printStackTrace();
							}
						   
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
					try {
						// Connect to the poisepms database, via the jdbc:mysql:
						Connection connection = DriverManager.getConnection(
							"jdbc:mysql://localhost:3306/poisepms?useSSL=false",
							"otheruser",
							"swordfish"
							);
						// Create a direct line to the database for running queries
						Statement statement = connection.createStatement();
						ResultSet results;
						int rowsAffected;
						// executeQuery: runs a SELECT statement and returns the results.
						String inputStr = "SELECT P_NAME FROM projects";
						results = statement.executeQuery(inputStr);
						
						printAllFromTable(statement);
						System.out.println("");
						
						// Close connections
						results.close();
						statement.close();
						connection.close();
					} catch (SQLException e) {
						//catch a SQLException 
						e.printStackTrace();
					}
					
					mainOrExit();
				}else if (input == 5){
				   	// print all overdue projects
					
					try {
						// Connect to the poisepms database, via the jdbc:mysql:
						Connection connection = DriverManager.getConnection(
							"jdbc:mysql://localhost:3306/poisepms?useSSL=false",
							"otheruser",
							"swordfish"
							);
						// Create a direct line to the database for running queries
						Statement statement = connection.createStatement();
						ResultSet results;
						int rowsAffected;
						// executeQuery: runs a SELECT statement and returns the results.
						results = statement.executeQuery("SELECT * FROM Projects");		
						// print projects
						printOverdueFromTable(statement);
						System.out.println("");
						// Close connections
						results.close();
						statement.close();
						connection.close();
					} catch (SQLException e) {
						//catch a SQLException 
						e.printStackTrace();
					}
					
					mainOrExit();
				}
				
			} catch (InputMismatchException e) {
				System.out.println("Please enter a number corresponding to function you want to perform.\n");
			}
		} 	//close while loop
	} 	// close main method

	
	// this method generates a project number, asks the user to capture all details and saves to database
	private static void captureProject() throws IOException {
		// capture project details
		
		// auto-generate project number
		int projectNo = 0;
		
		try {
			// Connect to the poisepms database, via the jdbc:mysql:
			Connection connection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/poisepms?useSSL=false",
				"otheruser",
				"swordfish"
				);
			// Create a direct line to the database for running queries
			Statement statement = connection.createStatement();
			ResultSet results;
			int rowsAffected;
			// executeQuery: runs a SELECT statement and returns the results.
			results = statement.executeQuery("SELECT * FROM projectCount");
			// get last project number
			int pNum = 0;
			while (results.next()) {
				pNum = results.getInt(1);
				//System.out.println(pNum);;
			}
			projectNo = pNum + 1;
			rowsAffected = statement.executeUpdate("UPDATE projectCount SET P_NUM=" + projectNo); 
			// Close connections
			results.close();
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			//catch a SQLException 
			e.printStackTrace();
		}
		
		// ask user to capture rest of details
		Scanner details = new Scanner(System.in);
		System.out.println("Enter project Name (e.g. 'House Tom'): ");
		String projectName = details.nextLine();
		System.out.println("Enter building type: ");
		String buildingType = details.nextLine();
		System.out.println("Enter project address: ");
		String projectAddress = details.nextLine();
		String projectAddressF = projectAddress.replace(',', '_'); // ensure user does not capture commas 
		System.out.println("Enter erf number: ");
		String erfNumber = details.nextLine();
		System.out.println("Enter project Fee (e.g. '10000'): ");
		Double projectFee = details.nextDouble();
		System.out.println("Enter Outstanding balance (e.g. '10000'): ");
		Double projectBalance = details.nextDouble();
		details.nextLine();
		System.out.println("Enter project deadline (e.g. '2010-12-31'): ");
		String deadline = details.nextLine();
		
		// assign user inputs to new object and print confirmation output
		Project currProject = new Project(projectNo, projectName, buildingType, projectAddressF,
											erfNumber, projectFee, projectBalance, deadline);
		System.out.println("\nThank you. Project Succesfully Captured.\n"); 
		System.out.println(currProject + "\n");
		
		// save new project to database  
		try {
			// Connect to the poisepms database, via the jdbc:mysql:
			Connection connection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/poisepms?useSSL=false",
				"otheruser",
				"swordfish"
				);
			// Create a direct line to the database for running queries
			Statement statement = connection.createStatement();
			ResultSet results;
			int rowsAffected;
			// executeQuery: runs a SELECT statement and returns the results.
			results = statement.executeQuery("SELECT * FROM Projects");
			
			// save 
			String output = "INSERT INTO Projects VALUES (" + projectNo + ", '" + projectName + "', '" + buildingType + "', '" + projectAddressF + "', '" 
			+ erfNumber + "', " + projectFee + ", " + projectBalance + ", '" + deadline + "')";
			rowsAffected = statement.executeUpdate(output); 
			System.out.println("Query complete, " + rowsAffected + " rows updated.\n");
			// Close connections
			results.close();
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			//catch a SQLException 
			e.printStackTrace();
		}

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
		String addressF = address.replace(",", "_");	// ensure user does not capture commas (file separator is commas)
		
		// assign user inputs to new object and print confirmation output
		Persons person = new Persons(role, name, tel, email, addressF);
		System.out.println("\nThank you. Details Succesfully Captured.\n"); 
		System.out.println(person + "\n");
		
		// save changes to databases
		try {
			// Connect to the poisepms database, via the jdbc:mysql:
			Connection connection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/poisepms?useSSL=false",
				"otheruser",
				"swordfish"
				);
			// Create a direct line to the database for running queries
			Statement statement = connection.createStatement();
			ResultSet results;
			int rowsAffected;
			// executeQuery: runs a SELECT statement and returns the results.
			results = statement.executeQuery("SELECT * FROM Assignees");
			
			// save 
			String output = "INSERT INTO Assignees VALUES (" + projectNo + ", '" + role + "', '" + name + "', '" + tel + "', '" 
			+ email + "', '" + addressF + "')";
			rowsAffected = statement.executeUpdate(output); 
			System.out.println("Query complete, " + rowsAffected + " rows updated.\n");
			// Close connections
			results.close();
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			//catch a SQLException 
			e.printStackTrace();
		}
		 		
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
		String addressArcF = addressArc.replace(",", "_");		// ensure user does not capture commas 
		
		// assign user inputs to new object and print confirmation output
		Persons arc = new Persons(roleArc, nameArc, telArc, emailArc, addressArcF);
		System.out.println("\nThank you. Details Succesfully Captured.\n"); 
		System.out.println(arc + "\n");
		
		// save changes to databases
		try {
			// Connect to the poisepms database, via the jdbc:mysql:
			Connection connection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/poisepms?useSSL=false",
				"otheruser",
				"swordfish"
				);
			// Create a direct line to the database for running queries
			Statement statement = connection.createStatement();
			ResultSet results;
			int rowsAffected;
			// executeQuery: runs a SELECT statement and returns the results.
			results = statement.executeQuery("SELECT * FROM Assignees");
			
			// save 
			String output = "INSERT INTO Assignees VALUES (" + projectNo + ", '" + roleArc + "', '" + nameArc + "', '" + telArc + "', '" 
			+ emailArc + "', '" + addressArcF + "')";
			rowsAffected = statement.executeUpdate(output); 
			System.out.println("Query complete, " + rowsAffected + " rows updated.\n");
			// Close connections
			results.close();
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			//catch a SQLException 
			e.printStackTrace();
		}
		
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
		String addressConF = addressCon.replace(",", "_"); 	// ensure user does not capture commas (file separator is commas)
		
		// assign user inputs to new object and print confirmation output
		Persons con = new Persons(roleCon, nameCon, telCon, emailCon, addressConF);
		System.out.println("\nThank you. Details Succesfully Captured.\n"); 
		System.out.println(con + "\n");
		
		// save changes to databases
		try {
			// Connect to the poisepms database, via the jdbc:mysql:
			Connection connection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/poisepms?useSSL=false",
				"otheruser",
				"swordfish"
				);
			// Create a direct line to the database for running queries
			Statement statement = connection.createStatement();
			ResultSet results;
			int rowsAffected;
			// executeQuery: runs a SELECT statement and returns the results.
			results = statement.executeQuery("SELECT * FROM Assignees");
			
			// save 
			String output = "INSERT INTO Assignees VALUES (" + projectNo + ", '" + roleCon + "', '" + nameCon + "', '" + telCon + "', '" 
			+ emailCon + "', '" + addressConF + "')";
			rowsAffected = statement.executeUpdate(output); 
			System.out.println("Query complete, " + rowsAffected + " rows updated.\n");
			// Close connections
			results.close();
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			//catch a SQLException 
			e.printStackTrace();
		}
	}
	
	// this method asks for an invoice date and generates a new invoice 
		private static void finalizeProject() throws IOException {
			System.out.println("Enter project number: ");
			Scanner sc = new Scanner(System.in);
			String in = sc.nextLine();
			
			Project fProject = selectProject(in); 
			Persons con = selectContact(in);
			System.out.println(fProject + "\n");
			System.out.println(con + "\n");

			// add completion date
			System.out.println("Enter Completion Date: ");
			Scanner st = new Scanner(System.in);
			String date = st.nextLine();
			
			// auto-generate invoice number
					int invNo = 0;
					
					try {
						// Connect to the poisepms database, via the jdbc:mysql:
						Connection connection = DriverManager.getConnection(
							"jdbc:mysql://localhost:3306/poisepms?useSSL=false",
							"otheruser",
							"swordfish"
							);
						// Create a direct line to the database for running queries
						Statement statement = connection.createStatement();
						ResultSet results;
						int rowsAffected;
						// executeQuery: runs a SELECT statement and returns the results.
						results = statement.executeQuery("SELECT * FROM invCount");
						// get last invoice number
						int tempNo = 0;
						while (results.next()) {
							tempNo = results.getInt(1);
						}
						invNo = tempNo + 1;
						rowsAffected = statement.executeUpdate("UPDATE invCount SET INV_NUM =" + invNo); 
						// Close connections
						results.close();
						statement.close();
						connection.close();
					} catch (SQLException e) {
						//catch a SQLException 
						e.printStackTrace();
					}
					
				// print invoice details to user
				Invoice invoice = new Invoice(invNo, date, con, getBalance(in));
					
				System.out.println("\nThank you.\n"); 
				System.out.println(invoice + "\n");
								
				// save completed project to database 
				try {
					// Connect to the poisepms database, via the jdbc:mysql:
					Connection connection = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/poisepms?useSSL=false",
						"otheruser",
						"swordfish"
						);
					// Create a direct line to the database for running queries
					Statement statement = connection.createStatement();
					ResultSet results;
					int rowsAffected;
					// executeQuery: runs a SELECT statement and returns the results.
					results = statement.executeQuery("SELECT * FROM Projects WHERE P_NUM = " + in);
					
					// get project from database
					int P_NUM = 0;
					String P_NAME, P_TYPE, P_ADDR, ERF_NUM, DUE_DATE;
					Double P_FEE, P_BAL;
					while (results.next()) {
						P_NUM = results.getInt(1);
						P_NAME = results.getString(2);
						P_TYPE = results.getString(3);
						P_ADDR = results.getString(4);
						ERF_NUM = results.getString(5);
						P_FEE = results.getDouble(6);
						P_BAL = results.getDouble(7);
						DUE_DATE = results.getString(8);			

					// save to complete project
					String output = "INSERT INTO CompleteProjects VALUES (" + P_NUM + ", '" + P_NAME + "', '" + P_TYPE + "', '" + P_ADDR + "', '" 
					+ ERF_NUM + "', " + P_FEE + ", " + P_BAL + ", '" + DUE_DATE +  "', " + invNo + ")";
					rowsAffected = statement.executeUpdate(output); 
					System.out.println("Query complete, " + rowsAffected + " rows added to completeProjects.\n");
					
					// delete from active projects
					String out = "DELETE FROM projects WHERE P_NUM = " + in;
					rowsAffected = statement.executeUpdate(out); 
					System.out.println("Query complete, " + rowsAffected + " rows deleted from projects.");
					}
					// Close connections
					results.close();
					statement.close();
					connection.close();
				} catch (SQLException e) {
					//catch a SQLException 
				}
				System.out.println("");
				
				mainOrExit(); // offer option to go back to main menu or exit	
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
	public static Project selectProject(String projectNum) throws IOException {
		Project currProject = null;
		
		try {
			// Connect to the poisepms database, via the jdbc:mysql:
			Connection connection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/poisepms?useSSL=false",
				"otheruser",
				"swordfish"
				);
			// Create a direct line to the database for running queries
			Statement statement = connection.createStatement();
			ResultSet results;
			int rowsAffected;
			// executeQuery: runs a SELECT statement and returns the results.
			String input = "SELECT * FROM projects WHERE P_NUM =" + projectNum;
			results = statement.executeQuery(input);
			// get project from database
			int P_NUM = 0;
			String P_NAME, P_TYPE, P_ADDR, ERF_NUM, DUE_DATE;
			Double P_FEE, P_BAL;
			while (results.next()) {
				P_NUM = results.getInt(1);
				P_NAME = results.getString(2);
				P_TYPE = results.getString(3);
				P_ADDR = results.getString(4);
				ERF_NUM = results.getString(5);
				P_FEE = results.getDouble(6);
				P_BAL = results.getDouble(7);
				DUE_DATE = results.getString(8);
							
				currProject = new Project(P_NUM, P_NAME, P_TYPE, P_ADDR, ERF_NUM, P_FEE, P_BAL, DUE_DATE);
			}
			// Close connections
			results.close();
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			//catch a SQLException 
			e.printStackTrace();
		}	
		return currProject; 
	}
	
	// this method gets and returns the contractor for a given project number
	public static Persons selectContractor(String projectNumber) throws IOException {
		Persons currContractor = null;
		
		try {
			// Connect to the poisepms database, via the jdbc:mysql:
			Connection connection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/poisepms?useSSL=false",
				"otheruser",
				"swordfish"
				);
			// Create a direct line to the database for running queries
			Statement statement = connection.createStatement();
			ResultSet results;
			// executeQuery: runs a SELECT statement and returns the results.
			String input = "SELECT * FROM Assignees WHERE P_NUM =" + projectNumber + " AND P_ROLE = 'Contractor'";
			results = statement.executeQuery(input);
			// get persons from database
			String P_ROLE, NAME, NUMBR, EMAIL, ADDR;

			while (results.next()) {
				P_ROLE = results.getString(2);
				NAME = results.getString(3);
				NUMBR = results.getString(4);
				EMAIL = results.getString(5);
				ADDR = results.getString(6);
							
				currContractor = new Persons(P_ROLE, NAME, NUMBR, EMAIL, ADDR);
			}
			// Close connections
			results.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			//catch a SQLException 
			e.printStackTrace();
		}
		return currContractor; 
	}
	
	// this method gets and returns the customer/contact for a given project number
	public static Persons selectContact(String projectNumber) throws IOException {
		Persons currContact = null;
		
		try {
			// Connect to the poisepms database, via the jdbc:mysql:
			Connection connection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/poisepms?useSSL=false",
				"otheruser",
				"swordfish"
				);
			// Create a direct line to the database for running queries
			Statement statement = connection.createStatement();
			ResultSet results;
			// executeQuery: runs a SELECT statement and returns the results.
			String input = "SELECT * FROM Assignees WHERE P_NUM =" + projectNumber + " AND P_ROLE = 'Contact'";
			results = statement.executeQuery(input);
			// get persons/assignees from database
			String P_ROLE, NAME, NUMBR, EMAIL, ADDR;

			while (results.next()) {
				P_ROLE = results.getString(2);
				NAME = results.getString(3);
				NUMBR = results.getString(4);
				EMAIL = results.getString(5);
				ADDR = results.getString(6);
							
				currContact = new Persons(P_ROLE, NAME, NUMBR, EMAIL, ADDR);
			}
			// Close connections
			results.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			//catch a SQLException 
			e.printStackTrace();
		}
		return currContact; 
	}
	
	// this method gets the balance customer is still owing
	public static Double getBalance(String projectNum) throws IOException {
		Double P_BAL = 0.00;
		
		try {
			// Connect to the poisepms database, via the jdbc:mysql:
			Connection connection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/poisepms?useSSL=false",
				"otheruser",
				"swordfish"
				);
			// Create a direct line to the database for running queries
			Statement statement = connection.createStatement();
			ResultSet results;
			// executeQuery: runs a SELECT statement and returns the results.
			String input = "SELECT * FROM projects WHERE P_NUM =" + projectNum;
			results = statement.executeQuery(input);
			
			// get info from database	
			while (results.next()) {
				P_BAL = results.getDouble(7);
			}
			// Close connections
			results.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			//catch a SQLException 
			e.printStackTrace();
		}
		return P_BAL; 
	}
	
	// this method prints all projects
	public static void printAllFromTable(Statement statement) throws SQLException {
		ResultSet results = statement.executeQuery("SELECT * FROM projects");
			while (results.next()) {
				System.out.println(
				results.getInt("P_NUM") + ", "
				+ results.getString("P_NAME") + ", "
				+ results.getString("P_TYPE") + ", "
				+ results.getString("P_ADDR") + ", "
				+ results.getString("ERF_NUM") + ", "
				+ results.getDouble("P_FEE") + ", "
				+ results.getDouble("P_BAL") + ", "
				+ results.getString("DUE_DATE") 
			);
		}
	}
	
	// this method prints all overdue projects
	public static void printOverdueFromTable(Statement statement) throws SQLException {
		LocalDate today = LocalDate.now();
		ResultSet results = statement.executeQuery("SELECT * FROM projects WHERE DUE_DATE < now()");
			while (results.next()) {
				System.out.println(
				results.getInt("P_NUM") + ", "
				+ results.getString("P_NAME") + ", "
				+ results.getString("P_TYPE") + ", "
				+ results.getString("P_ADDR") + ", "
				+ results.getString("ERF_NUM") + ", "
				+ results.getDouble("P_FEE") + ", "
				+ results.getDouble("P_BAL") + ", "
				+ results.getString("DUE_DATE") 
			);
		}
	}
	
}