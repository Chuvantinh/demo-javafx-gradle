package com.orders.vantinh.models;

import org.bson.types.ObjectId;

import java.util.List;

public class ModelProducts {
    private ObjectId ID;
    private final String SKU;
    private final String WPID;
    private  String productName;
    private  String productNameVN;
    private  String productDescription;
    private  String productShortDescription;
    private  String productImageUrl;
    private  Double productStock;

    private List<ModelUnit> units;

    public ModelProducts(ObjectId ID, String SKU, String WPID, String productName, String productNameVN,
                         String productDescription, String productShortDescription,
                         String productImageUrl, double productStock, List<ModelUnit> units) {
        this.ID = ID;
        this.SKU = SKU;
        this.WPID = WPID;
        this.productName = productName;
        this.productNameVN = productNameVN;
        this.productDescription = productDescription;
        this.productShortDescription = productShortDescription;
        this.productImageUrl = productImageUrl;
        this.productStock = productStock;
        this.units = units;
    }

    public ObjectId getId() {return ID;}
    public void setId(ObjectId ID) {this.ID = ID;}

    public String getSKU() {
        return SKU;
    }

    public String getWPID(){ return WPID;}

    public String getProductName() {return productName;}
    public void setProductName(String productName) {this.productName = productName;}

    public String getProductNameVN() {return productNameVN;}
    public void setProductNameVN( String ProductNameVN){this.productNameVN = ProductNameVN;}

    public String getProductDescription() {return productDescription;}
    public void setProductDescription(String productDescription) {this.productDescription = productDescription;}

    public String getProductShortDescription() {return productShortDescription;}
    public void setProductShortDescription(String productShortDescription) {this.productShortDescription = productShortDescription;}

    public String getProductImageUrl() {return productImageUrl;}
    public void setProductImageUrl(String productImageUrl) {this.productImageUrl = productImageUrl;}

    public Double getProductStock() {return productStock;}
    public void setProductStock( double productStock){ this.productStock = productStock;}

    // Method to create a formatted string for displaying units
    public String getUnitsDisplay() {
        StringBuilder display = new StringBuilder();
        for (ModelUnit unit : units) {
            display.append("Type: ").append(unit.getUnitType())
                    .append(", Description: ").append(unit.getUnitDescription())
                    .append(", Qty: ").append(unit.getQuantityInBaseUnit())
                    .append(", Regular Price: €").append(unit.getUnitRegularPricePrice());
            if (unit.getUnitSalePrice() != null) {
                display.append(", Sale Price: €").append(unit.getUnitSalePrice());
            }
            display.append(", Buy Price: €").append(unit.getUnitBuyPrice())
                    .append("\n");
        }
        return display.toString().trim(); // Remove the last newline
    }

    public List<ModelUnit> getUnits() {return units;}
    public void setUnits(List<ModelUnit> units) {this.units = units;}

}
