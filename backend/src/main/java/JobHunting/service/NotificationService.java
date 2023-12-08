package JobHunting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;
import JobHunting.model.*;
import JobHunting.repository.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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

    public void createJobNotificationMessageAndSend(Job job, String userName) {
        // Assuming job.getJobTitle() and job.getCompany() return non-null values
        String message = "New job posted: " + job.getJobTitle() + " at " + job.getCompany();

        Notification notification = new Notification();
        // Set the necessary notification details
        notification.setUserName(userName);
        notification.setMessage(message);
        notification.setNotificationType("Job");
        notification.setDate(new Date());
        notification.setSent(false);
        notification.setRead(false);
        // notification.setNotificationId(notificationRepository.findFirstByOrderByIdDesc().getNotificationId()
        // + 1);

        notificationRepository.save(notification); // Save the notification
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
        notification.setRead(false);
        notification.setNotificationType("Job"); // Set the type as per your logic

        notificationRepository.save(notification); // Save the notification

    }

    public List<Notification> getNotificationByUserName(String userName) {
        return notificationRepository.findByUserName(userName);
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
        LocalDate today = LocalDate.now();
        LocalDate threeDaysFromNow = today.plusDays(3);
        List<Progress> allProgresses = progressRepository.findAll();

        return allProgresses.stream()
                .flatMap(progress -> IntStream.range(0, progress.getStages().size())
                        .filter(i -> progress.getStageStatus().get(i) == 1 &&
                                (progress.getDates().get(i).equals(today)
                                        || progress.getDates().get(i).equals(threeDaysFromNow)))
                        .mapToObj(i -> createNotification(progress, i)))
                .collect(Collectors.toList());
    }

    private Notification createNotification(Progress progress, int index) {
        LocalDate date = progress.getDates().get(index);
        LocalDate today = LocalDate.now(); // Declare and initialize the 'today' variable with the current date

        String username = getUserName(progress.getUserId()); // Make sure this method is efficient

        String timingMessage = date.equals(today) ? " today." : " in 3 days.";
        String message = "You have an interview for " + progress.getJobTitle() + " at " +
                progress.getCompanyName() + timingMessage;

        Notification notifications = new Notification();
        notifications.setUserName(username);
        notifications.setMessage(message);
        notifications.setNotificationType("Interview");
        notifications.setDate(java.sql.Date.valueOf(date));
        notifications.setRead(false);

        return notifications;
    }

    public String getUserName(int userId) {
        Optional<Profile> profile = profileRepository.findById(String.valueOf(userId));
        return profile.map(Profile::getUserName).orElse("Unknown User");
    }

}
