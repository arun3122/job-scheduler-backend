package com.example.scheduler.service;

import com.example.scheduler.model.Job;
import com.example.scheduler.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    // Create a new job
    public Job createJob(String taskName, String priority, String payload) {
        Job job = new Job(taskName, priority, payload);
        return jobRepository.save(job);
    }

    // Get all jobs with optional filters
    public List<Job> getJobs(String status, String priority) {
        if (status != null && priority != null) {
            return jobRepository.findByStatusAndPriority(status, priority);
        } else if (status != null) {
            return jobRepository.findByStatus(status);
        } else if (priority != null) {
            return jobRepository.findByPriority(priority);
        }
        return jobRepository.findAll();
    }

    public Optional<Job> getJobById(Long id) {
        return jobRepository.findById(id);
    }

    // Logic to Run Job: Pending -> Running -> (Wait 3s) -> Completed
    public void runJob(Long id) {
        Optional<Job> optionalJob = jobRepository.findById(id);

        if (optionalJob.isPresent()) {
            Job job = optionalJob.get();

            // 1. Set to Running
            job.setStatus("running");
            job.setUpdatedAt(LocalDateTime.now());
            jobRepository.save(job);

            // 2. Simulate Background Processing (Async Thread)
            new Thread(() -> {
                try {
                    System.out.println("Processing Job ID: " + id);
                    Thread.sleep(3000); // Simulate 3 second delay

                    // 3. Set to Completed
                    job.setStatus("completed");
                    job.setUpdatedAt(LocalDateTime.now());
                    jobRepository.save(job);

                    System.out.println("Job ID: " + id + " Completed. Triggering Webhook...");
                    triggerWebhook(job);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    // Mock Webhook Trigger
    private void triggerWebhook(Job job) {
        // In a real app, use RestTemplate to call an external URL
        System.out.println("=== WEBHOOK SENT ===");
        System.out.println("Payload: " + job.getPayload());
        System.out.println("Status: " + job.getStatus());
        System.out.println("====================");
    }
}