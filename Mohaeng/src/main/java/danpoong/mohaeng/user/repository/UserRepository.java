package danpoong.mohaeng.user.repository;

import danpoong.mohaeng.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUuid(String uuid);
    User findUserByUuid(String uuid);
}
