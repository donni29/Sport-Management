package Exam;
/**
 * a class that allows you to show and manage a calendar using the mindfusion class
 * @authors Rossi Nicolò Delsante Laura
 */

import Exam.Utils.Calendario;
import Exam.Utils.DBManager;
import Exam.Utils.Form;
import com.mindfusion.common.DateTime;
import com.mindfusion.common.DayOfWeek;
import com.mindfusion.common.Duration;
import com.mindfusion.scheduling.*;
import com.mindfusion.scheduling.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Locale.Builder;

public class  CreateCalendar extends JPanel {
    @Serial
    private static final long serialVersionUID = 1L;

    public Calendar calendar;
    /**
     * Calendar initialization and setting
     * @param - nome_struttura
     * @throws -SQLException
     * @throws -ClassNotFoundException
     */
    public CreateCalendar( Object nome_struttura) throws SQLException, ClassNotFoundException {
        setLayout(new BorderLayout(10,10));
        setSize(600,400);

        calendar = new Calendar();

        calendar.beginInit();
        calendar.getTimetableSettings().setTwelveHourFormat(false);
        calendar.getTimetableSettings().setShowAM(false);
        calendar.setCurrentView(CalendarView.Timetable);
        calendar.setDateTimeFormat(new DateTimeInfo(calendar,new Builder().setLanguage("it").setRegion("IT").build()));
        calendar.getTimetableSettings().setShowCurrentTime(true);
        calendar.getTimetableSettings().setHeaderDateFormat("EEE dd-MM");
        calendar.setTheme(ThemeType.Silver);

        for (int i= 1 ; i < 7; i++){
            calendar.getTimetableSettings().getDates().add(DateTime.today().addDays(i));
        }
        calendar.getTimetableSettings().setStartTime(510);
        calendar.getTimetableSettings().setCellTime(Duration.fromHours(1));
        calendar.getTimetableSettings().setVisibleColumns(8);
        calendar.getTimetableSettings().getStyle().setBorderLeftWidth(5);
        calendar.getTimetableSettings().setShowNavigationButtons(true);
        calendar.getTimetableSettings().setScrollStep(7);
        calendar.getTimetableSettings().setCellSize(35);
        calendar.setEnableDragCreate(true);

        calendar.endInit();

        calendar.addCalendarListener(new Listener(calendar,nome_struttura));
        try {
            establishConnection(nome_struttura);
        } catch (SQLException e) {
            System.out.println(e);
        }

        add(calendar,BorderLayout.CENTER);
        setVisible(true);
    }

    /**
     * Insert in the new empty Calendar the appointments already present in the DB of the Calendar table
     * @param - nome_struttura the name of the specific Structure in which to insert the appointments
     * @throws - SQLException if there is no Table Calendario or no Entry in it
     */

    public void establishConnection(Object nome_struttura) throws SQLException {
        Statement statement = DBManager.getConnection().createStatement();
        Calendario Cal;
        try {
            String query = String.format("SELECT * FROM CALENDARIO WHERE NOME_STRUTTURA LIKE '%s' ",nome_struttura);
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date inizio = sdf.parse(rs.getString("inizio_prenotazione"));
                Date fine = sdf.parse(rs.getString("fine_prenotazione"));

                Cal = new Calendario(rs.getString("nome_struttura"),
                        rs.getString("info_prenotazione"),
                        rs.getString("descrizione_prenotazione"),
                        new DateTime( inizio),
                        new DateTime(fine),
                        rs.getInt("numero_ricorsioni"));


                Appointment a = new Appointment();
                a.setId(Cal.getNome_struttura());
                a.setHeaderText(Cal.getInfo_struttura());
                a.setDescriptionText(Cal.getDescrizione_prenotazione());
                a.setStartTime(Cal.getInizio_prenotazione());
                a.setEndTime(Cal.getFine_prenotazione());
                a.setAllowChangeEnd(true);
                a.setAllowChangeStart(true);
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
    }
    /**
     * method that allows to update a single appointment in the DB
     * @param  itemModifiedEvent the appointment that must be updated
     * @param  nome_struttura the name of the updated appointment structure
     * @throws  SQLException if there is no table Calendario or a problem with entering data
     */
    public void UpdateEvento(ItemModifiedEvent itemModifiedEvent, Object nome_struttura)  throws  SQLException{
        Statement statement =  DBManager.getConnection().createStatement();
        String query = String.format("UPDATE CALENDARIO SET descrizione_prenotazione = '%s' ,inizio_prenotazione = '%s' ,fine_prenotazione = '%s'  WHERE nome_struttura LIKE '%s' and inizio_prenotazione like '%s'",
                itemModifiedEvent.getItem().getDescriptionText(),
                itemModifiedEvent.getItem().getStartTime().toString("yyyy-MM-dd HH:mm:ss"),
                itemModifiedEvent.getItem().getEndTime().toString("yyyy-MM-dd HH:mm:ss"),
                nome_struttura,
                itemModifiedEvent.getOldStartTime().toString("yyyy-MM-dd HH:mm:ss"));
        statement.executeUpdate(query);
        statement.close();
    }
    /**
     * the listener method of the Calendar , with its override methods
     */
    public class Listener extends CalendarAdapter {

        public Listener(Calendar calendar, Object nome_struttura) {
            calendar.addCalendarListener(new CalendarAdapter(){
                public void showForm(Item item) throws ParseException {
                    new Form(item,nome_struttura);
                }/*
                public void showForm(Item item){
                    AppointmentForm form = new AppointmentForm(calendar.getSchedule());
                    form.setLocale(new Locale("it","ITALY"));
                    form.setAppointment((Appointment)item );
                    form.setTimeFormat("HH:mm:ss");

                    form.setVisible(true);
                    form.addWindowListener(new WindowAdapter()
                    {
                        @Override
                        public void windowClosed(WindowEvent we)
                        {
                            if (form.getDialogResult() == DialogResult.Remove)
                            {
                                try {
                                    if (item.getRecurrenceState() == RecurrenceState.Occurrence ||
                                            item.getRecurrenceState() == RecurrenceState.Exception) {
                                        item.getRecurrence().markException(item, true);
                                        DeleteEvento(item,nome_struttura);
                                    } else {
                                        calendar.getSchedule().getItems().remove(item);
                                        DeleteEvento(item, nome_struttura);
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }else{
                                try {
                                    InsertEvento(item, nome_struttura);

                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                }
                            }
                        }
                    });

                }*/

                @Override
                public void itemModified(ItemModifiedEvent itemModifiedEvent) {
                    try {
                        UpdateEvento(itemModifiedEvent,nome_struttura);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void itemClick(ItemMouseEvent e) {
                    if(e.getClicks() == 2) {
                        try {
                            showForm(e.getItem());
                        } catch (ParseException parseException) {
                            parseException.printStackTrace();
                        }
                    }
                }

                @Override
                public void itemCreated(ItemEvent e) {
                    calendar.getSelection().reset();
                    calendar.getSelection().add(e.getItem().getStartTime(),
                            e.getItem().getEndTime());
                    try {
                        showForm(e.getItem());
                    } catch (ParseException parseException) {
                        parseException.printStackTrace();
                    }
                }
            });
        }
        }

}

