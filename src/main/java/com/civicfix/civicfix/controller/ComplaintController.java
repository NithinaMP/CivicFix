// package com.civicfix.civicfix.controller;

// import com.civicfix.civicfix.model.Complaint;
// import com.civicfix.civicfix.service.ComplaintService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/complaints")
// public class ComplaintController {

//     @Autowired
//     private ComplaintService complaintService;

//     @PostMapping
//     public String createComplaint(@RequestBody Complaint complaint) {

//         complaintService.registerComplaint(complaint);

//         return "Complaint registered successfully!";
//     }
// }
package com.civicfix.civicfix.controller;

import com.civicfix.civicfix.model.Complaint;
import com.civicfix.civicfix.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/complaints")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    // POST - Submit a complaint
    @PostMapping
    public String createComplaint(@RequestBody Complaint complaint) {
        return complaintService.registerComplaint(complaint);
    }

    // GET - Get all complaints
    @GetMapping
    public List<Complaint> getAllComplaints() {
        return complaintService.getAllComplaints();
    }

    // GET - Get complaint by ID
    @GetMapping("/{id}")
    public Complaint getComplaintById(@PathVariable int id) {
        return complaintService.getComplaintById(id);
    }

    // PUT - Update complaint status
    @PutMapping("/{id}/status")
    public String updateStatus(@PathVariable int id, @RequestParam String status) {
        return complaintService.updateStatus(id, status);
    }
}