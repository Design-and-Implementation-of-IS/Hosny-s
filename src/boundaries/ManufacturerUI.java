//package boundaries;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.ArrayList;
//import java.util.List;
//
//import control.ManufacturerManagement;
//import control.WineManagement;
//import entity.Manufacturer;
//
//public class ManufacturerUI extends JFrame {
//    private ManufacturerManagement manufacturerManagement;
//    private WineInManufacturerUI wineInManufacturerUI;
//    private List<Manufacturer> manufacturers;
//    private int currentIndex = 0;
//
//    private JTextField manufacturerNumberField;
//    private JTextField fullNameField;
//    private JTextField phoneNumberField;
//    private JTextField addressField;
//    private JTextField emailField;
//
//    private JButton firstButton;
//    private JButton previousButton;
//    private JButton nextButton;
//    private JButton lastButton;
//
//    public ManufacturerUI(ManufacturerManagement manufacturerManagement, WineManagement wineManagement) {
//        this.manufacturerManagement = manufacturerManagement;
//        this.wineInManufacturerUI = new WineInManufacturerUI(wineManagement);
//        this.manufacturers = manufacturerManagement.getAllManufacturers();
//
//        if (this.manufacturers == null) {
//            this.manufacturers = new ArrayList<>(); // Ensure the list is not null
//        }
//
//        setTitle("Manufacturer Management");
//        setSize(1000, 700);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        getContentPane().setLayout(new BorderLayout());
//
//        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
//        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
//
//        // Add labels and text fields for each attribute
//        JLabel manufacturerNumberLabel = new JLabel("Manufacturer Number:");
//        manufacturerNumberField = new JTextField();
//        manufacturerNumberField.setEditable(false);
//        formPanel.add(manufacturerNumberLabel);
//        formPanel.add(manufacturerNumberField);
//
//        JLabel fullNameLabel = new JLabel("Full Name:");
//        fullNameField = new JTextField();
//        formPanel.add(fullNameLabel);
//        formPanel.add(fullNameField);
//
//        JLabel phoneNumberLabel = new JLabel("Phone Number:");
//        phoneNumberField = new JTextField();
//        formPanel.add(phoneNumberLabel);
//        formPanel.add(phoneNumberField);
//
//        JLabel addressLabel = new JLabel("Address:");
//        addressField = new JTextField();
//        formPanel.add(addressLabel);
//        formPanel.add(addressField);
//
//        JLabel emailLabel = new JLabel("Email:");
//        emailField = new JTextField();
//        formPanel.add(emailLabel);
//        formPanel.add(emailField);
//
//        getContentPane().add(formPanel, BorderLayout.NORTH);
//
//        // Button panel for manufacturer navigation
//        JPanel buttonPanel = new JPanel();
//
//        firstButton = new JButton("|<");
//        firstButton.setBounds(166, 15, 63, 23);
//        firstButton.addActionListener(e -> {
//            currentIndex = 0;
//            updateFields();
//            updateButtonStates();
//        });
//        buttonPanel.setLayout(null);
//        buttonPanel.add(firstButton);
//
//        previousButton = new JButton("<<");
//        previousButton.setBounds(239, 15, 63, 23);
//        previousButton.addActionListener(e -> {
//            if (currentIndex > 0) {
//                currentIndex--;
//                updateFields();
//                updateButtonStates();
//            }
//        });
//        buttonPanel.add(previousButton);
//
//        JButton addButton = new JButton("Add");
//        addButton.setBounds(312, 15, 76, 23);
//        addButton.addActionListener(e -> {
//            clearFields();
//            manufacturerNumberField.setText("Auto-generated");
//            currentIndex = manufacturers.size(); // Set index to the end for a new entry
//            updateButtonStates();
//        });
//        buttonPanel.add(addButton);
//
//        JButton deleteButton = new JButton("Delete");
//        deleteButton.setBounds(398, 15, 78, 23);
//        deleteButton.addActionListener(e -> {
//            if (!manufacturers.isEmpty() && currentIndex < manufacturers.size()) {
//                Manufacturer toDelete = manufacturers.get(currentIndex);
//                manufacturerManagement.deleteManufacturer(toDelete.getManufacturerNumber());
//                manufacturers.remove(currentIndex);
//                if (currentIndex > 0) {
//                    currentIndex--;
//                }
//                updateFields();
//                updateButtonStates();
//                JOptionPane.showMessageDialog(null, "Manufacturer deleted successfully!");
//            }
//        });
//        buttonPanel.add(deleteButton);
//
//        JButton saveButton = new JButton("Save");
//        saveButton.setBounds(486, 15, 76, 23);
//        saveButton.addActionListener(e -> {
//            Manufacturer newManufacturer = new Manufacturer(
//                    currentIndex < manufacturers.size() ? manufacturers.get(currentIndex).getManufacturerNumber() : manufacturers.size() + 1,
//                    fullNameField.getText(),
//                    phoneNumberField.getText(),
//                    addressField.getText(),
//                    emailField.getText()
//            );
//
//            if (currentIndex < manufacturers.size()) {
//                manufacturerManagement.updateManufacturer(newManufacturer);
//                manufacturers.set(currentIndex, newManufacturer);
//                JOptionPane.showMessageDialog(null, "Manufacturer updated successfully!");
//            } else {
//                manufacturerManagement.addManufacturer(newManufacturer);
//                manufacturers.add(newManufacturer);
//                JOptionPane.showMessageDialog(null, "Manufacturer added successfully!");
//            }
//            updateFields();
//            updateButtonStates();
//        });
//        buttonPanel.add(saveButton);
//
//        nextButton = new JButton(">>");
//        nextButton.setBounds(568, 15, 63, 23);
//        nextButton.addActionListener(e -> {
//            if (currentIndex < manufacturers.size() - 1) {
//                currentIndex++;
//                updateFields();
//                updateButtonStates();
//            }
//        });
//        buttonPanel.add(nextButton);
//
//        lastButton = new JButton(">|");
//        lastButton.setBounds(640, 15, 63, 23);
//        lastButton.addActionListener(e -> {
//            currentIndex = manufacturers.size() - 1;
//            updateFields();
//            updateButtonStates();
//        });
//        buttonPanel.add(lastButton);
//
//        getContentPane().add(buttonPanel, BorderLayout.CENTER);
//
//        // Add wine panel
//        getContentPane().add(wineInManufacturerUI, BorderLayout.SOUTH);
//
//        if (!manufacturers.isEmpty()) {
//            updateFields();
//        }
//        updateButtonStates();
//
//        setVisible(true);
//    }
//
//    private void updateFields() {
//        if (manufacturers.isEmpty() || currentIndex >= manufacturers.size()) {
//            clearFields();
//        } else {
//            Manufacturer manufacturer = manufacturers.get(currentIndex);
//            manufacturerNumberField.setText(String.valueOf(manufacturer.getManufacturerNumber()));
//            fullNameField.setText(manufacturer.getFullName());
//            phoneNumberField.setText(manufacturer.getPhoneNumber());
//            addressField.setText(manufacturer.getAddress());
//            emailField.setText(manufacturer.getEmail());
//
//            wineInManufacturerUI.updateWinePanel(manufacturer.getManufacturerNumber());
//        }
//    }
//
//    private void updateButtonStates() {
//        firstButton.setEnabled(currentIndex > 0);
//        previousButton.setEnabled(currentIndex > 0);
//        nextButton.setEnabled(currentIndex < manufacturers.size() - 1);
//        lastButton.setEnabled(currentIndex < manufacturers.size() - 1);
//
//        firstButton.setForeground(firstButton.isEnabled() ? Color.BLACK : Color.GRAY);
//        previousButton.setForeground(previousButton.isEnabled() ? Color.BLACK : Color.GRAY);
//        nextButton.setForeground(nextButton.isEnabled() ? Color.BLACK : Color.GRAY);
//        lastButton.setForeground(lastButton.isEnabled() ? Color.BLACK : Color.GRAY);
//    }
//
//    private void clearFields() {
//        manufacturerNumberField.setText("");
//        fullNameField.setText("");
//        phoneNumberField.setText("");
//        addressField.setText("");
//        emailField.setText("");
//        wineInManufacturerUI.updateWinePanel(-1); // Clear wine panel
//    }
//
//    public static void main(String[] args) {
//        ManufacturerManagement manufacturerManagement = new ManufacturerManagement();
//        WineManagement wineManagement = new WineManagement();
//        new ManufacturerUI(manufacturerManagement, wineManagement);
//    }
//}


package boundaries;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import control.ManufacturerManagement;
import control.WineManagement;
import control.XMLManagement;
import entity.Manufacturer;

public class ManufacturerUI extends JFrame {
    private ManufacturerManagement manufacturerManagement;
    private WineInManufacturerUI wineInManufacturerUI;
    private XMLManagement xmlManagement;
    private List<Manufacturer> manufacturers;
    private int currentIndex = 0;

    private JTextField manufacturerNumberField;
    private JTextField fullNameField;
    private JTextField phoneNumberField;
    private JTextField addressField;
    private JTextField emailField;

    private JButton firstButton;
    private JButton previousButton;
    private JButton nextButton;
    private JButton lastButton;

    public ManufacturerUI(ManufacturerManagement manufacturerManagement, WineManagement wineManagement) {
        this.manufacturerManagement = manufacturerManagement;
        this.wineInManufacturerUI = new WineInManufacturerUI(wineManagement);
        this.xmlManagement = new XMLManagement();
        this.manufacturers = manufacturerManagement.getAllManufacturers();

        if (this.manufacturers == null) {
            this.manufacturers = new ArrayList<>(); // Ensure the list is not null
        }

        setTitle("Manufacturer Management");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Add labels and text fields for each attribute
        JLabel manufacturerNumberLabel = new JLabel("Manufacturer Number:");
        manufacturerNumberField = new JTextField();
        manufacturerNumberField.setEditable(false);
        formPanel.add(manufacturerNumberLabel);
        formPanel.add(manufacturerNumberField);

        JLabel fullNameLabel = new JLabel("Full Name:");
        fullNameField = new JTextField();
        formPanel.add(fullNameLabel);
        formPanel.add(fullNameField);

        JLabel phoneNumberLabel = new JLabel("Phone Number:");
        phoneNumberField = new JTextField();
        formPanel.add(phoneNumberLabel);
        formPanel.add(phoneNumberField);

        JLabel addressLabel = new JLabel("Address:");
        addressField = new JTextField();
        formPanel.add(addressLabel);
        formPanel.add(addressField);

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        formPanel.add(emailLabel);
        formPanel.add(emailField);

        getContentPane().add(formPanel, BorderLayout.NORTH);

        // Button panel for manufacturer navigation
        JPanel buttonPanel = new JPanel();

        firstButton = new JButton("|<");
        firstButton.addActionListener(e -> {
            currentIndex = 0;
            updateFields();
            updateButtonStates();
        });
        buttonPanel.add(firstButton);

        previousButton = new JButton("<<");
        previousButton.addActionListener(e -> {
            if (currentIndex > 0) {
                currentIndex--;
                updateFields();
                updateButtonStates();
            }
        });
        buttonPanel.add(previousButton);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            clearFields();
            int newManufacturerNumber = manufacturerManagement.getNextManufacturerNumber();
            manufacturerNumberField.setText(String.valueOf(newManufacturerNumber));
            currentIndex = manufacturers.size(); // Set index to the end for a new entry
            updateButtonStates();
        });


        
        buttonPanel.add(addButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> {
            if (!manufacturers.isEmpty() && currentIndex < manufacturers.size()) {
                Manufacturer toDelete = manufacturers.get(currentIndex);
                manufacturerManagement.deleteManufacturer(toDelete.getManufacturerNumber());
                manufacturers.remove(currentIndex);
                if (currentIndex > 0) {
                    currentIndex--;
                }
                updateFields();
                updateButtonStates();
                JOptionPane.showMessageDialog(null, "Manufacturer deleted successfully!");
            }
        });
        buttonPanel.add(deleteButton);

        JButton saveButton = new JButton("Save");
//        saveButton.addActionListener(e -> {
//            Manufacturer newManufacturer = new Manufacturer(
//                    currentIndex < manufacturers.size() ? manufacturers.get(currentIndex).getManufacturerNumber() : manufacturers.size() + 1,
//                    fullNameField.getText(),
//                    phoneNumberField.getText(),
//                    addressField.getText(),
//                    emailField.getText()
//            );
//
//            if (currentIndex < manufacturers.size()) {
//                manufacturerManagement.updateManufacturer(newManufacturer);
//                manufacturers.set(currentIndex, newManufacturer);
//                JOptionPane.showMessageDialog(null, "Manufacturer updated successfully!");
//            } else {
//                manufacturerManagement.addManufacturer(newManufacturer);
//                manufacturers.add(newManufacturer);
//                JOptionPane.showMessageDialog(null, "Manufacturer added successfully!");
//            }
//            updateFields();
//            updateButtonStates();
//        });
        saveButton.addActionListener(e -> {
            Manufacturer newManufacturer = new Manufacturer(
                    Integer.parseInt(manufacturerNumberField.getText()),
                    fullNameField.getText(),
                    phoneNumberField.getText(),
                    addressField.getText(),
                    emailField.getText()
            );

            if (currentIndex < manufacturers.size()) {
                manufacturerManagement.updateManufacturer(newManufacturer);
                manufacturers.set(currentIndex, newManufacturer);
                JOptionPane.showMessageDialog(null, "Manufacturer updated successfully!");
            } else {
                manufacturerManagement.addManufacturer(newManufacturer);
                manufacturers.add(newManufacturer);
                JOptionPane.showMessageDialog(null, "Manufacturer added successfully!");
            }
            manufacturers = manufacturerManagement.getAllManufacturers(); // Refresh data
            updateFields();
            updateButtonStates();
        });

        buttonPanel.add(saveButton);

        nextButton = new JButton(">>");
        nextButton.addActionListener(e -> {
            if (currentIndex < manufacturers.size() - 1) {
                currentIndex++;
                updateFields();
                updateButtonStates();
            }
        });
        buttonPanel.add(nextButton);

        lastButton = new JButton("|>");
        lastButton.addActionListener(e -> {
            currentIndex = manufacturers.size() - 1;
            updateFields();
            updateButtonStates();
        });
        buttonPanel.add(lastButton);

        JButton importXMLButton = new JButton("Import XML");
        importXMLButton.addActionListener(e -> openXMLFileChooser());
        buttonPanel.add(importXMLButton);

        getContentPane().add(buttonPanel, BorderLayout.CENTER);

        // Add wine panel
        getContentPane().add(wineInManufacturerUI, BorderLayout.SOUTH);

        if (!manufacturers.isEmpty()) {
            updateFields();
        }
        updateButtonStates();

        setVisible(true);
    }

    private void openXMLFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            xmlManagement.importDataFromXML(selectedFile);
            manufacturers = manufacturerManagement.getAllManufacturers();
            updateFields();
            updateButtonStates();
            JOptionPane.showMessageDialog(this, "XML Data Imported Successfully!");
        }
    }

    private void updateFields() {
        if (manufacturers.isEmpty() || currentIndex >= manufacturers.size()) {
            clearFields();
        } else {
            Manufacturer manufacturer = manufacturers.get(currentIndex);
            manufacturerNumberField.setText(String.valueOf(manufacturer.getManufacturerNumber()));
            fullNameField.setText(manufacturer.getFullName());
            phoneNumberField.setText(manufacturer.getPhoneNumber());
            addressField.setText(manufacturer.getAddress());
            emailField.setText(manufacturer.getEmail());

            wineInManufacturerUI.updateWinePanel(manufacturer.getManufacturerNumber());
        }
    }

    private void updateButtonStates() {
        firstButton.setEnabled(currentIndex > 0);
        previousButton.setEnabled(currentIndex > 0);
        nextButton.setEnabled(currentIndex < manufacturers.size() - 1);
        lastButton.setEnabled(currentIndex < manufacturers.size() - 1);
    }

    private void clearFields() {
        manufacturerNumberField.setText("");
        fullNameField.setText("");
        phoneNumberField.setText("");
        addressField.setText("");
        emailField.setText("");
        wineInManufacturerUI.updateWinePanel(-1); // Clear wine panel
    }

    public static void main(String[] args) {
        ManufacturerManagement manufacturerManagement = new ManufacturerManagement();
        WineManagement wineManagement = new WineManagement();
        new ManufacturerUI(manufacturerManagement, wineManagement);
    }
}
