package aziztechs.sn.gestionmaterielbtp_backend.services;

import aziztechs.sn.gestionmaterielbtp_backend.dtos.JwtResponse;
import aziztechs.sn.gestionmaterielbtp_backend.dtos.request.LoginRequest;
import aziztechs.sn.gestionmaterielbtp_backend.dtos.request.SignupRequest;
import aziztechs.sn.gestionmaterielbtp_backend.models.ERole;
import aziztechs.sn.gestionmaterielbtp_backend.models.Utilisateur;
import aziztechs.sn.gestionmaterielbtp_backend.repositories.UtilisateurRepository;
import aziztechs.sn.gestionmaterielbtp_backend.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String role = userDetails.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse(null);

        return new JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getEmail(),
                userDetails.getNom(),
                userDetails.getPrenom(),
                role
        );
    }

    public void registerUser(SignupRequest signupRequest) {
        if (utilisateurRepository.existsByEmail(signupRequest.getEmail())) {
            throw new RuntimeException("Email déjà utilisé!");
        }

        Utilisateur user = new Utilisateur();
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setNom(signupRequest.getNom());
        user.setPrenom(signupRequest.getPrenom());
        user.setTelephone(signupRequest.getTelephone());
        user.setRole(signupRequest.getRole() != null ? signupRequest.getRole() : ERole.ROLE_CHEF_PROJET);

        utilisateurRepository.save(user);

        // Envoyer email de bienvenue
        emailService.sendWelcomeEmail(user);
    }
}
