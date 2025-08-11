package aziztechs.sn.gestionmaterielbtp_backend.dtos.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DevisRequest {
    @NotNull
    @DecimalMin("0.0")
    private BigDecimal montant;

    @NotBlank
    @Size(max = 100)
    private String fournisseur;

    @NotBlank
    @Size(max = 50)
    private String reference;

    @NotNull
    private Long besoinId;
}