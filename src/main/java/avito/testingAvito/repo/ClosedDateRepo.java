package avito.testingAvito.repo;

import avito.testingAvito.model.ClosedDate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClosedDateRepo extends CrudRepository<ClosedDate, Long> {
}
