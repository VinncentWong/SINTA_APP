package com.sinta.sinta_app.dto.agent;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record UpdateAgentDto(
    @NotNull(message = "email tidak boleh null")
    @NotBlank(message = "email tidak boleh kosong")
    @Email(message = "format email tidak valid")
    String email,

    @NotNull(message = "username tidak boleh null")
    @NotBlank(message = "username tidak boleh kosong")
    @Size(min = 8, max = 30, message = "panjang username < 8 atau panjang username > 30")
    String username
){}
