package project.devsbarber.model.dto;

import project.devsbarber.model.entities.Barber;
import project.devsbarber.model.entities.User;
import project.devsbarber.model.enums.TypeCut;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class CountTotalDTO {
    private List<LocalDate> totalDates;
    private List<LocalTime> totalTimes;
    private List<User> totalClients;
    private List<Barber> totalBarbers;
    private List<TypeCut> totalTypeCuts;
    private Double totalValue;

    public List<LocalDate> getTotalDates() {
        return totalDates;
    }

    public void setTotalDates(List<LocalDate> totalDates) {
        this.totalDates = totalDates;
    }

    public List<User> getTotalClients() {
        return totalClients;
    }

    public void setTotalClients(List<User> totalClients) {
        this.totalClients = totalClients;
    }

    public List<Barber> getTotalBarbers() {
        return totalBarbers;
    }

    public void setTotalBarbers(List<Barber> totalBarbers) {
        this.totalBarbers = totalBarbers;
    }

    public List<TypeCut> getTotalTypeCuts() {
        return totalTypeCuts;
    }

    public void setTotalTypeCuts(List<TypeCut> totalTypeCuts) {
        this.totalTypeCuts = totalTypeCuts;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    public List<LocalTime> getTotalTimes() {
        return totalTimes;
    }

    public void setTotalTimes(List<LocalTime> totalTimes) {
        this.totalTimes = totalTimes;
    }
}
