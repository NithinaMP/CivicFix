// package com.civicfix.civicfix.service;

// import com.civicfix.civicfix.dao.ComplaintDAO;
// import com.civicfix.civicfix.model.Complaint;
// import com.civicfix.civicfix.routing.ComplaintRouter;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// @Service
// public class ComplaintService {

//     @Autowired
//     private ComplaintDAO complaintDAO;

//     @Autowired
//     private ComplaintRouter complaintRouter;

//     public void registerComplaint(Complaint complaint) {

//         int deptId = complaintRouter.routeComplaint(complaint.getCategory());

//         complaint.setDepartmentId(deptId);
//         complaint.setStatus("PENDING");

//         complaintDAO.saveComplaint(complaint);
//     }
// }
package com.civicfix.civicfix.service;

import com.civicfix.civicfix.dao.ComplaintDAO;
import com.civicfix.civicfix.model.Complaint;
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

    // Register new complaint
    public String registerComplaint(Complaint complaint) {
        int deptId = router.routeComplaint(complaint.getCategory());
        String deptName = router.getDepartmentName(deptId);

        complaint.setDepartmentId(deptId);
        complaint.setStatus("PENDING");

        complaintDAO.saveComplaint(complaint);

        return "Complaint registered! Assigned to: " + deptName + " Department.";
    }

    // Get all complaints
    public List<Complaint> getAllComplaints() {
        return complaintDAO.getAllComplaints();
    }

    // Get complaint by ID
    public Complaint getComplaintById(int id) {
        return complaintDAO.getComplaintById(id);
    }

    // Update complaint status
    public String updateStatus(int id, String status) {
        complaintDAO.updateStatus(id, status);
        return "Status updated to: " + status;
    }
}