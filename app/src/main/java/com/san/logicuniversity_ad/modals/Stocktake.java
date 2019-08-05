package com.san.logicuniversity_ad.modals;

public class Stocktake {
    private String stocktakeId;
    private String doneBy;
    private String month;

    public Stocktake(String stocktakeId, String doneBy, String month) {
        this.stocktakeId = stocktakeId;
        this.doneBy = doneBy;
        this.month = month;
    }

    public String getStocktakeId() {
        return stocktakeId;
    }

    public void setStocktakeId(String stocktakeId) {
        this.stocktakeId = stocktakeId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDoneBy() {
        return doneBy;
    }

    public void setDoneBy(String doneBy) {
        this.doneBy = doneBy;
    }


}
