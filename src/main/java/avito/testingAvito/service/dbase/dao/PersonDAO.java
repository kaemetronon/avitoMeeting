package avito.testingAvito.service.dbase.dao;

import avito.testingAvito.model.Person;
import avito.testingAvito.repo.PersonRepo;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PersonDAO {

    private final PersonRepo personRepo;

    public PersonDAO(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    //find
    public Person findByName(String name) {

        Optional<Person> person = personRepo.findByName(name);
        if (person.isPresent()) {
            return person.get();
        }
        else return null;
    }

    //save
    public void save(Person person) {
        personRepo.save(person);
    }
}
