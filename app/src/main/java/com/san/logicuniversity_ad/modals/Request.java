package com.san.logicuniversity_ad.modals;

import java.time.LocalDate;

public class Request {
    public int ID;
    public LocalDate REQUEST_DATE;
    public LocalDate APPROVED_DATE;
    public String STATUS;
    public int DEPARTMENT_ID;
    public Employee SUBMITTED_BY;
    public Employee APPROVED_BY;

    public Request() {
    }


    public Request(int ID, LocalDate REQUEST_DATE, LocalDate APPROVED_DATE, String STATUS, int DEPARTMENT_ID, Employee SUBMITTED_BY, Employee APPROVED_BY) {
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

    public Employee getSUBMITTED_BY() {
        return SUBMITTED_BY;
    }

    public void setSUBMITTED_BY(Employee SUBMITTED_BY) {
        this.SUBMITTED_BY = SUBMITTED_BY;
    }

    public Employee getAPPROVED_BY() {
        return APPROVED_BY;
    }

    public void setAPPROVED_BY(Employee APPROVED_BY) {
        this.APPROVED_BY = APPROVED_BY;
    }
}
