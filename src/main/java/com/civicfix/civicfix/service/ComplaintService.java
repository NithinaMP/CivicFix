package com.civicfix.civicfix.service;

import com.civicfix.civicfix.dao.ComplaintDAO;
import com.civicfix.civicfix.model.Complaint;
import com.civicfix.civicfix.observer.NotificationService;
import com.civicfix.civicfix.routing.ComplaintRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        if (complaint.getPriority() == null || complaint.getPriority().isEmpty()) {
            complaint.setPriority("MEDIUM");
        }

        complaintDAO.saveComplaint(complaint);

        notificationService.notifyDepartment(deptName,
            "New " + complaint.getPriority() + " priority complaint: " + complaint.getTitle());

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
                "Complaint #" + id + " '" + complaint.getTitle() + "' updated to: " + status);
        }
        return "Status updated to: " + status;
    }

    public List<Complaint> searchComplaints(String keyword) {
        return complaintDAO.searchComplaints(keyword);
    }

    public List<Complaint> filterByStatus(String status) {
        return complaintDAO.getComplaintsByStatus(status);
    }

    public List<Complaint> filterByPriority(String priority) {
        return complaintDAO.getComplaintsByPriority(priority);
    }

    public Map<String, Integer> getStats() {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("total", complaintDAO.getAllComplaints().size());
        stats.put("pending", complaintDAO.countByStatus("PENDING"));
        stats.put("inProgress", complaintDAO.countByStatus("IN_PROGRESS"));
        stats.put("resolved", complaintDAO.countByStatus("RESOLVED"));
        stats.put("high", complaintDAO.countByPriority("HIGH"));
        stats.put("medium", complaintDAO.countByPriority("MEDIUM"));
        stats.put("low", complaintDAO.countByPriority("LOW"));
        return stats;
    }
}