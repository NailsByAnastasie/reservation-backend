package nails.yona.service;

import lombok.RequiredArgsConstructor;
import nails.yona.enums.WorkingDay;
import nails.yona.model.WorkingHour;
import nails.yona.repository.BlockedSlotRepository;
import nails.yona.repository.MeetRepository;
import nails.yona.repository.WorkingHourRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AvailabilityService {

    private final WorkingHourRepository workingHourRepository;
    private final MeetRepository meetRepository;
    private final BlockedSlotRepository blockedSlotRepository;

    @Transactional(readOnly = true)
    public List<String> getAvailableSlotsForDate(LocalDate date) {

        WorkingDay workingDay = WorkingDay.fromJavaDayOfWeek(date.getDayOfWeek());
        WorkingHour workingHour = workingHourRepository.findByDay(workingDay).orElse(null);

        if (workingHour == null || workingHour.getIsClosed()) {
            return List.of();
        }

        LocalTime currentSlot = workingHour.getStartTime();
        LocalTime closeTime = workingHour.getEndTime();

        List<String> availableSlots = new ArrayList<>();
        int slotDurationMinutes = 30;

        while (!currentSlot.plusMinutes(slotDurationMinutes).isAfter(closeTime)) {
            LocalDateTime slotStart = date.atTime(currentSlot);
            LocalDateTime slotEnd = slotStart.plusMinutes(slotDurationMinutes);

            boolean isMeetOverlap = meetRepository.hasOverlap(slotStart, slotEnd);
            boolean isBlockedOverlap = blockedSlotRepository.hasOverlap(slotStart, slotEnd);

            if (!isMeetOverlap && !isBlockedOverlap) {
                if (date.isEqual(LocalDate.now()) && currentSlot.isBefore(LocalTime.now())) {
                } else {
                    availableSlots.add(currentSlot.toString());
                }
            }

            currentSlot = currentSlot.plusMinutes(slotDurationMinutes);
        }

        return availableSlots;
    }
}