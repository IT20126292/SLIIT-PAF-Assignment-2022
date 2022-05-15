package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Customer {
		// Implement to Connect with DataBase
		public Connection connect() {
			Connection con = null;

			try {
				Class.forName("com.mysql.jdbc.Driver");
				con= DriverManager.getConnection("jdbc:mysql://localhost:3306/electri?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
												"root", "");
				// For testing purpose
				System.out.print("Succesfully connected to the DB");
				
			} catch (Exception e) {
				e.printStackTrace();
				
			}
			return con;

		}
		
		public String createNewCustomer(String cus_Name, String cus_Nic, String cus_addr, String cus_pnumber, String cus_username, String cus_pwd) { 
			Connection con = connect();
			String output = "";
			
			try	{ 
				  
				 if (con == null) { 
				    return "Error while connecting to the database"; 
				 } 
				 
				 // create a prepared statement
				 String query = " insert into customer (`cID`,`cus_Name`,`cus_Nic`,`cus_addr`,`cus_pnumber`,`cus_username`,`cus_pwd`)"+ " values (?, ?, ?, ?, ?, ?, ?)"; 
				 PreparedStatement Pstatement = con.prepareStatement(query); 
				 
				 // binding values
				 Pstatement.setInt(1, 0); 
				 Pstatement.setString(2, cus_Name); 
				 Pstatement.setString(3, cus_Nic); 
				 Pstatement.setString(4, cus_addr); 
				 Pstatement.setString(5, cus_pnumber);
				 Pstatement.setString(6, cus_username);
				 Pstatement.setString(7, cus_pwd);
				 
				//execute the statement
				 Pstatement.execute(); 
				 con.close();
		
				 String newCustomer = retrieveAllCustomer(); 
				 output = "{\"status\":\"success\", \"data\": \"" + newCustomer + "\"}"; 
				 
			} catch (Exception e) { 
				 output = "{\"status\":\"error\", \"data\": \"Error while inserting the Customer.\"}"; 
				 
				 System.err.println(e.getMessage()); 
			} 
			//binding values
			return output; 
		}
		
		//Read the bill
		public String retrieveAllCustomer() { 
			 String output = ""; 
			 
			 try { 
			     Connection con = connect(); 
			     
				 if (con == null) { 
					 return "Error while connecting to the database for reading the Bills."; 
				 } 
				 
				 
				 // Prepare the html table to be displayed
				 output =  "<table border='1' class='table table-dark table-hover'>"
				 		 + "<tr><th>Customer Name</th>"
				 		 + "<th>NIC</th>"
						 + "<th>Address</th>"
						 + "<th>Phone Number</th>"
						 + "<th>Username</th>"
						 + "<th>Password</th>"
						 + "<th>Update</th>"
						 + "<th>Delete</th></tr>"; 
				 
				 String query = "select * from customer"; 
				 
				 Statement stmt = (Statement) con.createStatement(); 
				 ResultSet res = ((java.sql.Statement) stmt).executeQuery(query); 
				 
				 // iterate through the rows in the result set
				 while (res.next()) { 
					 String cID = Integer.toString(res.getInt("cID")); 
					 String cus_Name = res.getString("cus_Name"); 
					 String cus_Nic = res.getString("cus_Nic"); 
					 String cus_addr = res.getString("cus_addr"); 
					 String cus_pnumber = res.getString("cus_pnumber"); 
					 String cus_username = res.getString("cus_username");
					 String cus_pwd = res.getString("cus_pwd");
					  
					 // Add a row into the html table
					 output += "<tr><td>" + cus_Name + "</td>"; 
					 output += "<td>" + cus_Nic + "</td>"; 
					 output += "<td>" + cus_addr + "</td>";
					 output += "<td>" + cus_pnumber + "</td>"; 
					 output += "<td>" + cus_username + "</td>";
					 output += "<td>" + cus_pwd + "</td>";
					 
					 				 
					 // buttons
					 output += "<td><input name='btnUpdate' type='button' value='Update' "
							 + "class='btnUpdate btn btn-secondary' data-customerid='" + cID + "'></td>"
							 
							 + "<td><input name='btnRemove' type='button' value='Remove' "
							 + "class='btnRemove btn btn-danger' data-customerid='" + cID + "'></td></tr>";
				 } 
				 
				 con.close(); 
				
				 // Complete the html table
				 output += "</table>";
				 
			 } catch (Exception e) { 
					 output = "Error while reading the customer details."; 
					 System.err.println(e.getMessage()); 
			 } 
			
			 return output; 
		}
		
		
		// Update buyers in the table
		public String updateCustomer(String cID, String cus_Name, String cus_Nic, String cus_addr, String cus_pnumber, String cus_username, String cus_pwd) { 
			String output = "";
			
			try { 
				Connection con = connect();
				
				if (con == null) {
					return "Error while connecting to the database for updating the bill."; 
						 
				} 
				// create a prepared statement
				String query = "UPDATE customer SET cus_Name=?, cus_Nic=?, cus_addr=?, cus_pnumber=?, cus_username=?, cus_pwd=? WHERE cID=?";
						
				PreparedStatement preparedStmt = con.prepareStatement(query);
					 
				// binding values
					 
				preparedStmt.setString(1, cus_Name); 
				preparedStmt.setString(2, cus_Nic); 
				preparedStmt.setString(3, cus_addr); 
				preparedStmt.setString(4, cus_pnumber); 
				preparedStmt.setString(5, cus_username);
				preparedStmt.setString(6, cus_pwd);
				preparedStmt.setInt(7, Integer.parseInt(cID)); 
					 
					 
				// execute the statement
				preparedStmt.execute(); 
				con.close(); 
				String updateCustomer = retrieveAllCustomer(); 
				output = "{\"status\":\"success\", \"data\": \"" + updateCustomer + "\"}"; 
						 
					   
			} catch (Exception e) { 
				output = "{\"status\":\"error\", \"data\": \"Error while Updating the Customer.\"}"; 
				System.err.println(e.getMessage()); 
			} 
					 
			return output; 
		}
		
		
		// Delete buyer in the table
		public String deleteCustomer(String cID) {
			String output = "";

			try {
				Connection con = connect();

				if (con == null) {
					return "Error while connecting to the database for deleting the bill.";
				}

				// create a prepared statement
				String query = "delete from customer where cID=?";
				PreparedStatement preparedStmt = con.prepareStatement(query);

				// binding values
				preparedStmt.setInt(1, Integer.parseInt(cID));

				// execute the statement
				preparedStmt.execute();
				con.close();

				String deleteCustomer = retrieveAllCustomer(); 
				output = "{\"status\":\"success\", \"data\": \"" + deleteCustomer + "\"}"; 
				

			} catch (Exception e) {
				output = "{\"status\":\"error\", \"data\": \"Error while Deleting the Bill.\"}"; 
				System.err.println(e.getMessage()); 
			}

			return output;
		}

}
