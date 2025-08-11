package aziztechs.sn.gestionmaterielbtp_backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "budget")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBudget;

    @Column(nullable = false)
    @Min(1) @Max(12)
    private Integer mois;

    @Column(nullable = false)
    @Min(2020)
    private Integer annee;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal montantTotal;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal chargesFixes;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private Utilisateur utilisateur;

    @OneToMany(mappedBy = "budget")
    private List<Approbation> approbations = new ArrayList<>();
}