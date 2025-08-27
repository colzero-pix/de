package com.jie.de.service.course;

import com.jie.de.model.entity.Course;
import com.jie.de.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser(username = "YUE",password = "123456",roles = {"TEACHER"})
class CourseServiceTest {

    private CourseRepository courseRepository;
    private CourseService courseService;

    @BeforeEach
    void setUp() {
        courseRepository = Mockito.mock(CourseRepository.class);
        courseService = new CourseService();
        // 通过反射注入 mock 的 repository
        try {
            var field = CourseService.class.getDeclaredField("courseRepository");
            field.setAccessible(true);
            field.set(courseService, courseRepository);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getTeacherCourses() {
        Long teacherId = 1L;
        Course c1 = new Course(); c1.setTeacherId(teacherId);
        Course c2 = new Course(); c2.setTeacherId(teacherId);
        Mockito.when(courseRepository.findByTeacherId(teacherId)).thenReturn(Arrays.asList(c1, c2));

        List<Course> result = courseService.getTeacherCourses(teacherId);
        assertEquals(2, result.size());
    }

    @Test
    void getTeacherDailyCourses() {
        Long teacherId = 1L;
        int weekday = 3;
        Course c1 = new Course(); c1.setTeacherId(teacherId); c1.setWeekday(3);
        Course c2 = new Course(); c2.setTeacherId(teacherId); c2.setWeekday(2);
        Mockito.when(courseRepository.findByTeacherId(teacherId)).thenReturn(Arrays.asList(c1, c2));

        List<Course> result = courseService.getTeacherDailyCourses(teacherId, weekday);
        assertEquals(1, result.size());
        assertEquals(3, result.get(0).getWeekday());
    }

    @Test
    void getCourseById() {
        Long id = 10L;
        Course c = new Course(); c.setId(id);
        Mockito.when(courseRepository.findById(id)).thenReturn(Optional.of(c));

        Optional<Course> result = courseService.getCourseById(id);
        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
    }

    @Transactional
    @Rollback()
    @Test
    void addCourse() {
        Course c = new Course();
        Mockito.when(courseRepository.save(c)).thenReturn(c);

        Course result = courseService.addCourse(c);
        assertEquals(c, result);
    }

    @Transactional
    @Rollback()
    @Test
    void updateCourse() {
        Course c = new Course();
        Mockito.when(courseRepository.save(c)).thenReturn(c);

        Course result = courseService.updateCourse(c);
        assertEquals(c, result);
    }
    @Transactional
    @Rollback()
    @Test
    void deleteCourse() {
        Long id = 5L;
        courseService.deleteCourse(id);
        Mockito.verify(courseRepository).deleteById(id);
    }
}