package com.sinta.sinta_app.dto.agent;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record CompleteDataDto(
    @NotNull(message = "alamat kantor tidak boleh null")
    @NotBlank(message = "alamat kantor tidak boleh kosong")
    @Size(min = 7, message = "panjang alamat kantor < 7")
    String alamatKantor,

    @NotNull(message = "namaPIC tidak boleh null")
    @NotBlank(message = "namaPIC tidak boleh kosong")
    @Size(min = 7, message = "panjang namaPIC < 7")
    String namaPIC,

    @NotNull(message = "kontakWhatsappPIC tidak boleh null")
    @NotBlank(message = "kontakWhatsappPIC tidak boleh kosong")
    @Size(min = 7, message = "panjang kontakWhatsappPIC < 7")
    String kontakWhatsappPIC,

    @NotNull(message = "bio tidak boleh null")
    @NotBlank(message = "bio tidak boleh kosong")
    @Size(min = 7, message = "panjang bio < 7")
    String bio,

    @NotNull(message = "nomorTelepon tidak boleh null")
    @NotBlank(message = "nomorTelepon tidak boleh kosong")
    @Size(min = 11, message = "panjang nomorTelepon < 11")
    String nomorTelepon,

    @Size(min = 7, message = "panjang linkTelegram < 7")
    String linkTelegram,

    @NotNull(message = "kontakWhatsapp tidak boleh null")
    @NotBlank(message = "kontakWhatsapp tidak boleh kosong")
    @Size(min = 11, message = "panjang kontakWhatsapp < 11")
    String kontakWhatsapp,

    @Size(min = 7, message = "panjang linkLine < 7")
    String linkLine,

    @NotNull(message = "linkInstagram tidak boleh null")
    @NotBlank(message = "linkInstagram tidak boleh kosong")
    @Size(min = 7, message = "panjang linkInstagram < 7")
    String linkInstagram,

    @Size(min = 7, message = "panjang linkTwitter < 7")
    String linkTwitter,

    @Size(min = 7, message = "panjang linkFacebook < 7")
    String linkFacebook,

    @NotNull(message = "aboutMe tidak boleh null")
    @NotBlank(message = "aboutMe tidak boleh kosong")
    @Size(min = 7, message = "panjang aboutMe < 7")
    String aboutMe
) {}
