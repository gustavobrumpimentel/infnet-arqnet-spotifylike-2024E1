package br.edu.infnet.spotifylike_user.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Subscription {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private boolean active;

    @Column
    private LocalDateTime date;

    @OneToOne(cascade = CascadeType.ALL)
    private Plan plan;

    public Subscription(boolean active, LocalDateTime date, Plan plan) {
        this.active = active;
        this.date = date;
        this.plan = plan;
    }
}
