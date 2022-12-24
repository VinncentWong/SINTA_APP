package com.sinta.sinta_app.dto.agent;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public record RegistrationDto(
    @NotNull(message = "email tidak boleh null")
    @NotBlank(message = "email tidak boleh kosong")
    @Email(message = "format email tidak valid")
    String email,

    @NotNull(message = "username tidak boleh null")
    @NotBlank(message = "username tidak boleh kosong")
    @Size(min = 8, max = 30, message = "panjang username < 8 atau panjang username > 30")
    String username,

    @NotNull(message = "password tidak boleh null")
    @NotBlank(message = "password tidak boleh kosong")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[-+_!@#$%^&*., ?]).+$", message = "password minimal satu huruf kapital, satu angka, dan 1 huruf spesial")
    String password,

    @NotNull(message = "nama badan usaha tidak boleh null")
    @NotBlank(message = "nama badan usaha tidak boleh kosong")
    @Size(min = 7, message = "panjang nama badan usaha harus >= 7")
    String namaBadanUsaha
){}
