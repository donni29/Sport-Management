package Exam;

import Exam.Utils.Persona;
import Exam.Utils.PersonaTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;


public class SearchFrame extends JFrame implements ActionListener {

    private final JButton bsearch;
    private final JTextField tfserach;
    private final JButton binsert;
    private final JTextField tfcf;
    private JTable table;
    private PersonaTableModel model;
    private List<Persona> personas;

    public SearchFrame() {
        super("Search Athlete Frame");

        bsearch = new JButton("Search Athlete");
        bsearch.addActionListener(this);
        tfserach = new JTextField("");
        binsert = new JButton("Insert CF");
        binsert.addActionListener(this);
        tfcf = new JTextField("");

        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(1, 3));
        panel.setAlignmentY(Component.CENTER_ALIGNMENT);
        panel.add(new Label("Insert Cognome: "));
        panel.add(tfserach);
        panel.add(bsearch);

        JPanel panel1 = new JPanel(new GridLayout(1, 2));
        panel1.add(tfcf);
        panel1.add(binsert);

        JPanel panel2 = new JPanel(new BorderLayout());
        panel2.add(panel, BorderLayout.NORTH);
        panel2.add(panel1, BorderLayout.PAGE_END);

        add(panel2, BorderLayout.NORTH);

        setSize(1000, 200);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.bsearch) {
            Table();
            setVisible(true);
        }

        if (e.getSource() == this.binsert) {
            dispose();
        }
    }


    public void Table (){
        String query = String.format("SELECT * FROM Persona WHERE cognome like '%s'",tfserach.getText());
        PersonaPanel pp = null;
        try {
            pp = new PersonaPanel(query);
            //personas = getListPersona("SELECT * FROM Persona where cognome like " + cognome);
            personas= pp.getListPersona(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        table = new JTable();
        model = new PersonaTableModel(personas);
        table.setModel(model);
        table.setAutoCreateRowSorter(true);

        add(new JScrollPane(table),BorderLayout.CENTER);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedPersonaIndex = table.getSelectedRow();
                Persona person = personas.get(selectedPersonaIndex);

                tfcf.setText(person.getCF());
            }
        });
        setVisible(false);
    }

    @Override
    public String toString() {
        return tfcf.getText();
    }

/* private ArrayList<Persona> getListPersona(String query) throws SQLException {


        ArrayList<Persona> personas = new ArrayList<>();
        Statement statement = DBManager.getConnection().createStatement();
        try {
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                personas.add(
                        new Persona(rs.getString("nome"),
                                rs.getString("cognome"),
                                rs.getString("tipo"),
                                rs.getString("luogo_nascita"),
                                rs.getString("data_nascita"),
                                rs.getString("città_residenza"),
                                rs.getString("CF"),
                                rs.getString("sport"),
                                rs.getString("squadra")
                        )
                );
            }
            statement.close();
        }catch (SQLException e){
            System.out.println(e);
        }
        return personas;

    }*/


}

