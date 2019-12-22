package avito.testingAvito.controller.meeting;

import avito.testingAvito.model.ClosedDate;
import avito.testingAvito.model.Meeting;
import avito.testingAvito.model.Person;
import avito.testingAvito.service.dbase.DBaseFunctional;
import avito.testingAvito.service.dbase.dao.ClosedDateDAO;
import avito.testingAvito.service.dbase.dao.MeetingDAO;
import avito.testingAvito.service.dbase.dao.PersonDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/meeting")
public class MeetingController {

    private Map<String, Object> tempMap = new HashMap<>();
    private final DBaseFunctional dBaseFunctional;

    @Autowired
    public MeetingController(DBaseFunctional dBaseFunctional) {
        this.dBaseFunctional = dBaseFunctional;
    }

    //input meeting page and add/delete pers, cancel meeting, send mails
    @PostMapping("/")
    public String meeting(String title, Map<String, Object> model) {

        Object[] responce = dBaseFunctional.getMeeting(title);
        if (responce[0].toString().equals("1")) {
            model.put("msg", responce[1].toString());
            return "main";
        }

        tempMap.put("meeting", (Meeting) responce[3]);
        model.put("msg", "");
        Meeting meeting = (Meeting) responce[3];
        model.put("personList", meeting.getPersonSet());
        return "meeting";
    }

    //adds a persone to meeting
    @PostMapping("/addPerson")
    public String addPersone(String name, Map<String, Object> model) {

        Meeting meeting = (Meeting) tempMap.get("meeting");

        Object[] responce = dBaseFunctional.addPersonToMeeting(meeting, name);
        if (responce[0].toString().equals("0")) {
            model.put("msg", responce[1]);
            return "meeting";
        }
        model.put("msg", responce[1]);
        return "meeting";
    }

    //deletes a persone from meeting
    @PostMapping("/deletePerson")
    public String deletePerson(String name, Map<String, Object> model) {

        Meeting meeting = (Meeting) tempMap.get("meeting");
        Object[] responce = dBaseFunctional.deletePersonFromMeeting(meeting, name);
        if (responce[0].toString().equals("1")) {
            model.put("msg", responce[1]);
            return "meeting";
        }

        model.put("msg", responce[1]);
        return "meeting";
    }


    //cancels the meeting
    @PostMapping("/cancelMeeting")
    public String cancelMeeting(Map<String, Object> model) {

        Meeting meeting = (Meeting) tempMap.get("meetnig");
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
        return "main";
    }

    @PostMapping("/sendMail")
    public String sendMail() {

        return "";
    }
}
