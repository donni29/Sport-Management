package Exam;


import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;


public class Rimborso extends JPanel implements ActionListener {
    public static String[] options = {"Ciclismo","Podismo","Calcio"};

    JButton rim;
    JTextField days;
    JTextField tcf= new JTextField();
    JTextField tga1 =new JTextField();
    JTextField tga2 =new JTextField();
    JTextField tga3 =new JTextField();
    JTextField tga4 =new JTextField();
    JTextField tga5 =new JTextField();
    JTextField tga6 =new JTextField();
    JTextField tga7 =new JTextField();
    JTextField tga8 =new JTextField();
    JComboBox<String> jc;


    public Rimborso() {

        jc =new JComboBox(options);
        JPanel p1 =new JPanel(new GridLayout(2,2));
        p1.add(new JLabel("CF"));
        p1.add(tcf);
        JPanel p2 =new JPanel(new FlowLayout());
        p2.add(jc);
        p2.add(new JLabel("Giorno Allenamento:"));
        p2.add(days);

        setLayout(new BorderLayout());
        add(p1,BorderLayout.PAGE_START);




    }

    public void Generate_PDF() {
        try {
            String file_name = "C:\\Users\\Utente\\Desktop\\Sport-Management\\Rimborso\\pdf_prova.pdf";
            com.itextpdf.text.Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file_name));

            document.open();
            Paragraph para =new Paragraph("Questa è una prova del file pdf_prova");
            document.add(para);
            document.close();

            System.out.println("La creazione del pdf è avvenuta con successo");
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==this.rim){
            Generate_PDF();
        }
    }
}

