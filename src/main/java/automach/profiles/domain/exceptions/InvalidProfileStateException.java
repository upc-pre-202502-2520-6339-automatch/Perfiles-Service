package automach.profiles.domain.exceptions;

/**
 * Se lanza cuando se intenta aplicar una operación
 * que deja al perfil en un estado inválido.
 */
public class InvalidProfileStateException extends RuntimeException {

    public InvalidProfileStateException(String message) {
        super(message);
    }
}
