package avito.testingAvito.service.dbase;

import etu.course.model.Department;
import etu.course.repo.DepartmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DepartmentDAO {

    private final DepartmentRepo departmentRepo;

    @Autowired
    public DepartmentDAO(DepartmentRepo departmentRepo) {
        this.departmentRepo = departmentRepo;
    }

    //find
    public Iterable<Department> findAll() {
        Iterable<Department> departments = departmentRepo.findAllByOrderByTitle();
        if (!departments.iterator().hasNext())
            return null;
        else return departments;
    }

    public Department findByTitle(String title) {
        return departmentRepo.findByTitle(title);
    }

    //save
    public void save(Department department) {
        departmentRepo.save(department);
    }


}
