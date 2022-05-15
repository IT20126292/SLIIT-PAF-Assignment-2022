package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Bill {
	// Connect to the DB
	public Connection connect() {
		Connection con = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con= DriverManager.getConnection("jdbc:mysql://localhost:3306/electri?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "");

			// For testing
			System.out.print("Succesfully connected to the DB");
			
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return con;

	}

	         //Insert Bill
			public String insertBill(String billName, String billAmount, String billDate, String NoOfTunits, String BillAr)
			{ 
				Connection con = connect();
				String output = "";
				try
				 { 
					  
					 if (con == null) 
					 { 
					    return "Error while connecting to the database"; 
					 } 
					 
					 // create a prepared statement
					 String query = " insert into bill (`billID`,`billName`,`billAmount`,`billDate`,`NoOfTunits`,`BillAr`)"+ " values (?, ?, ?, ?, ?,?)"; 
					 PreparedStatement Pstatement = con.prepareStatement(query); 
					 
					 // binding values
					 Pstatement.setInt(1, 0); 
					 Pstatement.setString(2, billName); 
					 Pstatement.setString(3, billAmount); 
					 Pstatement.setString(4, billDate); 
					 Pstatement.setString(5, NoOfTunits);
					 Pstatement.setString(6, BillAr);
					 
					 
					//execute the statement
					 
					 Pstatement.execute(); 
					 con.close();
					// System.out.println(query);
					 String newBill = readBill(); 
					 output = "{\"status\":\"success\", \"data\": \"" + 
					 newBill + "\"}"; 
					 
					// output = "Bill Inserted successfully"; 
				 } 
				
				catch (Exception e) 
				 { 
					 output = "{\"status\":\"error\", \"data\": \"Error while inserting the Bill.\"}"; 
					// output = "Error while inserting"; 
					 
					System.err.println(e.getMessage()); 
				 } 
				//binding values
				return output; 
			}

			//Read the bill
			public String readBill()
			{ 
				 String output = ""; 
				 
				 try
				 { 
				
			     Connection con = connect(); 
				 if (con == null) 
				 { 
					 return "Error while connecting to the database for reading the Bills."; 
				 } 
				 
				 
				 // Prepare the html table to be displayed
				 output =  "<table border='1' class='table table-dark table-hover'>"
				 		 + "<tr><th>billName</th>"
				 		 + "<th>billAmount</th>"
						 + "<th>billDate</th>"
						 + "<th>NoOfTunits</th>"
						 + "<th>BillAr</th>" 
						 + "<th>Update</th>"
						 + "<th>Delete</th></tr>"; 
				 
				 String query = "select * from bill"; 
				 
				 Statement stmt = (Statement) con.createStatement(); 
				 ResultSet res = ((java.sql.Statement) stmt).executeQuery(query); 
				 
				 // iterate through the rows in the result set
				 while (res.next()) 
				 { 
					 String billID = Integer.toString(res.getInt("billID")); 
					 String billName = res.getString("billName"); 
					 String billAmount = res.getString("billAmount"); 
					 String billDate = res.getString("billDate"); 
					 String NoOfTunits = res.getString("NoOfTunits"); 
					 String BillAr = res.getString("BillAr");
					  
					 // Add a row into the html table
					 output += "<tr><td>" + billName + "</td>"; 
					 output += "<td>" + billAmount + "</td>"; 
					 output += "<td>" + billDate + "</td>";
					 output += "<td>" + NoOfTunits + "</td>"; 
					 output += "<td>" + BillAr + "</td>";
					 
					 				 
					 // buttons
					 output += "<td><input name='btnUpdate' type='button' value='Update' "
							 + "class='btnUpdate btn btn-secondary' data-billid='" + billID + "'></td>"
							 
							 + "<td><input name='btnRemove' type='button' value='Remove' "
							 + "class='btnRemove btn btn-danger' data-billid='" + billID + "'></td></tr>";
				 } 
				 
				con.close(); 
				
				     // Complete the html table
				     output += "</table>"; 
				 } 
				 
				catch (Exception e) 
				 { 
					 output = "Error while reading the bill details."; 
					 System.err.println(e.getMessage()); 
				 } 
				
				
				return output; 
			}

	// Update buyers in the table
	public String updateBill(String billID, String billName, String billAmount, String billDate, String NoOfTunits, String BillAr)
			{ 
				 String output = ""; 
				 try
				 { 
				 Connection con = connect(); 
				 if (con == null) 
				 {
					 return "Error while connecting to the database for updating the bill."; 
					 
				 } 
				 // create a prepared statement
				 String query = "UPDATE bill SET billName=?, billAmount=?, billDate=?, NoOfTunits=?, BillAr=? WHERE billID=?";
					
				 PreparedStatement preparedStmt = con.prepareStatement(query);
				 
				 // binding values
				 
				 preparedStmt.setString(1, billName); 
				 preparedStmt.setString(2, billAmount); 
				 preparedStmt.setString(3, billDate); 
				 preparedStmt.setString(4, NoOfTunits); 
				 preparedStmt.setString(5, BillAr); 
				 preparedStmt.setInt(6, Integer.parseInt(billID)); 
				 
				 
				 // execute the statement
				    preparedStmt.execute(); 
				    con.close(); 
				    String newBill = readBill(); 
					 output = "{\"status\":\"success\", \"data\": \"" + newBill + "\"}"; 
					 
				   // output = "Bill Updated successfully"; 
				 } 
				 
				 catch (Exception e) 
				 { 
					 output = "{\"status\":\"error\", \"data\": \"Error while Updating the Bill.\"}"; 
				     //output = "Error while updating the bill details."; 
				     System.err.println(e.getMessage()); 
				 } 
				 
				 return output; 
				 }

	// Delete buyer in the table
	public String deleteBill(String billID) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting the bill.";
			}

			// create a prepared statement
			String query = "delete from bill where billID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(billID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			  String newBill = readBill(); 
				 output = "{\"status\":\"success\", \"data\": \"" + 
				 newBill + "\"}"; 
		//	output = "bill details Deleted successfully";

		} catch (Exception e) {
			 output = "{\"status\":\"error\", \"data\": \"Error while Deleting the Bill.\"}"; 
		//	output = "Error while deleting the Bill details.";
			System.err.println(e.getMessage());
		}

		return output;
	}

}

