package automach.profiles.domain.model.queries;

/**
 * Query pensada para cuando vincules Profiles con IAM.
 * De momento solo define el contrato.
 */
public record GetProfileByIamUserIdQuery(
        Long iamUserId
) { }
