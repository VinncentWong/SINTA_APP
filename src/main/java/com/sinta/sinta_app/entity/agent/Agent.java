package com.sinta.sinta_app.entity.agent;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Data
public class Agent {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String namaBadanUsaha;

    private String namaPIC;

    private String kontakPIC;

    private String alamatKantor;

    private String kontakKantor;

    private String whatsappKantor;

    private String akunInstagram;

    @Lob
    private byte[] suratIzinUsaha;

    private String linkGmaps;

    private String akunTelegram;

    private String akunFacebook;

    private String akunLine;

    private String akunTwitter;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDate createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDate updatedAt;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDate deletedAt;
}
