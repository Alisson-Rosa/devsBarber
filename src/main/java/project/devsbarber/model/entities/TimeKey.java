package project.devsbarber.model.entities;

import org.springframework.format.annotation.DateTimeFormat;

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
    private long key;

    @Column(name = "TIME", nullable = false)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime time;

    public long getKey() {
        return key;
    }

    public void setKey(long key) {
        this.key = key;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
