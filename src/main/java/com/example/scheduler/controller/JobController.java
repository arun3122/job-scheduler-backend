package com.example.scheduler.controller;

import com.example.scheduler.model.Job;
import com.example.scheduler.service.JobService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // Allow React Frontend
public class JobController {

    @Autowired
    private JobService jobService;

    @Autowired
    private ObjectMapper objectMapper; // Helper to convert JSON Object to String

    // GET /jobs (Supports filters like /jobs?status=pending)
    @GetMapping("/jobs")
    public List<Job> getAllJobs(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority) {
        return jobService.getJobs(status, priority);
    }

    // GET /jobs/{id}
    @GetMapping("/jobs/{id}")
    public Job getJobById(@PathVariable Long id) {
        return jobService.getJobById(id).orElseThrow(() -> new RuntimeException("Job not found"));
    }

    // POST /jobs
    @PostMapping("/jobs")
    public Job createJob(@RequestBody Map<String, Object> requestBody) {
        try {
            String taskName = (String) requestBody.get("taskName");
            String priority = (String) requestBody.get("priority");

            // Convert the 'payload' object (JSON) into a String for storage
            Object payloadObj = requestBody.get("payload");
            String payloadStr = objectMapper.writeValueAsString(payloadObj);

            return jobService.createJob(taskName, priority, payloadStr);
        } catch (Exception e) {
            throw new RuntimeException("Error processing JSON payload", e);
        }
    }

    // POST /run-job/{id}
    @PostMapping("/run-job/{id}")
    public String runJob(@PathVariable Long id) {
        jobService.runJob(id);
        return "Job processing started";
    }
}