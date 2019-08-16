package com.san.logicuniversity_ad.modals;

public class DisbursementItem {

    private String itemNumber;
    private String category;
    private String description;
    private String unitOfMeasure;
    private int qtyCollected;
    private int qtyIssued;
    private String reason;

    public DisbursementItem() {
    }

    public DisbursementItem(String itemNumber, String category, String description, String unitOfMeasure, int qtyCollected, int qtyIssued, String reason) {
        this.itemNumber = itemNumber;
        this.category = category;
        this.description = description;
        this.unitOfMeasure = unitOfMeasure;
        this.qtyCollected = qtyCollected;
        this.qtyIssued = qtyIssued;
        this.reason = reason;
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

    public int getQtyCollected() {
        return qtyCollected;
    }

    public void setQtyCollected(int qtyCollected) {
        this.qtyCollected = qtyCollected;
    }

    public int getQtyIssued() {
        return qtyIssued;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setQtyIssued(int qtyIssued) {
        this.qtyIssued = qtyIssued;
    }
}
