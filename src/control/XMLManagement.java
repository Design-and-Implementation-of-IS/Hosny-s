package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import entity.Consts;
import entity.Manufacturer;
import entity.Wine;

public class XMLManagement {

    public void importDataFromXML(File xmlFile) {
    	
    	
    	
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Element root = builder.parse(xmlFile).getDocumentElement();

            // Process Manufacturers
            List<Element> manufacturers = getElementsByTagName(root, "Manufacturer");
            for (Element manufacturerElement : manufacturers) {
                Manufacturer manufacturer = parseManufacturer(manufacturerElement);
                addOrUpdateManufacturer(manufacturer);

                // Process Wines
                List<Element> wines = getElementsByTagName(manufacturerElement, "Wine");
                for (Element wineElement : wines) {
                    Wine wine = parseWine(wineElement, manufacturer.getManufacturerNumber());
                    addOrUpdateWine(wine);
                }
            }

            System.out.println("XML data imported successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Manufacturer parseManufacturer(Element element) {
        int manufacturerNumber = Integer.parseInt(getTextContent(element, "ManufacturerNumber"));
        String fullName = getTextContent(element, "FullName");
        String phoneNumber = getTextContent(element, "PhoneNumber");
        String address = getTextContent(element, "Address");
        String email = getTextContent(element, "Email");

        return new Manufacturer(manufacturerNumber, fullName, phoneNumber, address, email);
    }

    private Wine parseWine(Element element, int manufacturerNumber) {
        int catalogNumber = Integer.parseInt(getTextContent(element, "CatalogNumber"));
        String name = getTextContent(element, "Name");
        String pairingDish = getTextContent(element, "PairingDish");
        String description = getTextContent(element, "Description");
        int productionYear = Integer.parseInt(getTextContent(element, "ProductionYear"));
        double pricePerBottle = Double.parseDouble(getTextContent(element, "PricePerBottle"));
        String sweetnessLevel = getTextContent(element, "SweetnessLevel");
        String occasions = getTextContent(element, "Occasions");

        return new Wine(manufacturerNumber, catalogNumber, name, pairingDish, description, productionYear, pricePerBottle, sweetnessLevel, occasions);
    }

    private void addOrUpdateManufacturer(Manufacturer manufacturer) {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            try (Connection conn = DriverManager.getConnection(Consts.CONN_STR)) {
                if (manufacturerExists(conn, manufacturer.getManufacturerNumber())) {
                    updateManufacturer(conn, manufacturer);
                } else {
                    addManufacturer(conn, manufacturer);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean manufacturerExists(Connection conn, int manufacturerNumber) throws SQLException {
        String query = "SELECT COUNT(*) FROM Manufacturer WHERE manufacturerNum = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, manufacturerNumber);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
    }

    private void addManufacturer(Connection conn, Manufacturer manufacturer) throws SQLException {
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

    private void updateManufacturer(Connection conn, Manufacturer manufacturer) throws SQLException {
        String query = "UPDATE Manufacturer SET fullName = ?, phoneNumber = ?, address = ?, email = ? WHERE manufacturerNum = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, manufacturer.getFullName());
            ps.setString(2, manufacturer.getPhoneNumber());
            ps.setString(3, manufacturer.getAddress());
            ps.setString(4, manufacturer.getEmail());
            ps.setInt(5, manufacturer.getManufacturerNumber());
            ps.executeUpdate();
        }
    }

    private void addOrUpdateWine(Wine wine) {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            try (Connection conn = DriverManager.getConnection(Consts.CONN_STR)) {
                if (wineExists(conn, wine.getCatalogNumber())) {
                    updateWine(conn, wine);
                } else {
                    addWine(conn, wine);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean wineExists(Connection conn, int catalogNumber) throws SQLException {
        String query = "SELECT COUNT(*) FROM Wines WHERE catalogNumber = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, catalogNumber);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
    }

    private void addWine(Connection conn, Wine wine) throws SQLException {
        String query = "INSERT INTO Wines (manufacturerNumber, catalogNumber, name, pairingDish, wineDesc, productionYear, pricePerBottle, sweetnessLevel, occasions) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
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
    }

    private void updateWine(Connection conn, Wine wine) throws SQLException {
        String query = "UPDATE Wines SET name = ?, pairingDish = ?, wineDesc = ?, productionYear = ?, pricePerBottle = ?, sweetnessLevel = ?, occasions = ? WHERE catalogNumber = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, wine.getName());
            ps.setString(2, wine.getPairingDish());
            ps.setString(3, wine.getWineDesc());
            ps.setInt(4, wine.getProductionYear());
            ps.setDouble(5, wine.getPricePerBottle());
            ps.setString(6, wine.getSweetnessLevel());
            ps.setString(7, wine.getOccasions());
            ps.setInt(8, wine.getCatalogNumber());
            ps.executeUpdate();
        }
    }

    private String getTextContent(Element parent, String tagName) {
        NodeList list = parent.getElementsByTagName(tagName);
        if (list != null && list.getLength() > 0) {
            return list.item(0).getTextContent();
        }
        return "";
    }

    private List<Element> getElementsByTagName(Element parent, String tagName) {
        NodeList list = parent.getElementsByTagName(tagName);
        List<Element> elements = new ArrayList<>();
        for (int i = 0; i < list.getLength(); i++) {
            if (list.item(i) instanceof Element) {
                elements.add((Element) list.item(i));
            }
        }
        return elements;
    }
}
