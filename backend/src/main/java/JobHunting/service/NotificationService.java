package JobHunting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Subscription;

import JobHunting.model.*;
import JobHunting.repository.*;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

@Service
public class NotificationService {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private ProgressRepository progressRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Value("${vapid.public.key}")
    private String publicKey;
    @Value("${vapid.private.key}")
    private String privateKey;

    private PushService pushService;

    @PostConstruct
    public void init() throws GeneralSecurityException, IOException {
        Security.addProvider(new BouncyCastleProvider());
        pushService = new PushService(publicKey, privateKey);
    }

    private String createInterviewReminderMessageJson(Progress progress) {
        return "{\"title\": \"Interview Reminder\", \"body\": \"You have an interview with " + progress.getCompanyName()
                + " in 3 days.\"}";
    }

    private String createJobNotificationMessage(Job job) {
        return "{\"title\": \"New Job Posting\", \"body\": \"" + job.getJobTitle() + " position available at "
                + job.getCompany() + "\"}";
    }

    public void sendNotification(Subscription subscription, String messageJson) {
        try {
            nl.martijndwars.webpush.Notification notification = new nl.martijndwars.webpush.Notification(subscription,
                    messageJson);
            pushService.send(notification);
        } catch (Exception e) {
            // Log the exception for better error tracking
            e.printStackTrace();
        }
    }

    public boolean deleteNotification(String userName, int notificationId) {
        JobHunting.model.Notification notification = notificationRepository
                .findByNotificationId(notificationId);
        if (notification == null || !notification.getUserName().equals(userName)) {
            return false;
        }
        notificationRepository.delete(notification);
        return true;
    }

    public List<JobHunting.model.Notification> getAllNotification(String userName) {
        return notificationRepository.findByUserName(userName);
    }

    public void sendNotification(JobHunting.model.Subscription customSubscription, String messageJson) {
        try {
            // Convert the custom subscription to the library's subscription type
            Subscription librarySubscription = new Subscription(
                    customSubscription.getEndpoint(),
                    new Subscription.Keys(customSubscription.getPublicKey(), customSubscription.getAuth()));

            // Create and send the notification
            Notification notification = new Notification(librarySubscription, messageJson);
            pushService.send(notification);
        } catch (Exception e) {
            e.printStackTrace(); // Consider logging this error
        }
    }

    @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
    public void checkAndNotifyNewJobs() {
        List<Job> newJobs = jobRepository.findByNotificationSentFalse();
        List<JobHunting.model.Subscription> allSubscriptions = subscriptionRepository.findAll();
        for (Job job : newJobs) {
            String message = createJobNotificationMessage(job);
            for (JobHunting.model.Subscription customSubscription : allSubscriptions) {
                sendNotification(customSubscription, message);
            }
            job.setNotificationSent(true);
            jobRepository.save(job);
        }
    }

    @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
    public void sendInterviewReminders() {
        List<Progress> upcomingInterviews = progressRepository.findIsDateWithinThreshold(new Date(), 3);
        upcomingInterviews.forEach(progress -> {
            String messageJson = createInterviewReminderMessageJson(progress);
            String userName = progress.getUserName();
            JobHunting.model.Subscription customSubscription = subscriptionRepository.findByUserName(userName);
            if (customSubscription != null) {
                sendNotification(customSubscription, messageJson);
            }
        });
    }

    public void IsDateWithinThreshold(Date interviewDate, int days) {
        List<Progress> upcomingInterviews = progressRepository.findIsDateWithinThreshold(interviewDate, days);
        upcomingInterviews.forEach(progress -> {
            String messageJson = createInterviewReminderMessageJson(progress);
            String userName = progress.getUserName();
            JobHunting.model.Subscription customSubscription = subscriptionRepository.findByUserName(userName);
            if (customSubscription != null) {
                sendNotification(customSubscription, messageJson);
            }
        });
    }

}
