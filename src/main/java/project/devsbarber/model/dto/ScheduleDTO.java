package project.devsbarber.model.dto;

import org.springframework.format.annotation.DateTimeFormat;
import project.devsbarber.model.entities.TimeKey;

import java.time.LocalDate;

public class ScheduleDTO {

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate date;
    private Long clientId;
    private Long barberId;
    private Long cutId;
    private Double value;
    private Long key;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getBarberId() {
        return barberId;
    }

    public void setBarberId(Long barberId) {
        this.barberId = barberId;
    }

    public Long getCutId() {
        return cutId;
    }

    public void setCutId(Long cutId) {
        this.cutId = cutId;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }
}
