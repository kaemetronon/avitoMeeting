package avito.testingAvito.service.dbase;

import avito.testingAvito.model.ClosedDate;
import avito.testingAvito.model.Meeting;
import avito.testingAvito.model.Person;
import avito.testingAvito.repo.ClosedDateRepo;
import avito.testingAvito.repo.MeetingRepo;
import avito.testingAvito.repo.PersonRepo;
import avito.testingAvito.service.mail.MailValidator;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DBaseFunctional {

    private final PersonRepo personRepo;
    private final MeetingRepo meetingRepo;
    private final ClosedDateRepo closedDateRepo;

    public DBaseFunctional(PersonRepo personRepo, MeetingRepo meetingRepo, ClosedDateRepo closedDateRepo) {
        this.personRepo = personRepo;
        this.meetingRepo = meetingRepo;
        this.closedDateRepo = closedDateRepo;
    }

    //meeting
    //create
    public void createMeeting(String title, String date) {
        Meeting meeting = new Meeting();
        meeting.setDate(date);
        meeting.setTitle(title);
        meetingRepo.save(meeting);
    }

    //get
    public List<String> findAllMeetingTitles() {

        Iterable<Meeting> meetings = meetingRepo.findAllByOrderByTitle();
        if (!meetings.iterator().hasNext())
            return null;
        List<String> resultList = new LinkedList<>();
        for (Meeting meeting : meetings) {
            resultList.add(meeting.getTitle());
        }
        return resultList;
    }

    public Object[] getMeeting(String title) {
        Object[] responce = new Object[3];

        Meeting meeting = meetingRepo.findByTitle(title);
        if (meeting == null) {

            responce[0] = "1";
            responce[1] = "Uncorrect title";
        } else {
            responce[0] = "0";
            responce[2] = meeting;
        }

        return responce;
    }

    public List<String> getFullList() {

        StringBuilder builder = new StringBuilder();
        List<String> resultList = new LinkedList<>();
        Iterable<Meeting> meetings = meetingRepo.findAllByOrderByTitle();
        if (!meetings.iterator().hasNext())
            return null;

        for (Meeting meeting : meetings) {

            builder.append(meeting.getTitle());

            Iterable<Person> people = meeting.getPersonSet();
            if (!people.iterator().hasNext()) {
                builder.append(" has no participants.");
                resultList.add(builder.toString());
                builder.setLength(0);
                continue;
            }
            builder.append(": ");
            for (Person person : people) {
                builder.append(person.getName() + ", ");
            }
            builder.setLength(builder.length() - 2);
            builder.append(".");
            resultList.add(builder.toString());
            builder.setLength(0);
        }
        return resultList;
    }

    //change
    public Object[] addPersonToMeeting(Meeting meeting, String name) {

        String meetingDate;
        ClosedDate closedDate;
        Set<ClosedDate> closedDateSetClasses;
        Meeting toRemove = null;
        Person person;

        Optional<Person> personOptional = personRepo.findByName(name);
        if (personOptional.isPresent()) {
            person = personOptional.get();
        }
        else return null;


        String[] responce = new String[2];

        if (person == null) {
            responce[0] = "1";
            responce[1] = "Uncorrect name. The employee doesn't exist.";
            return responce;
        }
        for (Person person1 : meeting.getPersonSet()) {
            if (person1.getId() == person.getId()) {
                responce[0] = "1";
                responce[1] = "The person already take a part at the meeting.";
                return responce;
            }
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

            closedDateRepo.save(closedDate);

            meeting.addOnePerson(person);
            meetingRepo.save(meeting);
            responce[1] = "";
        } else {
            Set<Meeting> meetingSet = person.getMeetingSet();
            for (Meeting meeting1 : meetingSet) {
                if (meeting1.getDate().equals(meetingDate))
                    toRemove = meeting1;
            }
            person.deleteOneMeeting(toRemove);
            person.addOneMeeting(meeting);

            personRepo.save(person);
        }
        responce[0] = "0";
        responce[1] = responce[1].concat("\n" + "The person signed up for the meeting");
        return responce;
    }

    //delete
    public Object[] deletePersonFromMeeting(Meeting meeting, String name) {

        String meetingDate = meeting.getDate();
        boolean coincidence = false;
        ClosedDate toRemove = null;
        Person person;

        Optional<Person> personOptional = personRepo.findByName(name);
        if (personOptional.isPresent()) {
            person =  personOptional.get();
        }
        else return null;


        Object[] responce = new Object[2];

        if (person == null) {
            responce[0] = "1";
            responce[1] = "Uncorrect name. The employee doesn't exist.";
        }

        for (Meeting tempMeeting : person.getMeetingSet()) {
            if (tempMeeting.getDate().equals(meetingDate)) {
                coincidence = true;
            }
        }
        if (!coincidence) {
            responce[0] = "1";
            responce[1] = "The person doesn't participate in this meeting.";
        }

        //idk for what, but without it app doesn't work normally
        meeting = meetingRepo.findByTitle(meeting.getTitle());
        Optional<Person> personOptional1 = personRepo.findByName(name);
        if (personOptional1.isPresent()) {
            person = personOptional1.get();
        }
        else return null;

        meeting.deleteOnePerson(person);
        meetingRepo.save(meeting);

        //at the momend person and meeting are untied
        //now i have to untie person and closedSet

        for (ClosedDate closedDate : person.getClosedDateSet()) {
            if (closedDate.getDate().equals(meetingDate)) {
                toRemove = closedDate;
            }
        }

        toRemove.deletePerson();
        closedDateRepo.delete(toRemove);

        responce[0] = "0";
        responce[1] = "The person was removed from meeting.";

        return responce;
    }

    public Object[] deleteMeeting(Meeting meeting) {
        String title = meeting.getTitle();
        String dateMeeting = meeting.getDate();
        ClosedDate toRemove = null;
        Set<ClosedDate> closedDateSet;
        List<Person> personList = new LinkedList<>();
        personList.addAll(meeting.getPersonSet());
        Object[] responce = new Object[2];

        for (int i = 0; i < personList.size(); i++) {
            closedDateSet = personList.get(i).getClosedDateSet();
            for (ClosedDate tempClosedDate : closedDateSet) {
                if (tempClosedDate.getDate().equals(dateMeeting)) {
                    toRemove = tempClosedDate;
                }
            }

            personList.get(i).deleteOneMeeting(meeting);
            if (toRemove != null)
                meeting.deleteOnePerson(personList.get(i));
            meetingRepo.save(meeting);

            personList.get(i).deleteOneClosedDate(toRemove);
            if (toRemove != null) {
                toRemove.deletePerson();
                closedDateRepo.delete(toRemove);
            }
        }

        meetingRepo.delete(meeting);


        if (meetingRepo.findByTitle(title) != null) {
            responce[0] = "0";
            responce[1] = "Do you really want to remove the meeting?. If yes, please repeat the same operation.";
        }
        else {
            responce[0] = "0";
            responce[1] = "The meeting was removed.";
        }

        return responce;
    }

    //person
    //create
    public boolean createPerson(String name, String mail) {

        if (!MailValidator.validateMail(mail))
            return false;
        Person person = new Person();
        person.setName(name);
        person.setMail(mail);
        personRepo.save(person);
        return true;
    }
}
