// package com.civicfix.civicfix.model;

// public class Complaint {

//     private int id;
//     private String title;
//     private String description;
//     private String category;
//     private String status;
//     private int departmentId;

//     public Complaint() {
//     }

//     public Complaint(String title, String description, String category, String status, int departmentId) {
//         this.title = title;
//         this.description = description;
//         this.category = category;
//         this.status = status;
//         this.departmentId = departmentId;
//     }

//     public int getId() {
//         return id;
//     }

//     public void setId(int id) {
//         this.id = id;
//     }


//     public String getTitle() {
//         return title;
//     }

//     public void setTitle(String title) {
//         this.title = title;
//     }


//     public String getDescription() {
//         return description;
//     }

//     public void setDescription(String description) {
//         this.description = description;
//     }


//     public String getCategory() {
//         return category;
//     }

//     public void setCategory(String category) {
//         this.category = category;
//     }


//     public String getStatus() {
//         return status;
//     }

//     public void setStatus(String status) {
//         this.status = status;
//     }


//     public int getDepartmentId() {
//         return departmentId;
//     }

//     public void setDepartmentId(int departmentId) {
//         this.departmentId = departmentId;
//     }
// }
package com.civicfix.civicfix.model;

public class Complaint {

    private int id;
    private String title;
    private String description;
    private String category;
    private String status;
    private int departmentId;

    public Complaint() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getDepartmentId() { return departmentId; }
    public void setDepartmentId(int departmentId) { this.departmentId = departmentId; }
}