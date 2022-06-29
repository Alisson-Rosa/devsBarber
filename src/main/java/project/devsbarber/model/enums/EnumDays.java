package project.devsbarber.model.enums;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

public enum EnumDays {

    SEGUNDA(DayOfWeek.MONDAY),
    TERCA(DayOfWeek.TUESDAY),
    QUARTA(DayOfWeek.WEDNESDAY),
    QUINTA(DayOfWeek.THURSDAY),
    SEXTA(DayOfWeek.FRIDAY),
    SABADO(DayOfWeek.SATURDAY)
    ;

    private final DayOfWeek dayOfWeek;
    EnumDays(DayOfWeek dayOfWeek){this.dayOfWeek = dayOfWeek;}
    public DayOfWeek getDayOfWeek(){return dayOfWeek;}

    private static final Map<DayOfWeek, EnumDays> enumToDayOfWeek = new HashMap<>();

    static {
        for (EnumDays enumDay : EnumDays.values()) {
            enumToDayOfWeek.put(enumDay.getDayOfWeek(), enumDay);
        }
    }

    public static EnumDays getEnumToDayOfWeek(DayOfWeek dayOfWeek){
        return enumToDayOfWeek.get(dayOfWeek);
    }

    public static EnumDays[] enumDaysAll() {
      return values();
    }
}
