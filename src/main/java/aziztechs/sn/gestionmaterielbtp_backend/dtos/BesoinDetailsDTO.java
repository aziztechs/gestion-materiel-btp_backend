package aziztechs.sn.gestionmaterielbtp_backend.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class BesoinDetailsDTO extends BesoinDTO {
    private String nomUtilisateur;
    private List<DevisDTO> devis;
}
