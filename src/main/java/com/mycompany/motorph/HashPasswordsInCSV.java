package com.mycompany.motorph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashPasswordsInCSV {

    // Hashing method for password
    public static String hashPassword(String password) {
        try {
            // Using SHA-256 hashing algorithm
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

    public static void main(String[] args) {
        // Define the input and output file paths
        String inputFilePath = "src/main/resources/credentials.csv";  // Path to your credentials.csv
        String outputFilePath = "src/main/resources/credentials_hashed.csv";  // Path to save the new file

        // Create a BufferedReader and BufferedWriter
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String username = parts[3].trim();  // Username column
                    String password = parts[4].trim();  // Password column (plaintext)

                    // Hash the password
                    String hashedPassword = hashPassword(password);

                    // Write the new line to the output file with the hashed password
                    writer.write(parts[0] + "," + parts[1] + "," + parts[2] + "," + username + "," + hashedPassword);
                    writer.newLine();
                }
            }

            System.out.println("Passwords have been hashed and saved to 'credentials_hashed.csv'.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
