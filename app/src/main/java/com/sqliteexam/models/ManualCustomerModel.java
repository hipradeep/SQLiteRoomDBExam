package com.sqliteexam.models;

public class ManualCustomerModel {
    private int id;
    private String customerName;
    private int customerAge;
    private boolean active;

    public ManualCustomerModel(int id, String customerName, int customerAge, boolean active) {
        this.id = id;
        this.customerName = customerName;
        this.customerAge = customerAge;
        this.active = active;
    }

    public ManualCustomerModel() {
    }

    @Override
    public String toString() {
        return "ManualModel{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", customerAge=" + customerAge +
                ", active=" + active +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getCustomerAge() {
        return customerAge;
    }

    public void setCustomerAge(int customerAge) {
        this.customerAge = customerAge;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
