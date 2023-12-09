package JobHunting.serviceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import JobHunting.model.*;
import JobHunting.repository.*;
import JobHunting.service.*;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {
    @Mock 
    private SearchRepository searchRepository;

    @InjectMocks
    private SearchService searchService;

    public Search setJobs(String company, String jobTitle, String level) {
        Search job = new Search();
        job.setCompany(company);
        job.setJobTitle(jobTitle);
        job.setLevel(level);
        return job;
    }

    // public boolean compare(String company, String jobTitle, String level) {

    // }

    @Test
    public void testGetJobs() {
        // TODO..............
        // List<Search> jobs = new ArrayList<>();
        // jobs.add(setJobs("Polymer Capital", "Full Stack Developer", "Entry level"));
        // jobs.add(setJobs("GoGoX", "Software Engineer Intern", "Internship"));
        // jobs.add(setJobs("Cisco", "Software Engineer - Full time - Taipei - Sourcing", "Not Applicable"));
        // jobs.add(setJobs("Microsoft", "Frontend Software Engineer", "Not Applicable"));
        // jobs.add(setJobs("Stellar Cyber", "Backend Engineer", "Entry level"));
        // jobs.add(setJobs("Crypto.com", "Full-stack Developer", "Mid-Senior level"));

        // List<Search> job1 = new ArrayList<>();
        // job1.add(setJobs("Cisco", "Software Engineer - Full time - Taipei - Sourcing", "Not Applicable"));
        // when(searchRepository.findByCompany("Cisco")).thenReturn(job1);
        // Object result1 = searchService.getJobs("Cisco", null, 2);
        // System.out.println(result1);
    }
}
