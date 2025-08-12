package aziztechs.sn.gestionmaterielbtp_backend.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DevisRequest {
    
    @NotNull(message = "Le montant est obligatoire")
    @DecimalMin(value = "0.0", message = "Le montant doit être positif")
    private BigDecimal montant;
    
    @NotBlank(message = "Le fournisseur est obligatoire")
    @Size(max = 100, message = "Le nom du fournisseur ne peut pas dépasser 100 caractères")
    private String fournisseur;
    
    @NotBlank(message = "La référence est obligatoire")
    @Size(max = 50, message = "La référence ne peut pas dépasser 50 caractères")
    private String reference;
    
    @NotNull(message = "L'ID du besoin est obligatoire")
    private Long besoinId;
    
    @Size(max = 500, message = "La description ne peut pas dépasser 500 caractères")
    private String description;
}
