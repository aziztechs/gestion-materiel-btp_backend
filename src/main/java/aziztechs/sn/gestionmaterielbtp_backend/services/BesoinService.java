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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        Utilisateur currentUser = getCurrentUser();
        
        Besoin besoin = new Besoin();
        besoin.setDescription(request.getDescription());
        besoin.setUnite(request.getUnite());
        besoin.setQuantite(request.getQuantite());
        besoin.setDateDemande(LocalDate.now());
        besoin.setUtilisateur(currentUser);
        besoin.setIsUrgence(request.getIsUrgence() != null ? request.getIsUrgence() : false);
        besoin.setStatut(EBesoinStatut.EN_ATTENTE);

        Besoin savedBesoin = besoinRepository.save(besoin);
        return convertToResponse(savedBesoin);
    }

    public BesoinResponse updateBesoin(Long id, BesoinRequest request) {
        Besoin besoin = besoinRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Besoin non trouvé avec l'ID: " + id));

        // Vérifier que l'utilisateur peut modifier ce besoin
        Utilisateur currentUser = getCurrentUser();
        if (!besoin.getUtilisateur().getId().equals(currentUser.getId()) && 
            !currentUser.getRole().name().equals("ROLE_ADMIN_SYSTEME")) {
            throw new RuntimeException("Vous n'êtes pas autorisé à modifier ce besoin");
        }

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

        // Vérifier que l'utilisateur peut supprimer ce besoin
        Utilisateur currentUser = getCurrentUser();
        if (!besoin.getUtilisateur().getId().equals(currentUser.getId()) && 
            !currentUser.getRole().name().equals("ROLE_ADMIN_SYSTEME")) {
            throw new RuntimeException("Vous n'êtes pas autorisé à supprimer ce besoin");
        }

        besoinRepository.delete(besoin);
    }

    public Page<BesoinResponse> getBesoinsByCurrentUser(Pageable pageable) {
        Utilisateur currentUser = getCurrentUser();
        Page<Besoin> besoins = besoinRepository.findByUtilisateur(currentUser, pageable);
        return besoins.map(this::convertToResponse);
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

    private Utilisateur getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }
}
