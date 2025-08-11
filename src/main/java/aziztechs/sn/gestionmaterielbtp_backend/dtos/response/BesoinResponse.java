package aziztechs.sn.gestionmaterielbtp_backend.dtos.response;

import aziztechs.sn.gestionmaterielbtp_backend.models.EBesoinStatut;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BesoinResponse {
    private Long id;
    private String description;
    private String unite;
    private Integer quantite;
    private Boolean isUrgence;
    private LocalDate dateDemande;
    private EBesoinStatut statut;
    private String utilisateurNom;
    private String utilisateurEmail;
}
