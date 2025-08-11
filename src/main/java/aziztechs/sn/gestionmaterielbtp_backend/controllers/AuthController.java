package aziztechs.sn.gestionmaterielbtp_backend.controllers;

import aziztechs.sn.gestionmaterielbtp_backend.dtos.JwtResponse;
import aziztechs.sn.gestionmaterielbtp_backend.dtos.request.LoginRequest;
import aziztechs.sn.gestionmaterielbtp_backend.dtos.request.SignupRequest;
import aziztechs.sn.gestionmaterielbtp_backend.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "API d'authentification")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signin")
    @Operation(summary = "Connexion utilisateur", description = "Authentifie un utilisateur et retourne un token JWT")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            JwtResponse jwtResponse = authService.authenticateUser(loginRequest);
            return ResponseEntity.ok(jwtResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/signup")
    @Operation(summary = "Inscription utilisateur", description = "Crée un nouveau compte utilisateur")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        try {
            authService.registerUser(signUpRequest);
            return ResponseEntity.ok().body("Utilisateur enregistré avec succès!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur: " + e.getMessage());
        }
    }

    @GetMapping("/test")
    @Operation(summary = "Test endpoint", description = "Endpoint de test pour vérifier que l'API fonctionne")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("API BTP Matériel fonctionne correctement!");
    }
}
