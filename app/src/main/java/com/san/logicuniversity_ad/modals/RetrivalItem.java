package com.san.logicuniversity_ad.modals;

public class RetrivalItem {
    private String itemNumber;
    private String category;
    private String zone;
    private int qtyNeeded;
    private int qtyRetrieved;

    public RetrivalItem(String itemNumber, String category, String zone, int qtyNeeded, int qtyRetrieved) {
        this.itemNumber = itemNumber;
        this.category = category;
        this.zone = zone;
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

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
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
}
