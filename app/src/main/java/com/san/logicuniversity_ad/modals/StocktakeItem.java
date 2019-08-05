package com.san.logicuniversity_ad.modals;

public class StocktakeItem {

    private String itemNumber;
    private String category;
    private String description;
    private String unitOfMeasure;
    private int qty;
    private int qtyActual;

    public StocktakeItem(String itemNumber, String category, String description, String unitOfMeasure, int qty, int qtyActual) {
        this.itemNumber = itemNumber;
        this.category = category;
        this.description = description;
        this.unitOfMeasure = unitOfMeasure;
        this.qty = qty;
        this.qtyActual = qtyActual;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getQtyActual() {
        return qtyActual;
    }

    public void setQtyActual(int qtyActual) {
        this.qtyActual = qtyActual;
    }
}
