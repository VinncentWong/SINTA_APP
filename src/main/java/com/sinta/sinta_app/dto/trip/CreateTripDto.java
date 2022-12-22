package com.sinta.sinta_app.dto.trip;

import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.sinta.sinta_app.dto.trip.deskripsi.DeskripsiDto;
import com.sinta.sinta_app.dto.trip.fasilitas.FasilitasTermasukDto;
import com.sinta.sinta_app.dto.trip.fasilitas.FasilitasTidakTermasukDto;
import com.sinta.sinta_app.dto.trip.harga.HargaDto;
import com.sinta.sinta_app.entity.trip.KategoriTrip;

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
    List<HargaDto> harga,
    @NotNull
    @NotBlank
    String kota,
    @NotNull
    @NotBlank
    String provinsi,
    @NotNull
    @NotBlank
    String lamaTrip,
    @NotNull
    Boolean needRequirement,
    @NotNull
    @Enumerated(EnumType.STRING)
    KategoriTrip kategoriTrip
) {}
