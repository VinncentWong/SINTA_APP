package com.sinta.sinta_app.dto.trip.fasilitas;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record FasilitasTidakTermasukDto(
    @NotNull
    @NotBlank
    String poinFasilitas
) {}
