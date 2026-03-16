package com.civicfix.civicfix.observer;

import com.civicfix.civicfix.dao.NotificationDAO;
import com.civicfix.civicfix.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NotificationService implements Subject {

    @Autowired
    private NotificationDAO notificationDAO;

    private List<Observer> observers = new ArrayList<>();

    public NotificationService() {
        observers.add(new DepartmentObserver("Electrical"));
        observers.add(new DepartmentObserver("Plumbing"));
        observers.add(new DepartmentObserver("Sanitation"));
        observers.add(new DepartmentObserver("Road Maintenance"));
    }

    @Override
    public void registerObserver(Observer o) { observers.add(o); }

    @Override
    public void removeObserver(Observer o) { observers.remove(o); }

    @Override
    public void notifyObservers(String message) {
        for (Observer o : observers) o.update(message);
    }

    public void notifyDepartment(String departmentName, String message) {
        for (Observer o : observers) {
            if (o instanceof DepartmentObserver) {
                DepartmentObserver d = (DepartmentObserver) o;
                if (d.getDepartmentName().equalsIgnoreCase(departmentName))
                    d.update(message);
            }
        }
    }

    // Save notification to database
    public void createNotification(int complaintId, String message, String type) {
        Notification n = new Notification();
        n.setComplaintId(complaintId);
        n.setMessage(message);
        n.setType(type);
        notificationDAO.save(n);
    }

    // Get all active notifications
    public List<Notification> getActiveNotifications() {
        return notificationDAO.getActiveNotifications();
    }

    // Remove notification when resolved
    public void removeNotification(int complaintId) {
        notificationDAO.deleteByComplaintId(complaintId);
    }

    // Clear all
    public void clearAll() {
        notificationDAO.deleteAll();
    }
}