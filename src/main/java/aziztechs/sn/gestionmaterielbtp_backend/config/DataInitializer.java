package aziztechs.sn.gestionmaterielbtp_backend.config;

import aziztechs.sn.gestionmaterielbtp_backend.models.Utilisateur;
import aziztechs.sn.gestionmaterielbtp_backend.models.ERole;
import aziztechs.sn.gestionmaterielbtp_backend.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Profile("test")
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public void run(String... args) throws Exception {
        // Créer un utilisateur de test s'il n'existe pas
        if (utilisateurRepository.count() == 0) {
            Utilisateur testUser = new Utilisateur();
            testUser.setNom("Test");
            testUser.setPrenom("User");
            testUser.setEmail("test@example.com");
            testUser.setPassword("password123"); // Mot de passe simple pour les tests
            testUser.setTelephone("123456789");
            testUser.setRole(ERole.ROLE_CHEF_PROJET);
            testUser.setIsActive(true);
            
            utilisateurRepository.save(testUser);
            System.out.println("Utilisateur de test créé: " + testUser.getEmail());
        }
    }
}
