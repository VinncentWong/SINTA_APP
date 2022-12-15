package com.sinta.sinta_app.dto.agent;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record LoginDto(
    @NotNull
    @NotBlank
    String email,

    @NotNull
    @NotBlank
    String password
){}
