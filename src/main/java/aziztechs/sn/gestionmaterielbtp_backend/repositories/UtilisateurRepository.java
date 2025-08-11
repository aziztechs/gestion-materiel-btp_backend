package aziztechs.sn.gestionmaterielbtp_backend.repositories;

import aziztechs.sn.gestionmaterielbtp_backend.models.ERole;
import aziztechs.sn.gestionmaterielbtp_backend.models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByEmail(String email);
    Boolean existsByEmail(String email);
    List<Utilisateur> findByRole(ERole role);
}
