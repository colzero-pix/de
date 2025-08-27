package com.jie.de.service.score;

import com.jie.de.exception.DuplicateScoreException;
import com.jie.de.exception.ForbiddenException;
import com.jie.de.exception.ScoreNotFoundException;
import com.jie.de.model.dto.ScoreDTO;
import com.jie.de.model.dto.StudentScoreDTO;
import com.jie.de.model.entity.Course;
import com.jie.de.model.entity.Score;
import com.jie.de.model.entity.User;
import com.jie.de.repository.CourseRepository;
import com.jie.de.repository.ScoreRepository;
import com.jie.de.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    // 新增成绩
    @Transactional
    public ResponseEntity<?> addScore(ScoreDTO scoreDTO) {
        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            User student = userRepository.findByUsername(scoreDTO.getStudentName()).orElseThrow(() -> new RuntimeException("该学生不存在"));

            Course course = courseRepository.findById(scoreDTO.getCourseId()).orElseThrow(() -> new RuntimeException("该课程不存在"));

            if(!(course.getTeacherName().equals(username) && course.getClassName().equals(student.getClassName()))) {
                throw new ForbiddenException("你无权修改该同学该成绩");
            }

            if (scoreRepository.findByCourseIdAndStudentId(scoreDTO.getCourseId(), student.getUserId()).isPresent()) {
                throw new DuplicateScoreException("学生：" + scoreDTO.getStudentName() + " 在课程：" + scoreDTO.getCourseName() + " 已有成绩");
            }

            Score score = new Score();
            score.setCourseName(scoreDTO.getCourseName());
            score.setTeacherName(scoreDTO.getTeacherName());
            score.setStudentName(scoreDTO.getStudentName());
            score.setStudentId(student.getUserId());
            score.setCourseId(scoreDTO.getCourseId());
            score.setScore(scoreDTO.getScore());

            Score savedScore = scoreRepository.save(score);
            return new ResponseEntity<>(savedScore, HttpStatus.CREATED);
        } catch (DuplicateScoreException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("新增成绩失败：" + e.getMessage());
        }
    }

    // 查询所有成绩
    @Transactional(readOnly = true)
    public ResponseEntity<?> getAllScores() {
        List<Score> scores = scoreRepository.findAll();
        return scores.isEmpty() ?
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("暂无成绩记录") :
                ResponseEntity.ok(scores);
    }

    // 按学生ID查询成绩（学生查看自己的成绩）
    @Transactional(readOnly = true)
    public ResponseEntity<?> getScoresByStudentId() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User student = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("该学生不存在"));

        List<Score> scores = scoreRepository.findByStudentId(student.getUserId());
        if (scores.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("该学生暂无成绩记录");
        }

        List<StudentScoreDTO> result = scores.stream()
                .map(score -> new StudentScoreDTO(
                        score.getCourseName(),
                        score.getTeacherName(),
                        score.getScore()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    // 按课程ID查询成绩（老师查看某课程成绩）
    @Transactional(readOnly = true)
    public ResponseEntity<?> getScoresByCourseId(Long courseId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("该课程不存在"));

        if(!course.getTeacherName().equals(username)) {
            throw new ForbiddenException("你无权查看该课程ID的成绩");
        }

        List<Score> scores = scoreRepository.findByCourseId(courseId);
        return scores.isEmpty() ?
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("该课程暂无成绩记录") :
                ResponseEntity.ok(scores);
    }

    // 修改成绩
    @Transactional
    public ResponseEntity<?> updateScore(Long id, ScoreDTO scoreDTO) {
        try {
            Score existingScore = scoreRepository.findById(id)
                    .orElseThrow(() -> new ScoreNotFoundException("成绩ID不存在：" + id));

            existingScore.setCourseName(scoreDTO.getCourseName());
            existingScore.setTeacherName(scoreDTO.getTeacherName());
            existingScore.setStudentName(scoreDTO.getStudentName());
            existingScore.setScore(scoreDTO.getScore());

            Score updatedScore = scoreRepository.save(existingScore);
            return ResponseEntity.ok(updatedScore);
        } catch (ScoreNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("修改成绩失败：" + e.getMessage());
        }
    }

    // 删除成绩
    @Transactional
    public ResponseEntity<?> deleteScore(Long id) {
        try {
            if (!scoreRepository.existsById(id)) {
                throw new ScoreNotFoundException("成绩ID不存在：" + id);
            }
            scoreRepository.deleteById(id);
            return ResponseEntity.ok("成绩删除成功，ID：" + id);
        } catch (ScoreNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("删除成绩失败：" + e.getMessage());
        }
    }

    public List<Course> getCourses() {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("错误"));

        return courseRepository.findCoursesByClassNameContaining(user.getClassName());
    }
}
