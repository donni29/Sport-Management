package Exam;

//questo è il nostro progetto, madonne//
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.SQLException;

public class SBM extends JFrame implements ActionListener {
    @Serial
    private static final long serialVersionUID = 1L;
    public static JMenu openArchivio;
    private static JMenuItem close;
    private static JMenuItem Atleti;
    private static JMenuItem Allenatori;
    private static JMenuItem Dirigenti;
    private static JMenuItem Nuovo;
    private static JMenuItem Open;
    private static JFileChooser OpenSource;
    private static ArchivioPanel panelAtleti;
    private static JTextArea textArea;
    private final ImageIcon sportIcon;
    private final JLabel label;



    /*
    Creare una variabile model( come nell'esempio SM_Updatable < sausagemanager < jdbc per separare la grafica dai dati
     */
    DB_Model model;

    public SBM() {
        super("Sport Business Management ");


        JMenuBar menuBar = new JMenuBar();
        JPanel panel =new JPanel();
        JMenu Archivio = new JMenu("Archivio");
        menuBar.add(Archivio);
        JMenu Rimborso = new JMenu("Rimborso");
        JMenu Strutture = new JMenu("Strutture");
        menuBar.add(Rimborso);
        menuBar.add(Strutture);

        sportIcon =new ImageIcon(this.getClass().getResource("/sportinsime.jpg"));
        label = new JLabel(sportIcon);
        label.setSize(600,600);
        setLocationRelativeTo(null);
        add(label);


        openArchivio = new JMenu ("Open Archivio di ...");
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
        //prova git//

        Open = new JMenuItem("Apri...");
        Open.addActionListener(this);
        Rimborso.add(Open);

        textArea =new JTextArea("");

        setJMenuBar(menuBar);
        setExtendedState(MAXIMIZED_BOTH);
        setSize(900, 600);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);


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
            dispose();
        } else {
            if (e.getSource() == this.Open) {
                OpenSource = new JFileChooser();

                int option = OpenSource.showOpenDialog(this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    textArea.setText("");

                    try {
                        File file = new File((OpenSource.getSelectedFile().getPath()));
                        System.out.println(String.valueOf(new FileReader(OpenSource.getSelectedFile().getPath())));
                        if (!Desktop.isDesktopSupported()) {
                            System.out.println("not supported");
                        }
                        Desktop desktop = Desktop.getDesktop();
                        if (file.exists()) {
                            desktop.open(file);
                        }
                    } catch (IOException e2) {
                        e2.printStackTrace();
                        System.out.println("IOException"+ "\n" + e2);
                    }
                }
            }
                if (e.getSource() == this.Atleti) {
                    /*panelAtleti = new ArchivioPanel();
                    setContentPane(panelAtleti);
                    setVisible(true);*/
                    try {
                        String query ="SELECT * FROM Persona WHERE tipo like 'Atleta'";
                        PersonaPanel pp= new PersonaPanel(query);
                        setContentPane(pp);
                        setVisible(true);

                    } catch (SQLException throwables) {
                        System.out.println("Errore Database Atleta"+ throwables);
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
                            System.out.println("Errore Database Allenatore"+ throwables);
                            throwables.printStackTrace();
                        }
                    }
                    if (e.getSource()== this.Dirigenti){
                        try {
                            String query ="SELECT * FROM Persona WHERE tipo like 'Dirigente'";
                            PersonaPanel pp= new PersonaPanel(query);
                            setContentPane(pp);
                            setVisible(true);
                        } catch (SQLException throwables) {
                            System.out.println("Errore Database Dirigente"+ throwables);
                            throwables.printStackTrace();
                        }

                    }
                }

            }
        }
