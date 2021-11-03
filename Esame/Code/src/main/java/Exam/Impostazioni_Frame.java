package Exam;


/**
  Class to create the Jframe for managing different aspects of our SBM

  @authors Rossi Nicolò Delsante Laura
 */

import Exam.Utils.DBManager;
import Exam.Utils.Utils;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static java.util.Objects.requireNonNull;
import static javax.swing.SwingConstants.CENTER;

public class Impostazioni_Frame extends JFrame  {

    private final JTree tree;
    private static JTextField txtUsername;
    private static JPasswordField txtPassword;
    private static JPasswordField txtPassword1;
    private static JPasswordField txtNewPassword;
    private static JTextField txinsert;
    private static JTextField dayinsert;
    private static JComboBox<Object> cbdelete2;
    private static JComboBox<Object> cbdelete1;

    private final JPanel panel;

    public Impostazioni_Frame() {

        super("Impostazioni");


        setBounds(200, 200, 900, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        panel = new JPanel(new BorderLayout(20, 20));


        Toolkit kit = Toolkit.getDefaultToolkit();
        Image image = kit.createImage(requireNonNull(this.getClass().getResource("/rotella.png")));
        setIconImage(image);

        //create the root node
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Impostazioni: ");

        //create the child nodes
        DefaultMutableTreeNode LoginNode = new DefaultMutableTreeNode("Login");
        Leaf[] leaves = new Leaf[]{
                new Leaf("Aggiungi/Elimina User", "/user.png"),
                new Leaf("Cambia Password", "/lucchetto.png")
        };

        for (Leaf leaf : leaves) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(leaf);
            LoginNode.add(node);
        }
        DefaultMutableTreeNode SportNode = new DefaultMutableTreeNode("Sport");
        SportNode.add(new DefaultMutableTreeNode(new Leaf("Modifica Sport", "/corridore.png")));
        DefaultMutableTreeNode StruttureNode = new DefaultMutableTreeNode("Strutture");
        StruttureNode.add(new DefaultMutableTreeNode(new Leaf("Modifica Strutture", "/edificio.png")));


        //add the child nodes to the root node
        root.add(LoginNode);
        root.add(SportNode);
        root.add(StruttureNode);

        tree = new JTree(root);
        tree.setCellRenderer(new LeafRenderer());
        tree.setModel(new DefaultTreeModel(root));
        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Object mia =  ((DefaultMutableTreeNode) requireNonNull(tree.getSelectionPath()).getLastPathComponent()).getUserObject();
                if (mia instanceof Leaf) {
                    String str = ((Leaf) mia).getName();
                    if ( (((BorderLayout) panel.getLayout()).getLayoutComponent(BorderLayout.CENTER)) != null){
                        (((BorderLayout) panel.getLayout()).getLayoutComponent(BorderLayout.CENTER)).setVisible(false);
                    }
                    panel.add(SelectPanel(str),BorderLayout.CENTER);
                    setVisible(true);
                }
            }
        });


        JScrollPane treepane = new JScrollPane(tree);
        treepane.setPreferredSize(new Dimension(215, 400));
        panel.add(treepane, BorderLayout.WEST);

        setContentPane(panel);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setAlwaysOnTop(true);

    }


    /**
     * To Select the Jpanel after the Jtree Leaf selection
     * @param str - String witch contains our selection
     * @return - different Jpanel
     */
    public JPanel SelectPanel(String str){
        JPanel jPanel =new JPanel();
        switch (str) {
            case "Aggiungi/Elimina User" -> jPanel = Imp_Panel(0);
            case "Cambia Password" -> jPanel = Imp_Panel(1);
            case "Modifica Sport" -> jPanel = Imp_Panel(2);
            case "Modifica Strutture" -> jPanel = Imp_Panel(4);
        }
        return jPanel;
    }


    /**
     * To Modify the JPanel with the different views needed
     * @param c - index of our selection
     * @return - JPanel modified
     */
    private JPanel Imp_Panel(int c){

        JPanel jpanel= new JPanel(new BorderLayout(10,10));

        switch (c){
            case 0->{
                JLabel lbname = new JLabel("AGGIUNGER/ELIMINARE USER");
                lbname.setHorizontalAlignment(CENTER);
                lbname.setFont(new Font("Tahoma", Font.BOLD, 30));
                lbname.setBounds(28, 10, 300, 66);
                jpanel.add(lbname, BorderLayout.NORTH);

                JPanel panel = new JPanel(new GridLayout(3, 2, 10, 120));
                JLabel lbusername = new JLabel("Username:", CENTER);
                lbusername.setFont(new Font("Tahoma", Font.BOLD, 18));
                lbusername.setBounds(59, 104, 60, 36);
                panel.add(lbusername);

                txtUsername = new JTextField();
                txtUsername.setFont(new Font("Tahoma", Font.BOLD, 18));
                txtUsername.setBounds(240, 100, 60, 20);
                panel.add(txtUsername);

                JLabel lbpassword = new JLabel("Password:", CENTER);
                lbpassword.setFont(new Font("Tahoma", Font.BOLD, 18));
                lbpassword.setBounds(59, 104, 160, 36);
                panel.add(lbpassword);

                txtPassword = new JPasswordField();
                txtPassword.setFont(new Font("Tahoma", Font.BOLD, 18));
                txtPassword.setBounds(240, 163, 60, 20);
                panel.add(txtPassword);

                JLabel lbpassword1 = new JLabel("Conferma Password:", CENTER);
                lbpassword1.setFont(new Font("Tahoma", Font.BOLD, 18));
                lbpassword1.setBounds(59, 104, 160, 36);
                panel.add(lbpassword1);

                txtPassword1 = new JPasswordField();
                txtPassword1.setFont(new Font("Tahoma", Font.BOLD, 18));
                txtPassword1.setBounds(240, 163, 60, 20);
                panel.add(txtPassword1);
                jpanel.add(panel, BorderLayout.CENTER);

                JPanel btnpanel = new JPanel(new FlowLayout(FlowLayout.CENTER,5,5));

                JButton btninsert = new JButton("Inserisci User");
                btninsert.setPreferredSize(new Dimension(200,30));
                btninsert.setHorizontalAlignment(CENTER);
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
                            txtPassword1.setText("");
                            txtPassword.setText("");
                            txtUsername.setText("");
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                });
                btnpanel.add(btninsert);

                JButton btndelete = new JButton("Delete User");
                btndelete.setPreferredSize(new Dimension(230,30));
                btndelete.setHorizontalAlignment(CENTER);
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
                                    txtPassword1.setText("");
                                    txtPassword.setText("");
                                    txtUsername.setText("");
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
                jpanel.add(btnpanel, BorderLayout.PAGE_END);
            }
            case 1 ->{

                JLabel lbname = new JLabel("CHANGE USER'S PASSWORD");
                lbname.setHorizontalAlignment(CENTER);
                lbname.setFont(new Font("Tahoma", Font.BOLD, 30));
                lbname.setBounds(28, 10, 500, 150);
                jpanel.add(lbname, BorderLayout.NORTH);

                JPanel panel = new JPanel(new GridLayout(4, 2, 10, 40));
                JLabel lbusername = new JLabel("Username:", CENTER);
                lbusername.setFont(new Font("Tahoma", Font.BOLD, 18));
                lbusername.setBounds(59, 104, 160, 36);
                panel.add(lbusername);

                txtUsername = new JTextField();
                txtUsername.setFont(new Font("Tahoma", Font.BOLD, 18));
                txtUsername.setBounds(240, 163, 80, 20);
                panel.add(txtUsername);

                JLabel lbpassword = new JLabel("Password:", CENTER);
                lbpassword.setFont(new Font("Tahoma", Font.BOLD, 18));
                lbpassword.setBounds(59, 104, 160, 36);
                panel.add(lbpassword);

                txtPassword = new JPasswordField();
                txtPassword.setFont(new Font("Tahoma", Font.BOLD, 18));
                txtPassword.setBounds(240, 163, 80, 20);
                panel.add(txtPassword);

                JLabel lbNewpassword = new JLabel("Nuova Password:", CENTER);
                lbNewpassword.setFont(new Font("Tahoma", Font.BOLD, 18));
                lbNewpassword.setBounds(59, 104, 160, 36);
                panel.add(lbNewpassword);

                txtNewPassword =new JPasswordField();
                txtNewPassword.setFont(new Font("Tahoma", Font.BOLD, 18));
                txtNewPassword.setBounds(240, 163, 80, 20);
                panel.add(txtNewPassword);

                JLabel lbpassword1 = new JLabel("Conferma Nuova Password:", CENTER);
                lbpassword1.setFont(new Font("Tahoma", Font.BOLD, 18));
                lbpassword1.setBounds(59, 104, 200, 36);
                panel.add(lbpassword1);

                txtPassword1 = new JPasswordField();
                txtPassword1.setFont(new Font("Tahoma", Font.BOLD, 18));
                txtPassword1.setBounds(240, 163, 80, 20);
                panel.add(txtPassword1);

                jpanel.add(panel, BorderLayout.CENTER);

                JPanel btnpanel = new JPanel(new FlowLayout(FlowLayout.CENTER,5,5));
                JButton btnchange = new JButton("Change Password");
                btnchange.setPreferredSize(new Dimension(230,30));
                btnchange.setHorizontalAlignment(CENTER);
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
                                    txtPassword1.setText("");
                                    txtPassword.setText("");
                                    txtUsername.setText("");
                                    txtNewPassword.setText("");
                                }
                                else if (result == JOptionPane.NO_OPTION){
                                    JOptionPane.showMessageDialog(this, "Modifica non confermata");
                                }
                            } else {
                                JOptionPane.showMessageDialog(this, "Conferma nuova Password Errata");
                            }
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                });
                btnpanel.add(btnchange);
                jpanel.add(btnpanel, BorderLayout.PAGE_END);

            }
            case 2->{
                JPanel pane = new JPanel();
                JPanel panel1 = new JPanel(new GridLayout(1,2,10,35));

                JLabel lbinsert = new JLabel("Inserisci Sport", CENTER);
                lbinsert.setFont(new Font("Tahoma", Font.BOLD, 18));
                lbinsert.setSize(90 , 60);
                panel1.add(lbinsert);

                txinsert = new JTextField("", CENTER);
                txinsert.setFont(new Font("Tahoma", Font.BOLD, 18));
                txinsert.setSize(190,60);
                panel1.add(txinsert);

                JButton btinsert = new JButton("Insert");
                btinsert.setFont(new Font("Tahoma", Font.BOLD, 18));
                btinsert.setSize(90,60);
                btinsert.addActionListener(e->{
                    try {
                        Statement statement = DBManager.getConnection().createStatement();
                        String query = String.format("INSERT INTO Sport (Name) VALUES ('%s')",
                                txinsert.getText());
                        statement.executeUpdate(query);
                        statement.close();
                        Utils.List_init();
                        txinsert.setText("");
                        cbdelete1.setModel(new DefaultComboBoxModel<>(Utils.options.toArray()));
                        cbdelete2.setModel(new DefaultComboBoxModel<>(Utils.options.toArray()));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                });
                panel1.add(btinsert);


                JPanel panel3 = new JPanel(new GridLayout(1,2,10,35));
                JLabel lbdelete = new JLabel("Elimina Sport", CENTER);
                lbdelete.setFont(new Font("Tahoma", Font.BOLD, 18));
                lbdelete.setSize(90 , 60);
                panel3.add(lbdelete);

                cbdelete1 = new JComboBox<>(Utils.options.toArray());
                cbdelete1.setFont(new Font("Tahoma", Font.BOLD, 18));
                cbdelete1.setSize(90,60);
                panel3.add(cbdelete1);

                JButton btdelete = new JButton("Delete");
                btdelete.setFont(new Font("Tahoma", Font.BOLD, 18));
                btdelete.setSize(90,60);
                btdelete.addActionListener(e->{
                    try{
                        Statement statement = DBManager.getConnection().createStatement();
                        String query = String.format("DELETE FROM Sport WHERE Name = '%s'",
                                cbdelete1.getSelectedItem());
                        statement.executeUpdate(query);
                        statement.close();
                        Utils.List_init();
                        cbdelete1.setModel(new DefaultComboBoxModel<>(Utils.options.toArray()));
                        cbdelete2.setModel(new DefaultComboBoxModel<>(Utils.options.toArray()));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                });
                panel3.add(btdelete);
                pane.add(panel1);
                pane.add(panel3);
                JLabel labelsport =new JLabel("Pannello Gestione Sport");
                labelsport.setHorizontalAlignment(CENTER);
                labelsport.setFont(new Font("Tahoma", Font.BOLD, 30));
                jpanel.add(labelsport,BorderLayout.NORTH);
                jpanel.add(pane,BorderLayout.CENTER);
                jpanel.add(Imp_Panel(3),BorderLayout.SOUTH);
            }

            case 3->{
                JPanel panel = new JPanel(new GridLayout(4,2,5,5));
                panel.setBounds(30,30,330,180);

                JLabel settimanali = new JLabel("Selezione Sport", CENTER);
                settimanali.setFont(new Font("Tahoma", Font.BOLD, 18));
                settimanali.setSize(90 , 60);
                panel.add(settimanali);

                cbdelete2 = new JComboBox<>(Utils.options.toArray());
                cbdelete2.setFont(new Font("Tahoma", Font.BOLD, 18));
                cbdelete2.setSize(90,60);
                panel.add(cbdelete2);

                JLabel ilabel = new JLabel("Inserisci Giorni Settimanali", CENTER);
                ilabel.setFont(new Font("Tahoma", Font.BOLD, 18));
                ilabel.setSize(90 , 60);
                panel.add(ilabel);

                dayinsert =new JTextField("");
                dayinsert.setFont(new Font("Tahoma", Font.BOLD, 18));
                dayinsert.setSize(90,60);
                panel.add(dayinsert);

                JButton btn =new JButton("INSERT/MODIFICATION");
                btn.setFont(new Font("Tahoma", Font.BOLD, 18));
                btn.setSize(90,60);
                btn.addActionListener(e->{
                    try {
                        Statement statement = DBManager.getConnection().createStatement();
                        String query = String.format("UPDATE Sport SET Giorni_Allenamento = '%s' WHERE Name = '%s'",
                                dayinsert.getText(),
                                cbdelete2.getSelectedItem()
                        );
                        statement.executeUpdate(query);
                        statement.close();
                        dayinsert.setText("");
                        cbdelete2.setSelectedItem(0);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                });
                JLabel labelsport =new JLabel("Pannello Gestione Giorni Allenamento");
                labelsport.setHorizontalAlignment(CENTER);
                labelsport.setFont(new Font("Tahoma", Font.BOLD, 30));
                jpanel.add(labelsport,BorderLayout.NORTH);
                jpanel.add(btn,BorderLayout.PAGE_END,CENTER);
                jpanel.add(panel,BorderLayout.CENTER);


            }
            case 4->{

                JLabel lbname = new JLabel("Pannello Controllo Palestre");
                lbname.setHorizontalAlignment(CENTER);
                lbname.setFont(new Font("Tahoma", Font.BOLD, 30));
                lbname.setBounds(28, 10, 500, 150);
                jpanel.add(lbname, BorderLayout.NORTH);

                JPanel panel = new JPanel(new GridLayout(6, 2, 10, 40));

                JLabel lname1 = new JLabel("Nome Palestra Utilizzata:", CENTER);
                lname1.setFont(new Font("Tahoma", Font.BOLD, 18));
                lname1.setBounds(59, 104, 160, 36);
                panel.add(lname1);

                JComboBox<Object> comboBox = new JComboBox<>(Utils.places.toArray());
                comboBox.setFont(new Font("Tahoma", Font.BOLD, 18));
                comboBox.setSize(90,60);
                panel.add(comboBox);


                JLabel lname = new JLabel("Nome Nuova Palestra:", CENTER);
                lname.setFont(new Font("Tahoma", Font.BOLD, 18));
                lname.setBounds(59, 104, 160, 36);
                panel.add(lname);

                JTextField txtname = new JTextField();
                txtname.setFont(new Font("Tahoma", Font.BOLD, 18));
                txtname.setBounds(240, 163, 80, 20);
                panel.add(txtname);

                JLabel lbvia = new JLabel("Via:", CENTER);
                lbvia.setFont(new Font("Tahoma", Font.BOLD, 18));
                lbvia.setBounds(59, 104, 160, 36);
                panel.add(lbvia);

                JTextField txtvia = new JTextField();
                txtvia.setFont(new Font("Tahoma", Font.BOLD, 18));
                txtvia.setBounds(240, 163, 80, 20);
                panel.add(txtvia);

                JLabel lbtelefono = new JLabel("Telefono:", CENTER);
                lbtelefono.setFont(new Font("Tahoma", Font.BOLD, 18));
                lbtelefono.setBounds(59, 104, 160, 36);
                panel.add(lbtelefono);

                JTextField txtelefono = new JTextField();
                txtelefono.setFont(new Font("Tahoma", Font.BOLD, 18));
                txtelefono.setBounds(240, 163, 80, 20);
                panel.add(txtelefono);

                JLabel lbstart = new JLabel("Orario Apertura:", CENTER);
                lbstart.setFont(new Font("Tahoma", Font.BOLD, 18));
                lbstart.setBounds(59, 104, 200, 36);
                panel.add(lbstart);

                JTextField txtstart = new JTextField();
                txtstart.setFont(new Font("Tahoma", Font.BOLD, 18));
                txtstart.setBounds(240, 163, 80, 20);
                panel.add(txtstart);

                JLabel lbend = new JLabel("Orario Chiusura:", CENTER);
                lbend.setFont(new Font("Tahoma", Font.BOLD, 18));
                lbend.setBounds(59, 104, 200, 36);
                panel.add(lbend);

                JTextField txtend = new JTextField();
                txtend.setFont(new Font("Tahoma", Font.BOLD, 18));
                txtend.setBounds(240, 163, 80, 20);
                panel.add(txtend);

                jpanel.add(panel, BorderLayout.CENTER);

                JPanel btnpanel = new JPanel(new FlowLayout(FlowLayout.CENTER,5,5));
                JButton btnchange = new JButton("Inserisci Struttura");
                btnchange.setPreferredSize(new Dimension(230,30));
                btnchange.setHorizontalAlignment(CENTER);
                btnchange.setFont(new Font("Tahoma",Font.BOLD,18));
                btnchange.setBounds(59,258,137,42);
                btnchange.addActionListener(e -> {
                    try {
                        Login.testconnection();
                        Statement statement = DBManager.getConnection().createStatement();
                        String query = String.format("INSERT INTO Struttura (nome,via,num_telefono,orario_mattina,orario_pomeriggio) VALUES ('%s','%s','%s','%s','%s')",
                                txtname.getText(),
                                txtvia.getText(),
                                txtelefono.getText(),
                                txtstart.getText(),
                                txtend.getText());
                        statement.executeUpdate(query);
                        statement.close();
                        Utils.List_init();
                        comboBox.setModel(new DefaultComboBoxModel<>(Utils.places.toArray()));
                        JOptionPane.showMessageDialog(this,"Inserimento Nuovo Struttura Avvenuto con successo!!");
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                });
                JButton btndelete = new JButton("Delete Struttura");
                btndelete.setPreferredSize(new Dimension(230,30));
                btndelete.setHorizontalAlignment(CENTER);
                btndelete.setFont(new Font("Tahoma",Font.BOLD,18));
                btndelete.setBounds(59,258,137,42);

                btndelete.addActionListener(e->{
                    int result = JOptionPane.showConfirmDialog(this,"Confermi l' Eliminazione di Questa Struttura?","Acknowledgment",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    if (result == JOptionPane.YES_NO_OPTION ) {
                        try{
                            Statement statement = DBManager.getConnection().createStatement();
                            String query = String.format("DELETE FROM Struttura WHERE nome = '%s'",
                                    comboBox.getSelectedItem());
                            int rs = statement.executeUpdate(query);
                            if (rs !=0) {
                                statement.close();
                                Utils.List_init();
                                comboBox.setModel(new DefaultComboBoxModel<>(Utils.places.toArray()));
                                JOptionPane.showMessageDialog(this, "Eliminazione avvenuta con successo!");
                            }else {
                                JOptionPane.showMessageDialog(this,"Struttura non trovata");
                            }
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }

                    }
                    else if (result == JOptionPane.NO_OPTION){
                        JOptionPane.showMessageDialog(this, "Eliminazione non confermata");
                    }
                });

                JButton btnupdate = new JButton("Update Struttura");
                btnupdate.setPreferredSize(new Dimension(230,30));
                btnupdate.setHorizontalAlignment(CENTER);
                btnupdate.setFont(new Font("Tahoma",Font.BOLD,18));
                btnupdate.setBounds(59,258,137,42);
                btnupdate.addActionListener(e -> {
                    try {
                        Statement statement = DBManager.getConnection().createStatement();
                        String query = String.format("UPDATE Struttura SET nome ='%s',via ='%s',num_telefono = '%s',orario_mattina ='%s', orario_pomeriggio ='%s'",
                                txtname.getText(),
                                txtvia.getText(),
                                txtelefono.getText(),
                                txtstart.getText(),
                                txtend.getText());
                        statement.executeUpdate(query);
                        statement.close();
                        JOptionPane.showMessageDialog(this,"Aggiornammento Struttura Avvenuto con successo!!");
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                });

                btnpanel.add(btnchange);
                btnpanel.add(btndelete);
                btnpanel.add(btnupdate);
                jpanel.add(btnpanel, BorderLayout.PAGE_END);
            }
        }
        return jpanel;
    }
}


/**
 * Class represents Leaf of our JTree
 *
 * @authors Rossi Nicolò Delsante Laura
 */
class Leaf {
    private final String name;
    private final String leafIcon;

    Leaf(String name, String leafIcon) {
        this.name = name;
        this.leafIcon = leafIcon;
    }

    public String getName() {
        return name;
    }
    public String getLeafIcon() {
        return leafIcon;
    }

}

/**
 * Class to Render TreeCell of our JTree
 */
class LeafRenderer implements TreeCellRenderer {
    private final JLabel label;

    LeafRenderer() {
        label = new JLabel();
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
                                                  boolean leaf1, int row, boolean hasFocus) {
        Object mia =  ((DefaultMutableTreeNode) value).getUserObject();
        if (mia instanceof Exam.Leaf) {
            URL imageUrl = getClass().getResource(((Exam.Leaf) mia).getLeafIcon());
            if (imageUrl != null) {
                ImageIcon icon = new ImageIcon(new ImageIcon(imageUrl).getImage().getScaledInstance(18,18,Image.SCALE_DEFAULT));
                label.setIcon(icon);
            }
            label.setText(((Exam.Leaf)mia).getName());
        }
        else {
            label.setIcon(null);
            label.setText(value.toString());
        }
        return label;
    }
}