package com.san.logicuniversity_ad.modals;

public class Disbursement {
    private String disbursementId;
    private String department;
    private String doneBy;

    public Disbursement(String disbursementId, String department, String doneBy) {
        this.disbursementId = disbursementId;
        this.department = department;
        this.doneBy = doneBy;
    }

    public String getDisbursementId() {
        return disbursementId;
    }

    public void setDisbursementId(String disbursementId) {
        this.disbursementId = disbursementId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDoneBy() {
        return doneBy;
    }

    public void setDoneBy(String doneBy) {
        this.doneBy = doneBy;
    }



}
