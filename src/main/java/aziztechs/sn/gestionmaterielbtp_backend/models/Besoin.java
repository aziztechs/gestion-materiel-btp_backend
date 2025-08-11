package aziztechs.sn.gestionmaterielbtp_backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "besoins")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Besoin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 500)
    private String description;

    @NotBlank
    @Size(max = 20)
    private String unite;

    @NotNull
    @Min(1)
    private Integer quantite;

    @NotNull
    @Column(name = "date_demande")
    private LocalDate dateDemande;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;

    @OneToMany(mappedBy = "besoin", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Devis> devis = new ArrayList<>();

    @Column(name = "is_urgence")
    private Boolean isUrgence = false;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EBesoinStatut statut = EBesoinStatut.EN_ATTENTE;

}