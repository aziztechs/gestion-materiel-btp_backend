package aziztechs.sn.gestionmaterielbtp_backend.services;

import aziztechs.sn.gestionmaterielbtp_backend.dto.DevisRequest;
import aziztechs.sn.gestionmaterielbtp_backend.dto.DevisResponse;
import aziztechs.sn.gestionmaterielbtp_backend.models.*;
import aziztechs.sn.gestionmaterielbtp_backend.repositories.BesoinRepository;
import aziztechs.sn.gestionmaterielbtp_backend.repositories.DevisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DevisService {

    private final DevisRepository devisRepository;
    private final BesoinRepository besoinRepository;

    /**
     * Créer un nouveau devis
     */
    public DevisResponse createDevis(DevisRequest request) {
        // Vérifier que le besoin existe
        Besoin besoin = besoinRepository.findById(request.getBesoinId())
                .orElseThrow(() -> new RuntimeException("Besoin non trouvé avec l'ID: " + request.getBesoinId()));

        // Créer le devis
        Devis devis = new Devis();
        devis.setMontant(request.getMontant());
        devis.setFournisseur(request.getFournisseur());
        devis.setReference(request.getReference());
        devis.setDescription(request.getDescription());
        devis.setBesoin(besoin);
        devis.setDateCreation(LocalDate.now());
        devis.setStatut(EDevisStatut.EN_ATTENTE);

        Devis savedDevis = devisRepository.save(devis);
        return mapToResponse(savedDevis);
    }

    /**
     * Récupérer tous les devis avec pagination
     */
    @Transactional(readOnly = true)
    public Page<DevisResponse> getAllDevis(Pageable pageable) {
        return devisRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    /**
     * Récupérer un devis par ID
     */
    @Transactional(readOnly = true)
    public DevisResponse getDevisById(Long id) {
        Devis devis = devisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Devis non trouvé avec l'ID: " + id));
        return mapToResponse(devis);
    }

    /**
     * Mettre à jour un devis
     */
    public DevisResponse updateDevis(Long id, DevisRequest request) {
        Devis devis = devisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Devis non trouvé avec l'ID: " + id));

        // Vérifier que le besoin existe si changé
        if (!devis.getBesoin().getId().equals(request.getBesoinId())) {
            Besoin besoin = besoinRepository.findById(request.getBesoinId())
                    .orElseThrow(() -> new RuntimeException("Besoin non trouvé avec l'ID: " + request.getBesoinId()));
            devis.setBesoin(besoin);
        }

        // Mettre à jour les champs
        devis.setMontant(request.getMontant());
        devis.setFournisseur(request.getFournisseur());
        devis.setReference(request.getReference());
        devis.setDescription(request.getDescription());

        Devis updatedDevis = devisRepository.save(devis);
        return mapToResponse(updatedDevis);
    }

    /**
     * Supprimer un devis
     */
    public void deleteDevis(Long id) {
        if (!devisRepository.existsById(id)) {
            throw new RuntimeException("Devis non trouvé avec l'ID: " + id);
        }
        devisRepository.deleteById(id);
    }

    /**
     * Valider un devis
     */
    public DevisResponse validerDevis(Long id) {
        Devis devis = devisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Devis non trouvé avec l'ID: " + id));

        devis.setStatut(EDevisStatut.VALIDE);
        Devis updatedDevis = devisRepository.save(devis);
        return mapToResponse(updatedDevis);
    }

    /**
     * Rejeter un devis
     */
    public DevisResponse rejeterDevis(Long id) {
        Devis devis = devisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Devis non trouvé avec l'ID: " + id));

        devis.setStatut(EDevisStatut.REJETE);
        Devis updatedDevis = devisRepository.save(devis);
        return mapToResponse(updatedDevis);
    }

    /**
     * Récupérer les devis par besoin
     */
    @Transactional(readOnly = true)
    public Page<DevisResponse> getDevisByBesoin(Long besoinId, Pageable pageable) {
        Besoin besoin = besoinRepository.findById(besoinId)
                .orElseThrow(() -> new RuntimeException("Besoin non trouvé avec l'ID: " + besoinId));

        return devisRepository.findByBesoin(besoin, pageable)
                .map(this::mapToResponse);
    }

    /**
     * Récupérer les devis par statut
     */
    @Transactional(readOnly = true)
    public List<DevisResponse> getDevisByStatut(EDevisStatut statut) {
        return devisRepository.findByStatut(statut)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Récupérer les devis par fournisseur
     */
    @Transactional(readOnly = true)
    public List<DevisResponse> getDevisByFournisseur(String fournisseur) {
        return devisRepository.findByFournisseurContainingIgnoreCase(fournisseur)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Récupérer les devis par plage de montant
     */
    @Transactional(readOnly = true)
    public List<DevisResponse> getDevisByMontantRange(BigDecimal min, BigDecimal max) {
        return devisRepository.findByMontantBetween(min, max)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Récupérer les devis par plage de dates
     */
    @Transactional(readOnly = true)
    public List<DevisResponse> getDevisByDateRange(LocalDate startDate, LocalDate endDate) {
        return devisRepository.findByDateCreationBetween(startDate, endDate)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Mapper un Devis vers DevisResponse
     */
    private DevisResponse mapToResponse(Devis devis) {
        DevisResponse response = new DevisResponse();
        response.setId(devis.getId());
        response.setMontant(devis.getMontant());
        response.setFournisseur(devis.getFournisseur());
        response.setReference(devis.getReference());
        response.setDescription(devis.getDescription());
        response.setDateCreation(devis.getDateCreation());
        response.setStatut(devis.getStatut());

        // Informations du besoin
        if (devis.getBesoin() != null) {
            response.setBesoinId(devis.getBesoin().getId());
            response.setBesoinDescription(devis.getBesoin().getDescription());
            response.setBesoinUnite(devis.getBesoin().getUnite());
            response.setBesoinQuantite(devis.getBesoin().getQuantite());
        }

        // Informations de validation
        response.setValide(devis.getStatut() == EDevisStatut.VALIDE);
        response.setNombreValidations(devis.getValidations() != null ? devis.getValidations().size() : 0);

        // Dernière validation
        if (devis.getValidations() != null && !devis.getValidations().isEmpty()) {
            ValidationDevis lastValidation = devis.getValidations().get(devis.getValidations().size() - 1);
            if (lastValidation.getUtilisateur() != null) {
                response.setDernierValidateur(lastValidation.getUtilisateur().getNom() + " " + 
                                            lastValidation.getUtilisateur().getPrenom());
            }
            response.setDateValidation(lastValidation.getDateValidation());
        }

        return response;
    }
}
