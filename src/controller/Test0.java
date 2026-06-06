package controller;

import gui.Login;

import javax.swing.*;

public class Test0 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login().setVisible(true));
    }
}
