package Exam;

import Exam.Utils.DBManager;
import Exam.Utils.Persona;
import Exam.Utils.Struttura;
import Exam.Utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class StrutturaPanel extends JPanel  implements ActionListener {
    @Serial
    private static final long serialVersionUID = 1L;

   public static  String[] Posti = {"Modena","Carpi","Cesena","Bologna","Sassuolo"};
    private JPanel Struttura;
    private JButton Button;
    JComboBox<String> jc;
    //private J


    JButton btInsert;
    JButton btDelete;
    JTextArea text = new JTextArea();

    public StrutturaPanel() {

        jc = new JComboBox(Posti);
        jc.setEditable(true);
        jc.addActionListener(this);



        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(1, 2));
        p1.add(new JLabel("Struttura:"), new GridLayout(1,2));
        //p1.setAlignmentX(Component.LEFT_ALIGNMENT);
        p1.add(jc);
        JPanel p2 = new JPanel();
        p2.setLayout(new GridLayout(1,2));
        p2.add(new JLabel("nome"));




        Struttura = new JPanel();
        Struttura.setLayout(new GridLayout(1,2));

        Struttura.add(p1);
        Struttura.add(p2);
        add(Struttura,BorderLayout.CENTER);
        setVisible(true);

        try {
            testconnection();
            JScrollPane scrollPane = new JScrollPane();
            Struttura.add(new JScrollPane(getTable("Select * from Persona")),BorderLayout.CENTER);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database Error");
        }
    }

    private void testconnection() throws SQLException {
        DBManager.setConnection(Utils.JDBC_Driver, Utils.JDBC_URL);
        Statement statement = DBManager.getConnection().createStatement();

        try {
            statement.executeQuery("SELECT * FROM Persona");
        } catch (SQLException e) {
            System.out.println("non funziona");
        }
    }

    public JTable getTable(String query) throws SQLException {
        JTable table = new JTable();


        return table;
    }



    /*public List<Struttura> getListStruttura(String query) throws  SQLException{
        ArrayList<Struttura> Strutture = new ArrayList<>();
        Statement statement = DBManager.getConnection().createStatement();
        try{
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Strutture.add(
                        new Struttura(rs.getString("nome"),
                                rs.getString("via"),
                                rs.getString("num_telefono"),
                                rs.getString("orario_apertura"),
                                rs.getString("orario_chiusura")
                        )
                );
            }
            statement.close();
        } catch (SQLException e){
            System.out.println(e);
        }
        return  Strutture;
    } */



    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            if (e.getSource() == this.jc){

            }
        } catch (Exception exception){
            exception.printStackTrace();
        }
    }
}

