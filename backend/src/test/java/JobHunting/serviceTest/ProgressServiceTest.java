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
public class ProgressServiceTest {
    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private ProgressRepository progressRepository;

    @InjectMocks
    private ProgressService progressService;

    Progress setProgress() {
        Progress progress = new Progress();
        progress.setProgressId(0);
        progress.setUserId(0);
        progress.setCompanyname("McDonald’s");
        progress.setJobTitle("Fries Quality Control Engineer");
        progress.initStages("send resume", LocalDate.parse("2023-12-09"), 0);
        return progress;
    }

    @Test
    public void testGetProgressList() {
        Progress progress0 = setProgress();
        List<Progress> progressList = Arrays.asList(
            progress0            
        );
        when(progressRepository.findByUserId(0)).thenReturn(progressList);
        Map<String, List<Object>> expected = new HashMap<>();
        expected.put("0", Arrays.asList("McDonald’s", "Fries Quality Control Engineer", Arrays.asList("send resume"), Arrays.asList("2023-12-09"), Arrays.asList(0)));
        Object result = progressService.getProgressList(0);
        String expectedString = expected.toString();
        String resultString = result.toString();
        assertEquals(expectedString, resultString);
    }

    @Test
    public void testGetSpecificProgress() {
        Progress progress0 = setProgress();
        when(progressRepository.findByProgressId(0)).thenReturn(progress0);
        Map<String, List<Object>> expected = new HashMap<>();
        expected.put("0", Arrays.asList("McDonald’s", "Fries Quality Control Engineer", Arrays.asList("send resume"), Arrays.asList("2023-12-09"), Arrays.asList(0)));
        Object result = progressService.getSpecificProgress(0, 0);
        String expectedString = expected.toString();
        String resultString = result.toString();
        assertEquals(expectedString, resultString);
    }

    @Test
    public void testCreateProgress() {
        when(progressRepository.findFirstByOrderByProgressIdDesc()).thenReturn(null);
        String companyName = "McDonald’s";
        String jobTitle = "Fries Quality Control Engineer";
        String stageName = "send resume";
        LocalDate date = LocalDate.parse("2023-12-09");
        int status = 0;
        int result = progressService.createProgress(0, companyName, jobTitle, stageName, date, status);
        assertEquals(0, result);

        Progress progress = setProgress();
        when(progressRepository.findFirstByOrderByProgressIdDesc()).thenReturn(progress);
        int result1 = progressService.createProgress(0, companyName, jobTitle, stageName, date, status);
        assertEquals(1, result1);
    }

    @Test
    public void testCreateStage() {
        Progress progress0 = setProgress();
        when(progressRepository.findByProgressId(0)).thenReturn(progress0);
        String stageName = "一面";
        LocalDate date = LocalDate.parse("2023-12-15");
        int status = 1;
        String result = progressService.createStage(0, stageName, date, status);
        assertEquals("Edit progress successfully", result);
    }

    @Test
    public void testEditStage() {
        Progress progress0 = setProgress();
        when(progressRepository.findByProgressId(0)).thenReturn(progress0);
        String stageName = "一面";
        LocalDate date = LocalDate.parse("2023-12-15");
        int status = 1;
        String result = progressService.editStage(0, 0, stageName, date, status);
        assertEquals("Edit progress successfully", result);
    }

    @Test
    public void testDeleteProgress() {
        String result = progressService.deleteProgress(0);
        assertEquals("Delete progress successfully", result);
    }
}
