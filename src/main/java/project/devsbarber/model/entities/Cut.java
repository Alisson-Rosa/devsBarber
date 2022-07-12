package project.devsbarber.model.entities;

import org.springframework.format.annotation.DateTimeFormat;
import project.devsbarber.model.enums.TypeCut;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "CUT")
public class Cut {

    @Id
    @Column(name="ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="TYPECUT", nullable = false)
    private TypeCut typeCut;

    @Column(name="VALUE", nullable = false)
    private Double value;

    @Column(name="SIZE", nullable = false)
    private Integer size;

    @DateTimeFormat(pattern = "HH:mm")
    @Column(name="TIME", nullable = false)
    private LocalTime time;

    @Column(name="ENABLE", nullable = false)
    private boolean enable;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TypeCut getTypeCut() {
        return typeCut;
    }

    public void setTypeCut(TypeCut typeCut) {
        this.typeCut = typeCut;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
