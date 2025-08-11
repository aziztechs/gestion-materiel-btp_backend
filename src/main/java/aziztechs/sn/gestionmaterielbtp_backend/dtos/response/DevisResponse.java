package aziztechs.sn.gestionmaterielbtp_backend.dtos.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class DevisResponse {
    private Long id;
    private BigDecimal montant;
    private String fournisseur;
    private String reference;
    private LocalDate dateCreation;
    private String statut;
    private List<ValidationDevisResponse> validations;
}
