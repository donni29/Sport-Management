package Exam;

import Exam.Utils.DBManager;
import Exam.Utils.Persona;
import Exam.Utils.Struttura;
import Exam.Utils.Utils;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
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
    private JPanel Strutture;
    JComboBox<String> jc;
    private JTextArea tel;
    private  JTextArea Ind;
    private  JTextArea Ora_ap;
    private  JTextArea Ora_ch;
    private  JTextArea nome;

    public StrutturaPanel() {
        setLayout(new BorderLayout());
        jc = new JComboBox(Posti);
        jc.setEditable(true);
        jc.addActionListener(this);
        jc.setSize(4,4);

        JPanel p1 = new JPanel(new GridLayout(1, 2,1,1));
        JLabel titolo =  new JLabel("Seleziona Struttura :");
        Font font = new Font("Helvetica", Font.BOLD, 20);
        titolo.setFont(font);
        p1.add(titolo);
        p1.add(jc);
        JPanel p2 = new JPanel();
        JPanel p3 = new JPanel( new GridLayout(5,2,10,2));


        JLabel l0 = new JLabel("Nome");
        nome = new JTextArea();
        p3.add(l0);
        p3.add(nome);

        JLabel l1 = new JLabel("Telefono");
        tel = new JTextArea();
        p3.add(l1);
        p3.add(tel);

        JLabel l2 = new JLabel("Indirizzo");
        Ind = new JTextArea();
        p3.add(l2);
        p3.add(Ind);

        JLabel l3 = new JLabel("Ora Mattina");
         Ora_ap = new JTextArea();
        p3.add(l3);
        p3.add(Ora_ap);

        JLabel l4 = new JLabel("Ora Mattina");
        Ora_ch = new JTextArea();
        p3.add(l4);
        p3.add(Ora_ch);


        //p2.setPreferredSize(new Dimension(450,110));
        p2.add(p3);
        /*Text = new JTextArea();
        JScrollPane sc = new JScrollPane(Text);
        sc.setPreferredSize( new Dimension(450,110));
        p2.add(sc);*/







        Strutture = new JPanel();
        Strutture.setLayout(new GridLayout(1,2,100,50));

        Strutture.add(p1);
        Strutture.add(p2);
        add(Strutture,BorderLayout.NORTH);
        setVisible(true);

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

    /*public JTable getTable(String query) throws SQLException {
        JTable table = new JTable();


        return table;
    }*/



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
                Struttura Posti = Cerca();

                if(Posti == null){
                    JOptionPane.showMessageDialog(this," struttura errata oppure non presente");
                }
            }
        } catch (SQLException throwables){
            System.out.println(throwables);
        }
    }

    private Struttura Cerca() throws SQLException {
        Statement statement = DBManager.getConnection().createStatement();
        Struttura Posto = null;
        try{
            String query1 = String.format("SELECT * FROM STRUTTURA WHERE nome like '%s'",
                    nome.getText());
            ResultSet rs =statement.executeQuery(query1);
            while(rs.next()){
                Posto = new Struttura(rs.getString("nome"),
                        rs.getString("via"),
                        rs.getString("num_telefono"),
                        rs.getString("orario_mattina"),
                        rs.getString("orario_pomeriggio")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Posto;
    }
}

