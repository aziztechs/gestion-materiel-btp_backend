package aziztechs.sn.gestionmaterielbtp_backend.repositories;

import aziztechs.sn.gestionmaterielbtp_backend.models.Besoin;
import aziztechs.sn.gestionmaterielbtp_backend.models.EBesoinStatut;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BesoinRepository extends JpaRepository<Besoin, Long> {
    List<Besoin> findByUtilisateurId(Long userId);
    List<Besoin> findByDateDemandeBetween(LocalDate start, LocalDate end);
    List<Besoin> findByIsUrgenceTrue();
    List<Besoin> findByStatut(EBesoinStatut statut);
}