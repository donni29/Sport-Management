package Exam;


/**
 *<p>
 *This project's request was drawn up by ASD Sportinsieme, a sport club of our Territory.
 *The Desktop Application aims to allow users to archive and manage Athletes, Managers and Leaders Dates, to develop
 *Refunds and to run its Facilities (at the moment).
 *<p>
 *The class implements the main() method
 *<p>
 * @authors Rossi Nicolò Delsante Laura
 *
 */

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

