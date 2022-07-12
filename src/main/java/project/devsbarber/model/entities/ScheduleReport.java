package project.devsbarber.model.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.time.LocalDate;
import java.time.LocalTime;

public class ScheduleReport {

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate date;
    @Column(name="TIME", nullable = false)
    private LocalTime time;
    private User client;
    private Barber barber;
    private Cut cut;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
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
}
