package automach.profiles.application.internal.queryservices;

import automach.profiles.domain.model.aggregates.User;
import automach.profiles.domain.model.queries.GetAllProfilesQuery;
import automach.profiles.domain.model.queries.GetProfileByIamUserIdQuery;
import automach.profiles.domain.model.queries.GetProfileByIdQuery;
import automach.profiles.domain.model.queries.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileQueryServiceImpl {

    private final UserRepository userRepository;

    public ProfileQueryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Maneja la consulta para obtener un perfil por su UserId.
     */
    public Optional<User> handle(GetProfileByIdQuery query) {
        return userRepository.findById(query.userId());
    }

    /**
     * Maneja la consulta para obtener todos los perfiles.
     */
    public List<User> handle(GetAllProfilesQuery query) {
        return userRepository.findAll();
    }


    public Optional<User> handle(GetProfileByIamUserIdQuery query) {
        return userRepository.findByIamUserId(query.iamUserId());
    }
    /*
     * OJO: GetProfileByIamUserIdQuery está definido en dominio
     * para futuro cuando enlaces Profiles con IAM.
     * Como el agregado User todavía no tiene iamUserId,
     * esta operación NO se implementa por ahora.
     *
     * Cuando agregues ese campo al dominio + repositorio,
     * aquí puedes añadir:
     *
     *  public Optional<User> handle(GetProfileByIamUserIdQuery query) { ... }
     */
}



