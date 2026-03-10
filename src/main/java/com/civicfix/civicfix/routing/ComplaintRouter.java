// package com.civicfix.civicfix.routing;

// import org.springframework.stereotype.Component;

// @Component
// public class ComplaintRouter {

//     public int routeComplaint(String category) {

//         if (category.equalsIgnoreCase("electrical")) {
//             return 1;
//         }

//         else if (category.equalsIgnoreCase("plumbing")) {
//             return 2;
//         }

//         else if (category.equalsIgnoreCase("sanitation")) {
//             return 3;
//         }

//         else {
//             return 4;
//         }
//     }
// }
package com.civicfix.civicfix.routing;

import org.springframework.stereotype.Component;

@Component
public class ComplaintRouter {

    public int routeComplaint(String category) {

        if (category == null) return 4;

        switch (category.toLowerCase()) {
            case "electrical": return 1;
            case "plumbing":   return 2;
            case "sanitation": return 3;
            default:           return 4;
        }
    }

    public String getDepartmentName(int deptId) {
        switch (deptId) {
            case 1: return "Electrical";
            case 2: return "Plumbing";
            case 3: return "Sanitation";
            default: return "Road Maintenance";
        }
    }
}