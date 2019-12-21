package avito.testingAvito.repo;

import avito.testingAvito.model.Meeting;
import org.springframework.data.repository.CrudRepository;

public interface MeetingRepo extends CrudRepository<Meeting, Long> {

    Meeting findByTitle(String title);

    Iterable<Meeting> findAllByOrderByTitle();
}
