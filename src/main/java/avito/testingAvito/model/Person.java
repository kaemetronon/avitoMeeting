package avito.testingAvito.model;


import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String mail;

    @OneToMany(orphanRemoval = true, mappedBy = "person", fetch = FetchType.EAGER)
    private Set<ClosedDate> closedDateSet = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "person_meeting",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "meeting_id"))
    private Set<Meeting> meetingSet;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setMeetingSet(Set<Meeting> meetingSet) {
        this.meetingSet = meetingSet;
    }

    public Set<ClosedDate> getClosedDateSet() {
        return closedDateSet;
    }

    public void setClosedDateSet(Set<ClosedDate> closedDateSet) {
        this.closedDateSet = closedDateSet;
    }

    public Set<Meeting> getMeetingSet() {
        return meetingSet;
    }
}
