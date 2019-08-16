package com.san.logicuniversity_ad.modals;

public class Employee {
    private int ID;
    private String NAME;
    private int ROLE_ID;
    private int DEPARTMENT_ID;
    private String EMAIL;

    public Employee() {
    }

    public Employee(int ID, String NAME, int ROLE_ID, int DEPARTMENT_ID, String EMAIL) {
        this.ID = ID;
        this.NAME = NAME;
        this.ROLE_ID = ROLE_ID;
        this.DEPARTMENT_ID = DEPARTMENT_ID;
        this.EMAIL = EMAIL;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public int getROLE_ID() {
        return ROLE_ID;
    }

    public void setROLE_ID(int ROLE_ID) {
        this.ROLE_ID = ROLE_ID;
    }

    public int getDEPARTMENT_ID() {
        return DEPARTMENT_ID;
    }

    public void setDEPARTMENT_ID(int DEPARTMENT_ID) {
        this.DEPARTMENT_ID = DEPARTMENT_ID;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    @Override
    public String toString() {
        return this.NAME;
    }
}
