package tech.studease.studease.domain.users;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import tech.studease.studease.domain.collections.Collection;
import tech.studease.studease.domain.tests.Test;
import tech.studease.studease.domain.users.Authority.AuthorityName;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "users")
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID userReference;

  private String email;
  private String firstName;
  private String lastName;
  private String password;

  private Integer balance;
  private Boolean isActive;

  @OneToMany(
      mappedBy = "author",
      fetch = FetchType.LAZY,
      orphanRemoval = true,
      cascade = CascadeType.ALL)
  private List<Test> tests;

  @OneToMany(
      mappedBy = "author",
      fetch = FetchType.LAZY,
      orphanRemoval = true,
      cascade = CascadeType.ALL)
  private List<Collection> collections;

  @ManyToMany(fetch = FetchType.EAGER)
  private Set<Authority> authorities;

  public boolean hasAuthority(AuthorityName authorityName) {
    return authorities.stream()
        .anyMatch(a -> Objects.equals(a.getAuthority(), authorityName.name()));
  }

  @Override
  public String getUsername() {
    return email;
  }
}
