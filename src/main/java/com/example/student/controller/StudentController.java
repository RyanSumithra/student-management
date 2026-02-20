package com.example.student.controller;

import org.springframework.web.bind.annotation.*;
import com.example.student.dto.StudentRequest;
import com.example.student.dto.StudentResponse;
import com.example.student.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping("/status")
    public String hello(){
        return "Server is Running on port http://localhost/8080";
    }

    // Read One
    @GetMapping("/{id}")
    public StudentResponse getById(@PathVariable Long id){
        return studentService.getStudentById(id);
    }

    // Read All
    @GetMapping
    public Page<StudentResponse> getAllStudents(Pageable pageable) {
        return studentService.getAllStudents(pageable);
    }

    // Update
    @PostMapping
    public StudentResponse create(@Valid @RequestBody StudentRequest request){
        return studentService.createStudent(request);
    }

    // Update
    @PutMapping("/{id}")
    public StudentResponse update(@PathVariable Long id, @Valid @RequestBody StudentRequest request){
        return studentService.updateStudent(id, request);
    }

    // Delete
    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Long id){
        return studentService.deleteStudent(id);
    }

}
