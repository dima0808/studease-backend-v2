package tech.studease.studease.domain.users;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.studease.studease.domain.users.Authority.AuthorityName;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, UUID> {

  Optional<Authority> findByAuthority(AuthorityName authority);
}
