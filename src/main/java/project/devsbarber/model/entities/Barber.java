package project.devsbarber.model.entities;

import org.springframework.format.annotation.DateTimeFormat;
import project.devsbarber.model.enums.EnumDays;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Table(name = "BARBER", uniqueConstraints = @UniqueConstraint(columnNames = {"NAME"}))
public class Barber {

    @Id
    @Column(name="ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="NAME", nullable = false)
    private String name;

    @Column(name = "DAYOFF", nullable = false)
    private EnumDays dayOff;

    @Column(name = "WORK_START_TIME", nullable = false)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime workStartTime;

    @Column(name = "WORK_END_TIME", nullable = false)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime workEndTime;

    @Column(name = "LUNCH_TIME", nullable = false)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime lunchTime = LocalTime.of(12,0,0);

    @Column(name = "LUNCH_DURATION", nullable = false)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime lunchDuration = LocalTime.of(1,0,0);

    @Column(name="ENABLE", nullable = false)
    private boolean enable;

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

    public EnumDays getDayOff() {
        return dayOff;
    }

    public void setDayOff(EnumDays dayoff) {
        this.dayOff = dayoff;
    }

    public LocalTime getWorkStartTime() {
        return workStartTime;
    }

    public void setWorkStartTime(LocalTime workStartTime) {
        this.workStartTime = workStartTime;
    }

    public LocalTime getWorkEndTime() {
        return workEndTime;
    }

    public void setWorkEndTime(LocalTime workEndTime) {
        this.workEndTime = workEndTime;
    }

    public LocalTime getLunchTime() {
        return lunchTime;
    }

    public void setLunchTime(LocalTime lunchTime) {
        this.lunchTime = lunchTime;
    }

    public LocalTime getLunchDuration() {
        return lunchDuration;
    }

    public void setLunchDuration(LocalTime lunchDuration) {
        this.lunchDuration = lunchDuration;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
