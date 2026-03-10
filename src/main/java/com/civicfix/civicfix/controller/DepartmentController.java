package com.civicfix.civicfix.controller;

import com.civicfix.civicfix.dao.DepartmentDAO;
import com.civicfix.civicfix.model.Complaint;
import com.civicfix.civicfix.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentDAO departmentDAO;

    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentDAO.getAllDepartments();
    }

    @GetMapping("/{id}/complaints")
    public List<Complaint> getComplaintsByDepartment(@PathVariable int id) {
        return departmentDAO.getComplaintsByDepartment(id);
    }
}