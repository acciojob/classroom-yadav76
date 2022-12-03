package com.driver;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Component                  // To make Bean of StudentRepository class
@Repository
public class StudentRepository {

    private HashMap<String, Student> studentMap;
    private HashMap<String, Teacher> teacherMap;
    private HashMap<String, List<String>> teacherStudentMap;

    public StudentRepository() {
        this.studentMap = new HashMap<String, Student>();
        this.teacherMap = new HashMap<String, Teacher>();
        this.teacherStudentMap = new HashMap<String, List<String>>();
    }

    // Adding Student Objects
    public void addStudent(Student s) {
        studentMap.put(s.getName(),s);
    }

    // Adding Teacher Objects
    public void addTeacher(Teacher t) {
        teacherMap.put(t.getName(),t);
    }

    // Pair an Existing Student and Teacher
    public void teacherStudentPair(String studentName, String teacherName) {
        if (studentMap.containsKey(studentName) && teacherMap.containsKey(teacherName)) {

            List<String> listOfStudents = new ArrayList<>();
            if (teacherStudentMap.containsKey(teacherName)) {
                listOfStudents = teacherStudentMap.get(teacherName);
            }

            listOfStudents.add(studentName);

            teacherStudentMap.put(teacherName,listOfStudents);
        }
    }

    // Find Student by Student Name
    public Student findStudent(String studentName) {
        return studentMap.get(studentName);
    }

    // Find Teacher by Teacher Name
    public Teacher findTeacher(String teacherName) {
        return teacherMap.get(teacherName);
    }

    // Get List of Student by TeacherName
    public List<String> getStudentByTeacher(String teacherName) {
        List<String> listOfStudentNames = new ArrayList<>();
        if (teacherStudentMap.containsKey(teacherName)) {
            listOfStudentNames = teacherStudentMap.get(teacherName);
        }

        return listOfStudentNames;
    }

    // Get List of All Students added
    public List<String> findAllStudentNames() {
        List<String> allStudentNames = new ArrayList<>();

        for (String name : studentMap.keySet())
            allStudentNames.add(name);

        return allStudentNames;
    }

    // Delete a Teacher and Student Pair
    public void deletePair(String teacherName) {
        List<String> studentNames = new ArrayList<>();
        if (teacherStudentMap.containsKey(teacherName)) {
            studentNames = teacherStudentMap.get(teacherName);

            // Delete Students from Student Map
            for (String name : studentNames)
                if (studentMap.containsKey(name)) {
                    studentMap.remove(name);
                }

            // Remove Teacher and Student pair from TeacherStudentMap
            teacherStudentMap.remove(teacherName);
        }

        // Now Delete Teacher from teacherMap also
        if (teacherMap.containsKey(teacherName)) {
            teacherMap.remove(teacherName);
        }
    }

    // Delete all Teachers and Students Records
    public void deleteAllRecords() {

        // Making a HashSet to Delete all records Because Deleting in HashMap Directly can occur error
        HashSet<String> studentSet = new HashSet<>();

        // add all Students names in Set to delete them
        for (String teacherName : teacherStudentMap.keySet()) {
            for (String studentName : teacherStudentMap.get(teacherName)) {
                studentSet.add(studentName);
            }
        }

        // Now Delete all records
        for (String student : studentSet) {
            if (studentMap.containsKey(student)) {
                studentMap.remove(student);
            }
        }
    }
}
