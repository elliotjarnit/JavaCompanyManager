import java.util.ArrayList;

public class Team
{
    private String name;
    private ArrayList<Member> employees;
    private int id;

    public Team(String name)
    {
        this.name = name;
        this.employees = new ArrayList<Member>();
        this.id = (int) (Math.random() * 1000000000);
    }

    public Member getMember(int id)
    {
        for (int i = 0; i < employees.size(); i++){
            if (this.employees.get(i).getId() == id){
                return employees.get(i);
            }
        }
        return null;
    }

    public Member[] getAllMembers() {
        Member[] members = new Member[employees.size()];
        for (int i = 0; i < employees.size(); i++) {
            members[i] = employees.get(i);
        }
        return members;
    }

    public void removeMember(int id)
    {
        for (int i = 0; i < employees.size(); i++){
            if (this.employees.get(i).getId() == id){
                employees.remove(i);
            }
        }
    }

    public void addMember(Member member)
    {
        employees.add(member);
    }

    public int getAverageSalary() {
        if (employees.size() == 0) return 0;
        int total = 0;
        for (int i = 0; i < employees.size(); i++) {
            total += employees.get(i).getSalary();
        }
        return total / employees.size();
    }

    public int getNumberOfEmployees() {
        return employees.size();
    }

    public void promote(String name, String newRole){
        for (int i = 0; i < employees.size(); i++){
            if (this.employees.get(i).getName().equals(name)){
                employees.get(i).promote(newRole);
            }
        }
    }

    public void demote(String name, String newRole){
        for (int i = 0; i < employees.size(); i++){
            if (this.employees.get(i).getName().equals(name)){
                employees.get(i).demote(newRole);
            }
        }
    }

    public String toString(){
        String temp = "Team " + this.name + "Employees: ";
        for (int i = 0; i < employees.size(); i++){
            temp += employees.get(i).getName();
        }
        return temp;
    }

    public String convertToJSON() {
        String json = "{";
        json += "\"name\": \"" + name + "\",";
        json += "\"id\": " + id + ",";
        json += "\"employees\": [";
        for (int i = 0; i < employees.size(); i++) {
            json += employees.get(i).convertToJSON();
            if (i != employees.size() - 1) {
                json += ",";
            }
        }
        json += "]";
        json += "}";
        return json;
    }

    // Getters
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
}
