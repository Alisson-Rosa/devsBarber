package project.devsbarber.model.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "SCHEDULE", uniqueConstraints = @UniqueConstraint(columnNames ={"BARBER_ID", "CUT_ID", "DATE"}))
public class Schedule {

    @Id
    @Column(name="ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "DATE", nullable = false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate date;

    @Column(name="HOURS", nullable = false)
    private LocalTime hours;

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
    @JoinColumn(name="TIME_KEY", nullable = false)
    private TimeKey timeKey;

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

    public LocalTime getHours() {
        return hours;
    }

    public void setHours(LocalTime hours) {
        this.hours = hours;
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

    public TimeKey getTimeKey() {
        return timeKey;
    }

    public void setTimeKey(TimeKey timeKey) {
        this.timeKey = timeKey;
    }
}
