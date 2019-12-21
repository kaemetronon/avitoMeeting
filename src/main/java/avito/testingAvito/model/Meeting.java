package avito.testingAvito.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private Date date;

    private Set<Person> personSet = new HashSet<>();
    @ManyToMany
    @JoinTable(name = "person_meeting",
            joinColumns = @JoinColumn(name = "meeting_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    public Set<Person> getPersonSet() {
        return personSet;
    }

    public Meeting() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPersonSet(Set<Person> personSet) {
        this.personSet = personSet;
    }
}
