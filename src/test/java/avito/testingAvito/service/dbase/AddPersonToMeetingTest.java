package avito.testingAvito.service.dbase;

import avito.testingAvito.model.Meeting;
import avito.testingAvito.model.Person;
import avito.testingAvito.repo.MeetingRepo;
import avito.testingAvito.repo.PersonRepo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;


class AddPersonToMeetingTest {

    Meeting meeting;
    Person person1;
    DBaseFunctional functional;

    private final MeetingRepo meetingRepo;

    @Autowired
    public AddPersonToMeetingTest(MeetingRepo meetingRepo) {
        this.meetingRepo = meetingRepo;
    }


    @Before
    public void setUp() {
        meeting = new Meeting();
        meeting.setTitle("Title");
        meeting.setDate("1970.01.01");
        meetingRepo.save(meeting);
    }

    @Test
    public void addPersonToMeeting1() {

        boolean actual = false;

        person1 = new Person();
        person1.setMail("vasya01@gmail.com");
        person1.setName("Vasya");
        person1.addOneMeeting(meeting);
        functional.addPersonToMeeting(meeting, person1.getName());


        Meeting expected = functional.getMeetingObject("Title");
        Set<Person> personSet = expected.getPersonSet();
        for (Person person : personSet) {
            if (person.getName().equals("Vasya"));
            actual = true;
        }

        Assert.assertEquals(true, actual);
    }


}