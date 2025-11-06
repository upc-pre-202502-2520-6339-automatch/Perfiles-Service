package automach.profiles.infrastructure.controller;

import automach.profiles.domain.model.valueobjects.BusinessType;

public record CreateSellerRequest(
        String firstName,
        String lastName,
        String email,
        String ruc,
        BusinessType businessType,
        String businessName,
        String address,
        String phoneNumber
) {}