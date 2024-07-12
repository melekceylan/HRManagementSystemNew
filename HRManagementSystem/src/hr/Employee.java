package hr;

public class Employee {
    private int id;
    private String firstName;
    private String lastName;
    private String department;
    private String phoneNumber;
    private String password;
    private int leaveDays;

    public Employee(int id, String firstName, String lastName, String department, String phoneNumber, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.leaveDays = 15;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDepartment() {
        return department;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public int getLeaveDays() {
        return leaveDays;
    }

    public void setLeaveDays(int leaveDays) {
        this.leaveDays = leaveDays;
    }

    @Override
    public String toString() {
        return "Çalışan ID: " + id + ", İsim: " + firstName + " " + lastName + ", Departman: " + department +
                ", Telefon: " + phoneNumber + ", Kalan izin günleri: " + leaveDays;
    }
}
