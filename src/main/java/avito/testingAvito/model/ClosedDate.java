package avito.testingAvito.model;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "closetDate")
public class ClosedDate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String date;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    private Person person;

    public ClosedDate() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = new SimpleDateFormat("yyyy.MM.dd").format(date);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void deletePerson() {
        person = null;
    }
}
