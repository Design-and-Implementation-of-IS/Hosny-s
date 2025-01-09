package entity;

import java.util.*;

// Entity: Wine
public class Wine {
    private int manufacturerNumber;
    private int catalogNumber;
    private String name;
    private String pairingDish;
    private String wineDesc;
    private int productionYear;
    private double pricePerBottle;
    private String sweetnessLevel;
    private String occasions;

    // Constructors, Getters, and Setters
    public Wine(int manufacturerNumber, int catalogNumber, String name, String pairingDish, String wineDesc,
                int productionYear, double pricePerBottle, String sweetnessLevel, String occasions) {
        this.manufacturerNumber = manufacturerNumber;
        this.catalogNumber = catalogNumber;
        this.name = name;
        this.pairingDish = pairingDish;
        this.wineDesc = wineDesc;
        this.productionYear = productionYear;
        this.pricePerBottle = pricePerBottle;
        this.sweetnessLevel = sweetnessLevel;
        this.occasions = occasions;
    }

	public int getManufacturerNumber() {
		return manufacturerNumber;
	}

	public void setManufacturerNumber(int manufacturerNumber) {
		this.manufacturerNumber = manufacturerNumber;
	}

	public int getCatalogNumber() {
		return catalogNumber;
	}

	public void setCatalogNumber(int catalogNumber) {
		this.catalogNumber = catalogNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPairingDish() {
		return pairingDish;
	}

	public void setPairingDish(String pairingDish) {
		this.pairingDish = pairingDish;
	}

	public String getWineDesc() {
		return wineDesc;
	}

	public void setWineDesc(String wineDesc) {
		this.wineDesc = wineDesc;
	}

	public int getProductionYear() {
		return productionYear;
	}

	public void setProductionYear(int productionYear) {
		this.productionYear = productionYear;
	}

	public double getPricePerBottle() {
		return pricePerBottle;
	}

	public void setPricePerBottle(double pricePerBottle) {
		this.pricePerBottle = pricePerBottle;
	}

	public String getSweetnessLevel() {
		return sweetnessLevel;
	}

	public void setSweetnessLevel(String sweetnessLevel) {
		this.sweetnessLevel = sweetnessLevel;
	}

	public String getOccasions() {
		return occasions;
	}

	public void setOccasions(String occasions) {
		this.occasions = occasions;
	}

	
    
}