package Exam;

import Exam.Utils.DBManager;
import Exam.Utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class Login implements KeyListener {

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

        Toolkit kit = Toolkit.getDefaultToolkit();
        Image image = kit.createImage(Objects.requireNonNull(this.getClass().getResource("/sportinsime.jpg")));
        frame.setIconImage(image);

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
        txtPassword.addKeyListener(this);
        panel.add(txtPassword);

        frame.getContentPane().add(panel, BorderLayout.CENTER);

        JPanel btnpanel = new JPanel( new FlowLayout());

        JButton btnlogin = new JButton("Login");
        btnlogin.setPreferredSize(new Dimension(90,30));
        btnlogin.setHorizontalAlignment(SwingConstants.CENTER);
        btnlogin.setFont(new Font("Tahoma",Font.BOLD,18));
        btnlogin.addActionListener(e -> {
                    /* primo metodo usato più base


                    if (Password.contains(Utils.Password) && Username.contains(Utils.User)) {
                        txtPassword.setText(null);
                        txtUsername.setText(null);

                        SBM sbm = new SBM();
                        sbm.setVisible(true);
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid Login Details", "Login Error", JOptionPane.ERROR_MESSAGE);
                        txtPassword.setText(null);
                        txtUsername.setText(null);
                    }
                });

    }*/

            /*secondo metodo con tabella in sql*/
            try {
                Check();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        });
        btnlogin.setBounds(59,258,137,42);
        btnpanel.add(btnlogin);

        frame.getContentPane().add(btnpanel,BorderLayout.PAGE_END);
    }

    public static void testconnection() throws SQLException {
        DBManager.setConnection(Utils.JDBC_Driver, Utils.JDBC_URL);
        Statement statement = DBManager.getConnection().createStatement();

        try {
            statement.executeQuery("SELECT * FROM Users");
        } catch (SQLException e1) {
            System.out.println("SQL Exception");

        }
    }

    public void Check() throws SQLException {
        testconnection();
        Statement statement = DBManager.getConnection().createStatement();
        String query = String.format("SELECT * FROM Users WHERE User = '%s' AND Password = '%s'",
                txtUsername.getText(),
                String.valueOf(txtPassword.getPassword()));
        try {
            ResultSet rs = statement.executeQuery(query);
            if (rs.next() //|| ((String.valueOf(txtPassword.getPassword())).contains(Utils.Password) && (txtUsername.getText()).contains(Utils.User ))
             ){
                SBM sbm = new SBM();
                sbm.setVisible(true);
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Login Details", "Login Error", JOptionPane.ERROR_MESSAGE);
                txtPassword.setText(null);
                txtUsername.setText(null);
            }
            statement.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                try {
                    Check();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
}