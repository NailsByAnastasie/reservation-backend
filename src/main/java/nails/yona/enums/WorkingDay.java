package nails.yona.enums;

import java.time.DayOfWeek;

public enum WorkingDay {
    LUNDI,
    MARDI,
    MERCREDI,
    JEUDI,
    VENDREDI,
    SAMEDI,
    DIMANCHE;

    public static WorkingDay fromJavaDayOfWeek(DayOfWeek dayOfWeek) {
        return switch (dayOfWeek) {
            case MONDAY -> LUNDI;
            case TUESDAY -> MARDI;
            case WEDNESDAY -> MERCREDI;
            case THURSDAY -> JEUDI;
            case FRIDAY -> VENDREDI;
            case SATURDAY -> SAMEDI;
            case SUNDAY -> DIMANCHE;
        };
    }
}
