package br.edu.infnet.spotifylike_user.domain;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Card {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private boolean active;

    @Column
    private float limite;

    @Column
    private String number;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Transaction> cards;

    public Card(boolean active, float limit, String number) {
        this.active = active;
        this.limite = limit;
        this.number = number;
    }

    public Card() {
        this.active = true;
        this.limite = 0L;
        this.number = "";
    }

    public void createTransaction(String name, float value, String description) {

    }
}
