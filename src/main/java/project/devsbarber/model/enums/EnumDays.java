package project.devsbarber.model.enums;

import java.time.DayOfWeek;

public enum EnumDays {

    SEGUNDA(DayOfWeek.MONDAY),
    TERCA(DayOfWeek.TUESDAY),
    QUARTA(DayOfWeek.WEDNESDAY),
    QUINTA(DayOfWeek.THURSDAY),
    SEXTA(DayOfWeek.FRIDAY),
    SABADO(DayOfWeek.SATURDAY)
    ;

    private DayOfWeek dayOfWeek;
    EnumDays(DayOfWeek dayOfWeek){this.dayOfWeek = dayOfWeek;}
    public DayOfWeek getDayOfWeek(){return dayOfWeek;}

    public static EnumDays[] enumDaysAll() {
      return values();
    }
}
