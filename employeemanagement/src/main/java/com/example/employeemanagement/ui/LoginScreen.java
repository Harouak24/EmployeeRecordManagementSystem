package com.example.employeemanagement.ui;

import net.miginfocom.swing.MigLayout;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class LoginScreen extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginScreen() {
        // JFrame setup
        setTitle("Employee Management System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLayout(new MigLayout("", "[][grow]", "[][]20[]"));

        // Username field
        add(new JLabel("Username:"));
        usernameField = new JTextField(20);
        add(usernameField, "grow, wrap");

        // Password field
        add(new JLabel("Password:"));
        passwordField = new JPasswordField(20);
        add(passwordField, "grow, wrap");

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(this::handleLogin);
        add(loginButton, "span, align center");

        // Center the JFrame
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Handle login action
    private void handleLogin(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Validate input fields
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username and Password are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Call backend API for authentication
            boolean authenticated = authenticate(username, password);
            if (authenticated) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                new EmployeeManagementScreen(username); // Open main screen
                dispose(); // Close login screen
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error connecting to server: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Authenticate using backend API
    private boolean authenticate(String username, String password) {
        // API endpoint
        String url = "http://localhost:8080/api/auth/login";

        // Create request body
        String requestBody = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

        // Create HTTP entity
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // Make the POST request using RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        // Parse and return the response
        return Boolean.parseBoolean(response.getBody());
    }

    public static void main(String[] args) {
        new LoginScreen();
    }
}