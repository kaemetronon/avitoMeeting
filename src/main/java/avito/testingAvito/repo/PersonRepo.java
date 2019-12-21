package avito.testingAvito.repo;

import avito.testingAvito.model.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepo extends CrudRepository<Person, Long> {

}
