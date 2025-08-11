package aziztechs.sn.gestionmaterielbtp_backend.dtos.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BesoinRequest {
    @NotBlank
    @Size(max = 500)
    private String description;

    @NotBlank
    @Size(max = 20)
    private String unite;

    @NotNull
    @Min(1)
    private Integer quantite;

    private Boolean isUrgence = false;
}

