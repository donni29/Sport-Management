package Exam;
/**
 * a class that allows you to show and manage a calendar using the mindfusion class
 * @authors Rossi Nicolò Delsante Laura
 */

import Exam.Utils.Calendario;
import Exam.Utils.DBManager;
import Exam.Utils.Form;
import com.mindfusion.common.DateTime;
import com.mindfusion.common.Duration;
import com.mindfusion.scheduling.*;
import com.mindfusion.scheduling.model.Appointment;
import com.mindfusion.scheduling.model.Item;
import com.mindfusion.scheduling.model.ItemEvent;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale.Builder;

public class  CreateCalendar extends JPanel {
    @Serial
    private static final long serialVersionUID = 1L;

    public Calendar calendar;
    /**
     * Calendar initialization and setting
     * @param  nome_struttura - the name of Facility needed
     * @throws SQLException - problem with the table Struttura or no entry in it
     * @throws ClassNotFoundException - no definition for the class
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

        calendar.addCalendarListener(new Listener(calendar, nome_struttura));
        try {
            establishConnection(nome_struttura);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        add(calendar,BorderLayout.CENTER);
        setVisible(true);
    }

    /**
     * Insert in the new empty Calendar the appointments already present in the DB of the Calendar table
     * @param  nome_struttura -the name of the specific Structure in which to insert the appointments
     * @throws SQLException if there is no Table Calendario or no Entry in it
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
                this.calendar.getSchedule().getItems().add(a);
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * the listener method of the Calendar , with its override methods
     */
    public static class Listener extends CalendarAdapter {

        public Listener(Calendar calendar, Object nome_struttura) {
            calendar.addCalendarListener(new CalendarAdapter(){
                public void showForm(Item item) throws ParseException {
                    new Form(item,nome_struttura,calendar);

                }/*
                public void showform(item item){
                    appointmentform form = new appointmentform(calendar.getschedule());
                    form.setlocale(new locale("it","italy"));
                    form.setappointment((appointment)item );
                    form.settimeformat("hh:mm:ss");

                    form.setvisible(true);
                    form.addwindowlistener(new windowadapter()
                    {
                        @override
                        public void windowclosed(windowevent we)
                        {
                            if (form.getdialogresult() == dialogresult.remove)
                            {
                                try {
                                    if (item.getrecurrencestate() == recurrencestate.occurrence ||
                                            item.getrecurrencestate() == recurrencestate.exception) {
                                        item.getrecurrence().markexception(item, true);
                                        deleteevento(item,nome_struttura);
                                    } else {
                                        calendar.getschedule().getitems().remove(item);
                                        deleteevento(item, nome_struttura);
                                    }
                                } catch (sqlexception e) {
                                    e.printstacktrace();
                                }
                            }else{
                                try {
                                    insertevento(item, nome_struttura);

                                } catch (sqlexception throwables) {
                                    throwables.printstacktrace();
                                }
                            }
                        }
                    });

                }*/

                @Override
                public void itemModified(ItemModifiedEvent itemModifiedEvent) {
                    try {
                        Form.UpdateEvento(itemModifiedEvent,nome_struttura);
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

