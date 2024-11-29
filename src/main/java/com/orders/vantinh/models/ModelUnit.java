package com.orders.vantinh.models;

public class ModelUnit {
    private String unitType;
    private String unitDescription;
    private Double quantityInBaseUnit;
    private Double unitRegularPrice;
    private Double unitSalePrice;
    private Double unitBuyPrice;

    public ModelUnit(String unitType, String unitDescription, Double quantityInBaseUnit, Double unitRegularPrice, Double unitSalePrice, Double unitBuyPrice) {
        this.unitType = unitType;
        this.unitDescription = unitDescription;
        this.quantityInBaseUnit = quantityInBaseUnit;
        this.unitRegularPrice = unitRegularPrice;
        this.unitSalePrice = unitSalePrice;
        this.unitBuyPrice = unitBuyPrice;
    }

    public String getUnitType() {return unitType;}

    public void setUnitType(String unitType) {this.unitType = unitType;}

    public String getUnitDescription(){return unitDescription;}

    public void setUnitDescription(String unitDescription){this.unitDescription = unitDescription;}

    public Double getQuantityInBaseUnit() { return quantityInBaseUnit; }
    public void setQuantityInBaseUnit(Double quantityInBaseUnit) {this.quantityInBaseUnit = quantityInBaseUnit; }

    public Double getUnitRegularPrice() {return unitRegularPrice;}
    public void setUnitPrice(Double unitRegularPrice) { this.unitRegularPrice = unitRegularPrice; }

    public Double getUnitSalePrice() {return unitSalePrice;}
    public void setUnitSalePrice( Double unitSalePrice) { this.unitSalePrice = unitSalePrice; }

    public Double getUnitBuyPrice() {return unitBuyPrice;}
    public void setUnitBuyPrice(Double unitBuyPrice) { this.unitBuyPrice = unitBuyPrice; }
}
