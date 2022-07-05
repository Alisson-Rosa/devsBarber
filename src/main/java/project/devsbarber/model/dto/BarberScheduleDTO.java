package project.devsbarber.model.dto;

import project.devsbarber.model.entities.Barber;
import project.devsbarber.model.entities.TimeKey;

import java.util.List;

public class BarberScheduleDTO {

    private Barber barber;
    private List<TimeKey> timeKeyList;

    public Barber getBarber() {
        return barber;
    }

    public void setBarber(Barber barber) {
        this.barber = barber;
    }

    public List<TimeKey> getTimeKeyList() {
        return timeKeyList;
    }

    public void setTimeKeyList(List<TimeKey> timeKeyList) {
        this.timeKeyList = timeKeyList;
    }
}
