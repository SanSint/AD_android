package com.san.logicuniversity_ad.modals;

public class Disbursement {
    private int disbursementId;
    private String collectionPoint;
    private String doneBy;
    private String status;
    private String departmentName;

    public Disbursement(int disbursementId, String collectionPoint, String doneBy, String status, String departmentName) {
        this.disbursementId = disbursementId;
        this.collectionPoint = collectionPoint;
        this.doneBy = doneBy;
        this.status = status;
        this.departmentName = departmentName;
    }


    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getDisbursementId() {
        return disbursementId;
    }

    public void setDisbursementId(int disbursementId) {
        this.disbursementId = disbursementId;
    }

    public String getCollectionPoint() {
        return collectionPoint;
    }

    public void setCollectionPoint(String collectionPoint) {
        this.collectionPoint = collectionPoint;
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
