package project.devsbarber.model.entities;

import javax.persistence.*;

@Entity
@Table(name = "TIMETABLE_BARBERS", uniqueConstraints = @UniqueConstraint(columnNames = {"BARBER_ID", "TIME_KEY_ID"}))
public class TimetableBarbers {

    @Id
    @Column(name="ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="BARBER_ID", nullable = false)
    private Barber barber;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="TIME_KEY_ID", nullable = false)
    private TimeKey timeKey;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Barber getBarber() {
        return barber;
    }

    public void setBarber(Barber barber) {
        this.barber = barber;
    }

    public TimeKey getTimeKey() {
        return timeKey;
    }

    public void setTimeKey(TimeKey timeKey) {
        this.timeKey = timeKey;
    }
}
