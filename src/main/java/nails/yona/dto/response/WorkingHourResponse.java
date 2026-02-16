package nails.yona.dto.response;

import nails.yona.enums.WorkingDay;

import java.time.LocalTime;
import java.util.UUID;

public record WorkingHourResponse(
        UUID id,
        WorkingDay day,
        LocalTime startTime,
        LocalTime endTime,
        Boolean isClosed
) {}