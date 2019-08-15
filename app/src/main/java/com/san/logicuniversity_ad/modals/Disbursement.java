package com.san.logicuniversity_ad.modals;

public class Disbursement {
    private int disbursementId;
    private String department;
    private String doneBy;
    private String status;

    public Disbursement(int disbursementId, String department, String doneBy, String status) {
        this.disbursementId = disbursementId;
        this.department = department;
        this.doneBy = doneBy;
        this.status = status;
    }

    public int getDisbursementId() {
        return disbursementId;
    }

    public void setDisbursementId(int disbursementId) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
