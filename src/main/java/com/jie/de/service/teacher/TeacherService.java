package com.jie.de.service.teacher;

import com.jie.de.model.dto.StudentLoginDTO;
import com.jie.de.model.entity.Student;
import com.jie.de.model.entity.User;

public interface TeacherService {

    public User studentRegister(StudentLoginDTO studentLoginDTO);



}
