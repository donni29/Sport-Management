package Exam;


import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.awt.*;

public class ExamTest {


    public static void main(String[] args){
        EventQueue.invokeLater(() -> {
            try{
                new DB_Model();
                UIManager.setLookAndFeel(new FlatDarkLaf());
                Login window = new Login();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}

