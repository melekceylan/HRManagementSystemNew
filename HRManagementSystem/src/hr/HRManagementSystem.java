package hr;

import java.util.HashMap;
import java.util.Scanner;

public class HRManagementSystem {
    private HashMap<Integer, Employee> employees;
    private AdminService adminService;
    private DataStorage dataStorage;
    private int employeeIdCounter;
    private Scanner scanner;

    public HRManagementSystem() {
        dataStorage = new DataStorage();
        employees = dataStorage.loadEmployees();
        adminService = new AdminService(employees);
        scanner = new Scanner(System.in);
        employeeIdCounter = dataStorage.getMaxEmployeeId() + 1;
    }

    public void mainMenu() {
        while (true) {
            System.out.println("1. Kullanıcı Oluştur");
            System.out.println("2. Kullanıcı Girişi");
            System.out.println("3. Yönetici Girişi");
            System.out.print("Bir seçenek seçin: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createUser();
                    break;
                case 2:
                    userLogin();
                    break;
                case 3:
                    adminLogin();
                    break;
                default:
                    System.out.println("Geçersiz seçenek. Lütfen tekrar deneyin.");
            }
        }
    }

    private void createUser() {
        System.out.print("Adınızı girin: ");
        String firstName = scanner.nextLine();
        System.out.print("Soyadınızı girin: ");
        String lastName = scanner.nextLine();
        System.out.print("Departmanınızı girin: ");
        String department = scanner.nextLine();
        System.out.print("Telefon numaranızı girin: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Şifrenizi girin: ");
        String password = scanner.nextLine();

        Employee employee = new Employee(employeeIdCounter++, firstName, lastName, department, phoneNumber, password);
        employees.put(employee.getId(), employee);
        dataStorage.saveEmployee(employee);
        System.out.println("Kullanıcı başarıyla oluşturuldu. Kullanıcı ID'niz " + employee.getId());
    }

    private void userLogin() {
        System.out.print("Kullanıcı ID'nizi girin: ");
        int userId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Şifrenizi girin: ");
        String password = scanner.nextLine();

        if (employees.containsKey(userId) && employees.get(userId).getPassword().equals(password)) {
            userMenu(employees.get(userId));
        } else {
            System.out.println("Geçersiz ID veya şifre.");
        }
    }

    private void userMenu(Employee employee) {
        while (true) {
            System.out.println("1. İzin Talep Et");
            System.out.println("2. Kişisel Bilgileri Görüntüle");
            System.out.println("3. Ana Menüye Dön");
            System.out.print("Bir seçenek seçin: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    requestLeave(employee);
                    break;
                case 2:
                    System.out.println(employee);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Geçersiz seçenek. Lütfen tekrar deneyin.");
            }
        }
    }

    private void requestLeave(Employee employee) {
        System.out.print("Talep edilen izin gün sayısını girin: ");
        int days = scanner.nextInt();
        scanner.nextLine();
        if (days <= employee.getLeaveDays()) {
            employee.setLeaveDays(employee.getLeaveDays() - days);
            dataStorage.updateLeaveDays(employee.getId(), employee.getLeaveDays());
            System.out.println("İzin onaylandı. Kalan izin günleriniz: " + employee.getLeaveDays());
        } else {
            System.out.println("Yeterli izin gününüz yok.");
        }
    }

    private void adminLogin() {
        System.out.print("Yönetici şifresini girin: ");
        String password = scanner.nextLine();
        if (password.equals("admin")) { // Basit yönetici şifre kontrolü
            adminMenu();
        } else {
            System.out.println("Geçersiz yönetici şifresi.");
        }
    }

    private void adminMenu() {
        while (true) {
            System.out.println("1. Tüm Çalışanları Görüntüle");
            System.out.println("2. Çalışanı Sil");
            System.out.println("3. Ana Menüye Dön");
            System.out.print("Bir seçenek seçin: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    adminService.viewAllEmployees();
                    break;
                case 2:
                    System.out.print("Silinecek çalışan ID'sini girin: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    adminService.deleteEmployee(id);
                    dataStorage.deleteEmployee(id);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Geçersiz seçenek. Lütfen tekrar deneyin.");
            }
        }
    }
    //main çalışmadığı için buraya yazıp çalıştırdım.
    public static void main(String[] args) {
        HRManagementSystem hrSystem = new HRManagementSystem();
        hrSystem.mainMenu();
    }
}


