package danpoong.mohaeng.course.controller;

import danpoong.mohaeng.course.dto.AICourseRes;
import danpoong.mohaeng.course.dto.CourseCreateReq;
import danpoong.mohaeng.course.dto.CourseCreateRes;
import danpoong.mohaeng.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/course")
@RequiredArgsConstructor
public class CourseControllerImpl implements CourseController{

    private final CourseService courseService;

    @PostMapping("")
    public ResponseEntity<CourseCreateRes> courseCreate(@RequestBody CourseCreateReq courseCreateReq) {
        CourseCreateRes courseCreateRes = courseService.createTrip(courseCreateReq);

        if (courseCreateRes == null || courseCreateRes.getDay1() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        return ResponseEntity.status(HttpStatus.CREATED).body(courseCreateRes);
    }

    @GetMapping("/ai-rec")
    public ResponseEntity<AICourseRes> courseCreate(@RequestParam("disability") List<Long> disability,
            @RequestParam("tripType") List<Long> tripType, @RequestParam("area") Long area, @RequestParam("period") Long period) {
        AICourseRes aiCourseRes = courseService.createAIRecCourse(disability, tripType, area, period);

        if (aiCourseRes == null || aiCourseRes.getDay1() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        return ResponseEntity.status(HttpStatus.CREATED).body(aiCourseRes);
    }
}
