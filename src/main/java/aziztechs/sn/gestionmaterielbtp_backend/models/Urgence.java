package aziztechs.sn.gestionmaterielbtp_backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "urgence")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Urgence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUrgence;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private LocalDate dateSignalement;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private Utilisateur utilisateur;
}
