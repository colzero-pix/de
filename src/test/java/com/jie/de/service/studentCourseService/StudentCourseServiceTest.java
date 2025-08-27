package com.jie.de.service.studentCourseService;

import com.jie.de.model.entity.Course;
import com.jie.de.repository.StudentCourseRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser(username = "yue", password = "11111111", roles = {"STUDENT"})
class StudentCourseServiceTest {

    @Autowired
    private StudentCourseService studentCourseService;

    @MockBean
    private StudentCourseRepository studentCourseRepository;

    @Test
    void getStudentCourses() {
        Long studentId = 1L;
        Course course1 = new Course();
        course1.setCourseName("数学");
        Course course2 = new Course();
        course2.setCourseName("英语");

        Mockito.when(studentCourseRepository.findCoursesByStudentId(studentId))
                .thenReturn(Arrays.asList(course1, course2));

        List<Course> result = studentCourseService.getStudentCourses(studentId);
        assertEquals(2, result.size());
        assertEquals("数学", result.get(0).getCourseName());
        assertEquals("英语", result.get(1).getCourseName());
    }
}