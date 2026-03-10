package com.civicfix.civicfix.dao;

import com.civicfix.civicfix.model.Complaint;
import com.civicfix.civicfix.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DepartmentDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Department> getAllDepartments() {
        String sql = "SELECT * FROM departments";
        return jdbcTemplate.query(sql, new DepartmentRowMapper());
    }

    public List<Complaint> getComplaintsByDepartment(int deptId) {
        String sql = "SELECT * FROM complaints WHERE department_id = ?";
        return jdbcTemplate.query(sql, new ComplaintRowMapper(), deptId);
    }

    private static class DepartmentRowMapper implements RowMapper<Department> {
        @Override
        public Department mapRow(ResultSet rs, int rowNum) throws SQLException {
            Department d = new Department();
            d.setId(rs.getInt("id"));
            d.setName(rs.getString("name"));
            return d;
        }
    }

    private static class ComplaintRowMapper implements RowMapper<Complaint> {
        @Override
        public Complaint mapRow(ResultSet rs, int rowNum) throws SQLException {
            Complaint c = new Complaint();
            c.setId(rs.getInt("id"));
            c.setTitle(rs.getString("title"));
            c.setDescription(rs.getString("description"));
            c.setCategory(rs.getString("category"));
            c.setStatus(rs.getString("status"));
            c.setDepartmentId(rs.getInt("department_id"));
            return c;
        }
    }
}