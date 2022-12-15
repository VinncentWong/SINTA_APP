package com.sinta.sinta_app.dto.trip.deskripsi;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record DeskripsiDto(
    @NotNull
    @NotBlank
    String judul,
    @NotNull
    DetailDestinasiDto detailDestinasi
) {}
