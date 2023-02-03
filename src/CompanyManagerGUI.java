import java.awt.*;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

// TITLE BAR IS 26 PIXELS ON MACOS
public class CompanyManagerGUI {
    private static Frame window;
    private static Panel leftSidePanel;
    private static List employeeList;
    private static Panel companyPanel;
    private static Panel employeePanel;
    private static Choice companySelector;
    private static MenuBar menuBar;
    private static Menu menuBarFile;
    private static Menu menuBarCompanies;
    private static Menu menuBarEmployees;
    private static Dialog newEmployeeDialog;
    private static Dialog newCompanyDialog;
    private static int selectedEmployeeIndex;
    private static int selectedCompanyIndex;
    private static ArrayList<Team> companies;
    private static String[] roles;
    public CompanyManagerGUI() {
        // Initialize variables
        selectedEmployeeIndex = -1;
        selectedCompanyIndex = -1;
        companies = new ArrayList<Team>();
        roles = new String[]{
            "Chief Technology Officer",
            "VP of Engineering",
            "Director of Software Development",
            "Lead Architect",
            "Manager of Software Development",
            "Senior Software Engineer",
            "Software Engineer",
            "Associate Software Engineer",
            "Intern Software Developer",
            "QA Engineer"
        };

        // Create window
        constructWindow();
    }

    public void constructWindow() {
        window = new Frame("Company Manager");

        this.createWindowWidgets();
        this.createWindowExtras();
        this.createWindowDialogs();

        this.populateWindowWidgets();
        this.populateWindowExtras();

        this.showWindow();
    }

    public void createWindowWidgets() {
        leftSidePanel = new Panel();
        leftSidePanel.setLayout(new GridLayout(2, 1));

        // Company panel
        companyPanel = new Panel();
        companyPanel.add(new Label("Company: "));
        companySelector = new Choice() {
            {
                for (Team t : companies) {
                    add(t.getName());
                }
                addItemListener(e -> {
                    selectedCompanyIndex = getSelectedIndex();
                    selectedEmployeeIndex = -1;
                    updateEmployeeList();
                    updateEmployeePanel();
                });
            }
        };
        companyPanel.add(companySelector);

        // Employee
        employeePanel = new Panel();
        employeePanel.setLayout(new FlowLayout());
        employeePanel.add(new Label("No employee selected"));

        // Right side
        employeeList = new List(10) {
            {
                addItemListener(e -> {
                    selectedEmployeeIndex = getSelectedIndex();
                    updateEmployeePanel();
                });
            }
        };
    }

    public void createWindowExtras() {
        menuBar = new MenuBar();
        menuBarFile = new Menu("File");
        menuBarCompanies = new Menu("Companies");
        menuBarEmployees = new Menu("Employees");
        menuBarFile.add(new MenuItem("Save") {
            {
                addActionListener(e -> {
                    FileDialog fileDialog = new FileDialog(window, "Save", FileDialog.SAVE);
                    fileDialog.setFilenameFilter((dir, name) -> name.endsWith(".kick"));
                    fileDialog.setVisible(true);
                    if (fileDialog.getFile() != null) {
                        String filename = fileDialog.getDirectory() + fileDialog.getFile();
                        if (!filename.endsWith(".kick")) {
                            filename += ".kick";
                        }
//                        SaveManager.saveCompany(companies, filename);
                    }
                });
            }
        });
        menuBarCompanies.add(new MenuItem("New") {
            {
                addActionListener(e -> {
                    newCompanyDialog.setVisible(true);
                });
            }
        });
        menuBarEmployees.add(new MenuItem("New") {
            {
                addActionListener(e -> {
                    newEmployeeDialog.setVisible(true);
                });
            }
        });
    }

    public void createWindowDialogs() {
        // NEW EMPLOYEE DIALOG

        // Create dialog
        newEmployeeDialog = new Dialog(window, "New Employee", true);
        newEmployeeDialog.setResizable(false);
        newEmployeeDialog.setSize(300, 200);
        newEmployeeDialog.setLayout(new GridLayout(5, 2));

        // Create widgets
        TextField name = new TextField(10);
        Choice gender = new Choice();
        gender.add("Male");
        gender.add("Female");
        gender.add("Other");
        Choice roleSelector = new Choice();
        for (String r : roles) roleSelector.add(r);
        TextField salary = new TextField(10);

        // Add widgets
        newEmployeeDialog.add(new Label("Name:"));
        newEmployeeDialog.add(name);
        newEmployeeDialog.add(new Label("Gender:"));
        newEmployeeDialog.add(gender);
        newEmployeeDialog.add(new Label("Role:"));
        newEmployeeDialog.add(roleSelector);
        newEmployeeDialog.add(new Label("Salary:"));
        newEmployeeDialog.add(salary);
        newEmployeeDialog.add(new Button("OK") {
            {
                addActionListener(e -> {
                    // Add new employee to company
                    companies.get(selectedCompanyIndex).addMember(new Member(name.getText(), roleSelector.getSelectedItem(), gender.getSelectedIndex(), parseInt(salary.getText())));
                    updateEmployeeList();
                    newEmployeeDialog.setVisible(false);
                });
            }
        });
        newEmployeeDialog.add(new Button("Cancel") {
            {
                addActionListener(e -> {
                    newEmployeeDialog.setVisible(false);
                });
            }
        });

        // NEW COMPANY DIALOG

        // Create dialog
        newCompanyDialog = new Dialog(window, "New Company", true);
        newCompanyDialog.setResizable(false);
        newCompanyDialog.setSize(300, 150);
        newCompanyDialog.setLayout(new GridLayout(2, 2));

        // Add widgets
        newCompanyDialog.add(new Label("Name:"));
        TextField companyNameField = new TextField(10);
        newCompanyDialog.add(companyNameField);
        newCompanyDialog.add(new Button("OK") {
            {
                addActionListener(e -> {
                    // Set teams selector options
                    companies.add(new Team(companyNameField.getText()));

                    updateCompanyList();
                    selectedCompanyIndex = companies.size() - 1;
                    companySelector.select(selectedCompanyIndex);
                    updateEmployeeList();
                    newCompanyDialog.setVisible(false);
                });
            }
        });
        newCompanyDialog.add(new Button("Cancel") {
            {
                addActionListener(e -> {
                    newCompanyDialog.setVisible(false);
                });
            }
        });
    }

    public void populateWindowWidgets() {
        // Left side
        leftSidePanel.add(companyPanel);
        leftSidePanel.add(employeePanel);

        // Window
        window.add(leftSidePanel);
        window.add(employeeList);
    }

    public void populateWindowExtras() {
        menuBar.add(menuBarFile);
        menuBar.add(menuBarCompanies);
        menuBar.add(menuBarEmployees);

        window.setMenuBar(menuBar);
    }

    public void showWindow() {
        window.setLayout(new GridLayout(1, 2));
        window.setSize(500, 400);
        window.setVisible(true);
    }

    public static void updateEmployeeList() {
        employeeList.removeAll();
        for (Member m : companies.get(selectedCompanyIndex).getAllMembers()) {
            employeeList.add(m.getName());
        }
    }

    public static void updateEmployeePanel() {
        employeePanel.removeAll();
        if (selectedEmployeeIndex < 0) {
            employeePanel.add(new Label("No employee selected"));
        } else {
            String label = "<html>";
            label += "Employee Info";
            label += "<br/><br/>ID: ";
            label += companies.get(selectedCompanyIndex).getAllMembers()[selectedEmployeeIndex].getId();
            label += "<br/>Name: ";
            label += companies.get(selectedCompanyIndex).getAllMembers()[selectedEmployeeIndex].getName();
            label += "<br/>Role: ";
            label += companies.get(selectedCompanyIndex).getAllMembers()[selectedEmployeeIndex].getRole();
            label += "<br/>Gender: ";
            label += companies.get(selectedCompanyIndex).getAllMembers()[selectedEmployeeIndex].getGender();
            label += "<br/>Salary: ";
            label += "$" + companies.get(selectedCompanyIndex).getAllMembers()[selectedEmployeeIndex].getSalary();
            label += "</html>";
            employeePanel.add(new Label(label));
        }
        employeePanel.revalidate();
        employeePanel.repaint();
    }

    public static void updateCompanyList() {
        companySelector.removeAll();
        for (Team t : companies) {
            companySelector.add(t.getName());
        }
    }
}
