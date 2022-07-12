package project.devsbarber.model.dto;

import org.springframework.format.annotation.DateTimeFormat;
import project.devsbarber.model.enums.TypeCut;

import java.time.LocalDate;
import java.time.LocalTime;

public class ScheduleDTO {

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate date;
    private Long clientId;
    private Long barberId;
    private String barberName;
    private Long cutId;
    private Double value;
    private String valueString;
    private Long key;
    private LocalTime timeKey;
    private LocalTime timeCut;
    private TypeCut tipeCut;
    private boolean save = false;
    private boolean confirm = true;

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

    public LocalTime getTimeCut() {
        return timeCut;
    }

    public void setTimeCut(LocalTime timeCut) {
        this.timeCut = timeCut;
    }

    public TypeCut getTipeCut() {
        return tipeCut;
    }

    public void setTipeCut(TypeCut tipeCut) {
        this.tipeCut = tipeCut;
    }

    public LocalTime getTimeKey() {
        return timeKey;
    }

    public void setTimeKey(LocalTime timeKey) {
        this.timeKey = timeKey;
    }

    public String getBarberName() {
        return barberName;
    }

    public void setBarberName(String barberName) {
        this.barberName = barberName;
    }

    public boolean isSave() {
        return save;
    }

    public void setSave(boolean save) {
        this.save = save;
    }

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }


    public String getValueString() {
        return valueString;
    }

    public void setValueString(String valueString) {
        this.valueString = valueString;
    }
}
