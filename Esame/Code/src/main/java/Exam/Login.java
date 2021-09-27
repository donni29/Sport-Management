package Exam;

import Exam.Utils.Utils;

import javax.swing.*;
import java.awt.*;

public class Login {

    public JFrame frame;
    private JTextField txtUsername;
    private JPasswordField txtPassword;

    public Login() {
        initialize();
    }

    private void initialize(){
        frame =new JFrame();
        frame.setBounds(200,200,500,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(20,20));
        frame.setLocationRelativeTo(null);


        JLabel lbname = new JLabel("Login System SBM");
        lbname.setHorizontalAlignment(SwingConstants.CENTER);
        lbname.setFont(new Font("Tahoma",Font.BOLD,30));
        lbname.setBounds(28,10,300,66);
        frame.getContentPane().add(lbname,BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(2,2,10,80));

        JLabel lbusername = new JLabel("Username:",SwingConstants.CENTER);
        lbusername.setFont(new Font("Tahoma",Font.BOLD,18));
        lbusername.setBounds(59,104,160,36);
        panel.add(lbusername);

        txtUsername = new JTextField();
        txtUsername.setFont(new Font("Tahoma",Font.BOLD,18));
        txtUsername.setBounds(240,100,80,20);
        panel.add(txtUsername);
        txtUsername.setColumns(10);

        JLabel lbpassword = new JLabel("Password:",SwingConstants.CENTER);
        lbpassword.setFont(new Font("Tahoma",Font.BOLD,18));
        lbpassword.setBounds(59,104,160,36);
        panel.add(lbpassword);

        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Tahoma",Font.BOLD,18));
        txtPassword.setBounds(240,163,80,20);
        panel.add(txtPassword);

        frame.getContentPane().add(panel, BorderLayout.CENTER);

        JButton btnlogin = new JButton("Login");
        btnlogin.setHorizontalAlignment(SwingConstants.CENTER);
        btnlogin.setFont(new Font("Tahoma",Font.BOLD,18));

        btnlogin.addActionListener(e -> {
            String Password = String.valueOf(txtPassword.getPassword());
            String Username = txtUsername.getText();

            if (Password.contains(Utils.Password) && Username.contains(Utils.User)){
                txtPassword.setText(null);
                txtUsername.setText(null);

                SBM sbm = new SBM();
                sbm.setVisible(true);
                frame.dispose();
            }

            else {
                JOptionPane.showMessageDialog(null,"Invalid Login Details", "Login Error",JOptionPane.ERROR_MESSAGE);
                txtPassword.setText(null);
                txtUsername.setText(null);
            }
        });
        btnlogin.setBounds(59,258,137,42);
        frame.getContentPane().add(btnlogin,BorderLayout.PAGE_END);

    }
}
