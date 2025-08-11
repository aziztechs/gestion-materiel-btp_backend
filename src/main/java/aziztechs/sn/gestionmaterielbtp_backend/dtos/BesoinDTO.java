package aziztechs.sn.gestionmaterielbtp_backend.dtos;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BesoinDTO {
    private Long idBesoin;
    private LocalDate date;
    private String description;
    private String unite;
    private Integer quantite;
    private Long idUser;
}

