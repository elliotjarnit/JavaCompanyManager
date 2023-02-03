import java.awt.*;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

// TITLE BAR IS 26 PIXELS ON MACOS
public class CompanyManagerGUI {
    private static Frame window;
    private static Panel leftSidePanel;
    private static List employeeList;
    private static Panel companyPanel;
    private static Label companyLabel;
    private static Panel employeePanel;
    private static Label employeeLabel;
    private static Panel employeeControls;
    private static Button buttonChangeRole;
    private static Button buttonFire;
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
            "CEO",
            "COO",
            "CFO",
            "Artificial Intelligence Developer",
            "Cloud Engineer",
            "Cybersecurity Analyst",
            "Data Scientist",
            "Full-Stack Developer",
            "Web Designer",
            "Front-End Developer",
            "DevOps Engineer",
            "Data scientist",
            "Android engineer",
            "Software sales rep ",
            "Software product manager",
            "Product marketing manager",
            "Project manager",
            "QA Tester",
            "UX Designers",
            "Digital Marketing",
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
                    updateCompanyPanel();
                });
            }
        };
        companyPanel.add(companySelector);
        companyLabel = new Label("No company selected");
        companyPanel.add(companyLabel);

        // Employee
        employeePanel = new Panel();
        employeeLabel = new Label("No employee selected");
        employeeControls = new Panel();
        employeeControls.setLayout(new GridLayout(1, 1));
        buttonFire = new Button("Fire") {
            {
                addActionListener(e -> {
                    if (selectedEmployeeIndex != -1) {
                        int id = companies.get(selectedCompanyIndex).getAllMembers()[selectedEmployeeIndex].getId();
                        companies.get(selectedCompanyIndex).removeMember(id);
                        selectedEmployeeIndex = -1;
                        updateEmployeeList();
                        updateEmployeePanel();
                    }
                });
            }
        };
        employeeControls.add(buttonFire);
        employeePanel.add(employeeLabel);
        employeePanel.add(employeeControls);

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
                    fileDialog.setFilenameFilter((dir, name) -> name.endsWith(".mrkick"));
                    fileDialog.setVisible(true);
                    if (fileDialog.getFile() != null) {
                        String filename = fileDialog.getDirectory() + fileDialog.getFile();
                        if (!filename.endsWith(".mrkick")) {
                            filename += ".mrkick";
                        }
                        Team[] companiesArray = new Team[companies.size()];
                        companiesArray = companies.toArray(companiesArray);
                        SaveManager.saveCompany(companiesArray, filename);
                    }
                });
            }
        });
        menuBarFile.add(new MenuItem("Open") {
            {
                addActionListener(e -> {
                    FileDialog fileDialog = new FileDialog(window, "Save", FileDialog.LOAD);
                    fileDialog.setFilenameFilter((dir, name) -> name.endsWith(".mrkick"));
                    fileDialog.setVisible(true);
                    if (fileDialog.getFile() != null) {
                        String filename = fileDialog.getDirectory() + fileDialog.getFile();
                        if (filename.endsWith(".mrkick")) {
                            Team[] companiesArray = SaveManager.loadCompany(filename);
                            companies.clear();
                            for (Team t : companiesArray) {
                                companies.add(t);
                            }
                            selectedCompanyIndex = -1;
                            selectedEmployeeIndex = -1;
                            updateCompanySelector();
                            updateEmployeePanel();
                            updateCompanyPanel();
                        }
                    }
                });
            }
        });
        menuBarFile.add(new MenuItem("Clear") {
            {
                addActionListener(e -> {
                    companies.clear();
                    selectedCompanyIndex = -1;
                    selectedEmployeeIndex = -1;
                    updateCompanySelector();
                    updateEmployeePanel();
                    updateEmployeeList();
                    updateCompanyPanel();
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

                    updateCompanySelector();
                    selectedCompanyIndex = companies.size() - 1;
                    selectedEmployeeIndex = -1;
                    companySelector.select(selectedCompanyIndex);
                    updateEmployeeList();
                    updateCompanyPanel();
                    updateEmployeePanel();
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
        employeePanel.remove(employeeLabel);
        String label;
        if (selectedEmployeeIndex < 0) {
            label = "No employee selected";
        } else {
            label = "<html>";
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
        }
        employeeLabel = new Label(label);
        employeePanel.add(employeeLabel);
        employeePanel.revalidate();
        employeePanel.repaint();
    }

    public static void updateCompanySelector() {
        companySelector.removeAll();
        for (Team t : companies) {
            companySelector.add(t.getName());
        }
    }

    public static void updateCompanyPanel() {
        companyPanel.remove(companyLabel);
        String label;
        if (selectedCompanyIndex < 0) {
            label = "No company selected";
        } else {
            label = "<html>";
            label += "Company Info";
            label += "<br/><br/>ID: ";
            label += companies.get(selectedCompanyIndex).getId();
            label += "<br/>Name: ";
            label += companies.get(selectedCompanyIndex).getName();
            label += "<br/>Number of Employees: ";
            label += companies.get(selectedCompanyIndex).getNumberOfEmployees();
            label += "<br/>Average Salary: ";
            label += "$" + companies.get(selectedCompanyIndex).getAverageSalary();
            label += "</html>";
        }
        companyLabel = new Label(label);
        companyPanel.add(companyLabel);
        companyPanel.revalidate();
        companyPanel.repaint();
    }
}
