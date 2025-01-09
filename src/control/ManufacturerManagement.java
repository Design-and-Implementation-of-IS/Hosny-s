package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Consts;
import entity.Manufacturer;

public class ManufacturerManagement {
	
//	public void addManufacturer(Manufacturer manufacturer) {
//	try {
//		 Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
//		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR); ) {
//			
//			String query = "INSERT INTO Manufacturer (manufacturerNum, fullName, phoneNumber, address, email) VALUES (?, ?, ?, ?, ?)";
//            PreparedStatement ps = conn.prepareStatement(query);
//            ps.setInt(1, manufacturer.getManufacturerNumber());
//            ps.setString(2, manufacturer.getFullName());
//            ps.setString(3, manufacturer.getPhoneNumber());
//            ps.setString(4, manufacturer.getAddress());
//            ps.setString(5, manufacturer.getEmail());
//            ps.executeUpdate();
//		  
//		 }
//		
//		catch (SQLException e) {e.printStackTrace();}
//		} catch (ClassNotFoundException e) {e.printStackTrace();}
//	}
	
	public void addManufacturer(Manufacturer manufacturer) {
	    try {
	        Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
	        try (Connection conn = DriverManager.getConnection(Consts.CONN_STR)) {
	            String query = "INSERT INTO Manufacturer (manufacturerNum, fullName, phoneNumber, address, email) VALUES (?, ?, ?, ?, ?)";
	            try (PreparedStatement ps = conn.prepareStatement(query)) {
	                ps.setInt(1, manufacturer.getManufacturerNumber());
	                ps.setString(2, manufacturer.getFullName());
	                ps.setString(3, manufacturer.getPhoneNumber());
	                ps.setString(4, manufacturer.getAddress());
	                ps.setString(5, manufacturer.getEmail());
	                ps.executeUpdate();
	            }
	        }
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    }
	}

		

	
	 public void deleteManufacturer(int manufacturerNumber) {
		 
		 try {
			 Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR); ) {
				 String query = "DELETE FROM Manufacturer WHERE manufacturerNum = ?";
		            PreparedStatement ps = conn.prepareStatement(query);
		            ps.setInt(1, manufacturerNumber);
		            ps.executeUpdate();
			 }
			
			catch (SQLException e) {e.printStackTrace();}
			} catch (ClassNotFoundException e) {e.printStackTrace();}
}
	
	public List<Manufacturer> getAllManufacturers() {
        List<Manufacturer> manufacturers = new ArrayList<>();
	try {
		 Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR); ) {
			String query = "SELECT * FROM Manufacturer";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                manufacturers.add(new Manufacturer(
                        rs.getInt("manufacturerNum"),
                        rs.getString("fullName"),
                        rs.getString("phoneNumber"),
                        rs.getString("address"),
                        rs.getString("email")
                ));
		 }
		}
		catch (SQLException e) {e.printStackTrace();}
		} catch (ClassNotFoundException e) {e.printStackTrace();}
	return manufacturers;
}

	
	public void updateManufacturer(Manufacturer manufacturer) {
	
		try {
			 Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR); ) {
				String query = "UPDATE Manufacturer SET fullName = ?, phoneNumber = ?, address = ?, email = ? WHERE manufacturerNum = ?";
	            PreparedStatement ps = conn.prepareStatement(query);
	            ps.setString(1, manufacturer.getFullName());
	            ps.setString(2, manufacturer.getPhoneNumber());
	            ps.setString(3, manufacturer.getAddress());
	            ps.setString(4, manufacturer.getEmail());
	            ps.setInt(5, manufacturer.getManufacturerNumber());
	            ps.executeUpdate();
			 }
			
			catch (SQLException e) {e.printStackTrace();}
			} catch (ClassNotFoundException e) {e.printStackTrace();}
	}
	
	public int getNextManufacturerNumber() {
	    int nextNumber = 1; // Default to 1 if no manufacturers exist
	    try {
	        Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
	        try (Connection conn = DriverManager.getConnection(Consts.CONN_STR)) {
	            String query = "SELECT MAX(manufacturerNum) AS maxNum FROM Manufacturer";
	            try (PreparedStatement ps = conn.prepareStatement(query);
	                 ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    nextNumber = rs.getInt("maxNum") + 1;
	                }
	            }
	        }
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    }
	    return nextNumber;
	}


	}

