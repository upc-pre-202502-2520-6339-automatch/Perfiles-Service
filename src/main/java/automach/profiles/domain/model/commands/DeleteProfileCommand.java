package automach.profiles.domain.model.commands;

import automach.profiles.domain.model.valueobjects.UserId;

/**
 * Comando para eliminar un perfil.
 */
public record DeleteProfileCommand(
        UserId userId
) { }
