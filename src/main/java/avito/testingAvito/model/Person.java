package avito.testingAvito.model;


import javax.persistence.*;
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

    @Column(name = "closedDates")
    @OneToMany(orphanRemoval = true, mappedBy = "person", fetch = FetchType.EAGER)
    private Set<ClosedDate> closedDateSet = new HashSet<>();

    @Column(name = "meetings")
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "person_meeting",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "meeting_id"))
    private Set<Meeting> meetingSet = new HashSet<>();

    public Person() {
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void addOneClosedSet(ClosedDate closedDate) {
        this.closedDateSet.add(closedDate);
    }

    public void addOneMeeting(Meeting meeting) {
        this.meetingSet.add(meeting);
    }

    public void deleteOneMeeting(Meeting meeting) {
        this.meetingSet.remove(meeting);
    }

    public void deleteOneClosedDate(ClosedDate closedDate) {
        this.closedDateSet.remove(closedDate);
    }
}
