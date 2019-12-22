package avito.testingAvito.repo;

import avito.testingAvito.model.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PersonRepo extends CrudRepository<Person, Long> {

    Optional<Person> findByName(String name);

}
