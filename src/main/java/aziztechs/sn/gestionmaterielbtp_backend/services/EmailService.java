package aziztechs.sn.gestionmaterielbtp_backend.services;

import aziztechs.sn.gestionmaterielbtp_backend.models.Besoin;
import aziztechs.sn.gestionmaterielbtp_backend.models.Utilisateur;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final Environment env;

    public void sendWelcomeEmail(Utilisateur user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(env.getProperty("spring.mail.username"));
        message.setTo(user.getEmail());
        message.setSubject("Bienvenue sur la plateforme de gestion des besoins matériels");
        message.setText(String.format(
                "Bonjour %s %s,\n\nVotre compte a été créé avec succès.\n\n" +
                        "Email: %s\nRôle: %s\n\n" +
                        "Cordialement,\nL'équipe BTP",
                user.getPrenom(), user.getNom(), user.getEmail(), user.getRole().name()));

        mailSender.send(message);
    }

    public void sendNewBesoinNotification(Besoin besoin, List<Utilisateur> responsables) {
        String subject = "Nouveau besoin matériel soumis";
        String content = String.format(
                "Un nouveau besoin a été soumis par %s %s:\n\n" +
                        "Description: %s\n" +
                        "Quantité: %d %s\n\n" +
                        "Merci de traiter cette demande dans les meilleurs délais.",
                besoin.getUtilisateur().getPrenom(), besoin.getUtilisateur().getNom(),
                besoin.getDescription(), besoin.getQuantite(), besoin.getUnite());

        responsables.forEach(responsable -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(env.getProperty("spring.mail.username"));
            message.setTo(responsable.getEmail());
            message.setSubject(subject);
            message.setText(content);
            mailSender.send(message);
        });
    }
}
