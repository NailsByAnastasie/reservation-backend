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

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkingHourService {

    private final WorkingHourRepository workingHourRepository;
    private final WorkingHourMapper workingHourMapper;

    @Transactional(readOnly = true)
    public List<WorkingHourResponse> getAllWorkingHours() {
        return workingHourMapper.toResponseList(workingHourRepository.findAll());
    }

    @Transactional
    public WorkingHourResponse upsertWorkingHour(WorkingDay day, WorkingHourRequest request) {

        if (!request.isClosed() && (request.startTime() == null || request.endTime() == null)) {
            throw new IllegalArgumentException("Si le salon est ouvert, les heures de début et de fin sont obligatoires.");
        }

        if (request.startTime() != null && request.endTime() != null && request.startTime().isAfter(request.endTime())) {
            throw new IllegalArgumentException("L'heure de début doit être avant l'heure de fin.");
        }

        Optional<WorkingHour> existing = workingHourRepository.findByDay(day);
        WorkingHour workingHour;

        if (existing.isPresent()) {
            workingHour = existing.get();
            workingHourMapper.updateEntity(request, workingHour);
        } else {
            workingHour = workingHourMapper.toEntity(request);
        }

        workingHour.setDay(day);

        WorkingHour saved = workingHourRepository.save(workingHour);
        return workingHourMapper.toResponse(saved);
    }
}