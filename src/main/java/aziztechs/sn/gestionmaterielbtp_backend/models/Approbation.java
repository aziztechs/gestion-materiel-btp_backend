package aziztechs.sn.gestionmaterielbtp_backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "approbation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Approbation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idApprobation;

    @ManyToOne
    @JoinColumn(name = "id_budget", nullable = false)
    private Budget budget;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private Utilisateur utilisateur;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private Decision decision;

    @Column(nullable = false)
    private LocalDate dateApprobation;

    public enum Decision {
        APPROUVE, REJETE, EN_ATTENTE
    }
}