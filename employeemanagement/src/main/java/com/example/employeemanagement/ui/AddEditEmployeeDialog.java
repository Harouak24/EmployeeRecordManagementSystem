package com.example.employeemanagement.ui;

import net.miginfocom.swing.MigLayout;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class AddEditEmployeeDialog extends JDialog {

    private JTextField employeeIdField;
    private JTextField fullNameField;
    private JTextField jobTitleField;
    private JTextField departmentField;
    private JTextField hireDateField;
    private JTextField contactInfoField;
    private JTextField addressField;

    private final boolean isEditMode;
    private final String baseUrl = "http://localhost:8080/api/employees";

    public AddEditEmployeeDialog(JFrame parent, boolean isEditMode, Map<String, String> existingData) {
        super(parent, isEditMode ? "Edit Employee" : "Add Employee", true);
        this.isEditMode = isEditMode;

        // Dialog setup
        setSize(400, 400);
        setLayout(new MigLayout("", "[][grow]", "[][][][][][][][]20[]"));

        // Input fields
        add(new JLabel("Employee ID:"));
        employeeIdField = new JTextField(20);
        add(employeeIdField, "grow, wrap");

        add(new JLabel("Full Name:"));
        fullNameField = new JTextField(20);
        add(fullNameField, "grow, wrap");

        add(new JLabel("Job Title:"));
        jobTitleField = new JTextField(20);
        add(jobTitleField, "grow, wrap");

        add(new JLabel("Department:"));
        departmentField = new JTextField(20);
        add(departmentField, "grow, wrap");

        add(new JLabel("Hire Date (YYYY-MM-DD):"));
        hireDateField = new JTextField(20);
        add(hireDateField, "grow, wrap");

        add(new JLabel("Contact Info:"));
        contactInfoField = new JTextField(20);
        add(contactInfoField, "grow, wrap");

        add(new JLabel("Address:"));
        addressField = new JTextField(20);
        add(addressField, "grow, wrap");

        // Populate fields if in edit mode
        if (isEditMode && existingData != null) {
            populateFields(existingData);
            employeeIdField.setEnabled(false); // Disable ID editing in edit mode
        }

        // Save button
        JButton saveButton = new JButton(isEditMode ? "Update" : "Save");
        saveButton.addActionListener(e -> handleSave());
        add(saveButton, "span, align center");

        setLocationRelativeTo(parent);
    }

    private void populateFields(Map<String, String> data) {
        employeeIdField.setText(data.get("employeeId"));
        fullNameField.setText(data.get("fullName"));
        jobTitleField.setText(data.get("jobTitle"));
        departmentField.setText(data.get("department"));
        hireDateField.setText(data.get("hireDate"));
        contactInfoField.setText(data.get("contactInfo"));
        addressField.setText(data.get("address"));
    }

    private void handleSave() {
        try {
            RestTemplate restTemplate = new RestTemplate();

            // Create the request body
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("employeeId", employeeIdField.getText());
            requestBody.put("fullName", fullNameField.getText());
            requestBody.put("jobTitle", jobTitleField.getText());
            requestBody.put("department", departmentField.getText());
            requestBody.put("hireDate", hireDateField.getText());
            requestBody.put("contactInfo", contactInfoField.getText());
            requestBody.put("address", addressField.getText());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

            if (isEditMode) {
                // Update existing employee
                restTemplate.put(baseUrl + "/" + employeeIdField.getText(), entity);
                JOptionPane.showMessageDialog(this, "Employee updated successfully!");
            } else {
                // Add new employee
                ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, entity, String.class);
                JOptionPane.showMessageDialog(this, "Employee added successfully!");
            }

            dispose(); // Close the dialog after saving
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Failed to save employee: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}