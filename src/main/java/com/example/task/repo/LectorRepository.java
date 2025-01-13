package com.example.task.repo;

import com.example.task.model.Lector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface LectorRepository extends JpaRepository<Lector, Long> {

    Set<Lector> findByNameContainsIgnoreCase(String name);

    Set<Lector> findBySurnameContainsIgnoreCase(String surname);

}
