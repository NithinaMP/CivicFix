package com.civicfix.civicfix.service;

import com.civicfix.civicfix.dao.ComplaintDAO;
import com.civicfix.civicfix.model.Complaint;
import com.civicfix.civicfix.observer.NotificationService;
import com.civicfix.civicfix.routing.ComplaintRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintDAO complaintDAO;

    @Autowired
    private ComplaintRouter router;

    @Autowired
    private NotificationService notificationService;

    public String registerComplaint(Complaint complaint) {
        int deptId = router.routeComplaint(complaint.getCategory());
        String deptName = router.getDepartmentName(deptId);

        complaint.setDepartmentId(deptId);
        complaint.setStatus("PENDING");

        complaintDAO.saveComplaint(complaint);

        // Observer Pattern triggers here
        notificationService.notifyDepartment(deptName,
            "New complaint assigned: " + complaint.getTitle());

        return "Complaint registered! Assigned to: " + deptName + " Department.";
    }

    public List<Complaint> getAllComplaints() {
        return complaintDAO.getAllComplaints();
    }

    public Complaint getComplaintById(int id) {
        return complaintDAO.getComplaintById(id);
    }

    public String updateStatus(int id, String status) {
        Complaint complaint = complaintDAO.getComplaintById(id);
        complaintDAO.updateStatus(id, status);

        if (complaint != null) {
            notificationService.notifyObservers(
                "Complaint #" + id + " status updated to: " + status);
        }

        return "Status updated to: " + status;
    }
}