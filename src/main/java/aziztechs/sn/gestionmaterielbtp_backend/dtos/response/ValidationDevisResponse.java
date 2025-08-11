package aziztechs.sn.gestionmaterielbtp_backend.dtos.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ValidationDevisResponse {
    private Long id;
    private String validateur;
    private String roleValidateur;
    private Boolean valide;
    private String commentaire;
    private LocalDate dateValidation;
}