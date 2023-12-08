package JobHunting.service;

import org.bson.types.ObjectId;
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

    @Autowired
    public NotificationService(
            JobRepository jobRepository,
            NotificationRepository notificationRepository,
            CompanyRepository companyRepository) { // Autowired companyRepository
        this.jobRepository = jobRepository;
        this.notificationRepository = notificationRepository;
        this.companyRepository = companyRepository;
    }

    @Scheduled(fixedRate = 1000 * 60 * 60 * 24) // Once a day
    public void checkAndNotifyNewJobs() {
        List<Job> newJobs = jobRepository.findByNotificationSentFalse();

        newJobs.forEach(job -> {
            Company company = companyRepository.findById(job.getCompanyId());

            if (company != null) {
                List<String> interestedUserNames = getAllInterestedUserNamesForCompany(company);

                interestedUserNames.forEach(userName -> {
                    String message = createJobNotificationMessage(job);
                    sendNotification(userName, message);
                });

                job.setNotificationSent(true);
                jobRepository.save(job);
            }
        });
    }

    private List<String> getAllInterestedUserNamesForCompany(Company company) {

        return company.getInterestedUsers();
    }

    public void createJobNotificationMessageAndSend(Job job) {
        String message = createJobNotificationMessage(job);
        sendNotification(job.getCompany(), message);
    }

    private String createJobNotificationMessage(Job job) {
        return "New job posted: " + job.getJobTitle() + " at " + job.getCompany();
    }

    private String determineNotificationType(String message) {
        return message.contains("interview") ? "Interview" : "Job";
    }

    private void fillNotificationDetails(Notification notification, String userName, String message) {
        notification.setUserName(userName);
        notification.setMessage(message);
        notification.setDate(new Date());
        notification.setSent(false);
        notification.setRead(false);
        notification.setNotificationType(determineNotificationType(message));
    }

    public List<Notification> getNotificationsByUserName(String userName) {
        return notificationRepository.findByUserName(userName);
    }

    public void CreateNotification(String userName, Job job) {
        // Create a new Notification instance
        Notification newNotification = new Notification();

        newNotification.setUserName(userName);
        newNotification.setMessage("New job posted: " + job.getJobTitle());
        newNotification.setJobId(job.get_id()); // Assuming you have a get_id method in Job class
        newNotification.setDate(new Date());
        newNotification.setSent(false);
        newNotification.setRead(false);
        newNotification.setNotificationType("Job"); // Set the type as per your logic

        createNotification(newNotification);
    }

    public void createNotification(Notification notification) {
        notificationRepository.save(notification);
    }

    public void sendNotification(String userName, String message) {
        Notification notification = new Notification();
        fillNotificationDetails(notification, userName, message);
        notificationRepository.save(notification);
    }

    public void markAsRead(ObjectId notificationId) {
        Optional<Notification> notificationOpt = notificationRepository.findById(notificationId);
        notificationOpt.ifPresent(notification -> {
            notification.setRead(true);
            notificationRepository.save(notification);
        });
    }

    public void deleteNotification(ObjectId notificationId) {
        notificationRepository.deleteById(notificationId);
    }

}
