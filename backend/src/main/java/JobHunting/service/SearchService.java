package JobHunting.service;

import JobHunting.model.*;
import JobHunting.repository.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchService {
  @Autowired private SearchRepository searchRepository;

  public Object getJobs(String company, String jobTitle, int level) {
    List<Search> returnJobs = new ArrayList<>();

    if (company != null && !company.isEmpty()) {
      List<Search> jobByCompany = searchRepository.findByCompany(company);
      if (jobTitle != null && !jobTitle.isEmpty()) {
        List<Search> jobByJobTitle = searchRepository.findByJobTitle(jobTitle);
        if (level == 0) {
          List<Search> jobByLevel = searchRepository.findByLevel("Entry level");
          for (Search job : jobByCompany) {
            if (jobByJobTitle.contains(job) == true && jobByLevel.contains(job) == true) {
              returnJobs.add(job);
            }
          }
        } else if (level == 1) {
          List<Search> jobByLevel = searchRepository.findByLevel("Mid-Senior level");
          for (Search job : jobByCompany) {
            if (jobByJobTitle.contains(job) == true && jobByLevel.contains(job) == true) {
              returnJobs.add(job);
            }
          }
        } else if (level == 2) {
          for (Search job : jobByCompany) {
            if (jobByJobTitle.contains(job) == true) {
              returnJobs.add(job);
            }
          }
        }
      } else {
        if (level == 0) {
          List<Search> jobByLevel = searchRepository.findByLevel("Entry level");
          for (Search job : jobByCompany) {
            if (jobByLevel.contains(job) == true) {
              returnJobs.add(job);
            }
          }
        } else if (level == 1) {
          List<Search> jobByLevel = searchRepository.findByLevel("Mid-Senior level");
          for (Search job : jobByCompany) {
            if (jobByLevel.contains(job) == true) {
              returnJobs.add(job);
            }
          }
        } else if (level == 2) {
          for (Search job : jobByCompany) {
            returnJobs.add(job);
          }
        }
      }
    } else {
      if (jobTitle != null && !jobTitle.isEmpty()) {
        List<Search> jobByJobTitle = searchRepository.findByJobTitle(jobTitle);
        if (level == 0) {
          List<Search> jobByLevel = searchRepository.findByLevel("Entry level");
          for (Search job : jobByJobTitle) {
            if (jobByLevel.contains(job) == true) {
              returnJobs.add(job);
            }
          }
        } else if (level == 1) {
          List<Search> jobByLevel = searchRepository.findByLevel("Mid-Senior level");
          for (Search job : jobByJobTitle) {
            if (jobByLevel.contains(job) == true) {
              returnJobs.add(job);
            }
          }
        } else if (level == 2) {
          for (Search job : jobByJobTitle) {
            returnJobs.add(job);
          }
        }
      } else {
        if (level == 0) {
          List<Search> jobByLevel = searchRepository.findByLevel("Entry level");
          for (Search job : jobByLevel) {
            returnJobs.add(job);
          }

        } else if (level == 1) {
          List<Search> jobByLevel = searchRepository.findByLevel("Mid-Senior level");
          for (Search job : jobByLevel) {
            returnJobs.add(job);
          }
        }
      }
    }

    return returnJobs;
  }
}

