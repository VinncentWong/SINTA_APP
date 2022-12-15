package com.sinta.sinta_app.dto.trip.harga;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record HargaDto(
    @NotNull
    @NotBlank
    String jumlahPesertaTour,
    @NotNull
    Integer harga
) {}
