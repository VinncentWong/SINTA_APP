package com.sinta.sinta_app.dto.agent;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public record RegistrationDto(
    @NotNull
    @NotBlank 
    @Email
    String email,

    @NotNull
    @NotBlank
    @Size(min = 8, max = 30, message = "panjang username < 8 atau panjang username > 30")
    String username,

    @NotNull
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[-+_!@#$%^&*., ?]).+$", message = "password minimal satu huruf kapital, satu angka, dan 1 huruf spesial")
    String password,

    @NotNull
    @NotBlank
    String namaBadanUsaha
){}
