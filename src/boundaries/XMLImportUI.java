package boundaries;

import org.w3c.dom.*;

import control.ManufacturerManagement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.File;

public class XMLImportUI extends JPanel {

    private JTable dataTable;
    private DefaultTableModel tableModel;

    public XMLImportUI(File xmlFile, ManufacturerManagement manufacturerManagement) {
        this.setLayout(new BorderLayout());

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton loadXMLButton = new JButton("Load XML Data");
        loadXMLButton.addActionListener(e -> loadXMLData());
        JButton importButton = new JButton("Import Selected Data");
        importButton.addActionListener(e -> importSelectedData());

        buttonPanel.add(loadXMLButton);
        buttonPanel.add(importButton);

        this.add(buttonPanel, BorderLayout.NORTH);

        // Table Setup
        String[] columnNames = {"Select", "Type", "ID", "Name", "Details"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0; // Only the checkbox column is editable
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return Boolean.class; // Checkbox column
                }
                return super.getColumnClass(columnIndex);
            }
        };
        dataTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(dataTable);
        scrollPane.setPreferredSize(new Dimension(800, 400));

        this.add(scrollPane, BorderLayout.CENTER);
    }


	public XMLImportUI() {
		// TODO Auto-generated constructor stub
	}


	private void loadXMLData() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File xmlFile = fileChooser.getSelectedFile();

                // Parse XML File
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(xmlFile);
                document.getDocumentElement().normalize();

                // Clear existing rows
                tableModel.setRowCount(0);

                // Process Manufacturers
                NodeList manufacturerNodes = document.getElementsByTagName("Manufacturer");
                for (int i = 0; i < manufacturerNodes.getLength(); i++) {
                    Node node = manufacturerNodes.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;
                        String id = element.getElementsByTagName("ManufacturerNumber").item(0).getTextContent();
                        String name = element.getElementsByTagName("Name").item(0).getTextContent();
                        String details = "Location: " + element.getElementsByTagName("Location").item(0).getTextContent() + ", Established: " + element.getElementsByTagName("EstablishedYear").item(0).getTextContent();
                        tableModel.addRow(new Object[]{false, "Manufacturer", id, name, details});
                    }
                }

                // Process Wines
                NodeList wineNodes = document.getElementsByTagName("Wine");
                for (int i = 0; i < wineNodes.getLength(); i++) {
                    Node node = wineNodes.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;
                        String id = element.getElementsByTagName("CatalogNumber").item(0).getTextContent();
                        String name = element.getElementsByTagName("Name").item(0).getTextContent();
                        String details = "Year: " + element.getElementsByTagName("ProductionYear").item(0).getTextContent() + ", Price: $" + element.getElementsByTagName("PricePerBottle").item(0).getTextContent();
                        tableModel.addRow(new Object[]{false, "Wine", id, name, details});
                    }
                }

                JOptionPane.showMessageDialog(this, "XML Data Loaded Successfully!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading XML: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void importSelectedData() {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Boolean isSelected = (Boolean) tableModel.getValueAt(i, 0);
            if (isSelected != null && isSelected) {
                String type = (String) tableModel.getValueAt(i, 1);
                String id = (String) tableModel.getValueAt(i, 2);
                String name = (String) tableModel.getValueAt(i, 3);
                String details = (String) tableModel.getValueAt(i, 4);

                // Perform the import logic (for example, store the data in the system)
                System.out.println("Imported: " + type + " - ID: " + id + ", Name: " + name + ", Details: " + details);
            }
        }

        JOptionPane.showMessageDialog(this, "Selected Data Imported Successfully!");
    }
}


