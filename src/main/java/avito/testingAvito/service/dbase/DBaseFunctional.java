package avito.testingAvito.service.dbase;

import avito.testingAvito.model.ClosedDate;
import avito.testingAvito.model.Meeting;
import avito.testingAvito.model.Person;
import avito.testingAvito.service.dbase.dao.ClosedDateDAO;
import avito.testingAvito.service.dbase.dao.MeetingDAO;
import avito.testingAvito.service.dbase.dao.PersonDAO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Component
public class DBaseFunctional {

    private final PersonDAO personDAO;
    private final MeetingDAO meetingDAO;
    private final ClosedDateDAO closedDateDAO;

    public DBaseFunctional(PersonDAO personDAO, MeetingDAO meetingDAO, ClosedDateDAO closedDateDAO) {
        this.personDAO = personDAO;
        this.meetingDAO = meetingDAO;
        this.closedDateDAO = closedDateDAO;
    }

    public Object[] getMeeting(String title) {
        Object[] responce = new Object[3];

        Meeting meeting = meetingDAO.findByTitle(title);
        if (meeting == null) {

            responce[0] = "1";
            responce[1] = "Uncorrect title";
        }

        responce[0] = "0";
        responce[3] = meeting;

        return responce;
    }

    public Object[] addPersonToMeeting(Meeting meeting, String name) {

        String meetingDate;
        ClosedDate closedDate;
        Set<ClosedDate> closedDateSetClasses;
        Meeting toRemove = null;
        Person person = personDAO.findByName(name);
        String[] responce = new String[2];

        if (person == null) {
            responce[0] = "1";
            responce[1] = "Uncorrect name. The employee doesn't exist.";
            return responce;
        }

        meetingDate = meeting.getDate();
        closedDateSetClasses = person.getClosedDateSet();

        for (ClosedDate closedDateTemp : closedDateSetClasses) {
            if (closedDateTemp.getDate().equals(meetingDate)) {
                responce[1] = "Warning: the person already had an appointment meeting. " +
                        "Old meeting was removed and exchanged";
            }
        }

        if (responce[1] == null) { // free day
            closedDate = new ClosedDate();
            closedDate.setDate(meetingDate);
            closedDate.setPerson(person);

            person.addOneClosedSet(closedDate);
            person.addOneMeeting(meeting);

            personDAO.save(person);
        } else {
            Set<Meeting> meetingSet = person.getMeetingSet();
            for (Meeting meeting1 : meetingSet) {
                if (meeting1.getDate().equals(meetingDate))
                    toRemove = meeting1;
            }
            person.deleteOneMeeting(toRemove);
            person.addOneMeeting(meeting);

            personDAO.save(person);
        }
        responce[0] = "0";
        responce[1] = responce[1].concat("\n" + "The person signed up for the meeting");
        return responce;
    }

    public Object[] deletePersonFromMeeting(Meeting meeting, String name) {

        String meetingDate = meeting.getDate();
        boolean coincidence = false;
        ClosedDate toRemove = null;
        Person person = personDAO.findByName(name);
        Object[] responce = new Object[2];

        if (person == null) {
            responce[0] = "1";
            responce[1] = "Uncorrect name. The employee doesn't exist.";
        }

        for (Meeting tempMeeting : person.getMeetingSet()) {
            if (tempMeeting.getTitle().equals(meetingDate)) {
                coincidence = true;
            }
        }
        if (!coincidence) {
            responce[0] = "1";
            responce[1] = "The person doesn't participate in this meeting.";
        }

        meeting.deleteOnePerson(person);
        person.deleteOneMeeting(meeting);

        meetingDAO.save(meeting);
        //at the momend person and meeting are untied
        //now i have to untie person and closedSet

        for (ClosedDate closedDate : person.getClosedDateSet()) {
            if (closedDate.getDate().equals(meetingDate)) {
                toRemove = closedDate;
            }
        }
        person.deleteOneClosedDate(toRemove);
        toRemove.deletePerson();

        closedDateDAO.delete(toRemove);

        responce[0] = "0";
        responce[1] = "The person was remover from meeting.";

        return responce;
    }
//--------------------------------------------
Person person = personDAO.findByName("n1");
    Meeting meeting = meetingDAO.findByTitle("t");
    boolean is = false;
    ClosedDate toRemove = null;

        meeting.deleteOnePerson(person);
        person.deleteOneMeeting(meeting);

        meetingDAO.save(meeting);
//--------------------------------------------
    public Object[] deleteMeeting(Meeting meeting) {
        String dateMeeting = meeting.getDate();
        ClosedDate closedDate, toRemove = null;
        Set<ClosedDate> closedDateSet;
        Set<Person> personSet = meeting.getPersonSet();

        for (Person person : personSet) {
            closedDateSet = person.getClosedDateSet();
            for (ClosedDate tempDate : closedDateSet) {
                if (tempDate.getDate().equals(dateMeeting)) {
                    toRemove = tempDate;
                }
            }
            person.deleteOneClosedDate(toRemove);
            person.deleteOneMeeting(meeting);

            closedDateDAO.save(toRemove);

            personDAO.save(person);
        }

        meetingDAO.delete(meeting);

        model.put("msg", "The meeting was removed.");
        return null;
    }
}
