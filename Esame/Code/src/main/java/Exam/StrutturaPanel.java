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

    public static  String[] Posti = {"Poggio","Tazio Nuvolari","Bosco saliceta"};
    private JPanel Strutture;
    JComboBox<String> jc;
    private JTextArea tel;
    private  JTextArea Ind;
    private  JTextArea Ora_ap;
    private  JTextArea Ora_ch;
    private  JTextArea nome;

    public StrutturaPanel() {
        setLayout(new BorderLayout(10,10));
        jc = new JComboBox(Posti);
        jc.setEditable(true);
        jc.addActionListener(this);
        jc.setSize(1,1);
        Font font1 = new Font("Helvetica", Font.ITALIC, 15);
        jc.setFont(font1);

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

        JLabel l3 = new JLabel("Orario Mattina");
         Ora_ap = new JTextArea();
        p3.add(l3);
        p3.add(Ora_ap);

        JLabel l4 = new JLabel("Orario Pomeriggio");
        Ora_ch = new JTextArea();
        p3.add(l4);
        p3.add(Ora_ch);

        p2.add(p3);



        add(p1,BorderLayout.NORTH);
        add(p2,BorderLayout.CENTER);
        //setBackground();
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
            String query1 = String.format("SELECT * FROM STRUTTURA WHERE nome like '%s'",jc.getSelectedItem());
            ResultSet rs =statement.executeQuery(query1);
            while(rs.next()){
                Posto = new Struttura(rs.getString("nome"),
                        rs.getString("via"),
                        rs.getString("num_telefono"),
                        rs.getString("orario_mattina"),
                        rs.getString("orario_pomeriggio")
                );
                nome.setText(Posto.getNome());
                tel.setText(Posto.getNum_telefono());
                Ind.setText(Posto.getVia());
                Ora_ap.setText(Posto.getOrario_mattina());
                Ora_ch.setText(Posto.getOrario_pomeriggio());

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Posto;
    }
}

