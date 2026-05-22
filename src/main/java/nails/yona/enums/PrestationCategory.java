package nails.yona.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PrestationCategory {
    HAND("Beauté des mains"),
    FOOT("Beauté des pieds"),
    EYE("Beauté des cils");

    private final String label;
}
