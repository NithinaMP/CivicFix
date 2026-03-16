package com.civicfix.civicfix.service;

import com.civicfix.civicfix.dao.ComplaintDAO;
import com.civicfix.civicfix.model.Complaint;
import com.civicfix.civicfix.model.Notification;
import com.civicfix.civicfix.observer.NotificationService;
import com.civicfix.civicfix.routing.ComplaintRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ComplaintService {

    @Autowired private ComplaintDAO complaintDAO;
    @Autowired private ComplaintRouter router;
    @Autowired private NotificationService notificationService;

    private final String UPLOAD_DIR = "uploads/";

    public String registerComplaint(Complaint complaint, MultipartFile image) {
        int deptId = router.routeComplaint(complaint.getCategory());
        String deptName = router.getDepartmentName(deptId);
        complaint.setDepartmentId(deptId);
        complaint.setStatus("PENDING");
        if (complaint.getPriority() == null || complaint.getPriority().isEmpty())
            complaint.setPriority("MEDIUM");

        // Handle image upload
        if (image != null && !image.isEmpty()) {
            try {
                File uploadDir = new File(UPLOAD_DIR);
                if (!uploadDir.exists()) uploadDir.mkdirs();
                String filename = UUID.randomUUID() + "_" + image.getOriginalFilename();
                Path path = Paths.get(UPLOAD_DIR + filename);
                Files.write(path, image.getBytes());
                complaint.setImagePath("/uploads/" + filename);
            } catch (IOException e) {
                System.out.println("Image upload failed: " + e.getMessage());
            }
        }

        complaintDAO.saveComplaint(complaint);

        List<Complaint> all = complaintDAO.getAllComplaints();
        int newId = all.isEmpty() ? 1 : all.get(0).getId();

        notificationService.notifyDepartment(deptName,
            "New complaint assigned: " + complaint.getTitle());

        notificationService.createNotification(newId,
            "📌 New " + complaint.getPriority() + " priority complaint assigned to " +
            deptName + " Dept: \"" + complaint.getTitle() + "\"", "NEW_COMPLAINT");

        if ("HIGH".equalsIgnoreCase(complaint.getPriority())) {
            notificationService.createNotification(newId,
                "🚨 HIGH PRIORITY ALERT: \"" + complaint.getTitle() +
                "\" needs immediate attention!", "HIGH_PRIORITY");
        }

        return "Complaint registered! Assigned to: " + deptName + " Department.";
    }

    public List<Complaint> getAllComplaints() { return complaintDAO.getAllComplaints(); }

    public List<Complaint> getComplaintsByUser(int userId) {
        return complaintDAO.getComplaintsByUserId(userId);
    }

    public Complaint getComplaintById(int id) { return complaintDAO.getComplaintById(id); }

    public String updateStatus(int id, String status, String resolutionNote) {
        Complaint complaint = complaintDAO.getComplaintById(id);
        complaintDAO.updateStatus(id, status, resolutionNote);
        if (complaint != null) {
            if ("RESOLVED".equalsIgnoreCase(status)) {
                notificationService.removeNotification(id);
                notificationService.notifyObservers(
                    "✅ Complaint #" + id + " RESOLVED: \"" + complaint.getTitle() + "\"");
            } else {
                notificationService.createNotification(id,
                    "🔧 Complaint #" + id + " \"" + complaint.getTitle() +
                    "\" status → " + status, "STATUS_UPDATE");
            }
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

    public List<Notification> getNotifications() {
        return notificationService.getActiveNotifications();
    }

    public void clearNotifications() { notificationService.clearAll(); }

    public Map<String, Integer> getStats() {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("total", complaintDAO.getAllComplaints().size());
        stats.put("pending", complaintDAO.countByStatus("PENDING"));
        stats.put("inProgress", complaintDAO.countByStatus("IN_PROGRESS"));
        stats.put("resolved", complaintDAO.countByStatus("RESOLVED"));
        stats.put("high", complaintDAO.countByPriority("HIGH"));
        stats.put("medium", complaintDAO.countByPriority("MEDIUM"));
        stats.put("low", complaintDAO.countByPriority("LOW"));
        stats.put("electrical", complaintDAO.countByDepartment(1));
        stats.put("plumbing", complaintDAO.countByDepartment(2));
        stats.put("sanitation", complaintDAO.countByDepartment(3));
        stats.put("road", complaintDAO.countByDepartment(4));
        return stats;
    }
}