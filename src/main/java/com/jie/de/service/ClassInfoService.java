package com.jie.de.service;

import com.jie.de.exception.ClassInfoNotFoundException;
import com.jie.de.exception.DuplicateMajorClassNameException;
import com.jie.de.model.dto.ClassInfoDTO;
import com.jie.de.model.entity.ClassInfo;
import com.jie.de.repository.ClassInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassInfoService {

    @Autowired
    private ClassInfoRepository classInfoRepository;

    //新增班级信息
    @Transactional
    public ResponseEntity<?> addClassInfo(ClassInfoDTO classInfoDTO) {
        try {
            if (classInfoRepository.findByMajorClassName(classInfoDTO.getMajorClassName()).isPresent()) {
                throw new DuplicateMajorClassNameException(
                        "专业班级名称已存在：" + classInfoDTO.getMajorClassName()
                );
            }

            ClassInfo classInfo = new ClassInfo();
            classInfo.setAcademyName(classInfoDTO.getAcademyName());
            classInfo.setMajorClassName(classInfoDTO.getMajorClassName());
            classInfo.setClassSize(classInfoDTO.getClassSize());

            ClassInfo savedClassInfo = classInfoRepository.save(classInfo);

            return new ResponseEntity<>(savedClassInfo, HttpStatus.CREATED);

        } catch (DuplicateMajorClassNameException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("新增班级失败：" + e.getMessage());
        }
    }

    //查询所有班级信息
    @Transactional(readOnly = true)
    public ResponseEntity<?> getAllClassInfo() {
        try {
            List<ClassInfo> classInfoList = classInfoRepository.findAll();

            if (classInfoList.isEmpty()) {
                throw new ClassInfoNotFoundException("当前系统中暂无班级信息");
            }

            return ResponseEntity.ok(classInfoList);

        } catch (ClassInfoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("查询所有班级失败：" + e.getMessage());
        }
    }

    //根据ID查询单个班级信息
    @Transactional(readOnly = true)
    public ResponseEntity<?> getClassInfoById(Long id) {
        try {
            ClassInfo classInfo = classInfoRepository.findById(id)
                    .orElseThrow(() -> new ClassInfoNotFoundException("班级ID不存在：" + id));

            ClassInfoDTO resultDTO = convertToDTO(classInfo);
            return ResponseEntity.ok(resultDTO);

        } catch (ClassInfoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("查询班级失败：" + e.getMessage());
        }
    }

    //修改班级信息
    @Transactional
    public ResponseEntity<?> updateClassInfo(Long id, ClassInfoDTO classInfoDTO) {
        try {
            ClassInfo existingClassInfo = classInfoRepository.findById(id)
                    .orElseThrow(() -> new ClassInfoNotFoundException("班级ID不存在：" + id));

            String newMajorClassName = classInfoDTO.getMajorClassName();
            if (!existingClassInfo.getMajorClassName().equals(newMajorClassName)) {
                classInfoRepository.findByMajorClassName(newMajorClassName)
                        .ifPresent(classInfo -> {
                            throw new DuplicateMajorClassNameException(
                                    "专业班级名称已存在：" + newMajorClassName
                            );
                        });
            }

            if (classInfoDTO.getAcademyName() != null) {
                existingClassInfo.setAcademyName(classInfoDTO.getAcademyName());
            }
            if (newMajorClassName != null) {
                existingClassInfo.setMajorClassName(newMajorClassName);
            }
            if (classInfoDTO.getClassSize() != null) {
                existingClassInfo.setClassSize(classInfoDTO.getClassSize());
            }

            ClassInfo updatedClassInfo = classInfoRepository.save(existingClassInfo);

            ClassInfoDTO resultDTO = convertToDTO(updatedClassInfo);
            return ResponseEntity.ok(resultDTO);

        } catch (ClassInfoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (DuplicateMajorClassNameException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("修改班级失败：" + e.getMessage());
        }
    }

    //删除班级信息
    @Transactional
    public ResponseEntity<?> deleteClassInfo(Long id) {
        try {
            if (!classInfoRepository.existsById(id)) {
                throw new ClassInfoNotFoundException("班级ID不存在：" + id);
            }

            classInfoRepository.deleteById(id);

            return ResponseEntity.ok("班级删除成功，ID：" + id); // 200 成功

        } catch (ClassInfoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("删除班级失败：" + e.getMessage());
        }
    }

    private ClassInfoDTO convertToDTO(ClassInfo classInfo) {
        ClassInfoDTO dto = new ClassInfoDTO();
        dto.setAcademyName(classInfo.getAcademyName());
        dto.setMajorClassName(classInfo.getMajorClassName());
        dto.setClassSize(classInfo.getClassSize());
        return dto;
    }

}
