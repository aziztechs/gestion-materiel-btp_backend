package aziztechs.sn.gestionmaterielbtp_backend.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "devis")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Devis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal montant;

    @NotBlank
    @Size(max = 100)
    private String fournisseur;

    @NotBlank
    @Size(max = 50)
    private String reference;

    @Size(max = 500)
    private String description;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "besoin_id", nullable = false)
    private Besoin besoin;

    @OneToMany(mappedBy = "devis", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ValidationDevis> validations = new ArrayList<>();

    @Column(name = "date_creation")
    private LocalDate dateCreation = LocalDate.now();

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EDevisStatut statut = EDevisStatut.EN_ATTENTE;

}
