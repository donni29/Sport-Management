package Exam;

import Exam.Utils.DBManager;
import Exam.Utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Change_User_Password_Sport extends JFrame {

    private  static JTextField txtUsername;
    private static JPasswordField txtPassword;
    private static JPasswordField txtPassword1;
    private static JPasswordField txtNewPassword;
    private static JTextField txinsert;
    private static JTextField dayinsert;
    private static JComboBox<String> cbdelete;

    public Change_User_Password_Sport(int c) {

        setBounds(200,200,550,400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(20,20));
        setLocationRelativeTo(null);
        setVisible(true);

        switch (c) {
            case 0 -> {

                JLabel lbname = new JLabel("ADD/DELETE NEW USER");
                lbname.setHorizontalAlignment(SwingConstants.CENTER);
                lbname.setFont(new Font("Tahoma", Font.BOLD, 30));
                lbname.setBounds(28, 10, 300, 66);
                getContentPane().add(lbname, BorderLayout.NORTH);

                JPanel panel = new JPanel(new GridLayout(3, 2, 10, 80));
                JLabel lbusername = new JLabel("Username:", SwingConstants.CENTER);
                lbusername.setFont(new Font("Tahoma", Font.BOLD, 18));
                lbusername.setBounds(59, 104, 160, 36);
                panel.add(lbusername);

                txtUsername = new JTextField();
                txtUsername.setFont(new Font("Tahoma", Font.BOLD, 18));
                txtUsername.setBounds(240, 100, 80, 20);
                panel.add(txtUsername);

                JLabel lbpassword = new JLabel("Password:", SwingConstants.CENTER);
                lbpassword.setFont(new Font("Tahoma", Font.BOLD, 18));
                lbpassword.setBounds(59, 104, 160, 36);
                panel.add(lbpassword);

                txtPassword = new JPasswordField();
                txtPassword.setFont(new Font("Tahoma", Font.BOLD, 18));
                txtPassword.setBounds(240, 163, 80, 20);
                panel.add(txtPassword);

                JLabel lbpassword1 = new JLabel("Conferma Password:", SwingConstants.CENTER);
                lbpassword1.setFont(new Font("Tahoma", Font.BOLD, 18));
                lbpassword1.setBounds(59, 104, 160, 36);
                panel.add(lbpassword1);

                txtPassword1 = new JPasswordField();
                txtPassword1.setFont(new Font("Tahoma", Font.BOLD, 18));
                txtPassword1.setBounds(240, 163, 80, 20);
                panel.add(txtPassword1);
                add(panel, BorderLayout.CENTER);

                JPanel btnpanel = new JPanel(new GridLayout(1,2,5,5));

                JButton btninsert = new JButton("Inserisci User");
                btninsert.setHorizontalAlignment(SwingConstants.CENTER);
                btninsert.setFont(new Font("Tahoma",Font.BOLD,18));
                btninsert.setBounds(59,258,137,42);
                btninsert.addActionListener(e->{
                    if (String.valueOf(txtPassword.getPassword()).equals(String.valueOf(txtPassword1.getPassword()))){
                        try {
                            Login.testconnection();
                            Statement statement = DBManager.getConnection().createStatement();
                            String query = String.format("INSERT INTO Users (User,Password) VALUES ('%s','%s')",
                                    txtUsername.getText(),
                                    String.valueOf(txtPassword.getPassword()));
                            statement.executeUpdate(query);
                            statement.close();
                            JOptionPane.showMessageDialog(this,"Inserimento Nuovo User Avvenuto con successo!!");
                            dispose();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                });
                btnpanel.add(btninsert);

                JButton btndelete = new JButton("Delete User");
                btndelete.setHorizontalAlignment(SwingConstants.CENTER);
                btndelete.setFont(new Font("Tahoma",Font.BOLD,18));
                btndelete.setBounds(59,258,137,42);

                btndelete.addActionListener(e->{
                    int result = JOptionPane.showConfirmDialog(this,"Confermi l' Eliminazione di Questo User?","Acknowledgment",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    if (result == JOptionPane.YES_NO_OPTION ) {
                        if (String.valueOf(txtPassword.getPassword()).equals(String.valueOf(txtPassword1.getPassword()))) {
                            try{
                                Login.testconnection();
                                Statement statement = DBManager.getConnection().createStatement();
                                String query = String.format("DELETE FROM Users WHERE User = '%s' AND Password = '%s'",
                                        txtUsername.getText(),
                                        String.valueOf(txtPassword.getPassword()));
                                int rs = statement.executeUpdate(query);
                                if (rs !=0) {
                                    statement.close();
                                    JOptionPane.showMessageDialog(this, "Eliminazione avvenuta con successo!");
                                    dispose();
                                }else {
                                    JOptionPane.showMessageDialog(this,"User non trovato");
                                }
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                        }
                        else  {
                            JOptionPane.showMessageDialog(this,"Conferma di Password Errata");
                        }
                    }
                    else if (result == JOptionPane.NO_OPTION){
                        JOptionPane.showMessageDialog(this, "Eliminazione non confermata");
                    }
                });
                btnpanel.add(btndelete);
                getContentPane().add(btnpanel, BorderLayout.PAGE_END);

            }
            case 1 ->{

                JLabel lbname = new JLabel("CHANGE USER'S PASSWORD");
                lbname.setHorizontalAlignment(SwingConstants.CENTER);
                lbname.setFont(new Font("Tahoma", Font.BOLD, 30));
                lbname.setBounds(28, 10, 500, 150);
                getContentPane().add(lbname, BorderLayout.NORTH);

                JPanel panel = new JPanel(new GridLayout(4, 2, 10, 40));
                JLabel lbusername = new JLabel("Username:", SwingConstants.CENTER);
                lbusername.setFont(new Font("Tahoma", Font.BOLD, 18));
                lbusername.setBounds(59, 104, 160, 36);
                panel.add(lbusername);

                txtUsername = new JTextField();
                txtUsername.setFont(new Font("Tahoma", Font.BOLD, 18));
                txtUsername.setBounds(240, 163, 80, 20);
                panel.add(txtUsername);

                JLabel lbpassword = new JLabel("Password:", SwingConstants.CENTER);
                lbpassword.setFont(new Font("Tahoma", Font.BOLD, 18));
                lbpassword.setBounds(59, 104, 160, 36);
                panel.add(lbpassword);

                txtPassword = new JPasswordField();
                txtPassword.setFont(new Font("Tahoma", Font.BOLD, 18));
                txtPassword.setBounds(240, 163, 80, 20);
                panel.add(txtPassword);

                JLabel lbNewpassword = new JLabel("Nuova Password:", SwingConstants.CENTER);
                lbNewpassword.setFont(new Font("Tahoma", Font.BOLD, 18));
                lbNewpassword.setBounds(59, 104, 160, 36);
                panel.add(lbNewpassword);

                txtNewPassword =new JPasswordField();
                txtNewPassword.setFont(new Font("Tahoma", Font.BOLD, 18));
                txtNewPassword.setBounds(240, 163, 80, 20);
                panel.add(txtNewPassword);

                JLabel lbpassword1 = new JLabel("Conferma Nuova Password:", SwingConstants.CENTER);
                lbpassword1.setFont(new Font("Tahoma", Font.BOLD, 18));
                lbpassword1.setBounds(59, 104, 160, 36);
                panel.add(lbpassword1);

                txtPassword1 = new JPasswordField();
                txtPassword1.setFont(new Font("Tahoma", Font.BOLD, 18));
                txtPassword1.setBounds(240, 163, 80, 20);
                panel.add(txtPassword1);

                add(panel, BorderLayout.CENTER);

                JButton btnchange = new JButton("Change Password");
                btnchange.setHorizontalAlignment(SwingConstants.CENTER);
                btnchange.setFont(new Font("Tahoma",Font.BOLD,18));
                btnchange.setBounds(59,258,137,42);
                btnchange.addActionListener(e -> {

                    try{
                        Login.testconnection();
                        Statement statement = DBManager.getConnection().createStatement();
                        String query = String.format("SELECT * FROM Users WHERE User = '%s' AND Password = '%s'",
                                txtUsername.getText(),
                                String.valueOf(txtPassword.getPassword()));
                        ResultSet rs = statement.executeQuery(query);
                        if (!rs.next()){
                            JOptionPane.showMessageDialog(this,"Username o Password ERRATI");
                        }
                        else {
                            if (String.valueOf(txtNewPassword.getPassword()).equals(String.valueOf(txtPassword1.getPassword()))) {
                                int result = JOptionPane.showConfirmDialog(this, "Confermi di Cambiare la Password?", "Improvement",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.QUESTION_MESSAGE);
                                if (result == JOptionPane.YES_NO_OPTION) {
                                    Statement newStatement = DBManager.getConnection().createStatement();
                                    String newquery = String.format("UPDATE Users SET Password = '%s' WHERE User = '%s'",
                                            String.valueOf(txtNewPassword.getPassword()),
                                            txtUsername.getText());
                                    newStatement.executeUpdate(newquery);
                                    statement.close();
                                    JOptionPane.showMessageDialog(this, "Cambiamento di Password Avvenuto con Successo");
                                    dispose();
                                }
                                else if (result == JOptionPane.NO_OPTION){
                                    JOptionPane.showMessageDialog(this, "Eliminazione non confermata");
                                }
                            } else {
                                JOptionPane.showMessageDialog(this, "Conferma nuova Password Errata");
                            }
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                });
                getContentPane().add(btnchange, BorderLayout.PAGE_END);

            }
            case 2->{
                setBounds(200,200,500,120);
                setLocationRelativeTo(null);

                JPanel panel1 = new JPanel(new GridLayout(1,3,10,20));
                panel1.setBounds(30,30,330,180);

                JLabel lbinsert = new JLabel("Inserisci Sport",SwingConstants.CENTER);
                lbinsert.setFont(new Font("Tahoma", Font.BOLD, 18));
                lbinsert.setSize(90 , 60);
                panel1.add(lbinsert);

                txinsert = new JTextField("",SwingConstants.CENTER);
                txinsert.setFont(new Font("Tahoma", Font.BOLD, 18));
                txinsert.setSize(90,60);
                panel1.add(txinsert);

                JButton btinsert = new JButton("Insert");
                btinsert.setFont(new Font("Tahoma", Font.BOLD, 18));
                btinsert.setSize(90,60);
                btinsert.addActionListener(e->{
                        try {
                            Statement statement = DBManager.getConnection().createStatement();
                            String query = String.format("INSERT INTO Sport (Sport) VALUES ('%s')",
                                    txinsert.getText());
                            statement.executeUpdate(query);
                            statement.close();
                            Utils.List_init();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }

                    dispose();
                });
                panel1.add(btinsert);

                JPanel panel2 = new JPanel(new GridLayout(1,3,10,20));
                panel2.setBounds(30,30,330,180);

                JLabel lbdelete = new JLabel("Elimina Sport",SwingConstants.CENTER);
                lbdelete.setFont(new Font("Tahoma", Font.BOLD, 18));
                lbdelete.setSize(90 , 60);
                panel2.add(lbdelete);

                cbdelete = new JComboBox(Utils.options.toArray());
                cbdelete.setFont(new Font("Tahoma", Font.BOLD, 18));
                cbdelete.setSize(90,60);
                panel2.add(cbdelete);

                JButton btdelete = new JButton("Delete");
                btdelete.setFont(new Font("Tahoma", Font.BOLD, 18));
                btdelete.setSize(90,60);
                btdelete.addActionListener(e->{
                  try{
                      Statement statement = DBManager.getConnection().createStatement();
                      String query = String.format("DELETE FROM Sport WHERE Sport = '%s'",
                              cbdelete.getSelectedItem());
                      statement.executeUpdate(query);
                      statement.close();
                      Utils.List_init();
                  } catch (SQLException throwables) {
                      throwables.printStackTrace();
                  }
                    dispose();
                });
                panel2.add(btdelete);

                add(panel1,BorderLayout.NORTH);
                add(panel2,BorderLayout.CENTER);
            }

        }
    }
}
