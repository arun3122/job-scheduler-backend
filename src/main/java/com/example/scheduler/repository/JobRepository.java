package com.example.scheduler.repository;

import com.example.scheduler.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    // Custom filter methods
    List<Job> findByStatus(String status);

    List<Job> findByPriority(String priority);

    List<Job> findByStatusAndPriority(String status, String priority);
}