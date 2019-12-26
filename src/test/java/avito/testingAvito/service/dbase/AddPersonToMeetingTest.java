package avito.testingAvito.service.dbase;

import avito.testingAvito.model.Meeting;
import avito.testingAvito.model.Person;
import org.junit.Before;
import org.junit.Test;


class AddPersonToMeetingTest {

    Meeting meeting;
    Person person1;
    Person person2;
    DBaseFunctional functional;

    @Before
    void setUp() {
        meeting = new Meeting();
        meeting.setTitle("Title");
        meeting.setDate("1970.01.01");

        person1 = new Person();
        person1.setMail("vasya01@gmail.com");
        person1.setName("Vasya");
        person1.addOneMeeting(meeting);

        person1 = new Person();
        person1.setMail("kolya99@gmail.com");
        person1.setName("Kolya");
        person2.addOneMeeting(meeting);

        meeting.addOnePerson(person1);
        meeting.addOnePerson(person2);

    }

    @Test
    void addPersonToMeeting1() {

        functional.addPersonToMeeting(meeting, "Vasya");
    }


}