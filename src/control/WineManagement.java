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
import entity.Wine;

public class WineManagement {
	
	public void addWine(Wine wine) {
	try {
		 Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR); ) {
		 
			String query = "INSERT INTO Wines (manufacturerNumber, catalogNumber, name, pairingDish, wineDesc, productionYear, pricePerBottle, sweetnessLevel, occasions) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
     PreparedStatement ps = conn.prepareStatement(query);
     ps.setInt(1, wine.getManufacturerNumber());
     ps.setInt(2, wine.getCatalogNumber());
     ps.setString(3, wine.getName());
     ps.setString(4, wine.getPairingDish());
     ps.setString(5, wine.getWineDesc());
     ps.setInt(6, wine.getProductionYear());
     ps.setDouble(7, wine.getPricePerBottle());
     ps.setString(8, wine.getSweetnessLevel());
     ps.setString(9, wine.getOccasions());
     ps.executeUpdate();
		 }
		
		catch (SQLException e) {e.printStackTrace();}
		} catch (ClassNotFoundException e) {e.printStackTrace();}
	}

	
	


    public List<Wine> getAllWines() {
    	List<Wine> wines = new ArrayList<>();
    	try {
    		 Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
    		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR); ) {
    			String query = "SELECT * FROM Wine";
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    wines.add(new Wine(
                            rs.getInt("manufacturerNumber"),
                            rs.getInt("catalogNumber"),
                            rs.getString("name"),
                            rs.getString("pairingDish"),
                            rs.getString("wineDesc"),
                            rs.getInt("productionYear"),
                            rs.getDouble("pricePerBottle"),
                            rs.getString("sweetnessLevel"),
                            rs.getString("occasions")
                           
                    ));
                }
    		 
    		 }
    		catch (SQLException e) {e.printStackTrace();}
    		} catch (ClassNotFoundException e) {e.printStackTrace();}
		return wines;
    }

    
    public int getNextCatalogNumber() {
        int nextCatalogNumber = 0;
        try {
        	 Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        	try (Connection conn = DriverManager.getConnection(Consts.CONN_STR); ) {
        	 
        		String query = "SELECT MAX(catalogNumber) AS maxCatalog FROM Wines";
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    nextCatalogNumber = rs.getInt("maxCatalog") + 1;
                } else {
                    nextCatalogNumber = 1; // Default starting catalog number if no records exist
                }
        		
        	 
        	}
        	catch (SQLException e) {e.printStackTrace();}
        	} catch (ClassNotFoundException e) {e.printStackTrace();}
        	
        return nextCatalogNumber;
    }
    
    public void deleteWine(int catalogNumber) {
    	try {
    		 Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
    		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR); ) {
    			String query = "DELETE FROM Wines WHERE catalogNumber = ?";
    	        PreparedStatement ps = conn.prepareStatement(query);
    	        ps.setInt(1, catalogNumber);
    	        ps.executeUpdate();
    		 }
    		
    		catch (SQLException e) {e.printStackTrace();}
    		} catch (ClassNotFoundException e) {e.printStackTrace();}
    }
    
    public List<Wine> getWinesByManufacturer(int manufacturerNumber) {
        List<Wine> wines = new ArrayList<>();
        try {
        	 Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        	try (Connection conn = DriverManager.getConnection(Consts.CONN_STR); ) {
        		String query = "SELECT * FROM Wines WHERE manufacturerNumber = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setInt(1, manufacturerNumber);
                System.out.println("Executing query: " + query + " with manufacturerNumber=" + manufacturerNumber);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    System.out.println("Wine found: " + rs.getString("name")); // Debug output
                    wines.add(new Wine(
                        rs.getInt("manufacturerNumber"),
                        rs.getInt("catalogNumber"),
                        rs.getString("name"),
                        rs.getString("pairingDish"),
                        rs.getString("wineDesc"),
                        rs.getInt("productionYear"),
                        rs.getDouble("pricePerBottle"),
                        rs.getString("sweetnessLevel"),
                        rs.getString("occasions")
                    ));
        	 }
        	}
        	catch (SQLException e) {e.printStackTrace();}
        	} catch (ClassNotFoundException e) {e.printStackTrace();}
        return wines;
    }
    
    public void updateWinePrice(int catalogNumber, double newPrice) {
    
    	try {
    		 Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
    		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR); ) {
    		 
    			String query = "UPDATE Wine SET pricePerBottle = ? WHERE catalogNumber = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setDouble(1, newPrice);
                ps.setInt(2, catalogNumber);
                ps.executeUpdate();
    		}
    		
    		catch (SQLException e) {e.printStackTrace();}
    		} catch (ClassNotFoundException e) {e.printStackTrace();}
    }
    		
  
    public void updateWine(Wine wine) {
    
    	try {
    		 Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
    		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR); ) {
    			String query = "UPDATE Wines SET name = ?, pairingDish = ?, wineDesc = ?, productionYear = ?, pricePerBottle = ?, sweetnessLevel = ?, occasions = ? WHERE catalogNumber = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, wine.getName());
                ps.setString(2, wine.getPairingDish());
                ps.setString(3, wine.getWineDesc());
                ps.setInt(4, wine.getProductionYear());
                ps.setDouble(5, wine.getPricePerBottle());
                ps.setString(6, wine.getSweetnessLevel());
                ps.setString(7, wine.getOccasions());
                ps.setInt(8, wine.getCatalogNumber());

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Wine updated successfully.");
                } else {
                    System.out.println("No wine was updated. Please check the catalog number.");
    		 }
    		}
    		catch (SQLException e) {e.printStackTrace();}
    		} catch (ClassNotFoundException e) {e.printStackTrace();}
    	
    }
    
    
}
