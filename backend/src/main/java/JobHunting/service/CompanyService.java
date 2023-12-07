package JobHunting.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JobHunting.repository.*;
import JobHunting.model.*;


@Service
public class CompanyService {
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private JobRepository jobRepository;

    public Object getAllCompany(int userId) {
        List<Job> jobs = jobRepository.findAll();
        Map<String, List<Object>> returnMap = new HashMap<>();
        List<Object> companyName = new ArrayList<>();
        for (Job job: jobs) {
            Object name = job.getCompany();
            if (name != null){
                companyName.add(name);
            }
        }
        Set<Object> uniqueCompanies = new HashSet<>(companyName);
        List<Company> companies = companyRepository.findByUserId(userId);
        List<Object> companyDeatils = new ArrayList<>();
        for (Object name: uniqueCompanies) {
            List<Object> companyDeatil = new ArrayList<>();
            String strName = name.toString();
            boolean exists = companies.stream().anyMatch(company -> company.getName().equals(strName));
            if (exists) {
                companyDeatil.add(strName);
                companyDeatil.add(true);
            } else  {
                companyDeatil.add(strName);
                companyDeatil.add(false);
            }
            companyDeatils.add(companyDeatil);
        }
        returnMap.put("companies", companyDeatils);

        return returnMap;
    }

    public int getUserId(String userName) {
        Profile profile = profileRepository.findByUserName(userName);
        if (profile == null) {
            return -1;
        }
        return profile.getId();
    }

    public boolean checkCompanyId(int companyId) {
        Company company = companyRepository.findById(companyId);
        if (company == null) {
            return false;
        }
        return true;
    }

    public boolean checkPermission(int userId, int companyId) {
        Company company = companyRepository.findById(companyId);
        if (company.getUserId() != userId) {
            return false;
        }
        return true;
    }

    public int addCompany(int userId, String companyName) {
        int id;
        Company largestCompany = companyRepository.findFirstByOrderByIdDesc();
        if (largestCompany != null){
            id = largestCompany.getId() + 1;
        } else {
            id = 0;
        }
        Company company = new Company();
        company.setCompany(userId, companyName, id, 1);
        companyRepository.save(company);

        return id;
    }

    public Object getSpecificCompany(int userId) {
        List<Company> companies = companyRepository.findByUserId(userId);
        Map<String, List<Object>> returnMap = new HashMap<>();
        for (Company company: companies) {
            List<Object> companyList = new ArrayList<>();
            Map<String, Object> companyDetails = new HashMap<>();
            companyDetails.put("companyName", company.getName());
            companyDetails.put("receiveEmail", company.getReceiveEmail());
            companyList.add(companyDetails);
            returnMap.put(String.valueOf(company.getId()), companyList);
        }
        return returnMap;
    }

    public String updateCompany(int companyId) {
        Company company = companyRepository.findById(companyId);
        if (company.getReceiveEmail() == 0) {
            company.setReceiveEmail(1);
        } else {
            company.setReceiveEmail(0);
        }
        companyRepository.save(company);

        return "Update company successfully";
    }

    public String deleteCompany(int companyId) {
        companyRepository.deleteById(companyId);

        return "Delete company successfully";
    }
}
