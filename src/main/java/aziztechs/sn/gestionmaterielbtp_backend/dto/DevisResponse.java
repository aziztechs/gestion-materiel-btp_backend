package aziztechs.sn.gestionmaterielbtp_backend.dto;

import aziztechs.sn.gestionmaterielbtp_backend.models.EDevisStatut;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DevisResponse {
    
    private Long id;
    private BigDecimal montant;
    private String fournisseur;
    private String reference;
    private String description;
    private LocalDate dateCreation;
    private EDevisStatut statut;
    
    // Informations du besoin associé
    private Long besoinId;
    private String besoinDescription;
    private String besoinUnite;
    private Integer besoinQuantite;
    
    // Informations de validation
    private boolean isValide;
    private int nombreValidations;
    private String dernierValidateur;
    private LocalDate dateValidation;
}
