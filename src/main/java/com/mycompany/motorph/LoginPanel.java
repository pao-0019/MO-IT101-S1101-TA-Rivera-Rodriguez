package com.mycompany.motorph;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Map;
import java.util.HashMap;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class LoginPanel {
    private JPanel panel;
    private final MainFrame mainFrame;
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    
    private Map<String, String> credentialsMap;
    

    public LoginPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        loadCredentials();  // Load the credentials from the credentials.csv file
        initialize();
    }
    
    // Load credentials from the CSV file (credentials.csv)
    private void loadCredentials() {
    credentialsMap = new HashMap<>();
    try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/credentials.csv"))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 5) {
                // Assuming the username is in the 3rd column (index 2) and the password is in the 4th column (index 3)
                String username = parts[3].trim();  // Username (3rd column)
                String password = parts[4].trim();  // Password (4th column)
                credentialsMap.put(username, password); // Add to map
            }
        }

        // Debugging: Print out the map to verify the loaded credentials
        System.out.println("Loaded credentials: " + credentialsMap);
    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Error reading credentials file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    // Initialize the login panel UI
    private void initialize() {
        panel = new JPanel(new BorderLayout());
        
        // Left panel (Blue background with centered header)
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(new Color(0x004C99)); // Blue color
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER; // Center the components

        JLabel headerLabel = new JLabel("MotorPH", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 36));
        headerLabel.setForeground(Color.WHITE);

        JLabel subHeaderLabel = new JLabel("Employee Management System", JLabel.CENTER);
        subHeaderLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        subHeaderLabel.setForeground(Color.WHITE);

        // Center the header and subheader in the left panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        leftPanel.add(headerLabel, gbc);

        gbc.gridy = 1;
        leftPanel.add(subHeaderLabel, gbc);

        // Right panel (Login form)
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Username label and field
        JLabel usernameLabel = new JLabel("Username:");
         usernameField = new JTextField(20);

        // Password label and field
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        
        // Show password checkbox
        JCheckBox showPasswordCheckBox = new JCheckBox("Show Password");
        showPasswordCheckBox.addActionListener(e -> {
            if (showPasswordCheckBox.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        });

        // Login button
        JButton loginButton = new JButton("Log In");
        loginButton.setPreferredSize(new Dimension(150, 40));
        loginButton.setBackground(Color.decode("#004C99"));
        loginButton.setForeground(Color.WHITE);

        // Add components to the right panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        rightPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        rightPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        rightPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        rightPanel.add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        rightPanel.add(showPasswordCheckBox, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        rightPanel.add(loginButton, gbc);
        

        // Action Listener for login button
        loginButton.addActionListener(e -> login(usernameField, passwordField));

        // Action Listener for pressing Enter key (password field)
        passwordField.addActionListener(e -> login(usernameField, passwordField));

        // Action Listener for pressing Enter key (username field)
        usernameField.addActionListener(e -> login(usernameField, passwordField));

        // Add left and right panels to the main panel (split layout)
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(450); // Control how much space each side takes (250 px for top side)

        // Add the split pane to the panel
        panel.add(splitPane, BorderLayout.CENTER);
    }

    
    // Validate credentials by comparing username and hashed password
    private void login(JTextField usernameField, JPasswordField passwordField) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (validateCredentials(username, password)) {
             // Create a custom message dialog that auto-closes
        JLabel successMessage = new JLabel("Log in Successfully!", JLabel.CENTER);
        successMessage.setFont(new Font("Arial", Font.BOLD, 16));
        successMessage.setForeground(Color.BLUE);
        
          // Create a pop-up window (JWindow) to display the success message
        JWindow window = new JWindow();
        window.getContentPane().add(successMessage);
        window.setSize(300, 100);  // Size of the pop-up
        window.setLocationRelativeTo(panel);  // Center it relative to the panel
        window.setVisible(true);

        // Timer to close the message (1500 milliseconds)
        Timer timer = new Timer(1500, e -> window.dispose());
        timer.setRepeats(false);
        timer.start();
        
         // Clear the text fields after successful login
        clearFields(); 
            
             System.out.println("Login successful, opening home page...");
             mainFrame.showHome(); 
        } else {
            JOptionPane.showMessageDialog(panel, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Validate credentials by comparing username and hashed password
   private boolean validateCredentials(String username, String password) {
    // Fetch the stored password (plain text) from credentialsMap for the given username
    String storedPassword = credentialsMap.get(username);  // This is where we fetch the password from the map

     System.out.println("Attempting to login with username: " + username);
     System.out.println("Stored password for " + username + ": " + storedPassword);
     
    // Check if the stored password exists for the given username
    if (storedPassword != null) {
        // Directly compare the plain text password with the stored password (no hashing here)
        return storedPassword.equals(password);  // Compare plain text password
    }

    return false;
    }

   // Method to clear the fields when logging out
    public void clearFields() {
        usernameField.setText(""); // Clear the username field
        passwordField.setText(""); // Clear the password field
    }
    
    // Get the panel for the LoginPanel
    public JPanel getPanel() {
        return panel;
    }

    // HashUtil class to hash password using SHA-256
    public static class HashUtil {
        public static String hashPassword(String password) {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(password.getBytes());
                StringBuilder hexString = new StringBuilder();
                for (byte b : hash) {
                    hexString.append(String.format("%02x", b));
                }
                return hexString.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
