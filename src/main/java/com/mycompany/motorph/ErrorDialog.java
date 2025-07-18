/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.motorph;

import javax.swing.*;

/**
 *
 * @author angeliquerivera
 */

public class ErrorDialog {
    public static void show(String message) {
        show(message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public static void show(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(null, message, title, messageType);
    }
}
