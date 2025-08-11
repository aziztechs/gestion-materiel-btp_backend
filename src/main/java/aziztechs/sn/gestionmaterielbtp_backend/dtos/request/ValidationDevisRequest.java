package aziztechs.sn.gestionmaterielbtp_backend.dtos.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ValidationDevisRequest {
    @NotNull
    private Boolean valide;

    @Size(max = 500)
    private String commentaire;
}