package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class Appointment {
	
	public Connection connect() {

		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Properties connectionProps = new Properties();
			connectionProps.put("user", "root");
			connectionProps.put("password", "");
			con = (Connection) DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/healthcare?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Colombo",
					connectionProps);
			// For testing
			System.out.print("Successfully connected");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String readAppointment() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Appointment ID</th><th>Discription</th><th>Patient ID</th>"
					+ "<th>Doctor ID</th><th>Hospital ID</th><th>Date & Time</th>"
					+ "<th>Chage</th><th>Update</th><th>Remove</th></tr>";

			String query = "select * from appoinment";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the rows in the result set
			while (rs.next()) 
			{
				String apt_ID = Integer.toString(rs.getInt("appointmentId"));
				String discription = rs.getString("appointmentDese");
				String patient_id = rs.getString("patientId");
				String doctor_id = rs.getString("doctorId");
				String hospital_id = rs.getString("hospitalId");
				String datetime = rs.getString("dateTime");
				String charge = Double.toString(rs.getDouble("charge"));
				
// Add into the html table
				
				output += "<tr><td><input id='hidItemIDUpdate'name='hidItemIDUpdate'type='hidden' value='" + apt_ID
						+ "'>" + discription + "</td>";
				output += "<td>" + patient_id + "</td>";
				output += "<td>" + doctor_id + "</td>";
				output += "<td>" + hospital_id + "</td>";
				output += "<td>" + datetime + "</td>";
				output += "<td>" + charge + "</td>";
// buttons
				output += "<td><input name='btnUpdate'type='button' value='Update'class='btnUpdate btn btn-secondary'></td>"
						+ "<td><input name='btnRemove'type='button' value='Remove'class='btnRemove btn btn-danger'data-apt_ID='"
						+ apt_ID + "'>" + "</td></tr>";
			}
			con.close();
			
// Complete the html table
			output += "</table>";
			
			}
				catch (Exception e) 
		{
			output = "Error while reading the appoinments.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String insertAppointment(String desc, String Pid, String Did, String Hid, String datetime, String charge) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
// create a prepared statement
			String query = " insert into appoinment(apt_ID,discription,patient_id,doctor_id,hospital_id,datetime,charge)"
					+ " values (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, desc);
			preparedStmt.setString(3, Pid);
			preparedStmt.setString(4, Did);
			preparedStmt.setString(5, Hid);
			preparedStmt.setString(6, datetime);
			preparedStmt.setDouble(7, Double.parseDouble(charge));
			
// execute the statement
			preparedStmt.executeUpdate();
			
			String newAppointments = readAppointment();
			output = "{\"status\":\"success\", \"data\": \"" + newAppointments + "\"}";
			
		} catch (Exception e) 
		{
			output = "{\"status\":\"error\", \"data\":\"Error while inserting the appoinment.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateAppointment(String ID, String desc, String Pid, String Did, String Hid, String datetime, String charge) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
// create a prepared statement
			String query = "UPDATE appoinment SET discription=?,patient_id=?,doctor_id=?,hospital_id=?,datetime=?,charge=? WHERE apt_ID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
// binding values
			preparedStmt.setString(1, desc);
			preparedStmt.setString(2, Pid);
			preparedStmt.setString(3, Did);
			preparedStmt.setString(4, Hid);
			preparedStmt.setString(5, datetime);
			preparedStmt.setDouble(6, Double.parseDouble(charge));
			preparedStmt.setInt(7, Integer.parseInt(ID));
			
// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newAppointment = readAppointment();
			output = "{\"status\":\"success\", \"data\": \"" + newAppointment + "\"}";
			
		} catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\":\"Error while updating the appoinments.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteAppointment(String apt_ID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

// create a prepared statement
			String query = "delete from appoinment where apt_ID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
// binding values
			preparedStmt.setInt(1, Integer.parseInt(apt_ID));
			
// execute the statement
			preparedStmt.execute();
			con.close();
			String newAppointment = readAppointment();
			
			output = "{\"status\":\"success\", \"data\": \"" + newAppointment + "\"}";
			}
				catch (Exception e)
		{
				output = "{\"status\":\"error\", \"data\":\"Error while deleting the Appointment.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public static void main(String[] args) {
		new Appointment().insertAppointment("sdf", "12", "10", "100", "2020-04-15 10:00:00", "123.23");
		//new Appointment().deleteAppointment("1");
	}


}
