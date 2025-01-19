package com.example.employeemanagement.ui;

import net.miginfocom.swing.MigLayout;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeManagementScreen extends JFrame {

    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private static final String BASE_URL = "http://localhost:8080/api/employees";

    public EmployeeManagementScreen(String username) {
        // JFrame setup
        setTitle("Employee Management - Logged in as: " + username);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new MigLayout("", "[grow]", "[][grow][]"));

        // Table setup
        String[] columnNames = {"Employee ID", "Full Name", "Job Title", "Department", "Hire Date"};
        tableModel = new DefaultTableModel(columnNames, 0);
        employeeTable = new JTable(tableModel);
        add(new JScrollPane(employeeTable), "grow, wrap");

        // Buttons for CRUD operations
        JButton addButton = new JButton("Add Employee");
        JButton editButton = new JButton("Edit Employee");
        JButton deleteButton = new JButton("Delete Employee");
        JButton refreshButton = new JButton("Refresh Data");

        addButton.addActionListener(this::handleAddEmployee);
        editButton.addActionListener(this::handleEditEmployee);
        deleteButton.addActionListener(this::handleDeleteEmployee);
        refreshButton.addActionListener(e -> populateEmployeeTable());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        add(buttonPanel, "dock south");
        addReportButton(buttonPanel);
        // Populate table on startup
        populateEmployeeTable();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Populate employee table with backend data
    private void populateEmployeeTable() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(BASE_URL, HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), new ParameterizedTypeReference<List<Map<String, Object>>>() {});
            List<Map<String, Object>> employees = response.getBody();

            tableModel.setRowCount(0); // Clear existing rows

            if (employees != null) {
                for (Map<String, Object> employee : employees) {
                    tableModel.addRow(new Object[]{
                            employee.get("employeeId"),
                            employee.get("fullName"),
                            employee.get("jobTitle"),
                            employee.get("department"),
                            employee.get("hireDate")
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to load data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Handle Add Employee
    private void handleAddEmployee(ActionEvent e) {
        AddEditEmployeeDialog dialog = new AddEditEmployeeDialog(this, false, null);
        dialog.setVisible(true);
        populateEmployeeTable(); // Refresh table after adding
    }

    // Handle Edit Employee
    private void handleEditEmployee(ActionEvent e) {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an employee to edit.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Map<String, String> existingData = new HashMap<>();
        existingData.put("employeeId", (String) tableModel.getValueAt(selectedRow, 0));
        existingData.put("fullName", (String) tableModel.getValueAt(selectedRow, 1));
        existingData.put("jobTitle", (String) tableModel.getValueAt(selectedRow, 2));
        existingData.put("department", (String) tableModel.getValueAt(selectedRow, 3));
        existingData.put("hireDate", (String) tableModel.getValueAt(selectedRow, 4));

        AddEditEmployeeDialog dialog = new AddEditEmployeeDialog(this, true, existingData);
        dialog.setVisible(true);
        populateEmployeeTable(); // Refresh table after editing
    }

    // Handle Delete Employee
    private void handleDeleteEmployee(ActionEvent e) {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an employee to delete.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String employeeId = (String) tableModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete employee " + employeeId + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.delete(BASE_URL + "/" + employeeId);
                JOptionPane.showMessageDialog(this, "Employee deleted successfully.");
                populateEmployeeTable(); // Refresh table
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Failed to delete employee: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void addReportButton(JPanel buttonPanel) {
        JButton reportButton = new JButton("Generate CSV Report");
        reportButton.addActionListener(this::handleGenerateReport);
        buttonPanel.add(reportButton);
    }
    
    private void handleGenerateReport(ActionEvent e) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            byte[] report = restTemplate.getForObject("http://localhost:8080/api/employees/report", byte[].class);
    
            // Save the report locally
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setSelectedFile(new java.io.File("Employee_Report.csv"));
            int choice = fileChooser.showSaveDialog(this);
    
            if (choice == JFileChooser.APPROVE_OPTION) {
                java.io.File file = fileChooser.getSelectedFile();
                java.nio.file.Files.write(file.toPath(), report);
                JOptionPane.showMessageDialog(this, "Report saved successfully at " + file.getAbsolutePath());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Failed to generate report: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}