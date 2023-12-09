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
public class CompanyServiceTest {
    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private SearchRepository searchRepository;

    @InjectMocks
    private CompanyService companyService;

    public Search setJobs(String company, String jobTitle, String level) {
        Search job = new Search();
        job.setCompany(company);
        job.setJobTitle(jobTitle);
        job.setLevel(level);
        return job;
    }

    public Company setCompany(int id, int userId, String name, int receiveEmail) {
        Company company = new Company();
        company.setId(id);
        company.setUserId(userId);
        company.setName(name);
        company.setReceiveEmail(receiveEmail);
        return company;
    }

    @Test
    public void testGetAllCompany() {
        List<Search> jobs = new ArrayList<>();
        jobs.add(setJobs("Polymer Capital", "Full Stack Developer", "Entry level"));
        jobs.add(setJobs("GoGoX", "Software Engineer Intern", "Internship"));
        jobs.add(setJobs("Cisco", "Software Engineer - Full time - Taipei - Sourcing", "Not Applicable"));
        jobs.add(setJobs("Microsoft", "Frontend Software Engineer", "Not Applicable"));
        // jobs.add(setJobs("Stellar Cyber", "Backend Engineer", "Entry level"));
        // jobs.add(setJobs("Crypto.com", "Full-stack Developer", "Mid-Senior level"));
        when(searchRepository.findAll()).thenReturn(jobs);
        List<Company> companies = new ArrayList<>();
        companies.add(setCompany(0, 0, "Google", 1));
        companies.add(setCompany(1, 0, "Microsoft", 1));
        companies.add(setCompany(2, 0, "Mcdonald’s", 1));
        when(companyRepository.findByUserId(0)).thenReturn(companies);
        Map<String, List<Object>> expected = new HashMap<>();
        expected.put("companies", Arrays.asList(Arrays.asList("Polymer Capital", false), Arrays.asList("GoGoX", false), Arrays.asList("Cisco", false), Arrays.asList("Microsoft", true)));
        Object result = companyService.getAllCompany(0);
        String expectedString = expected.toString();
        String resultString = result.toString();
        assertEquals(expectedString, resultString);
    }

    @Test
    public void testAddCompany() {

    }
}
