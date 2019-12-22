package avito.testingAvito.controller;


import avito.testingAvito.model.Meeting;
import avito.testingAvito.service.dbase.dao.ClosedDateDAO;
import avito.testingAvito.service.dbase.dao.MeetingDAO;
import avito.testingAvito.service.dbase.dao.PersonDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.Map;


@Controller
public class MainController {

    private final MeetingDAO meetingDAO;
    private final ClosedDateDAO closedDateDAO;
    private final PersonDAO personDAO;

    @Autowired
    public MainController(MeetingDAO meetingDAO, ClosedDateDAO closedDateDAO, PersonDAO personDAO) {
        this.meetingDAO = meetingDAO;
        this.closedDateDAO = closedDateDAO;
        this.personDAO = personDAO;
    }


//    @GetMapping("/")
//    public String scratch(Map<String, Object> model) {
//
//        Calendar calendar = Calendar.getInstance();

//        ClosedDate closedDate = new ClosedDate();
//        closedDate.setDate(new Date(calendar.get(Calendar.DAY_OF_MONTH),
//                calendar.get(Calendar.MONTH),
//                calendar.get(Calendar.YEAR)));
//        Set<ClosedDate> closedDateSet = new HashSet<>();
//        closedDateSet.add(closedDate);
//        closedDate.setPerson(null);

//        Meeting meeting = new Meeting();
//        meeting.setTitle("title");
//        meeting.setDate(new Date(calendar.get(Calendar.DAY_OF_MONTH),
//                calendar.get(Calendar.MONTH),
//                calendar.get(Calendar.YEAR)));
//        Set<Meeting> meetingSet = new HashSet<>();
//        meetingSet.add(meeting);

//        Person person = new Person();
//        person.setName("name");
//        person.setMail("mail");
//        person.setClosedDateSet(null);
//        Set<Person> personSet = new HashSet<>();
//        personSet.add(person);
//        person.setMeetingSet(null);
//
//        personDAO.save(person);
//
//
//
//        return "main";
//    }

    @GetMapping("/")
    public String mainGet(Map<String, Object> model) {

        Iterable<Meeting> meetings = meetingDAO.findAll();

        model.put("msgList", "");
        model.put("MList", meetings);
        return "main";
    }

    @PostMapping("/viewFull")
    public String viewFull(Map<String, Object> model) {
        //сделать доставание инфы в теле этого метода, на страницу отправить уже готовое

        Iterable<Meeting> meetings = meetingDAO.findAll();

        model.put("MListFull", meetings);
        return "main";
    }

}





















