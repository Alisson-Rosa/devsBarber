package project.devsbarber.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalTime;

@Entity
@Table(name = "TIME_KEY")
public class TimeKey {

    @Id
    @Column(name = "KEY", nullable = false)
    private Integer key;

    @Column(name = "TIME", nullable = false)
    private LocalTime time;

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
