package Exam;

import Exam.Utils.DBManager;
import Exam.Utils.Struttura;
import Exam.Utils.Utils;
import com.mindfusion.scheduling.Calendar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class StrutturaPanel extends JPanel implements ActionListener {
    @Serial
    private static final long serialVersionUID = 1L;
    private Calendar calendar;
    public static java.util.Calendar calen_i = null;
    public static java.util.Calendar calen_f = null;

    public static  String[] Posti = {"Poggio","Bosco saliceta","Tazio Nuvolari"};
    public JComboBox<String> jc;
    private JTextArea tel;
    private  JTextArea Ind;
    private  JTextArea Ora_ap;
    private  JTextArea Ora_ch;
    private  JTextArea nome;
    public static JPanel p2;

    public StrutturaPanel() throws SQLException, ClassNotFoundException {
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
        p2 = new JPanel(new BorderLayout(2,2));
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

        JLabel l3 = new JLabel("Orario Apertura");
        Ora_ap = new JTextArea();
        p3.add(l3);
        p3.add(Ora_ap);

        JLabel l4 = new JLabel("Orario Chiusura");
        Ora_ch = new JTextArea();
        p3.add(l4);
        p3.add(Ora_ch);

        p2.add(p3, BorderLayout.NORTH);




        //p2.add(new CreateCalendar(jc.getSelectedItem()),BorderLayout.CENTER);

        add(p1,BorderLayout.NORTH);
        add(p2,BorderLayout.CENTER);
        //setBackground();
        setVisible(true);

    }
    private void testconnection() throws SQLException {
        DBManager.setConnection(Utils.JDBC_Driver, Utils.JDBC_URL);
        Statement statement = DBManager.getConnection().createStatement();

        try {
            statement.executeQuery("SELECT * FROM Struttura");
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

                if (((BorderLayout) p2.getLayout()).getLayoutComponent(BorderLayout.CENTER) != null){
                (((BorderLayout) p2.getLayout()).getLayoutComponent(BorderLayout.CENTER)).setVisible(false);}
                p2.add(new CreateCalendar(jc.getSelectedItem()),BorderLayout.CENTER);
                setVisible(true);
                //(((BorderLayout) p2.getLayout()).getLayoutComponent(BorderLayout.CENTER)).setVisible(true);
                if(Posti == null){
                    JOptionPane.showMessageDialog(this,"struttura errata oppure non presente");
                }
            }
        } catch (SQLException | ClassNotFoundException throwables){
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

