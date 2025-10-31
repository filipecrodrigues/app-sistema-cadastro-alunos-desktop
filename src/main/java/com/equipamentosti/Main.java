package com.equipamentosti;

import com.equipamentosti.view.EquipamentoFrame;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new EquipamentoFrame().setVisible(true);
        });
    }
}