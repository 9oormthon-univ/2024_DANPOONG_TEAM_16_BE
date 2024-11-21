package danpoong.mohaeng.user.service;

import danpoong.mohaeng.user.domain.User;
import danpoong.mohaeng.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public boolean createUser(String uuid) {
        if (userRepository.existsByUuid(uuid))
            return false;

        User createUser = new User(uuid);
        userRepository.save(createUser);

        return true;
    }
}
