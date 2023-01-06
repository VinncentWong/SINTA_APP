package com.sinta.sinta_app.entity.agent;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sinta.sinta_app.entity.Extractable;
import com.sinta.sinta_app.entity.Role;
import com.sinta.sinta_app.entity.StatusVerified;
import com.sinta.sinta_app.entity.portofolio.Portofolio;
import com.sinta.sinta_app.entity.trip.Trip;

import lombok.Data;

@Entity
@Data
@DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Agent implements Extractable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String password;

    private String namaBadanUsaha;

    private String namaPIC;

    private String kontakPIC;

    private String alamatKantor;

    private String kontakAdminKantor;

    private String whatsappKantor;

    private String linkAkunInstagram;

    private byte[] suratIzinUsaha;

    private byte[] fotoProfil;

    private String linkGmaps;

    private String linkAkunTelegram;

    private String linkAkunFacebook;

    private String linkAkunLine;

    private String linkAkunTwitter;

    private String bio;

    private StatusVerified statusVerified = StatusVerified.BELUM_TERVERIFIKASI;

    private String aboutMe;

    private boolean isPaymentExpired;

    private LocalDate premiumDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "agent")
    private List<Portofolio> portofolio;

    @CreationTimestamp
    private LocalDate createdAt;

    @UpdateTimestamp
    private LocalDate updatedAt;

    private LocalDate deletedAt;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "agent")
    private List<Trip> trip;

    private final Role role = Role.AGENT;
}
