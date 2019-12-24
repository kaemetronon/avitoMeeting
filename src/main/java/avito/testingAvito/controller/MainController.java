package avito.testingAvito.controller;


import avito.testingAvito.model.Meeting;
import avito.testingAvito.service.dbase.DBaseFunctional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;


@Controller
public class MainController {

    private final DBaseFunctional dBaseFunctional;

    @Autowired
    public MainController(DBaseFunctional dBaseFunctional) {
        this.dBaseFunctional = dBaseFunctional;
    }

    @GetMapping("/")
    public String mainGet(Map<String, Object> model) {

        List<String> meetingTitles = dBaseFunctional.findAllMeetingTitles();
        if (meetingTitles == null) {
            model.put("msg", "No meetings yet.");
        } else {
            model.put("listMsg", "List of active meetings:");
            model.put("list", meetingTitles);
        }

        return "main";
    }

    @GetMapping("/addMeeting")
    public String addMeetingGet(Map<String, Object> model) {
        model.put("meeting", "");
        return "addEntitiy";
    }

    @PostMapping("/addMeeting")
    public String addMeetingPost(String title, String date, Map<String, Object> model) {
        dBaseFunctional.createMeeting(title, date);
        List<String> meetingTitles = dBaseFunctional.findAllMeetingTitles();

        model.put("msg", "New meeting was created.");
        model.put("listMsg", "List of active meetings:");
        model.put("list", meetingTitles);

        return "main";
    }

    @GetMapping("/addPerson")
    public String addPersonGet(Map<String, Object> model) {
        model.put("person", "");
        return "addEntitiy";
    }

    @PostMapping("/addPerson")
    public String addPersonPost(String name, String mail, Map<String, Object> model) {
        if (!dBaseFunctional.createPerson(name, mail))
            model.put("msg", "Error: invalid email.");
        else {
            model.put("msg", "New person was created.");
        }

        List<String> meetingTitles = dBaseFunctional.findAllMeetingTitles();
        if (meetingTitles == null) {
            model.put("msg", "No meetings yet.");
        } else {
            model.put("listMsg", "List of active meetings:");
            model.put("list", meetingTitles);
        }

        return "main";
    }

    @GetMapping("/viewFull")
    public String viewFull(Map<String, Object> model) {
        model.put("msg", "Full list:");
        List<String> fullList = dBaseFunctional.getFullList();
        if (fullList.isEmpty()) {
            model.put("msg", "No meetings yet.");
        } else {
            model.put("list", fullList);
        }
        return "main";
    }
}





















