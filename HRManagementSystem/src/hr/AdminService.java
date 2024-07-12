package hr;

import java.util.HashMap;

public class AdminService {
    private HashMap<Integer, Employee> employees;

    public AdminService(HashMap<Integer, Employee> employees) {
        this.employees = employees;
    }

    public void viewAllEmployees() {
        if (employees.isEmpty()) {
            System.out.println("Çalışan Bulunamadıı.");
        } else {
            for (Employee employee : employees.values()) {
                System.out.println(employee);
            }
        }
    }

    public void deleteEmployee(int id) {
        if (employees.containsKey(id)) {
            employees.remove(id);
            System.out.println("ID'si " + id + " olan çalışan silindi.");
        } else {
            System.out.println("ID'si " + id + " olan çalışan bulunamadı.");
        }
    }
}
