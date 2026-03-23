package tech.studease.studease.common.config;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tech.studease.studease.domain.users.Authority;
import tech.studease.studease.domain.users.Authority.AuthorityName;
import tech.studease.studease.domain.users.AuthorityRepository;
import tech.studease.studease.domain.users.User;
import tech.studease.studease.domain.users.UserRepository;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

  private final UserRepository userRepository;
  private final AuthorityRepository authorityRepository;
  private final PasswordEncoder passwordEncoder;

  @Value("${app.jwt.admin-email}")
  private String adminEmail;

  @Value("${app.jwt.admin-password}")
  private String adminPassword;

  @Override
  @Transactional
  public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
    Authority userAuthority = createAuthorityIfNotFound(AuthorityName.ROLE_USER);
    Authority adminAuthority = createAuthorityIfNotFound(AuthorityName.ROLE_ADMIN);
    if (!userRepository.existsByEmail(adminEmail)) {
      User user =
          User.builder()
              .email(adminEmail)
              .firstName("Admin")
              .lastName("Admin")
              .password(passwordEncoder.encode(adminPassword))
              .balance(1_000_000)
              .isActive(true)
              .authorities(Set.of(userAuthority, adminAuthority))
              .build();

      userRepository.save(user);
    }
  }

  private Authority createAuthorityIfNotFound(AuthorityName authority) {
    return authorityRepository
        .findByAuthority(authority)
        .orElseGet(
            () -> authorityRepository.save(Authority.builder().authority(authority).build()));
  }
}
