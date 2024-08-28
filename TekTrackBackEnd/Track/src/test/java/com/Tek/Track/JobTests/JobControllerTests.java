package com.Tek.Track.JobTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import com.Tek.Track.Controllers.JobController;
import com.Tek.Track.Models.JobInfo;
import com.Tek.Track.Services.JobService;

@WebMvcTest(controllers = JobController.class)
@ActiveProfiles("test")
public class JobControllerTests {

    @Mock
    private JobService jobService;

    @InjectMocks
    private JobController jobController;

    private JobInfo job1;
    private JobInfo job2;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        job1 = new JobInfo();
        job1.setJobInfoId(1L);
        job1.setJobTitle("Software Engineer");

        job2 = new JobInfo();
        job2.setJobInfoId(2L);
        job2.setJobTitle("Data Scientist");
    }

    @Test
    public void testGetAllJobs() {
        List<JobInfo> jobs = new ArrayList<>();
        jobs.add(job1);
        jobs.add(job2);
        when(jobService.findAll()).thenReturn(jobs);

        ResponseEntity<List<JobInfo>> response = jobController.getAllJobs();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(jobService, times(1)).findAll();
    }

    @Test
    public void testGetJob() {
        when(jobService.findById(1L)).thenReturn(job1);

        ResponseEntity<JobInfo> response = jobController.getJob(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(job1, response.getBody());
        verify(jobService, times(1)).findById(1L);
    }

    @Test
    public void testCreateJob() {
        when(jobService.create(any(JobInfo.class))).thenReturn(job1);

        ResponseEntity<JobInfo> response = jobController.create(job1);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(job1, response.getBody());
        verify(jobService, times(1)).create(job1);
    }

    



}
