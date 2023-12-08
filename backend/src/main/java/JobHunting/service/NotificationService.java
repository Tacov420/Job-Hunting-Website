package JobHunting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;
import JobHunting.model.*;
import JobHunting.repository.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    private JobRepository jobRepository;
    private NotificationRepository notificationRepository;
    private CompanyRepository companyRepository;
    private ProfileRepository profileRepository;

    @Autowired
    public NotificationService(
            JobRepository jobRepository,
            NotificationRepository notificationRepository,
            CompanyRepository companyRepository) {
        this.jobRepository = jobRepository;
        this.notificationRepository = notificationRepository;
        this.companyRepository = companyRepository;
    }

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
                    sendNotification(profile.getUserName(), message);
                }

                job.setNotificationSent(true);
                jobRepository.save(job);
            }
        }
    }

    private void sendNotification(String userName, String message) {
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

    public int createNotification(String userName, Job job, String companyName, String message, String notificationType,
            Date date, boolean sent, boolean isRead) {
        // Create a new Notification instance
        Notification largestNotification = notificationRepository.findFirstByOrderByIdDesc();
        int id;
        if (largestNotification != null) {
            id = largestNotification.getNotificationId() + 1;
        } else {
            id = 0;
        }
        Notification newNotification = new Notification();
        newNotification.setUserName(userName);
        newNotification.setMessage("New job posted: " + job.getJobTitle());
        newNotification.setJobId(job.get_id()); // Link the notification to the job
        newNotification.setDate(new Date());
        newNotification.setSent(false);
        newNotification.setRead(false);
        newNotification.setNotificationType("Job"); // Set the type as per your logic

        // Save the notification to the repository
        notificationRepository.save(newNotification);
        return id;
    }

    public void createJobNotificationMessageAndSend(Job job, String userName) {
        String message = "New job posted: " + job.getJobTitle() + " at " + job.getCompany();
        sendNotification(userName, message);
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

}
