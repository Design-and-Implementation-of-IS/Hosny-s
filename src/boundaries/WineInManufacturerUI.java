package boundaries;

import control.WineManagement;
import entity.Wine;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class WineInManufacturerUI extends JPanel {
    private WineManagement wineManagement;
    private JTable wineTable;
    private DefaultTableModel tableModel;
    private int manufacturerNumber;

    public WineInManufacturerUI(WineManagement wineManagement) {
        this.wineManagement = wineManagement;
        this.setLayout(new BorderLayout());

        // Buttons for adding, saving, and canceling edits
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addButton = new JButton("Add Wine");
        addButton.addActionListener(e -> addNewWine());
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveTableEdits());
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> updateWinePanel(manufacturerNumber));

        buttonPanel.add(addButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        this.add(buttonPanel, BorderLayout.NORTH);

        // Table setup
        String[] columnNames = {"Catalog Number", "Name", "Pairing Dish", "Description", "Production Year", "Price per Bottle", "Sweetness Level", "Occasions"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0; // Make all columns editable except Catalog Number
            }
        };
        wineTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(wineTable);
        scrollPane.setPreferredSize(new Dimension(800, 300)); // Adjusted size to make the panel smaller

        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void updateWinePanel(int manufacturerNumber) {
        this.manufacturerNumber = manufacturerNumber;
        tableModel.setRowCount(0); // Clear existing rows

        List<Wine> wines = wineManagement.getWinesByManufacturer(manufacturerNumber);
        if (wines != null) {
            for (Wine wine : wines) {
                Object[] rowData = {
                    wine.getCatalogNumber(),
                    wine.getName(),
                    wine.getPairingDish(),
                    wine.getWineDesc(),
                    wine.getProductionYear(),
                    wine.getPricePerBottle(),
                    wine.getSweetnessLevel(),
                    wine.getOccasions()
                };
                tableModel.addRow(rowData);
            }
        }
    }

    private void saveTableEdits() {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            int catalogNumber = (int) tableModel.getValueAt(i, 0);
            String name = (String) tableModel.getValueAt(i, 1);
            String pairingDish = (String) tableModel.getValueAt(i, 2);
            String wineDesc = (String) tableModel.getValueAt(i, 3);
            int productionYear = Integer.parseInt(tableModel.getValueAt(i, 4).toString());
            double pricePerBottle = Double.parseDouble(tableModel.getValueAt(i, 5).toString());
            String sweetnessLevel = (String) tableModel.getValueAt(i, 6);
            String occasions = (String) tableModel.getValueAt(i, 7);

            Wine updatedWine = new Wine(manufacturerNumber, catalogNumber, name, pairingDish, wineDesc, productionYear, pricePerBottle, sweetnessLevel, occasions);
            wineManagement.updateWine(updatedWine); // Update the database
        }

        JOptionPane.showMessageDialog(this, "All changes saved successfully to the database!");
        updateWinePanel(manufacturerNumber);
    }

    private void addNewWine() {
        // Automatically assign a new catalog number
        int newCatalogNumber = wineManagement.getNextCatalogNumber();

        // Add an empty row for the new wine
        Object[] newRow = {newCatalogNumber, "", "", "", 0, 0.0, "", ""};
        tableModel.addRow(newRow);
        wineTable.scrollRectToVisible(wineTable.getCellRect(tableModel.getRowCount() - 1, 0, true));

        // Immediately save the new wine to the database with default values
        Wine newWine = new Wine(manufacturerNumber, newCatalogNumber, "", "", "", 0, 0.0, "", "");
        wineManagement.addWine(newWine);
    }
}


