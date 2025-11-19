package automach.profiles.infrastructure.messaging;

import automach.profiles.application.internal.commandservices.ProfileCommandServiceImpl;
import automach.profiles.application.internal.queryservices.ProfileQueryServiceImpl;
import automach.profiles.domain.model.commands.RegisterCustomerProfileCommand;
import automach.profiles.domain.model.queries.GetProfileByIamUserIdQuery;
import automach.profiles.infrastructure.messaging.events.UserRegisteredEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "automatch.events.enabled", havingValue = "true")
public class UserEventsConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserEventsConsumer.class);

    private final ProfileCommandServiceImpl profileCommandService;
    private final ProfileQueryServiceImpl profileQueryService;   // 👈 NUEVO

    public UserEventsConsumer(ProfileCommandServiceImpl profileCommandService,
                              ProfileQueryServiceImpl profileQueryService) { // 👈 NUEVO PARAM
        this.profileCommandService = profileCommandService;
        this.profileQueryService = profileQueryService;          // 👈 ASIGNACIÓN
    }

    @KafkaListener(topics = "user-registered", groupId = "profiles-service")
    public void onUserRegistered(UserRegisteredEvent event) {
        LOGGER.info("📥 user-registered recibido: id={}, username={}", event.userId(), event.username());

        // si ya existe, no lo recreas
        var existing = profileQueryService.handle(new GetProfileByIamUserIdQuery(event.userId()));
        if (existing.isPresent()) {
            LOGGER.info("Perfil ya existe para iamUserId={}, no se crea de nuevo", event.userId());
            return;
        }

        var cmd = new RegisterCustomerProfileCommand(
                event.userId(),
                event.username(),
                "",
                event.username()
        );

        profileCommandService.handle(cmd);
        LOGGER.info("Perfil creado en Profiles para iamUserId={}", event.userId());
    }
}
