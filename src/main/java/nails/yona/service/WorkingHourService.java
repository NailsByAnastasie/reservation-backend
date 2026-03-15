package nails.yona.service;

import lombok.RequiredArgsConstructor;
import nails.yona.dto.request.WorkingHourRequest;
import nails.yona.dto.response.WorkingHourResponse;
import nails.yona.enums.WorkingDay;
import nails.yona.mapper.WorkingHourMapper;
import nails.yona.model.WorkingHour;
import nails.yona.repository.WorkingHourRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkingHourService {

    private final WorkingHourRepository workingHourRepository;
    private final WorkingHourMapper workingHourMapper;

    @Transactional(readOnly = true)
    public List<WorkingHourResponse> getAllWorkingHours() {
        List<WorkingHour> existingHours = workingHourRepository.findAll();

        return Arrays.stream(WorkingDay.values())
                .map(day -> {
                    Optional<WorkingHour> foundHour = existingHours.stream()
                            .filter(wh -> wh.getDay() == day)
                            .findFirst();
                    return foundHour
                            .map(workingHourMapper::toResponse)
                            .orElseGet(() -> new WorkingHourResponse(
                                    null,
                                    day,
                                    LocalTime.of(9, 0),
                                    LocalTime.of(18, 0),
                                    day == WorkingDay.DIMANCHE
                            ));
                })
                .toList();
    }

    @Transactional
    public List<WorkingHourResponse> upsertWorkingHours(List<WorkingHourRequest> requests) {

        List<WorkingHour> hoursToSave = new ArrayList<>();

        for (WorkingHourRequest request : requests) {
            if (!request.isClosed() && (request.startTime() == null || request.endTime() == null)) {
                throw new IllegalArgumentException("Si le salon est ouvert le " + request.day() + ", les heures de début et de fin sont obligatoires.");
            }

            if (request.startTime() != null && request.endTime() != null && request.startTime().isAfter(request.endTime())) {
                throw new IllegalArgumentException("L'heure de début doit être avant l'heure de fin pour le " + request.day() + ".");
            }

            Optional<WorkingHour> existing = workingHourRepository.findByDay(request.day());
            WorkingHour workingHour;

            if (existing.isPresent()) {
                workingHour = existing.get();
                workingHourMapper.updateEntity(request, workingHour);
            } else {
                workingHour = workingHourMapper.toEntity(request);
            }

            workingHour.setDay(request.day());
            hoursToSave.add(workingHour);
        }

        List<WorkingHour> saved = workingHourRepository.saveAll(hoursToSave);
        return workingHourMapper.toResponseList(saved);
    }
}