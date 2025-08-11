package aziztechs.sn.gestionmaterielbtp_backend.controllers;

import aziztechs.sn.gestionmaterielbtp_backend.dtos.request.BesoinRequest;
import aziztechs.sn.gestionmaterielbtp_backend.dtos.response.BesoinResponse;
import aziztechs.sn.gestionmaterielbtp_backend.services.BesoinService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/besoins")
@RequiredArgsConstructor
@Tag(name = "Besoins", description = "API de gestion des besoins")
public class BesoinController {

    private final BesoinService besoinService;

    @GetMapping
    @Operation(summary = "Lister tous les besoins", description = "Récupère la liste paginée de tous les besoins")
    public ResponseEntity<Page<BesoinResponse>> getAllBesoins(Pageable pageable) {
        Page<BesoinResponse> besoins = besoinService.getAllBesoins(pageable);
        return ResponseEntity.ok(besoins);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un besoin par ID", description = "Récupère les détails d'un besoin spécifique")
    public ResponseEntity<BesoinResponse> getBesoinById(@PathVariable Long id) {
        BesoinResponse besoin = besoinService.getBesoinById(id);
        return ResponseEntity.ok(besoin);
    }

    @PostMapping
    @Operation(summary = "Créer un nouveau besoin", description = "Crée un nouveau besoin dans le système")
    public ResponseEntity<BesoinResponse> createBesoin(@Valid @RequestBody BesoinRequest besoinRequest) {
        BesoinResponse createdBesoin = besoinService.createBesoin(besoinRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBesoin);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un besoin", description = "Met à jour les informations d'un besoin existant")
    public ResponseEntity<BesoinResponse> updateBesoin(@PathVariable Long id, @Valid @RequestBody BesoinRequest besoinRequest) {
        BesoinResponse updatedBesoin = besoinService.updateBesoin(id, besoinRequest);
        return ResponseEntity.ok(updatedBesoin);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un besoin", description = "Supprime un besoin du système")
    public ResponseEntity<Void> deleteBesoin(@PathVariable Long id) {
        besoinService.deleteBesoin(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/mes-besoins")
    @Operation(summary = "Mes besoins", description = "Récupère les besoins de l'utilisateur connecté")
    public ResponseEntity<Page<BesoinResponse>> getMesBesoins(Pageable pageable) {
        Page<BesoinResponse> besoins = besoinService.getBesoinsByCurrentUser(pageable);
        return ResponseEntity.ok(besoins);
    }

    @PatchMapping("/{id}/urgence")
    @Operation(summary = "Marquer comme urgent", description = "Marque un besoin comme urgent")
    public ResponseEntity<BesoinResponse> marquerUrgent(@PathVariable Long id) {
        BesoinResponse besoin = besoinService.marquerUrgent(id);
        return ResponseEntity.ok(besoin);
    }
}
