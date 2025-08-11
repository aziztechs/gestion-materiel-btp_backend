package aziztechs.sn.gestionmaterielbtp_backend.services;

import aziztechs.sn.gestionmaterielbtp_backend.dtos.request.BesoinRequest;
import aziztechs.sn.gestionmaterielbtp_backend.dtos.response.BesoinResponse;
import aziztechs.sn.gestionmaterielbtp_backend.exceptions.ResourceNotFoundException;
import aziztechs.sn.gestionmaterielbtp_backend.exceptions.BadRequestException;
import aziztechs.sn.gestionmaterielbtp_backend.models.Besoin;
import aziztechs.sn.gestionmaterielbtp_backend.models.EBesoinStatut;
import aziztechs.sn.gestionmaterielbtp_backend.models.Utilisateur;
import aziztechs.sn.gestionmaterielbtp_backend.repositories.BesoinRepository;
import aziztechs.sn.gestionmaterielbtp_backend.repositories.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class BesoinService {

    private final BesoinRepository besoinRepository;
    private final UtilisateurRepository utilisateurRepository;

    public Page<BesoinResponse> getAllBesoins(Pageable pageable) {
        Page<Besoin> besoins = besoinRepository.findAll(pageable);
        return besoins.map(this::convertToResponse);
    }

    public BesoinResponse getBesoinById(Long id) {
        Besoin besoin = besoinRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Besoin non trouvé avec l'ID: " + id));
        return convertToResponse(besoin);
    }

    public BesoinResponse createBesoin(BesoinRequest request) {
        // TODO: Implémenter la récupération de l'utilisateur connecté après l'ajout de la sécurité
        // Pour l'instant, nous utilisons le premier utilisateur trouvé
        Utilisateur utilisateur = utilisateurRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Aucun utilisateur trouvé"));
        
        Besoin besoin = new Besoin();
        besoin.setDescription(request.getDescription());
        besoin.setUnite(request.getUnite());
        besoin.setQuantite(request.getQuantite());
        besoin.setDateDemande(LocalDate.now());
        besoin.setUtilisateur(utilisateur);
        besoin.setIsUrgence(request.getIsUrgence() != null ? request.getIsUrgence() : false);
        besoin.setStatut(EBesoinStatut.EN_ATTENTE);

        Besoin savedBesoin = besoinRepository.save(besoin);
        return convertToResponse(savedBesoin);
    }

    public BesoinResponse updateBesoin(Long id, BesoinRequest request) {
        Besoin besoin = besoinRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Besoin non trouvé avec l'ID: " + id));

        // TODO: Ajouter la vérification des permissions après l'implémentation de la sécurité

        besoin.setDescription(request.getDescription());
        besoin.setUnite(request.getUnite());
        besoin.setQuantite(request.getQuantite());
        if (request.getIsUrgence() != null) {
            besoin.setIsUrgence(request.getIsUrgence());
        }

        Besoin updatedBesoin = besoinRepository.save(besoin);
        return convertToResponse(updatedBesoin);
    }

    public void deleteBesoin(Long id) {
        Besoin besoin = besoinRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Besoin non trouvé avec l'ID: " + id));

        // TODO: Ajouter la vérification des permissions après l'implémentation de la sécurité

        besoinRepository.delete(besoin);
    }

    public Page<BesoinResponse> getBesoinsByCurrentUser(Pageable pageable) {
        // TODO: Implémenter après l'ajout de la sécurité
        // Pour l'instant, retourner tous les besoins
        return getAllBesoins(pageable);
    }

    public BesoinResponse marquerUrgent(Long id) {
        Besoin besoin = besoinRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Besoin non trouvé avec l'ID: " + id));

        besoin.setIsUrgence(true);
        Besoin updatedBesoin = besoinRepository.save(besoin);
        return convertToResponse(updatedBesoin);
    }

    private BesoinResponse convertToResponse(Besoin besoin) {
        BesoinResponse response = new BesoinResponse();
        response.setId(besoin.getId());
        response.setDescription(besoin.getDescription());
        response.setUnite(besoin.getUnite());
        response.setQuantite(besoin.getQuantite());
        response.setDateDemande(besoin.getDateDemande());
        response.setIsUrgence(besoin.getIsUrgence());
        response.setStatut(besoin.getStatut());
        response.setUtilisateurNom(besoin.getUtilisateur().getNom() + " " + besoin.getUtilisateur().getPrenom());
        response.setUtilisateurEmail(besoin.getUtilisateur().getEmail());
        return response;
    }


}
