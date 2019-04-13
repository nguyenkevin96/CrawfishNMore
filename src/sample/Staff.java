package sample;

public class Staff {
    private int staffId;
    private String firstname;
    private String lastname;
    private int loginId;
    private String permtype_id;
    private String username;
    private String perm_desc;

    public Staff(String firstname, String lastname, int loginId, String username, String perm_desc) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.loginId = loginId;
        this.username = username;
        this.perm_desc = perm_desc;
    }

    public Staff(String firstname, String lastname, String username, String perm_desc) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.perm_desc = perm_desc;
    }

    public Staff(int loginId, int staffId, String firstname, String lastname, String username, String permtype_id) {
        this.loginId = loginId;
        this.staffId = staffId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.permtype_id = permtype_id;
    }

    public Staff() {
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public int getLoginId() {
        return loginId;
    }

    public void setLoginId(int loginId) {
        this.loginId = loginId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPerm_desc() {
        return perm_desc;
    }

    public void setPerm_desc(String perm_desc) {
        this.perm_desc = perm_desc;
    }

    @Override
    public String toString(){
        return "StaffID: " + staffId +
                "\nFirstName: " + firstname +
                "\nLastName: " + lastname +
                "\nLoginID: " + loginId +
                "\nPermTypeID: " + permtype_id;
    }
}
