package com.civicfix.civicfix.observer;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class NotificationService implements Subject {

    private List<Observer> observers = new ArrayList<>();

    public NotificationService() {
        // Register all departments as observers
        observers.add(new DepartmentObserver("Electrical"));
        observers.add(new DepartmentObserver("Plumbing"));
        observers.add(new DepartmentObserver("Sanitation"));
        observers.add(new DepartmentObserver("Road Maintenance"));
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer o : observers) {
            o.update(message);
        }
    }

    public void notifyDepartment(String departmentName, String message) {
        for (Observer o : observers) {
            if (o instanceof DepartmentObserver) {
                DepartmentObserver deptObserver = (DepartmentObserver) o;
                if (deptObserver.getDepartmentName().equalsIgnoreCase(departmentName)) {
                    deptObserver.update(message);
                }
            }
        }
    }
}