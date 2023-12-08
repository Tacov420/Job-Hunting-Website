package JobHunting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;
import JobHunting.model.*;
import JobHunting.repository.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.time.LocalDate;

@Service
public class NotificationService {

    private JobRepository jobRepository;
    private NotificationRepository notificationRepository;
    private CompanyRepository companyRepository;
    private ProfileRepository profileRepository;
    private ProgressRepository progressRepository;

    @Autowired
    public NotificationService(
            JobRepository jobRepository,
            NotificationRepository notificationRepository,
            CompanyRepository companyRepository, ProfileRepository profileRepository,
            ProgressRepository progressRepository) {
        this.jobRepository = jobRepository;
        this.notificationRepository = notificationRepository;
        this.companyRepository = companyRepository;
        this.profileRepository = profileRepository;
        this.progressRepository = progressRepository;
    }

    public void saveNotification(List<Notification> notification) {
        notificationRepository.saveAll(notification);
    }

    public void createNotification(Notification notification) {
        notificationRepository.save(notification);
    }

    public void markAsRead(int notificationId) {
        Optional<Notification> notificationOpt = notificationRepository.findById(notificationId);
        notificationOpt.ifPresent(notification -> {
            notification.setRead(true);
            notificationRepository.save(notification);
        });
    }

    public void deleteNotification(int notificationId) {
        notificationRepository.deleteById(notificationId);
    }

    // For Jobs posted by companies

    @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
    public void checkAndNotifyNewJobs() {
        List<Job> newJobs = jobRepository.findByNotificationSentFalse();

        for (Job job : newJobs) {
            Optional<Company> companyOptional = companyRepository.findOptionalById(job.getCompanyId());
            if (companyOptional.isPresent()) {
                Company company = companyOptional.get();
                List<Profile> profiles = profileRepository.findByCompanyId(company.getId());

                for (Profile profile : profiles) {
                    String message = "New job posted: " + job.getJobTitle() + " at " + company.getName();
                    sendJobNotification(profile.getUserName(), message);
                }
                job.setNotificationSent(true);
                jobRepository.save(job);
            }
        }
    }

    private void sendJobNotification(String userName, String message) {
        Notification notification = new Notification();
        notification.setUserName(userName); // Link the notification to the user
        notification.setMessage(message);
        notification.setDate(new Date());
        notification.setSent(false);
        notification.setRead(false);
        notification.setNotificationType("Job"); // Set the type as per your logic

        notificationRepository.save(notification);
    }

    public List<Notification> getNotificationByUserName(String userName) {
        return notificationRepository.findByUserName(userName);
    }

    public void createJobNotificationMessageAndSend(Job job, String userName) {
        String message = "New job posted: " + job.getJobTitle() + " at " + job.getCompany();
        sendJobNotification(userName, message);
    }

    // For progress notifications

    @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
    public void createInterviewNotifications() {
        List<Notification> notifications = getUpcomingInterviewNotifications();
        for (Notification notification : notifications) {
            notificationRepository.save(notification);
        }
    }

    public List<Notification> getUpcomingInterviewNotifications() {
        LocalDate threeDaysFromNow = LocalDate.now().plusDays(3);
        List<Progress> allProgresses = progressRepository.findAll();
        List<Notification> notifications = new ArrayList<>();

        for (Progress progress : allProgresses) {
            // Assuming stageStatus of 1 means 'Interview Scheduled'
            for (int i = 0; i < progress.getStages().size(); i++) {
                if (progress.getStageStatus().get(i) == 1 && progress.getDates().get(i).equals(threeDaysFromNow)) {
                    int userId = progress.getUserId();
                    String username = getUserName(userId);

                    Notification newNotification = new Notification();
                    newNotification.setUserName(username);
                    newNotification.setMessage("You have an interview for " + progress.getJobTitle() + " at " +
                            progress.getCompanyName() + " in 3 days.");
                    newNotification.setNotificationType("Interview");
                    newNotification.setDate(java.sql.Date.valueOf(progress.getDates().get(i))); // Set the interview
                                                                                                // date
                    newNotification.setSent(false);
                    newNotification.setRead(false);

                    notifications.add(newNotification); // Add the notification to the list
                } else if (progress.getStageStatus().get(i) == 1
                        && progress.getDates().get(i).equals(LocalDate.now())) {
                    int userId = progress.getUserId();
                    String username = getUserName(userId);

                    Notification newNotification = new Notification();
                    newNotification.setUserName(username);
                    newNotification.setMessage("You have an interview for " + progress.getJobTitle() + " at " +
                            progress.getCompanyName() + " today.");
                    newNotification.setNotificationType("Interview");
                    newNotification.setDate(java.sql.Date.valueOf(progress.getDates().get(i))); // Set the interview
                                                                                                // date
                    newNotification.setSent(false);
                    newNotification.setRead(false);

                    notifications.add(newNotification); // Add the notification to the list
                }
            }

        }

        return notifications; // Return the list of notifications
    }

    public String getUserName(int userId) {
        Optional<Profile> profile = profileRepository.findById(String.valueOf(userId));
        return profile.map(Profile::getUserName).orElse("Unknown User");
    }

}
