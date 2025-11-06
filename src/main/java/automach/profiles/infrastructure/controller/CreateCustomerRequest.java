package automach.profiles.infrastructure.controller;

public record CreateCustomerRequest(
        String firstName,
        String lastName,
        String email
) {}

