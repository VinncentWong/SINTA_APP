package com.sinta.sinta_app.dto.agent;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record RegistrationDto(
    @NotNull
    @NotBlank 
    String email,

    @NotNull
    @NotBlank
    String username,

    @NotNull
    @NotBlank
    String password,

    @NotNull
    @NotBlank
    String namaBadanUsaha
){}
