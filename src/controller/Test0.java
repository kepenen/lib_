package controller;

import gui.Login;
import service.sql.MySQLConnection;

import javax.swing.*;

public class Test0 {
    public static void main(String[] args) {
        MySQLConnection.init();
        SwingUtilities.invokeLater(() -> new Login().setVisible(true));
    }
}
