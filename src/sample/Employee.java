package sample;

public class Employee {
    private String firstName;
    private String lastName;
    private String phoneE;
    private String userName;
    private int roleE;

    /*
        Constructor for creating employee
     */

    public Employee(String firstName, String lastName, String phoneE, String userName, int roleE) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneE = phoneE;
        this.userName = userName;
        this.roleE = roleE;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneE() {
        return phoneE;
    }

    public void setPhoneE(String phoneE) {
        this.phoneE = phoneE;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getRoleE() {
        return roleE;
    }

    public void setRoleE(int roleE) {
        this.roleE = roleE;
    }
}
