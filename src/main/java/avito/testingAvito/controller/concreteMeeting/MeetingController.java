package avito.testingAvito.controller.concreteMeeting;

import avito.testingAvito.model.Meeting;
import avito.testingAvito.service.dbase.DBaseFunctional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

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
        Object[] responce = dBaseFunctional.deleteMeeting(meeting);
        if (!responce[0].toString().equals("0"))
            model.put("msg", "IDK what is it, but meeting still exists");

        model.put("msg", responce[1]);
        return "main";
    }

    @PostMapping("/sendMail")
    public String sendMail() {

        return "";
    }
}
