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

    public void saveComplaint(Complaint complaint) {
        String sql = "INSERT INTO complaints(title, description, category, status, priority, department_id) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                complaint.getTitle(),
                complaint.getDescription(),
                complaint.getCategory(),
                complaint.getStatus(),
                complaint.getPriority(),
                complaint.getDepartmentId());
    }

    public List<Complaint> getAllComplaints() {
        String sql = "SELECT * FROM complaints ORDER BY id DESC";
        return jdbcTemplate.query(sql, new ComplaintRowMapper());
    }

    public Complaint getComplaintById(int id) {
        String sql = "SELECT * FROM complaints WHERE id = ?";
        List<Complaint> results = jdbcTemplate.query(sql, new ComplaintRowMapper(), id);
        return results.isEmpty() ? null : results.get(0);
    }

    public void updateStatus(int id, String status) {
        String sql = "UPDATE complaints SET status = ? WHERE id = ?";
        jdbcTemplate.update(sql, status, id);
    }

    public List<Complaint> getComplaintsByStatus(String status) {
        String sql = "SELECT * FROM complaints WHERE status = ? ORDER BY id DESC";
        return jdbcTemplate.query(sql, new ComplaintRowMapper(), status);
    }

    public List<Complaint> getComplaintsByPriority(String priority) {
        String sql = "SELECT * FROM complaints WHERE priority = ? ORDER BY id DESC";
        return jdbcTemplate.query(sql, new ComplaintRowMapper(), priority);
    }

    public List<Complaint> searchComplaints(String keyword) {
        String sql = "SELECT * FROM complaints WHERE title LIKE ? OR description LIKE ? OR category LIKE ? ORDER BY id DESC";
        String k = "%" + keyword + "%";
        return jdbcTemplate.query(sql, new ComplaintRowMapper(), k, k, k);
    }

    public int countByStatus(String status) {
        String sql = "SELECT COUNT(*) FROM complaints WHERE status = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, status);
        return count != null ? count : 0;
    }

    public int countByPriority(String priority) {
        String sql = "SELECT COUNT(*) FROM complaints WHERE priority = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, priority);
        return count != null ? count : 0;
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
            c.setPriority(rs.getString("priority"));
            c.setDepartmentId(rs.getInt("department_id"));
            return c;
        }
    }
}