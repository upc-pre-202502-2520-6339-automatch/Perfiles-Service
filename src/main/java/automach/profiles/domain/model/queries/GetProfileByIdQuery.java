package automach.profiles.domain.model.queries;

import automach.profiles.domain.model.valueobjects.UserId;

/**
 * Query para obtener un perfil por su UserId interno.
 */
public record GetProfileByIdQuery(
        UserId userId
) { }
