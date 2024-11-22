package danpoong.mohaeng.user.service;

import danpoong.mohaeng.course.repository.CourseRepository;
import danpoong.mohaeng.user.domain.User;
import danpoong.mohaeng.user.dto.UserCourseRes;
import danpoong.mohaeng.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public boolean createUser(String uuid) {
        if (checkUserExist(uuid))
            return false;

        User createUser = new User(uuid);
        userRepository.save(createUser);

        return true;
    }

    public boolean checkUserExist(String uuid) {
        return userRepository.existsByUuid(uuid);
    }

    public List<UserCourseRes> getCourseList(String uuid) {
        return courseRepository.findAllByUser(userRepository.findUserByUuid(uuid)).stream()
                .map(UserCourseRes::new)
                .toList();
    }
}
