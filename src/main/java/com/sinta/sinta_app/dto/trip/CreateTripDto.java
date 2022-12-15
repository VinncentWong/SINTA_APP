package com.sinta.sinta_app.dto.trip;

import javax.validation.constraints.NotNull;

import com.sinta.sinta_app.dto.trip.deskripsi.DeskripsiDto;
import com.sinta.sinta_app.dto.trip.fasilitas.FasilitasTermasukDto;
import com.sinta.sinta_app.dto.trip.fasilitas.FasilitasTidakTermasukDto;
import com.sinta.sinta_app.dto.trip.harga.HargaDto;

public record CreateTripDto(
    @NotNull
    DeskripsiDto deskripsi,
    @NotNull
    FasilitasTermasukDto fasilitasTermasuk,
    @NotNull
    FasilitasTidakTermasukDto fasilitasTidakTermasuk,
    @NotNull
    HargaDto harga
) {}
