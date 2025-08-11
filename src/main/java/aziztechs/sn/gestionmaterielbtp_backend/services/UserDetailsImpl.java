package aziztechs.sn.gestionmaterielbtp_backend.services;

import aziztechs.sn.gestionmaterielbtp_backend.models.Utilisateur;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private Long id;
    private String email;
    private String password;
    private String nom;
    private String prenom;
    private String telephone;
    private Boolean isActive;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImpl build(Utilisateur user) {
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().name());
        
        return new UserDetailsImpl(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getNom(),
                user.getPrenom(),
                user.getTelephone(),
                user.getIsActive(),
                Collections.singletonList(authority)
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive != null ? isActive : true;
    }
}
