package project.devsbarber.model.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "SCHEDULE", uniqueConstraints = @UniqueConstraint(columnNames ={"BARBER_ID", "DATE", "TIMETABLE_BARBERS_ID", "TIME" }))
public class Schedule {

    @Id
    @Column(name="ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "DATE", nullable = false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate date;

    @DateTimeFormat(pattern = "HH:mm")
    @Column(name="TIME", nullable = false)
    private LocalTime time;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="CLIENT_ID", nullable = false)
    private User client;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="BARBER_ID", nullable = false)
    private Barber barber;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="CUT_ID", nullable = false)
    private Cut cut;

    @ManyToOne
    @JoinColumn(name="TIMETABLE_BARBERS_ID", nullable = false)
    private TimetableBarbers timetableBarber;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public Barber getBarber() {
        return barber;
    }

    public void setBarber(Barber barber) {
        this.barber = barber;
    }

    public Cut getCut() {
        return cut;
    }

    public void setCut(Cut cut) {
        this.cut = cut;
    }

    public TimetableBarbers getTimetableBarber() {
        return timetableBarber;
    }

    public void setTimetableBarber(TimetableBarbers timetableBarber) {
        this.timetableBarber = timetableBarber;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
