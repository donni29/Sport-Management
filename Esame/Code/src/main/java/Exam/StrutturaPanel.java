package Exam;
/**
 * Class to manage appointments of specific structures
 * @authors Rossi Nicolò Delsante Laura
 */

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

    public JComboBox<String> jc;
    private final JTextArea tel;
    private final JTextArea Ind;
    private final JTextArea Ora_ap;
    private final JTextArea Ora_ch;
    private final JTextArea nome;
    public static JPanel p2;

    public StrutturaPanel() throws SQLException {
        super();
        setLayout(new BorderLayout(10,10));
        jc = new JComboBox(Utils.places.toArray());
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


        add(p1,BorderLayout.NORTH);
        add(p2,BorderLayout.CENTER);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            if (e.getSource() == this.jc){
                Struttura Posti = Cerca();

                if (((BorderLayout) p2.getLayout()).getLayoutComponent(BorderLayout.CENTER) != null){
                (((BorderLayout) p2.getLayout()).getLayoutComponent(BorderLayout.CENTER)).setVisible(false);}
                p2.add(new CreateCalendar(jc.getSelectedItem()),BorderLayout.CENTER);
                setVisible(true);
                if(Posti == null){
                    JOptionPane.showMessageDialog(this,"struttura errata oppure non presente");
                }
            }
        } catch (SQLException | ClassNotFoundException el){
            el.printStackTrace();
        }
    }

    /**
     * method to show the attributes into the corresponding JPanel's JtextArea related of every column
     * @return a new Struttura Object taken from the DB Structure table
     * @throws SQLException - if there is no Table Struttura or no Entry in it
     */
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

