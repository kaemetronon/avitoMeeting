package avito.testingAvito.controller;


import avito.testingAvito.service.dbase.DBaseFunctional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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

        StringBuilder builder = new StringBuilder();
        builder.append("List of active meetings:\n");
        builder.append(dBaseFunctional.findAllMeetings());

        model.put("msg", builder.toString());

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
        model.put("msg", "New meeting was created.");
        return "main";
    }

    @GetMapping("/addPerson")
    public String addPersonGet(Map<String, Object> model) {
        model.put("person", "");
        return "addEntity";
    }

    @PostMapping("/addPerson")
    public String addPersonPost(String name, String mail, Map<String, Object> model) {
        if (!dBaseFunctional.createPerson(name, mail))
            model.put("msg", "Error: invalid email.");
        model.put("msg", "New person was created.");
        return "main";
    }

    @PostMapping("/viewFull")
    public String viewFull(Map<String, Object> model) {

        StringBuilder fullLIst = new StringBuilder();
        fullLIst.append("Full list: \n");
        fullLIst.append(dBaseFunctional.getFullList());
        model.put("msg", fullLIst);
        return "main";
    }

}





















