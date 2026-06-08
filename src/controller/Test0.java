package controller;

import gui.Login;

import javax.swing.*;
import java.awt.*;

public class Test0 {
    public static void main(String[] args) {
        // 全局统一字体、字号
        Font font = new Font("微软雅黑", Font.PLAIN, 20);
        UIManager.put("Button.font", font);
        UIManager.put("Label.font", font);
        UIManager.put("TextField.font", font);
        UIManager.put("TextArea.font", font);
        UIManager.put("ComboBox.font", font);
        UIManager.put("Menu.font", font);
        UIManager.put("MenuItem.font", font);
        SwingUtilities.invokeLater(() -> new Login().setVisible(true));
    }
}
