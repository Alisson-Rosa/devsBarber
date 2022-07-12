package project.devsbarber.model.entities;

import org.springframework.format.annotation.DateTimeFormat;
import project.devsbarber.model.enums.TypeCut;

import java.time.LocalDate;
import java.time.LocalTime;

public class ScheduleReportFilter {

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate dateInit;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate dateEnd;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime timeInit;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime timeEnd;
    private Long barberId;
    private Long clientId;
    private String clientName;
    private TypeCut typeCut;

    public LocalTime getTimeInit() {
        return timeInit;
    }

    public void setTimeInit(LocalTime timeInit) {
        this.timeInit = timeInit;
    }

    public LocalTime getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(LocalTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Long getBarberId() {
        return barberId;
    }

    public void setBarberId(Long barberId) {
        this.barberId = barberId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public TypeCut getTypeCut() {
        return typeCut;
    }

    public void setTypeCut(TypeCut typeCut) {
        this.typeCut = typeCut;
    }

    public LocalDate getDateInit() {
        return dateInit;
    }

    public void setDateInit(LocalDate dateInit) {
        this.dateInit = dateInit;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
