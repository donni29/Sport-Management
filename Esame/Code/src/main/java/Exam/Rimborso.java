package Exam;


import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;


public class Rimborso extends JPanel implements ActionListener {

    JPanel panel;
    JButton rim;

    public Rimborso() {

        rim =new JButton("pdf");
        rim.addActionListener(this);
        panel.add(rim);




    }

    public void Generate_PDF() {
        try {
            String file_name = "C:\\Users\\Utente\\Desktop\\Sport-Management\\pdf_prova.pdf";
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

