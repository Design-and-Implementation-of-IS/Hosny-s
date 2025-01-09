package entity;


import java.util.*;
import java.sql.*; // For database connectivity

// ENTITY: Manufacturer
public class Manufacturer {
    private int manufacturerNumber;
    private String fullName;
    private String phoneNumber;
    private String address;
    private String email;

    // Constructors, Getters, and Setters
    public Manufacturer(int manufacturerNumber, String fullName, String phoneNumber, String address, String email) {
        this.manufacturerNumber = manufacturerNumber;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
    }

	public int getManufacturerNumber() {
		return manufacturerNumber;
	}

	public void setManufacturerNumber(int manufacturerNumber) {
		this.manufacturerNumber = manufacturerNumber;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

    // Getters and Setters
    
    
}