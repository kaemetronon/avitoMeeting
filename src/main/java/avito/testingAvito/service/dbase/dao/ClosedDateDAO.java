package avito.testingAvito.service.dbase.dao;

import avito.testingAvito.model.ClosedDate;
import avito.testingAvito.repo.ClosedDateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClosedDateDAO {

    private final ClosedDateRepo closedDateRepo;

    @Autowired
    public ClosedDateDAO(ClosedDateRepo closedDateRepo) {
        this.closedDateRepo = closedDateRepo;
    }

    //save
    public void save(ClosedDate closedDate) {
        closedDateRepo.save(closedDate);
    }

    //delete
    public void delete(ClosedDate closedDate) { closedDateRepo.delete(closedDate); }
}
