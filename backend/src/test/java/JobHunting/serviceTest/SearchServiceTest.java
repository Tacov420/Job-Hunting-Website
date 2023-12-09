package JobHunting.serviceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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

    @Test
    @SuppressWarnings("unchecked")
    public void testGetJobs() {
        List<Search> jobs = new ArrayList<>();
        jobs.add(setJobs("Polymer Capital", "Full Stack Developer", "Entry level"));
        jobs.add(setJobs("GoGoX", "Software Engineer", "Internship"));
        jobs.add(setJobs("Cisco", "Software Engineer", "Not Applicable"));
        jobs.add(setJobs("Microsoft", "Frontend Software Engineer", "Not Applicable"));
        jobs.add(setJobs("Stellar Cyber", "Backend Engineer", "Entry level"));
        jobs.add(setJobs("Crypto.com", "Full-stack Developer", "Mid-Senior level"));

        List<Search> job1 = new ArrayList<>();
        job1.add(setJobs("Cisco", "Software Engineer", "Not Applicable"));
        when(searchRepository.findByCompany("Cisco")).thenReturn(job1);
        Object result1 = searchService.getJobs("Cisco", null, 2);
        assertEquals(job1.get(0).getCompany(), ((List<Search>) result1).getFirst().getCompany());
        assertEquals(job1.get(0).getJobTitle(), ((List<Search>) result1).getFirst().getJobTitle());
        assertEquals(job1.get(0).getLevel(), ((List<Search>) result1).getFirst().getLevel());

        List<Search> job2 = new ArrayList<>();
        job2.add(setJobs("Cisco", "Software Engineer", "Not Applicable"));
        job2.add(setJobs("GoGoX", "Software Engineer", "Internship"));
        when(searchRepository.findByJobTitle("Software Engineer")).thenReturn(job2);
        Object result2 = searchService.getJobs(null, "Software Engineer", 2);
        System.out.println(result2);
        assertEquals(job2.get(0).getCompany(), ((List<Search>) result2).getFirst().getCompany());
        assertEquals(job2.get(0).getJobTitle(), ((List<Search>) result2).getFirst().getJobTitle());
        assertEquals(job2.get(0).getLevel(), ((List<Search>) result2).getFirst().getLevel());
        assertEquals(job2.get(1).getCompany(), ((List<Search>) result2).getLast().getCompany());
        assertEquals(job2.get(1).getJobTitle(), ((List<Search>) result2).getLast().getJobTitle());
        assertEquals(job2.get(1).getLevel(), ((List<Search>) result2).getLast().getLevel());

        List<Search> job3 = new ArrayList<>();
        job3.add(setJobs("Polymer Capital", "Full Stack Developer", "Entry level"));
        job3.add(setJobs("Stellar Cyber", "Backend Engineer", "Entry level"));
        when(searchRepository.findByLevel("Entry level")).thenReturn(job3);
        Object result3 = searchService.getJobs(null, null, 0);
        System.out.println(result3);
        assertEquals(job3.get(0).getCompany(), ((List<Search>) result3).getFirst().getCompany());
        assertEquals(job3.get(0).getJobTitle(), ((List<Search>) result3).getFirst().getJobTitle());
        assertEquals(job3.get(0).getLevel(), ((List<Search>) result3).getFirst().getLevel());
        assertEquals(job3.get(1).getCompany(), ((List<Search>) result3).getLast().getCompany());
        assertEquals(job3.get(1).getJobTitle(), ((List<Search>) result3).getLast().getJobTitle());
        assertEquals(job3.get(1).getLevel(), ((List<Search>) result3).getLast().getLevel());
    }
}
