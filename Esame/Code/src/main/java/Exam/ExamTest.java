package Exam;


import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.awt.*;

public class ExamTest  {

    public static void main(String[] args){

        /**try {
         * come il professore
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        SwingUtilities.invokeLater(SBM::new); **/
        EventQueue.invokeLater(() -> {
            try{
                UIManager.setLookAndFeel(new FlatDarkLaf());
                Login window = new Login();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}

