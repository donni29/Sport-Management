package Exam;


import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;


public class ExamTest  {

    public static void main(String[] args){

        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        SwingUtilities.invokeLater(SBM::new);
    }
}

