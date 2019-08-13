package com.san.logicuniversity_ad.modals;

public class RequestDetails {

    private int REQUEST_ID;
    private String DESCRIPTION;
    private int QUANTITY;
    private Double PRICE;
    private String REQUESTOR;

    public RequestDetails() {
    }

    public RequestDetails(int REQUEST_ID, String DESCRIPTION, int QUANTITY, Double PRICE, String REQUESTOR) {
        this.REQUEST_ID = REQUEST_ID;
        this.DESCRIPTION = DESCRIPTION;
        this.QUANTITY = QUANTITY;
        this.PRICE = PRICE;
        this.REQUESTOR = REQUESTOR;
    }

    public int getREQUEST_ID() {
        return REQUEST_ID;
    }

    public void setREQUEST_ID(int REQUEST_ID) {
        this.REQUEST_ID = REQUEST_ID;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public int getQUANTITY() {
        return QUANTITY;
    }

    public void setQUANTITY(int QUANTITY) {
        this.QUANTITY = QUANTITY;
    }

    public Double getPRICE() {
        return PRICE;
    }

    public void setPRICE(Double PRICE) {
        this.PRICE = PRICE;
    }

    public String getREQUESTOR() {
        return REQUESTOR;
    }

    public void setREQUESTOR(String REQUESTOR) {
        this.REQUESTOR = REQUESTOR;
    }
}
