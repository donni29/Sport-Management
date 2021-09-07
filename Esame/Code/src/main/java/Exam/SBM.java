package Exam;

//questo è il nostro progetto, madonne//

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serial;
import java.sql.SQLException;
import java.util.Objects;

public class SBM extends JFrame implements ActionListener {
    @Serial
    private static final long serialVersionUID = 1L;
    public static JMenu openArchivio;
    private static JMenuItem close;
    private static JMenuItem Atleti;
    private static JMenuItem Allenatori;
    private static JMenuItem Dirigenti;
    private static JMenuItem Open;
    private static JMenuItem Nuovo;
    private static JTextArea textArea;
    private static JMenuItem  Strutture;


    /*
    Creare una variabile model( come nell'esempio SM_Updatable < sausagemanager < jdbc per separare la grafica dai dati
     */
    DB_Model model;

    public SBM() {
        super("Sport Business Management ");


        JMenuBar menuBar = new JMenuBar();
        JMenu Archivio = new JMenu("Archivio");
        menuBar.add(Archivio);
        JMenu Rimborso = new JMenu("Rimborso");
        Strutture = new JMenuItem("Strutture");
        Strutture.addActionListener(this);
        menuBar.add(Rimborso);
        menuBar.add(Strutture);

        Desktop();

        openArchivio = new JMenu("Open Archivio di ...");
        openArchivio.addSeparator();
        openArchivio.addActionListener(this);
        Archivio.add(openArchivio);

        //parte codice menu Archivio//

        Atleti = new JMenuItem("Atleti");
        Atleti.addActionListener(this);
        openArchivio.add(Atleti);

        Allenatori = new JMenuItem("Allenatori");
        Allenatori.addActionListener(this);
        openArchivio.add(Allenatori);

        Dirigenti = new JMenuItem("Dirigenti");
        Dirigenti.addActionListener(this);
        openArchivio.add(Dirigenti);

        close = new JMenuItem("Close");
        close.addActionListener(this);
        Archivio.add(close);

        //parte codice menu Rimborso//
        Nuovo = new JMenuItem("Nuovo...");
        Nuovo.addActionListener(this);
        Rimborso.add(Nuovo);

        Open = new JMenuItem("Apri...");
        Open.addActionListener(this);
        Rimborso.add(Open);

        textArea = new JTextArea("");

        setJMenuBar(menuBar);

        setExtendedState(MAXIMIZED_BOTH);


        setDefaultCloseOperation(EXIT_ON_CLOSE);



        try {
            model = new DB_Model();
        } catch (SQLException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(this, "Database Error!");
        }
        // ShowItem(); come nel esempio del prof :  SM_Updatable < sausagemanager < jdbc
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.close) {
            Desktop();
        } else {
            if (e.getSource() == this.Open) {
                JFileChooser openSource = new JFileChooser();

                int option = openSource.showOpenDialog(this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    textArea.setText("");

                    try {
                        File file = new File((openSource.getSelectedFile().getPath()));
                        System.out.println(new FileReader(openSource.getSelectedFile().getPath()));
                        if (!Desktop.isDesktopSupported()) {
                            System.out.println("not supported");
                        }
                        Desktop desktop = Desktop.getDesktop();
                        if (file.exists()) {
                            desktop.open(file);
                        }
                    } catch (IOException e2) {
                        e2.printStackTrace();
                        System.out.println("IOException" + "\n" + e2);
                    }
                }
            }
            if (e.getSource() == this.Atleti) {
                try {
                    String query = "SELECT * FROM Persona WHERE tipo like 'Atleta'";
                    PersonaPanel pp = new PersonaPanel(query);
                    setContentPane(pp);
                    setVisible(true);
                } catch (SQLException throwables) {
                    System.out.println("Errore Database Atleta" + throwables);
                    throwables.printStackTrace();
                }


            }
            if (e.getSource() == this.Allenatori) {
                try {
                    String query = "SELECT * FROM Persona WHERE tipo like 'Allenatore'";
                    PersonaPanel pp = new PersonaPanel(query);
                    setContentPane(pp);
                    setVisible(true);
                } catch (SQLException throwables) {
                    System.out.println("Errore Database Allenatore" + throwables);
                    throwables.printStackTrace();
                }
            }
            if (e.getSource() == this.Dirigenti) {
                try {
                    String query = "SELECT * FROM Persona WHERE tipo like 'Dirigente'";
                    PersonaPanel pp = new PersonaPanel(query);
                    setContentPane(pp);
                    setVisible(true);
                } catch (SQLException throwables) {
                    System.out.println("Errore Database Dirigente" + throwables);
                    throwables.printStackTrace();
                }

            }
            if (e.getSource() == this.Nuovo) {
                Rimborso rim = new Rimborso();
                setContentPane(rim);
                setVisible(true);
            }
            if(e.getSource() == this.Strutture){
                StrutturaPanel st = new StrutturaPanel();
                setContentPane(st);
                setVisible(true);
            }
        }

    }

    public void Desktop() {
        ImageIcon sportIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/sportinsime.jpg")));
        Image image = sportIcon.getImage();
        Image newing = image.getScaledInstance(600, 600, Image.SCALE_SMOOTH);
        sportIcon = new ImageIcon(newing);
        JLabel jLabelObject = new JLabel();
        jLabelObject.setText( "<html>Welcome to <br> SPORTINSIEME's <br> Sport Management");
        jLabelObject.setIcon(sportIcon);
        Font font = new Font("Helvetica", Font.BOLD, 30);
        jLabelObject.setFont(font);
        add(jLabelObject);
        setSize(900,600);
        setVisible(true);
    }
}
