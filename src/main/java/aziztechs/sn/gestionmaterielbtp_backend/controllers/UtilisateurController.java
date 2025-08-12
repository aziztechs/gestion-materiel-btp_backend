package aziztechs.sn.gestionmaterielbtp_backend.controllers;

import aziztechs.sn.gestionmaterielbtp_backend.models.Utilisateur;
import aziztechs.sn.gestionmaterielbtp_backend.repositories.UtilisateurRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/utilisateurs")
@RequiredArgsConstructor
@Tag(name = "Utilisateurs", description = "API de gestion des utilisateurs")
public class UtilisateurController {

    private final UtilisateurRepository utilisateurRepository;

    @GetMapping
    @Operation(summary = "Lister tous les utilisateurs", description = "Récupère la liste paginée de tous les utilisateurs")
    public ResponseEntity<Page<Utilisateur>> getAllUtilisateurs(Pageable pageable) {
        Page<Utilisateur> utilisateurs = utilisateurRepository.findAll(pageable);
        return ResponseEntity.ok(utilisateurs);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un utilisateur par ID", description = "Récupère les détails d'un utilisateur spécifique")
    public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable Long id) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(id);
        return utilisateur.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/count")
    @Operation(summary = "Compter les utilisateurs", description = "Retourne le nombre total d'utilisateurs")
    public ResponseEntity<Long> countUtilisateurs() {
        long count = utilisateurRepository.count();
        return ResponseEntity.ok(count);
    }
}

