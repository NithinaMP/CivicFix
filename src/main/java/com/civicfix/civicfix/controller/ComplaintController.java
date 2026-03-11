package com.civicfix.civicfix.controller;

import com.civicfix.civicfix.model.Complaint;
import com.civicfix.civicfix.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/complaints")
@CrossOrigin(origins = "*")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @PostMapping
    public String createComplaint(@RequestBody Complaint complaint) {
        return complaintService.registerComplaint(complaint);
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
    public String updateStatus(@PathVariable int id, @RequestParam String status) {
        return complaintService.updateStatus(id, status);
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
}