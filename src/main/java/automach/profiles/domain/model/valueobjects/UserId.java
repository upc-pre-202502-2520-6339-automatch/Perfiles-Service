package automach.profiles.domain.model.valueobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Value Object que representa el identificador del agregado User.
 * Envuelve un Long porque toda la plataforma usa IDs Long.
 */
@Getter
@EqualsAndHashCode
public class UserId {

    private final Long value;

    public UserId(Long value) {
        if (value == null) {
            throw new IllegalArgumentException("UserId value must not be null");
        }
        if (value <= 0) {
            throw new IllegalArgumentException("UserId value must be positive");
        }
        this.value = value;
    }

    public static UserId of(Long value) {
        return new UserId(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
