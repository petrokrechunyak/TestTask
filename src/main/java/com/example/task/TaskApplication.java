package com.example.task;

import com.example.task.service.UniversityService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootApplication
public class TaskApplication implements CommandLineRunner {

    @Autowired
    private UniversityService universityService;

    private final Map<Pattern, Consumer<Matcher>> commandPatterns = new HashMap<>();

    public static void main(String[] args) {
        SpringApplication.run(TaskApplication.class, args);
    }


    /**
     * @param args Main method, all program logic will be run here
     */
    @Override
    public void run(String... args) {
        fillData();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter a command: ");
            String input = scanner.nextLine().trim().toLowerCase();

            boolean matched = false;
            for (Map.Entry<Pattern, Consumer<Matcher>> entry : commandPatterns.entrySet()) {
                Matcher matcher = entry.getKey().matcher(input);
                if (matcher.matches()) {
                    try {
                        entry.getValue().accept(matcher);
                    } catch (EntityNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    matched = true;
                }
            }

            if (!matched) {
                System.out.println("Unknown command.");
            }
            System.out.println("============================");
        }
    }

    /**
     * This method is needed to fill commandPatterns map with commands and their behavior
     */
    private void fillData() {
        fillEntry("who is head of department (.+)",
                matcher -> {
                    String departmentName = matcher.group(1);
                    System.out.println(universityService.getDepartmentHead(departmentName));
                });

        fillEntry("show (.+) statistics",
                matcher -> {
                    String departmentName = matcher.group(1);
                    System.out.println(universityService.getDepartmentStatistics(departmentName));
                });

        fillEntry("show the average salary for the department (.+)",
                matcher -> {
                    String departmentName = matcher.group(1);
                    System.out.println(universityService.getAverageSalary(departmentName));
                });

        fillEntry("show count of employee for (.+)",
                matcher -> {
                    String departmentName = matcher.group(1);
                    System.out.println(universityService.getEmployeeCount(departmentName));
                });

        fillEntry("global search by (.+)",
                matcher -> {
                    String searchTemplate = matcher.group(1);
                    String result = universityService.globalSearch(searchTemplate);
                    if(result.isBlank()) {
                        System.out.println("No matches found!");
                        return;
                    }
                    System.out.println(result);
                });
    }

    private void fillEntry(String command, Consumer<Matcher> action) {
        commandPatterns.put(Pattern.compile(command), action);
    }

}
