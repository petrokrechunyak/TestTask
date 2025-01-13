package com.example.task.service.impl;

import com.example.task.model.Degree;
import com.example.task.model.Department;
import com.example.task.model.Lector;
import com.example.task.repo.DepartmentRepository;
import com.example.task.repo.LectorRepository;
import com.example.task.service.UniversityService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service

public class UniversityServiceImpl implements UniversityService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private LectorRepository lectorRepository;

    @Override
    public String getDepartmentHead(String departmentName) {
        Department department = departmentRepository.findByNameIgnoreCase(departmentName)
                .orElseThrow(() -> new EntityNotFoundException("Department " + departmentName + " not found"));
        return "Head of " + departmentName + " department is " + department.getHead().getName();
    }

    @Transactional
    @Override
    public String getDepartmentStatistics(String departmentName) {
        Department department = departmentRepository.findByNameIgnoreCase(departmentName)
                .orElseThrow(() -> new EntityNotFoundException("Department " + departmentName + "not found"));

        long assistantCount = department.getLectors().stream().filter(lector -> lector.getDegree() == Degree.ASSISTANT).count();
        long associateProfessorCount = department.getLectors().stream().filter(lector -> lector.getDegree() == Degree.ASSOCIATE_PROFESSOR).count();
        long professorCount = department.getLectors().stream().filter(lector -> lector.getDegree() == Degree.PROFESSOR).count();

        return "assistants - " + assistantCount + "\nassociate professors - " + associateProfessorCount + "\nprofessors - " + professorCount;
    }

    @Transactional
    @Override
    public String getAverageSalary(String departmentName) {
        Department department = departmentRepository.findByNameIgnoreCase(departmentName)
                .orElseThrow(() -> new EntityNotFoundException("Department " + departmentName + " not found"));

        double averageSalary = department.getLectors().stream().mapToDouble(Lector::getSalary).average().orElse(0);
        return "The average salary of " + departmentName + " is " + averageSalary;
    }

    @Transactional
    @Override
    public String getEmployeeCount(String departmentName) {
        Department department = departmentRepository.findByNameIgnoreCase(departmentName)
                .orElseThrow(() -> new EntityNotFoundException("Department " + departmentName + " not found"));

        return String.valueOf(department.getLectors().size());
    }

    @Transactional
    @Override
    public String globalSearch(String template) {
        Set<Lector> names = lectorRepository.findByNameContainsIgnoreCase(template);
        names.addAll(lectorRepository.findBySurnameContainsIgnoreCase(template));
        return names.stream().map(lector -> lector.getName() + " " + lector.getSurname()).collect(Collectors.joining(", "));
    }
}
