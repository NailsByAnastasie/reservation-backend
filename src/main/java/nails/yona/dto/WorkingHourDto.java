package nails.yona.dto;

import jakarta.validation.constraints.NotNull;
import nails.yona.enums.WorkingDay;

import java.time.LocalTime;

public record WorkingHourDto(
        @NotNull(message = "Le jour de la semaine est obligatoire.")
        WorkingDay day,

        LocalTime startTime,
        LocalTime endTime,

        @NotNull(message = "Veuillez indiquer si le salon est fermé ou ouvert ce jour-là.")
        Boolean isClosed
) {}
