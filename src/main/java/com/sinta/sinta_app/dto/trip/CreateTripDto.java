package com.sinta.sinta_app.dto.trip;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.sinta.sinta_app.dto.trip.deskripsi.DeskripsiDto;
import com.sinta.sinta_app.dto.trip.fasilitas.FasilitasTermasukDto;
import com.sinta.sinta_app.dto.trip.fasilitas.FasilitasTidakTermasukDto;
import com.sinta.sinta_app.dto.trip.harga.HargaDto;

public record CreateTripDto(
    @NotNull
    @NotBlank
    String informasiPenting,
    @NotNull
    @NotBlank
    String linkRundown,
    @NotNull
    @NotBlank
    String catatanHarga,
    @NotNull
    DeskripsiDto deskripsi,
    @NotNull
    List<FasilitasTermasukDto> fasilitasTermasuk,
    @NotNull
    List<FasilitasTidakTermasukDto> fasilitasTidakTermasuk,
    @NotNull
    List<HargaDto> harga
) {}
