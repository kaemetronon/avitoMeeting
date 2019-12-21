package avito.testingAvito.repo;

import etu.course.model.Department;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepo extends CrudRepository<Department, Long> {

    Department findByTitle(String title);

    Iterable<Department> findAllByOrderByTitle();
}
