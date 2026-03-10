// package com.civicfix.civicfix.dao;

// import com.civicfix.civicfix.model.Complaint;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.jdbc.core.JdbcTemplate;
// import org.springframework.stereotype.Repository;

// @Repository
// public class ComplaintDAO {

//     @Autowired
//     private JdbcTemplate jdbcTemplate;

//     public void saveComplaint(Complaint complaint) {

//         String sql = "INSERT INTO complaints(title, description, category, status, department_id) VALUES (?, ?, ?, ?, ?)";

//         jdbcTemplate.update(sql,
//                 complaint.getTitle(),
//                 complaint.getDescription(),
//                 complaint.getCategory(),
//                 complaint.getStatus(),
//                 complaint.getDepartmentId());
//     }
// }
package com.civicfix.civicfix.dao;

import com.civicfix.civicfix.model.Complaint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ComplaintDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Save complaint
    public void saveComplaint(Complaint complaint) {
        String sql = "INSERT INTO complaints(title, description, category, status, department_id) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                complaint.getTitle(),
                complaint.getDescription(),
                complaint.getCategory(),
                complaint.getStatus(),
                complaint.getDepartmentId());
    }

    // Get all complaints
    public List<Complaint> getAllComplaints() {
        String sql = "SELECT * FROM complaints";
        return jdbcTemplate.query(sql, new ComplaintRowMapper());
    }

    // Get complaint by ID
    public Complaint getComplaintById(int id) {
        String sql = "SELECT * FROM complaints WHERE id = ?";
        List<Complaint> results = jdbcTemplate.query(sql, new ComplaintRowMapper(), id);
        return results.isEmpty() ? null : results.get(0);
    }

    // Update status
    public void updateStatus(int id, String status) {
        String sql = "UPDATE complaints SET status = ? WHERE id = ?";
        jdbcTemplate.update(sql, status, id);
    }

    // RowMapper
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