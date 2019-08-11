package com.san.logicuniversity_ad.modals;

public class RetrivalItem {
    private String itemNumber;
    private String category;
    private String description;
    private int qtyNeeded;
    private int qtyRetrieved;

    public RetrivalItem(String itemNumber, String category, String description, int qtyNeeded, int qtyRetrieved) {
        this.itemNumber = itemNumber;
        this.category = category;
        this.description = description;
        this.qtyNeeded = qtyNeeded;
        this.qtyRetrieved = qtyRetrieved;
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

    public int getQtyNeeded() {
        return qtyNeeded;
    }

    public void setQtyNeeded(int qtyNeeded) {
        this.qtyNeeded = qtyNeeded;
    }

    public int getQtyRetrieved() {
        return qtyRetrieved;
    }

    public void setQtyRetrieved(int qtyRetrieved) {
        this.qtyRetrieved = qtyRetrieved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
