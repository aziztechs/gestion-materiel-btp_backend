package aziztechs.sn.gestionmaterielbtp_backend.controllers;

import aziztechs.sn.gestionmaterielbtp_backend.dto.DevisRequest;
import aziztechs.sn.gestionmaterielbtp_backend.dto.DevisResponse;
import aziztechs.sn.gestionmaterielbtp_backend.models.EDevisStatut;
import aziztechs.sn.gestionmaterielbtp_backend.services.DevisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/devis")
@RequiredArgsConstructor
@Tag(name = "Devis", description = "API de gestion des devis")
public class DevisController {

    private final DevisService devisService;

    @PostMapping
    @Operation(
        summary = "Créer un nouveau devis",
        description = "Crée un nouveau devis pour un besoin spécifique"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Devis créé avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides"),
        @ApiResponse(responseCode = "404", description = "Besoin non trouvé")
    })
    public ResponseEntity<DevisResponse> createDevis(@Valid @RequestBody DevisRequest request) {
        DevisResponse response = devisService.createDevis(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(
        summary = "Lister tous les devis",
        description = "Récupère la liste paginée de tous les devis"
    )
    @ApiResponse(responseCode = "200", description = "Liste des devis récupérée avec succès")
    public ResponseEntity<Page<DevisResponse>> getAllDevis(Pageable pageable) {
        Page<DevisResponse> devis = devisService.getAllDevis(pageable);
        return ResponseEntity.ok(devis);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Récupérer un devis par ID",
        description = "Récupère les détails d'un devis spécifique"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Devis trouvé"),
        @ApiResponse(responseCode = "404", description = "Devis non trouvé")
    })
    public ResponseEntity<DevisResponse> getDevisById(
            @Parameter(description = "ID du devis") @PathVariable Long id) {
        DevisResponse response = devisService.getDevisById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Mettre à jour un devis",
        description = "Met à jour les informations d'un devis existant"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Devis mis à jour avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides"),
        @ApiResponse(responseCode = "404", description = "Devis non trouvé")
    })
    public ResponseEntity<DevisResponse> updateDevis(
            @Parameter(description = "ID du devis") @PathVariable Long id,
            @Valid @RequestBody DevisRequest request) {
        DevisResponse response = devisService.updateDevis(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Supprimer un devis",
        description = "Supprime un devis du système"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Devis supprimé avec succès"),
        @ApiResponse(responseCode = "404", description = "Devis non trouvé")
    })
    public ResponseEntity<Void> deleteDevis(
            @Parameter(description = "ID du devis") @PathVariable Long id) {
        devisService.deleteDevis(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/valider")
    @Operation(
        summary = "Valider un devis",
        description = "Change le statut d'un devis à VALIDE"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Devis validé avec succès"),
        @ApiResponse(responseCode = "404", description = "Devis non trouvé")
    })
    public ResponseEntity<DevisResponse> validerDevis(
            @Parameter(description = "ID du devis") @PathVariable Long id) {
        DevisResponse response = devisService.validerDevis(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/rejeter")
    @Operation(
        summary = "Rejeter un devis",
        description = "Change le statut d'un devis à REJETE"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Devis rejeté avec succès"),
        @ApiResponse(responseCode = "404", description = "Devis non trouvé")
    })
    public ResponseEntity<DevisResponse> rejeterDevis(
            @Parameter(description = "ID du devis") @PathVariable Long id) {
        DevisResponse response = devisService.rejeterDevis(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/besoin/{besoinId}")
    @Operation(
        summary = "Récupérer les devis par besoin",
        description = "Récupère tous les devis associés à un besoin spécifique"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Devis récupérés avec succès"),
        @ApiResponse(responseCode = "404", description = "Besoin non trouvé")
    })
    public ResponseEntity<Page<DevisResponse>> getDevisByBesoin(
            @Parameter(description = "ID du besoin") @PathVariable Long besoinId,
            Pageable pageable) {
        Page<DevisResponse> devis = devisService.getDevisByBesoin(besoinId, pageable);
        return ResponseEntity.ok(devis);
    }

    @GetMapping("/statut/{statut}")
    @Operation(
        summary = "Récupérer les devis par statut",
        description = "Récupère tous les devis ayant un statut spécifique"
    )
    @ApiResponse(responseCode = "200", description = "Devis récupérés avec succès")
    public ResponseEntity<List<DevisResponse>> getDevisByStatut(
            @Parameter(description = "Statut du devis") @PathVariable EDevisStatut statut) {
        List<DevisResponse> devis = devisService.getDevisByStatut(statut);
        return ResponseEntity.ok(devis);
    }

    @GetMapping("/fournisseur")
    @Operation(
        summary = "Rechercher des devis par fournisseur",
        description = "Recherche des devis par nom de fournisseur (recherche partielle)"
    )
    @ApiResponse(responseCode = "200", description = "Devis trouvés")
    public ResponseEntity<List<DevisResponse>> getDevisByFournisseur(
            @Parameter(description = "Nom du fournisseur") @RequestParam String fournisseur) {
        List<DevisResponse> devis = devisService.getDevisByFournisseur(fournisseur);
        return ResponseEntity.ok(devis);
    }

    @GetMapping("/montant")
    @Operation(
        summary = "Récupérer les devis par plage de montant",
        description = "Récupère les devis dont le montant est dans une plage spécifiée"
    )
    @ApiResponse(responseCode = "200", description = "Devis récupérés avec succès")
    public ResponseEntity<List<DevisResponse>> getDevisByMontantRange(
            @Parameter(description = "Montant minimum") @RequestParam BigDecimal min,
            @Parameter(description = "Montant maximum") @RequestParam BigDecimal max) {
        List<DevisResponse> devis = devisService.getDevisByMontantRange(min, max);
        return ResponseEntity.ok(devis);
    }

    @GetMapping("/dates")
    @Operation(
        summary = "Récupérer les devis par plage de dates",
        description = "Récupère les devis créés dans une plage de dates spécifiée"
    )
    @ApiResponse(responseCode = "200", description = "Devis récupérés avec succès")
    public ResponseEntity<List<DevisResponse>> getDevisByDateRange(
            @Parameter(description = "Date de début (format: yyyy-MM-dd)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "Date de fin (format: yyyy-MM-dd)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<DevisResponse> devis = devisService.getDevisByDateRange(startDate, endDate);
        return ResponseEntity.ok(devis);
    }

    @GetMapping("/statistiques")
    @Operation(
        summary = "Statistiques des devis",
        description = "Récupère les statistiques générales des devis"
    )
    @ApiResponse(responseCode = "200", description = "Statistiques récupérées avec succès")
    public ResponseEntity<DevisStatistiques> getStatistiques() {
        // Cette méthode sera implémentée plus tard
        DevisStatistiques stats = new DevisStatistiques();
        return ResponseEntity.ok(stats);
    }

    // Classe interne pour les statistiques (temporaire)
    public static class DevisStatistiques {
        private long totalDevis = 0;
        private long devisEnAttente = 0;
        private long devisValides = 0;
        private long devisRejetes = 0;
        private BigDecimal montantTotal = BigDecimal.ZERO;

        // Getters et setters
        public long getTotalDevis() { return totalDevis; }
        public void setTotalDevis(long totalDevis) { this.totalDevis = totalDevis; }
        
        public long getDevisEnAttente() { return devisEnAttente; }
        public void setDevisEnAttente(long devisEnAttente) { this.devisEnAttente = devisEnAttente; }
        
        public long getDevisValides() { return devisValides; }
        public void setDevisValides(long devisValides) { this.devisValides = devisValides; }
        
        public long getDevisRejetes() { return devisRejetes; }
        public void setDevisRejetes(long devisRejetes) { this.devisRejetes = devisRejetes; }
        
        public BigDecimal getMontantTotal() { return montantTotal; }
        public void setMontantTotal(BigDecimal montantTotal) { this.montantTotal = montantTotal; }
    }
}
