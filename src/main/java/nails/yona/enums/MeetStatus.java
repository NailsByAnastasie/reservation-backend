package nails.yona.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MeetStatus {
    PENDING("Attente Validation"),
    CANCELED("Annulé"),
    CONFIRMED("Validé");

    private final String label;
}