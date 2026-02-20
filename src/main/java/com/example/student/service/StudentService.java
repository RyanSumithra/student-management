package com.example.student.service;

import com.example.student.dto.StudentRequest;
import com.example.student.dto.StudentResponse;
import com.example.student.exception.StudentNotFoundException;
import com.example.student.entity.Student;
import com.example.student.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;




@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    // Helper Method to convert StudentRequest to Student Entity
    private StudentResponse mapToResponse(Student student){
        return new StudentResponse(student.getId(), student.getName(), student.getEmail());
    }
    
    // CREATE
    public StudentResponse createStudent(StudentRequest request){
        Student student = new Student();
        student.setName(request.getName());
        student.setEmail(request.getEmail());
        Student saved = studentRepository.save(student);
        return mapToResponse(saved);
    }

    // READ
    public Page<StudentResponse> getAllStudents(Pageable pageable){
        return studentRepository.findAll(pageable).map(this::mapToResponse);
    }

    public StudentResponse getStudentById(Long id){
        Student student = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
        return mapToResponse(student);
    }

    // UPDATE
    public StudentResponse updateStudent(Long id, StudentRequest request){
        Student student = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));      
        student.setName(request.getName());
        student.setEmail(request.getEmail());
        Student saved = studentRepository.save(student);
        return mapToResponse(saved);    
    }

    // DELETE
    public String deleteStudent(Long id){
        if(!studentRepository.existsById(id)){
            throw new StudentNotFoundException(id);
        }
        studentRepository.deleteById(id);
        return "Student deleted successfully";
    }
}
