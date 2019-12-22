package avito.testingAvito.service.dbase.dao;

import avito.testingAvito.model.Meeting;
import avito.testingAvito.repo.MeetingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MeetingDAO {

    private final MeetingRepo meetingRepo;

    @Autowired
    public MeetingDAO(MeetingRepo meetingRepo) {
        this.meetingRepo = meetingRepo;
    }

    //find
    public Iterable<Meeting> findAll() {
        Iterable<Meeting> meetings = meetingRepo.findAllByOrderByTitle();
        if (!meetings.iterator().hasNext())
            return null;
        else return meetings;
    }

    public Meeting findByTitle(String title) {
        return meetingRepo.findByTitle(title);
    }

    //save
    public void save(Meeting meeting) {
        meetingRepo.save(meeting);
    }

    //delete
    public void delete(Meeting meeting) { meetingRepo.delete(meeting); }

}
