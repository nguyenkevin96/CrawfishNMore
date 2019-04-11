package sample;

public class Employee {
   private int employee_id;
   private int permType_id;
   private String first_nameE;
   private String last_name;
   private int meal_status;

    public Employee(int permType_id, String first_name, String last_name, int meal_status) {
        this.permType_id = permType_id;
        this.first_nameE = first_name;
        this.last_name = last_name;
        this.meal_status = meal_status;
    }

    public Employee() {
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public int getPermType_id() {
        return permType_id;
    }

    public void setPermType_id(int permType_id) {
        this.permType_id = permType_id;
    }

    public String getFirst_name() {
        return first_nameE;
    }

    public void setFirst_name(String first_name) {
        this.first_nameE = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getMeal_status() {
        return meal_status;
    }

    public void setMeal_status(int meal_status) {
        this.meal_status = meal_status;
    }

    @Override
    public String toString(){
        return String.format("%s %s", first_nameE, last_name);
    }
}
