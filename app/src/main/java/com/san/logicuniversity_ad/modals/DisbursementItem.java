package com.san.logicuniversity_ad.modals;

public class DisbursementItem {

    private String itemNumber;
    private String category;
    private String description;
    private String unitOfMeasure;
    private int qtyIssued;

    public DisbursementItem(String itemNumber, String category, String description, String unitOfMeasure, int qtyIssued, int actualQtyIssued) {
        this.itemNumber = itemNumber;
        this.category = category;
        this.description = description;
        this.unitOfMeasure = unitOfMeasure;
        this.qtyIssued = qtyIssued;
        this.actualQtyIssued = actualQtyIssued;
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

    public int getQtyIssued() {
        return qtyIssued;
    }

    public void setQtyIssued(int qtyIssued) {
        this.qtyIssued = qtyIssued;
    }

    public int getActualQtyIssued() {
        return actualQtyIssued;
    }

    public void setActualQtyIssued(int actualQtyIssued) {
        this.actualQtyIssued = actualQtyIssued;
    }

    private int actualQtyIssued;

}
