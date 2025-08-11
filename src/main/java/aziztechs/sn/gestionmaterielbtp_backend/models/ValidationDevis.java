package aziztechs.sn.gestionmaterielbtp_backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "validation_devis")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationDevis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idValidation;

    @ManyToOne
    @JoinColumn(name = "id_devis", nullable = false)
    private Devis devis;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private Utilisateur utilisateur;

    @Column(nullable = false)
    private Boolean valide = false;

    @Column(nullable = false)
    private LocalDate dateValidation;
}