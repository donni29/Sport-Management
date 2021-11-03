package Exam.Utils;


import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * This Inner Class implements a JPanel to create the home screen of the application.
 *
 * @authors Rossi Nicolò Delsante Laura
 */

public class  DesktopTop extends JPanel {

    public DesktopTop() {
        super();
        setLayout(new BorderLayout());
        ImageIcon sportIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/sportinsime.jpg")));
        Image image = sportIcon.getImage();
        Image newing = image.getScaledInstance(600, 600, Image.SCALE_SMOOTH);
        sportIcon = new ImageIcon(newing);
        JLabel jLabelObject = new JLabel();
        jLabelObject.setText("<html>Welcome to  SPORTINSIEME's  Sport Management <br/> Created and Managed By Students: <br/> Rossi Nicol\u00F2 & Delsante Laura");
        jLabelObject.setIcon(sportIcon);
        Font font = new Font("Helvetica", Font.BOLD, 30);
        jLabelObject.setFont(font);
        jLabelObject.setHorizontalAlignment(JLabel.CENTER);
        add(jLabelObject,BorderLayout.CENTER);
        setSize(900, 600);
        setVisible(true);
    }
}