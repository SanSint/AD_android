package com.san.logicuniversity_ad.modals;

import java.time.LocalDate;

public class Request {
    private int ID;
    private LocalDate REQUEST_DATE;
    private LocalDate APPROVED_DATE;
    private String STATUS;
    private int DEPARTMENT_ID;
    private String SUBMITTED_BY;
    private String APPROVED_BY;

    public Request() {
    }


    public Request(int ID, LocalDate REQUEST_DATE, LocalDate APPROVED_DATE, String STATUS, int DEPARTMENT_ID, String SUBMITTED_BY, String APPROVED_BY) {
        this.ID = ID;
        this.REQUEST_DATE = REQUEST_DATE;
        this.APPROVED_DATE = APPROVED_DATE;
        this.STATUS = STATUS;
        this.DEPARTMENT_ID = DEPARTMENT_ID;
        this.SUBMITTED_BY = SUBMITTED_BY;
        this.APPROVED_BY = APPROVED_BY;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public LocalDate getREQUEST_DATE() {
        return REQUEST_DATE;
    }

    public void setREQUEST_DATE(LocalDate REQUEST_DATE) {
        this.REQUEST_DATE = REQUEST_DATE;
    }

    public LocalDate getAPPROVED_DATE() {
        return APPROVED_DATE;
    }

    public void setAPPROVED_DATE(LocalDate APPROVED_DATE) {
        this.APPROVED_DATE = APPROVED_DATE;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public int getDEPARTMENT_ID() {
        return DEPARTMENT_ID;
    }

    public void setDEPARTMENT_ID(int DEPARTMENT_ID) {
        this.DEPARTMENT_ID = DEPARTMENT_ID;
    }

    public String getSUBMITTED_BY() {
        return SUBMITTED_BY;
    }

    public void setSUBMITTED_BY(String SUBMITTED_BY) {
        this.SUBMITTED_BY = SUBMITTED_BY;
    }

    public String getAPPROVED_BY() {
        return APPROVED_BY;
    }

    public void setAPPROVED_BY(String APPROVED_BY) {
        this.APPROVED_BY = APPROVED_BY;
    }
}
