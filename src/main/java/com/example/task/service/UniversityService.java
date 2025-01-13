package com.example.task.service;

public interface UniversityService {

    String getDepartmentHead(String departmentName);

    String getDepartmentStatistics(String departmentName);

    String getAverageSalary(String departmentName);

    String getEmployeeCount(String departmentName);

    String globalSearch(String template);
}
