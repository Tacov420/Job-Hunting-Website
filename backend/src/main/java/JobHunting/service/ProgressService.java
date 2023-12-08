package JobHunting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import JobHunting.repository.*;
import JobHunting.model.*;

@Service
public class ProgressService {
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProgressRepository progressRepository;

    public int getUserId(String userName) {
        Profile profile = profileRepository.findByUserName(userName);
        if (profile == null) {
            return -1;
        }
        return profile.getId();
    }

    // public Object getProgressList(int userId, int categoryId) {
    // List<Progress> progresses = progressRepository.findByCategoryId(categoryId);
    // Map<String, List<Object>> returnMap = new HashMap<>();
    // for (Progress progress: progresses) {
    // List<Object> progressList = new ArrayList<>();
    // progressList.add(progress.getCompanyName());
    // progressList.add(progress.getJobTitle());
    // progressList.add(progress.getStages());
    // progressList.add(progress.getDates());
    // progressList.add(progress.getStageStatus());
    // if (progress.getUserId() == userId) {
    // progressList.add(true);
    // } else {
    // progressList.add(false);
    // }
    // returnMap.put(String.valueOf(progress.getId()), progressList);
    // }

    // return returnMap;
    // }

    public Object getProgressList(int userId) {
        List<Progress> progressList = progressRepository.findByUserId(userId);
        Map<String, List<Object>> returnMap = new HashMap<>();
        for (Progress progress : progressList) {
            List<Object> progressData = new ArrayList<>();
            progressData.add(progress.getCompanyName());
            progressData.add(progress.getJobTitle());
            progressData.add(progress.getStages());
            progressData.add(progress.getDates());
            progressData.add(progress.getStageStatus());
            returnMap.put(String.valueOf(progress.getProgressId()), progressData);
        }

        return returnMap;
    }

    public Object getSpecificProgress(int userId, int progressId) {
        Progress progress = progressRepository.findByProgressId(progressId);
        Map<String, List<Object>> returnMap = new HashMap<>();
        List<Object> progressData = new ArrayList<>();
        progressData.add(progress.getCompanyName());
        progressData.add(progress.getJobTitle());
        progressData.add(progress.getStages());
        progressData.add(progress.getDates());
        progressData.add(progress.getStageStatus());
        returnMap.put(String.valueOf(progress.getProgressId()), progressData);

        return returnMap;
    }

    public boolean checkProgressId(int progressId) {
        Progress progress = progressRepository.findByProgressId(progressId);
        if (progress == null) {
            return false;
        }
        return true;
    }

    public int createProgress(int userId, String companyName, String jobTitle, String stageName, LocalDate date,
            int status) {
        int id;
        Progress largestProgress = progressRepository.findFirstByOrderByProgressIdDesc();
        if (largestProgress != null) {
            id = largestProgress.getProgressId() + 1;
        } else {
            id = 0;
        }
        Progress progress = new Progress();
        progress.setId(id);
        progress.setUserId(userId);
        progress.setCompanyname(companyName);
        progress.setJobTitle(jobTitle);
        progress.initStages(stageName, date, status);
        progressRepository.save(progress);

        return id;
    }

    public boolean checkPermission(int userId, int progressId) {
        Progress progress = progressRepository.findByProgressId(progressId);
        if (progress.getUserId() != userId) {
            return false;
        }
        return true;
    }

    public String createStage(int progressId, String stageName, LocalDate date, int status) {
        Progress progress = progressRepository.findByProgressId(progressId);
        progress.addStage(stageName, date, status);
        progressRepository.save(progress);

        return "Edit progress successfully";
    }

    public String editStage(int progressId, int index, String stageName, LocalDate date, int status) {
        Progress progress = progressRepository.findByProgressId(progressId);
        progress.setStage(index, stageName, date, status);
        progressRepository.save(progress);

        return "Edit progress successfully";
    }

    public String deleteProgress(int progressId) {
        progressRepository.deleteByProgressId(progressId);

        return "Delete progress successfully";
    }
}
