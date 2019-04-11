package sample;

public class Staff {
    private String firstname;
    private String lastname;
    private String username;
    private String perm_desc;

    public Staff(String firstname, String lastname, String username, String perm_desc) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.perm_desc = perm_desc;
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
}
