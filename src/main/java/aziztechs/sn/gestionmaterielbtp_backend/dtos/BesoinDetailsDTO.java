package aziztechs.sn.gestionmaterielbtp_backend.dtos;

import lombok.Data;

import java.util.List;

@Data
public class BesoinDetailsDTO extends BesoinDTO {
    private String nomUtilisateur;
    private List<DevisDTO> devis;
}
