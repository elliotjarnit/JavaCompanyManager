import java.io.Serializable;

public class Member implements Serializable
{
    private String name;
    private int id;
    private String role;
    private int gender;  // 0 = Male, 1 = Female, 2 = Other
    private int salary;

    public Member(String name, String role, int gender, int salary){
        this.id = (int) (Math.random() * 1000000000);
        this.name = name;
        this.role = role;
        this.gender = gender;
        this.salary = salary;
    }

    public void promote(String newRole){
        this.role = newRole;
    }

    public void demote(String newRole){
        this.role = newRole;
    }

    public String toString(){
        return "Employee " + this.name + "id " + this.id + "role " + this.role;
    }

    public String convertToJSON() {
        String json = "{";
        json += "\"name\": \"" + name + "\",";
        json += "\"id\": " + id + ",";
        json += "\"role\": \"" + role + "\",";
        json += "\"salary\": \"" + salary + "\",";
        json += "\"gender\": \"" + getGender() + "\"";
        json += "}";
        return json;
    }

    // Getters
    public String getName() {
        return name;
    }
    public String getRole(){
        return role;
    }
    public int getId(){
        return id;
    }
    public int getSalary(){
        return salary;
    }
    public String getGender() {
        if (gender == 0) return "Male";
        if (gender == 1) return "Female";
        return "Other";
    }
}

