package aziztechs.sn.gestionmaterielbtp_backend.repositories;

import aziztechs.sn.gestionmaterielbtp_backend.models.Besoin;
import aziztechs.sn.gestionmaterielbtp_backend.models.Devis;
import aziztechs.sn.gestionmaterielbtp_backend.models.EDevisStatut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface DevisRepository extends JpaRepository<Devis, Long> {
    List<Devis> findByBesoin(Besoin besoin);
    Page<Devis> findByBesoin(Besoin besoin, Pageable pageable);
    List<Devis> findByStatut(EDevisStatut statut);
    List<Devis> findByDateCreationBetween(LocalDate start, LocalDate end);
    List<Devis> findByMontantBetween(BigDecimal min, BigDecimal max);
    List<Devis> findByFournisseurContainingIgnoreCase(String fournisseur);
}
