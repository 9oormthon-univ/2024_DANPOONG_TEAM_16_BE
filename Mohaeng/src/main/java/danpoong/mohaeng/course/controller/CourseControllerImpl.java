package danpoong.mohaeng.course.controller;

import danpoong.mohaeng.course.dto.*;
import danpoong.mohaeng.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/course")
@RequiredArgsConstructor
public class CourseControllerImpl implements CourseController{

    private final CourseService courseService;

    @PatchMapping("")
    public ResponseEntity<CourseCreateRes> courseCreate(@RequestBody CourseCreateReq courseCreateReq) {
        CourseCreateRes courseCreateRes = courseService.createTrip(courseCreateReq);

        if (courseCreateRes == null || courseCreateRes.getDay1() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        return ResponseEntity.status(HttpStatus.CREATED).body(courseCreateRes);
    }

    @PostMapping("/ai-rec")
    public ResponseEntity<Long> courseCreate(@RequestBody AICourseReq aiCourseReq) {
        Long courseNumber = courseService.createAIRecCourse(aiCourseReq);

        if (courseNumber == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        return ResponseEntity.status(HttpStatus.CREATED).body(courseNumber);
    }

    @DeleteMapping("/{course_Number}")
    public ResponseEntity<Void> courseDelete(@PathVariable Long course_Number){
        boolean isDeleted = courseService.deleteCourseByNum(course_Number);

        if(!isDeleted){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{course_Number}/location/{location}")
    public ResponseEntity<Void> deleteLocationFromCourse(
            @PathVariable Long course_Number,
            @PathVariable Long location) {
        boolean isDeleted = courseService.deletedLocationFromCourse(course_Number, location);

        if(!isDeleted){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/search/{course_Number}")
    public ResponseEntity<CourseSearchResponse> getCourseDetail(@PathVariable Long course_Number){
        CourseSearchResponse courseDetail = courseService.getCourseDetail(course_Number);

        if (courseDetail == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(courseDetail);
    }
}
