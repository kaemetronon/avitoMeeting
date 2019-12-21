package avito.testingAvito.controller;

import avito.testingAvito.model.ClosedDate;
import avito.testingAvito.model.Meeting;
import avito.testingAvito.model.Person;
import avito.testingAvito.repo.ClosedDateRepo;
import avito.testingAvito.repo.MeetingRepo;
import avito.testingAvito.repo.PersonRepo;
import avito.testingAvito.service.dbase.MeetingDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@Controller
public class MainController {

    @Autowired
    private MeetingDAO meetingDAO;

    @GetMapping("/")
    public String mainGet(Map<String, Object> model) {

        Iterable<Meeting> meetings = meetingDAO.findAll();

        model.put("msgList", "");
        model.put("MList", meetings);
        return "main";
    }

    @PostMapping("/viewFull")
    public String viewFull(Map<String, Object> model) {

        Iterable<Meeting> meetings = meetingDAO.findAll();

        model.put("MListFull", meetings);
        return "main";
    }

    @PostMapping("/meeting")
    public String meeting(String title, Map<String, Object> model) {

        Meeting meeting = meetingDAO.findByTitle(title);
        if (meeting == null) {
            model.put("msg", "Uncorrect title");
            return "main";
        }
        model.put("personList", meeting.getPersonSet());
        return "concreteMeeting";
    }
}





















