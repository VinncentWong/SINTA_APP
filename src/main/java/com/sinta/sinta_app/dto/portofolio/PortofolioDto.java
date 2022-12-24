package com.sinta.sinta_app.dto.portofolio;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record PortofolioDto(
    @NotNull(message = "judul portofolio tidak boleh null")
    @NotBlank(message = "judul portofolio tidak boleh kosong")
    @Size(max = 120, message = "maksimal panjang judul portofolio adalah 120")
    String judulPortofolio,

    @NotNull(message = "deskripsi portofolio tidak boleh null")
    @NotBlank(message = "deskripsi portofolio tidak boleh kosong")
    @Size(max = 700, message = "maksimal panjang deskripsi portofolio adalah 700")
    String deskripsiPortofolio,

    @NotNull(message = "link paket wisata tidak boleh null")
    @NotBlank(message = "link paket wisata tidak boleh kosong")
    String linkPaketWisata
) {}
