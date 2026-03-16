package com.civicfix.civicfix.controller;

import com.civicfix.civicfix.model.Complaint;
import com.civicfix.civicfix.model.Notification;
import com.civicfix.civicfix.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/complaints")
@CrossOrigin(origins = "*")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @PostMapping
    public String createComplaint(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            @RequestParam("priority") String priority,
            @RequestParam(value = "userId", defaultValue = "0") int userId,
            @RequestParam(value = "image", required = false) MultipartFile image) {

        Complaint complaint = new Complaint();
        complaint.setTitle(title);
        complaint.setDescription(description);
        complaint.setCategory(category);
        complaint.setPriority(priority);
        complaint.setUserId(userId);
        return complaintService.registerComplaint(complaint, image);
    }

    @GetMapping
    public List<Complaint> getAllComplaints() {
        return complaintService.getAllComplaints();
    }

    @GetMapping("/user/{userId}")
    public List<Complaint> getByUser(@PathVariable int userId) {
        return complaintService.getComplaintsByUser(userId);
    }

    @GetMapping("/{id}")
    public Complaint getComplaintById(@PathVariable int id) {
        return complaintService.getComplaintById(id);
    }

    @PutMapping("/{id}/status")
    public String updateStatus(
            @PathVariable int id,
            @RequestParam String status,
            @RequestParam(required = false, defaultValue = "") String resolutionNote) {
        return complaintService.updateStatus(id, status, resolutionNote);
    }

    @GetMapping("/search")
    public List<Complaint> search(@RequestParam String keyword) {
        return complaintService.searchComplaints(keyword);
    }

    @GetMapping("/filter")
    public List<Complaint> filter(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority) {
        if (status != null) return complaintService.filterByStatus(status);
        if (priority != null) return complaintService.filterByPriority(priority);
        return complaintService.getAllComplaints();
    }

    @GetMapping("/stats")
    public Map<String, Integer> getStats() {
        return complaintService.getStats();
    }

    @GetMapping("/notifications")
    public List<Notification> getNotifications() {
        return complaintService.getNotifications();
    }

    @DeleteMapping("/notifications")
    public String clearNotifications() {
        complaintService.clearNotifications();
        return "Notifications cleared";
    }
}