package com.civicfix.civicfix.dao;

import com.civicfix.civicfix.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class NotificationDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Save new notification
    public void save(Notification notification) {
        String sql = "INSERT INTO notifications(complaint_id, message, type) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql,
                notification.getComplaintId(),
                notification.getMessage(),
                notification.getType());
    }

    // Get all active (unresolved) notifications
    public List<Notification> getActiveNotifications() {
        String sql = "SELECT * FROM notifications ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, new NotificationRowMapper());
    }

    // Delete notification when complaint is resolved
    public void deleteByComplaintId(int complaintId) {
        String sql = "DELETE FROM notifications WHERE complaint_id = ?";
        jdbcTemplate.update(sql, complaintId);
    }

    // Delete all notifications
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM notifications");
    }

    private static class NotificationRowMapper implements RowMapper<Notification> {
        @Override
        public Notification mapRow(ResultSet rs, int rowNum) throws SQLException {
            Notification n = new Notification();
            n.setId(rs.getInt("id"));
            n.setComplaintId(rs.getInt("complaint_id"));
            n.setMessage(rs.getString("message"));
            n.setType(rs.getString("type"));
            n.setRead(rs.getBoolean("is_read"));
            n.setCreatedAt(rs.getString("created_at"));
            return n;
        }
    }
}