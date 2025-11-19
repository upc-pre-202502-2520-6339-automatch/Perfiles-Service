package automach.profiles.domain.exceptions;

/**
 * Se lanza cuando no se encuentra un perfil (User)
 * para el identificador solicitado.
 */
public class ProfileNotFoundException extends RuntimeException {

    public ProfileNotFoundException(String message) {
        super(message);
    }
}
