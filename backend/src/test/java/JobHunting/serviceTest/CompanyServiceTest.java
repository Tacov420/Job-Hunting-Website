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
        when(companyRepository.findFirstByOrderByIdDesc()).thenReturn(null);
        int result = companyService.addCompany(0, "777");
        assertEquals(0, result);
        Company company = setCompany(0, 0, "888", 1);
        when(companyRepository.findFirstByOrderByIdDesc()).thenReturn(company);
        int result1 = companyService.addCompany(0, "999");
        assertEquals(1, result1);
    }

    @Test
    public void testGetSpecificCompany() {
        List<Company> companies = new ArrayList<>();
        companies.add(setCompany(0, 0, "Google", 1));
        companies.add(setCompany(1, 0, "Microsoft", 0));
        companies.add(setCompany(2, 0, "Mcdonald’s", 1));
        when(companyRepository.findByUserId(0)).thenReturn(companies);
        Map<String, List<Object>> expected = new LinkedHashMap<>();
        Map<String, Object> expected1 = new LinkedHashMap<>();
        expected1.put("companyName","Google");
        expected1.put("receiveEmail",1);
        expected.put("0", Arrays.asList(expected1));
        Map<String, Object> expected2 = new LinkedHashMap<>();
        expected2.put("companyName","Microsoft");
        expected2.put("receiveEmail",0);
        expected.put("1", Arrays.asList(expected2));
        Map<String, Object> expected3 = new LinkedHashMap<>();
        expected3.put("companyName","Mcdonald’s");
        expected3.put("receiveEmail",1);
        expected.put("2", Arrays.asList(expected3));

        Object result = companyService.getSpecificCompany(0);
        String expectedString = expected.toString();
        String resultString = result.toString();
        assertEquals(expectedString, resultString);
    }

    @Test
    public void testUpdateCompany() {
        Company company0 = setCompany(0, 0, "Google", 1);
        when(companyRepository.findById(0)).thenReturn(company0);
        String result = companyService.updateCompany(0);
        assertEquals("Update company successfully", result);
        Company company1 = setCompany(0, 0, "Appier", 0);
        when(companyRepository.findById(0)).thenReturn(company1);
        String result1 = companyService.updateCompany(0);
        assertEquals("Update company successfully", result1);
    }

    @Test
    public void testDeleteCompany() {
        String result = companyService.deleteCompany(0);
        assertEquals("Delete company successfully", result);
    }
}
