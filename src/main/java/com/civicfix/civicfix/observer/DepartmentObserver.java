package com.civicfix.civicfix.observer;

public class DepartmentObserver implements Observer {

    private String departmentName;

    public DepartmentObserver(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public void update(String message) {
        System.out.println("[" + departmentName + " Department] Notification: " + message);
    }

    public String getDepartmentName() {
        return departmentName;
    }
}