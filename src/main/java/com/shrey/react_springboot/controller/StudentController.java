package com.shrey.react_springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shrey.react_springboot.model.Student;

import java.util.Arrays;
import java.util.List;


@RestController
public class StudentController {

   @GetMapping("/students")
   public List<Student> students() {
    return Arrays.asList(
        new Student(1, "John Doe", "Computer Science"),
        new Student(2, "Jane Smith", "Mathematics"),
        new Student(3, "Mike Johnson", "Physics")
    );
   }
   

    
}