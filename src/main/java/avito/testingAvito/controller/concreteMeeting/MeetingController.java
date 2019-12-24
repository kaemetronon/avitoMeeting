package avito.testingAvito.controller.concreteMeeting;

import avito.testingAvito.model.Meeting;
import avito.testingAvito.model.Person;
import avito.testingAvito.service.dbase.DBaseFunctional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
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

        Object[] responce = dBaseFunctional.getMeeting(title.trim());
        if (responce[0].toString().equals("1")) {
            List<String> meetingTitles = dBaseFunctional.findAllMeetingTitles();
            model.put("msg", responce[1].toString());
            model.put("listMsg", "List of active meetings:");
            model.put("list", meetingTitles);
            return "main";
        }

        Meeting meeting = (Meeting) responce[2];
        tempMap.put("meeting", meeting);
        Set<Person> personSet = meeting.getPersonSet();
        if (personSet.isEmpty())
            model.put("msg", title + " is empty.");
        else {
            model.put("msg", "List of persons: ");
            model.put("personList", meeting.getPersonSet());
            model.put("notEmpty", "");
        }

        return "meeting";
    }


    //adds a persone to meeting
    @PostMapping("/addPerson")
    public String addPersone(String name, Map<String, Object> model) {

        Meeting meeting = (Meeting) tempMap.get("meeting");

        Object[] responce = dBaseFunctional.addPersonToMeeting(meeting, name);
        model.put("msg", responce[1]);
        return "main";
    }

    //deletes a persone from meeting
    @PostMapping("/deletePerson")
    public String deletePerson(String name, Map<String, Object> model) {

        Meeting meeting = (Meeting) tempMap.get("meeting");
        Object[] responce = dBaseFunctional.deletePersonFromMeeting(meeting, name);

        model.put("msg", responce[1]);
        return "main";
    }

    //cancels the meeting
    @PostMapping("/cancelMeeting")
    public String cancelMeeting(Map<String, Object> model) {

        Meeting meeting = (Meeting) tempMap.get("meeting");
        Object[] responce = dBaseFunctional.deleteMeeting(meeting);
        if (!responce[0].toString().equals("0"))
            model.put("msg", "IDK what is it, but meeting still exists");

        List<String> meetingTitles = dBaseFunctional.findAllMeetingTitles();
        if (meetingTitles == null) {
            model.put("msg", "No meetings yet.");
        }
        else {
            model.put("msg", responce[1]);
            model.put("listMsg", "List of active meetings:");
            model.put("list", meetingTitles);
        }
        return "main";
    }

    @PostMapping("/sendMail")
    public String sendMail() {

        return "";
    }
}
