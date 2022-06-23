package project.devsbarber.model.entities;

import javax.persistence.*;
import java.time.DayOfWeek;

@Entity
@Table(name = "BARBER")
public class Barber {

    @Id
    @Column(name="ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="NAME", nullable = false)
    private String name;

    @Column(name = "DAYOFF", nullable = false)
    private DayOfWeek dayOff;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DayOfWeek getDayOff() {
        return dayOff;
    }

    public void setDayOff(DayOfWeek dayoff) {
        this.dayOff = dayoff;
    } //TODO ajustar para dias da semana
}
